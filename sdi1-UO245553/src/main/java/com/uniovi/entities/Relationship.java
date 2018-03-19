package com.uniovi.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;

@Entity
@IdClass(RelationshipKey.class)
public class Relationship {
	@Id
	@ManyToOne
	private User sender;

	@Id
	@ManyToOne
	private User recipient;

	@Column
	private String status;

	public Relationship() {
	}

	public Relationship(User sender, User recipient, String status) {
		this.status = status;
		Association.FriendRequest.link(sender, this, recipient);
	}

	public void _setSender(User sender) {
		this.sender = sender;
	}

	public void _setRecipient(User recipient) {
		this.recipient = recipient;
	}

	public String getStatus() {
		return status;
	}

	public User getSender() {
		return sender;
	}

	public User getRecipient() {
		return recipient;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
