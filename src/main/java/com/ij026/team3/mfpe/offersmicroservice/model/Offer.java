package com.ij026.team3.mfpe.offersmicroservice.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Offer {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer offerId;
	private String authorId; // -> empId
	@JsonFormat(shape = Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate createdAt;
	@JsonFormat(shape = Shape.STRING, pattern = "dd-MM-yyyy")
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
