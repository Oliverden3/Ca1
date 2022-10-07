package datafacades;

import dtos.PersonDTO;
import entities.Address;
import entities.Cityinfo;
import entities.Person;
import errorhandling.EntityNotFoundException;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;


class PersonFacadeTest {

    private static EntityManagerFactory emf;
    private static PersonFacade facade;

    Person p1,p2;

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = PersonFacade.getPersonFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test

    }

    // Setup the DataBase in  a known state BEFORE EACH TEST
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        Cityinfo cityinfoOne = new Cityinfo(1,2670,"greve");
        Cityinfo cityinfoTwo = new Cityinfo(2,2750,"ballerup");
        Address addressOne = new Address("knøsen 52","huehue",cityinfoOne);
        Address addressTwo = new Address("sankt jacobsvej","test",cityinfoTwo);

        try {
            em.getTransaction().begin();
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
            p1 = new Person("email@email.com", "Postmand", "Per",addressOne);
            p2 = new Person("amen@altså.com", "Ben", "Dover",addressTwo);

            em.persist(p1);
            em.persist(p2);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
    }


    @Test
    void create() {
        System.out.println("Testing create(Person p)");
        Cityinfo cityinfo = new Cityinfo(4,1000,"Kbh");
        Address address = new Address(1,"nisseland","jul",cityinfo);
        Person p = new Person("jegErEnLilleNisse@mail.com","Christoffer","Nielsen",address);
        p.setId(3);
        Person expected = p;
        Person actual   = facade.create(p);
        assertEquals(expected, actual);
    }


    @Test
    void getById() throws EntityNotFoundException {
        System.out.println("Testing getbyid(id)");
        String expected =p1.getEmail();
        PersonDTO actual = facade.getPersonById(p1.getId());
        String actualEmail = actual.getEmail();
        assertEquals(expected, actualEmail);
    }

    @Test
    void getAll() {
        System.out.println("Testing getAll()");
        int expected = 2;
        int actual = facade.getAllPeople().size();
        assertEquals(expected,actual);
    }

    @Test
    void update() throws EntityNotFoundException {
        System.out.println("Testing Update(Persin p)");
        p2.setEmail("lightWeightBby@hmail.cz");
        Person expected = p2;
        Person actual = facade.update(p2);
        assertEquals(expected,actual);
    }


    @Test
    void delete() throws EntityNotFoundException {
        System.out.println("Testing delete(id)");
        Person p = facade.delete(p1.getId());
        int expected = 1;
        int actual = facade.getAllPeople().size();
        assertEquals(expected, actual);
        assertEquals(p,p1);
    }
}







