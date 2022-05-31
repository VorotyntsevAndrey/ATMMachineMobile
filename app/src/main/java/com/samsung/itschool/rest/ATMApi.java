package com.samsung.itschool.rest;

import com.samsung.itschool.model.Operation;

public interface ATMApi {

    void fillOperationList();

    void fillCardList();

    void fillUserAccountList();

    void newOperation(Operation operation);

    void updateOperation(Long id, String newOperationInfo, String newCardNumber, String newUserAccountLogin);

    void deleteOperation(Long id);
}
