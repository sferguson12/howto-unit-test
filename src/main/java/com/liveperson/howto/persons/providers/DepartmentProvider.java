package com.liveperson.howto.persons.providers;

import java.util.List;

import com.liveperson.howto.persons.contracts.Person;
import com.liveperson.howto.persons.dataproviders.IDataProvider;

public class DepartmentProvider implements IDepartmentProvider {

    private final IDataProvider<Person> dataProvider;

    public DepartmentProvider(
            IDataProvider<Person> dataProvider) {
        this.dataProvider = dataProvider;    
    }

    @Override
    public List<Person> getPersonsInDepartment(int departmentId, int roleId) {
        return dataProvider.query(departmentId, roleId);
    }
}
