package com.liveperson.howto.persons.contracts;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Person implements IRecord {
    
    private Integer id;

    private String firstName;

    private String lastName;

    private Character middleInitial;

    private Integer age;

    public Person(
            Integer id,
            String firstName,
            String lastName,
            Character middleInitial,
            Integer age) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleInitial = middleInitial;
        this.age = age;
    }
}
