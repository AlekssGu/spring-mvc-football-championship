package lv.ag12098.dao;

import lv.ag12098.entity.*;
import org.hibernate.Query;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Named
@Transactional
public class GameRefereesDAOImpl extends AbstractBaseDAOImpl<GameRefereesEntity>
        implements GameRefereesDAO {

        private static double round(double value, int places) {
            if (places < 0) throw new IllegalArgumentException();

            BigDecimal bd = new BigDecimal(value);
            bd = bd.setScale(places, RoundingMode.HALF_UP);
            return bd.doubleValue();
        }

        @Override
        public GameRefereesEntity getGameRefereeById (int refereeId){
                return (GameRefereesEntity) currentSession()
                        .createQuery("from " + entityName() + " where id = :refereeId ")
                        .setParameter("refereeId", refereeId)
                        .uniqueResult();
        }
        //nākošā sekvence (ID) - implementācija
        @Override
        public Long getNextSeq(){
                BigInteger nextSeq = (BigInteger) currentSession()
                        .createSQLQuery("SELECT nextval('public.\"game_referees_seq\"');")
                        .uniqueResult();

                return nextSeq.longValue();
        }

        public List<BestRefereesEntity> getAllRefereeData() {
            List<BestRefereesEntity> refereeList =  currentSession()
                    .createSQLQuery("select * from (\n" +
                            "select gr.referee_name || ' ' || gr.referee_surname as fullname, gr.referee_name as name, gr.referee_surname as surname, count(*) penalties\n" +
                            "  from game_referees gr\n" +
                            "  join game_referees_link grl on grl.referee_id = gr.id\n" +
                            "  join game gm on gm.id = grl.game_id\n" +
                            "  join game_penalties gp on gp.game_id = gm.id\n" +
                            " where referee_type = :refereeType\n" +
                            " group by gr.referee_name || ' ' || gr.referee_surname, gr.referee_name, gr.referee_surname) a \n" +
                            " order by penalties desc;")
                    .addEntity(BestRefereesEntity.class)
                    .setParameter("refereeType", "T")
                    .list();

            int index = 0;
            for (BestRefereesEntity referee : refereeList) {
                referee.setRefereesEntity(getGameRefereeByNameAndSurname(referee.getName(), referee.getSurname(), "T"));
                referee.setMinutesPlayedAsReferee(
                        getRefereeTimePlayed(getGameRefereeByNameAndSurname(referee.getName(),referee.getSurname(), "T")
                                ,"T")
                );
                referee.setMinutesPlayedAsLinesman(
                        getRefereeTimePlayed(getGameRefereeByNameAndSurname(referee.getName(), referee.getSurname(), "T")
                                ,"VT"));
                refereeList.set(index, referee);
                index++;
            }

            // sort by penalties given (goals = penalties)
            Collections.sort(refereeList, new Comparator<BestRefereesEntity>(){
                public int compare(BestRefereesEntity o1, BestRefereesEntity o2){
                    if(o1.getPenalties() == o2.getPenalties())
                        return 0;
                    return o1.getPenalties() < o2.getPenalties() ? 1 : -1;
                }
            });

            if (refereeList == null) return null;
            else return refereeList;
        }

        public GameRefereesEntity getGameRefereeByNameAndSurname(String name, String surname, String refereeType) {
            return (GameRefereesEntity) currentSession()
                    .createQuery("from " + entityName() + " where referee_name = :name and referee_surname = :surname and referee_type = :refereeType")
                    .setParameter("name", name)
                    .setParameter("surname", surname)
                    .setParameter("refereeType", refereeType)
                    .uniqueResult();
        }

        public boolean exists (String name, String surname, String refereeType) {
            Query query = currentSession().createQuery("select count(*) from " + entityName() +
                    " where referee_name = :name and referee_surname = :surname and referee_type = :refereeType");

            query.setParameter("name", name);
            query.setParameter("surname", surname);
            query.setParameter("refereeType", refereeType);

            Long count = (Long) query.uniqueResult();

            if (count > 0) return true;
            else return false;
        }

        public double getRefereeTimePlayed(GameRefereesEntity refereesEntity, String refereeType) {
            List result = currentSession()
                    .createSQLQuery("select coalesce(sum(get_game_length(g.id)),0)\n" +
                            "  from game g\n" +
                            "  join game_referees_link grl on grl.game_id = g.id\n" +
                            "  join game_referees gr on gr.id = grl.referee_id\n" +
                            " where gr.referee_type = :refereeType \n" +
                            "   and gr.referee_name = :refereeName \n" +
                            "   and gr.referee_surname = :refereeSurname ;")
                    .setParameter("refereeName",refereesEntity.getRefereeName())
                    .setParameter("refereeSurname",refereesEntity.getRefereeSurname())
                    .setParameter("refereeType",refereeType)
                    .list();

            if (result == null) return 0;
            else {
                double resultVal = (float) result.get(0);
                return round(resultVal,2);
            }
        }
}
