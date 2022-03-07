package com.epf.rentmanager.model;

import java.time.LocalDate;
import java.time.Period;


public class Client {
	

	
	private String name;
	private String lastname;
	private String email;
	private LocalDate birthDate;
	
	private int id;
	
	public Client() {
		super();
	}

	public Client(int id, String name, String lastname, String email, LocalDate birthDate) {
		super();
		this.id = id;
		this.name = name;
		this.lastname = lastname.toUpperCase();
		this.email = email;
		this.birthDate = birthDate;
	}


	public Client(String name, String lastname, String email, LocalDate birthDate) {
		super();
		this.name = name;
		this.lastname = lastname.toUpperCase();
		this.email = email;
		this.birthDate = birthDate;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname.toUpperCase();
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public LocalDate getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	@Override
	public String toString() {
		return "Client [name=" + name + ", lastname=" + lastname + ", email=" + email + ", birthDate=" + birthDate
				+ ", id=" + id + "]";
	}
	public int getAge() {
		LocalDate now=LocalDate.now();
		return Period.between(this.birthDate, now).getYears();
	}

	public boolean isLegal() {
		return this.getAge() >= 18;
	}

	public boolean isLongFirst() {
		return this.getName().length() >= 3;
	
	}

	public boolean isLongLast() {
		return this.getLastname().length() >= 3;
	}
	public boolean hasProperEmail() {
		return this.getEmail().contains("@");
	}


}
