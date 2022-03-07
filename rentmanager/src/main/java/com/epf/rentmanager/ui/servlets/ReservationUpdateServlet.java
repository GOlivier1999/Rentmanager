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
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;

import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;

@WebServlet("/rents/update")
public class ReservationUpdateServlet extends HttpServlet{
	public ReservationUpdateServlet() {
    }

    @Autowired
    VehicleService vehicleService;
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

        int id = Integer.valueOf(request.getQueryString().substring(3));
        Reservation reservation = new Reservation();
        try {
            reservation = reservationService.findById(id); 
        } catch (ServiceException e) {
        }
        request.setAttribute("debut", reservation.getDateStart());
        request.setAttribute("fin", reservation.getDateEnd());

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/rents/update.jsp");
        dispatcher.forward(request, response);

    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.valueOf(request.getQueryString().substring(3));
        int client_id;
        int vehicule_id;
        LocalDate debut;
        LocalDate fin;

        client_id = Integer.valueOf(request.getParameter("client"));
        vehicule_id = Integer.valueOf(request.getParameter("car"));
        debut = LocalDate.parse(request.getParameter("begin"));
        fin = LocalDate.parse(request.getParameter("end"));

        Reservation reservation = new Reservation(id, client_id, vehicule_id, debut, fin);

        try {
            reservationService.update(reservation); 
        } catch (ServiceException e) {
            // TODO: handle exception
        }
        response.sendRedirect("http://localhost:8080/rentmanager/rents");

    }



}
