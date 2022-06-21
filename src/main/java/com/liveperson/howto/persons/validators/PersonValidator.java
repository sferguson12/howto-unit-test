package com.liveperson.howto.persons.validators;

import com.liveperson.howto.persons.contracts.Person;

public class PersonValidator implements IPersonValidator {

    @Override
    public boolean isValid(Person person) {
        if (person == null) {
            throw new NullPointerException("person is required");
        }

        return !(person.getFirstName() == null || person.getLastName() == null);
    }
}
