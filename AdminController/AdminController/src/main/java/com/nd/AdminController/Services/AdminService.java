package com.project.luxuryCoupons.services;

import com.project.luxuryCoupons.beans.Company;
import com.project.luxuryCoupons.beans.Customer;
import com.project.luxuryCoupons.exceptions.LuxuryCouponsException;

import java.util.List;

/**
 * The admin service interface
 * indicates which methods we will implement in the admin service impl class
 */
public interface AdminService {
    /**
     * Login method
     * checks if allowed to get access and enables the admin to login to the system and make actions
     * @param email - the admin's email
     * @param password - the admin's password
     * @return boolean - if the login was successful
     * @throws LuxuryCouponsException if the values provided are incorrect
     */
    boolean login(String email, String password) throws LuxuryCouponsException;

    /**
     * Add comapny
     * this method adds company to the DB
     * @param company-  the company we want to add
     * @throws LuxuryCouponsException if the company alreadt exist
     */
    Company addCompany(Company company) throws LuxuryCouponsException;

    /**
     * Update company
     * this method updates the info of an exiting company in DB
     * @param company- the company with the new info for updating
     * @throws LuxuryCouponsException if the company doesn't exist in DB
     */
    Company updateCompany(Company company) throws LuxuryCouponsException;

    /**
     * Delete company
     * this method deletes a comapny from DB by email
     * @param email - the company's email
     * @throws LuxuryCouponsException if the company doesn't exist in DB
     */
    void deleteCompany(String email) throws LuxuryCouponsException;

    /**
     * Get all companies
     * this method returns a list of all the comapnies exist in DB
     * @return List of all the comapnies in the DB table
     */
    List<Company> getAllCompanies();

    /**
     * Find by comapny id
     * this method finds a company by Id
     * @param companyId - the company's id
     * @return Company instance
     * @throws LuxuryCouponsException if the company doesn't exist in DB
     */
    Company findByCompanyId(int companyId) throws LuxuryCouponsException;

    /**
     * Update customer
     * this method will update the info of an existing customer in DB
     * @param customer - the customer we the new info for updating
     * @throws LuxuryCouponsException if the customer doesn't exist in DB
     */
    void updateCustomer(Customer customer) throws LuxuryCouponsException;

    /**
     * Delete customer
     * this method deletes a customer from DB by email
     * @param email - the customer's email
     * @throws LuxuryCouponsException if the customer doesn't exist in DB
     */
    void deleteCustomer(String email) throws LuxuryCouponsException;

    /**
     * Get all customers
     * this method will return a list of all the customers exist in DB
     * @return List of all the customers from the table in DB
     */
    List<Customer> getAllCustomers();

    /**
     * Find customer by Id
     * this method finds a cusotmer from DB by id
     * @param customerId - the customer's id
     * @return Customer instance
     * @throws LuxuryCouponsException if the customer doesn't exist in DB
     */
    Customer findByCustomerId(int customerId) throws LuxuryCouponsException;
}
