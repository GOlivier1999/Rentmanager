package com.epf.rentmanager.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.persistence.ConnectionManager;

@Repository
public class ReservationDao {

	private static ReservationDao instance = null;
	private ReservationDao() {}

	
	private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(?, ?, ?, ?);";
	private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY = "SELECT id,client_id, vehicle_id, debut, fin FROM Reservation WHERE client_id=?;";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT id, client_id,vehicle_id, debut, fin FROM Reservation WHERE vehicle_id=?;";
	private static final String FIND_RESERVATIONS_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation;";
	private static final String UPDATE_RESERVATION_QUERY = "UPDATE Reservation SET client_id = ?, vehicle_id = ?, debut = ?, fin = ? WHERE id = ?;";
	private static final String FIND_RESERVATION_QUERY = "SELECT client_id, vehicle_id, debut, fin FROM Reservation WHERE id=?;";

	

	/**
	 * Créer une Reservation
	 * 
	 * @param  Reservation / La reservation à  créer
	 * @return int / L'id de la réservation créée
	 * @throws DaoException
	 */
	public int create(Reservation reservation) throws DaoException {
		int id = 1;
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(CREATE_RESERVATION_QUERY,
			id = Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, reservation.getIdClient());
			pstmt.setInt(2, reservation.getIdVehicule());
			pstmt.setDate(3, Date.valueOf(reservation.getDateStart()));
			pstmt.setDate(4, Date.valueOf(reservation.getDateEnd()));
			pstmt.execute();
			pstmt.close();
			conn.close();
			return id;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
		
	}
	
	/**
	 * Delete une Reservation via son idlient via son id
	 * 
	 * @param int / id 
	 * @throws DaoException
	 */
	public void delete(int id) throws DaoException {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(DELETE_RESERVATION_QUERY);
			pstmt.setInt(1, id);
			pstmt.execute();
			pstmt.close();
			conn.close();

		} catch (SQLException e) {

			throw new DaoException("Une erreur a eu lieu lors de la suppression de la reservation (DAO)");
		}
	}


	/**
	* Trouve une reservation via son id
	* 
	* @param int / L'id de la reservation
	* @return  Reservation / La reservation à  trouver
	* @throws DaoException
	*/
	public Optional<Reservation> findById(int id)  throws DaoException {
		Reservation reservation = new Reservation();
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement ps = conn.prepareStatement(FIND_RESERVATION_QUERY );
			ps.setInt(1, id);
			ps.execute();

			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				
				reservation.setId(id);
				reservation.setIdClient(resultSet.getInt("client_id"));
				reservation.setIdVehicule(resultSet.getInt("vehicle_id"));
				reservation.setDateStart(resultSet.getDate("debut").toLocalDate());
				reservation.setDateEnd(resultSet.getDate("fin").toLocalDate());	
				return Optional.ofNullable(reservation);
			}
			resultSet.close();
			ps.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.ofNullable(reservation);
		
	}
	/**
	* Retourne une liste de reservation via un client_id
	* 
	* @param int / L'id du client
	* @return  List<Reservation> / liste des réservations à  trouver
	* @throws DaoException
	*/
	public List<Reservation> findByClient_id(int client_id) throws DaoException {

		List<Reservation> result = new ArrayList<>();
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY);
			ps.setInt(1, client_id);
			ps.execute();

			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				Reservation reservation = new Reservation();
				reservation.setId(resultSet.getInt("id"));
				reservation.setIdClient(client_id);
				reservation.setIdVehicule(resultSet.getInt("vehicle_id"));
				reservation.setDateStart(resultSet.getDate("debut").toLocalDate());
				reservation.setDateEnd(resultSet.getDate("fin").toLocalDate());
				result.add(reservation);
			}
			resultSet.close();
			ps.close();
			connection.close();

		} catch (SQLException e) {
			throw new DaoException("erreur lors du findByClient_id");
		}

		return result;

	}	

	/**
	* Retourne une liste de reservation via un vehicleId
	* 
	* @param int / L'id du vehicle
	* @return  List<Reservation> / liste des réservations à  trouver
	* @throws DaoException
	*/
	public List<Reservation> findByVehicleId(int vehicleId) throws DaoException {
		List<Reservation> listReservations = new ArrayList<>();
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(FIND_RESERVATIONS_BY_VEHICLE_QUERY);
			ps.setInt(1, vehicleId);
			ps.execute();

			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				Reservation reservation = new Reservation();
				reservation.setIdClient(resultSet.getInt("id"));
				reservation.setIdClient(resultSet.getInt("client_id"));
				reservation.setIdVehicule(resultSet.getInt(vehicleId));
				reservation.setDateStart(resultSet.getDate("debut").toLocalDate());
				reservation.setDateEnd(resultSet.getDate("fin").toLocalDate());	
				listReservations.add(reservation);
			}
			resultSet.close();
			ps.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();;
		}
		return listReservations;
		 
	}

	 /**
	 * Retourne une liste contenant toutes les réservations
	 * 
	 * @return  client / Les clients à  trouver
	 * @throws DaoException
	 */
	public List<Reservation> findAll() throws DaoException {
		
		List<Reservation> listReservations = new ArrayList<>();
		Connection conn;
		try {
				conn = ConnectionManager.getConnection();
				PreparedStatement preparedStatement = conn.prepareStatement(FIND_RESERVATIONS_QUERY);
				ResultSet resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
						Reservation reservation = new Reservation();
						reservation.setId(resultSet.getInt("id"));
						reservation.setIdClient(resultSet.getInt("client_id"));
						reservation.setIdVehicule(resultSet.getInt("vehicle_id"));
						reservation.setDateStart(resultSet.getDate("debut").toLocalDate());
						reservation.setDateEnd(resultSet.getDate("fin").toLocalDate());
						listReservations.add(reservation);
				}
				resultSet.close();
				preparedStatement.close();
				conn.close();
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return listReservations;
		 
	}
	
	/**
	 * Compte le nombre de reservations
	 * 
	 * @return int /  nombre de reservations
	 * @throws DaoException
	 */
	public int count() throws DaoException {
		int count = 0;
		try {
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement ps = connection.prepareStatement(FIND_RESERVATIONS_QUERY);
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
	/**
	 * Update la reservation
	 * 
	 * @p Reservation/  La reservation  à modifier
	 * @throws DaoException
	 */
	public void update(Reservation reservation) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(UPDATE_RESERVATION_QUERY);
			ps.setInt(1, reservation.getIdClient());
			ps.setInt(2, reservation.getIdVehicule());
			ps.setDate(3, Date.valueOf(reservation.getDateStart()));
			ps.setDate(4, Date.valueOf(reservation.getDateEnd()));
			ps.setInt(5, reservation.getId());

			ps.execute();
			ps.close();
			connection.close();
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de l'update");
		}
	}	
}
