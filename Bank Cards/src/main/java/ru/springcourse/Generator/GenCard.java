package ru.springcourse.Generator;

import org.springframework.stereotype.Controller;
import ru.springcourse.models.Card;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import ru.springcourse.dao.CardDAO;

/**
 * @author Nikita Kalashnikov
 */
@Controller
public class GenCard {
    static final long mask = 0xFFFF_FFFF_FFFF_FL;
    static final long minCardNumberLength = 1000_0000_0000_0000L;

    static final int cardValidityPeriodYear  = 5;// Период действия карты в годах
    static final int cardValidityPeriodMonth = 0;// Период действия карты  в месяцах
    static final int cardValidityPeriodMonthDay = 0; //период действия карты в днях

//Генератор 3 значного CVC кода карты
    public int cvc(){
        int cvc = 1 ;
        while(99 > cvc) {cvc = (int) (1000 * Math.random());}
        return cvc;
    }
//Генератор 16 значного кода карты
    public long cardNumber() {
        long result;
        do {
            result = Double.toString(Math.random()).hashCode();
            result = 31 * result + cvc();
            result = 31 * result + cvc();
            result = Math.abs(result);
            result = (result << 20 | result) & mask;
        }
        while (result < minCardNumberLength);
        return result;
    }


    //Конвертер даты из java.util.Date в java.sql.Date
    public  java.sql.Date convertJavaDateToSqlDate(java.util.Date date) {
        return new java.sql.Date(date.getTime());
    }

    // Получить сегодняшнюю дату в формате sql.Date подходящем для PostgreSQL таблиц.
    public  java.sql.Date todayDate(){
        return convertJavaDateToSqlDate (new Date());
    }

    //Получить сегодняшнюю дату - todayDate плюс период действия карты - card Validity Period.
    public  java.sql.Date expirationDate() {
        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.YEAR, cardValidityPeriodYear);
        calendar.add(Calendar.MONTH, cardValidityPeriodMonth);
        calendar.add(Calendar.DAY_OF_MONTH,cardValidityPeriodMonthDay);
        return convertJavaDateToSqlDate( calendar.getTime());
    }

    //Сборка всех данных карты
    public Card allData(int idPerson) {
        Card card = new Card();
        card.setCardNumber(cardNumber());
        card.setCvcNumber(cvc());
        card.setExpirationCard(expirationDate());
        card.setIssuingCard(todayDate());
        card.setOwnerId(idPerson);
        return card;
    }
}
