package com.project.hello;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Adas on 2017-03-28.
 */
public class PersonDTO {

    private String name;
    private Set<String> friends;

    public PersonDTO(Person person) {
        this.name = person.getName();
        this.friends = person.friends
                .stream()
                .map(x -> x.getName())
                .collect(Collectors.toSet());
    }

    public String getName() {
        return name;
    }

    public Set<String> getFriends() {
        return friends;
    }
}
