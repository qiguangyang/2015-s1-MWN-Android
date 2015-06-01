package com.watchdog.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;

import com.watchdog.entity.CaregiverEntity;

public class CaregiverDAOImpl implements CaregiverDAO {

	//Session factory injected by spring context
    private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CaregiverEntity> getAuthInfo(String name) {
		StringBuilder hql = new StringBuilder();
		hql.append("from CaregiverEntity where username = :username");
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql.toString());
		query.setParameter("username", name);
		List<CaregiverEntity> result = query.list();
    	return result;
	}
}
