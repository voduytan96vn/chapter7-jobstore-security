package org.osbook.jobstore.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@NamedQueries({
		@NamedQuery(name = "Allergy.findAllByPatient", query = "SELECT NEW Allergy(j, c.id, c.name,c.description) FROM Allergy j JOIN j.patient c WHERE j.patient =:patient"),
		@NamedQuery(name = "Allergy.findById", query = "SELECT NEW Allergy(j.id,j.title,j.description) FROM Allergy j WHERE j.id =:allergyId")
})
public class Allergy {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	private String title;

	@NotNull
	@Size(max = 4000)
	private String description;

	@Column(updatable = false)
	@Temporal(TemporalType.DATE)
	@NotNull
	private Date submissionDate = new Date();

	private boolean filled = false;

	@ElementCollection(fetch = FetchType.EAGER)
	private Set<String> skills = new HashSet<>();

	@ManyToOne
	@NotNull
	private Patient patient;

	public Allergy() {
	}

	public Allergy(Long id, String title, String description) {
		this.id = id;
		this.title = title;
		this.description = description;
	}

	public Allergy(String title, String description, boolean filled) {
		this.title = title;
		this.description = description;
		this.filled = filled;
	}

	public Allergy(Allergy job, long id, String name, String description) {
		this.id = job.id;
		this.title = job.title;
		this.description = job.description;
		this.filled = job.filled;
		this.submissionDate = job.submissionDate;
		this.skills = job.skills;
		this.patient = new Patient(id, name, description);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isFilled() {
		return filled;
	}

	public void setFilled(boolean filled) {
		this.filled = filled;
	}

	public Set<String> getSkills() {
		return skills;
	}

	public void setSkills(Set<String> skills) {
		this.skills = skills;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Date getSubmissionDate() {
		return submissionDate;
	}

}