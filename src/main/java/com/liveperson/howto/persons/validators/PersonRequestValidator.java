package com.liveperson.howto.persons.validators;

import com.liveperson.howto.persons.contracts.PersonRequest;
import com.liveperson.howto.persons.contracts.RequestType;

public class PersonRequestValidator implements IValidator<PersonRequest> {

    @Override
    public boolean isValid(PersonRequest personRequest) {
        if (personRequest == null) {
            throw new NullPointerException("personRequest is required");
        }

        boolean hasPersonId = personRequest.getId() != null;

        // Note on immutable/mutable values in conditionals
        return RequestType.ADD == personRequest.getRequestType()
            ? !hasPersonId
            : hasPersonId;
    }
}
