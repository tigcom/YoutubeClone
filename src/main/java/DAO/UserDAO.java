package DAO;

import java.util.List;

import bean.User;

public interface UserDAO {
	List <User> findAll();

	void delete(String id);

	void update(User user);

	void insert(User user);
	List <User> findMail (String mail);
	User findById(String id);
	User findByLog(String info);
	List <User> seatchUsers(String key);
}
