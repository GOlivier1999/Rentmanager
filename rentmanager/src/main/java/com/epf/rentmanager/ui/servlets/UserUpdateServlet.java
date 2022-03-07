package com.epf.rentmanager.ui.servlets;

import java.io.IOException;
import java.time.LocalDate;

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
import com.epf.rentmanager.service.ClientService;

@WebServlet("/users/update")
public class UserUpdateServlet extends HttpServlet {
	private int id;

    public UserUpdateServlet() {
    }

    @Autowired
    ClientService clientService;
    @Override
	public void init() throws ServletException {
	super.init();
	SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        id = Integer.valueOf(request.getQueryString().substring(3));
        request.setAttribute("id", id);
        Client client = new Client();
        try {
            client = clientService.findById(id);
        } catch (ServiceException e) {
            // TODO: handle exception
        }

        request.setAttribute("nom", client.getLastname());
        request.setAttribute("prenom", client.getName());
        request.setAttribute("email", client.getEmail());
        request.setAttribute("birthdate", client.getBirthDate());

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/users/update.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nom;
        String prenom;
        String email;
        LocalDate naissance;

        nom = request.getParameter("last_name");
        prenom = request.getParameter("first_name");
        email = request.getParameter("email");
        naissance = LocalDate.parse(request.getParameter("birthdate"));

        Client client = new Client(id, prenom, nom, email, naissance);

        try {
            clientService.update(client);
            response.sendRedirect("http://localhost:8080/rentmanager/users");
        } catch (ServiceException e) {
            
        }

    }

}

