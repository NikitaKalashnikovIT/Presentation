package ru.springcourse.models;
import java.sql.Date;
/**
 * @author Nikita Kalashnikov
 */
public class Card {

    private int     cardId;
    private long    cardNumber;
    private int     cvcNumber;
    private Date    issuingCard;
    private Date    expirationCard;
    private int     ownerId;


    public int  getCardId()         {return cardId;}

    public long getCardNumber()     {return cardNumber;}

    public int  getCvcNumber()      {return cvcNumber;}

    public Date getIssuingCard ()    {return issuingCard;}

    public Date getExpirationCard() {return expirationCard;}

    public int  getOwnerId()        {return ownerId;}



    public void setCardId(int cardId)                   {this.cardId = cardId;}

    public void setCardNumber(long cardNumber)          {this.cardNumber = cardNumber;}

    public void setCvcNumber(int cvcNumber)             {this.cvcNumber = cvcNumber;}

    public void setIssuingCard (Date issuingCard)       {this.issuingCard = issuingCard;}

    public void setExpirationCard(Date expirationCard)  {this.expirationCard = expirationCard;}

    public void setOwnerId(int ownerId)                 {this.ownerId = ownerId;}



    public Card(int cardId, long cardNumber, int cvcNumber, Date issuingCard, Date expirationCard, int ownerId) {
        this.cardId = cardId;
        this.cardNumber = cardNumber;
        this.cvcNumber = cvcNumber;
        this.issuingCard = issuingCard;
        this.expirationCard = expirationCard;
        this.ownerId = ownerId;
    }

    public Card(){}
}
