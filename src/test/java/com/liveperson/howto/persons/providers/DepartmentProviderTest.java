package com.liveperson.howto.persons.providers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

@RunWith(MockitoJUnitRunner.class)
public class DepartmentProviderTest {

    private JFixture fixture;

    @Mock
    IDataProvider<Person> dataProvider;

    @InjectMocks
    DepartmentProvider provider;

    @Before
    public void setup() {
        fixture = new JFixture();
        fixture.customise().circularDependencyBehaviour().omitSpecimen();
        fixture.customise().noResolutionBehaviour().omitSpecimen();
    }

    // Example from QueryMessagesSystemTest.queryMessagesByAgentTest()
    //         agent.queryMessages(convId, "1", "1", "1", reqId.get());
    @Category(DoNotDoThisTest.class)
    @Test
    public void query_WithDepartmentAndRole_ReturnsPersons_BAD01() {
        Person person =  fixture.create(Person.class);
        List<Person> persons = new ArrayList<Person>(Arrays.asList(person));
        when(dataProvider.query(1, 1)).thenReturn(persons);

        List<Person> result = provider.getPersonsInDepartment(1, 1);

        assertThat(result).containsExactly(person);
    }

    @Test
    public void query_WithDepartmentAndRole_ReturnsPersons() {
        int departmentId = fixture.create(Integer.class);
        int roleId = fixture.create(Integer.class);

        Person person =  fixture.create(Person.class);
        List<Person> persons = new ArrayList<Person>(Arrays.asList(person));
        when(dataProvider.query(departmentId, roleId)).thenReturn(persons);

        // Why is this better?  Show with broken code
        List<Person> result = provider.getPersonsInDepartment(departmentId, roleId);

        assertThat(result).containsExactly(person);
    }
}
