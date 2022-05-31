package com.samsung.itschool.mapper;

import com.samsung.itschool.model.Card;

import org.json.JSONException;
import org.json.JSONObject;

public class CardMapper {

    public Card cardFromOperationJsonArray(JSONObject jsonObject) {

        Card card = null;
        try {
            card = new Card(
                    jsonObject.getJSONObject("cardDto").getLong("id"),
                    jsonObject.getJSONObject("cardDto").getString("number")
            );
        } catch (JSONException e) {

            e.printStackTrace();
        }
        return card;
    }

    public Card cardFromJsonArray(JSONObject jsonObject) {

        Card card = null;
        try {

            card = new Card(
                    jsonObject.getLong("id"),
                    jsonObject.getString("number")
            );
        } catch (JSONException e) {

            e.printStackTrace();
        }
        return card;
    }
}