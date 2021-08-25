package com.nd.CustomerController.Service;

import com.nd.CustomerController.Beans.Category;
import com.nd.CustomerController.Beans.Coupon;
import com.nd.CustomerController.Beans.Customer;
import com.nd.CustomerController.Exeptions.CustomerControllerException;

import java.util.List;

/**
 * The Customer service interface
 * indicates which methods we will implement in the 'Customer service impl' class
 */
public interface CustomerService {
    /**
     * Login method
     * checks if allowed to get access and enables the customer to login to the system and make actions
     * @param email - the customer's email
     * @param password - the customer's password
     * @return boolean - if the login was successful
     * @throws CustomerControllerException - if the values provided are incorrect
     */
    boolean login(String email, String password) throws CustomerControllerException;

    /**
     * Update customer
     * this method will update the info of an existing customer in DB
     * @param customer - the customer we the new info for updating
     * @throws CustomerControllerException if the customer doesn't exist in DB
     */
    void updateCustomer(Customer customer) throws CustomerControllerException;

    /**
     * Add coupon purchase
     * this method will chcek if the purchasing matches all the conditions, and makes the purchase if possible
     * @param coupon - the coupon
     * @throws CustomerControllerException - throws an error based on what condition wasn't right
     */
    void addCouponPurchase(Coupon coupon) throws CustomerControllerException;

    /**
     * Get customer coupons
     * this method gets all the coupons of a specific customer
     * @return List of the customer's coupons
     */
    List<Coupon> getCustomerCoupons();

    /**
     * Get customer coupons
     * this method gets all the coupons of a specific customer by specific category
     * @param category - the category
     * @return List of the customer's coupons in this category
     */
    List<Coupon> getCustomerCoupons(Category category);

    /**
     * Get customer coupons
     * this method gets all the coupons of a specific customer under a max price
     * @param maxPrice - the maxPrice
     * @return List of the customer's coupons that are under the max price
     */
    List<Coupon> getCustomerCoupons(double maxPrice);

    /**
     * Get customer details
     * @return the customer's info
     */
    Customer getCustomerDetails();
}
