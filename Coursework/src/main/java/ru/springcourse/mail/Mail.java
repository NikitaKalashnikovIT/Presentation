package ru.springcourse.mail;
import ru.springcourse.controllers.CardController;
import ru.springcourse.dao.CardDAO;
import ru.springcourse.repository.PersonRepository;
import ru.springcourse.timer.TimerDelay;
import ru.springcourse.mail.ssl.Sender;

import java.util.ArrayList;
import java.util.List;

public class Mail {
    private static final long millisecond = 1000 * 60;//1мин = 60000милисек
    private static final long Minutes = 1;
    private static ru.springcourse.mail.tls.Sender tlsSender = new ru.springcourse.mail.tls.Sender("nkalashnikov575@gmail.com", "dnrcmxwwtyapmsbp");
    private static Sender sslSender = new Sender("nkalashnikov575@gmail.com", "dnrcmxwwtyapmsbp");

    public void send (String message,String toEmail) {

            tlsSender.send("СберБанк", message, "nkalashnikov575@gmail.com", toEmail);
            //sslSender.send("СберБанк", message, "nkalashnikov575@gmail.com", "katpat888@yandex.ru");
            }



}
