package com.uniovi.services;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.uniovi.entities.User;

@Service
public class InsertSampleDataService {
	@Autowired
	private UserService userService;

	@PostConstruct
	public void init() {
		User user1 = new User("Daniel", "daniel@gmail.com");
		user1.setPassword("123456");

		User user2 = new User("Maria", "maria@gmail.com");
		user2.setPassword("123456");

		User user3 = new User("Carlos", "carlos@gmail.com");
		user3.setPassword("123456");
		
		User user4 = new User("Marta", "marta@gmail.com");
		user4.setPassword("123456");

		User user5 = new User("Pelayo", "pelayo@gmail.com");
		user5.setPassword("123456");
		
		User user6 = new User("Laura", "laura@gmail.com");
		user6.setPassword("123456");
		
		User user7 = new User("Miguel", "miguel@gmail.com");
		user7.setPassword("123456");
		
		User user8 = new User("Carla", "carla@gmail.com");
		user8.setPassword("123456");
		
		User user9 = new User("Raul", "raul@gmail.com");
		user9.setPassword("123456");
		
		User user10 = new User("Lara", "lara@gmail.com");
		user10.setPassword("123456");

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

	}

}