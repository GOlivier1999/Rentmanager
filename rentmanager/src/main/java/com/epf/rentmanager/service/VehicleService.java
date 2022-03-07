package com.epf.rentmanager.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Vehicle;
@Service
public class VehicleService {

	private VehicleDao vehicleDao;
	public static VehicleService instance;

	private VehicleService(VehicleDao VehicleDao){
		this.vehicleDao = VehicleDao;
		}	
	public long create(Vehicle vehicle) throws ServiceException {
		try {
			return this.vehicleDao.create(vehicle);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException("Une erreur a eu lieu lors de la creation du vehicule");
		}
		
	}

	public Vehicle findById(int id) throws ServiceException {
		try {
			return this.vehicleDao.findById(id).get();
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException("Une erreur a eu lieu dans findById de Vehiclexservice");
		}
		
	}

	public List<Vehicle> findAll() throws ServiceException {
		try {
			return this.vehicleDao.findAll();
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return null;	
	}
	

	public int count() throws ServiceException{
		int count = 1;
		try {
			return count = this.vehicleDao.count();
			
		} catch (DaoException e) {
			throw new ServiceException("Une erreur a eu lieu lors du comptage des vehicules");
		}
		
	}

	public void delete(int id) throws ServiceException {
		try {
			vehicleDao.delete(id);
		} catch (DaoException e) {
			throw new ServiceException("Une erreur a eu lieu lors de la suppression du vehicule");
		}
		
	}

	public void update(Vehicle vehicle) throws ServiceException {
		try {

			vehicleDao.update(vehicle);
		} catch (DaoException e) {
			throw new ServiceException("Une erreur a eu lieu lors de l'update du client");
		}		
	}	
}
