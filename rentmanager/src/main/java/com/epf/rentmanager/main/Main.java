package com.epf.rentmanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.epf.rentmanager.configuration.AppConfiguration;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;

public class Main {

	public static void main(String[] args) {
		
		ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
		ClientService clientService = context.getBean(ClientService.class);
		VehicleService vehicleService = context.getBean(VehicleService.class);
		ReservationService reservationService = context.getBean(ReservationService.class);
		
		/*try {
			System.out.println(clientService.findById(1));
			LocalDate dateBirthTest = LocalDate.of(1999, 10, 29);
			Client newClient = new Client(5,"toto","toto","toto@gmail.com",dateBirthTest);
			clientService.create(newClient);
			System.out.println(clientService.create(newClient));
			System.out.println(clientService.findAll());
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		/*Vehicle newVehicle = new Vehicle ("Renaud","clio",2);
		try {
			//vehicleService.create(newVehicle);
			//System.out.println(vehicleService.create(newVehicle));
			System.out.println(vehicleService.findAll());
			//System.out.println(clientService.count());
			//System.out.println(vehicleService.count());
			Vehicle newVehicle1 = new Vehicle (1,"test","clio",2);
			vehicleService.update(newVehicle1);
			System.out.println(vehicleService.findAll());
			
			Vehicle vehicule = vehicleService.findById(1);
			System.out.println(vehicule.toString());
			
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		LocalDate dateStart = LocalDate.of(1999, 10, 29);
		LocalDate dateEnd = LocalDate.of(2005, 10, 29);
		try {
			System.out.println(reservationService.findAll());
		} catch (ServiceException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Reservation newReservation = new Reservation (1,3, dateStart, dateEnd);
		
		System.out.println(newReservation);
		try {
			//System.out.println(clientService.findAll());
			//System.out.println(vehicleService.findAll());
			//reservationService.create(newReservation);
			Vehicle Vehicule = new Vehicle();
			Vehicule = vehicleService.findById(1);
			System.out.println(Vehicule);
			
			
			/*System.out.println(reservationService.findAll());
			System.out.println(reservationService.count());
			//reservationService.delete(1);
			//System.out.println(reservationService.findAll());
			//System.out.println(reservationService.count());
			Reservation reservation = new Reservation();
			reservation =reservationService.findById(2);
			System.out.println(reservation);
			
			LocalDate dateStart1 = LocalDate.of(2000, 10, 29);
			LocalDate dateEnd2 = LocalDate.of(2006, 10, 29);
			Reservation reservation1 = new Reservation(2, 2, 4,dateStart1,dateEnd2);
			
			List<Reservation> listR = new ArrayList<>();
			listR = reservationService.findByClient_id(1);
			System.out.println(listR);

			reservationService.update(reservation1);*/
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		
	}
}
