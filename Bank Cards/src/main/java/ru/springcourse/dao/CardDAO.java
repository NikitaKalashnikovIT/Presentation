package ru.springcourse.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.springcourse.Generator.GenCard;
import ru.springcourse.models.Card;
import ru.springcourse.repository.CardRepository;

import java.util.*;

/**
 * @author Nikita Kalashnikov
 */
@Component
public class CardDAO implements CardRepository {
//jdbcTemplate убирает дублирование кода. И проверку SQLexception
    private final JdbcTemplate jdbcTemplate;

    /*
    jdbcTemplate.query - первый аргумет это sql запрос
    второй аргумент это так называемый RowMapper это объект который отображает строки их таблици
    в мою сущность( тоесть брать строку и записывать в переменные метода Card
    */

    @Autowired
    public CardDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Card> index(int owner_id) {
        return jdbcTemplate.query("SELECT * FROM Cards WHERE owner_id=?",new Object[]{owner_id}, new BeanPropertyRowMapper<>(Card.class));
    }

    /*
    stream().findAny().orElse(null); лямда выражение которое возвращает null если по задонному id
    человек не найден.   findAny() - ищет объект в списке если обект есть он будет возвращен в ином
    случае orElse(null); вернет null;
     */

    public Card show(int card_id) {
        return jdbcTemplate.query("SELECT * FROM Cards WHERE card_id=?", new Object[]{card_id}, new BeanPropertyRowMapper<>(Card.class))
        .stream().findAny().orElse(null);
    }

    public boolean save(Card card) {

        while (jdbcTemplate.query("SELECT * FROM Cards WHERE card_number=?", new Object[]{card.getCardNumber()}, new BeanPropertyRowMapper<>(Card.class)).stream().findAny().orElse(null) != null){
            card.setCardNumber(new GenCard ().cardNumber());
        }

        String sql = "insert into Cards values(?, ?, ?, ?, ? );";
        jdbcTemplate.update(sql, card.getCardNumber(), card.getCvcNumber(), card.getIssuingCard(), card.getExpirationCard(), card.getOwnerId());
        return true;
    }

    public void update(Card updatedCard) {
        jdbcTemplate.update("UPDATE Cards SET card_number=?, cvc_number=? , issuing_card=?,expiration_card=?,owner_id=? WHERE card_id=?", updatedCard.getCardNumber(),
        updatedCard.getCvcNumber(), updatedCard.getIssuingCard(),updatedCard.getExpirationCard(),updatedCard.getOwnerId(),updatedCard.getCardId());
    }

    /*
    public Card show(int card_id) {
        return jdbcTemplate.query("SELECT * FROM Cards WHERE card_id=?", new Object[]{card_id}, new BeanPropertyRowMapper<>(Card.class))
        .stream().findAny().orElse(null);
    }
    */
    public List<Card>  cardExpirationDate() {
       new GenCard().todayDate();

        return jdbcTemplate.query("SELECT * FROM Cards WHERE expiration_card=?",new Object[]{new GenCard ().todayDate()}, new BeanPropertyRowMapper<>(Card.class));
    }

    public void delete(int card_id) {
        jdbcTemplate.update("DELETE FROM Cards WHERE card_id=?", card_id);
    }
}
