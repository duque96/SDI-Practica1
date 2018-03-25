package com.uniovi.services;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.User;

@Service
public class InsertSampleDataService {

	private static final Logger logger = LoggerFactory.getLogger(InsertSampleDataService.class);

	@Autowired
	private UserService userService;

	@PostConstruct
	public void init() {
		User user1 = new User("Daniel", "daniel@gmail.com");
		user1.setPassword("123456");
		user1.setRole("ROLE_USER");

		User user2 = new User("Maria", "maria@gmail.com");
		user2.setPassword("123456");
		user2.setRole("ROLE_USER");

		User user3 = new User("Carlos", "carlos@gmail.com");
		user3.setPassword("123456");
		user3.setRole("ROLE_USER");

		User user4 = new User("Marta", "marta@gmail.com");
		user4.setPassword("123456");
		user4.setRole("ROLE_USER");

		User user5 = new User("Pelayo", "pelayo@gmail.com");
		user5.setPassword("123456");
		user5.setRole("ROLE_USER");

		User user6 = new User("Laura", "laura@gmail.com");
		user6.setPassword("123456");
		user6.setRole("ROLE_USER");

		User user7 = new User("Miguel", "miguel@gmail.com");
		user7.setPassword("123456");
		user7.setRole("ROLE_USER");

		User user8 = new User("Carla", "carla@gmail.com");
		user8.setPassword("123456");
		user8.setRole("ROLE_USER");

		User user9 = new User("Raul", "raul@gmail.com");
		user9.setPassword("123456");
		user9.setRole("ROLE_USER");

		User user10 = new User("Lara", "lara@gmail.com");
		user10.setPassword("123456");
		user10.setRole("ROLE_USER");

		User admin = new User("admin", "admin@gmail.com");
		admin.setPassword("123456");
		admin.setRole("ROLE_ADMIN");

		userService.addUser(user1);
		userService.addUser(user2);
		userService.addUser(user3);
		userService.addUser(user4);
		userService.addUser(user5);

		userService.addUser(user6);
		userService.addUser(user7);
		userService.addUser(user8);
		userService.addUser(user9);
		userService.addUser(user10);
		userService.addUser(admin);

		logger.debug("Info: se han cargado los usuarios en la base de datos");

	}

}