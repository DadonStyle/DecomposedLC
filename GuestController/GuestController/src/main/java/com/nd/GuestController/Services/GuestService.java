package com.nd.GuestController.Services;

import com.project.luxuryCoupons.beans.Customer;
import com.project.luxuryCoupons.exceptions.LuxuryCouponsException;

public interface GuestService {

    /**
     * Register
     * this method allows a geust to register to the client's system, and adds a customer to DB
     * @param customer - the customer we want to add
     * @throws LuxuryCouponsException if the customer already exist in DB
     * @return Customer instance
     */
    Customer register(Customer customer) throws LuxuryCouponsException;
}
