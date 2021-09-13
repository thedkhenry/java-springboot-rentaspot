package com.henrysican.rentaspot;

import com.henrysican.rentaspot.models.Location;
import com.henrysican.rentaspot.models.Message;
import com.henrysican.rentaspot.models.User;
import com.henrysican.rentaspot.services.LocationService;
import com.henrysican.rentaspot.services.MessageService;

import com.henrysican.rentaspot.services.UserService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceTests {

	@Autowired
	private UserService userService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private LocationService locationService;

	@Order(1)
	@ParameterizedTest
	@CsvSource({"26,3","3,1","2,26","6,5","8,8","1,26","1,26","26,1"})
	void testSaveMessage(int senderId, int receiverId){
		String msg = "my message {" + senderId + "," + receiverId+ "}";
		Message message = messageService.saveMessage(
				Message.builder()
				.messageContent(msg).senderId(senderId).receiverId(receiverId)
				.build());
		System.out.println(message);
	}

	@Order(2)
	@ParameterizedTest
	@CsvSource({"26,3","3,2","2,1","6,1","8,0","1,2"})
	void testContacts(int userId, int contactCount){
		List<User> contacts = userService.getAllContactsForUser(userId);
		contacts.forEach(user -> System.out.println("Contact ID: " + user.getId()));
		Assertions.assertEquals(contactCount, contacts.size());
	}

	@Test
	void testAddToWishlist(){
		Location location = locationService.getLocationById(3);
		boolean added = userService.addToWishlist(15, location);
		Assertions.assertTrue(added);
	}

	@Test
	@Transactional
	void testRemoveFromWishlist(){
		int userId = 15;
		Location location1 = locationService.getLocationById(3);
		Location location2 = locationService.getLocationById(16);
		userService.addToWishlist(userId, location1);
		userService.addToWishlist(userId, location2);
		int expectedSize = userService.getUserById(userId).getWishlist().size() - 1;
		userService.removeFromWishlist(userId,location1);
		int actualSize = userService.getUserById(userId).getWishlist().size();
		Assertions.assertEquals(expectedSize,actualSize);
	}

	@ParameterizedTest
	@CsvSource({"medward4@flavors.me,true","a@b.com,false","e@mail.com,false","cjaulme9@bing.com,true"})
	void testEmailExists(String userEmail, boolean expected){
		boolean actual = userService.checkUserEmailExists(userEmail);
		Assertions.assertEquals(expected,actual);
	}
}
