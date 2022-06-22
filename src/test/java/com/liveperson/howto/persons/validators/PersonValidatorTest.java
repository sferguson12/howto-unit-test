package com.liveperson.howto.persons.validators;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.flextrade.jfixture.JFixture;
import com.liveperson.howto.persons.contracts.Person;

@RunWith(MockitoJUnitRunner.class)
public class PersonValidatorTest {
    
    private JFixture fixture;

    // Not strictly necessary, no dependencies
    @InjectMocks
    PersonValidator validator;

    @Before
    public void setup() {
        fixture = new JFixture();
        fixture.customise().circularDependencyBehaviour().omitSpecimen();
        fixture.customise().noResolutionBehaviour().omitSpecimen();
    }

    @Test(expected = NullPointerException.class)
    public void isValid_WithNullPerson_ThrowsException() {
        validator.isValid(null);
    }

    @Test
    public void isValid_WithNullFirstName_ReturnsFalse() {
        Person person = fixture.create(Person.class);
        person.setFirstName(null);

        boolean result = validator.isValid(person);

        assertFalse(result);
        // Can also use this, but consider arrange/act/assert
        assertFalse(validator.isValid(person));
    }

    @Test
    public void isValid_WithNullLastName_ReturnsFalse() {
        Person person = fixture.create(Person.class);
        person.setLastName(null);

        boolean result = validator.isValid(person);

        assertFalse(result);
    }

    @Test
    public void isValid_WithValidPerson_ReturnsTrue() {
        Person person = fixture.create(Person.class);

        boolean result = validator.isValid(person);

        assertTrue(result);
    }
}
