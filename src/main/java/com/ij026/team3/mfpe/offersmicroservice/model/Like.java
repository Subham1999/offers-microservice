package com.ij026.team3.mfpe.offersmicroservice.model;

import java.time.LocalDate;

import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Like {
	@EqualsAndHashCode.Include
	private String empId;

	@JsonFormat(shape = Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate likedDate;
}
