package DAO;

import java.util.List;

import bean.History;
import bean.User;
import bean.Video;

public interface HistoryDAO {

	void delete(Integer id);
	void deleteBy2(User u, Video v);

	void update(History h);

	void insert(History h);
	List <User> ListU();
	List <Video> ListV();
	History findById(Integer id);
	boolean findCheck(User u ,Video v);
	void findAll();
	List<History> findAllSort();
	List<History> findByUserId(String id);

}
