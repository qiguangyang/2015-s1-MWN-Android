package com.watchdog.dao;

import java.util.Date;
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
		hql.append("from EmerTaskEntity P where P.relatedCareGiverId = " + userId);
		hql.append(" order by case status when 'RED' then 1 when 'YELLOW' then 2 when 'GREEN' then 3 end");
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql.toString());
		List<EmerTaskEntity> result = query.list();
    	return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EmerTaskEntity> getAllTasks() {
		StringBuilder hql = new StringBuilder();
		hql.append("from EmerTaskEntity");
		hql.append(" order by case status when 'RED' then 1 when 'YELLOW' then 2 when 'GREEN' then 3 end");
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql.toString());
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public EmerTaskEntity getTaskById(int taskId) {
		StringBuilder hql = new StringBuilder();
		hql.append("from EmerTaskEntity P where P.id = " + taskId);
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql.toString());
		List<EmerTaskEntity> result = query.list();
		if (result.size() !=0 ) {
			return result.get(0);
		} else {
			return null;
		}
	}

	@Override
	public boolean updateStatus(Integer taskId, String status) {
		status = status.toUpperCase();
		String hql = "UPDATE EmerTaskEntity set status = :status, endtime = :endtime " + 
	             "WHERE id = :taskid AND status != :status";
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("status", status);
		query.setParameter("endtime", new Date());
		query.setParameter("taskid", taskId);
		try {
			int result = query.executeUpdate();
			if (result == 1) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
