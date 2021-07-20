package com.ij026.team3.mfpe.offersmicroservice.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Offer {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer offerId;
	private String authorId; // -> empId
	private LocalDate createdAt;
	private LocalDate closedAt;
	@Column(length = 100)
	private String details;
	private Integer likes;
	private OfferCategory offerCategory;
	private boolean isOpen;
	private String buyerId; // -> empId

	public void like() {
		++likes;
	}
}
