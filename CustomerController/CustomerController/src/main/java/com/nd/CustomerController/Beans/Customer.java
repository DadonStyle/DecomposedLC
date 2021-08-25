package com.nd.CustomerController.Beans;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The Customer class
 * represent the customer information managed by the application
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Customer {
    //Fields
    /**
     * Customer id field
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private int customerId;
    /**
     * first name - field
     */
    private String firstName;
    /**
     * last name - field
     */
    private String lastName;
    /**
     * email - field
     */
    private String email;
    /**
     * password - field
     */
    private String password;
    /**
     * List of coupons field
     */
    @OneToMany(cascade = CascadeType.ALL)
    @Singular
    private List<Coupon> coupons = new ArrayList<>();

    //Methods
    /**
     * To string method
     *
     * @return String - returns the customer details for display
     */
    @Override
    public String toString() {
        return "Customer{" +
                "customer id: " + customerId + "|" +
                " first name: " + firstName + "|" +
                " last name: " + lastName + "|" +
                " email: " + email + "|" +
                " password: " + password + "|" +
                " coupons: \n" + coupons +
                '}';
    }
}

