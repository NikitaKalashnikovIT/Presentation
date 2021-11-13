package ru.springcourse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.springcourse.Generator.GenCard;
import ru.springcourse.dao.CardDAO;
import ru.springcourse.mail.Mail;
import ru.springcourse.models.Card;
import ru.springcourse.models.Person;
import ru.springcourse.repository.PersonRepository;
import java.util.List;
import java.util.stream.Stream;


/**
 * @author Nikita Kalashnikov
 */
//Аннотация Controller определяет класс как Контроллер Spring MVC
@Controller
//@RequestMapping указывает, что все методы в данном контроллере относятся к URL-адресу
@RequestMapping("/people/card")
public class CardController {

    private final CardDAO cardDAO;
    private final PersonRepository repository;
    @Autowired
    public CardController(CardDAO cardDAO, PersonRepository repository) {
        this.cardDAO = cardDAO;
        this.repository = repository;
    }

    @GetMapping("/{id}")
    public String index(@PathVariable("id") int id,Model model) {

        List<Card> card = cardDAO.index(id);
        if (card.size() == 0){
            Card cardZero = new Card();
            cardZero.setOwnerId(id);
            card.add(cardZero);
            card.add(cardZero);
        }
        model.addAttribute("dataCard", card);
        return "people/indexCard";
    }

    @GetMapping("/show/{card_id}/{owner_id}")
    public String show(@PathVariable("owner_id") int owner_id,Model model) {
        model.addAttribute("dataCard", cardDAO.index(owner_id));
        return "people/showCard";
    }

    @GetMapping("/showCard/{id}")
    public String create(@PathVariable("id") int id , Model model) {
        Card card = new GenCard ().allData(id);
        cardDAO.save(card);
        model.addAttribute("dataCard", cardDAO.index(id));
        return "people/indexCard";
    }

    @GetMapping("/delete/{card_id}/{owner_id}")
    public String delete(@PathVariable("card_id")  int card_id,@PathVariable("owner_id") int owner_id,Model model) {
        cardDAO.delete(card_id);
        index(owner_id,model);
        return "people/indexCard";
    }

    @Scheduled(cron = "0/5 * * * * ?")
    public void checkEndDate(){
        System.out.println("1213213212132");
        List<Card> card = cardDAO.cardExpirationDate();
        Mail mail = new Mail();
        //Person person ;
        for(int i = 0;card.size() > i;i++){

        Person person =  repository.show(card.get(i).getOwnerId());
        Card cardNew = new GenCard ().allData(person.getId());
        cardDAO.save(cardNew);

        String message = (
        person.getName() + " cрок действия карты " + card.get(i).getCardNumber() + "  закончился.\n"
        + "Произведен перевыпуск карты. Новые данные карты:\n" +
        "Номер карты " + cardNew.getCardNumber() + '\n' +
        "Номер CVC " + cardNew.getCvcNumber() + '\n' +
        "Срок действия карты до " + cardNew.getExpirationCard()
        );
            mail.send (message,person.getEmail());
            System.out.println(message);
            cardDAO.delete(card.get(i).getCardId());
        }
    }
    //////////////////////////////////////
//    public int i = 1;
//    @Scheduled(cron = "0/30 * * * * ?")
//    public void runFirst()throws InterruptedException{
//        i = i +1;
//        System.out.println("runFirst" + i);
////        Thread.sleep(5000);
////        System.out.println("First" + i);
//    }
}


