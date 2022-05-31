package com.samsung.itschool.nodb;


import com.samsung.itschool.model.Card;
import com.samsung.itschool.model.Operation;
import com.samsung.itschool.model.UserAccount;

import java.util.ArrayList;
import java.util.List;

public class ATMSimpleDB {

    private ATMSimpleDB(){}

    public static final List<Operation> OPERATION_LIST = new ArrayList<>();

    public static final List<Card> CARD_LIST = new ArrayList<>();

    public static final List<UserAccount> USER_ACCOUNT_LIST = new ArrayList<>();
}