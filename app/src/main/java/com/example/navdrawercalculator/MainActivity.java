package com.example.navdrawercalculator;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private EditText num1, num2;
    private int num1Int, num2Int;
    private ArrayList<String> answers = new ArrayList<>();;
    private ListView listView;
    private String operation = "";
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
                inputHandler();
                Snackbar.make(view, "Equation has been calculated", Snackbar.LENGTH_LONG)
                        .setAction("Undo", undo).show();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        adapter = new ArrayAdapter(MainActivity.this, R.layout.calculation_item, answers);
        listView.setAdapter(adapter);
    }

    public void inputHandler() {
        String num1Str = num1.getText().toString();
        String num2Str = num2.getText().toString();

        num1Int = Integer.parseInt(num1Str);
        num2Int = Integer.parseInt(num2Str);

        int result = performOperation(num1Int, num2Int);

        answers.add(num1Str + operation + num2Str + " = " + result);
        adapter.notifyDataSetChanged();

    }

    private int performOperation(int num1, int num2) {
        int result = 0;

            switch (operation) {
                case "/":
                    result = num1 / num2;
                    break;
                case "-":
                    result = num1 - num2;
                    break;
                case "*":
                    result = num1 * num2;
                    break;
                case "+":
                    result = num1 + num2;
                    break;
            }

            return result;
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

        return true;
    }
}
