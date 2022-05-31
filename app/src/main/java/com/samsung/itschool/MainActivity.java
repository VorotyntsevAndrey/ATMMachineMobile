package com.samsung.itschool;

import static com.samsung.itschool.nodb.ATMSimpleDB.OPERATION_LIST;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.samsung.itschool.adapter.OperationAdapter;
import com.samsung.itschool.fragment.AddOperationFragment;
import com.samsung.itschool.model.Operation;
import com.samsung.itschool.rest.ATMApi;
import com.samsung.itschool.rest.impl.ATMApiVolleyImpl;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String MSG_NAME = "operationFromListByPos";

    private AppCompatButton btnAdd;

    private FragmentTransaction transaction;

    private OperationAdapter operationAdapter;

    private RecyclerView rvOperations;

    private ItemTouchHelper.SimpleCallback simpleItemTouchCallback;

    private final ATMApi atmApi = new ATMApiVolleyImpl(this);

    @Override
    protected void onResume() {
        super.onResume();

        atmApi.fillOperationList();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        atmApi.fillCardList();
        atmApi.fillUserAccountList();

        rvOperations = findViewById(R.id.rv_operations);
        operationAdapter = new OperationAdapter(this, OPERATION_LIST);
        rvOperations.setAdapter(operationAdapter);

        btnAdd = findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(view -> {

            transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.fl_main, new AddOperationFragment());
            transaction.commit();
        });

        simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT
        ) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                Toast.makeText(MainActivity.this, "on Move", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int position = viewHolder.getAdapterPosition();
                Operation operation = OPERATION_LIST.get(position);

                if (swipeDir == ItemTouchHelper.LEFT) {
                    Toast.makeText(MainActivity.this, "Удалено", Toast.LENGTH_SHORT).show();
                    atmApi.deleteOperation(operation.getId());

                }

            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(rvOperations);
    }

    @Override
    public void onBackPressed() {

        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        int size = fragments.size();
        if (size > 0)
            getSupportFragmentManager().beginTransaction().remove(fragments.get(size-1)).commit();
        else
            finish();
    }

    public void update() {

        operationAdapter.notifyDataSetChanged();
    }

}