package com.liveperson.howto.persons.controllers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNull;

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
import com.liveperson.howto.persons.contracts.PersonRequest;
import com.liveperson.howto.persons.exceptions.ValidationException;
import com.liveperson.howto.persons.providers.IPersonProvider;
import com.liveperson.howto.persons.translators.IPersonTranslator;

@RunWith(MockitoJUnitRunner.class)
public class PersonControllerTest {
    
    private JFixture fixture;

    @Mock
    IPersonProvider provider;

    @Mock
    IPersonTranslator translator;

    @InjectMocks
    PersonController controller;

    @Before
    public void setup() {
        fixture = new JFixture();
        fixture.customise().circularDependencyBehaviour().omitSpecimen();
        fixture.customise().noResolutionBehaviour().omitSpecimen();
    }

    //#region GET
    @Test
    public void getPerson_InvokesPersonProvider() {
        final int personId = fixture.create(Integer.class);
        final Person expect = fixture.create(Person.class);

        when(provider.get(personId)).thenReturn(expect);

        Person result = controller.getPerson(personId);

        // Verifies reference equality
        assertThat(result, is(expect));
    }
    //#endregion

    //#region POST
    @Test
    public void savePerson_WithSaveException_ReturnsNull() {
        final PersonRequest request = fixture.create(PersonRequest.class);
        final Person person = fixture.create(Person.class);
        final ValidationException exception = fixture.create(ValidationException.class);

        when(translator.toPerson(request)).thenReturn(person);
        when(provider.save(person)).thenThrow(exception);

        Person result = controller.savePerson(request);

        assertNull(result);
    }

    @Test
    public void savePersonSavesTranslatedPerson() {
        final PersonRequest request = fixture.create(PersonRequest.class);
        final Person person = fixture.create(Person.class);
        final Person savedPerson = fixture.create(Person.class);

        when(translator.toPerson(request)).thenReturn(person);
        when(provider.save(person)).thenReturn(savedPerson);

        Person result = controller.savePerson(request);

        assertThat(result, is(savedPerson));
    }

    @Category(DoNotDoThisTest.class)
    @Test
    public void savePerson_SavesTranslatedPerson_BAD01() {
        final PersonRequest request = fixture.create(PersonRequest.class);
        final Person person = fixture.create(Person.class);
        final Person savedPerson = fixture.create(Person.class);

        when(translator.toPerson(request)).thenReturn(person);
        try {
            // What if this throws an exception?
            when(provider.save(person)).thenReturn(savedPerson);

            Person result = controller.savePerson(request);

            assertThat(result, is(savedPerson));
        }
        catch (Exception ex) {}
    }

    @Category(DoNotDoThisTest.class)
    @Test
    public void savePerson_SavesTranslatedPerson_BAD02() {
        final PersonRequest request = fixture.create(PersonRequest.class);
        final Person person = fixture.create(Person.class);
        final Person savedPerson = fixture.create(Person.class);

        when(translator.toPerson(request)).thenReturn(person);
        when(provider.save(person)).thenReturn(savedPerson);

        Person result = controller.savePerson(request);

        // What happens if controller.savePerson is broken and result comes back as null?
        if (result != null)
        {
            assertThat(result.getId(), is(savedPerson.getId()));
        }
    }
    //#endregion

    //#region DELETE
    @Test
    public void deletePerson_InvokesPersonProvider() {
        final int personId = fixture.create(Integer.class);

        controller.deletePerson(personId);

        verify(provider).delete(personId);
        // Or, if we're concerned about multiple invocations
        verify(provider, times(1)).delete(personId);
    }
    //#endregion
}
