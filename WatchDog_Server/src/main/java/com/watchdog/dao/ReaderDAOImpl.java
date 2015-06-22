package com.watchdog.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.watchdog.entity.ReaderEntity;

@Repository
public class ReaderDAOImpl implements ReaderDAO {

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	@Transactional
	public ReaderEntity getReaderByIp(String ip) {
		StringBuilder hql = new StringBuilder();
		hql.append("from ReaderEntity R where R.readerIP = :ip");
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql.toString());
		query.setParameter("ip", ip);
		List<ReaderEntity> result = query.list();
		if (!result.isEmpty()) {
			return result.get(0);
		}
    	return null;
	}

	@Override
	public ReaderEntity getReaderByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
