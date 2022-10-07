/* package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import datafacades.AddressFacade;
import datafacades.CityInfoFacade;
import datafacades.HobbyFacade;
import datafacades.PersonFacade;
import dtos.PersonDTO;
import entities.Address;
import entities.Cityinfo;
import entities.Person;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.List;

import static io.restassured.RestAssured.given;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;

public class PersonResourceTest {


    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";

    private static Person p1, p2;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        //System.in.read();

        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        PersonFacade pf = PersonFacade.getPersonFacade(emf);
        HobbyFacade hf = HobbyFacade.getHobbyFacade(emf);
        //   PhoneFacade phoneFacade = PhoneFacade.getPhoneFacade(emf);
        //   phoneFacade.create(new Phone(911,"samsung"));
        CityInfoFacade cf = CityInfoFacade.getCityInfoFacade(emf);
        AddressFacade af = AddressFacade.getAddressFacade(emf);
        try {
            Address address = new Address("nisseland","jul",new Cityinfo(4000,1000,"Kbh"));
            p1 = new Person(1,"christoffer@christoffer.com","christoffer","nielsen",address);
            p2 = new Person(2,"rasmus@rasmus.com","rasmus","taulo",address);
            em.getTransaction().begin();
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
            em.persist(p1);
            em.persist(p2);
            em.getTransaction().commit();
        } finally {
            em.close();
        }

    }


    @Test
    public void testServerIsUp() {
        System.out.println("testing is server up");
        given().when().get("/person/all").then().statusCode(200);
    }

    //TODO: remember to change the expected result after what is in your database
    @Test
    public void CheckoutPersonNameByID() {
      System.out.println("we're checking if we can take the firstname from localhost");
      try {
          System.out.println("HERE IT IS");
          System.out.println(p1.getId());

          given()
                  .contentType(ContentType.JSON)
                  .get("/person/{id}", p1.getId())
                  .then()
                  .assertThat()
                  .body("id", equalTo(p1.getId()))
                  .body("firstName", equalTo(p1.getFirstName()))
                  .body("lastName", equalTo(p1.getLastName()));
      }
      catch (NullPointerException e){
          System.out.println("HERE IT IS");
          System.out.println(p1.getId());
      }
    }
    @Test
    public void TestGetAllPeople(){
        System.out.println("testing getting all person");
        List<PersonDTO> personDTOs;

        personDTOs = given()
                .contentType("application/json")
                .when()
                .get("/person/all")
                .then()
                .extract().body().jsonPath().getList("", PersonDTO.class);
        assertEquals(personDTOs.size(), 2);
    }
    /*
    @Test
    public void TestUpdatePerson(){
        p1.setFirstName("Scooby");
        p2.setLastName("Doo");
       given()
                .contentType("application/json")
               .put("/person/{id}",p1.getId());
       given()
               .get("/person/{id}", p1.getId())
               .then()
               .assertThat()
               .body("firstName",equalTo("Scooby") )
               .body("lastName", equalTo("Doo"));
    }
     */ /*
    @Test
    public void testDeleteParent() {
        given()
                .contentType(ContentType.JSON)
                .pathParam("id", p2.getId())
                .delete("/person/{id}")
                .then()
                .statusCode(200)
                .body("id",equalTo(p2.getId()));
    }
}
*/
