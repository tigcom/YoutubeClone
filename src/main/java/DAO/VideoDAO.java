package DAO;

import java.util.List;

import bean.Video;

public interface VideoDAO {

	public List<Video>  findAll();
	public List<Video>  ListSort();
	public List<Video>  ListSortNew();
	public List<Video>  ListSortOld();
	public List<Video>  ListLike();
	public Video findById(String id);
	public List<Video> findSearch(String key);
	void insert(Video video);

	void update(Video video);

	public boolean delete(String id);
	
	public List<Video>  findOut(String id);
	List<Video>  findTitleByKeyWord(String key);
	List<Video> findByPoster(String id);
	List<Video> FindVideoById(String id);
	
}
