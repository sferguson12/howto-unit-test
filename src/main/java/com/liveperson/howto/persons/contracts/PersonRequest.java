package com.liveperson.howto.persons.contracts;

import java.time.Instant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonRequest {
    
    private RequestType requestType;

    private Integer id;

    private String firstName;

    private String middleName;

    private String lastName;

    private Instant dateOfBirth;

    public PersonRequest(
            RequestType requestType,
            Integer id,
            String firstName,
            String middleName,
            String lastName,
            Instant dateOfBirth)
    {
        this.requestType = requestType;
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }
}
