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
import model.dao.InvestorDao;
import model.entities.Investor;

public class InvestorDaoJDBC implements InvestorDao {

	private Connection conn;

	public InvestorDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Investor obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("INSERT INTO investor "
					+ "(Id, FirstName, LastName, Email, Password, BirthDate, Country, Category) " + "VALUES "
					+ "(?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

			st.setInt(1, obj.getId());
			st.setString(2, obj.getFirstName());
			st.setString(3, obj.getLastName());
			st.setString(4, obj.getEmail());
			st.setString(5, obj.getPassword());
			st.setString(6, obj.getBirthDate());
			st.setString(7, obj.getCountry());
			st.setString(8, obj.getCategory());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(rs);
			} else {
				throw new DbException("Unexpected error! No rows affected!");
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(Investor obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE investor "
					+ "SET FirstName = ?, LastName = ?, Email = ?, Password = ?, BirthDate = ?, Country = ?, Category = ? "
					+ "WHERE Id = ?");

			st.setString(1, obj.getFirstName());
			st.setString(2, obj.getLastName());
			st.setString(3, obj.getEmail());
			st.setString(4, obj.getPassword());
			st.setString(5, obj.getBirthDate());
			st.setString(6, obj.getCountry());
			st.setString(7, obj.getCategory());
			st.setInt(8, obj.getId());

			st.executeUpdate();
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM investor WHERE Id = ?");

			st.setInt(1, id);

			st.executeUpdate();
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public Investor findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM investor WHERE Id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Investor obj = new Investor();
				obj.setId(rs.getInt("Id"));
				obj.setFirstName(rs.getString("FirstName"));
				obj.setFirstName(rs.getString("LastName"));
				obj.setEmail(rs.getString("Email"));
				obj.setPassword(rs.getString("Password"));
				obj.setBirthDate(rs.getString("BirthDate"));
				obj.setCountry(rs.getString("Country"));
				obj.setCategory(rs.getString("Category"));

				return obj;
			}
			return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Investor> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM investor ORDER BY Id");
			rs = st.executeQuery();

			List<Investor> list = new ArrayList<>();

			while (rs.next()) {

				Investor obj = new Investor();
				obj.setId(rs.getInt("Id"));
				obj.setFirstName(rs.getString("FirstName"));
				obj.setFirstName(rs.getString("LastName"));
				obj.setEmail(rs.getString("Email"));
				obj.setPassword(rs.getString("Password"));
				obj.setBirthDate(rs.getString("BirthDate"));
				obj.setCountry(rs.getString("Country"));
				obj.setCategory(rs.getString("Category"));
				list.add(obj);
			}
			return list;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
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
		String query = "select * from investor where Email = ? and Password = ?";
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

