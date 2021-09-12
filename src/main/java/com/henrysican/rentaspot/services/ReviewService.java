package com.henrysican.rentaspot.services;

import com.henrysican.rentaspot.dao.ReviewRepo;
import com.henrysican.rentaspot.models.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ReviewService {
    private final ReviewRepo reviewRepo;

    @Autowired
    public ReviewService(ReviewRepo reviewRepo){
        this.reviewRepo = reviewRepo;
    }

    /**
     * Creates a new entry in the database table with the Review provided and returns it.
     * @param review    the review to be saved
     * @return          the saved review
     */
    public Review saveReview(Review review){
        return reviewRepo.save(review);
    }
}