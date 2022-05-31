package com.samsung.itschool.rest.impl;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.samsung.itschool.MainActivity;
import com.samsung.itschool.mapper.CardMapper;
import com.samsung.itschool.mapper.OperationMapper;
import com.samsung.itschool.mapper.UserAccountMapper;
import com.samsung.itschool.model.Card;
import com.samsung.itschool.model.Operation;
import com.samsung.itschool.model.UserAccount;
import com.samsung.itschool.nodb.ATMSimpleDB;
import com.samsung.itschool.rest.ATMApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ATMApiVolleyImpl implements ATMApi {

    public static final String BASE_URL = "http://192.168.123.65:8080";
    private final Context context;

    private Response.ErrorListener errorListener;

    public ATMApiVolleyImpl(Context context) {

        this.context = context;
        errorListener = new ErrorListenerImpl();
    }

    @Override
    public void fillOperationList() {

        RequestQueue queue = Volley.newRequestQueue(context);
        String url = BASE_URL + "/operations";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        try {

                           ATMSimpleDB.OPERATION_LIST.clear();
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject jsonObject = response.getJSONObject(i);

                                UserAccount userAccount = new UserAccountMapper().userAccountFromOperationJsonArray(jsonObject);

                                Card card = new CardMapper().cardFromOperationJsonArray(jsonObject);

                                Operation operation = new OperationMapper().operationFromJsonArray(jsonObject, userAccount, card);
                                ATMSimpleDB.OPERATION_LIST.add(operation);
                            }
                            Log.d("OPERATIONS_LIST", ATMSimpleDB.OPERATION_LIST.toString());
                            ((MainActivity) context).update();
                        } catch (JSONException e) {

                            Log.d("OPERATIONS_LIST", e.getMessage());
                        }

                    }
                },

                errorListener
        );

        queue.add(jsonArrayRequest);
    }

    @Override
    public void fillUserAccountList() {

        RequestQueue queue = Volley.newRequestQueue(context);
        String url = BASE_URL + "/useraccounts";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject jsonObject = response.getJSONObject(i);

                                UserAccount userAccount = new UserAccountMapper().userAccountFromJsonArray(jsonObject);

                                ATMSimpleDB.USER_ACCOUNT_LIST.add(userAccount);
                            }
                            Log.d("USER_ACCOUNT_LIST", ATMSimpleDB.USER_ACCOUNT_LIST.toString());
                        } catch (JSONException e) {

                            Log.d("USER_ACCOUNT_LIST", e.getMessage());
                        }

                    }
                },

                errorListener
        );

        queue.add(jsonArrayRequest);
    }

    @Override
    public void fillCardList() {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = BASE_URL + "/cards";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject jsonObject = response.getJSONObject(i);

                                Card card = new CardMapper().cardFromJsonArray(jsonObject);

                               ATMSimpleDB.CARD_LIST.add(card);
                            }
                            Log.d("CARDS_LIST", ATMSimpleDB.CARD_LIST.toString());
                        } catch (JSONException e) {

                            Log.d("CARDS_LIST", e.getMessage());
                        }

                    }
                },

                errorListener
        );

        queue.add(jsonArrayRequest);
    }

    @Override
    public void newOperation(Operation operation) {

        RequestQueue queue = Volley.newRequestQueue(context);
        String url = BASE_URL + "/operations";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("Response", response);

                        // выгрузка заново плохо?
                        fillOperationList();
                    }
                },

                errorListener
        ) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                params.put("operationInfo", operation.getOperationInfo());
                params.put("cardNumber", operation.getCard().getNumber());
                params.put("userAccountLogin", operation.getUserAccount().getLogin());

                return params;
            }
        };

        queue.add(postRequest);
    }

    @Override
    public void updateOperation(Long id, String newOperationInfo, String newCardNumber, String newUserAccountLogin) {

        RequestQueue queue = Volley.newRequestQueue(context);
        String url = BASE_URL + "/operations/" + id + "/";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("Response", response);

                        //стоит обновлять локально
                        //но пока так
                        fillOperationList();
                    }
                },

                errorListener
        ) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                params.put("newOperationInfo", newOperationInfo);
                params.put("newCardNumber", newCardNumber);
                params.put("newUserAccountLogin", newUserAccountLogin);
                params.put("id", String.valueOf(id));

                return params;
            }
        };

        queue.add(postRequest);
    }

    @Override
    public void deleteOperation(Long id) {

        RequestQueue queue = Volley.newRequestQueue(context);
        String url = BASE_URL + "/operations/" + id;

        StringRequest dr = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        fillOperationList();
                    }
                },

                errorListener
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(id));

                return params;
            }
        };

        queue.add(dr);
    }

    private class ErrorListenerImpl implements Response.ErrorListener {


        @Override
        public void onErrorResponse(VolleyError error) {

            Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
        }
    }
}
