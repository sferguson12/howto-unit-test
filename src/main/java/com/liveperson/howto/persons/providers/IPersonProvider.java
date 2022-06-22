package com.liveperson.howto.persons.providers;

import com.liveperson.howto.persons.contracts.Person;

public interface IPersonProvider {
    
    Person get(int personId);

    Person save(Person person);

    void delete(int personId);
}
