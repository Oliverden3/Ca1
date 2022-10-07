package rest;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import datafacades.CityInfoFacade;
import datafacades.HobbyFacade;
import dtos.PersonDTO;
import entities.Person;
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
@Path("hobby")
public class HobbyResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final HobbyFacade FACADE =  HobbyFacade.getHobbyFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
            
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAll() {

        return Response.ok().entity(GSON.toJson(FACADE.getAll())).build();
    }
    @GET
    @Path("/{description}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getPeopleByHobby(@PathParam("description") String description) throws EntityNotFoundException {
        try{
            return Response.ok().entity(GSON.toJson(FACADE.getPeopleByHobby(description))).build();
        } catch (Exception e){
            throw new EntityNotFoundException("There's no hobby named that!");
        }

    }

    @GET
    @Path("/amount/{description}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response AmountWithSpecificHobby(@PathParam("description") String description) throws EntityNotFoundException {
        try{
            return Response.ok().entity(GSON.toJson(FACADE.AmountOfPeopleWithSpecificHobby(description))).build();
        } catch (Exception e){
            throw new EntityNotFoundException("There's no hobby named that!");
        }

    }



}
