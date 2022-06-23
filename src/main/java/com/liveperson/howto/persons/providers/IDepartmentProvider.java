package com.liveperson.howto.persons.providers;

import java.util.List;

import com.liveperson.howto.persons.contracts.Person;

public interface IDepartmentProvider {
    
    public List<Person> getPersonsInDepartment(int departmentId, int roleId);
}
