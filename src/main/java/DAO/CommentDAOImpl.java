package DAO;

import java.util.List;

import bean.Comment;
import bean.Favorite;
import bean.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import util.JpaUtils;

public class CommentDAOImpl implements CommentDAO {

	@Override
	public void insert(Comment comment) {
		 EntityManager em = JpaUtils.getEntityManager();
		 try {
			 	em.getTransaction().begin();
	            em.persist(comment);
	            em.getTransaction().commit();
	        }  catch (Exception e) {
	            e.printStackTrace();
	            if (em.getTransaction().isActive()) {
	                em.getTransaction().rollback();
	            }
	        } finally {
	            em.close();
	        }
	}

	@Override
	public void update(Comment comment) {
		 EntityManager em = JpaUtils.getEntityManager();
		 try {
			 	em.getTransaction().begin();
	            em.merge(comment);
	            em.getTransaction().commit();
	        }  catch (Exception e) {
	            e.printStackTrace();
	            if (em.getTransaction().isActive()) {
	                em.getTransaction().rollback();
	            }
	        } finally {
	            em.close();
	        }
	}

	@Override
	public void delete(int id) {
	  EntityManager em = JpaUtils.getEntityManager();
        try {
            em.getTransaction().begin();
            Comment comment = em.find(Comment.class, id);
            if (comment != null) {
            	em.remove(comment);
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
	public Comment findById(int id) {
		EntityManager em = JpaUtils.getEntityManager();
		Comment cmt = em.find(Comment.class, id);
		em.close();
		return cmt;
	}

	@Override
	public List<Comment> findAll() {
		 EntityManager em = JpaUtils.getEntityManager();
        String jsql = "select cmt from Comment cmt";
        TypedQuery<Comment> query = em.createQuery(jsql, Comment.class);
        List<Comment> list = query.getResultList();
        em.close();
		return list;
	}

	@Override
	 public List<Comment> findByVideoId(String videoId) {
		EntityManager em = JpaUtils.getEntityManager();
        TypedQuery<Comment> query = em.createQuery("SELECT c FROM Comment c WHERE c.video.id = :videoId", Comment.class);
        query.setParameter("videoId", videoId);
        List<Comment> list =query.getResultList();
        em.close();
        return list;
    }

	@Override
	public List<Comment> findUserId(String userId) {
		EntityManager em = JpaUtils.getEntityManager();
		 TypedQuery<Comment> query = em.createQuery("SELECT c FROM Comment c WHERE c.user.id = :userId", Comment.class);
	        query.setParameter("userId", userId);
	        List<Comment> list =query.getResultList();
	        em.close();
	        return list;
	}

}
