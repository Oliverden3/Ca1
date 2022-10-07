package rest;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import datafacades.CityInfoFacade;
import datafacades.PhoneFacade;
import dtos.PersonDTO;
import errorhandling.EntityNotFoundException;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


//Todo Remove or change relevant parts before ACTUAL use
@Path("phone")
public class PhoneResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final PhoneFacade FACADE =  PhoneFacade.getPhoneFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
            
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAll() {

        return Response.ok().entity(GSON.toJson(FACADE.getAllPhones())).build();
    }

    @GET
    @Path("/{number}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getPersonByNumber(@PathParam("number") int number) throws EntityNotFoundException {
        PersonDTO p = FACADE.getPersonByPhone(number);
        return Response.ok().entity(GSON.toJson(p)).build();
    }

}
