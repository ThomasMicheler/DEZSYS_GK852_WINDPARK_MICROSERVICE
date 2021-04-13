package io.pivotal.microservices.windpark;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface WindparkRepository extends MongoRepository<WindengineData, String> {

    public WindengineData findByWindengineID(String windengineID);
    public List<WindengineData> findByWindparkID(String windparkID);

}
