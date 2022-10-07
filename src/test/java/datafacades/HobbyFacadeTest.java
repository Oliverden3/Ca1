package datafacades;
import dtos.HobbyDTO;
import entities.*;
import errorhandling.EntityNotFoundException;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class HobbyFacadeTest {

    private static EntityManagerFactory emf;
    private static HobbyFacade facade;
    Person person1, person2, person3, person4;
    Hobby h1,h2;

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = HobbyFacade.getHobbyFacade(emf);

    }
    @AfterAll
    public static void tearDownClass() {

    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Set<Person> h1Set = new HashSet<>();
            em.createNamedQuery("Hobby.deleteAllRows").executeUpdate();
            person1 = new Person("anders@meinicke.dk","Anders","Meinicke");
            person2 = new Person("emil@meinicke.dk","Emil","Meinicke");
            em.persist(person1);
            em.persist(person2);
            h1Set.add(person1);
            h1Set.add(person2);
            Set<Person> h2Set = new HashSet<>();
            person3 = new Person("christopher@ottesen.dk","Christopher","Ottesen");
            person4 = new Person("someone@iknow.dk","Someone","Iknow");
            h2Set.add(person3);
            h2Set.add(person4);
            h1 = new Hobby("Karate",h1Set);
            h2 = new Hobby("Swimming",h2Set);

            em.persist(h1);
            em.persist(h2);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    @Test
    void create() {
        Set<Person> h3set = new HashSet<>();
        Person p = new Person("jegErEnLilleNisse@mail.com","Christoffer","Nielsen");
        h3set.add(p);
        Hobby h3 = new Hobby("Creating Cults", h3set);
        h3.setId(3);
        Hobby expected = h3;
        Hobby actual = facade.create(h3);
        assertEquals(expected, actual);
    }
    @Test
    void getById() throws EntityNotFoundException {
        System.out.println("Testing getbyid(id)");
        Hobby expected = h1;
        String hobbyDescExpected = h1.getDescription();
        HobbyDTO actual = facade.getHobbyById(h1.getId());
        String hobbyDescActual = actual.getDescription();
        assertEquals(hobbyDescExpected, hobbyDescActual);
    }
    @Test
    void getAll() {
        System.out.println("Testing getAll()");
        int expected = 2;
        int actual = facade.getAll().size();
        assertEquals(expected,actual);
    }
    @Test
    void update() throws EntityNotFoundException {
        System.out.println("Testing Update");
        h1.setDescription("Pokemon club");
        Hobby expected = h1;
        Hobby actual = facade.update(h1);
        assertEquals(expected,actual);
    }
    @Test
    void delete() throws EntityNotFoundException {
        System.out.println("Testing delete(id)");
        Hobby h = facade.delete(h1.getId());
        int expected = 1;
        int actual = facade.getAll().size();
        assertEquals(expected, actual);
        assertEquals(h,h1);
    }
}


