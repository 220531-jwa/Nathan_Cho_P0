package dev.cho.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dev.cho.model.client;
import dev.cho.utils.ConnectionUtil;

public class UserDAO {
		//this is the public method that will use our private method in the ConnectionUtil class.
		private static ConnectionUtil cu = ConnectionUtil.getConnectionUtil();
				
		
		public client createClient(client c) {
			
			String sql = "insert into clients values (default, ?, ?) returning *";
			
			try(Connection conn = cu.getConnection()){
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, c.getUsername());
				ps.setString(2, c.getPassword());
				
				ResultSet rs = ps.executeQuery();
				
				if(rs.next()) {
					return new client(rs.getInt("id"), rs.getString("username"), rs.getString("password"));
				}	
				
			} catch (SQLException e) {
				e.printStackTrace();
				
			}
			return null;
			
		}
		public List<client> getAllClients(){
			List<client> clients= new ArrayList<client>();
			String sql = "select * from clients"; //This sends the command to sql that is in quotes
			
			//try with resources - will auto close any resources we need without a finally block
			try (Connection conn = cu.getConnection()) {
				PreparedStatement ps = conn.prepareStatement(sql); //preparing a statement into SQL
				
				ResultSet rs = ps.executeQuery(); //the result set will be the table given.
				
				/*
				 * The result set will break down each record into some sort of cursor.
				 */
				
				while(rs.next()){ //next() will move the cursor to the first row of information on the table in the database
					//This while loop exits when there is no more rows.
					int id = rs.getInt("id");
					String username = rs.getString("username");
					String password = rs.getString("password");
					/*
					 * You'll need to set up the Database such that each client has an id, username and password stored in columns.
					 */
					client c = new client(id, username, password);
					
					clients.add(c);
				}
				return clients;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			//If we didn't use a try with resources, we'd have to write a finally block to close the connection and have another try-catch nested inside of it.
			
			return null;
		}
		
		public client getClientByID(int id) {
			
			String sql = "select * from clients where id = ?";
			
			try(Connection conn = cu.getConnection()){
				PreparedStatement ps = conn.prepareStatement(sql);
				
				ps.setInt(1, id);
				
				ResultSet rs = ps.executeQuery();
				
				if(rs.next()) {
					return new client(rs.getInt("id"), rs.getString("username"), rs.getString("password"));
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		public client getClientByUsername(String username) {
			
			String sql = "select * from clients where username = ?";
			try(Connection conn = cu.getConnection()){
				PreparedStatement ps = conn.prepareStatement(sql);
				
				ps.setString(1, username);
				
				ResultSet rs = ps.executeQuery();
				
				if(rs.next()) {
					return new client(rs.getInt("id"), rs.getString("username"), rs.getString("password"));
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}

		public void updateClient(int id, client cUpdate) {
			
			String sql = "update clients set username = ?, password = ? where id = ?";
			
			try(Connection conn = cu.getConnection()){
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, cUpdate.getUsername());
				ps.setString(2, cUpdate.getPassword());
				ps.setInt(3, id);
				
				ps.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		
		public void deleteClient(int id) {
			String sql = "delete from bankapp.clients where id = ?";
			
			try(Connection conn = cu.getConnection()){
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setInt(1, id);
				ps.execute();
				
				
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
}
