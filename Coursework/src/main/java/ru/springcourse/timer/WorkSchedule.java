package ru.springcourse.timer;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.springcourse.controllers.CardController;
import ru.springcourse.dao.CardDAO;

@Component
public class WorkSchedule {

    static int i = 1;
//    @Scheduled(initialDelay = 5000,fixedDelayString = "5000")
//    public void runFirst()throws InterruptedException{
//        System.out.println("runFirst" + ++i);
//        Thread.sleep(5000);
//        System.out.println("First" + i);
//    }
//
//        public int i = 1;
//    @Scheduled(cron = "0/5 * * * * ?")
//    public void runFirst()throws InterruptedException{
////        i = i +1;
//////        System.out.println("runFirst" + i);
//        //CardController cardController = new CardController();
//        cardController.checkEndDate();
//    }

//    @Scheduled(fixedRate = 5000)
//    public void runSecond(){
//        System.out.println("runFirst" + ++i);
//
//    }
}
