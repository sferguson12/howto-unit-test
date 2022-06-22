package com.liveperson.howto.persons.controllers;

import com.liveperson.howto.persons.contracts.Person;
import com.liveperson.howto.persons.contracts.PersonRequest;
import com.liveperson.howto.persons.exceptions.ValidationException;
import com.liveperson.howto.persons.providers.IPersonProvider;
import com.liveperson.howto.persons.translators.IPersonTranslator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PersonController {

    private final IPersonProvider personProvider;
    private final IPersonTranslator personTranslator;

    public PersonController(
            IPersonProvider personProvider,
            IPersonTranslator personTranslator) {
        this.personProvider = personProvider;
        this.personTranslator = personTranslator;
    }

    // Pretend HTTP GET request
    public Person getPerson(int personId) {
        log.info("Received person get request for id {}", personId);
        
        return personProvider.get(personId);
    }

    // Pretend HTTP POST request
    public Person savePerson(PersonRequest personRequest)
    {
        log.info("Received person save request type {}", personRequest.getRequestType());

        Person person = personTranslator.toPerson(personRequest);

        Person result = null;
        try {
            result = personProvider.save(person);
        }
        catch (ValidationException ex) {
            log.error("Failed to save person request {}", personRequest.getRequestType(), ex);
        }

        return result;
    }

    // Pretend HTTP DELETE request
    public void deletePerson(int personId) {
        log.info("Received person delete request for id {}", personId);
        
        personProvider.delete(personId);
    }
}
