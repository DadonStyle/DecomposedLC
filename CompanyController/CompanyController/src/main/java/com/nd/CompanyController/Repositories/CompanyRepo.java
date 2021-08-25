package com.nd.CompanyController.Repositories;

import com.nd.CompanyController.Beans.Company;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The Company repository class
 * exacute companies CRUD
 */
public interface CompanyRepo extends JpaRepository<Company,Integer> {
    /**
     * Find by email and password
     * this method finds a company from DB by it's email and password
     * @param email - the company's email
     * @param password - the company's password
     * @return Comapny instance
     */
    Company findByEmailAndPassword(String email, String password);

    /**
     * Find by email
     * this method finds a company from DB by email
     * @param email - the company's email
     * @return Company instance
     */
    Company findByEmail(String email);

    /**
     * Find company by Id
     * this method finds a company from DB by id
     * @param companyId - the company's id
     * @return Company instance
     */
    Company findByCompanyId(int companyId);
}
