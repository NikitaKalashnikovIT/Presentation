package ru.springcourse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.springcourse.dao.PersonDAO;
import ru.springcourse.repository.PersonRepository;
import ru.springcourse.models.Person;
import javax.validation.Valid;

/**
 * @author Nikita Kalashnikov
 */
//Аннотация Controller определяет класс как Контроллер Spring MVC
@Controller
//@RequestMapping указывает, что все методы в данном Контроллере относятся к URL-адресу
@RequestMapping("/people")
public class PeopleController {
private final PersonRepository personRepository;

/*
@Autowired
Фреймворк Spring обеспечивает автоматическое внедрение зависимостей. Другими словами,
объявляя все зависимости bean-компонентов в файле конфигурации Spring, контейнер
Spring может автоматически устанавливать связи между сотрудничающими bean-компонентами
 */
    @Autowired
    public PeopleController(PersonDAO personDAO) {
        //this.personDAO = personDAO;
        this.personRepository = personDAO;
    }

    /*
    @GetMapping
    Эта аннотация используется для отображения запросов HTTP GET на определенные
    методы-обработчики
     */
    /*
    @PathVariable
    Эта аннотация используется для аннотирования аргументов метода обработчика запроса.
    @RequestMapping. Аннотации могут быть использованы для обработки динамических изменений
    в URI , где определенное значение URI , действует в качестве параметра. Вы можете
    указать этот параметр с помощью регулярного выражения. @PathVariable Аннотации могут быть использованы для объявления этого параметра.
     */
    //model.addAttribute(ключ,значение);

    //Получить массив данных из таблицы person  ***
    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", personRepository.index());
        return "people/index";
    }

    //Получить данные человека по его id ***
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personRepository.show(id));
        return "people/show";
    }
    /*
    @ModelAttribute создает новый объект, в данном случае Person,
    добавление значений в поля,
    добавление созданного объекта в модель (Model)(person - ключ ,Person person - значение)
     */
    //Переход на страницу для ввода данных клиента ***
    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    //Создание нового клиента ***
    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "people/new";
        personRepository.save(person);
        return "redirect:/people";
    }
    //Редактирование данных клиента
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", personRepository.show(id));
        return "people/edit";
    }
    /*
    @Valid аннотация необходимая для проверки вводимых данных. Описание ограничений в файле Person.
    bindingResult хранит значения проверки.bindingResult должен стоять после описываемого объекта
    в данном случае после Person person.
     */
    //Обновление страницы ***
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        //проверка на присутствие ошибок
        if (bindingResult.hasErrors())
            return "people/edit";

        personRepository.update(id, person);
        return "redirect:/people";
    }

    //Удаление клиента
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        personRepository.delete(id);
        return "redirect:/people";
    }
}
