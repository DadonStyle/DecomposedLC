package com.nd.CompanyController.Service;

import com.nd.CompanyController.Beans.Category;
import com.nd.CompanyController.Beans.Company;
import com.nd.CompanyController.Beans.Coupon;
import com.nd.CompanyController.Exeptions.CompanyException;
import com.nd.CompanyController.Repositories.CompanyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

/**
 * The Company service impl class
 * exacute 'Company service'
 */
@Service
@SuppressWarnings("all")
public class CompanyServiceImpl implements CompanyService {
    //Fields
    /**
     * Company repository field
     */
    @Autowired
    private CompanyRepo companyRepository;

    @Autowired
    private RestTemplate restTemplate;

    //Methods

//    /**
//     * Login method
//     * checks if allowed to get access and enables the comapny to login to the system and make actions
//     * @param email - the company's email
//     * @param password - the company's password
//     * @return boolean - if the login was successful
//     * @throws CompanyException - if the values provided are incorrect
//     */
//    @Override
//    public boolean login(String email, String password) throws CompanyException {
//        if (companyRepository.findByEmailAndPassword(email, password) == null){
//            throw new CompanyException("Company not found !");
//        }
//        Company company = companyRepository.findByEmailAndPassword(email, password);
//        this.companyId = company.getCompanyId();
//        System.out.println("Logging was successful, welcome Company: " + company.getName());
//        return true;
//    }

    /**
     * Add coupon
     * this method adds coupon to the DB
     * @param coupon -  the coupon we want to add
     * @throws LuxuryCouponsException if the coupon alreadt exist
     * @return Coupon instance
     */
    @Override
    public Coupon addCoupon(Coupon coupon, MultipartFile image) throws CompanyException {
//        for (Coupon item : couponRepository.findAll()) {
//            if (coupon.getTitle().equals(item.getTitle()) && coupon.getCompanyId() == companyId) {
//                throw new LuxuryCouponsException("Title already taken !");
//            }
//        }
        Coupon returnCoupon = restTemplate.postForObject("localhost:8081/coupon", new HttpEntity<>(coupon), Coupon.class,new HashMap<>().put("image", image)); //in coupon rest controller for saving the coupon to the db
        Company company = companyRepository.findByCompanyId(returnCoupon.getCompanyId());
        company.getCoupons().add(returnCoupon);
        companyRepository.saveAndFlush(company);
        System.out.println(returnCoupon.getTitle() + " added to the company " + returnCoupon.getCompanyId());
        return returnCoupon;
    }


    /**
     * Update coupon
     * this method updates the info of an exiting coupon in DB
     * @param coupon- the coupon with the new info for updating
     * @throws LuxuryCouponsException - if the coupon doesn't exist in DB
     */
    @Override
    public Coupon updateCoupon(Coupon coupon) throws CompanyException {
        if (!(couponRepository.existsById(coupon.getCouponId()))){
            throw new LuxuryCouponsException("Coupon not found !");
        } else {
            couponRepository.saveAndFlush(coupon);
        }
        return couponRepository.findByCouponId(coupon.getCouponId());
    }

    /**
     * Delete coupon
     * this method deletes a coupon from DB by id
     * @param couponId - the coupon's id
     * @throws LuxuryCouponsException - if the company doesn't exist in DB
     */
    @Override
    public void deleteCoupon(int couponId) throws CompanyException {
        if (!(couponRepository.existsById(couponId))) {
            throw new LuxuryCouponsException("Coupon id doesn't exist !");
        }
        couponRepository.deleteCompanyCoupon(couponId);
        couponRepository.deleteCustomerCoupon(couponId);
        couponRepository.deleteById(couponId);
        System.out.println("Coupon " + couponId + " deleted successfully!");
    }

    /**
     * Find by coupon id
     * this method finds a coupon by Id
     * @param couponId - the coupon's id
     * @return Coupon instance
     * @throws LuxuryCouponsException - if the coupon doesn't exist in DB
     */
    @Override
    public Coupon findByCouponId(int couponId) throws CompanyException {
        if (!(couponRepository.existsById(couponId))) {
            throw new LuxuryCouponsException("Coupon id doesn't exist !");
        }
        return couponRepository.findByCouponId(couponId);
    }

    /**
     * Get company coupons
     * this method gets all the coupons of a specific company
     * @return List of the company's coupons
     */
    @Override
    public List<Coupon> getCompanyCoupons() {
        return couponRepository.findByCompanyId(companyId);
    }

    /**
     * Get company coupons
     * this method gets all the coupons of a specific company by specific category
     * @return List of the company's coupons in this category
     */
    @Override
    public List<Coupon> getCompanyCoupons(Category category) {
        List<Coupon> coupons = getCompanyCoupons();
        coupons.removeIf(coupon -> !(coupon.getCategory().equals(category)));
        return coupons;
    }

    /**
     * Get company coupons
     * this method gets all the coupons of a specific company under a max price
     * @return List of the company's coupons that are under the max price
     */
    @Override
    public List<Coupon> getCompanyCoupons(double maxPrice) {
        List<Coupon> coupons = getCompanyCoupons();
        coupons.removeIf(coupon -> coupon.getPrice() > maxPrice);
        return coupons;
    }

    /**
     * Get company details
     * @return the company's info
     */
    @Override
    public Company getCompanyDetails() {
        return companyRepository.findByCompanyId(companyId);
    }
}
