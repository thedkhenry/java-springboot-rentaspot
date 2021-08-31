package com.henrysican.rentaspot.services;

import com.henrysican.rentaspot.dao.BookingRepo;
import com.henrysican.rentaspot.models.Booking;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

@Service
public class CsvExportService {
    private final BookingRepo bookingRepo;

    public CsvExportService(BookingRepo bookingRepo){
        this.bookingRepo = bookingRepo;
    }

    public void writeBookingToCsv(Writer writer, int locationId){
        List<Booking> bookings = bookingRepo.findAllByLocation_Id(locationId);
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
}
