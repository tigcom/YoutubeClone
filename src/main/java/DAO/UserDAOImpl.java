package DAO;

import java.util.ArrayList;
import java.util.List;

import bean.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import util.JpaUtils;

public class UserDAOImpl implements UserDAO {

	

	@Override
	public List <User> findAll() {
		EntityManager em = JpaUtils.getEntityManager();
		String jsql = "select u from User u";
		TypedQuery<User> query = em.createQuery(jsql, User.class);
		List<User> list = (List<User>) query.getResultList();
		return list;
	}

	@Override
	public User findById(String id) {
		EntityManager em = JpaUtils.getEntityManager();
		User user = em.find(User.class, id);
		em.close();
		return user;
	}

	@Override
	public void insert(User user) {
		EntityManager em = JpaUtils.getEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(user);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
		} finally {
			em.close();
		}
	}

	@Override
	public void update(User user) {
		EntityManager em = JpaUtils.getEntityManager();
		try {
			em.getTransaction().begin();
			em.merge(user);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
		} finally {
			em.close();
		}
	}

	@Override
	public void delete(String id) {
		EntityManager em = JpaUtils.getEntityManager();
		try {
			em.getTransaction().begin();
			User user = em.find(User.class, id);
			if (user != null) {
				em.remove(user);
			}
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
		} finally {
			em.close(); 
		}
	}

	@Override
	public User findByLog(String info) {
		try {
			EntityManager em = JpaUtils.getEntityManager();
			String jsql = "select u from User u where u.id = :info or u.email = :info";
			TypedQuery<User> query = em.createQuery(jsql, User.class);
			query.setParameter("info", info);
			User us =  query.getSingleResult();
			return us;
		} catch (Exception e) {
			return null;
		}
		
	}

	@Override
	public List<User> findMail(String mail) {
	    EntityManager em = JpaUtils.getEntityManager();
	    String jsql = "select u from User u where u.email like :mail";
	    TypedQuery<User> query = em.createQuery(jsql, User.class);
	    query.setParameter("mail", "%" + mail);
	    List<User> list = query.getResultList();
	    return list;
	}

	@Override
	public List<User> seatchUsers(String key) {
		EntityManager em = JpaUtils.getEntityManager();
		String jsql = "select u from User u where u.id like :key or u.email like :key or u.fullname like :key or u.phone like :key";
		TypedQuery<User> query = em.createQuery(jsql, User.class);
		query.setParameter("key", "%"+key+"%");
		List<User> list =  query.getResultList();
		return list;
	}


}
