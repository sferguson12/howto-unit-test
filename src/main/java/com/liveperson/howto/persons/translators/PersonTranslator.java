package com.liveperson.howto.persons.translators;

import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import com.liveperson.howto.persons.contracts.Person;
import com.liveperson.howto.persons.contracts.PersonRequest;

public class PersonTranslator implements IPersonTranslator {

    private final ZoneId utcZone = ZoneId.of("UTC");

    @Override
    public Person toPerson(PersonRequest personRequest) {
        if (personRequest == null) {
            throw new NullPointerException("personRequest is required");
        }

        return new Person(
            personRequest.getId(),
            personRequest.getFirstName(),
            personRequest.getLastName(),
            getMiddleInitial(personRequest.getMiddleName()),
            getAge(personRequest.getDateOfBirth()));
    }

    @Override
    public List<Person> toPersons(List<PersonRequest> personRequests) {
        return personRequests.stream()
            .map(r -> toPerson(r))
            .collect(Collectors.toList());
    }

    private Character getMiddleInitial(String middleName) {
        return middleName == null || middleName.length() < 1
            ? null
            : middleName.charAt(0);
    }

    private Integer getAge(Instant dateOfBirth) {
        if (dateOfBirth == null) return null;

        Long delta = ChronoUnit.YEARS.between(
            dateOfBirth.atZone(utcZone),
            Instant.now().atZone(utcZone));
        return delta.intValue();
    }
}
