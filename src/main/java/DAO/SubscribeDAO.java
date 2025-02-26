package DAO;

import bean.Subscribe;
import bean.User;
import bean.Video;

public interface SubscribeDAO {

	void delete(Integer id);
	void deleteBy2(User uAuth, User vDk);

	void update(Subscribe h);

	void insert(Subscribe h);
	void findAll();

}
