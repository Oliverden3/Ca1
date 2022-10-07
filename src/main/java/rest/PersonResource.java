package rest;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import datafacades.PersonFacade;
import dtos.PersonDTO;
import entities.Person;
import errorhandling.EntityNotFoundException;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


//Todo Remove or change relevant parts before ACTUAL use
@Path("person")
public class PersonResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final PersonFacade FACADE =  PersonFacade.getPersonFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
            
    @GET
    @Path("/all")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAll() {

        return Response.ok().entity(GSON.toJson(FACADE.getAllPeople())).build();
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getById(@PathParam("id") int id) throws EntityNotFoundException {
        PersonDTO p = FACADE.getPersonById(id);
        return Response.ok().entity(GSON.toJson(p)).build();
    }


    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/addperson")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(String content) {
        PersonDTO personDTO = GSON.fromJson(content, PersonDTO.class);
        personDTO = FACADE.createDTOPersonTest(personDTO);
        return Response.ok().entity(GSON.toJson(personDTO)).build();
    }


//

    @PUT
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response update(@PathParam("id") int id, String content) throws EntityNotFoundException {
        Person personJson = GSON.fromJson(content, Person.class);
        personJson.setId(id);
        Person updated = FACADE.update(personJson);
        return Response.ok().entity(GSON.toJson(updated)).build();
    }


    @DELETE
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response delete(@PathParam("id") int id) throws EntityNotFoundException {
        Person deleted = FACADE.delete(id);
        return Response.ok().entity(GSON.toJson(deleted)).build();
    }


}
