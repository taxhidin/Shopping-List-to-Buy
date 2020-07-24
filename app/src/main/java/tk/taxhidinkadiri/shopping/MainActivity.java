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
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import tk.taxhidinkadiri.shopping.data.DatabaseHandler;
import tk.taxhidinkadiri.shopping.model.Item;

public class MainActivity extends AppCompatActivity {
  private AlertDialog.Builder builder;
  private AlertDialog dialog;
  private Button saveButton;
  private EditText edit_enter_item, edit_quantity, edit_measure_quantity_other, edit_price, edit_produced;
  private TextView text_title, text_categories, text_price, text_produced;
  private DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        text_title=findViewById(R.id.title);
        text_categories=findViewById(R.id.text_Categories);
        text_price=findViewById(R.id.text_price);
        text_produced=findViewById(R.id.edit_produced);


            byPassActivity();


        databaseHandler= new DatabaseHandler(this);
        List <Item> items=databaseHandler.getAllItems();
        for (Item item:items){
            Log.d("Main", "onCreate: " +item.getEdit_enter_item());
        }

        text_title =(TextView) findViewById(R.id.title);
        saveButton = (Button) findViewById(R.id.savebutton);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                createPopupDialog();

            }
        });
    }



    private void byPassActivity() {
        try {
            if (databaseHandler.getItemsCount() > 0) {
                startActivity(new Intent(MainActivity.this, ListActivity.class));
                finish();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }




    private void saveItem(View view) {
        Item item=new Item();
        //  private EditText edit_enter_item, edit_quantity,
        //  edit_measure_quantity_other, edit_price, edit_produced;
        String new_enter_name=edit_enter_item.getText().toString().trim();
        double new_edit_quantity=Double.parseDouble(edit_quantity.getText().toString().trim());
        String new_edit_measure_other=edit_measure_quantity_other.getText().toString().trim();
        double new_edit_price=Double.valueOf(edit_price.getText().toString().trim());
        String new_edit_produced=edit_produced.getText().toString().trim();

        item.setEdit_enter_item(new_enter_name);
        item.setEdit_quantity(new_edit_quantity);
        item.setEdit_measure_quantity_other(new_edit_measure_other);
        item.setEdit_price(new_edit_price);
        item.setEdit_produced(new_edit_produced);

        databaseHandler.addItem(item);

        Snackbar.make(view,"Item Saved",Snackbar.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
              //  Intent intent=new Intent(MainActivity.this, ListActivity.class);
                startActivity(new Intent(MainActivity.this, ListActivity.class));
            }
        },1200);


    }



    private void createPopupDialog() {

        builder =new AlertDialog.Builder(MainActivity.this);
        View view=getLayoutInflater().inflate(R.layout.popup,null);

        edit_enter_item=view.findViewById(R.id.enter_item_edit);
        edit_quantity=view.findViewById(R.id.quantity_edit);
        edit_measure_quantity_other=view.findViewById(R.id.edit_measure_quantity_other);
        edit_price=view.findViewById(R.id.edit_price);
        edit_produced=view.findViewById(R.id.edit_produced);

        text_title=view.findViewById(R.id.title);
        text_categories=view.findViewById(R.id.text_Categories);
        text_price=view.findViewById(R.id.text_price);
        text_produced=view.findViewById(R.id.edit_produced);
        saveButton=view.findViewById(R.id.savebutton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edit_enter_item.getText().toString().isEmpty()
                        && !edit_quantity.getText().toString().isEmpty()
                        && !edit_measure_quantity_other.getText().toString().isEmpty()
                        && !edit_price.getText().toString().isEmpty()
                        && !edit_produced.getText().toString().isEmpty()
                )

                {

                    saveItem(v);
                }

                else {

                    Snackbar.make(v,"Empty Fields not Allowed",Snackbar.LENGTH_SHORT).show();
                }


            }
        });
        builder.setView(view);
        dialog=builder.create();
        dialog.show();



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
}
