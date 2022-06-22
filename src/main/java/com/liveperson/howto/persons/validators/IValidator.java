package com.liveperson.howto.persons.validators;

public interface IValidator<T> {
    
    public boolean isValid(T person);
}
