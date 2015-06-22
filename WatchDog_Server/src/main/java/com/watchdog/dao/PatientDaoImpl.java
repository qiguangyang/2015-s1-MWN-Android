package com.watchdog.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.watchdog.entity.PatientEntity;

@Repository
public class PatientDaoImpl implements PatientDAO  
{
	//Session factory injected by spring context
    private SessionFactory sessionFactory;
	
	@Override
	public void addPatient(PatientEntity patient) {
		this.sessionFactory.getCurrentSession().save(patient);
	}

	//This setter will be used by Spring context to inject the sessionFactory instance
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PatientEntity> getAllPatients() {
		return this.sessionFactory.getCurrentSession().createQuery("from PatientEntity").list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
    public List<PatientEntity> getPatientByName(String name) {
		StringBuilder hql = new StringBuilder();
		hql.append("from PatientEntity P where P.firstname = " + name);
		hql.append(" or p.lastname = " + name);
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql.toString());
		List<PatientEntity> result = query.list();
    	return result;
    }
	
	@Override
    public PatientEntity getPatientById(Integer id) {
		StringBuilder hql = new StringBuilder();
		hql.append("from PatientEntity P where P.id = " + id);
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql.toString());
		PatientEntity result = (PatientEntity) query.list().get(0);
    	return result;
    }

	@Override
	public void deletePatient(Integer patientId) {
		PatientEntity patient = (PatientEntity) sessionFactory.getCurrentSession()
										.load(PatientEntity.class, patientId);
        if (null != patient) {
        	this.sessionFactory.getCurrentSession().delete(patient);
        }
	}

	@Override
	public PatientEntity getPatientByTagId(String tagId) {
		StringBuilder hql = new StringBuilder();
		hql.append("from PatientEntity P where P.tagId = :tagId ");
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql.toString());
		query.setParameter("tagId", tagId);
		PatientEntity result = (PatientEntity) query.list().get(0);
    	return result;
	}
	
	
}
