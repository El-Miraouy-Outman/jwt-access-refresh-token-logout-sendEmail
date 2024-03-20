package com.elmiraouy.jwtsecurity.repository;

import com.elmiraouy.jwtsecurity.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository  extends JpaRepository<Customer,Long> {

    @Query("select c from Customer c where upper( c.email) = upper(:email) ")
    public Customer findByEmail(@Param("email") String email);

}
