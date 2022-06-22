package com.liveperson.howto.persons.validators;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.flextrade.jfixture.JFixture;
import com.liveperson.howto.persons.contracts.PersonRequest;
import com.liveperson.howto.persons.contracts.RequestType;

@RunWith(MockitoJUnitRunner.class)
public class PersonRequestValidatorTest {
    
    private JFixture fixture;

    // Not strictly necessary, no dependencies
    @InjectMocks
    PersonRequestValidator validator;

    @Before
    public void setup() {
        fixture = new JFixture();
        fixture.customise().circularDependencyBehaviour().omitSpecimen();
        fixture.customise().noResolutionBehaviour().omitSpecimen();
    }

    @Test(expected = NullPointerException.class)
    public void isValid_WithNullRequest_ThrowsException() {
        validator.isValid(null);
    }

    //#region ADD
    @Test
    public void isValid_WithAddUsingId_ReturnsFalse() {
        PersonRequest request = fixture.create(PersonRequest.class);
        request.setRequestType(RequestType.ADD);

        boolean result = validator.isValid(request);

        assertFalse(result);
    }

    @Test
    public void isValid_WithAddUsingNullId_ReturnsTrue() {
        PersonRequest request = fixture.create(PersonRequest.class);
        request.setRequestType(RequestType.ADD);
        request.setId(null);

        boolean result = validator.isValid(request);

        assertTrue(result);
    }
    //#endregion

    //#region UPDATE
    @Test
    public void isValid_WithUpdateUsingNullId_ReturnsFalse() {
        PersonRequest request = fixture.create(PersonRequest.class);
        request.setRequestType(RequestType.UPDATE);
        request.setId(null);

        boolean result = validator.isValid(request);

        assertFalse(result);
    }

    @Test
    public void isValid_WithUpdateUsingId_ReturnsTrue() {
        PersonRequest request = fixture.create(PersonRequest.class);
        request.setRequestType(RequestType.UPDATE);

        boolean result = validator.isValid(request);

        assertTrue(result);
    }
    //#endregion
}
