package com.uniovi.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Relationship {
	@Id
	@ManyToOne
	private User user;
	
	@Id
	@ManyToOne
	private User friend;
	
	@Column
	private String status;
	
	public Relationship() {}
}
