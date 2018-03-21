package com.uniovi.entities;

public class Association {
	public static class FriendRequest {

		public static void link(User sender, Relationship relationship, User recipient) {
			relationship._setSender(sender);
			relationship._setRecipient(recipient);

			recipient._getFriendsRequest().add(relationship);
		}

		// public static void unlink(Cargo cargo) {
		// cargo.getMedioPago()._getCargos().remove(cargo);
		// cargo.getFactura()._getCargos().remove(cargo);
		//
		// cargo._setMedioPago(null);
		// cargo._setFactura(null);
		// }
	}

}
