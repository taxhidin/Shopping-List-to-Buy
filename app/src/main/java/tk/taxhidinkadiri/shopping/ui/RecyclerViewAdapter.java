package tk.taxhidinkadiri.shopping.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.hardware.input.InputManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;



import java.util.List;

import tk.taxhidinkadiri.shopping.R;
import tk.taxhidinkadiri.shopping.data.DatabaseHandler;
import tk.taxhidinkadiri.shopping.model.Item;

import static tk.taxhidinkadiri.shopping.R.id.item_product;

public class RecyclerViewAdapter extends Adapter<RecyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<Item> itemList;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private LayoutInflater inflater;
    private Button noButton;
    private Button yesButton;

    public RecyclerViewAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_row, viewGroup, false);



        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder viewHolder, int position) {

        Item item = itemList.get(position);

        viewHolder.item_list_enter_item.setText(item.getEdit_enter_item());
        viewHolder.item_list_quantity.setText((String.valueOf(item.getEdit_quantity())));
        viewHolder.item_measure_quantity_other.setText(item.getEdit_measure_quantity_other());
        viewHolder.item_list_price.setText(String.valueOf(item.getEdit_price()));
        viewHolder.item_list_product.setText(item.getEdit_produced());
        viewHolder.dateAdded.setText(item.getDateItemAdded());


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView item_list_title, item_list_enter_item, item_list_quantity;

        public TextView item_measure_quantity_other, item_list_price, item_list_product;
        public TextView dateAdded, enterItem_list_row;

        public Button item_list_editButton, item_list_deleteButton;

        public int item_list_id;

        public ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);
            context = ctx;

            item_list_title = itemView.findViewById(R.id.item_name_title);
            item_list_enter_item = itemView.findViewById(R.id.item_enter_item);
            item_list_quantity = itemView.findViewById(R.id.item_quantity);
            item_measure_quantity_other = itemView.findViewById(R.id.item_measure_quantity);
            item_list_product = itemView.findViewById(item_product);
            item_list_price = itemView.findViewById(R.id.item_price);

            item_list_editButton = itemView.findViewById(R.id.editButton);
            item_list_deleteButton = itemView.findViewById(R.id.deleteButton);
            dateAdded = itemView.findViewById(R.id.item_data);
            enterItem_list_row=itemView.findViewById(R.id.item_enter_item);




            item_list_editButton.setOnClickListener(this);
            item_list_deleteButton.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {

            int position;
            position = getAdapterPosition();
            Item item = itemList.get(position);

            switch (v.getId()) {
                case R.id.editButton:
                    editItem(item);
                  //  Log.d("editButton in switch", "onClick: ");
                    break;
                case R.id.deleteButton:
                    deleteItem(item.getId());
                 //   Log.d("delete in switch", "onClick: ");
                    break;


                default:
                    throw new IllegalStateException("Unexpected value: " + v.getId());
            }

        }

        private void deleteItem(final int id) {
            builder = new AlertDialog.Builder(context);
//            inflater = LayoutInflater.from(context);
//            View view = inflater.inflate(R.layout.confirmation_pop, null);
            View view=LayoutInflater.from(context).inflate(R.layout.confirmation_pop,null);


            noButton = view.findViewById(R.id.noButton);
             yesButton = view.findViewById(R.id.yesButton);
      TextView aresure=view.findViewById(R.id.confirmation_title);


            builder.setView(view);
            dialog = builder.create();
            dialog.show();




            yesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseHandler db = new DatabaseHandler(context);
                    db.deleteItem(id);
                    itemList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    dialog.dismiss();
                    Log.d("delete yes", "onClick: ");

                }
            });

            noButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    Log.d("delete no", "onClick: ");
                }
            });


        }

        private void editItem(final Item newItem) {
            builder = new AlertDialog.Builder(context);
            inflater = LayoutInflater.from(context);
            final View view = inflater.inflate(R.layout.popup, null);

            Button saveButton;
            final EditText popup_enter_item, popup_quantity, popup_measure_quantity;
            final EditText popup_price, popup_produced;
            TextView popup_title;

            popup_enter_item = view.findViewById(R.id.enter_item_edit);
            popup_quantity = view.findViewById(R.id.quantity_edit);
            popup_measure_quantity = view.findViewById(R.id.edit_measure_quantity_other);
            popup_price = view.findViewById(R.id.edit_price);
            popup_produced = view.findViewById(R.id.edit_produced);
            popup_title = view.findViewById(R.id.title);
            saveButton = view.findViewById(R.id.savebutton);

            popup_title.setText("Edit Title");
            saveButton.setText(R.string.update);
            popup_enter_item.setText(newItem.getEdit_enter_item());
            popup_quantity.setText(String.valueOf(newItem.getEdit_quantity()));
            popup_measure_quantity.setText(String.valueOf(newItem.getEdit_quantity()));
            popup_price.setText(String.valueOf(newItem.getEdit_price()));
            popup_produced.setText(newItem.getEdit_produced());

            builder.setView(view);
            dialog = builder.create();
            dialog.show();

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    DatabaseHandler databaseHandler = new DatabaseHandler(context);
                    //  final EditText popup_enter_item, popup_quantity, popup_measure_quantity;
                    //  final EditText popup_price, popup_produced;
                    newItem.setEdit_enter_item(popup_enter_item.getText().toString());
                    newItem.setEdit_quantity(Double.valueOf(popup_quantity.getText().toString()));
                    newItem.setEdit_measure_quantity_other(popup_measure_quantity.getText().toString());
                    newItem.setEdit_price(Double.valueOf(popup_price.getText().toString()));
                    newItem.setEdit_produced(popup_produced.getText().toString());




                    if (!popup_enter_item.getText().toString().isEmpty()
                            && !popup_quantity.getText().toString().isEmpty()
                            && !popup_measure_quantity.getText().toString().isEmpty()
                            && !popup_price.getText().toString().isEmpty()
                            && !popup_produced.getText().toString().isEmpty()) {


                        databaseHandler.updateItem(newItem);
                        notifyItemChanged(getAdapterPosition(), newItem);
                        //Log.d("editButton bre", "onClick: ");
                        Log.d("editbutton", "onClick: ");




                    } else {


                        Snackbar.make(view, "Fields Empty", Snackbar.LENGTH_SHORT).show();
                        Log.d("editbutton", "onClick: Snackbar");


                    }

                    dialog.dismiss();

                    Log.d("outside", "onClick: ");


                }
            });


        }


    }
}
