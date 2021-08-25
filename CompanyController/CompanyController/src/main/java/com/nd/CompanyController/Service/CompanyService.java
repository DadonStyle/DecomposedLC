package com.nd.CompanyController.Service;


import com.nd.CompanyController.Beans.Category;
import com.nd.CompanyController.Beans.Company;
import com.nd.CompanyController.Beans.Coupon;
import com.nd.CompanyController.Exeptions.CompanyException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * The Company service interface
 * indicates which methods we will implement in the 'Comapny service impl' class
 */
@SuppressWarnings("all")
public interface CompanyService {
    /**
     * Login method
     * checks if allowed to get access and enables the comapny to login to the system and make actions
     * @param email - the company's email
     * @param password - the company's password
     * @return boolean - if the login was successful
     * @throws LuxuryCouponsException if the values provided are incorrect
     */
    boolean login(String email, String password) throws CompanyException;

    /**
     * Add coupon
     * this method adds coupon to the DB
     * @param coupon -  the coupon we want to add
     * @throws LuxuryCouponsException if the coupon alreadt exist
     * @return Coupon instance
     */
    Coupon addCoupon(Coupon coupon, MultipartFile image) throws CompanyException;


    /**
     * Update coupon
     * this method updates the info of an exiting coupon in DB
     * @param coupon- the coupon with the new info for updating
     * @throws LuxuryCouponsException if the coupon doesn't exist in DB
     */
    Coupon updateCoupon(Coupon coupon) throws CompanyException;

    /**
     * Delete coupon
     * this method deletes a coupon from DB by id
     * @param couponId - the coupon's id
     * @throws LuxuryCouponsException if the company doesn't exist in DB
     */
    void deleteCoupon(int couponId) throws CompanyException;

    /**
     * Find by coupon id
     * this method finds a coupon by Id
     * @param couponId - the coupon's id
     * @return Coupon instance
     * @throws LuxuryCouponsException if the coupon doesn't exist in DB
     */
    Coupon findByCouponId(int couponId) throws CompanyException;

    /**
     * Get company coupons
     * this method gets all the coupons of a specific company
     * @return List of the company's coupons
     */
    List<Coupon> getCompanyCoupons();

    /**
     * Get company coupons
     * this method gets all the coupons of a specific company by specific category
     * @param category - the category type
     * @return List of the company's coupons in this category
     */
    List<Coupon> getCompanyCoupons(Category category);

    /**
     * Get company coupons
     * this method gets all the coupons of a specific company under a max price
     * @param maxPrice - the maxPrice value
     * @return List of the company's coupons that are under the max price
     */
    List<Coupon> getCompanyCoupons(double maxPrice);

    /**
     * Get company details
     * @return the company's info
     */
    Company getCompanyDetails();
}
