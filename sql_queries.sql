delete from game_penalties;
delete from game_changes;
delete from goal_assists;
delete from game_goals;
delete from public.players_on_field;
delete from public.game_referees_link;
delete from public.game_referees;
delete from public.game;
delete from public.team_players;
delete from public.team;

select team_id, player_number, count(*)
  from team_players tp
  group by team_id, player_number;
 
 
 
select * from players_on_field where EXTRACT(MINUTE FROM time_on) > 0 OR EXTRACT(SECOND FROM TIME_ON) > 0;
select time_on, time_off, extract(minute from time_off) - extract(minute from time_on) from players_on_field where player_id = 4461;

CREATE OR REPLACE FUNCTION get_game_length (integer) RETURNS real AS $$
DECLARE
  v_game_id ALIAS FOR $1;
  v_max_goal_time timestamp;
  v_seconds real;
  v_minutes real;
  v_minutes_total real;
  v_hours real;
BEGIN
    SELECT max(gg.goal_time)
      INTO v_max_goal_time
      FROM game g
      JOIN game_goals gg ON gg.game_id = g.id
     WHERE g.id = v_game_id;
      
    v_hours := extract(hour from v_max_goal_time);  
    v_minutes := extract(minute from v_max_goal_time);
    v_seconds := extract(second from v_max_goal_time);
    
    v_minutes_total := (v_hours * 60) + v_minutes + (v_seconds/60);
    
    IF v_minutes_total > 60 THEN
    	RETURN v_minutes_total;
    ELSE
    	RETURN 60;
END;
$$ LANGUAGE plpgsql;

select * 
  from team t
  join game g on (g.team_home_id = t.id or g.team_guest_id = t.id) 
  where t.id = 267 
    and g.is_overtime = true;
    
select coalesce(id,0) from game_goals;    
    
select * from (     
select tplay.id,
coalesce((select count(g.id) goal_cnt
  from team t
  join team_players tp on tp.team_id = t.id and tp.id = tplay.id
  join game_goals g on g.team_id = t.id and g.player_number = tp.player_number
 GROUP by tp.id),0) goals,
coalesce((select count(ga.id) goal_assist_count
  from team t
  join team_players tp on tp.team_id = t.id and tp.id = tplay.id
  join game_goals g on g.team_id = t.id
  join goal_assists ga on ga.goal_id = g.id and ga.player_number = tp.player_number
 GROUP by tp.id),0) assists
 from team_players tplay 
) tdata order by tdata.goals desc nulls last, tdata.assists desc nulls last;

select * 
  from players_on_field;
select tp.* 
  from team_players tp 
 where tp.position = 'V'
   and exists (select 1 
                 from players_on_field po 
                where po.team_id = tp.team_id 
                  and po.player_id = tp.id);

select player_id, count(*) 
  from players_on_field
 where player_id = 4239
 group by player_id;
                  
select * from team_players where id in (4254,4265) or( player_number = 16 and team_id = 294);
select * from game_changes;
select * from team_players where team_id = 292 and player_number = 16;
select gg.*, fd.*, g.*
  from players_on_field fd
  join game g on g.id = fd.game_id
  left outer join game_goals gg on gg.game_id = fd.game_id
 where fd.player_id = 4265 -- 4254 
   and gg.team_id <> fd.team_id
   and (fd.time_off is null or gg.goal_time between fd.time_on and fd.time_off);
 
select gp.*
  from game_penalties gp
  join team tm on tm.id = gp.team_id
  join team_players tp on tp.team_id = tm.id 
 where tp.id = 4265
   and gp.player_number = tp.player_number;

select * from game_referees where id = 1788;

select * from game_referees;
select * from game_referees_link;

select * from (
select gr.referee_name || ' ' || gr.referee_surname, gr.referee_name, gr.referee_surname, count(*) penalties
  from game_referees gr
  join game_referees_link grl on grl.referee_id = gr.id
  join game gm on gm.id = grl.game_id
  join game_penalties gp on gp.game_id = gm.id
 where referee_type = 'T'
 group by gr.referee_name || ' ' || gr.referee_surname, gr.referee_name, gr.referee_surname) a 
 order by penalties desc;
 
select * from players_on_field where player_id in (4254,4265); 
select * from game where id = 593;
select * from game_changes where game_id = 593 and team_id = 294;
 select * from game_goals where game_id = 593;
select * from team where id in(293,294); 
select * from team where id in(292); 
 
select *
  from players_on_field fd
 where fd.id = 4226;
 
select * from game;

select fd.* 
  from game_changes gc
  join team tm on tm.id = gc.team_id
  join team_players tp on tp.team_id = tm.id
  join players_on_field fd on fd.game_id = gc.game_id 
       and fd.team_id = tm.id 
       and fd.player_id = tp.id
 where exists (select 1 
                 from game_goals gg 
                where gg.game_id = gc.game_id
                  and gg.team_id != tm.id
                  and (fd.time_off is null or gg.goal_time between fd.time_on and fd.time_off))
   and tp.id = 4249;
   
  join game_goals gg on gg.game_id = gc.game_id 
       and gg.team_id = tm.id 
       and gg.player_number = tp.player_number;
                  
select * from game;
select * from game_changes;
select * from players_on_field where player_id = 4249;
select * from team where id = 293;
select * 
  from public.goal_assists ga
  join game_goals gg on gg.id = ga.goal_id
  join team_players tp on tp.id = 4249;
  
  
4249
4223
4254
select * from public.game where is_overtime = true;

select count(*) 
                from team t 
                join game g on (g.team_home_id = t.id or g.team_guest_id = t.id) 
                where t.id = 240
                and g.is_overtime = true;

select * from game_changes where game_id = 366;

select count(*) from team t  join game g on g.team_home_id = t.id or g.team_guest_id = t.id where t.id = 1 and g.is_overtime = true

select teamentity0_.id as id1_7_, teamentity0_.game_count as game_cou2_7_, teamentity0_.games_lost as games_lo3_7_, teamentity0_.games_tied as games_ti4_7_, teamentity0_.games_won as games_wo5_7_, teamentity0_.goals_lost as goals_lo6_7_, teamentity0_.goals_relation as goals_re7_7_, teamentity0_.goals_scored as goals_sc8_7_, teamentity0_.team_name as team_nam9_7_, teamentity0_.total_points as total_p10_7_ 
from football_champ.public.team teamentity0_;