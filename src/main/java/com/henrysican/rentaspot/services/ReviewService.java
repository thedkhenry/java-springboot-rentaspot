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

    public Review saveReview(Review review){
        return reviewRepo.save(review);
    }

    public List<Review> getReviewsForLocation(int id){
        return reviewRepo.findAllByLocation_Id(id);
    }
}