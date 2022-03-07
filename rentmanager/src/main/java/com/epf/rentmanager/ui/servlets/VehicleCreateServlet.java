package com.epf.rentmanager.ui.servlets;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.VehicleService;


@WebServlet("/cars/create")
public class VehicleCreateServlet extends HttpServlet {
	@Autowired
	VehicleService vehicleService;
	@Override
	public void init() throws ServletException {
	super.init();
	SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicles/create.jsp").forward(request, response);
	
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String constructor;
		String modele;
		int numPlace;
			
        constructor = request.getParameter("manufacturer");
        modele = request.getParameter("modele");
        numPlace = Integer.valueOf(request.getParameter("seats"));
        

        Vehicle vehicle = new Vehicle(constructor, modele, numPlace);
        try {
            vehicleService.create(vehicle);
            response.sendRedirect("http://localhost:8080/rentmanager/cars");
        } catch (ServiceException e) {
        	this.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicle/create.jsp").forward(request, response);
        	 
        }
	
		}
}