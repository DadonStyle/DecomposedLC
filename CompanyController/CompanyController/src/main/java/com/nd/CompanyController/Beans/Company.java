package com.nd.CompanyController.Beans;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The Company class
 * represent the company information managed by the application
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Company {
    //Fields
    /**
     * Company Id- field
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private int companyId;
    /**
     * Name - field
     */
    @Column(updatable = false)
    private String name;
    /**
     * Email, password - fields
     */
    private String email, password;
    /**
     * List of coupons field
     */
    @OneToMany(cascade = CascadeType.ALL)
    private List<Coupon> coupons = new ArrayList<>();

    //Methods
    /**
     * To string method
     *
     * @return String - returns all the company info for display
     */
    @Override
    public String toString() {
        return "Company{" +
                "company id: " + companyId + "|" +
                " name: " + name + "|" +
                " email: " + email + "|" +
                " password: " + password + "|" +
                " coupons: \n" + coupons +
                '}';
    }
}