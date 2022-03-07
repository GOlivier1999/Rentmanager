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
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;

@WebServlet("/rents/details")
public class ReservationDetailsServlet extends HttpServlet  {
	public ReservationDetailsServlet() {
    }
	
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

        int id = Integer.valueOf(request.getQueryString().substring(3));

        Reservation reservation = new Reservation();
        try {
            reservation = reservationService.findById(id);
        } catch (ServiceException e) {
        }
        Client client = new Client();
        try {
            client = clientService.findById(reservation.getIdClient());
        } catch (ServiceException e) {
        }
        Vehicle vehicule = new Vehicle();
        try {
            vehicule = vehiculeService.findById(reservation.getIdClient());
        } catch (ServiceException e) {
        }


        request.setAttribute("client_id", client.getId());
        request.setAttribute("prenom", client.getName());
        request.setAttribute("nom", client.getLastname());
        request.setAttribute("email", client.getEmail());
        request.setAttribute("naissance", client.getBirthDate());
        
        request.setAttribute("vehicule_id", client.getId());
        request.setAttribute("constructeur", vehicule.getConstructor());
        request.setAttribute("modele", vehicule.getModel());
        request.setAttribute("nb_places", vehicule.getNumPlace());

        request.setAttribute("reservation_id", client.getId());
        request.setAttribute("debut", reservation.getDateStart());
        request.setAttribute("fin", reservation.getDateEnd());
       

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/rents/details.jsp");
        dispatcher.forward(request, response);
    }

}
