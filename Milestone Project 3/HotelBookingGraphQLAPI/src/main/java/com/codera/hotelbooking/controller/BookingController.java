package com.codera.hotelbooking.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.codera.hotelbooking.model.authentication.User;
import com.codera.hotelbooking.model.master.Booking;
import com.codera.hotelbooking.model.master.Room;
import com.codera.hotelbooking.service.BookingService;
import com.codera.hotelbooking.service.RoomService;
import com.codera.hotelbooking.service.UserService;



import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Controller
@Tag(name="BookingController", description="Booking Management")
public class BookingController {
    private final BookingService bookingService;
    private final UserService userService;
    private final RoomService roomService;
    
    

    @Autowired
    public BookingController(BookingService bookingService, UserService userService, RoomService roomService ) {
        this.bookingService = bookingService;
        this.userService=userService;
        this.roomService=roomService;
    }


 
    @QueryMapping
    public List<Booking> getAllBookings(){
        return bookingService.getAllBookings();
    }
    

 
 
    @QueryMapping
    public Optional<Booking> getBookingById(@Argument Long id) {
        return bookingService.getBookingById(id);
        
    }

   
    @MutationMapping
    public Booking createBooking(
    	    @Argument Long userId,
    	    @Argument Long roomId,
    	    @Argument LocalDateTime checkInDate,
    	    @Argument LocalDateTime checkOutDate
    	) {
    	    // Retrieve the user entity based on the provided userId
    	    Optional<User> optionalUser = userService.getUserById(userId);
    	    if (optionalUser.isEmpty()) {
    	        throw new IllegalArgumentException("User with id " + userId + " not found");
    	    }

    	    // Retrieve the room entity based on the provided roomId
    	    Optional<Room> optionalRoom = roomService.getRoomById(roomId);
    	    if (optionalRoom.isEmpty()) {
    	        throw new IllegalArgumentException("Room with id " + roomId + " not found");
    	    }

    	    // Extract the User and Room objects from the Optional
    	    User user = optionalUser.get();
    	    Room room = optionalRoom.get();

    	    // Create a new booking and set its attributes
    	    Booking booking = new Booking();
    	    booking.setUser(user);
    	    booking.setRoom(room);
    	    booking.setCheckInDate(checkInDate);
    	    booking.setCheckOutDate(checkOutDate);

    	    // Call the booking service to persist the new booking
    	    return bookingService.createBooking(booking);
    	}

    
    @MutationMapping
    public Booking updateBooking(
            @Argument Long bookingId,
            @Argument Long userId,
            @Argument Long roomId,
            @Argument LocalDateTime checkInDate,
            @Argument LocalDateTime checkOutDate
    ) {
        // Check if the booking exists
        Optional<Booking> optionalBooking = bookingService.getBookingById(bookingId);
        if (optionalBooking.isEmpty()) {
            throw new IllegalArgumentException("Booking with id " + bookingId + " not found");
        }

        // Check if the user exists
        Optional<User> optionalUser = userService.getUserById(userId);
        if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException("User with id " + userId + " not found");
        }

        // Check if the room exists
        Optional<Room> optionalRoom = roomService.getRoomById(roomId);
        if (optionalRoom.isEmpty()) {
            throw new IllegalArgumentException("Room with id " + roomId + " not found");
        }

        // Update the booking
        Booking booking = optionalBooking.get();
        booking.setUser(optionalUser.get());
        booking.setRoom(optionalRoom.get());
        booking.setCheckInDate(checkInDate);
        booking.setCheckOutDate(checkOutDate);

        // Save and return the updated booking
        return bookingService.updateBooking(booking);
    }

   
    @MutationMapping
    public String deleteBooking(@Argument Long bookingId) {
        // Check if the booking exists
        Optional<Booking> optionalBooking = bookingService.getBookingById(bookingId);
        if (optionalBooking.isEmpty()) {
            throw new IllegalArgumentException("Booking with id " + bookingId + " not found");
        }

        // Delete the booking
        bookingService.deleteBooking(bookingId);

        return "Booking with id " + bookingId + " has been successfully deleted";
    }
}

