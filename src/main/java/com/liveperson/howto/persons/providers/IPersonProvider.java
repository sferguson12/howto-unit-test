package com.liveperson.howto.persons.providers;

import java.io.InvalidObjectException;

import com.liveperson.howto.persons.contracts.Person;

public interface IPersonProvider {
    
    Person get(int personId);

    Person save(Person person) throws InvalidObjectException;

    void delete(int personId);
}
