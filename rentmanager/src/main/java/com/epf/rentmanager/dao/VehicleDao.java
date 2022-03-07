
package com.epf.rentmanager.dao;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;
@Repository
public class VehicleDao {
	
	private static VehicleDao instance = null;
	private VehicleDao() {}
	
	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicle(constructeur,modele, nb_places) VALUES(?, ?,?);";
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLE_QUERY = "SELECT id, constructeur,modele, nb_places FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur,modele, nb_places FROM Vehicle;";
	private static final String UPDATE_VEHICLE_QUERY = "UPDATE Vehicle SET constructeur = ?, modele = ?, nb_places = ? WHERE id = ?;";
	
	/**
	 * Créer un Vehicle
	 * 
	 * @param Vehicle / Le vehicle à  créer.
	 * @return long / L'id du vehicle créé
	 * @throws DaoException
	 */
	public long create(Vehicle vehicle) throws DaoException {
		
		int id = 0;

		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(CREATE_VEHICLE_QUERY,
			id = Statement.RETURN_GENERATED_KEYS);
			
			pstmt.setString(1, vehicle.getConstructor());
			pstmt.setString(2, vehicle.getModel());
			pstmt.setInt(3, vehicle.getNumPlace());
			pstmt.execute();
			pstmt.close();
			conn.close();

		} catch (SQLException e) {
			throw new DaoException();
		}
		return id;

	}
	
	/**
	 * Delete un vehicle via son id
	 * 
	 * @param int / id 
	 * @throws DaoException
	 */
	public void delete(int id) throws DaoException {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(DELETE_VEHICLE_QUERY);
			pstmt.setInt(1, id);
			pstmt.execute();
			pstmt.close();
			conn.close();

		} catch (SQLException e) {
			
			e.printStackTrace();
		}	
		
	}

	 /**
	 * Trouve un vehicle via son id
	 * 
	 * @param int / L'id du vehicle créer
	 * @return  client / Le vehicle à  trouver
	 * @throws DaoException
	 */
	public Optional<Vehicle> findById(int id) throws DaoException {
		// gestion de l'exception
		try {
				Connection conn = ConnectionManager.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(FIND_VEHICLE_QUERY);
				pstmt.setInt(1, id);
				ResultSet rs=pstmt.executeQuery();
					
				rs.next();
					
				int vehicleId = rs.getInt("id");
				String vehicleConstructeur = rs.getString("constructeur");
				String vehicleModel = rs.getString("modele");
				int vehicleNbPlace = rs.getInt("nb_places");
					
				Vehicle vehicle = new Vehicle(vehicleId,vehicleConstructeur,vehicleModel,vehicleNbPlace);	
				//Vehicle vehicle = new Vehicle(vehicleId,vehicleConstructeur,vehicleNbPlace);	
				return Optional.of(vehicle);
					
			} catch (SQLException e) {;
				e.printStackTrace();
			}
				
			return Optional.empty();
		
		
	}

	/**
	  * Retourne une liste contenant tous les vehicles
	  * 
	  * @return  Vehicles / Les vehicles à  trouver
	  * @throws DaoException
	  */
	public List<Vehicle> findAll() throws DaoException {
		List<Vehicle> listVehicles = new ArrayList<Vehicle>();
		try {
			
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(FIND_VEHICLES_QUERY);
			ResultSet rs=pstmt.executeQuery();
			
			while (rs.next()) {
				int vehicleId = rs.getInt("id");
				String vehicleConstructeur = rs.getString("constructeur");
				String vehicleModel = rs.getString("modele");
				int vehicleNbPlace = rs.getInt("nb_places");
					
				Vehicle vehicle = new Vehicle(vehicleId,vehicleConstructeur,vehicleModel,vehicleNbPlace);
				
				listVehicles.add(vehicle);						
			}			
			return listVehicles ;
		} catch (SQLException e) {
			e.printStackTrace();			
		}		
		return null;		
	}
	
	/**
	* Compte le nombre de vehicles
	* 
	* @return int /  nombre de vehicles
	* @throws DaoException
	*/
	public int count() throws DaoException {
		int count = 0;
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(FIND_VEHICLES_QUERY);
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
	* Update un vehicle
	* 
	* @p Vehicle/  Le vehicle  à modifier
	* @throws DaoException
	*/
	public void update(Vehicle vehicle)throws DaoException  {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(UPDATE_VEHICLE_QUERY);
			ps.setString(1, vehicle.getConstructor());
			ps.setString(2, vehicle.getModel());
			ps.setInt(3, vehicle.getNumPlace());
			ps.setInt(4, vehicle.getId());
			
			ps.execute();
			ps.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}				
	}
}
