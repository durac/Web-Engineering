package at.ac.tuwien.big.we16.ue3.service;

import at.ac.tuwien.big.we16.ue3.exception.UserNotFoundException;
import at.ac.tuwien.big.we16.ue3.model.User;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class UserService {

    private EntityManager em;

    public UserService() {
        this.em = ServiceFactory.getEntityManagerFactory().createEntityManager();
    }


    public synchronized void createUser(User user) {
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
    }

    public synchronized void updateUser(User user) {
        em.getTransaction().begin();
        em.merge(user);
        em.getTransaction().commit();
    }

    public User getUserByEmail(String email) throws UserNotFoundException {
        Query q = em.createQuery("select u from User u where u.email=:email");
        q.setParameter("email",email);
        List<User> list = q.getResultList();
        if(list.isEmpty()){
            throw new UserNotFoundException();
        }
        return list.get(0);
    }

    public void close(){
        em.close();
    }

}
