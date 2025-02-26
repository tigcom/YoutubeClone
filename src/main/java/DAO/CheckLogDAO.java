package DAO;

import java.util.Date;
import java.util.List;

import bean.Checklog;

public interface CheckLogDAO  {
	List <Checklog> findAll();
	List <Checklog> findByDate(String start, String end);
	void insert(Checklog cl);
}
