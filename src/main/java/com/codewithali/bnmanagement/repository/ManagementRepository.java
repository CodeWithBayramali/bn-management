package com.codewithali.bnmanagement.repository;

import com.codewithali.bnmanagement.dto.ManagementDto;
import com.codewithali.bnmanagement.model.Management;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ManagementRepository extends MongoRepository<Management, String> {
    @Query("{ '$expr': { '$and': [ " +
            "{ '$eq': [{ '$month': '$date' }, ?0] }, " +
            "{ '$eq': [{ '$year': '$date' }, ?1] } ] } }")
    List<Management> findByDateMonthAndYear(int month, int year);
}
