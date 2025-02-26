package DAO;

import java.util.List;

import bean.History;
import bean.User;
import bean.Video;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import util.JpaUtils;

public class HistoryDAOImpl implements HistoryDAO {

    @Override
    public void findAll() {
        EntityManager em = JpaUtils.getEntityManager();
        String jsql = "select h from History h";
        TypedQuery<History> query = em.createQuery(jsql, History.class);
        List<History> list = query.getResultList();
        list.forEach(h -> System.out.println(h));
        em.close();
    }
    @Override
    public History findById(Integer id) {
        EntityManager em = JpaUtils.getEntityManager();
        History h = em.find(History.class, id);
        em.close();
        return h;
    }
    @Override
    public void insert(History h) {
        EntityManager em = JpaUtils.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(h);
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
    public void update(History h) {
        EntityManager em = JpaUtils.getEntityManager();
        try {
            em.getTransaction().begin(); 
            em.merge(h);
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
    public void delete(Integer id) {
        EntityManager em = JpaUtils.getEntityManager();
        try {
            em.getTransaction().begin();
            History h = em.find(History.class, id);
            if (h != null) {
                em.remove(h);
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
    public void deleteBy2(User u, Video v) {
        EntityManager em = JpaUtils.getEntityManager();
        String jsql = "delete from History h where h.user = :u and h.video = :v"; 
        Query query = em.createQuery(jsql);
        query.setParameter("u", u);
        query.setParameter("v", v);
        em.getTransaction().begin();
        query.executeUpdate();
        em.getTransaction().commit();
        em.close();
    }
	@Override
	public List<User> ListU() {
		EntityManager em = JpaUtils.getEntityManager();
		 String jsql = "select f.user from History h where year(h.dateVisti) = 2024";
	    TypedQuery<User> query = em.createQuery(jsql, User.class);
	    List<User> list = query.getResultList();
	    return list;
	}
	@Override
	public List<Video> ListV() {
		EntityManager em = JpaUtils.getEntityManager();
		 String jsql = "select h.video from History h where function('year', h.likeDate) = 2024";
	    TypedQuery<Video> query = em.createQuery(jsql, Video.class);
	    List<Video> list = query.getResultList();
	    return list;
	}

	@Override
	public List<History> findByUserId(String id) {
		EntityManager em = JpaUtils.getEntityManager();
	    TypedQuery<History> query = em.createNamedQuery("history.findByUserId", History.class);
	    query.setParameter("key",id);
	    List<History> list = query.getResultList();
	    return list;
	}
	@Override
	public List<History> findAllSort() {
		EntityManager em = JpaUtils.getEntityManager();
	    TypedQuery<History> query = em.createNamedQuery("history.findAll", History.class);
	    List<History> list = query.getResultList();
	    return list;
	}
	@Override
	public boolean findCheck(User u, Video v) {
	    EntityManager em = JpaUtils.getEntityManager();
	    try {
	        String jsql = "select h from History h where h.user = :u and h.video = :v";
	        TypedQuery<History> query = em.createQuery(jsql, History.class);
	        query.setParameter("u", u);
	        query.setParameter("v", v);
	        query.getSingleResult();
	        return true; 
	    } catch (NoResultException e) {
	        return false;
	    } finally {
	        em.close();
	    }
	}


}
