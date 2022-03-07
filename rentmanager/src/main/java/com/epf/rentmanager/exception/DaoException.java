package com.epf.rentmanager.exception;

public class DaoException extends Exception{
	
	public DaoException() {
		super("Erreur dans la DAO");
	}
    public DaoException(String message) {
        super(message);
    }

}