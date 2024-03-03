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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AddElementDialog.DialogCallback, TotalDialog.TotalDialogCallback {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    private LayoutInflater inflater;

    private RecyclerView listContentView;
    private ArrayList<Element> listContentElements;
    private RecyclerView.Adapter<ListElementViewHolder> listContentAdapter;

    private void showElementDialog(View view){
        AddElementDialog addElementDialog = new AddElementDialog(this);
        addElementDialog.show(getSupportFragmentManager(), "dialog");

    }
    private void showElementDialog(View view, String name, String quantity, int index){
        AddElementDialog addElementDialog = new AddElementDialog(this);
        addElementDialog.setData(name,quantity,index);
        addElementDialog.show(getSupportFragmentManager(), "dialog");

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
                viewHolder.setIndex(i);
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

        Button scaleButton = (Button) this.findViewById(R.id.button_scale);
        scaleButton.setOnClickListener(v ->{
            TotalDialog totalDialog = new TotalDialog(this);
            totalDialog.show(getSupportFragmentManager(), "totalDialog");
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
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResult(String name, double value, int index) {
        if(index<0) {
            listContentElements.add(new Element(name, value));
            listContentAdapter.notifyItemInserted(listContentElements.size() - 1);
        }else{
            listContentElements.get(index).setName(name);
            listContentElements.get(index).setQuantity(value);
            listContentAdapter.notifyItemChanged(index);
        }
    }

    @Override
    public void onTotalChanged(double value, int index) {
        Toast.makeText(this.getBaseContext(),String.valueOf(value),Toast.LENGTH_SHORT).show();
    }

    class ListElementViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView quantity;
        int index;

        public void setIndex(int index) {
            this.index = index;
        }

        public ListElementViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.list_element);
            quantity = (TextView) itemView.findViewById(R.id.list_element_qty);

            ImageButton editButton = (ImageButton) itemView.findViewById((R.id.edit_element_button));
            editButton.setOnClickListener(v -> {
                showElementDialog(v,title.getText().toString(), quantity.getText().toString(),index);
            });
            ImageButton deleteButton = (ImageButton) itemView.findViewById((R.id.delete_element_button));
            deleteButton.setOnClickListener(v -> {
                listContentElements.remove(index);
                listContentAdapter.notifyItemRemoved(index);
                listContentAdapter.notifyDataSetChanged();
            });


        }
    }
}