package com.ij026.team3.mfpe.offersmicroservice.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ij026.team3.mfpe.offersmicroservice.dao.OfferRepository;
import com.ij026.team3.mfpe.offersmicroservice.model.Offer;
import com.ij026.team3.mfpe.offersmicroservice.model.OfferCategory;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class OfferService implements GenericOfferService {

	@Autowired
	private OfferRepository offerRepository;

	@Override
	public boolean createOffer(Offer offer) {
		Offer save = offerRepository.save(offer);
		return save != null;
	}

	// ...../offers/{id} [POST] => request Body => offer
	@Override
	public boolean updateOffer(int offerId, Offer offer) {
		if (offerId == offer.getOfferId() && checkIfPresent(offerId)) {
			Offer save = offerRepository.save(offer);
			return save != null;
		}
		return false;
	}

	private boolean checkIfPresent(int offerId) {
		return offerRepository.findByOfferId(offerId).isPresent();
	}

	@Override
	public boolean likeOffer(String authorId, int offerId) {
		Optional<Offer> foundByOfferId = offerRepository.findByOfferId(offerId);
		if (foundByOfferId.isPresent()) {
			foundByOfferId.get().like();
			offerRepository.save(foundByOfferId.get());
			return true;
		}
		return false;
	}

	@Override
	public boolean buyOffer(String buyerId, int offerId) {
		Optional<Offer> foundByOfferId = offerRepository.findByOfferId(offerId);
		if (foundByOfferId.isPresent()) {
			foundByOfferId.get().setBuyerId(buyerId);
			Offer save = offerRepository.save(foundByOfferId.get());
			return save != null;
		}
		return false;
	}

	@Override
	public Map<String, String> offerStatus(int offerId) {
		Map<String, String> matrix = new HashMap<>();
		Optional<Offer> foundByOfferId = offerRepository.findByOfferId(offerId);
		if (foundByOfferId.isPresent()) {
			log.debug("preparing offer status for offerId {}", offerId);
			Offer offer = foundByOfferId.get();
			matrix.put("Author ID", offer.getAuthorId());
			if (!offer.getBuyerId().isBlank()) {
				matrix.put("Buyer ID", offer.getBuyerId());
			}
			matrix.put("Category", offer.getOfferCategory().toString());
			matrix.put("Details", offer.getDetails());
			matrix.put("Open status", Boolean.toString(offer.isOpen()));
			matrix.put("Likes", Integer.toString(offer.getLikes()));
			return matrix;
		} else {
			return matrix;
		}
	}

	@Override
	public List<Offer> getOffersByCategory(OfferCategory offerCategory) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Integer, Offer> getTopOffers(String authorId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Integer, Offer> getOffersByCreationDate(LocalDate createdAt) {
		// TODO Auto-generated method stub
		return null;
	}

}
