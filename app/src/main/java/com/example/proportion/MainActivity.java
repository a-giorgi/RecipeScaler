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
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    private LayoutInflater inflater;

    private RecyclerView listContentView;
    private ArrayList<String> listContentElements;
    private RecyclerView.Adapter<ListElementViewHolder> listContentAdapter;

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
                viewHolder.title.setText(listContentElements.get(i));
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
                listContentElements.add("TITLETITLETITLETITLETIT");
                listContentAdapter.notifyItemInserted(listContentElements.size() - 1);

                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAnchorView(R.id.fab)
                        .setAction("Action", null).show();
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
/*
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    } */

    static class ListElementViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        

        public ListElementViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.list_element);

        }
    }
}