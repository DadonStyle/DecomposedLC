package com.nd.DailyThread.Thread;

import com.project.luxuryCoupons.beans.Coupon;
import com.project.luxuryCoupons.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * The Coupon expiration daily job class
 * is a schedule task that runs in the background and deletes expired coupons every day
 */
@Component
@EnableAsync
@RequiredArgsConstructor
@SuppressWarnings("all")
public class CouponExpirationDailyJob {
    //Fields
    /**
     * Coupon repositpry field
     */
    private final CouponRepository couponRepository;

    /**
     * Scan coupons
     * this method runs the schedule task once a day in 12 am.
     * if the coupon date expired it deletes it from sql coupons table
     * and all the purchases of this coupon will be deleted too
     */
    @Async
    @Scheduled(cron = "0 0 0 * * ?")
    public void scanCoupons() {
        Date currentDate = new Date();
        for (Coupon item : couponRepository.findAll()) {
            if (currentDate.after(item.getEndDate())) {
                couponRepository.deleteCompanyCoupon(item.getCouponId());
                couponRepository.deleteCustomerCoupon(item.getCouponId());
                couponRepository.deleteById(item.getCouponId());
                System.out.println("Coupon was expired and deleted from DB : " + item.getTitle() + "\n");
            }
        }
    }
}
