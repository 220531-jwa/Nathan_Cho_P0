package dev.cho.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dev.cho.model.account;
import dev.cho.model.client;
import dev.cho.utils.ConnectionUtil;

public class AccountDAO {
	private static ConnectionUtil cu = ConnectionUtil.getConnectionUtil();
	
	public account createAccount(account a, int id) {
		
		String sql = "insert into accounts values (default, ?, ?, ?) returning *";
		
		try(Connection conn = cu.getConnection()){
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setDouble(1, a.getBalance());
			ps.setString(2, a.getType());
			ps.setInt(3, id);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				return new account(
						rs.getInt("account_number"), 
						rs.getDouble("balance"), 
						rs.getString("type"));
			}	
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;	
	}
	
	public account getAccountByAccountNumber(int id, int accNum) {
		
		String sql = "select * from bankapp.accounts where client_id = ? and account_number = ?";
		
		try(Connection conn = cu.getConnection()){
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.setInt(2, accNum);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				return new account(
						rs.getInt("account_number"), 
						rs.getDouble("balance"), 
						rs.getString("type"));
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<account> getAccountsByID(int id) {
		
		String sql = "select * from bankapp.accounts where client_id = ?"; //need to join here!!
		List<account> accounts = new ArrayList<account>();
		
		try(Connection conn = cu.getConnection()){
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				int accNum = rs.getInt("account_number");
				double balance = rs.getDouble("balance");
				String type = rs.getString("type");
				
				account a = new account(accNum, balance, type);
				accounts.add(a);
			}
			if(accounts.size() == 0) {
				accounts = null;
			}
			return accounts;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void updateAccount(int id, account aUpdate) {
		String sql = "update accounts set balance = ?, type = ? where client_id = ?";
		
		try(Connection conn = cu.getConnection()){
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setDouble(1, aUpdate.getBalance());
			ps.setString(2, aUpdate.getType());
			ps.setInt(3, id);
			
			ps.executeUpdate();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public void deleteAccountByAccountNumber(int id, int accNum) {
		String sql = "delete from bankapp.accounts where client_id = ? and account_number = ?";
		
		try(Connection conn = cu.getConnection()){
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.setInt(2, accNum);
			
			ps.execute();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
