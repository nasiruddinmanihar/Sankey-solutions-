package com.codera.hotelbooking.controller;

import com.codera.hotelbooking.model.authentication.User;
import com.codera.hotelbooking.model.master.Booking;
import com.codera.hotelbooking.model.transactional.BookingDetails;
import com.codera.hotelbooking.service.BookingDetailsService;
import com.codera.hotelbooking.service.BookingService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class BookingDetailsController {

    private final BookingDetailsService bookingDetailsService;
    private final BookingService bookingService;

    @Autowired
    public BookingDetailsController(BookingDetailsService bookingDetailsService, BookingService bookingService) {
        this.bookingDetailsService = bookingDetailsService;
        this.bookingService=bookingService;
    }

   
    @QueryMapping
    public List<BookingDetails> getAllBookingDetails() {
        return bookingDetailsService.getAllBookingDetails();
    }

 
   
    @QueryMapping
    public Optional<BookingDetails> getBookingDetailsById(@Argument Long id) {
        return bookingDetailsService.getBookingDetailsById(id);
        
    }

    
   
    @MutationMapping
    public BookingDetails createBookingDetails(
    	    @Argument Long bookingId,
    	    @Argument String guestName,
    	    @Argument String guestEmail
    	) {
    	    // Retrieve the booking entity based on the provided bookingId
    	    Optional<Booking> optionalBooking = bookingService.getBookingById(bookingId);
    	    if (optionalBooking.isEmpty()) {
    	        throw new IllegalArgumentException("Booking with id " + bookingId + " not found");
    	    }

    	    // Extract the Booking object from the Optional
    	    Booking booking = optionalBooking.get();

    	    // Create a new booking details object and set its attributes
    	    BookingDetails bookingDetails = new BookingDetails();
    	    bookingDetails.setBooking(booking);
    	    bookingDetails.setGuestName(guestName);
    	    bookingDetails.setGuestEmail(guestEmail);

    	    // Call the booking details service to persist the new booking details
    	    return bookingDetailsService.createBookingDetails(bookingDetails);
    	}

    @MutationMapping
    public BookingDetails updateBookingDetails(
            @Argument Long bookingDetailsId,
            @Argument Long bookingId,
            @Argument String guestName,
            @Argument String guestEmail
    ) {
        // Check if the booking details exist
        Optional<BookingDetails> optionalBookingDetails = bookingDetailsService.getBookingDetailsById(bookingDetailsId);
        if (optionalBookingDetails.isEmpty()) {
            throw new IllegalArgumentException("Booking details with id " + bookingDetailsId + " not found");
        }

        // Check if the booking exists
        Optional<Booking> optionalBooking = bookingService.getBookingById(bookingId);
        if (optionalBooking.isEmpty()) {
            throw new IllegalArgumentException("Booking with id " + bookingId + " not found");
        }

        // Update the booking details
        BookingDetails bookingDetails = optionalBookingDetails.get();
        bookingDetails.setBooking(optionalBooking.get());
        bookingDetails.setGuestName(guestName);
        bookingDetails.setGuestEmail(guestEmail);

        // Save and return the updated booking details
        return bookingDetailsService.updateBookingDetails(bookingDetails);
    }

   
    @MutationMapping
    public String deleteBookingDetails(@Argument Long bookingDetailsId) {
        // Check if the booking details exists
        Optional<BookingDetails> optionalBookingDetails = bookingDetailsService.getBookingDetailsById(bookingDetailsId);
        if (optionalBookingDetails.isEmpty()) {
            throw new IllegalArgumentException("Booking details with id " + bookingDetailsId + " not found");
        }

        // Delete the booking details
        bookingDetailsService.deleteBookingDetails(bookingDetailsId);

        return "Booking details with id " + bookingDetailsId + " has been successfully deleted";
    }
}
