package model.dao;

import java.util.List;

import model.entities.Investor;

public interface InvestorDao {

	void insert(Investor obj);
	void update(Investor obj);
	void deleteById(Integer id);
	Investor findById(Integer id);
	List<Investor> findAll();
}
