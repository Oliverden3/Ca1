package rest;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import datafacades.AddressFacade;
import datafacades.PhoneFacade;
import entities.Address;
import entities.Person;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


//Todo Remove or change relevant parts before ACTUAL use

@Path("address")
public class AddressResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final AddressFacade FACADE =  AddressFacade.getAddressFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
            
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAll() {

        return Response.ok().entity(GSON.toJson(FACADE.getAll())).build();
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(String content) {
        Address addressJson = GSON.fromJson(content, Address.class);
        Address newAddress = FACADE.create(addressJson);
        return Response.ok().entity(GSON.toJson(newAddress)).build();
    }

}
