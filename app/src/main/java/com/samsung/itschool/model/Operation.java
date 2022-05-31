package com.samsung.itschool.model;


import com.samsung.itschool.model.enums.OperationType;

import java.io.Serializable;
import java.sql.Date;

public class Operation implements Serializable {

    private Long id;

    private Date date;

    private OperationType operationType;

    private String operationInfo;

    private UserAccount userAccount;

    private Card card;

    public Operation(Long id, String operationInfo, UserAccount userAccount, Card card) {
        this.id = id;
        this.operationInfo = operationInfo;
        this.userAccount = userAccount;
        this.card = card;
    }

    public Long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public String getOperationInfo() {
        return operationInfo;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public Card getCard() {
        return card;
    }

    @Override
    public String toString() {
        return "Operation{" +
                "id=" + id +
                ", operationInfo='" + operationInfo + '\'' +
                ", userAccount=" + userAccount +
                ", card=" + card +
                '}';
    }
}
