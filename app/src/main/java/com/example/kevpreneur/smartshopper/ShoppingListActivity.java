package com.example.kevpreneur.smartshopper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class ShoppingListActivity extends AppCompatActivity {
    private ListView mShoppingList;
    private EditText mItemEdit;
    private Button mAddButton;

    private ArrayAdapter<String> mAdapter;
    private ArrayList<String> shoppingList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        mShoppingList = (ListView) findViewById(R.id.shopping_listView);
        mItemEdit = (EditText) findViewById(R.id.item_editText);
        mAddButton = (Button) findViewById(R.id.add_button);

        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        mShoppingList.setAdapter(mAdapter);

        if(!shoppingList.isEmpty()) {
            for (String p: shoppingList
                    ) {
                mAdapter.add(p);
            }
        }

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = mItemEdit.getText().toString();
                mAdapter.add(item);
                shoppingList.add(item);
                mAdapter.notifyDataSetChanged();
                mItemEdit.setText("");
            }
        });
    }


}
