package com.uniovi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.Publication;

public interface PublicationRepository extends CrudRepository<Publication, Long> {

	@Query("SELECT p FROM Publication p WHERE (p.creator.id=?1) ORDER BY creationDate DESC ")
	List<Publication> getPublicationsByCreator(Long id);

}
