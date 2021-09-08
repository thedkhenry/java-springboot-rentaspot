package com.henrysican.rentaspot;

import com.henrysican.rentaspot.models.Message;
import com.henrysican.rentaspot.models.User;
import com.henrysican.rentaspot.services.MessageService;
import com.henrysican.rentaspot.services.ReviewService;
import static org.junit.jupiter.api.Assertions.*;

import com.henrysican.rentaspot.services.UserService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;

@SpringBootTest
class RentaspotApplicationTests {

	@Autowired
	private ReviewService reviewService;
	@Autowired
	private UserService userService;
	@Autowired
	private MessageService messageService;

	@ParameterizedTest
	@CsvSource({"1, 3", "3, 2", "5, 6", "6, 5", "8, 8", "9, 4", "10, 2", "12, 1"})
	void testReviewServiceReviewCount(int locationId, int expectedCount) {
		int count = reviewService.getReviewsForLocation(locationId).size();
		System.out.println(count);
		assertEquals(expectedCount,count);
	}

	@ParameterizedTest
	@CsvSource({"26, 3", "3, 1", "2, 26", "6, 5", "8, 8", "1, 26", "1, 26", "26, 1"})
	void testContacts(int senderId, int receiverId){
		messageService.saveMessage(Message.builder()
				.messageContent("mymessagecontent")
				.senderId(senderId)
				.receiverId(receiverId)
				.build());
		List<User> contacts = userService.getAllContactsForUser(26);
		System.out.println("size: " + contacts.size());
		contacts.forEach(System.out::println);
	}
}
