package com.example.productsmanager2.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.example.productsmanager2.Product;
import com.example.productsmanager2.R;
import com.example.productsmanager2.database.DatabaseAdapter;

public class ProductActivity extends AppCompatActivity {
    private EditText nameBox;
    private EditText priceBox;
    private EditText countBox;
    //    private EditText isBoughtBox;
    private Button deleteBtn;
    private Button saveBtn;
    private DatabaseAdapter adapter;
    private long productId = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);



        nameBox = findViewById(R.id.name);
        priceBox = findViewById(R.id.price);
        countBox = findViewById(R.id.count);
//        isBoughtBox = findViewById(R.id.bought);
        deleteBtn = findViewById(R.id.deleteBtn);
        saveBtn = findViewById(R.id.saveBtn);
        adapter = new DatabaseAdapter(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            productId = extras.getLong("id");
        }
        if (productId > 0) {
            adapter.open();
            Product product = adapter.getOne(productId);
            nameBox.setText(product.getName());
            priceBox.setText(String.valueOf(product.getPrice()));
            countBox.setText(String.valueOf(product.getCount()));
//            isBoughtBox.setText(String.valueOf(product.isBought()));
            adapter.close();
        } else {
            deleteBtn.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void save(View view) {
        String name = nameBox.getText().toString();
        int price = Integer.parseInt(priceBox.getText().toString());
        int count = Integer.parseInt(countBox.getText().toString());
//        boolean bought = Boolean.parseBoolean(isBoughtBox.getText().toString());
        Product product = new Product(productId, name, price, count);
        adapter.open();
        if (productId > 0) {
            adapter.update(product);
        } else {
            adapter.insert(product);
        }
        adapter.close();
        goHome();
    }

    public void delete(View view) {
        adapter.open();
        adapter.delete(productId);
        goHome();
    }

    private void goHome() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}
