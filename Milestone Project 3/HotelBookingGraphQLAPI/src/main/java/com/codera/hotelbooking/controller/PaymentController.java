package com.codera.hotelbooking.controller;

import com.codera.hotelbooking.model.authentication.User;
import com.codera.hotelbooking.model.master.Booking;
import com.codera.hotelbooking.model.master.Room;
import com.codera.hotelbooking.model.transactional.Payment;
import com.codera.hotelbooking.service.BookingService;
import com.codera.hotelbooking.service.PaymentService;

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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
public class PaymentController {

    private final PaymentService paymentService;
    private final BookingService bookingService;

    @Autowired
    public PaymentController(PaymentService paymentService, BookingService bookingService) {
        this.paymentService = paymentService;
        this.bookingService=bookingService;
    }

 
   
    @QueryMapping
    public List<Payment> getAllPayments() {
        return paymentService.getAllPayments();
    }

   
   
    @QueryMapping
    public Optional<Payment> getPaymentById(@Argument Long id) {
        return paymentService.getPaymentById(id);
        
    }

  
    @MutationMapping
    public Payment createPayment(
    	    @Argument Long bookingId,
    	    @Argument double amount,
    	    @Argument LocalDateTime paymentDate,
    	    @Argument String paymentMethod
    	) {
    	    // Retrieve the booking entity based on the provided bookingId
    	    Optional<Booking> optionalBooking = bookingService.getBookingById(bookingId);
    	    if (optionalBooking.isEmpty()) {
    	        throw new IllegalArgumentException("Booking with id " + bookingId + " not found");
    	    }

    	    // Extract the Booking object from the Optional
    	    Booking booking = optionalBooking.get();

    	    // Create a new payment object and set its attributes
    	    Payment payment = new Payment();
    	    payment.setBooking(booking);
    	    payment.setAmount(amount);
    	    payment.setPaymentDate(paymentDate);
    	    payment.setPaymentMethod(paymentMethod);

    	    // Call the payment service to persist the new payment
    	    return paymentService.createPayment(payment);
    }


    @MutationMapping
    public Payment updatePayment(
            @Argument Long paymentId,
            @Argument Long bookingId,
            @Argument double amount,
            @Argument LocalDateTime paymentDate,
            @Argument String paymentMethod
    ) {
        // Check if the payment exists
        Optional<Payment> optionalPayment = paymentService.getPaymentById(paymentId);
        if (optionalPayment.isEmpty()) {
            throw new IllegalArgumentException("Payment with id " + paymentId + " not found");
        }

        // Check if the booking exists
        Optional<Booking> optionalBooking = bookingService.getBookingById(bookingId);
        if (optionalBooking.isEmpty()) {
            throw new IllegalArgumentException("Booking with id " + bookingId + " not found");
        }

        // Update the payment
        Payment payment = optionalPayment.get();
        payment.setBooking(optionalBooking.get());
        payment.setAmount(amount);
        payment.setPaymentDate(paymentDate);
        payment.setPaymentMethod(paymentMethod);

        // Save and return the updated payment
        return paymentService.updatePayment(payment);
    }
    
  
    @MutationMapping
    public String deletePayment(@Argument Long paymentId) {
        // Check if the payment exists
        Optional<Payment> optionalPayment = paymentService.getPaymentById(paymentId);
        if (optionalPayment.isEmpty()) {
            throw new IllegalArgumentException("Payment with id " + paymentId + " not found");
        }

        // Delete the payment
        paymentService.deletePayment(paymentId);

        return "Payment with id " + paymentId + " has been successfully deleted";
    }
}
