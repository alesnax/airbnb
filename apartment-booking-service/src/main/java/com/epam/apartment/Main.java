package com.epam.apartment;

import java.time.LocalDate;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.epam.apartment.dao.ApartmentDao;
//import com.epam.apartment.dao.UserDao;
import com.epam.apartment.domain.Apartment;
import com.epam.apartment.domain.ApartmentCriteria;
import com.epam.apartment.domain.User;

public class Main {

	public static void main(String[] args){
		
		ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
		ApartmentDao apartmentDao = (ApartmentDao) context.getBean("jdbcApartmentDao");
		//UserDao userDao = (UserDao) context.getBean("jdbcUserDao");
		User user = new User();
		user.setBirthday(null);
		user.setEmail("alesales@asfklj.assd");
		user.setName("asfkl");
		user.setSurname("asfhkl");
		user.setId(90);
		
		ApartmentCriteria criteria = new ApartmentCriteria();
		criteria.setArrivalDate(LocalDate.now());
		criteria.setLeavingDate(LocalDate.now());
		
		List<Apartment> aps = apartmentDao.findAvailableApartmentByCriteria(criteria);
		System.out.println(aps.size());
		((AbstractApplicationContext) context).close();
		
		
	}
	
}
