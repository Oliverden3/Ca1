package datafacades;

import dtos.PersonDTO;
import entities.Address;
import entities.Hobby;
import entities.Person;
import entities.Phone;
import errorhandling.EntityNotFoundException;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * created by THA
 * Purpose of this facade example is to show a facade used as a DB facade (only operating on entity classes - no DTOs
 * And to show case some different scenarios
 */
public class PersonFacade  {

    private static PersonFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private PersonFacade() {}


    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static PersonFacade getPersonFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }


    public Person create(Person person){

        EntityManager em = getEntityManager();
        Person newPerson = new Person(person.getEmail(),person.getFirstName(),person.getLastName(),person.getAddress());

        try {

            em.getTransaction().begin();
            em.persist(newPerson);
            em.getTransaction().commit();

        } finally {
            em.close();
        }
        return person;
    }

    public PersonDTO createDTOPerson (PersonDTO personDTO){
        EntityManager em = getEntityManager();
        Set<Hobby> hobbies = new LinkedHashSet<>();
        personDTO.getHobbies().forEach(hobbyInnerDTO -> {
            hobbies.add(em.find(Hobby.class,hobbyInnerDTO.getId()));
        });
        Set<Phone> phones = new LinkedHashSet<>();

        Address address = em.find(Address.class,personDTO.getAddress().getId());
        Person person = new Person(personDTO);
        person.setHobbies(hobbies);
        person.setAddress(address);
        try{
            em.getTransaction().begin();
            personDTO.getPhones().forEach(phoneInnerDTO -> {
                Phone phone = new Phone(phoneInnerDTO.getNumber(),phoneInnerDTO.getDescription(),person);
                em.persist(phone);
                phones.add(phone);
                person.setPhones(phones);

            });
            em.persist(person);
            em.getTransaction().commit();
        }finally {
            em.close();
        }
        return new PersonDTO(person);
    }

    public PersonDTO createDTOPersonTest (PersonDTO personDTO){
        EntityManager em = getEntityManager();
        Person person = new Person(personDTO);
        try{
            em.getTransaction().begin();
            em.persist(person);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new PersonDTO(person);
    }


   /* public PersonDTO getPersonByPhone(int phoneNumber){
        EntityManager em = getEntityManager();

        try{
            TypedQuery findPerson = em.createQuery("SELECT p FROM Person p WHERE p.phone.number = :number",Person.class);
            findPerson.setParameter("number",phoneNumber);
            Person personFound = (Person) findPerson.getSingleResult();
            return new PersonDTO(personFound);
        } finally {
            em.close();
        }
    } */


    public PersonDTO getPersonById(int id) throws EntityNotFoundException {

        EntityManager em = getEntityManager();

        try{

            TypedQuery findPerson = em.createQuery("SELECT p FROM Person p WHERE p.id =:person_id",Person.class);
            findPerson.setParameter("person_id",id);
            Person personFound = (Person) findPerson.getSingleResult();
            return new PersonDTO(personFound);

        } finally {
            em.close();
        }
    }


    public List<PersonDTO> getAllPeople(){

        EntityManager em = getEntityManager();

        try{

            TypedQuery findAll = em.createQuery("SELECT p FROM Person p", Person.class);
            List<Person> people = findAll.getResultList();
            return PersonDTO.getDTOs(people);

        }finally {
            em.close();
        }

    }


    public Person update(Person person) throws EntityNotFoundException {
        if (person.getId() == 0)
            throw new IllegalArgumentException("No Person can be updated when id is missing");
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        Person p = em.merge(person);
        em.getTransaction().commit();
        return p;
    }


    public Person delete(int id) throws EntityNotFoundException{
        EntityManager em = getEntityManager();
        Person p = em.find(Person.class, id);
        if (p == null)
            throw new EntityNotFoundException("Could not remove Person with id: "+id);
        em.getTransaction().begin();
        em.remove(p);
        em.getTransaction().commit();
        return p;
    }

    public Person addPhoneToPerson(int phoneID, int personID){
        EntityManager em = emf.createEntityManager();
        Phone phone = em.find(Phone.class, phoneID);
        Person person = em.find(Person.class, personID);
        em.getTransaction().begin();
            phone.setPerson(person);
        em.getTransaction().commit();
        em.close();
        return person;
    }
    public Person addHobbyToPerson(int hobbyID, int personID){
        EntityManager em = emf.createEntityManager();
        Hobby hobby = em.find(Hobby.class, hobbyID);
        Person person = em.find(Person.class, personID);
        em.getTransaction().begin();
        person.addHobby(hobby);
        em.getTransaction().commit();
        em.close();
        return person;
    }

}

