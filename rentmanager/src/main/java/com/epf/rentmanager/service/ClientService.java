
package com.epf.rentmanager.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
@Service
public class ClientService {

	private ClientDao clientDao;
	public static ClientService instance;
	
	private ClientService(ClientDao clientDao){
		this.clientDao = clientDao;
		}

	
	public long create(Client client) throws ServiceException {
		if (!client.isLegal()) {
			throw new ServiceException("L'utilisateur doit avoir au moins 18ans");
		} else if (!client.isLongFirst()) {
			throw new ServiceException("Le prénom de l'utilisateur doit avoir 3 caractères");
		} else if (!client.isLongLast()) {
			throw new ServiceException("Le nom de l'utilisateur doit avoir 3 caractères");
		}else if (!client.hasProperEmail()) {
			throw new ServiceException("L'email proposé n'est pas un email");
		} else if(!hasNoSameEmail(client)) {
			throw new ServiceException("L'email existe déjà");
		}else {

		try {
			return this.clientDao.create(client);
		} catch (DaoException e) {
			
			e.printStackTrace();
			throw new ServiceException("Une erreur a eu lieu lors de la création du client");
		}
		}
		
	}

	public boolean hasNoSameEmail(Client client) throws ServiceException {
		try {
			return clientDao.hasNoSameEmail(client);
		} catch (DaoException e) {
			throw new ServiceException("Erreur lors du test sur l'email");

		}
	}


	public Client findById( int id) throws ServiceException {
		
		
		try {
			return this.clientDao.findById(id).get();
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return null;
	}



	public List<Client>  findAll() throws ServiceException  {
		try {
			return this.clientDao.findAll();
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void update(Client client) throws ServiceException {
		try {
				clientDao.update(client);
			} catch (DaoException e) {
				throw new ServiceException("Une erreur a eu lieu lors de l'update du client");
			}
	}
	
	
	public void delete(int id) throws ServiceException {
		try {
			clientDao.delete(id);
		} catch (DaoException e) {
			throw new ServiceException("Une erreur a eu lieu lors de la suppression du client");
		}
		
	}

	public int count() throws ServiceException{
		int count = 1;
		try {
			return count = this.clientDao.count();
			
		} catch (DaoException e) {
			throw new ServiceException("Une erreur a eu lieu lors du comptage des clients");
		}
		
	}
}

	
