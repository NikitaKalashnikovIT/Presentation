
package ru.springcourse.timer;

public class TimerDelay {

    private long delay ;//Сумма текущего времени плюс задержка

    public TimerDelay(){
        this.delay = 0;
    }

    //Задержка в миллисекундах - delay
    //Текущее время currentTimeMillis()
    public void setDelay(long delay) {
        this.delay = System.currentTimeMillis() + delay;
    }

    //Получить оставшееся время задержки
    public long getRemain(){
        if(delay >= System.currentTimeMillis())
            return (delay - System.currentTimeMillis());
        else
            return 0 ;
    }
}