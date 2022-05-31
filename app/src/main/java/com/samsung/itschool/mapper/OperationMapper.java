package com.samsung.itschool.mapper;

import com.samsung.itschool.model.Card;
import com.samsung.itschool.model.Operation;
import com.samsung.itschool.model.UserAccount;

import org.json.JSONException;
import org.json.JSONObject;

public class OperationMapper {

    public Operation operationFromJsonArray(JSONObject jsonObject, UserAccount userAccount, Card card) {

        Operation operation = null;

        try {
            operation = new Operation(
                    jsonObject.getLong("id"),
                    jsonObject.getString("operationInfo"),
                    userAccount,
                    card
            );
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return operation;
    }

}