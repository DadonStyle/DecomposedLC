package com.project.luxuryCoupons.services;

import com.project.luxuryCoupons.beans.Company;
import com.project.luxuryCoupons.beans.Customer;
import com.project.luxuryCoupons.exceptions.LuxuryCouponsException;
import com.project.luxuryCoupons.repository.CompanyRepository;
import com.project.luxuryCoupons.repository.CouponRepository;
import com.project.luxuryCoupons.repository.CustomerRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.util.List;

/**
 * The Admin service impl class
 * exacute 'Admin service'
 */
@Service
@Data
@SuppressWarnings("all")
public class AdminServiceImpl extends ClientService implements AdminService {
    //Fields
    /**
     * Comapny repository field
     */
    @Autowired
    @SuppressWarnings("UnusedDeclaration")
    private CompanyRepository companyRepository;
    /**
     * Customer repository field
     */
    @Autowired
    @SuppressWarnings("UnusedDeclaration")
    private CustomerRepository customerRepository;
    /**
     * Coupon repository field
     */
    @Autowired
    @SuppressWarnings("UnusedDeclaration")
    private CouponRepository couponRepository;

    //Methods

    /**
     * Login method
     * checks if allowed to get access and enables the admin to login to the system and make actions
     * @param email - the admin's email
     * @param password - the admin's password
     * @return boolean - if the login was successful
     * @throws LuxuryCouponsException if the values provided are incorrect
     */
    @Override
    public boolean login(String email, String password) throws LuxuryCouponsException {
        String EMAIL = "admin@admin.com";
        String PASSWORD = "admin";
        if (!(email.equals(EMAIL) && password.equals(PASSWORD))){
            throw new LuxuryCouponsException("Incorrect email or passwsord !");
        } else {
            System.out.println("Logging was successful, welcome Admin");
            return true;
        }
    }

    /**
     * Add comapny
     * this method adds company to the DB
     * @param company-  the company we want to add
     * @throws LuxuryCouponsException if the company already exist
     */
    @Override
    @CrossOrigin
    public Company addCompany(Company company) throws LuxuryCouponsException {
        for (Company item : companyRepository.findAll()) {
            if (company.getEmail().equals(item.getEmail()) || item.getName().equals(company.getName())) {
                throw new LuxuryCouponsException("Company " + company.getName() + " already exists !");
            }
        }
        companyRepository.save(company);
        System.out.println("Company " + company.getName() + " added to the system");
        return companyRepository.findByEmailAndPassword(company.getEmail(),company.getPassword());
    }

    /**
     * Update company
     * this method updates the info of an exiting company in DB
     * @param company- the company with the new info for updating
     * @throws LuxuryCouponsException - if the company doesn't exist in DB
     */
    @Override
    public Company updateCompany(Company company) throws LuxuryCouponsException {
        if(!(companyRepository.existsById(company.getCompanyId()))){
            throw new LuxuryCouponsException("Company not found !");
        } else {
            company.setCompanyId(company.getCompanyId());
            companyRepository.saveAndFlush(company);
        }
        return companyRepository.findByEmailAndPassword(company.getEmail(),company.getPassword());
    }

    /**
     * Delete company
     * this method deletes a comapny from DB by email
     * @param email - the company's email
     * @throws LuxuryCouponsException - if the company doesn't exist in DB
     */
    @Override
    public void deleteCompany(String email) throws LuxuryCouponsException {
        int companyId=companyRepository.findByEmail(email).getCompanyId();
        if (!(companyRepository.existsById(companyId))) {
            throw new LuxuryCouponsException("Company id doesn't exist !");
        }
        companyRepository.getOne(companyId).getCoupons().forEach(item -> couponRepository.deleteCompanyCoupon(item.getCouponId()));
        companyRepository.deleteById(companyId);
        System.out.println("Company " + companyId + " deleted successfully!");
    }


    /**
     * Get all companies
     * this method returns a list of all the comapnies exist in DB
     * @return List of all the comapnies in the DB table
     */
    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    /**
     * Find by comapny id
     * this method finds a company by Id
     * @param companyId - the company's id
     * @return Company instance
     * @throws LuxuryCouponsException - if the company doesn't exist in DB
     */
    @Override
    public Company findByCompanyId(int companyId) throws LuxuryCouponsException {
        if (!(companyRepository.existsById(companyId))) {
            throw new LuxuryCouponsException("Company id doesn't exist !");
        }
        return companyRepository.findByCompanyId(companyId);
    }

    /**
     * Update customer
     * this method will update the info of an existing customer in DB
     * @param customer - the customer we the new info for updating
     * @throws LuxuryCouponsException - if the customer doesn't exist in DB
     */
    @Override
    public void updateCustomer(Customer customer) throws LuxuryCouponsException {
        if (!(customerRepository.existsById(customer.getCustomerId()))){
            throw new LuxuryCouponsException("Customer not found");
        } else {
            customer.setCustomerId(customer.getCustomerId());
            customerRepository.saveAndFlush(customer);
        }
    }

    /**
     * Delete customer
     * this method deletes a customer from DB by id
     * @param email - the customer's email
     * @throws LuxuryCouponsException - if the customer doesn't exist in DB
     */
    @Override
    public void deleteCustomer(String email) throws LuxuryCouponsException {
        int customerId=customerRepository.findByEmail(email).getCustomerId();
        if (!(customerRepository.existsById(customerId))) {
            throw new LuxuryCouponsException("Customer doesn't exist !");
        }
        customerRepository.getOne(customerId).getCoupons().forEach(item -> couponRepository.deleteCustomerCoupon(item.getCouponId()));
        customerRepository.deleteById(customerId);
        System.out.println("Customer " + customerId + " deleted successfully!");
    }

    /**
     * Get all customers
     * this method will return a list of all the customers exist in DB
     * @return List of all the customers from the table in DB
     */
    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();    }

    /**
     * Find customer by Id
     * this method finds a cusotmer from DB by id
     * @param customerId - the customer's id
     * @return Customer instance
     * @throws LuxuryCouponsException - if the customer doesn't exist in DB
     */
    @Override
    public Customer findByCustomerId(int customerId) throws LuxuryCouponsException {
        if (!(customerRepository.existsById(customerId))) {
            throw new LuxuryCouponsException("Customer id doesn't exist !");
        }
        return customerRepository.findByCustomerId(customerId);
    }
}
