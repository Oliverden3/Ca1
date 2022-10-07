/* package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.Person;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

//Uncomment the line below, to temporarily disable this test
@Disabled
public class MovieResourceTest {

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
    //TODO -- Make sure to change the EntityClass used below to use YOUR OWN (renamed) Entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        p1 = new Person("christoffer@christoffer.com","christoffer","nielsen");
        p2 = new Person("rasmus@rasmus.com","rasmus","taulo");

        try {
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
        System.out.println("Testing is server UP");
        given().when().get("/person").then().statusCode(200);
    }

    @Test
    public void testLogRequest() {
        System.out.println("Testing logging request details");
        given().log().all()
                .when().get("/person")
                .then().statusCode(200);
    }

    @Test
    public void testLogResponse() {
        System.out.println("Testing logging response details");
        given()
                .when().get("/person")
                .then().log().body().statusCode(200);
    }

    @Test
    public void testGetById()  {
        given()
                .contentType(ContentType.JSON)
                .get("/person/{id}",p1.getId())
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("id", equalTo(p1.getId()))
                .body("firstName", equalTo(p1.getFirstName()))
                .body("lastName",equalTo(p1.getLastName()));
    }

    @Test
    public void testError() {
        given()
                .contentType(ContentType.JSON)
//                .pathParam("id", p1.getId()).when()
                .get("/person/{id}",999999999)
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND_404.getStatusCode())
                .body("code", equalTo(404))
                .body("message", equalTo("The person entity with ID: 999999999 Was not found"));
    }

    @Test
    public void testPrintResponse(){
        Response response = given().when().get("/person/"+p1.getId());
        ResponseBody body = response.getBody();
        System.out.println(body.prettyPrint());

        response
                .then()
                .assertThat()
                .body("firstName",equalTo("christoffer"));
    }


    @Test 
    public void exampleJsonPathTest() {
        Response res = given().get("/movie/"+
        .getId());
        assertEquals(200, res.getStatusCode());
        String json = res.asString();
        JsonPath jsonPath = new JsonPath(json);
        assertEquals("GTA the movie", jsonPath.get("title"));
    }

    @Test
    public void getAllMovies() throws Exception {
        List<MovieDTO> movieDTOs;

        movieDTOs = given()
                .contentType("application/json")
                .when()
                .get("/movie")
                .then()
                .extract().body().jsonPath().getList("", MovieDTO.class);

        MovieDTO m1DTO = new MovieDTO(m1);
        MovieDTO m2DTO = new MovieDTO(m2);
        assertThat(movieDTOs, containsInAnyOrder(m1DTO, m2DTO));

    }


    @Test
    public void postTest() {
        Movie m = new Movie(2004,"Harry Potter");

        MovieDTO mdto = new MovieDTO(m);
        String requestBody = GSON.toJson(mdto);

        given()
                .header("Content-type", ContentType.JSON)
                .and()
                .body(requestBody)
                .when()
                .post("/movie")
                .then()
                .assertThat()
                .statusCode(200)
                .body("id", notNullValue())
                .body("year", equalTo(2004))
                .body("title",equalTo("Harry Potter"));
    }

    @Test
    public void updateTest() {
        m2.setYear(1602);
        m2.setTitle("Krigen");
        MovieDTO mdto = new MovieDTO(m2);
        String requestBody = GSON.toJson(mdto);

        given()
                .header("Content-type", ContentType.JSON)
                .body(requestBody)
                .when()
                .put("/movie/"+m2.getId())
                .then()
                .assertThat()
                .statusCode(200)
                .body("id", equalTo(m2.getId()))
                .body("year", equalTo(1602))
                .body("title", equalTo("Krigen"));
    }

    @Test
    public void testDeleteParent() {
        given()
                .contentType(ContentType.JSON)
                .pathParam("id", m2.getId())
                .delete("/movie/{id}")
                .then()
                .statusCode(200)
                .body("id",equalTo(m2.getId()));
    }

    // More test tools from: https://www.baeldung.com/java-junit-hamcrest-guide
    @Test
    public void testListSize() {
        System.out.println("Check size of list");
        List<String> hamcrestMatchers = Arrays.asList(
                "collections", "beans", "text", "number");
        assertThat(hamcrestMatchers, hasSize(4));
    }
    @Test
    public void testPropAndValue() {
        System.out.println("Check for property and value on an entity instance");
        Movie movie = new Movie(2009, "San Andreas");
        assertThat(movie, hasProperty("title", equalTo("San Andreas")));
    }
    @Test
    public void testCompareObjects() {
        System.out.println("Check if 2 instances has same property values (EG. use compare properties rather than objects");
        Movie movie1 = new Movie(1999, "My Movie");
        Movie movie2 = new Movie(1999, "My Movie");
        assertThat(movie1, samePropertyValuesAs(movie2));
    }
    @Test
    public void testToString(){
        System.out.println("Check if obj.toString() creates the right output");
        Movie movie = new Movie(2004, "Ost");
        String str=movie.toString();
        assertThat(movie,hasToString(str));
    }

    @Test
    public void testNumeric() {
        System.out.println("Test numeric values");
        assertThat(1.2, closeTo(1, 0.5));
        assertThat(5, greaterThanOrEqualTo(5));

        List<Integer> list = Arrays.asList(1, 2, 3);
        int baseCase = 0;
        assertThat(list, everyItem(greaterThan(baseCase)));
    }
    @Test
    public void testMoreReadable() {
        System.out.println("Use the IS, NOT etc keywords for readability");
        String str1 = "text";
        String str2 = "texts";
        String str3 = "texts";
        String str4 = "These are several texts in one sentence";
        assertThat(str1, not(str2));
        assertThat(str2, is(str3));
        assertThat(str4, containsString(str2));

    }



}

*/
