package com.liveperson.howto.persons.dataproviders;

import com.liveperson.howto.persons.contracts.IRecord;

public interface IDataProvider<T extends IRecord> {
    
    public T select(int id);

    public T upsert(T record);

    public void delete(int id);
}
