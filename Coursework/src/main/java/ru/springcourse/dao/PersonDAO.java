package ru.springcourse.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.springcourse.models.Person;
import ru.springcourse.repository.PersonRepository;

import java.util.Date;
import java.util.List;

/**
 * @author Nikita Kalashnikov
 */
/*
В данном случае будет создан компонент типа PersonDAO с именем и идентификатором
simpleBean. Контекст Spring автоматически найдёт этот класс и создаст объект
этого типа, который будет использоваться в дальнейшем для внедрения зависимостей.
 */
@Component
public class PersonDAO implements PersonRepository {
/*
jdbcTemplate убирает дублирование кода. И проверку SQLexception
 */
    private final JdbcTemplate jdbcTemplate;
/*
        @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/first_db");
        dataSource.setUsername("postgres");
        dataSource.setPassword("2589654");

        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }
    из файла springConfig внедрит спомощью аннотации @Autowired
 */
/*
jdbcTemplate.query - первый аргумет это sql запрос
второй аргумент это так называемый RowMapper это объект который отображает строки их таблицы
в мою сущность(то есть брать строку и записывать в переменные метода persen
* */
    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }

/*
stream().findAny().orElse(null); лямда выражение, которое возвращает null, если по заданному id
человек не найден.   findAny() - ищет объект в списке, если объект есть, он будет возвращен в ином
случае orElse(null); вернет null;
 */
    public Person show(int id) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);

    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO Person VALUES(?, ?, ?)", person.getName(), person.getAge(),
                person.getEmail());
    }



    public void update(int id, Person updatedPerson) {
        Date date = new Date();
        jdbcTemplate.update("UPDATE Person SET name=?, age=?, email=? WHERE id=?", updatedPerson.getName(),
                updatedPerson.getAge(), updatedPerson.getEmail(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Cards WHERE owner_id=?", id);
        jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
    }
}
