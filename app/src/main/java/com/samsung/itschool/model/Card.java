package com.samsung.itschool.model;

import com.samsung.itschool.model.enums.CardType;

import java.io.Serializable;


public class Card implements Serializable {

    private Long id;

    private String number;

    private String validThru;

    private Integer cardSecurityCode;

    private CardType cardType;

    public Card(Long id, String number) {
        this.id = id;
        this.number = number;
    }

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public String getValidThru() {
        return validThru;
    }

    public Integer getCardSecurityCode() {
        return cardSecurityCode;
    }

    public CardType getCardType() {
        return cardType;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", number='" + number +
                '}';
    }
}
