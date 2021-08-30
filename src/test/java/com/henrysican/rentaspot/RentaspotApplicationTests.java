package com.henrysican.rentaspot;

import com.henrysican.rentaspot.services.ReviewService;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RentaspotApplicationTests {

	@Autowired
	private ReviewService reviewService;

	@ParameterizedTest
	@CsvSource({"1, 3", "3, 2", "5, 6", "6, 5", "8, 8", "9, 4", "10, 2", "12, 1"})
	void testReviewServiceReviewCount(int locationId, int expectedCount) {
		int count = reviewService.getReviewsForLocation(locationId).size();
		System.out.println(count);
		assertEquals(expectedCount,count);
	}

}
