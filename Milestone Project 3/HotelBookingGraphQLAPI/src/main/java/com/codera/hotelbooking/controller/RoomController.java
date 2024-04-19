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
import com.codera.hotelbooking.model.master.Room;
import com.codera.hotelbooking.service.HotelService;
import com.codera.hotelbooking.service.RoomService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;

@Controller
@Tag(name="RoomController", description="Room Management")
public class RoomController {
    private final RoomService roomService;
    private final HotelService hotelService;
    
    

    @Autowired
    public RoomController(RoomService roomService, HotelService hotelService) {
        this.roomService = roomService;
        this.hotelService=hotelService;
    }

    @Operation(
    		summary="Get Operation",
    		description="Retrieves a list of all rooms"
    		)
 
    @QueryMapping
    public List<Room> getAllRooms() {
        return roomService.getAllRooms();
    }


    @Operation(
    		summary="Get Operation by its ID",
    		description=" Retrieves a specific room by its ID"
    		)
 
    @QueryMapping
    public Optional<Room> getRoomById(@Argument Long id) {
        return roomService.getRoomById(id);
        
    }

    @Operation(
    		summary="Post Operation",
    		description="Creates a new room"
    		)
    @MutationMapping
    public Room createRoom(
    	    @Argument String roomNumber,
    	    @Argument int capacity,
    	    @Argument double price,
    	    @Argument Long hotelId 
    	) {
    	    // Retrieve the hotel entity based on the provided hotelId
    	 Optional<Hotel> optionalHotel = hotelService.getHotelById(hotelId);
    	    if (optionalHotel.isEmpty()) {
    	        throw new IllegalArgumentException("Hotel with id " + hotelId + " not found");
    	    }

    	    // Extract the Hotel object from the Optional
    	    Hotel hotel = optionalHotel.get();

    	    // Create a new room and set its attributes
    	    Room room = new Room();
    	    room.setHotel(hotel);
    	    room.setRoomNumber(roomNumber);
    	    room.setCapacity(capacity);
    	    room.setPrice(price);

    	    // Call the room service to persist the new room
    	    return roomService.createRoom(room);
    }

    @Operation(
    		summary="Put Operation by its ID",
    		description=" Updates an existing room with the specified ID"
    		)
    @MutationMapping
    public Room updateRoom(
            @Argument Long roomId,
            @Argument String roomNumber,
            @Argument int capacity,
            @Argument double price,
            @Argument Long hotelId
    ) {
        // Check if the room exists
        Optional<Room> optionalRoom = roomService.getRoomById(roomId);
        if (optionalRoom.isEmpty()) {
            throw new IllegalArgumentException("Room with id " + roomId + " not found");
        }

        // Check if the hotel exists
        Optional<Hotel> optionalHotel = hotelService.getHotelById(hotelId);
        if (optionalHotel.isEmpty()) {
            throw new IllegalArgumentException("Hotel with id " + hotelId + " not found");
        }

        // Update the room
        Room room = optionalRoom.get();
        room.setRoomNumber(roomNumber);
        room.setCapacity(capacity);
        room.setPrice(price);
        room.setHotel(optionalHotel.get());

        // Save and return the updated room
        return roomService.updateRoom(room);
    }

    
    @Operation(
    		summary="Delete Operation by its ID",
    		description=" Deletes a room with the specified ID"
    		)
    @MutationMapping
    public String deleteRoom(@Argument Long roomId) {
        // Check if the room exists
        Optional<Room> optionalRoom = roomService.getRoomById(roomId);
        if (optionalRoom.isEmpty()) {
            throw new IllegalArgumentException("Room with id " + roomId + " not found");
        }

        // Delete the room
        roomService.deleteRoom(roomId);

        return "Room with id " + roomId + " has been successfully deleted";
    }
    
}

