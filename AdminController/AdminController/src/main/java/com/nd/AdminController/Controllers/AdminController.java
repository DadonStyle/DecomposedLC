package com.project.luxuryCoupons.controller;

import com.project.luxuryCoupons.beans.Company;
import com.project.luxuryCoupons.beans.Customer;
import com.project.luxuryCoupons.beans.UserDetails;
import com.project.luxuryCoupons.enums.ClientType;
import com.project.luxuryCoupons.exceptions.LuxuryCouponsException;
import com.project.luxuryCoupons.exceptions.TokenExpiredException;
import com.project.luxuryCoupons.services.AdminServiceImpl;
import com.project.luxuryCoupons.utils.JWTutil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Admin controller class
 * Allows to send and get data using the admin service with REST API
 */
//http://localhost:8080/admin
@RestController
@RequestMapping("ADMIN")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@SuppressWarnings("all")
public class AdminController extends ClientController {
    //Fields
    /**
     * Admin service field
     */
    @Autowired
    private AdminServiceImpl adminService;
    /**
     * JWTutil field
     */
    private final JWTutil JWTutil;

    //Methods

    /**
     * Login method
     * checks if allowed to get access and enables the admin to login to the system and make actions
     *
     * @param email    - the admin's email
     * @param password - the admin's password
     * @return ResponseEntity - http response if the login was successful
     */
    //http://localHost:8080/admin/login
    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody UserDetails userDetails) {
        try {
            if (adminService.login(userDetails.getEmail(),userDetails.getPassword())) {
                String myToken = JWTutil.generateToken(new UserDetails(userDetails.getEmail(), ClientType.ADMIN,0));
                return new ResponseEntity<>(myToken, HttpStatus.ACCEPTED);
            }
        } catch (LuxuryCouponsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        return null;
    }

    /**
     * Add comapny
     * this method adds company to the DB
     *
     * @param company- the company we want to add
     * @return response status
     */
    //http://localhost:8080/admin/addCompany
    @PostMapping("addCompany")
    public ResponseEntity<?> addCompany(@RequestHeader(name = "Authorization") String token, @RequestBody Company company) throws TokenExpiredException {
        JWTutil.validateToken(token);
        try {
            return ResponseEntity.ok()
                    .header("Authorization", JWTutil.generateToken(new UserDetails(
                            JWTutil.extractEmail(token),
                            ClientType.ADMIN,0)))
                    .body(adminService.addCompany(company));
        } catch (LuxuryCouponsException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Update company
     * this method updates the info of an exiting company in DB
     *
     * @param company- the company with the new info for updating
     * @return response status
     */
    //http://localhost:8080/company/updateCompany
    @PutMapping("updateCompany")
    public ResponseEntity<?> updateCompany(@RequestHeader(name = "Authorization") String token, @RequestBody Company company) throws TokenExpiredException {
        if (JWTutil.validateToken(token)) {
            try {
                adminService.updateCompany(company);
            } catch (LuxuryCouponsException e) {
                return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
            }
            return ResponseEntity.ok()
                    .header("Authorization", JWTutil.generateToken(new UserDetails(
                            JWTutil.extractEmail(token),
                            ClientType.ADMIN,0)))
                    .body(null);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    /**
     * Delete company
     * this method deletes a comapny from DB by id
     *
     * @param companyId - the company's id
     * @return response status
     */
    //http://localhost:8080/admin/deleteCompany/email
    @DeleteMapping("deleteCompany/{email}")
    public ResponseEntity<?> deleteCompany(@RequestHeader(name = "Authorization") String token, @PathVariable String email) throws TokenExpiredException {
        if (JWTutil.validateToken(token)) {
            try {
                adminService.deleteCompany(email);
            } catch (LuxuryCouponsException e) {
                return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
            }
            return ResponseEntity.ok().header("Authorization", JWTutil.generateToken(new UserDetails(
                    JWTutil.extractEmail(token),
                    ClientType.ADMIN,0))).body(null);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    /**
     * Get all companies
     * this method returns a list of all the comapnies exist in DB
     *
     * @return List of all the comapnies in the DB table and response status
     */
    //http://localhost:8080/admin/getAllCompanies
    @GetMapping("getAllCompanies")
    public ResponseEntity<?> getAllCompanies(@RequestHeader(name = "Authorization") String token) throws TokenExpiredException {
        if (JWTutil.validateToken(token)) {
            return ResponseEntity.ok()
                    .header("Authorization", JWTutil.generateToken(new UserDetails(
                            JWTutil.extractEmail(token),
                            ClientType.ADMIN,0
                    )))
                    .body(adminService.getAllCompanies());
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    /**
     * Find by comapny id
     * this method finds a company by Id
     *
     * @param companyId - the company's id
     * @return Company instance and response status
     */
    //http://localhost:8080/admin/singleCompany/companyId
    @GetMapping("getOneCompany/{companyId}")
    public ResponseEntity<?> getOneCompany(@RequestHeader(name = "Authorization") String token, @PathVariable int companyId) throws TokenExpiredException {
        if (JWTutil.validateToken(token)) {
            try {
                return ResponseEntity.ok()
                        .header("Authorization", JWTutil.generateToken(new UserDetails(
                                JWTutil.extractEmail(token),
                                ClientType.ADMIN,0
                        )))
                        .body(adminService.findByCompanyId(companyId));
            } catch (LuxuryCouponsException e) {
                return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }


    /**
     * Update customer
     * this method will update the info of an existing customer in DB
     * @param customer - the customer we the new info for updating
     * @return response status
     */
    //http://localhost:8080/ADMIN/adminUpdateCustomer
    @PutMapping("adminUpdateCustomer")
    public ResponseEntity<?> updateCustomer(@RequestHeader (name="Authorization") String token, @RequestBody Customer customer) throws TokenExpiredException, LuxuryCouponsException {
        if (JWTutil.validateToken(token)) {
            adminService.updateCustomer(customer);
            return ResponseEntity.ok()
                    .header("Authorization", JWTutil.generateToken(new UserDetails(
                            JWTutil.extractEmail(token),
                            ClientType.ADMIN, customer.getCustomerId())))
                    .body(null);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    /**
     * Delete customer
     * this method deletes a customer from DB by id
     *
     * @param email - the customer's email
     * @return response status
     */
    //http://localHost:8080/admin/deleteCustomer/email
    @DeleteMapping("deleteCustomer/{email}")
    public ResponseEntity<?> deleteCustomer(@RequestHeader(name = "Authorization") String token, @PathVariable String email) throws TokenExpiredException {
        if (JWTutil.validateToken(token)) {
            try {
                adminService.deleteCustomer(email);
            } catch (LuxuryCouponsException e) {
                return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
            }
            return ResponseEntity.ok()
                    .header("Authorization", JWTutil.generateToken(new UserDetails(
                            JWTutil.extractEmail(token),
                            ClientType.ADMIN,0
                    )))
                    .body(null);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    /**
     * Get all customers
     * this method will return a list of all the customers exist in DB
     *
     * @return List of all the customers from the table in DB and response status
     */
    //http://localHost:8080/admin/gatAllCustomers
    @GetMapping("getAllCustomers")
    public ResponseEntity<?> getAllCustomers(@RequestHeader(name = "Authorization") String token) throws TokenExpiredException {
        if(JWTutil.validateToken(token)) {
            return ResponseEntity.ok()
                    .header("Authorization", JWTutil.generateToken(new UserDetails(
                            JWTutil.extractEmail(token),
                            ClientType.ADMIN,0
                    )))
                    .body(adminService.getAllCustomers());
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    /**
     * Find customer by Id
     * this method finds a cusotmer from DB by id
     *
     * @param customerId - the customer's id
     * @return Customer instance and response status
     */
    //http://localHost:8080/admin/getOneCustomer/customerId
    @GetMapping("getOneCustomer/{customerId}")
    public ResponseEntity<?> getOneCustomer(@RequestHeader(name = "Authorization") String token, @PathVariable int customerId) throws TokenExpiredException {
        if (JWTutil.validateToken(token)) {
            try {
                return ResponseEntity.ok()
                        .header("Authorization", JWTutil.generateToken(new UserDetails(
                                JWTutil.extractEmail(token),
                                ClientType.ADMIN,0
                        )))
                        .body(adminService.findByCustomerId(customerId));
            } catch (LuxuryCouponsException e) {
                return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
