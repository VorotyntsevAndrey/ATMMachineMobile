package com.samsung.itschool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.samsung.itschool.R;
import com.samsung.itschool.model.Card;
import com.samsung.itschool.nodb.ATMSimpleDB;

import java.util.List;

public class CardSpinnerAdapter extends ArrayAdapter<Card> {

    public CardSpinnerAdapter(@NonNull Context context, int resource, @NonNull List<Card> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.spinner_item, null);
        }

        ((TextView) convertView.findViewById(R.id.tv_spinner_item))
                .setText(ATMSimpleDB.CARD_LIST.get(position).getNumber());

        return convertView;
    }


}