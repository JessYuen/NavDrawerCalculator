package com.example.navdrawercalculator;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private EditText num1, num2;
    private ArrayList<String> answers = new ArrayList<>();;
    private ListView listView;
    private String operation = "+";
    private DecimalFormat decimalFormat = new DecimalFormat("#.##########");

    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = findViewById(R.id.listView);
        num1 = findViewById(R.id.etNum1);
        num2 = findViewById(R.id.etNum2);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inputHandler()) {
                    Snackbar.make(view, "Equation has been calculated", Snackbar.LENGTH_LONG)
                            .setAction("Undo", undo).show();
                } else {
                    Toast.makeText(MainActivity.this, "Please enter another number", Toast.LENGTH_LONG).show();
                }
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, answers);
        listView.setAdapter(adapter);
    }

    public boolean inputHandler() {
        String num1Str = num1.getText().toString();
        String num2Str = num2.getText().toString();

        if (!num1Str.isEmpty() && !num2Str.isEmpty()) {
            Double num1Int = Double.parseDouble(num1Str);
            Double num2Int = Double.parseDouble(num2Str);

            String result = performOperation(num1Int, num2Int);

            answers.add(num1Str + operation + num2Str + " = " + result);
            adapter.notifyDataSetChanged();
        } else {
            return false;
        }

        return true;

    }

    private String performOperation(Double num1, Double num2) {
        Double result = Double.NaN;

        switch (operation) {
            case "+":
                result = num1 + num2;
                break;
            case "-":
                result = num1 - num2;
                break;
            case "/":
                if (num1 == 0 && num2 == 0) {
                    result = 0.0;
                } else {
                    result = num1 / num2;
                }
                break;
            case "*":
                result = num1 * num2;
                break;
            default:

        }
        return decimalFormat.format(result);

        }

    View.OnClickListener undo = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            answers.remove(answers.size() - 1);
            adapter.notifyDataSetChanged();
        }
    };

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        switch (id) {
            case R.id.nav_addition:
                operation = "+";
                break;
            case R.id.nav_subtraction:
                operation = "-";
                break;
            case R.id.nav_multiplication:
                operation = "*";
                break;
            case R.id.nav_division:
                operation = "/";
                break;
            default:

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

}
