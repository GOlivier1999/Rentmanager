package com.epf.rentmanager.ui.servlets;

import java.io.IOException;
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

@WebServlet("/cars/details")
public class VehicleDetailsServlet extends HttpServlet {
    public VehicleDetailsServlet() {
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

        int id = Integer.valueOf(request.getQueryString().substring(3));

        List<Client> listC  = new ArrayList<>();
        List<Reservation> listR = new ArrayList<>();

        Vehicle Vehicule = new Vehicle();
        try {
            Vehicule = vehicleService.findById(id);
        } catch (ServiceException e) {
        }

        try {
            listR = reservationService.findByVehicleId(id);
        } catch (ServiceException e) {
        }

        try {
            for (int i = 0; i < listR.size(); i++) {
                listC.add(clientService.findById(listR.get(i).getIdVehicule()));
            }
        } catch (ServiceException e) {
        }

        request.setAttribute("constructeur", Vehicule.getConstructor());
        request.setAttribute("modele", Vehicule.getModel());
        request.setAttribute("nb_places", Vehicule.getNumPlace());

        request.setAttribute("reservations", listR);
        request.setAttribute("clients", listC);
        
        request.setAttribute("Csize", listC.size());
        request.setAttribute("Rsize", listR.size());
        

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/vehicles/details.jsp");
        dispatcher.forward(request, response);
    }

}
