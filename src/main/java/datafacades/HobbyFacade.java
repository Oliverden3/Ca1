package datafacades;

import dtos.HobbyDTO;
import dtos.PersonDTO;
import entities.Hobby;
import entities.Person;
import errorhandling.EntityNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class HobbyFacade{
    private static HobbyFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private HobbyFacade() {}

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static HobbyFacade getHobbyFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new HobbyFacade();
        }
        return instance;
    }
    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }


    public Hobby create(Hobby hobby) {

        EntityManager em = getEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(hobby);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return hobby;
    }

    public HobbyDTO getHobbyById(int id) throws EntityNotFoundException {
        EntityManager em = getEntityManager();
        try{

            TypedQuery findHobby = em.createQuery("SELECT h FROM Hobby h WHERE h.id =:hobby_id",Hobby.class);
            findHobby.setParameter("hobby_id",id);
            Hobby hobbyFound = (Hobby) findHobby.getSingleResult();
            return new HobbyDTO(hobbyFound);

        } finally {
            em.close();
        }
    }

    public List<PersonDTO> getPeopleByHobby(String hobbyName) throws EntityNotFoundException{
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Person> findPeople = em.createQuery("SELECT p.person FROM PersonsHobby p JOIN p.hobby h WHERE h.description = :description", Person.class);
            findPeople.setParameter("description", hobbyName);
            List<Person> personDTOList = findPeople.getResultList();
            return PersonDTO.getDTOs(personDTOList);

        } finally {
            em.close();
        }
    }

    public int AmountOfPeopleWithSpecificHobby(String description) throws EntityNotFoundException{
        EntityManager em = getEntityManager();
        try {
            Query findPeople = em.createQuery("SELECT p.person FROM PersonsHobby p JOIN p.hobby h WHERE h.description = :description", Person.class);
            findPeople.setParameter("description", description);
            List<Person> personList = findPeople.getResultList();
            return personList.size();



        } finally {
            em.close();
        }
    }


    public List<HobbyDTO> getAll() {
        EntityManager em = getEntityManager();

        try{

            TypedQuery findAll = em.createQuery("SELECT h FROM Hobby h", Hobby.class);
            List<Hobby> hobbies = findAll.getResultList();
            return HobbyDTO.getDTOs(hobbies);

        }finally {
            em.close();
        }
    }


    public Hobby update(Hobby h) throws EntityNotFoundException {
        if (h.getId() == 0)
            throw new IllegalArgumentException("No hobby can be updated when id is missing");
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        Hobby hobby = em.merge(h);
        em.getTransaction().commit();
        return hobby;
    }


    public Hobby delete(int id) throws EntityNotFoundException {
        EntityManager em = getEntityManager();
        Hobby h = em.find(Hobby.class, id);
        if (h == null)
            throw new EntityNotFoundException("Could not remove hobby with id: "+id);
        em.getTransaction().begin();
        em.remove(h);
        em.getTransaction().commit();
        return h;
    }

}

