package com.uniovi.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.uniovi.entities.Relationship;
import com.uniovi.entities.RelationshipKey;

@Repository
public interface RelationshipRepository extends CrudRepository<Relationship, RelationshipKey> {
	
	@Query("SELECT r.status FROM Relationship r WHERE r.id=?1") 
	public String getStatus(RelationshipKey relationshipKey);
	
}
