package DAO;

import java.sql.Date;
import java.util.List;

import bean.Favorite;
import bean.User;
import bean.Video;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import util.JpaUtils;

public class FavoriteDAOImpl implements FavoriteDAO {

    @Override
    public void findAll() {
        EntityManager em = JpaUtils.getEntityManager();
        String jsql = "select f from Favorite f";
        TypedQuery<Favorite> query = em.createQuery(jsql, Favorite.class);
        List<Favorite> list = query.getResultList();
        list.forEach(favorite -> System.out.println(favorite));
        em.close();
    }
    @Override
    public Favorite findById(Integer id) {
        EntityManager em = JpaUtils.getEntityManager();
        Favorite favorite = em.find(Favorite.class, id);
        em.close();
        return favorite;
    }
    @Override
    public void insert(Favorite favorite) {
        EntityManager em = JpaUtils.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(favorite);
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
    public void update(Favorite favorite) {
        EntityManager em = JpaUtils.getEntityManager();
        try {
            em.getTransaction().begin(); 
            em.merge(favorite);
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
            Favorite favorite = em.find(Favorite.class, id);
            if (favorite != null) {
                em.remove(favorite);
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
        String jsql = "delete from Favorite f where f.user = :u and f.video = :v"; 
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
		 String jsql = "select f.user from Favorite f where year(f.likeDate) = 2024";
	    TypedQuery<User> query = em.createQuery(jsql, User.class);
	    List<User> list = query.getResultList();
	    return list;
	}
	@Override
	public List<Video> ListV() {
		EntityManager em = JpaUtils.getEntityManager();
		 String jsql = "select f.video from Favorite f where function('year', f.likeDate) = 2024";
	    TypedQuery<Video> query = em.createQuery(jsql, Video.class);
	    List<Video> list = query.getResultList();
	    return list;
	}
	@Override
	public List<Favorite> findAllSort() {
		EntityManager em = JpaUtils.getEntityManager();
	    TypedQuery<Favorite> query = em.createNamedQuery("favorites.findAll", Favorite.class);
	    List<Favorite> list = query.getResultList();
	    return list;
	}
	@Override
	public List<Favorite> findByUserId(String id) {
		EntityManager em = JpaUtils.getEntityManager();
	    TypedQuery<Favorite> query = em.createNamedQuery("favorites.findByUserId", Favorite.class);
	    query.setParameter("key",id);
	    List<Favorite> list = query.getResultList();
	    return list;
	}

//    public static void main(String[] args) {
//        FavoriteDAO favoriteDAO = new FavoriteDAOImpl();
//        UserDAO userDAO = new UserDAOImpl();
//        VideoDAO videoDAO = new VideoDAOImpl();
//        		
//        
//        try {
//        	Favorite f = new Favorite();
//        	f.setUser(userDAO.findById("u01"));
//        	f.setVideo(videoDAO.findById("vid123"));
//        	f.setLikeDate(new Date());
//            favoriteDAO.insert(f);
//            favoriteDAO.findAll();
//           
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
