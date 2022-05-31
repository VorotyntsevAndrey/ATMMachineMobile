package com.samsung.itschool.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.samsung.itschool.MainActivity;
import com.samsung.itschool.R;
import com.samsung.itschool.fragment.ChangeOperationFragment;
import com.samsung.itschool.model.Operation;

import java.util.List;

public class OperationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater inflater;
    private List<Operation> operationList;
    private Context context;

    public OperationAdapter(Context context, List<Operation> operationList) {

        this.inflater = LayoutInflater.from(context);
        this.operationList = operationList;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvOperationInfo,
                tvCard,
                tvUserAccount;
        private final LinearLayout ll_item;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

            ll_item = itemView.findViewById(R.id.ll_item);
            tvOperationInfo = itemView.findViewById(R.id.tv_operation_info);
            tvCard = itemView.findViewById(R.id.tv_card);
            tvUserAccount = itemView.findViewById(R.id.tv_user_account);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_operation_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull RecyclerView.ViewHolder holder,
            @SuppressLint("RecyclerView") int position
    ) {

        Operation operation = operationList.get(position);

        ((MyViewHolder) holder).tvOperationInfo.setText(operation.getOperationInfo());
        ((MyViewHolder) holder).tvCard.setText(operation.getCard().getNumber());
        ((MyViewHolder) holder).tvUserAccount.setText(operation.getUserAccount().getLogin());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangeOperationFragment changeClientFragment = new ChangeOperationFragment();

                Bundle bundle = new Bundle();
                bundle.putSerializable(MainActivity.MSG_NAME, operationList.get(position));
                changeClientFragment.setArguments(bundle);

                ((AppCompatActivity) context).getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fl_main, changeClientFragment)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {

        return operationList.size();
    }
}