package com.uniovi.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Relationship;
import com.uniovi.entities.RelationshipKey;
import com.uniovi.entities.User;
import com.uniovi.repositories.RelationshipRepository;

@Service
public class RelationshipService {

	private static final Logger logger = LoggerFactory.getLogger(RelationshipService.class);

	@Autowired
	private RelationshipRepository relationshipRepository;

	public void addRelationship(Relationship relationShip) {
		relationshipRepository.save(relationShip);
		logger.debug("Info: relación almacenada en la base de datos entre " + relationShip.getSender() + " y "
				+ relationShip.getRecipient());
	}

	public Page<User> getRequests(Pageable pageable, User user) {
		List<User> aux = new ArrayList<User>(user.getFriendsRequest());

		int start = pageable.getOffset();
		int end = (start + pageable.getPageSize()) > aux.size() ? aux.size() : (start + pageable.getPageSize());

		Page<User> requests = new PageImpl<User>(aux.subList(start, end), pageable, aux.size());

		logger.debug("Info: Se han obtenido todas las peticiones de amistada para el usuario " + user.getId());

		return requests;
	}

	public void acceptFriend(User activeUser, User user) {
		activeUser._getFriendsRequest().remove(user);

		Relationship r = relationshipRepository.findOne(new RelationshipKey(user.getId(), activeUser.getId()));
		r.setStatus("FRIEND");

		Relationship r2 = relationshipRepository.findOne(new RelationshipKey(activeUser.getId(), user.getId()));

		if (r2 == null) {
			r2 = new Relationship();
			r2._setSender(activeUser);
			r2._setRecipient(user);
			r2.setStatus("FRIEND");
			relationshipRepository.save(r2);
		} else {
			user._getFriendsRequest().remove(activeUser);
			r2.setStatus("FRIEND");
		}

		logger.debug("Info: Se ha aceptado la petición de amistad entre " + activeUser.getId() + " y " + user.getId());
	}

	public Page<User> getFriends(Pageable pageable, Long id) {
		logger.debug("Info: Se ha obtenido la lista de amigos para el usuario " + id);
		return relationshipRepository.getFriends(pageable, id);
	}
}
