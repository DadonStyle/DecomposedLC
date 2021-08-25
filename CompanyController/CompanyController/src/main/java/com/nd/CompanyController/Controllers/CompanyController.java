package com.project.luxuryCoupons.controller;

import com.nd.CompanyController.Beans.Coupon;
import com.nd.CompanyController.Exeptions.CompanyException;
import com.nd.CompanyController.Service.CompanyServiceImpl;
import com.project.luxuryCoupons.beans.Coupon;
import com.project.luxuryCoupons.beans.UserDetails;
import com.project.luxuryCoupons.enums.Category;
import com.project.luxuryCoupons.enums.ClientType;
import com.project.luxuryCoupons.exceptions.LuxuryCouponsException;
import com.project.luxuryCoupons.exceptions.TokenExpiredException;
import com.project.luxuryCoupons.services.CompanyServiceImpl;
import com.project.luxuryCoupons.utils.JWTutil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

/**
 * Comapny controller class
 * Allows to send and get data using the company service with REST API
 */
//http://localHost:8080/company
@RestController
@RequestMapping("COMPANY")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@SuppressWarnings("all")
public class CompanyController extends ClientController {

    @Autowired
    private HttpServletRequest request;

    //Fields
    /**
     * Company service field
     */
    @Autowired
    private CompanyServiceImpl companyService;

    //Methods
//
//    /**
//     * Login method
//     * checks if allowed to get access and enables the comapny to login to the system and make actions
//     *
//     * @param email    - the company's email
//     * @param password - the company's password
//     * @return ResponseEntity - http response if the login was successful
//     */
//    //http://localHost:8080/company/login
//    @PostMapping("login")
//    public ResponseEntity<?> login(@RequestBody UserDetails userDetails) {
//        try {
//            if (companyService.login(userDetails.getEmail(), userDetails.getPassword())) {
//                //go to database, get user by it's email and password
//                String myToken = JWTutil.generateToken(new UserDetails(userDetails.getEmail(), ClientType.COMPANY, companyService.getCompanyId()));
//                return new ResponseEntity<>(myToken, HttpStatus.ACCEPTED);
//            }
//        } catch (LuxuryCouponsException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//        return null;
//    }

    /**
     * Add coupon
     * this method adds coupon to the DB
     * @param coupon-  the coupon we want to add
     * @return response status
     */
    //http://localhost:8080/company/addCoupon
    @PostMapping("addCoupon")
    public ResponseEntity<?> addCoupon(@RequestBody Coupon coupon, @RequestParam MultipartFile image) throws IOException {
            try {
                return ResponseEntity.ok().body(companyService.addCoupon(coupon,image));
            } catch (CompanyException e) {
                return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    /**
     * Update coupon
     * this method updates the info of an exiting coupon in DB
     * @param coupon- the coupon with the new info for updating
     * @return response status
     */
    //http://localhost:8080/company/updateCoupon
    @PutMapping("updateCoupon")
    public ResponseEntity<?> updateCoupon(@RequestHeader (name="Authorization") String token, @RequestBody Coupon coupon) throws TokenExpiredException {
        if (JWTutil.validateToken(token)) {
            try {
                companyService.updateCoupon(coupon);
            } catch (LuxuryCouponsException e) {
                return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
            }
            return ResponseEntity.ok()
                    .header("Authorization", JWTutil.generateToken(new UserDetails(
                            JWTutil.extractEmail(token),
                            ClientType.COMPANY, companyService.getCompanyId())))
                    .body(null);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    /**
     * Delete coupon
     * this method deletes a coupon from DB by id
     * @param couponId - the coupon's id
     * @return response status
     */
    //http://localhost:8080/company/deleteCoupon/couponId
    @DeleteMapping("deleteCoupon/{couponId}")
    public ResponseEntity<?> deleteCoupon(@RequestHeader (name="Authorization") String token,@PathVariable int couponId) throws TokenExpiredException {
        if (JWTutil.validateToken(token)) {
            try {
                companyService.deleteCoupon(couponId);
            } catch (LuxuryCouponsException e) {
                return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
            }
            return ResponseEntity.ok().header("Authorization", JWTutil.generateToken(new UserDetails(
                    JWTutil.extractEmail(token),
                    ClientType.COMPANY, companyService.getCompanyId()))).body(null);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    /**
     * Get company coupons
     * this method gets all the coupons of a specific company
     * @return List of the company's coupons and response status
     */
    //http://localHost:8080/company/getCompanyCoupons
    @GetMapping("getCompanyCoupons")
    public ResponseEntity<?> getCompanyCoupons(@RequestHeader (name="Authorization") String token) throws TokenExpiredException {
        if (JWTutil.validateToken(token)) {
            return ResponseEntity.ok()
                    .header("Authorization", JWTutil.generateToken(new UserDetails(
                            JWTutil.extractEmail(token),
                            ClientType.COMPANY, companyService.getCompanyId()
                    )))
                    .body(companyService.getCompanyCoupons());
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    /**
     * Get company coupons
     * this method gets all the coupons of a specific company by specific category
     * @param category - the category
     * @return List of the company's coupons in this category and response status
     */
    //http://localHost:8080/company/getCoupons/getCompanyCouponsByCategory/category
    @GetMapping("getCompanyCouponsByCategory/{category}")
    public ResponseEntity<?> getCompanyCoupons(@RequestHeader (name="Authorization") String token,@PathVariable Category category) throws TokenExpiredException {
        if (JWTutil.validateToken(token)) {
            return ResponseEntity.ok()
                    .header("Authorization", JWTutil.generateToken(new UserDetails(
                            JWTutil.extractEmail(token),
                            ClientType.COMPANY, companyService.getCompanyId()
                    )))
                    .body(companyService.getCompanyCoupons(category));
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    /**
     * Get company coupons
     * this method gets all the coupons of a specific company under a max price
     * @param maxPrice -the maximum price
     * @return List of the company's coupons that are under the max price and response status
     */
    //http://localHost:8080/company/getCompanyCouponsByPrice/maxPrice
    @GetMapping("getCompanyCouponsByPrice/{maxPrice}")
    public ResponseEntity<?> getCompanyCoupons(@RequestHeader (name="Authorization") String token,@PathVariable double maxPrice) throws TokenExpiredException {
        if (JWTutil.validateToken(token)) {
            return ResponseEntity.ok()
                    .header("Authorization", JWTutil.generateToken(new UserDetails(
                            JWTutil.extractEmail(token),
                            ClientType.COMPANY, companyService.getCompanyId()
                    )))
                    .body(companyService.getCompanyCoupons(maxPrice));
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    /**
     * Company details
     * @return the company's info and response status
     */
    //http://localHost:8080/company/companyDetails
    @GetMapping("companyDetails")
    public ResponseEntity<?> getCompanyDetails(@RequestHeader (name="Authorization") String token) throws TokenExpiredException {
        if (JWTutil.validateToken(token)) {
            return ResponseEntity.ok()
                    .header("Authorization", JWTutil.generateToken(new UserDetails(
                            JWTutil.extractEmail(token),
                            ClientType.COMPANY, companyService.getCompanyId()
                    )))
                    .body(companyService.getCompanyDetails());
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
