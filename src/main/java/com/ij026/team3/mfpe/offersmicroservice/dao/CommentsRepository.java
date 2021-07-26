package com.ij026.team3.mfpe.offersmicroservice.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ij026.team3.mfpe.offersmicroservice.model.Comment;

public interface CommentsRepository extends JpaRepository<Comment, Long> {
	List<Comment> findByOfferId(int offerId);

	List<Comment> findByCommenterId(String commenterId);
}
