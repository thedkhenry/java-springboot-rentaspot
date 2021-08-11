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

    public int getReviewCountForLocation(int id){
        return reviewRepo.countAllByLocation_Id(id);
    }

    public List<Review> getReviewsForLocation(int id){
        return reviewRepo.findAllByLocation_Id(id);
    }

    public double getWeightedAverageForLocation(int id){
        List<Review> reviews = reviewRepo.findAllByLocation_Id(id);
        double weightedAvg = 0;
        if (!reviews.isEmpty()) {
            long countFives = reviews.stream().filter(review -> review.getRating() == 5).count();
            long countFours = reviews.stream().filter(review -> review.getRating() == 4).count();
            long countThrees = reviews.stream().filter(review -> review.getRating() == 3).count();
            long countTwos = reviews.stream().filter(review -> review.getRating() == 2).count();
            long countOnes = reviews.stream().filter(review -> review.getRating() == 1).count();
            weightedAvg = Math.round(((5*countFives + 4*countFours + 3*countThrees + 2*countTwos + 1*countOnes) / (double)reviews.size()) * 100d)/100d;
        }
        return weightedAvg;
    }
}