package com.example.proportion;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;

import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proportion.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AddElementDialog.DialogCallback {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    private LayoutInflater inflater;

    private RecyclerView listContentView;
    private ArrayList<Element> listContentElements;
    private RecyclerView.Adapter<ListElementViewHolder> listContentAdapter;

    private void showElementDialog(View view){
        AddElementDialog addElementDialog = new AddElementDialog(this);
        addElementDialog.show(getSupportFragmentManager(), "dialog");


        /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAnchorView(R.id.fab)
                .setAction("Action", null).show();*/
    }
    private void showElementDialog(View view, String name, String quantity){
        AddElementDialog addElementDialog = new AddElementDialog(this);
        addElementDialog.show(getSupportFragmentManager(), "dialog");


        /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAnchorView(R.id.fab)
                .setAction("Action", null).show();*/
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        inflater = LayoutInflater.from(this.getBaseContext());

        listContentElements = new ArrayList<>();
        listContentView = (RecyclerView) findViewById(R.id.main_list);
        listContentAdapter = new RecyclerView.Adapter<ListElementViewHolder>() {

            @NonNull
            @Override
            public ListElementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_element
                        , parent, false);
                return new ListElementViewHolder(view);
            }

            @Override
            public void onBindViewHolder(@NonNull ListElementViewHolder viewHolder, int i) {
                viewHolder.title.setText(listContentElements.get(i).getName());
                viewHolder.quantity.setText(String.valueOf(listContentElements.get(i).getQuantity()));

            }

            @Override
            public int getItemCount() {
                return listContentElements.size();
            }
        };

        listContentView.setAdapter(listContentAdapter);
        listContentView.setLayoutManager(new LinearLayoutManager(this));

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showElementDialog(view);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResult(String name, double value) {
        //Toast.makeText(this, "Name: " + name + ", Value: " + value, Toast.LENGTH_SHORT).show();
        listContentElements.add(new Element(name,value));
        listContentAdapter.notifyItemInserted(listContentElements.size() - 1);
    }

    class ListElementViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView quantity;
        

        public ListElementViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.list_element);
            quantity = (TextView) itemView.findViewById(R.id.list_element_qty);

            ImageButton editButton = (ImageButton) itemView.findViewById((R.id.edit_element_button));
            editButton.setOnClickListener(v -> {
                showElementDialog(v,title.getText().toString(), quantity.getText().toString());
            });

        }
    }
}