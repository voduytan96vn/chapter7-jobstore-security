package org.osbook.jobstore.rest;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.osbook.jobstore.domain.Patient;
import org.osbook.jobstore.services.PatientService;
import org.osbook.jobstore.utils.NumberUtils;

@Path("/patients")
public class PatientResource {

	@Inject
	private PatientService patientService;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createNewPatient(@Valid Patient patient) {
		Patient existingPatientWithName = patientService.findByName(patient.getName());
		if (existingPatientWithName != null) {
			return Response.status(Status.NOT_ACCEPTABLE)
					.entity(String.format("Patient already exists with name: %s", patient.getName())).build();
		}
		patient = patientService.save(patient);
		return Response.status(Status.CREATED).entity(patient).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Patient> showAll() {
		return patientService.findAll();
	}

	@Path("/{idOrName}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Patient showPatient(@PathParam("idOrName") String idOrName) {
		if (NumberUtils.isNumber(idOrName)) {
			return patientService.findById(Long.valueOf(idOrName));
		}
		return patientService.findByName(idOrName);
	}

	@Path("/{id}")
	@DELETE
	public Response deletePatient(@PathParam("id") Long id) {
		boolean deleted = patientService.delete(id);
		if (deleted) {
			return Response.ok().build();
		}
		return Response.status(Status.NOT_FOUND).build();
	}

	@Path("/{id}")
	@PUT
	public Response updatePatientInformation(@PathParam("id") Long id, @Valid Patient patient) {
		Patient existingPatient = patientService.findById(id);
		if (existingPatient == null) {
			return Response.status(Status.NOT_FOUND).entity(String.format("No patient exists with id: %d", id)).build();
		}
		patient.setId(id);
		patientService.update(patient);
		return Response.ok().build();
	}

}
