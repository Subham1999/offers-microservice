package com.ij026.team3.mfpe.offersmicroservice.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.ij026.team3.mfpe.offersmicroservice.model.Offer;
import com.ij026.team3.mfpe.offersmicroservice.model.OfferCategory;

public interface GenericOfferService {

	boolean createOffer(Offer offer);

	boolean updateOffer(int offerId, Offer offer);

	boolean likeOffer(String authorId, int offerId);

	boolean buyOffer(String buyerId, int offerId);

	// {"no_likes" : 25, .....}
	Map<String, String> offerStatus(int offerId);

	List<Offer> getOffersByCategory(OfferCategory offerCategory);
	
	/**
	 * @param minLikes
	 * @return all offers with minimum {minLikes}
	 */
	// [{"likes" : 150, {"akgkags"}}, {"likes" : 87, {"akgkags"}}, {"likes" : 80, {"akgkags"}}, ...]	
	Map<Integer, Offer> getTopOffers(String authorId);
	
	Map<Integer, Offer> getOffersByCreationDate(LocalDate createdAt);
}
