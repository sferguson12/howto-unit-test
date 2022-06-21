package com.liveperson.howto.persons.translators;

import java.util.List;

import com.liveperson.howto.persons.contracts.Person;
import com.liveperson.howto.persons.contracts.PersonRequest;

public interface IPersonTranslator {
    
    Person toPerson(PersonRequest personRequest);

    List<Person> toPersons(List<PersonRequest> personRequests);
}
