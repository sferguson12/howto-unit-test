package com.liveperson.howto.persons.providers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.flextrade.jfixture.annotations.Fixture;
import com.flextrade.jfixture.rules.FixtureRule;
import com.liveperson.howto.persons.contracts.Person;
import com.liveperson.howto.persons.dataproviders.IDataProvider;

@RunWith(MockitoJUnitRunner.class)
public class DepartmentProviderTestRule {

    @Rule
    public FixtureRule rule = FixtureRule.initFixtures();

    @Fixture
    private List<Person> persons;

    @Fixture
    private int departmentId;

    @Fixture
    private int roleId;

    @Mock
    IDataProvider<Person> dataProvider;

    @InjectMocks
    DepartmentProvider provider;

    @Test
    public void query_WithDepartmentAndRole_ReturnsPersons() {
        when(dataProvider.query(departmentId, roleId)).thenReturn(persons);

        List<Person> result = provider.getPersonsInDepartment(departmentId, roleId);

        // Another way of asserting on a list against specific elements
        assertThat(result).containsExactlyElementsOf(persons);
    }
}
