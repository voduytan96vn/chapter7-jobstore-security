package org.osbook.jobstore.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.osbook.jobstore.domain.Patient;

@Stateless
public class PatientService {

	@PersistenceContext(unitName = "jobstore")
	private EntityManager entityManager;

	public Patient save(Patient patient) {
		entityManager.persist(patient);
		return patient;
	}

	public Patient findById(Long id) {
		try {
			Patient patient = entityManager.createNamedQuery("Patient.findById", Patient.class).setParameter("id", id)
					.getSingleResult();
			return patient;
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<Patient> findAll() {
		return entityManager.createNamedQuery("Patient.findAll", Patient.class).getResultList();
	}

	public Patient findByName(String name) {
		try {
			Patient patient = entityManager.createNamedQuery("Patient.findByName", Patient.class)
					.setParameter("name", name).getSingleResult();
			return patient;
		} catch (NoResultException e) {
			return null;
		}

	}

	public void update(Patient patient) {
		entityManager.merge(patient);
	}

	public boolean delete(Long id) {
		try {
			Patient patient = entityManager.getReference(Patient.class, id);
			entityManager.remove(patient);
			return true;
		} catch (EntityNotFoundException e) {
			return false;
		}
	}
}
