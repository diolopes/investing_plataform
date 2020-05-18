package model.dao;

import db.DB;
import model.dao.impl.CompanyDaoJDBC;
import model.dao.impl.InvestorDaoJDBC;

public class DaoFactory {

	public static InvestorDao createInsvetorDao() {
		return new InvestorDaoJDBC(DB.getConnection());
	}
	
	public static CompanyDao createCompanyDao() {
		return new CompanyDaoJDBC(DB.getConnection());
	}
}
