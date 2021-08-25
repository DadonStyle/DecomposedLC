package com.nd.GuestController.Beans;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

/**
 * The Coupon class
 * represent the coupon information managed by the application
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Coupon {
    /**
     * constructor without the coupon Id
     * @param companyId company id
     * @param amount coupon amount
     * @param price coupon price
     * @param category coupon category
     * @param title coupon title
     * @param description coupon description
     * @param image coupon image
     * @param startDate coupon start date
     * @param endDate coupon end date
     */
    public Coupon(int companyId, int amount, double price, Category category, String title, String description, byte[] image, Date startDate, Date endDate) {
        this.companyId = companyId;
        this.amount = amount;
        this.price = price;
        this.category = category;
        this.title = title;
        this.description = description;
        this.image = image;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    //Fields
    /**
     * Coupon id field
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private int couponId;
    /**
     * Company id field
     */
    private int companyId;
    /**
     * Amount field
     */
    private int amount;
    /**
     * Price field
     */
    private double price;
    /**
     * Category field
     */
    @Enumerated(EnumType.STRING)
    private Category category;
    /**
     * Title, description, image fields
     */
    private String title, description;
    /**
     * file field
     */
    //@Lob use this for MySQL, PostgresSQL dont need it
    private byte[] image;
    /**
     * Start and End date fields
     */
    private Date startDate, endDate;

    //Methods

    /**
     * To string method
     *
     * @return String - returns the coupon's info for display
     */
    @Override
    public String toString() {
        return "Coupon{" +
                "coupon id: " + couponId + "|" +
                " company id: " + companyId + "|" +
                " amount: " + amount + "|" +
                " price: " + price + "|" +
                " category: " + category + "|" +
                " title: " + title + "|" +
                " description: " + description + "|" +
                " image: " + image + "|" +
                " start date: " + startDate + "|" +
                " end date: " + endDate +
                '}';
    }
}
