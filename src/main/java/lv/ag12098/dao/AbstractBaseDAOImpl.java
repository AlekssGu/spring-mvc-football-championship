package lv.ag12098.dao;

import org.hibernate.SessionFactory;
import org.springframework.core.GenericTypeResolver;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

@Transactional
public class AbstractBaseDAOImpl<T> extends HibernateDaoSupport implements
		AbstractBaseDAO<T> {

	/**
	 * Spring injects the SessionFactory, we pass it to HibernateDaoSupport
	 * 
	 * @param sessionFactory
     */
	@Inject
	public void initSessionFactory(SessionFactory sessionFactory) {
		this.setSessionFactory(sessionFactory);
	}

	private Class<T> type;

	protected List<T> castedList(List l) {
		@SuppressWarnings("unchecked")
		List<T> list = l;
		return list;
	}

	public String entityName() {
		return this.type.getName();
	}

	@SuppressWarnings("unchecked")
	public AbstractBaseDAOImpl() {
		this.type = (Class<T>) GenericTypeResolver.resolveTypeArgument(
				getClass(), AbstractBaseDAOImpl.class);
	}

	public void delete(T obj) {
		currentSession().delete(obj);
	}

	public void deleteAll() {
		getHibernateTemplate().deleteAll(this.findAll());
	}

	public T find(Integer id) {
		return (T) getHibernateTemplate().get(this.type, id);
	}

	public void save(T obj) {
		getHibernateTemplate().saveOrUpdate(obj);
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		return (List<T>) getHibernateTemplate().find("FROM " + type.getName());
	}

	public Integer countAll() {
		return DataAccessUtils.intResult(getHibernateTemplate().find(
				"select count(*) from " + type.getName()));
	}
}
