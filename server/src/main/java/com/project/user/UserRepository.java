package com.project.user;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Adas on 2017-04-18.
 */
@Repository
public interface UserRepository extends GraphRepository<User> {

    /**
     * finds user by email
     * @param email
     * @return
     */
    User findByEmail(String email);

    /**
     * finds user by lastname
     * @param lastname
     * @return
     */
    List<User> findBySurnameIgnoreCaseContaining(String lastname);


}
