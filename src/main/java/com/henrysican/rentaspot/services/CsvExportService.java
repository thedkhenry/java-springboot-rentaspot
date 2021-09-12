package com.henrysican.rentaspot.services;

import com.henrysican.rentaspot.models.Address;
import com.henrysican.rentaspot.models.Booking;
import com.henrysican.rentaspot.models.Location;
import com.henrysican.rentaspot.models.User;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

@Service
public class CsvExportService {
    private final BookingService bookingService;
    private final LocationService locationService;
    private final AddressService addressService;
    private final UserService userService;

    public CsvExportService(BookingService bookingRepo,
                            LocationService locationService,
                            AddressService addressService,
                            UserService userService){
        this.bookingService = bookingRepo;
        this.locationService = locationService;
        this.addressService = addressService;
        this.userService = userService;
    }

    /**
     * Writes a location's booking records to a csv file. The fields printed are:
     * "Price","Start Date","End Date","Status","Cars","Firstname","Email","Address","City","Zip","Date Created"
     * @param writer        the writer used to create the CSVPrinter
     * @param locationId    the ID of the location
     */
    public void writeBookingsToCsv(Writer writer, int locationId){
        List<Booking> bookings = bookingService.getAllBookingsForLocation(locationId);
        try {
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);
            csvPrinter.printRecord("Price","Start Date","End Date","Status","Cars","Firstname","Email","Address","City","Zip","Date Created");
            for(Booking booking : bookings){
                csvPrinter.printRecord(booking.getPrice(), booking.getStartDate(),booking.getEndDate(),booking.getBookingStatus(),booking.getCars(),
                        booking.getCustomer().getFirstName(),booking.getCustomer().getEmail(),
                        booking.getLocation().getAddress().getAddress1(),booking.getLocation().getAddress().getCity(),booking.getLocation().getAddress().getZipCode(),
                        booking.getCreatedAt());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes all booking records to a csv file. The fields printed are:
     * "ID","Start Date","End Date","Price","Cars","Status","Host Email","Customer Email","Location Address","Reviewed","Date Created"
     * @param writer        the writer used to create the CSVPrinter
     */
    public void writeBookingsToCsv(Writer writer){
        List<Booking> bookings = bookingService.getAllBookings();
        try {
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);
            csvPrinter.printRecord("ID","Start Date","End Date","Price","Cars","Status",
                    "Host Email","Customer Email","Location Address","Reviewed","Date Created");
            for(Booking booking : bookings){
                csvPrinter.printRecord(booking.getId(), booking.getStartDate(),booking.getEndDate(), booking.getPrice(),booking.getCars(),booking.getBookingStatus(),
                        booking.getHost().getEmail(),booking.getCustomer().getEmail(),
                        booking.getLocation().getAddress().getFullAddress(), booking.isHasReview(), booking.getCreatedAt());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes all location records to a csv file. The fields printed are:
     * "ID","Title","Cars","Price","Active","Reviews","Bookings","User","Date Created"
     * @param writer        the writer used to create the CSVPrinter
     */
    public void writeLocationsToCsv(Writer writer){
        List<Location> locations = locationService.getAllLocations();
        try {
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);
            csvPrinter.printRecord("ID","Title","Cars","Price","Active","Reviews","Bookings","User","Date Created");
            for(Location location : locations){
                csvPrinter.printRecord(location.getId(), location.getTitle(),location.getTotalOccupancy(),location.getPrice(),location.isActive(),
                        location.getReviews().size(),location.getBookings().size(),
                        location.getUser().getEmail(),location.getCreatedAt());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes all address records to a csv file. The fields printed are:
     * "ID","Address","City","State","Country","Zip","Latitude","Longitude"
     * @param writer        the writer used to create the CSVPrinter
     */
    public void writeAddressesToCsv(Writer writer) {
        List<Address> addresses = addressService.getAddresses();
        try {
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);
            csvPrinter.printRecord("ID","Address","City","State","Country","Zip","Latitude","Longitude");
            for(Address address : addresses){
                csvPrinter.printRecord(address.getId(), address.getAddress1(),
                        address.getCity(),address.getState(),address.getCountry(),address.getZipCode(),
                        address.getLatitude(),address.getLongitude());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes all user records to a csv file. The fields printed are:
     * "ID","First","Last","Email","Phone","Image","Host","Date Created"
     * @param writer        the writer used to create the CSVPrinter
     */
    public void writeUsersToCsv(Writer writer) {
        List<User> users = userService.getAllUsers();
        try {
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);
            csvPrinter.printRecord("ID","First","Last","Email","Phone","Image","Host","Date Created");
            for(User user : users){
                csvPrinter.printRecord(user.getId(), user.getFirstName(),user.getLastName(),
                        user.getEmail(),user.getPhoneNumber(),user.getProfileImage().getName(),
                        user.isHost(),user.getCreatedAt());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes all user profile image records to a csv file. The fields printed are:
     * "ID","Name","Type","User"
     * @param writer        the writer used to create the CSVPrinter
     */
    public void writeUsersImagesToCsv(Writer writer) {
        List<User> users = userService.getAllUsersWithImages();
        try {
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);
            csvPrinter.printRecord("ID","Name","Type","User");
            for(User user : users){
                csvPrinter.printRecord(user.getProfileImage().getId(),user.getProfileImage().getName(),user.getProfileImage().getType(),user.getEmail());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
