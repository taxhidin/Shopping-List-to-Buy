package tk.taxhidinkadiri.shopping;

import android.content.Context;
import android.content.Intent;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import tk.taxhidinkadiri.shopping.data.DatabaseHandler;
import tk.taxhidinkadiri.shopping.model.Item;
import tk.taxhidinkadiri.shopping.ui.RecyclerViewAdapter;

public class ListActivity extends AppCompatActivity {

    private static final String TAG = "ListActivity";
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<Item> itemList;
    private DatabaseHandler databaseHandler;
    private FloatingActionButton fab;
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    private Button saveButton;
    private EditText pop_edit_enter_item, pop_edit_quantity, pop_edit_measure_quantity;
    private EditText pop_edit_price, pop_edit_produced;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        recyclerView = findViewById(R.id.recyclerview);
        fab = findViewById(R.id.fab);

        databaseHandler = new DatabaseHandler(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemList = new ArrayList<>();

        itemList = databaseHandler.getAllItems();

        for (Item item : itemList) {
            Log.d(TAG, "onCreate: " + item.getEdit_enter_item());
        }

        recyclerViewAdapter = new RecyclerViewAdapter(this, itemList);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPopDialog();
            }
        });


    }

    private void createPopDialog() {
        builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.popup, null);
        pop_edit_enter_item = view.findViewById(R.id.enter_item_edit);
        pop_edit_quantity = view.findViewById(R.id.quantity_edit);
        pop_edit_measure_quantity = view.findViewById(R.id.edit_measure_quantity_other);
        pop_edit_price = view.findViewById(R.id.edit_price);
        pop_edit_produced = view.findViewById(R.id.edit_produced);
        saveButton=view.findViewById(R.id.savebutton);

        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.show();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!pop_edit_enter_item.getText().toString().isEmpty()
                        && !pop_edit_quantity.getText().toString().isEmpty()
                        && !pop_edit_measure_quantity.getText().toString().isEmpty()
                        && !pop_edit_price.getText().toString().isEmpty()
                        && !pop_edit_produced.getText().toString().isEmpty() ) {

                    saveItem(v);

                }

                else {
                    Snackbar.make(v, "Empty Fields not Allowed", Snackbar.LENGTH_SHORT)
                            .show();
                }



            }




        });

    }

    private void saveItem(View v) {

        Item item=new Item();
        String new_edit_enter=pop_edit_enter_item.getText().toString().trim();
        double new_edit_quantity= Double.parseDouble(pop_edit_quantity.getText().toString().trim());
        String new_edit_measure_other=pop_edit_measure_quantity.getText().toString().trim();
        double new_edit_price= Double.parseDouble(pop_edit_price.getText().toString().trim());
        String new_product=pop_edit_produced.getText().toString().trim();

        item.setEdit_enter_item(new_edit_enter);
        item.setEdit_quantity(new_edit_quantity);
        item.setEdit_measure_quantity_other(new_edit_measure_other);
        item.setEdit_price(new_edit_price);
        item.setEdit_produced(new_product);

        databaseHandler.addItem(item);

        Snackbar.make(v, "Item Saved",Snackbar.LENGTH_SHORT)
                .show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                alertDialog.dismiss();
                Intent intent=new Intent(ListActivity.this, ListActivity.class);
                startActivity(intent);
                finish();
            }
        },1200);


    }


}
