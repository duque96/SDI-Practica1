package com.uniovi.repositories;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.uniovi.entities.Relationship;
import com.uniovi.entities.RelationshipKey;
import com.uniovi.entities.User;

@Repository
public interface RelationshipRepository extends CrudRepository<Relationship, RelationshipKey> {

	@Query("SELECT r.status FROM Relationship r WHERE r.id=?1")
	public String getStatus(RelationshipKey relationshipKey);

	@Query("SELECT r.recipient FROM Relationship r WHERE (r.status='FRIEND') AND (r.sender.id=?1)")
	public Page<User> getFriends(Pageable pageable, Long id);

	@Query("SELECT r FROM Relationship r WHERE (r.recipient.id=?1) AND (r.status='REQUEST')")
	public Page<Relationship> getRequests(Pageable pageable, Long id);

	@Transactional
	@Modifying
	@Query("UPDATE Relationship SET status='FRIEND' WHERE id=?1")
	public void updateStatus(RelationshipKey relationshipKey);

}
