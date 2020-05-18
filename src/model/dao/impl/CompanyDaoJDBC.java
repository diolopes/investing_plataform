package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import db.DbIntegrityException;
import model.dao.CompanyDao;
import model.entities.Company;

public class CompanyDaoJDBC implements CompanyDao {

	private Connection conn;
	
	public CompanyDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public Company findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				"SELECT * FROM company WHERE Id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Company obj = new Company();
				obj.setId(rs.getInt("Id"));
				obj.setCompanyName(rs.getString("CompanyName"));
				obj.setCeoName(rs.getString("CeoName"));
				obj.setPhoneNumber(rs.getString("PhoneNumber"));
				obj.setEmail(rs.getString("Email"));
				obj.setPassword(rs.getString("Password"));
				obj.setCountry(rs.getString("Country"));
				obj.setFoundationDate(rs.getString("FoundationDate"));
				obj.setWebsite(rs.getString("Website"));
				obj.setIndustry(rs.getString("Industry"));
				obj.setGrossSales(rs.getString("GrossSales"));
				
				return obj;
			}
			return null;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Company> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				"SELECT * FROM company ORDER BY CompanyName");
			rs = st.executeQuery();

			List<Company> list = new ArrayList<>();

			while (rs.next()) {
				Company obj = new Company();
				obj.setId(rs.getInt("Id"));
				obj.setCompanyName(rs.getString("CompanyName"));
				obj.setCeoName(rs.getString("CeoName"));
				obj.setPhoneNumber(rs.getString("PhoneNumber"));
				obj.setEmail(rs.getString("Email"));
				obj.setPassword(rs.getString("Password"));
				obj.setCountry(rs.getString("Country"));
				obj.setFoundationDate(rs.getString("FoundationDate"));
				obj.setWebsite(rs.getString("Website"));
				obj.setIndustry(rs.getString("Industry"));
				obj.setGrossSales(rs.getString("GrossSales"));
				list.add(obj);
			}
			return list;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public void insert(Company obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"INSERT INTO company " +
				"(Id, CompanyName, CeoName, PhoneNumber, Email, Password, Country, FoundationDate, Website, Industry, GrossSales) " +
				"VALUES " +
				"(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", 
				Statement.RETURN_GENERATED_KEYS);

			st.setInt(1, obj.getId());
			st.setString(2, obj.getCompanyName());
			st.setString(3, obj.getCeoName());
			st.setString(4, obj.getPhoneNumber());
			st.setString(5, obj.getEmail());
			st.setString(6, obj.getPassword());			
			st.setString(7, obj.getCountry());
			st.setString(8, obj.getFoundationDate());
			st.setString(9, obj.getWebsite());
			st.setString(10, obj.getIndustry());
			st.setString(11, obj.getGrossSales());

			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
			}
			else {
				throw new DbException("Unexpected error! No rows affected!");
			}
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		} 
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(Company obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"UPDATE company " 
				+ "SET CompanyName = ?, CeoName = ?,PhoneNumber = ?, Email = ?, Password = ?, Country = ?, FoundationDate = ?, Website = ?, Industry = ?, GrossSales = ? "
				+ "WHERE Id = ?");
			
			st.setString(1, obj.getCompanyName());
			st.setString(2, obj.getCeoName());
			st.setString(3, obj.getPhoneNumber());
			st.setString(4, obj.getEmail());
			st.setString(5, obj.getPassword());
			st.setString(6, obj.getCountry());
			st.setString(7, obj.getFoundationDate());
			st.setString(8, obj.getWebsite());
			st.setString(9, obj.getIndustry());
			st.setString(10, obj.getGrossSales());

			st.setInt(11, obj.getId());

			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		} 
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"DELETE FROM company WHERE Id = ?");

			st.setInt(1, id);

			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbIntegrityException(e.getMessage());
		} 
		finally {
			DB.closeStatement(st);
		}
	}
	
	public boolean isDbConnected() {
		try {
			return !conn.isClosed();
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean isLogin(String email, String pass) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "select * from company where Email = ? and Password = ?";
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, email);
			ps.setString(2, pass);
			
			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}
			else {
				return false;
			}
		}catch (Exception e) {
			return false;
		} finally {
			ps.close();
			rs.close();
		}
	}
}
