package datafacades;

import dtos.CityInfoDTO;
import dtos.PersonDTO;
import entities.Cityinfo;
import entities.Person;
import errorhandling.EntityNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 *
 * created by THA
 * Purpose of this facade example is to show a facade used as a DB facade (only operating on entity classes - no DTOs
 * And to show case some different scenarios
 */
public class CityInfoFacade {

    private static CityInfoFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private CityInfoFacade() {}
    
    
    /**
     * 
     * @param _emf
     * @return an instance of this facade class.
     */
    public static CityInfoFacade getCityInfoFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new CityInfoFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public CityInfoDTO getCityInfoById(int id) throws EntityNotFoundException {
        EntityManager em = getEntityManager();

        try{

            TypedQuery findCityInfo = em.createQuery("SELECT c FROM Cityinfo c WHERE c.id =:cityinfo_id", Cityinfo.class);
            findCityInfo.setParameter("cityinfo_id",id);
            Cityinfo cityInfoFound = (Cityinfo) findCityInfo.getSingleResult();
            return new CityInfoDTO(cityInfoFound);

        } finally {
            em.close();
        }
    }

    public List<PersonDTO> getPeopleByZipCode(int zipCode) throws EntityNotFoundException {
        EntityManager em = getEntityManager();
        try{
            TypedQuery<Person> findPeople = em.createQuery("SELECT p FROM Person p JOIN p.address a JOIN a.cityinfo ci WHERE ci.zipCode = :zipCode",Person.class);
            findPeople.setParameter("zipCode",zipCode);
            List<Person> personDTOList = findPeople.getResultList();
            return PersonDTO.getDTOs(personDTOList);
        } catch (Exception e){
            throw new EntityNotFoundException("There are no people living in that zipcode!");
        } finally {
            em.close();
        }

    }
/*
 try {
            TypedQuery<Person> findPeople = em.createQuery("SELECT p.person FROM PersonsHobby p JOIN p.hobby h WHERE h.description = :description", Person.class);
            findPeople.setParameter("description", hobbyName);
            List<Person> personDTOList = findPeople.getResultList();
            return PersonDTO.getDTOs(personDTOList);

        } finally {
            em.close();
        }
 */

    public List<CityInfoDTO> getAll(){
        EntityManager em = getEntityManager();

        try{

            TypedQuery findAll = em.createQuery("SELECT c FROM Cityinfo c", Cityinfo.class);
            List<Cityinfo> cityinfos = findAll.getResultList();
            return CityInfoDTO.getDTOs(cityinfos);

        }finally {
            em.close();
        }
    }

}
