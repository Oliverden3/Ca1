package datafacades;

import dtos.PhoneDTO;
import entities.*;
import errorhandling.DuplicateException;
import errorhandling.EntityNotFoundException;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.enterprise.inject.New;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PhoneFacadeTest {

    private static EntityManagerFactory emf;
    private static PhoneFacade facade;

    Phone p1,p2;


    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = PhoneFacade.getPhoneFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test

    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Phone.deleteAllRows").executeUpdate();

            p1 = new Phone(88888888, "Iphone");
            p2 = new Phone(2020, "Titanic");

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
    void create() throws DuplicateException {
        System.out.println("Testing create(Phone p)");

        Phone p = new Phone(2022,"Rock");
        p.setId(3);
        Phone expected = p;
        Phone actual   = facade.create(p);
        assertEquals(expected, actual);
    }


    @Test
    void getById() throws EntityNotFoundException {
        System.out.println("Testing getbyid(id)");
        int phoneNumberExpected = p1.getNumber();
        PhoneDTO actual = facade.getPhoneById(p1.getId());
        int phoneNumberActual = actual.getNumber();
        assertEquals(phoneNumberExpected, phoneNumberActual);
    }

    @Test
    void getAll() {
        System.out.println("Testing getAll()");
        int expected = 2;
        int actual = facade.getAllPhones().size();
        assertEquals(expected,actual);
    }

    @Test
    void update() throws EntityNotFoundException {
        System.out.println("Testing Update(Phone p)");
        p2.setNumber(1965);
        Phone expected = p2;
        Phone actual = facade.update(p2);
        assertEquals(expected,actual);
    }


    @Test
    void delete() throws EntityNotFoundException {
        System.out.println("Testing delete(id)");
        Phone p = facade.delete(p1.getId());
        int expected = 1;
        int actual = facade.getAllPhones().size();
        assertEquals(expected, actual);
        assertEquals(p,p1);
    }
}

