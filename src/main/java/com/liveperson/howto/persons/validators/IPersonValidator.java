package com.liveperson.howto.persons.validators;

import com.liveperson.howto.persons.contracts.Person;

public interface IPersonValidator {
    
    public boolean isValid(Person person);
}
