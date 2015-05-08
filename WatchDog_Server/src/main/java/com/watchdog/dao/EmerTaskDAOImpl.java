package com.watchdog.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.watchdog.entity.EmerTaskEntity;

@Repository
public class EmerTaskDAOImpl implements EmerTaskDAO {

	//Session factory injected by spring context
    private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public void addTask(EmerTaskEntity task) {
		this.sessionFactory.getCurrentSession().save(task);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EmerTaskEntity> getTaskByUserID(int userId) {
		StringBuilder hql = new StringBuilder();
		hql.append("from EmerTaskEntity P where P.userid = " + userId);
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql.toString());
		List<EmerTaskEntity> result = query.list();
    	return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EmerTaskEntity> getAllTasks() {
		return this.sessionFactory.getCurrentSession().createQuery("from EmerTaskEntity").list();
	}

	@Override
	public void updateStatus(Integer patientId, String status) {
		// TODO Auto-generated method stub

	}

}
