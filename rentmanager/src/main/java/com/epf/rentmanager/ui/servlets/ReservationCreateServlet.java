package com.epf.rentmanager.ui.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;


@WebServlet("/rents/create")
public class ReservationCreateServlet extends HttpServlet {
	@Autowired
	ReservationService reservationService;


    @Autowired
    VehicleService vehicleService;
    @Autowired
    ClientService clientService;

	@Override
	public void init() throws ServletException {
	super.init();
	SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {


        List<Vehicle> listV = new ArrayList<>();
        try {
            listV = vehicleService.findAll();
        } catch (ServiceException e) {
        }
        request.setAttribute("listVehicles", listV);

        List<Client> listC = new ArrayList<>();
        try {
            listC = clientService.findAll();
        } catch (ServiceException e) {
        }
        request.setAttribute("listClients", listC);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/rents/create.jsp");
        dispatcher.forward(request, response);
		
		}

		protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int client_id;
        int vehicule_id;
        LocalDate debut;
        LocalDate fin;

        client_id = Integer.valueOf(request.getParameter("client"));
        vehicule_id = Integer.valueOf(request.getParameter("car"));
        debut = LocalDate.parse(request.getParameter("begin"));
        fin = LocalDate.parse(request.getParameter("end"));

        Reservation reservation = new Reservation(client_id, vehicule_id, debut, fin);
        try {
        	
            reservationService.create(reservation);
            response.sendRedirect("http://localhost:8080/rentmanager/rents");
        } catch (ServiceException e) {
        	this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/create.jsp").forward(request, response);
        	
        }
        

    }

	
	
}
 