package ru.springcourse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.springcourse.Generator.GenCard;
import ru.springcourse.dao.CardDAO;
import ru.springcourse.mail.Mail;
import ru.springcourse.models.Card;
import ru.springcourse.models.Person;
import ru.springcourse.repository.CardRepository;
import ru.springcourse.repository.PersonRepository;
import java.util.List;

/**
 * @author Nikita Kalashnikov
 */
//Аннотация Controller определяет класс как Контроллер Spring MVC
@Controller
//@RequestMapping указывает, что все методы в данном контроллере относятся к URL-адресу
@RequestMapping("/people/card")
public class CardController {

    //private final CardDAO cardDAO;
    private final PersonRepository personRepository;
    private final CardRepository cardRepository;
        /*
    @Autowired
    Фреймворк Spring обеспечивает автоматическое внедрение зависимостей. Другими словами,
    объявляя все зависимости bean-компонентов в файле конфигурации Spring, контейнер
    Spring может автоматически устанавливать связи между сотрудничающими bean-компонентами
     */
    @Autowired
    public CardController(CardDAO cardDAO, PersonRepository personRepository) {

        //this.cardDAO = cardDAO;
        cardRepository = cardDAO;
        this.personRepository = personRepository;
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

    //Получить массив из карт по id владельца ***
    @GetMapping("/{id}")
    public String index(@PathVariable("id") int id,Model model) {

        List<Card> card = cardRepository.index(id);
        if (card.size() == 0){
            Card cardZero = new Card();
            cardZero.setOwnerId(id);
            card.add(cardZero);
            card.add(cardZero);
        }
        model.addAttribute("dataCard", card);
        return "people/indexCard";
    }

    //Показать выбранноую карту ***
    @GetMapping("/show/{card_id}/{owner_id}")
    public String show(@PathVariable("owner_id") int owner_id,Model model) {
        model.addAttribute("dataCard", cardRepository.index(owner_id));
        return "people/showCard";
    }

    //Генерация новой карты с привязкой к id владельца***
    @GetMapping("/showCard/{id}")
    public String create(@PathVariable("id") int id , Model model) {
        Card card = new GenCard ().allData(id);
        cardRepository.save(card);
        model.addAttribute("dataCard", cardRepository.index(id));
        return "people/indexCard";
    }

    //Удаление карты***
    @GetMapping("/delete/{card_id}/{owner_id}")
    public String delete(@PathVariable("card_id")  int card_id,@PathVariable("owner_id") int owner_id,Model model) {
        cardRepository.delete(card_id);
        index(owner_id,model);
        return "people/indexCard";
    }

    //Проверка срока действия карты и отправка уведомления (0/секунды)***
    //Выставил 10 сек чтобы долго не ждать проверку
    @Scheduled(cron = "0/10 * * * * ?")
    public void checkEndDate(){
        System.out.println("1111111111111111111122222222222222222222222222222222222222");
        List<Card> card = cardRepository.cardExpirationDate();
        Mail mail = new Mail();
        for(int i = 0;card.size() > i;i++){

        Person person =  personRepository.show(card.get(i).getOwnerId());
        Card cardNew = new GenCard ().allData(person.getId());
        cardRepository.save(cardNew);

        String message = (
        person.getName() + " cрок действия карты " + card.get(i).getCardNumber() + "  закончился.\n"
        + "Произведен перевыпуск карты. Новые данные карты:\n" +
        "Номер карты " + cardNew.getCardNumber() + '\n' +
        "Номер CVC " + cardNew.getCvcNumber() + '\n' +
        "Срок действия карты до " + cardNew.getExpirationCard()
        );
            mail.send (message,person.getEmail());
            System.out.println(message);
            cardRepository.delete(card.get(i).getCardId());
        }
    }
}


