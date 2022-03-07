package com.epf.rentmanager.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.persistence.ConnectionManager;
@Repository
public class ClientDao {
	
	private static ClientDao instance = null;
	private ClientDao() {}

	
	private static final String CREATE_CLIENT_QUERY = "INSERT INTO Client(nom, prenom, email, naissance) VALUES(?, ?, ?, ?);";
	private static final String DELETE_CLIENT_QUERY = "DELETE FROM Client WHERE id=?;";
	private static final String FIND_CLIENT_QUERY = "SELECT nom, prenom, email, naissance FROM Client WHERE id=?;";
	private static final String FIND_CLIENTS_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client;";
	private static final String UPDATE_CLIENT_QUERY = "UPDATE Client SET nom = ?, prenom = ?, email = ?, naissance = ? WHERE id= ?;";
	private static final String FIND_EMAIL_QUERY = "SELECT id FROM Client WHERE email=?;";
	
	/**
	 * Créer un client.
	 * 
	 * @param Client / Le client à  créer.
	 * @return long / L'id du client créé
	 * @throws DaoException
	 */
	public long create(Client client) throws DaoException {
		int id = 0;
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(CREATE_CLIENT_QUERY, id = Statement.RETURN_GENERATED_KEYS);
			
			pstmt.setString(1, client.getLastname());
			pstmt.setString(2, client.getName());
			pstmt.setString(3, client.getEmail());
			pstmt.setDate(4, Date.valueOf(client.getBirthDate()));
			pstmt.execute();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		return id;
		
	}

	/**
	 * Delete un client via son id
	 * 
	 * @param int / id 
	 * @throws DaoException
	 */	
	public void delete(int id) throws DaoException {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(DELETE_CLIENT_QUERY);
			pstmt.setInt(1, id);
			pstmt.execute();
			pstmt.close();
			conn.close();

		} catch (SQLException e) {
			
			e.printStackTrace();
		}		
	}
	
	 /**
	 * Trouve un client via son id
	 * 
	 * @param int / L'id du client créer
	 * @return  client / Le client à  trouver
	 * @throws DaoException
	 */
	public Optional<Client> findById(int id) throws DaoException {

		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(FIND_CLIENT_QUERY);
			pstmt.setInt(1, id);
			ResultSet rs=pstmt.executeQuery();
			
			rs.next();
			//int clientId = rs.getInt("id");
			String clientName = rs.getString("nom");
			String clientFirstname = rs.getString("prenom");
			String clientEmail = rs.getString("email");
			LocalDate  clientBirthdate = rs.getDate("naissance").toLocalDate();
			
			Client client = new Client(
				id, clientFirstname,clientName,clientEmail, clientBirthdate);
			
			return Optional.of(client);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return Optional.empty();
		
	}
	
	 /**
	 * Retourne une liste contenant tous les clients
	 * 
	 * @return  client / Les clients à  trouver
	 * @throws DaoException
	 */
	public List<Client> findAll() throws DaoException {
		List<Client> listClients = new ArrayList<Client>();
		try {
			
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(FIND_CLIENTS_QUERY);
			ResultSet rs=pstmt.executeQuery();
			
			while (rs.next()) {
				int clientId = rs.getInt("id");
				String clientName = rs.getString("nom");
				String clientFirstname = rs.getString("prenom");
				String clientEmail = rs.getString("email");
				LocalDate  clientBirthdate = rs.getDate("naissance").toLocalDate();
				
				Client client = new Client(clientId, clientFirstname, clientName, clientEmail, clientBirthdate);			
				
				listClients.add(client);				
						
			}
			rs.close();
			pstmt.close();
			conn.close();
			return listClients ;
		} catch (SQLException e) {			
			e.printStackTrace();
			
		}
		return listClients;
		
	}

	/**
	 * Update le client
	 * 
	 * @p client /  Le client à modifier
	 * @throws DaoException
	 */
	public void update(Client client) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement pstmt = connection.prepareStatement(UPDATE_CLIENT_QUERY);
			pstmt.setString(1, client.getLastname());
			pstmt.setString(2, client.getName());
			pstmt.setString(3, client.getEmail());
			pstmt.setDate(4, Date.valueOf(client.getBirthDate()));
			pstmt.setInt(5, client.getId());

			pstmt.execute();
			pstmt.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Compte le nombre de clients
	 * 
	 * @return int /  nombre de clients
	 * @throws DaoException
	 */
	public int count() throws DaoException {
		int count = 0;
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(FIND_CLIENTS_QUERY);
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				count++;
			}
						
			
			resultSet.close();
			ps.close();
			connection.close();
			return count;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public boolean hasNoSameEmail(Client client) throws DaoException {
		boolean hasNoSameEmail = true;
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(FIND_EMAIL_QUERY);
			ps.setString(1, client.getEmail());
			ps.execute();

			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
	
					hasNoSameEmail = false;
			}
			resultSet.close();
			ps.close();
			connection.close();
		} catch (SQLException e) {
			throw new DaoException("L'email choisit est déjà pris ");
		}
		return hasNoSameEmail;
	}

}

	
	

