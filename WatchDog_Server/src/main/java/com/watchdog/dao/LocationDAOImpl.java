package com.watchdog.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.watchdog.entity.LocationEntity;

@Repository
public class LocationDAOImpl implements LocationDAO {

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public LocationEntity getLocationById(int id) {
		StringBuilder hql = new StringBuilder();
		hql.append("from LocationEntity L where L.id = " + id);
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql.toString());
		List<LocationEntity> result = query.list();
		if (!result.isEmpty()) {
			return result.get(0);
		}
    	return null;
	}

}
