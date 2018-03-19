package com.uniovi.entities;

import java.io.Serializable;

public class RelationshipKey implements Serializable{
	private static final long serialVersionUID = 1L;
	
	Long sender;
	Long recipient;
	
	public RelationshipKey(Long sender, Long recipient) {
		this.sender = sender;
		this.recipient = recipient;
	}
	
	public RelationshipKey() {}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((recipient == null) ? 0 : recipient.hashCode());
		result = prime * result + ((sender == null) ? 0 : sender.hashCode());
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
		RelationshipKey other = (RelationshipKey) obj;
		if (recipient == null) {
			if (other.recipient != null)
				return false;
		} else if (!recipient.equals(other.recipient))
			return false;
		if (sender == null) {
			if (other.sender != null)
				return false;
		} else if (!sender.equals(other.sender))
			return false;
		return true;
	}
}
