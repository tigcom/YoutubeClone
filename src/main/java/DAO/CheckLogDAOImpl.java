package DAO;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import bean.Checklog;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import util.JpaUtils;

public class CheckLogDAOImpl implements CheckLogDAO{

	@Override
	public List<Checklog> findAll() {
		EntityManager em = JpaUtils.getEntityManager();
		String jsql = "select cl from Checklog cl";
		TypedQuery<Checklog> query = em.createQuery(jsql, Checklog.class);
		List<Checklog> list = (List<Checklog>) query.getResultList();
		return list;
	}

	@Override
	public void insert(Checklog cl) {
		EntityManager em = JpaUtils.getEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(cl);
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
	public List<Checklog> findByDate(String start, String end) {
		EntityManager em = JpaUtils.getEntityManager();
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		 Date dateStart = null;
		 Date dateEnd = null;
	        try {
	            dateStart = sdf.parse(start);
	            dateEnd = sdf.parse(end);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		String jsql = "select cl from Checklog cl where cl.accecsTime between  :end and :start";
		TypedQuery<Checklog> query = em.createQuery(jsql, Checklog.class);
		query.setParameter("start",dateStart);
		query.setParameter("end",dateEnd);
		List<Checklog> list = (List<Checklog>) query.getResultList();
		return list;
	}

}
