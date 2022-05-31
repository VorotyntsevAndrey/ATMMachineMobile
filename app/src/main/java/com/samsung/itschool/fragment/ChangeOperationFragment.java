package com.samsung.itschool.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;

import com.samsung.itschool.MainActivity;
import com.samsung.itschool.R;
import com.samsung.itschool.adapter.CardSpinnerAdapter;
import com.samsung.itschool.adapter.UserAccountSpinnerAdapter;
import com.samsung.itschool.model.Card;
import com.samsung.itschool.model.Operation;
import com.samsung.itschool.model.UserAccount;
import com.samsung.itschool.nodb.ATMSimpleDB;
import com.samsung.itschool.rest.impl.ATMApiVolleyImpl;

public class ChangeOperationFragment extends Fragment {

    private EditText etOperationName;
    private AppCompatSpinner sp_card;
    private AppCompatSpinner sp_userAccount;

    private boolean checkEmpty() {
        boolean problem = false;

        if (TextUtils.isEmpty(etOperationName.getText().toString())) {
            etOperationName.setError("Обязательное поле");
            problem = true;
        }

        return problem;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_change_operation, container, false);

        etOperationName = view.findViewById(R.id.et_operation_info);

        Operation operation = (Operation) (getArguments().getSerializable(MainActivity.MSG_NAME));

        etOperationName.setText(operation.getOperationInfo());

        sp_card = view.findViewById(R.id.sp_card);
        CardSpinnerAdapter cardSpinnerAdapter =
                new CardSpinnerAdapter(
                        getActivity(),
                        R.layout.spinner_item,
                        ATMSimpleDB.CARD_LIST
                );
        sp_card.setAdapter(cardSpinnerAdapter);

        sp_userAccount = view.findViewById(R.id.sp_user_account);
        UserAccountSpinnerAdapter userAccountSpinnerAdapter =
                new UserAccountSpinnerAdapter(
                        getActivity(),
                        R.layout.spinner_item,
                        ATMSimpleDB.USER_ACCOUNT_LIST
                );
        sp_userAccount.setAdapter(userAccountSpinnerAdapter);

        AppCompatButton btn_add = view.findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (!checkEmpty()) {


                    new ATMApiVolleyImpl(getActivity())
                            .updateOperation(
                                    operation.getId(),
                                    etOperationName.getText().toString(),
                                    ((Card) sp_card.getSelectedItem()).getNumber(),
                                    ((UserAccount) sp_userAccount.getSelectedItem()).getLogin()
                            );


                    getActivity().getSupportFragmentManager().beginTransaction().remove(ChangeOperationFragment.this).commit();

                }
            }
        });

        return view;
    }

    @Override
    public void onStop() {
        super.onStop();

        ((MainActivity) getActivity()).update();
    }


}