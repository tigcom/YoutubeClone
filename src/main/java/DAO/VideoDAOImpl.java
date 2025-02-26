package DAO;

import java.util.List;

import bean.Video;
import jakarta.persistence.EntityManager;
import jakarta.persistence.StoredProcedureQuery;
import jakarta.persistence.TypedQuery;
import util.JpaUtils;

public class VideoDAOImpl implements VideoDAO {

    @Override
    public List<Video> findAll() {
        EntityManager em = JpaUtils.getEntityManager();
        String jsql = "select v from Video v where active = true";
        TypedQuery<Video> query = em.createQuery(jsql, Video.class);
        List<Video> list = query.getResultList();
        em.close();
		return list;
    }
    @Override
    public Video findById(String id) {
        EntityManager em = JpaUtils.getEntityManager();
        Video video = em.find(Video.class, id);
        em.close();
        return video;
    }
    @Override
    public void insert(Video video) {
        EntityManager em = JpaUtils.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(video);
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
    public void update(Video video) {
        EntityManager em = JpaUtils.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(video);
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
    public boolean delete(String id) {
    	boolean checkDel = true;
        EntityManager em = JpaUtils.getEntityManager();
        try {
            em.getTransaction().begin();
            Video video = em.find(Video.class, id);
            if (video != null) {
                em.remove(video);
                checkDel =false;
            }
            em.getTransaction().commit();
        } catch (Exception e) {
        	checkDel =true;
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            em.close();
        }
		return checkDel;
    }
	@Override
	public List<Video> findOut(String id) {
		 EntityManager em = JpaUtils.getEntityManager(); 
	        String jsql = "select v from Video v where v.id  != :id";
	        TypedQuery<Video> query = em.createQuery(jsql, Video.class);
	        query.setParameter("id", id );
	        List<Video> list = query.getResultList();
	        em.close();
			return list;
	}
	@Override
	public List<Video> ListSort() {
		 EntityManager em = JpaUtils.getEntityManager();
		 	String jsql = "select v from Video v order  by size(v.favorites) desc";
	        TypedQuery<Video> query = em.createQuery(jsql, Video.class);
	        query.setMaxResults(10);
	        List<Video> list = query.getResultList();
	        em.close();
			return list;
	}
	@Override
	public List<Video> findSearch(String key) {
		EntityManager em = JpaUtils.getEntityManager();
	 	String jsql = "select v from Video v where v.title like :key";
        TypedQuery<Video> query = em.createQuery(jsql, Video.class);
        query.setParameter("key","%" + key + "%" );
        List<Video> list = query.getResultList();
        em.close();
		return list;
	}
	@Override
	public List<Video> ListLike() {
		EntityManager em = JpaUtils.getEntityManager();
	 	String jsql = "select v from Video v where size(v.favorites) >=3";
        TypedQuery<Video> query = em.createQuery(jsql, Video.class);
        List<Video> list = query.getResultList();
        em.close();
		return list;
	}
	@Override
	public List<Video> ListSortNew() {
		EntityManager em = JpaUtils.getEntityManager();
	 	String jsql = "select v from Video v order by v.dateUpload desc";
        TypedQuery<Video> query = em.createQuery(jsql, Video.class);
        List<Video> list = query.getResultList();
        em.close();
		return list;
	}
	@Override
	public List<Video> ListSortOld() {
		EntityManager em = JpaUtils.getEntityManager();
	 	String jsql = "select v from Video v order by v.dateUpload asc";
        TypedQuery<Video> query = em.createQuery(jsql, Video.class);
        List<Video> list = query.getResultList();
        em.close();
		return list;
	}
	@Override
	public List<Video>  findTitleByKeyWord(String key) {
		EntityManager em = JpaUtils.getEntityManager();
        TypedQuery<Video> query = em.createNamedQuery("video.findTitleByKeyWord",Video.class);
        query.setParameter("key", "%" + key + "%"); 
        List<Video> list = query.getResultList();
        em.close();
		return list;
	}
	@Override
	public List<Video> findByPoster(String id) {
		EntityManager em = JpaUtils.getEntityManager();
        TypedQuery<Video> query = em.createNamedQuery("video.findByPoster",Video.class);
        query.setParameter("key",id);
        List<Video> vd = query.getResultList();
        em.close();
		return vd;
	}
	@Override
	public List<Video> FindVideoById(String id) {
		EntityManager em = JpaUtils.getEntityManager();
		StoredProcedureQuery query = em.createNamedStoredProcedureQuery("video.FindVideoById");
        query.setParameter("id",id);
        List<Video> vd = query.getResultList();
        em.close();
		return vd;
	}

}
