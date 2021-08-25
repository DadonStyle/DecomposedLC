package com.nd.GuestController.Services;

import com.project.luxuryCoupons.beans.Customer;
import com.project.luxuryCoupons.exceptions.LuxuryCouponsException;
import com.project.luxuryCoupons.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GuestServiceImpl implements com.project.luxuryCoupons.services.GuestService {

    @Autowired
    private CustomerRepository customerRepository;


    /**
     * Register
     * this method adds a customer to DB
     * @param customer - the customer we want to add
     * @throws LuxuryCouponsException - if the customer already exist in DB
     * @return customer object
     */
    @Override
    public Customer register(Customer customer) throws LuxuryCouponsException {
        for (Customer item : customerRepository.findAll()) {
            if (customer.getEmail().equals(item.getEmail())) {
                throw new LuxuryCouponsException("Customer " + customer.getFirstName() + " " + customer.getLastName() + " already exists !");
            }
        }
        customerRepository.save(customer);
        System.out.println("Customer " + customer.getFirstName() + " " + customer.getLastName() + " added to the system");
        return customerRepository.findByEmailAndPassword(customer.getEmail(),customer.getPassword());
    }
}
