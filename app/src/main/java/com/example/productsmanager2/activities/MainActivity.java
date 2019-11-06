package com.example.productsmanager2.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.productsmanager2.Product;
import com.example.productsmanager2.R;
import com.example.productsmanager2.database.DatabaseAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView productsList;
    private ArrayAdapter<Product> arrayAdapter;
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        boolean darkTheme = loadTheme();
        setTheme(darkTheme ? R.style.AppThemeDark : R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout = findViewById(R.id.mainLayout);

        productsList = findViewById(R.id.list);
        productsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product product = arrayAdapter.getItem(position);
                if (product != null) {
                    Intent intent = new Intent(getApplicationContext(), ProductActivity.class);
                    intent.putExtra("id", product.getId());
                    intent.putExtra("click", 25);
                    startActivity(intent);
                }
            }
        });
        if (loadBackground())
            layout.setBackgroundColor(Color.parseColor("#38ae1d"));

    }

    public boolean loadTheme() {
        SharedPreferences sharedPreferences = getSharedPreferences(SharedPrefs.
                SHARED_PREFS, MODE_PRIVATE);
        return sharedPreferences.getBoolean(SharedPrefs.PREF_TEXT, false);

    }

    public boolean loadBackground() {
        SharedPreferences sharedPreferences = getSharedPreferences(SharedPrefs.
                SHARED_PREFS, MODE_PRIVATE);
        return sharedPreferences.getBoolean(SharedPrefs.PREF_BACKGROUND, false);
    }
    @Override
    protected void onResume() {
        super.onResume();

        DatabaseAdapter adapter = new DatabaseAdapter(this);
        adapter.open();

        List<Product> products = adapter.listAll();

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, products);
        productsList.setAdapter(arrayAdapter);
        adapter.close();
    }

    public void add(View view) {
        Intent intent = new Intent(this, ProductActivity.class);
        startActivity(intent);
    }

    public void openSettings(View view){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}