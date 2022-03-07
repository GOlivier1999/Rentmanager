package com.epf.rentmanager.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Reservation;


@Service
public class ReservationService {

	
	private ReservationDao reservationDao;
	public static ReservationService instance;
	
	private  ReservationService(ReservationDao reservationDao){
		this.reservationDao = reservationDao;
		}
	
	
	public List<Reservation> findByClient_id(int client_id) throws ServiceException {
		try {
			return reservationDao.findByClient_id(client_id);
		} catch (DaoException e) {
			throw new ServiceException("Erreur lors du find by client id");
		}
	}

	
	public List<Reservation> findAll() throws ServiceException {
		try {
			return reservationDao.findAll();
		} catch (DaoException e) {
			throw new ServiceException();
		}
	}
	
	public int create(Reservation reservation) throws ServiceException {
		try {
			return reservationDao.create(reservation);
		} catch (DaoException e) {
			throw new ServiceException("Une erreur a eu lieu lors de la création de la reservation");
		}

	}


	public int count() throws ServiceException{
		int count = 0;
		try {
			return count = this.reservationDao.count();
			
		} catch (DaoException e) {
			throw new ServiceException("Une erreur a eu lieu lors du comptage des reservations");
		}
		
	}


	public void delete(int id) throws ServiceException {
		try {
			reservationDao.delete(id);
		} catch (DaoException e) {
			throw new ServiceException("Une erreur a eu lieu lors de la suppression de la reservation");
		}
		
	}

	public void update(Reservation reservation) throws ServiceException {

		try {
			reservationDao.update(reservation);
		} catch (DaoException e) {
			throw new ServiceException("Une erreur a eu lieu lors de l'update de la reservation");
		}
	}


	public Reservation findById(int id) throws ServiceException {
		try {
			Optional<Reservation> reservation = reservationDao.findById(id);
			return reservation.get();
		} catch (DaoException e) {
			throw new ServiceException("Une erreur a eu lieu lors de la récupération de la reservation");
		}

	}


	public List<Reservation> findByVehicleId(int vehicle_id)throws ServiceException {
		try {
			return reservationDao.findByVehicleId(vehicle_id);
		} catch (DaoException e) {
			throw new ServiceException("Erreur lors du find by vehicule id");
		}
	}

}
