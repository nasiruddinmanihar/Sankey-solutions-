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
import com.codera.hotelbooking.model.master.Hotel;
import com.codera.hotelbooking.service.HotelService;



import java.util.List;
import java.util.Optional;


@Controller
public class HotelController {
    private final HotelService hotelService;

    @Autowired
    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

   
    @QueryMapping
    public List<Hotel> getAllHotels() {
        return hotelService.getAllHotels();
    }


 

    @QueryMapping
    public Optional<Hotel> getHotelById(@Argument Long id) {
        return hotelService.getHotelById(id);
        
    }

    @MutationMapping
    public Hotel createHotel(@Argument String name, @Argument String location) {
    	Hotel hotel = new Hotel();
        hotel.setName(name);
        hotel.setLocation(location);
        
        return hotelService.createHotel(hotel);
    }
    
    
 
    @MutationMapping
    public Hotel updateHotel(
    	    @Argument Long hotelId,
    	    @Argument String name,
    	    @Argument String location
    	) {
    	    // Retrieve the hotel entity based on the provided hotelId
    	    Optional<Hotel> optionalHotel = hotelService.getHotelById(hotelId);
    	    if (optionalHotel.isEmpty()) {
    	        throw new IllegalArgumentException("Hotel with id " + hotelId + " not found");
    	    }

    	    // Extract the Hotel object from the Optional
    	    Hotel hotel = optionalHotel.get();

    	    // Update the hotel attributes
    	    hotel.setName(name);
    	    hotel.setLocation(location);

    	    // Call the hotel service to update the hotel
    	    return hotelService.updateHotel(hotel);
    	}
    
    
   
    @MutationMapping
    public String deleteHotel(@Argument Long hotelId) {
        // Check if the hotel exists
        Optional<Hotel> optionalHotel = hotelService.getHotelById(hotelId);
        if (optionalHotel.isEmpty()) {
            throw new IllegalArgumentException("Hotel with id " + hotelId + " not found");
        }

        // Delete the hotel
        hotelService.deleteHotel(hotelId);

        return "Hotel with id " + hotelId + " has been successfully deleted";
    }
}

