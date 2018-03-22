package com.uniovi.entities;

public class Association {
	public static class FriendRequest {

		public static void link(User sender, Relationship relationship, User recipient) {
			relationship._setSender(sender);
			relationship._setRecipient(recipient);

			recipient._getFriendsRequest().add(relationship);
		}
	}

	public static class CreatePublication {

		public static void link(User creator, Publication publication) {
			publication.setCreator(creator);

			creator._getPublications().add(publication);
		}
	}
}
