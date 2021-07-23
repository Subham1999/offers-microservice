package com.ij026.team3.mfpe.offersmicroservice.controller;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ij026.team3.mfpe.offersmicroservice.exception.NoSuchEmpIdException;
import com.ij026.team3.mfpe.offersmicroservice.model.Offer;
import com.ij026.team3.mfpe.offersmicroservice.model.OfferCategory;
import com.ij026.team3.mfpe.offersmicroservice.service.OfferService;

import lombok.extern.log4j.Log4j2;

/**
 * @author Admin
 *
 */
@RestController
@Log4j2
public class OfferServiceController {

	private ConcurrentHashMap<String, Object> empIdCache = new ConcurrentHashMap<>();

	public OfferServiceController() {
		empIdCache.put("guru", new Object());
		empIdCache.put("nikky", new Object());
		empIdCache.put("subsa", new Object());
		empIdCache.put("rish", new Object());
		empIdCache.put("ujjw", new Object());
	}

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
	public ResponseEntity<Offer> getOfferDetails(@PathVariable int offerId) {
		try {
			Optional<Offer> offerStatus = offerService.getOffer(offerId);
			log.debug("fetching offer details by offer id {} was succesfull", offerId);
			return offerStatus.isPresent() ? ResponseEntity.ok(offerStatus.get())
					: ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (Exception e) {
			log.debug("exception {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping("/offers/search/by-category")
	public ResponseEntity<List<Offer>> getOfferDetailsByCategory(
			@RequestParam(required = true) OfferCategory offerCategory) {
		try {
			List<Offer> offers = offerService.getOffersByCategory(offerCategory);
			log.debug("fetching offer details by category {} was succesfull", offerCategory.toString());
			return ResponseEntity.ok(offers);
		} catch (Exception e) {
			log.debug("exception {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of());
		}
	}

	@GetMapping("/offers/search/by-likes")
	public ResponseEntity<List<Offer>> getOfferDetailsByLikes(
			@RequestParam(required = false, defaultValue = "3") Integer limit,
			@RequestParam(required = false) String empId) {

		if (empIdCache.containsKey(empId)) {
			try {

				Predicate<Offer> predicate;

				if (empId == null) {
					predicate = o -> true;
				} else {
					predicate = o -> o.getAuthorId().equals(empId);
				}

				List<Offer> offers = offerService.getTopNOffers(limit, predicate);

				log.debug("fetching top offer details by likes was succesfull");
				return ResponseEntity.ok(offers);
			} catch (Exception e) {
				log.debug("exception {}", e.getMessage());
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of());
			}
		} else {
			throw new NoSuchEmpIdException("empid " + empId + " is invalid");
		}
	}

	@GetMapping("/offers/search/by-creation-date")
	public ResponseEntity<List<Offer>> getOfferDetailsByPostDate(@RequestParam(required = true) String createdOn) {
		LocalDate createdAt = null;

		try {
			createdAt = LocalDate.parse(createdOn, dateTimeFormatter);
		} catch (DateTimeException e) {
			log.debug("exception {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of());
		}

		try {
			List<Offer> offers = offerService.getOffersByCreationDate(createdAt);
			log.debug("fetching top offer details by likes was succesfull");
			return ResponseEntity.ok(offers);
		} catch (Exception e) {
			log.debug("exception {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of());
		}
	}

	@GetMapping("/offers/search/by-author")
	public ResponseEntity<List<Offer>> getOfferDetailsByAuthor(@RequestParam(required = true) String authorId) {

		if (empIdCache.containsKey(authorId)) {

			Predicate<Offer> filter1 = o -> o.getAuthorId().equals(authorId);

			try {
				Collection<Offer> allOffers = offerService.allOffers();
				List<Offer> offers = allOffers.stream().filter(filter1).collect(Collectors.toList());
				log.debug("fetching top offer details by likes was succesfull");
				return ResponseEntity.ok(offers);

			} catch (Exception e) {
				log.debug("exception {}", e.getMessage());
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of());
			}
		}
		
		throw new NoSuchEmpIdException("authorId " + authorId + " is invalid");
	}

	@PostMapping("/offers")
	public ResponseEntity<Boolean> addOffer(@Valid @RequestBody Offer newOffer) {
		boolean b = offerService.createOffer(newOffer);
		return b ? ResponseEntity.status(HttpStatus.CREATED).body(b)
				: ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(b);
	}

	@PostMapping("/offers/{offerId}/likes")
	public ResponseEntity<Offer> likeOffer(@PathVariable int offerId, @RequestParam(required = true) String likedBy) {
		Optional<Offer> optional = offerService.getOffer(offerId);
		if (optional.isPresent()) {
			Offer offer = optional.get();
			offer.like(likedBy);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(offerService.updateOffer(offer));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}

}
