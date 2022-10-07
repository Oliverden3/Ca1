package rest;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import datafacades.CityInfoFacade;
import datafacades.PersonFacade;
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
@Path("cityinfo")
public class CityInfoResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final CityInfoFacade FACADE =  CityInfoFacade.getCityInfoFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
            
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAll() {

        return Response.ok().entity(GSON.toJson(FACADE.getAll())).build();
    }

    @GET
    @Path("/{zipCode}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getPeopleByZipCode(@PathParam("zipCode") int zipCode) throws EntityNotFoundException {
        try{
            return Response.ok().entity(GSON.toJson(FACADE.getPeopleByZipCode(zipCode))).build();
        } catch (Exception e){
            throw new EntityNotFoundException("There's no people in that zipcode!");
        }

    }

}
