package com.samsung.itschool.mapper;

import com.samsung.itschool.model.UserAccount;

import org.json.JSONException;
import org.json.JSONObject;

public class UserAccountMapper {

    public UserAccount userAccountFromOperationJsonArray(JSONObject jsonObject) {

        UserAccount userAccount = null;
        try {

            userAccount = new UserAccount(
                    jsonObject.getJSONObject("userAccountDto").getLong("id"),
                    jsonObject.getJSONObject("userAccountDto").getString("login")
            );
        } catch (JSONException e) {

            e.printStackTrace();
        }
        return userAccount;
    }

    public UserAccount userAccountFromJsonArray(JSONObject jsonObject) {

        UserAccount userAccount = null;
        try {

            userAccount = new UserAccount(
                    jsonObject.getLong("id"),
                    jsonObject.getString("login")
            );
        } catch (JSONException e) {

            e.printStackTrace();
        }
        return userAccount;
    }

}