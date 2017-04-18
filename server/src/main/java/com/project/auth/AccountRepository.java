package com.project.auth;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Adas on 2017-04-18.
 */
@Repository
public interface AccountRepository extends GraphRepository<Account> {

    Account findByEmail(String email);
}
