package DAO;

import java.sql.Date;
import java.util.List;

import bean.Favorite;
import bean.User;
import bean.Video;

public interface FavoriteDAO {

	void delete(Integer id);
	void deleteBy2(User u, Video v);

	void update(Favorite favorite);

	void insert(Favorite favorite);
	List <User> ListU();
	List <Video> ListV();
	Favorite findById(Integer id);

	void findAll();
	List<Favorite> findAllSort();
	List<Favorite> findByUserId(String id);

}
