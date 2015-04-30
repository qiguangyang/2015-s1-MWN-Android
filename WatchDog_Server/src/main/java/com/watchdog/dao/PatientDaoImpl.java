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
	
    //This method will be called when a patient object is added
	@Override
	public void addPatient(PatientEntity patient) {
		this.sessionFactory.getCurrentSession().save(patient);
	}

	//This method return list of patients in database
	@SuppressWarnings("unchecked")
	@Override
	public List<PatientEntity> getAllPatients() {
		return this.sessionFactory.getCurrentSession().createQuery("from PatientEntity").list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
    public List<PatientEntity> getPatient(String name) {
		StringBuilder hql = new StringBuilder();
		hql.append("from PatientEntity P where P.firstname = " + name);
		hql.append(" or p.lastname = " + name);
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql.toString());
		List<PatientEntity> result = query.list();
    	return result;
    }

	//Deletes a patient by it's id
	@Override
	public void deletePatient(Integer patientId) {
		PatientEntity patient = (PatientEntity) sessionFactory.getCurrentSession()
										.load(PatientEntity.class, patientId);
        if (null != patient) {
        	this.sessionFactory.getCurrentSession().delete(patient);
        }
	}

	//This setter will be used by Spring context to inject the sessionFactory instance
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
