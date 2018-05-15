package org.osbook.jobstore.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
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
		@NamedQuery(name = "Allergy.findById", query = "SELECT NEW Allergy(j.id,j.title,j.description) FROM Allergy j WHERE j.id =:allergyId") })
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

	// @ManyToOne
	// @NotNull
	// private Patient patient;

	public Allergy() {
	}

	public Allergy(Long id, String title, String description) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		System.out.println("Allergy a");
		System.out.println(id);
		System.out.println(title);
		System.out.println(description);
	}

	public Allergy(String title, String description) {
		this.title = title;
		this.description = description;
		System.out.println("Allergy b");
		System.out.println(title);
		System.out.println(description);
	}

	public Allergy(Allergy allergy, long id, String name, String description) {
		this.id = allergy.id;
		this.title = allergy.title;
		this.description = allergy.description;
		this.submissionDate = allergy.submissionDate;
		// this.patient = new Patient(id, name, description);
		System.out.println("Allergy c");
		System.out.println(allergy.id);
		System.out.println(allergy.title);
		System.out.println(allergy.description);
		System.out.println(allergy.submissionDate);
		System.out.println(id);
		System.out.println(name);
		System.out.println(description);
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

	public Date getSubmissionDate() {
		return submissionDate;
	}

	public void setSubmissionDate(Date submissionDate) {
		this.submissionDate = submissionDate;
	}

}
