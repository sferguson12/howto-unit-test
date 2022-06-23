package com.liveperson.howto.persons.translators;

import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import com.flextrade.jfixture.JFixture;
import com.liveperson.howto.persons.contracts.Person;
import com.liveperson.howto.persons.contracts.PersonRequest;

@RunWith(Parameterized.class)
public class PersonTranslatorParametersTest {
    
    private JFixture fixture;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Parameter
    public String middleName;

    // Not strictly necessary, no dependencies
    @InjectMocks
    PersonTranslator translator;

    @Before
    public void init() {
        fixture = new JFixture();
        fixture.customise().circularDependencyBehaviour().omitSpecimen();
        fixture.customise().noResolutionBehaviour().omitSpecimen();
    }

    @Parameters
    public static Collection<String> data() {
        return Arrays.asList("", null);
    }

    // Test runs once for each value in the collection
    @Test
    public void toPerson_WithInvalidMiddleName_HasNullMiddleInitial() {
        PersonRequest request = fixture.create(PersonRequest.class);
        request.setMiddleName(middleName);
        
        Person result = translator.toPerson(request);
        assertNull(result.getMiddleInitial());
    }
}
