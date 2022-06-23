package com.liveperson.howto.persons.translators;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import org.assertj.core.api.SoftAssertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.flextrade.jfixture.JFixture;
import com.liveperson.howto.persons.contracts.Person;
import com.liveperson.howto.persons.contracts.PersonRequest;

@RunWith(MockitoJUnitRunner.class)
public class PersonTranslatorTest {
    // Review code coverage with team
    
    private final ZoneId utcZone = ZoneId.of("UTC");

    private JFixture fixture;

    // Not strictly necessary, no dependencies
    @InjectMocks
    PersonTranslator translator;

    @Before
    public void setup() {
        fixture = new JFixture();
        fixture.customise().circularDependencyBehaviour().omitSpecimen();
        fixture.customise().noResolutionBehaviour().omitSpecimen();
    }
    
    @Test
    public void toPerson_WithNullMiddleName_HasNullMiddleInitial() {
        PersonRequest request = fixture.create(PersonRequest.class);
        request.setMiddleName(null);
        
        Person result = translator.toPerson(request);
        assertNull(result.getMiddleInitial());
    }
    
    @Test
    public void toPerson_WithEmptyMiddleName_HasNullMiddleInitial() {
        PersonRequest request = fixture.create(PersonRequest.class);
        request.setMiddleName("");
        
        Person result = translator.toPerson(request);
        assertNull(result.getMiddleInitial());
    }
    
    // Why do this test? Compare to getMiddleInitial() method in translator
    @Test
    public void toPerson_WithSingleCharacterMiddleName_HasMiddleInitial() {
        PersonRequest request = fixture.create(PersonRequest.class);
        Character initial = fixture.create(Character.class);
        request.setMiddleName(initial.toString());
        
        Person result = translator.toPerson(request);
        assertEquals(result.getMiddleInitial(), initial);
    }

    @Test
    public void toPerson_WithNullDateOfBirth_HasNullAge() {
        PersonRequest request = fixture.create(PersonRequest.class);
        request.setDateOfBirth(null);
        
        Person result = translator.toPerson(request);
        assertNull(result.getAge());
    }
    
    @Test
    public void toPerson_WithValidDateOfBirth_HasCorrectAge() {
        // Why test this specifically in addition to the next one? Static vs. dynamic values
        PersonRequest request = fixture.create(PersonRequest.class);

        // Note: Do not use local time for testing unless all dates are local time
        int currentYear = Instant.now().atZone(utcZone).getYear();
        // Only numeric values are supported
        int birthYear = fixture.create().inRange(Integer.class, 1981, 2001);
        request.setDateOfBirth(Instant.parse(birthYear + "-01-01T00:00:00Z"));
        
        Person result = translator.toPerson(request);
        assertEquals(result.getAge().intValue(), currentYear - birthYear);

        // Compare to failure for
        assertTrue(result.getAge().intValue() == currentYear - birthYear);
    }

    @Test
    public void toPerson_WithValidPersonRequest_ReturnsPerson() {
        PersonRequest request = fixture.create(PersonRequest.class);

        Long age = ChronoUnit.YEARS.between(
            request.getDateOfBirth().atZone(utcZone),
            Instant.now().atZone(utcZone));
        Person expect = new Person(
            request.getId(),
            request.getFirstName(),
            request.getLastName(),
            request.getMiddleName().charAt(0),
            age.intValue());

        Person result = translator.toPerson(request);
        // Why does this not work? Reference vs. value
        // assertThat(result, is(expect));

        // Value comparison of primitives, no need to test each property individually
        // Useful for testing JSON by deserializing into an object rather than comparing strings
        assertThat(result).usingRecursiveComparison().isEqualTo(expect);
    }

    @Test
    public void toPerson_WithValidPersonRequest_ReturnsPerson_SOFT() {
        PersonRequest request = fixture.create(PersonRequest.class);

        Long age = ChronoUnit.YEARS.between(
            request.getDateOfBirth().atZone(utcZone),
            Instant.now().atZone(utcZone));

        Person result = translator.toPerson(request);

        SoftAssertions bundle = new SoftAssertions();
        bundle.assertThat(result.getId()).isEqualTo(request.getId());
        bundle.assertThat(result.getFirstName()).isEqualTo(request.getFirstName());
        bundle.assertThat(result.getLastName()).isEqualTo(request.getLastName());
        bundle.assertThat(result.getMiddleInitial()).isEqualTo(request.getMiddleName().charAt(0));
        bundle.assertThat(result.getAge()).isEqualTo(age.intValue());
        bundle.assertAll();
    }
}
