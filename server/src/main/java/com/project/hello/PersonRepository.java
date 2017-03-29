package com.project.hello;

import org.springframework.data.neo4j.repository.GraphRepository;

/**
 * Created by Adas on 2017-03-28.
 */
public interface PersonRepository extends GraphRepository<Person> {

    Person findByName(String name);
}
