package com.liveperson.howto.persons.providers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.InvalidObjectException;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.flextrade.jfixture.JFixture;
import com.liveperson.howto.persons.categories.DoNotDoThisTest;
import com.liveperson.howto.persons.contracts.Person;
import com.liveperson.howto.persons.dataproviders.IDataProvider;
import com.liveperson.howto.persons.validators.IPersonValidator;

@RunWith(MockitoJUnitRunner.class)
public class PersonProviderTest {
    
    private JFixture fixture;

    @Mock
    IDataProvider<Person> dataProvider;

    @Mock
    IPersonValidator validator;

    @InjectMocks
    PersonProvider provider;

    @Before
    public void setup() {
        fixture = new JFixture();
        fixture.customise().circularDependencyBehaviour().omitSpecimen();
        fixture.customise().noResolutionBehaviour().omitSpecimen();
    }

    //#region get
    @Test
    public void get_WithPersonId_CallsDataProvider() {
        int personId = fixture.create(Integer.class);
        provider.get(personId);
        verify(dataProvider, times(1)).select(personId);
    }
    //#endregion

    //#region save
    @Test(expected = NullPointerException.class)
    public void save_WithNullPerson_ThrowsException() throws InvalidObjectException {
        provider.save(null);
        // Optional, though perhaps useful
        // Note on 'any' for positive vs. negative verifications
        verify(validator, times(0)).isValid(any(Person.class));
    }

    @Category(DoNotDoThisTest.class)
    @Test
    public void save_WithNullPerson_ThrowsException_BAD01() throws InvalidObjectException {
        try {
            // What is the expected outcome here?  What if we don't get that?
            provider.save(null);
        }
        catch (NullPointerException ex) {
            // Verifying the correct exception was thrown for the correct reason
            assertThat(ex.getMessage()).matches("Person.*");
        }
    }

    @Test
    public void save_WithNullPerson_ThrowsException_ALT01() throws InvalidObjectException {
        try {
            provider.save(null);
            // Vital - otherwise a missing exception allows the test to pass
            fail("Save did not throw expected exception");
        }
        catch (NullPointerException ex) {
            // Verifying the correct exception was thrown for the correct reason
            assertThat(ex.getMessage()).matches("Person.*");
        }
    }

    @Test(expected = InvalidObjectException.class)
    public void save_WithInvalidPerson_ThrowsException() throws InvalidObjectException {
        Person person = fixture.create(Person.class);
        when(validator.isValid(person)).thenReturn(false);
        provider.save(person);
    }

    @Test
    public void save_WithInvalidPerson_ThrowsException_ALT01() {
        Person person = fixture.create(Person.class);
        when(validator.isValid(person)).thenReturn(false);

        try {
            provider.save(person);
            // Vital - otherwise a missing exception allows the test to pass
            fail("Save did not throw expected exception");
        }
        catch (InvalidObjectException ex) {
            assertThat(ex.getMessage()).matches(".*not valid");
        }
    }

    @Test
    public void save_WithValidPerson_CallsDataProvider() throws InvalidObjectException {
        Person person = fixture.create(Person.class);
        when(validator.isValid(person)).thenReturn(true);
        provider.save(person);
        verify(dataProvider, times(1)).upsert(person);
    }
    //#endregion

    //#region delete
    @Test
    public void delete_WithPersonId_CallsDataProvider() {
        int personId = fixture.create(Integer.class);
        provider.delete(personId);
        verify(dataProvider, times(1)).delete(personId);
    }
    //#endregion
}
