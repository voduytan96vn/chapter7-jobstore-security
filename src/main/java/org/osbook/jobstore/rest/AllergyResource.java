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

import org.osbook.jobstore.domain.Allergy;
import org.osbook.jobstore.services.AllergyService;

@Path("/patients/{patientId}/allergies")
public class AllergyResource {

	@Inject
	private AllergyService allergyService;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createNewAllergy(@PathParam("patientId") Long patientId, @Valid Allergy allergy) {
		allergy = allergyService.save(patientId, allergy);
		return Response.status(Status.CREATED).entity(allergyService.findById(allergy.getId())).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Allergy> showAll(@PathParam("patientId") Long patientId) {
		return allergyService.findAllByPatient(patientId);
	}

	@Path("/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Allergy showAllergy(@PathParam("patientId") Long patientId, @PathParam("id") Long id) {
		return allergyService.findById(id);
	}

	@Path("/{id}")
	@DELETE
	public Response deleteAllergy(@PathParam("patientId") Long patientId, @PathParam("id") Long id) {
		boolean deleted = allergyService.delete(id);
		if (deleted) {
			return Response.ok().build();
		}
		return Response.status(Status.NOT_FOUND).build();
	}

	@Path("/{id}")
	@PUT
	public Response updateAllergyInformation(@PathParam("patientId") Long patientId, @PathParam("id") Long id,
			@Valid Allergy allergy) {
		Allergy existingAllergy = allergyService.findById(id);
		if (existingAllergy == null) {
			return Response.status(Status.NOT_FOUND).entity(String.format("No allergy exists with id: %d", id)).build();
		}
		allergy.setId(id);
		allergyService.update(allergy);
		return Response.ok().build();
	}
}
