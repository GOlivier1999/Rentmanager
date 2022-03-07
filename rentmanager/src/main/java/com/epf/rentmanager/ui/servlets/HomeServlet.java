package com.epf.rentmanager.ui.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;



@WebServlet("/home")
public class HomeServlet extends  HttpServlet{
	@Autowired
	VehicleService vehiculeService;
	@Autowired
	ReservationService reservationService;
	@Autowired
	ClientService clientService;
		
	@Override
	public void init() throws ServletException {
	    super.init();
	    SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			
		//this.getServletContext().getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request,response);
		try {
				request.setAttribute("countUsers", this.clientService.count());
				request.setAttribute("countCars", this.vehiculeService.count());
				request.setAttribute("countRents", this.reservationService.count());
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/home.jsp");
				dispatcher.forward(request, response);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			}

}
