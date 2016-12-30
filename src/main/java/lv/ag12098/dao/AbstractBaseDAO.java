package lv.ag12098.dao;

import java.util.List;

public interface AbstractBaseDAO<T> {
	void delete(T obj);

	T find(Integer id);

	void save(T t);

	List<T> findAll();

	Integer countAll();
}
