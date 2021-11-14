package ru.springcourse.repository;

import java.util.List;
import ru.springcourse.models.Person;

/**
 * @author Nikita Kalashnikov
 */

public interface PersonRepository<T> {
    public List<T> index();

    public Person show(int id);

    public void save(Person person);

    public void update(int id, Person updatedPerson);

    public void delete(int id);


}

