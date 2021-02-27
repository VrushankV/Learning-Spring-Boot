package com.example.demo.dao;

import com.example.demo.model.Person;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("fakeDao")
public class FakePersonDataAccessService implements PersonDao {

    private static List<Person> db = new ArrayList();
    @Override
    public int insertPerson(UUID id, Person person) {
        db.add(new Person(id, person.getName()));
        return 1;
    }

    @Override
    public List<Person> selectAllPeople() {
        return db;
    }

    @Override
    public Optional<Person> selectPersonById(UUID id) {
        return db.stream()
                .filter(person -> person.getId().equals(id))
                .findFirst();
    }

    @Override
    public int deletePersonById(UUID id) {
        final Optional<Person> person = selectPersonById(id);
        if(person.isPresent()){
            db.remove(person.get());
            return 1;
        }
        return 0;
    }

    @Override
    public int updatePersonById(UUID id, Person update) {
        return selectPersonById(id)
                .map(person ->{
                    int indexToUpdate = db.indexOf(person);
                    if( indexToUpdate >= 0){
                        db.set(indexToUpdate, new Person(id,update.getName()));
                        return 1;
                    }
                     return 0;
                        }

                ).orElse( 0);
    }

}
