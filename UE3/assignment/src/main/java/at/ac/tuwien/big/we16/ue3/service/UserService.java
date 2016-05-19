package at.ac.tuwien.big.we16.ue3.service;

import at.ac.tuwien.big.we16.ue3.exception.UserNotFoundException;
import at.ac.tuwien.big.we16.ue3.model.User;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class UserService {

    private EntityManager em;

    public UserService() {
        this.em = ServiceFactory.getEntityManager();
    }

    public void createUser(User user) {
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
    }

    public void updateUser(User user) {
        em.getTransaction().begin();
        em.refresh(user);
        em.getTransaction().commit();
    }

    public User getUserByEmail(String email) throws UserNotFoundException {
        em.getTransaction().begin();
        Query q = em.createQuery("select u from User u where u.email=:email");
        q.setParameter("email",email);
        List<User> list = q.getResultList();
        em.getTransaction().commit();
        if(list.isEmpty()){
            throw new UserNotFoundException();
        }
        return list.get(0);
    }

}
