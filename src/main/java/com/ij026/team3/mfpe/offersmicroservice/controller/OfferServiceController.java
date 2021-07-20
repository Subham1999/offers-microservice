package com.ij026.team3.mfpe.offersmicroservice.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ij026.team3.mfpe.offersmicroservice.model.Offer;
import com.ij026.team3.mfpe.offersmicroservice.model.OfferCategory;
import com.ij026.team3.mfpe.offersmicroservice.service.OfferService;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
public class OfferServiceController {

	@Autowired
	private OfferService offerService;

	@GetMapping("/test")
	public String test() {
		return "aaa";
	}

	@GetMapping("/offer-details/{offerId}")
	public ResponseEntity<?> getOfferDetails(@PathVariable String offerId) {
		try {
			Map<String, String> offerStatus = offerService.offerStatus(Integer.parseInt(offerId));
			log.debug("fetching offer details by offer id {} was succesfull", offerId);
			return ResponseEntity.ok(offerStatus);
		} catch (Exception e) {
			log.debug("exception {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@GetMapping("/offer-details/category/{offerCategory}")
	public ResponseEntity<?> getOfferDetailsByCategory(@PathVariable OfferCategory offerCategory) {
		try {
			List<Offer> offers = offerService.getOffersByCategory(offerCategory);
			log.debug("fetching offer details by category {} was succesfull", offerCategory.toString());
			return ResponseEntity.ok(offers);
		} catch (Exception e) {
			log.debug("exception {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@GetMapping("/offer-details/toplikes")
	public ResponseEntity<?> getOfferDetailsByLikes() {
		try {
			List<Offer> offers = offerService.getTopNOffers(3);
			log.debug("fetching top offer details by likes was succesfull");
			return ResponseEntity.ok(offers);
		} catch (Exception e) {
			log.debug("exception {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@GetMapping("/offer-details/creation-date/{createdAt}")
	public ResponseEntity<?> getOfferDetailsByPostDate(@PathVariable LocalDate createdAt) {
		try {
			Map<Integer, Offer> offers = offerService.getOffersByCreationDate(createdAt);
			log.debug("fetching top offer details by likes was succesfull");
			return ResponseEntity.ok(offers);
		} catch (Exception e) {
			log.debug("exception {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

}
