package ru.springcourse.repository;

import ru.springcourse.models.Card;
import java.util.List;

public interface CardRepository<T> {
    public List<T> index(int id);

    public Card show(int id);

    public boolean save(Card card);

    public List<T> cardExpirationDate();

    public void delete(int id);
}
