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
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.VehicleService;

@WebServlet("/cars/update")
public class VehicleUpdateServlet extends HttpServlet{
	private int id;

    public VehicleUpdateServlet() {
    }
    @Autowired
    VehicleService vehicleService;
    
 
    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }
    

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                
        id = Integer.valueOf(request.getQueryString().substring(3));
        request.setAttribute("id", id);
        Vehicle vehicule = new Vehicle();
        try {
            vehicule = vehicleService.findById(id);
        } catch (ServiceException e) {

        }

        request.setAttribute("manufacturer", vehicule.getConstructor());
        request.setAttribute("modele", vehicule.getModel());
        request.setAttribute("seats", vehicule.getNumPlace());

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/vehicles/update.jsp");
        dispatcher.forward(request, response);
    }

    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String constructeur;
        String modele;
        int nb_places;

        constructeur = request.getParameter("manufacturer");
        modele = request.getParameter("modele");
        nb_places = Integer.valueOf(request.getParameter("seats"));

        Vehicle vehicule = new Vehicle(id, constructeur, modele, nb_places);

        try {
			vehicleService.update(vehicule);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.sendRedirect("http://localhost:8080/rentmanager/cars");
       

    }

}
