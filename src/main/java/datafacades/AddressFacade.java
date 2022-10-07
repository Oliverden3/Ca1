package datafacades;

import dtos.AddressDTO;
import dtos.PersonDTO;
import entities.Address;
import entities.Person;
import errorhandling.EntityNotFoundException;
import utils.EMF_Creator;

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
public class AddressFacade {

    private static AddressFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private AddressFacade() {}
    
    
    /**
     * 
     * @param _emf
     * @return an instance of this facade class.
     */
    public static AddressFacade getAddressFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new AddressFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }


    public Address create(Address address){

        EntityManager em = getEntityManager();
        Address newAddress = new Address(address.getAddress(),address.getAdditionalInfo());

        try {
            em.getTransaction().begin();
            em.persist(newAddress);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return address;
    }


    public AddressDTO getAddressById(int id) throws EntityNotFoundException {
        EntityManager em = getEntityManager();

        try{

            TypedQuery findAddress = em.createQuery("SELECT a FROM Address a WHERE a.id =:address_id", Address.class);
            findAddress.setParameter("address_id",id);
            Address addressFound = (Address) findAddress.getSingleResult();
            return new AddressDTO(addressFound);

        } finally {
            em.close();
        }
    }


    public List<AddressDTO> getAll(){
        EntityManager em = getEntityManager();

        try{

            TypedQuery findAll = em.createQuery("SELECT a FROM Address a", Address.class);
            List<Address> addresses = findAll.getResultList();
            return AddressDTO.getDTOs(addresses);

        }finally {
            em.close();
        }
    }


    public Address update(Address address) throws EntityNotFoundException {
        if (address.getId() == 0)
            throw new IllegalArgumentException("No Address can be updated when id is missing");
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        Address a = em.merge(address);
        em.getTransaction().commit();
        return a;
    }


    public Address delete(int id) throws EntityNotFoundException{
        EntityManager em = getEntityManager();
        Address a = em.find(Address.class, id);
        if (a == null)
            throw new EntityNotFoundException("Could not remove Address with id: "+id);
        em.getTransaction().begin();
        em.remove(a);
        em.getTransaction().commit();
        return a;
    }

}
