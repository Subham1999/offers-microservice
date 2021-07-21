package com.ij026.team3.mfpe.offersmicroservice.controller;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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

	@Autowired
	private DateTimeFormatter dateTimeFormatter;

	@GetMapping("/test")
	public String test(@RequestParam(required = false) Map<String, Object> map) {
		map.forEach((s, o) -> System.err.println(s + " : " + o));
		return "aaa";
	}
	
	@GetMapping("/offers")
	public Collection<Offer> getOffers() {
		return offerService.allOffers();
	}

	@GetMapping("/offers/{offerId}")
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

	@GetMapping("/offers/search/by-category")
	public ResponseEntity<?> getOfferDetailsByCategory(@RequestParam(required = true) OfferCategory offerCategory) {
		try {
			List<Offer> offers = offerService.getOffersByCategory(offerCategory);
			log.debug("fetching offer details by category {} was succesfull", offerCategory.toString());
			return ResponseEntity.ok(offers);
		} catch (Exception e) {
			log.debug("exception {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@GetMapping("/offers/search/by-likes")
	public ResponseEntity<?> getOfferDetailsByLikes(@RequestParam(required = false, defaultValue = "3") Integer limit) {
		try {
			List<Offer> offers = offerService.getTopNOffers(limit);
			log.debug("fetching top offer details by likes was succesfull");
			return ResponseEntity.ok(offers);
		} catch (Exception e) {
			log.debug("exception {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@GetMapping("/offers/search/by-creation-date")
	public ResponseEntity<?> getOfferDetailsByPostDate(@RequestParam(required = true) String createdOn) {
		LocalDate createdAt = null;

		try {
			createdAt = LocalDate.parse(createdOn, dateTimeFormatter);
		} catch (DateTimeException e) {
			log.debug("exception {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}

		try {
			Map<Integer, Offer> offers = offerService.getOffersByCreationDate(createdAt);
			log.debug("fetching top offer details by likes was succesfull");
			return ResponseEntity.ok(offers);
		} catch (Exception e) {
			log.debug("exception {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@GetMapping("/offers/search/by-author")
	public ResponseEntity<?> getOfferDetailsByAuthor(@RequestParam(required = true) String authorId,
			@RequestParam(required = false) boolean open) {

		try {
			Collection<Offer> allOffers = offerService.allOffers();
			List<Offer> offers = allOffers.stream().filter(Offer::isOpen).collect(Collectors.toList());
			log.debug("fetching top offer details by likes was succesfull");
			return ResponseEntity.ok(offers);
		} catch (Exception e) {
			log.debug("exception {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@PostMapping("/offers")
	public ResponseEntity<Object> addOffer(@Valid @RequestBody Offer newOffer) {
		boolean b = offerService.createOffer(newOffer);
		return b ? ResponseEntity.status(HttpStatus.CREATED).build()
				: ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
	}

}
