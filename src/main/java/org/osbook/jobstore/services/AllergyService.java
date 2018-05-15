package org.osbook.jobstore.services;

import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

import org.osbook.jobstore.domain.Patient;
import org.osbook.jobstore.domain.Allergy;

@Stateless
public class AllergyService {

	@PersistenceContext(unitName = "jobstore")
	private EntityManager entityManager;
	@Inject
	private PatientService patientService;

	public Allergy save(Long patientId, Allergy allergy) {
		Patient patient = patientService.findById(patientId);
		System.out.println("Save Patient Name" + patient.getName());
		allergy.setPatient(patient);
		patient.getAllergies().add(allergy);
		entityManager.persist(allergy);
		System.out.println("Allergy Name" + allergy.getTitle());
		return allergy;
	}

	public Allergy findById(Long id) {
		return entityManager.createNamedQuery("Allergy.findById", Allergy.class).setParameter("allergyId", id)
				.getSingleResult();
	}

	public List<Allergy> findAllByPatient(Long patientId) {
		try {
			Patient patient = entityManager.getReference(Patient.class, patientId);
			return entityManager.createNamedQuery("Allergy.findAllByPatient", Allergy.class)
					.setParameter("patient", patient).getResultList();
		} catch (EntityNotFoundException e) {
			return Collections.emptyList();
		}
	}

	public void update(Allergy allergy) {
		entityManager.merge(allergy);
	}

	public boolean delete(Long id) {
		try {
			Allergy allergy = entityManager.getReference(Allergy.class, id);
			entityManager.remove(allergy);
			return true;
		} catch (EntityNotFoundException e) {
			return false;
		}
	}
}
