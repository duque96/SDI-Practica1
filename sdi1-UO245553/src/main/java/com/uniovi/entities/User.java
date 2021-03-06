package com.uniovi.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
public class User {

	@Id
	@GeneratedValue
	private Long id;
	private String name;
	@Column(unique = true)
	private String email;
	private String password;
	private String role;

	@Transient // No se almacena en la base de datos
	private String passwordConfirm;

	@Transient
	private String status;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "sender")
	private Set<Relationship> friendRequests = new HashSet<Relationship>();

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "creator")
	private Set<Publication> publications = new HashSet<Publication>();

	public User() {
	}

	public User(String name, String email) {
		this.name = name;
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String confirmPassword) {
		this.passwordConfirm = confirmPassword;
	}

	public Long getId() {
		return id;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Set<Relationship> _getFriendsRequest() {
		return friendRequests;
	}

	public Set<Relationship> getFriendsRequest() {
		return new HashSet<Relationship>(friendRequests);
	}

	public Set<Publication> _getPublications() {
		return publications;
	}

	public Set<Publication> getPublications() {
		return new HashSet<Publication>(publications);
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", email=" + email + ", password=" + password + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
