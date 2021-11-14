package ru.springcourse.mail;
import ru.springcourse.controllers.CardController;
import ru.springcourse.dao.CardDAO;
import ru.springcourse.repository.PersonRepository;
import ru.springcourse.timer.TimerDelay;
import ru.springcourse.mail.ssl.Sender;

import java.util.ArrayList;
import java.util.List;

public class Mail {

    private static ru.springcourse.mail.tls.Sender tlsSender = new ru.springcourse.mail.tls.Sender("nkalashnikov575@gmail.com", "dnrcmxwwtyapmsbp");
    private static Sender sslSender = new Sender("nkalashnikov575@gmail.com", "dnrcmxwwtyapmsbp");

    public void send (String message,String toEmail) {

            tlsSender.send("СберБанк", message, "nkalashnikov575@gmail.com", toEmail);
            //sslSender.send("СберБанк", message, "nkalashnikov575@gmail.com", toEmail);
            }
}
