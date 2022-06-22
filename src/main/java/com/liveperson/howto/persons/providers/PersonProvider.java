package com.liveperson.howto.persons.providers;

import org.springframework.stereotype.Component;

import com.liveperson.howto.persons.contracts.Person;
import com.liveperson.howto.persons.dataproviders.IDataProvider;
import com.liveperson.howto.persons.exceptions.ValidationException;
import com.liveperson.howto.persons.validators.IPersonValidator;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class PersonProvider implements IPersonProvider {

    private final IDataProvider<Person> dataProvider;
    private final IPersonValidator personValidator;

    public PersonProvider(
            IDataProvider<Person> dataProvider,
            IPersonValidator personValidator) {
        this.dataProvider = dataProvider;
        this.personValidator = personValidator;
    }

    @Override
    public Person get(int personId) {
        return dataProvider.select(personId);
    }

    @Override
    public Person save(Person person) {
        if (person == null) {
            throw new NullPointerException("Person must be provided");
        }

        if (!personValidator.isValid(person)) {
            throw new ValidationException("Person is not valid");
        }

        log.debug("Validated person save for id {}", person.getId());

        return dataProvider.upsert(person);
    }

    @Override
    public void delete(int personId) {
        dataProvider.delete(personId);
    }
}
