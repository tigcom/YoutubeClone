package DAO;

import java.util.List;

import bean.History;
import bean.Subscribe;
import bean.User;
import bean.Video;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import util.JpaUtils;

public class SubscribeDAOImpl implements SubscribeDAO {

    @Override
    public void findAll() {
        EntityManager em = JpaUtils.getEntityManager();
        String jsql = "select s from Subscribe s";
        TypedQuery<Subscribe> query = em.createQuery(jsql, Subscribe.class);
        List<Subscribe> list = query.getResultList();
        list.forEach(h -> System.out.println(h));
        em.close();
    }
    @Override
    public void insert(Subscribe s) {
        EntityManager em = JpaUtils.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(s);
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
    public void update(Subscribe s) {
        EntityManager em = JpaUtils.getEntityManager();
        try {
            em.getTransaction().begin(); 
            em.merge(s);
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
            Subscribe s = em.find(Subscribe.class, id);
            if (s != null) {
                em.remove(s);
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
    public void deleteBy2(User uAuth, User uDk) {
        EntityManager em = JpaUtils.getEntityManager();
        String jsql = "delete from Subscribe s where s.userAuth = :uAuth and s.userDk = :uDk"; 
        Query query = em.createQuery(jsql);
        query.setParameter("uAuth", uAuth);
        query.setParameter("uDk", uDk);
        em.getTransaction().begin();
        query.executeUpdate();
        em.getTransaction().commit();
        em.close();
    }
}
