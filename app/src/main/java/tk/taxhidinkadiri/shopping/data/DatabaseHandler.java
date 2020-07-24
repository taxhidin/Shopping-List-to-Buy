package tk.taxhidinkadiri.shopping.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tk.taxhidinkadiri.shopping.model.Item;
import tk.taxhidinkadiri.shopping.util.Constants;

public class DatabaseHandler extends SQLiteOpenHelper {
    //Constants constants=new Constants();
    private final Context context;

    public DatabaseHandler(@Nullable Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);

        this.context = context;
    }

    @Override

    public void onCreate(SQLiteDatabase db) {
        String CREATE_SHOPPING_TABLE = "CREATE TABLE "
                + Constants.DB_TABLE + "("
                + Constants.KEY_ID + " INTEGER PRIMARY KEY, "
                + Constants.KEY_edit_enter_item + " TEXT, "
                + Constants.KEY_edit_quantity + " DOUBLE, "
                + Constants.KEY_edit_measure_quantity_other + " TEXT,"
                + Constants.KEY_edit_price + " DOUBLE, "
                + Constants.KEY_edit_produced + " TEXT, "
                + Constants.KEY_DATE_NAME + " LONG);";

        db.execSQL(CREATE_SHOPPING_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + Constants.DB_TABLE);
        onCreate(db);
    }

    //SRUD operation
    public void addItem(Item item) {


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.KEY_edit_enter_item, item.getEdit_enter_item());
        values.put(Constants.KEY_edit_quantity, item.getEdit_quantity());
        values.put(Constants.KEY_edit_measure_quantity_other, item.getEdit_measure_quantity_other());
        values.put(Constants.KEY_edit_price, item.getEdit_price());
        values.put(Constants.KEY_edit_produced, item.getEdit_produced());
        values.put(Constants.KEY_DATE_NAME, java.lang.System.currentTimeMillis());
        db.insert(Constants.DB_TABLE, null, values);
        Log.d("DBHandler", "addItem: added Item");


    }


    public Item getItem(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Constants.DB_TABLE,
                new String[]{Constants.KEY_ID, Constants.KEY_edit_enter_item,
                        Constants.KEY_edit_quantity, Constants.KEY_edit_measure_quantity_other,
                        Constants.KEY_edit_price, Constants.KEY_edit_produced,
                        Constants.KEY_DATE_NAME
                }
                , Constants.KEY_ID + "=?", new String[]{String.valueOf(id)},
                null, null, null, null);

        if (cursor != null )

            cursor.moveToFirst();

            Item item = new Item();
        if (cursor !=null) {
            item.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
            item.setEdit_enter_item(cursor.getString(cursor.getColumnIndex(Constants.KEY_edit_enter_item)));
            item.setEdit_quantity(Double.parseDouble(cursor.getString(cursor.getColumnIndex(Constants.KEY_edit_quantity))));
            item.setEdit_measure_quantity_other(cursor.getString(cursor.getColumnIndex(Constants.KEY_edit_measure_quantity_other)));
            item.setEdit_price(Double.parseDouble(cursor.getString(cursor.getColumnIndex(Constants.KEY_edit_price))));
            item.setEdit_produced(cursor.getString(cursor.getColumnIndex(Constants.KEY_edit_produced)));

            DateFormat dateFormat = DateFormat.getDateInstance();
            String formattedDate = dateFormat.format(new Date(cursor.getLong(Integer.parseInt(Constants.KEY_DATE_NAME))).getTime());
            item.setDateItemAdded(formattedDate);

        }
            return item;
      //  }

      //  return null;
    }

    public List<Item> getAllItems() {

        SQLiteDatabase db = this.getReadableDatabase();

        List<Item> itemList = new ArrayList<>();

        Cursor cursor = db.query(Constants.DB_TABLE,
                new String[]{Constants.KEY_ID,
                        Constants.KEY_edit_enter_item,
                        Constants.KEY_edit_quantity,
                        Constants.KEY_edit_measure_quantity_other,
                        Constants.KEY_edit_price,
                        Constants.KEY_edit_produced,
                        Constants.KEY_DATE_NAME

                },
               null, null, null, null, Constants.KEY_DATE_NAME+ " DESC");

        if (cursor.moveToFirst()) {
            do {

                Item item = new Item();
                item.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
                item.setEdit_enter_item(cursor.getString(cursor.getColumnIndex(Constants.KEY_edit_enter_item)));
                item.setEdit_quantity(Double.parseDouble(cursor.getString(cursor.getColumnIndex(Constants.KEY_edit_quantity))));
                item.setEdit_measure_quantity_other(cursor.getString(cursor.getColumnIndex(Constants.KEY_edit_measure_quantity_other)));
                item.setEdit_price(Double.valueOf(cursor.getString(cursor.getColumnIndex(Constants.KEY_edit_price))));
                item.setEdit_produced(cursor.getString(cursor.getColumnIndex(Constants.KEY_edit_produced)));


                DateFormat dateFormat = DateFormat.getDateInstance();
                String formattedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE_NAME))).getTime());
                item.setDateItemAdded(formattedDate);
                itemList.add(item);
            } while (cursor.moveToNext());


        }

        return itemList;
    }

    public int updateItem(Item item) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Constants.KEY_edit_enter_item,item.getEdit_enter_item());
        values.put(Constants.KEY_edit_quantity, item.getEdit_quantity());
        values.put(Constants.KEY_edit_measure_quantity_other, item.getEdit_measure_quantity_other());
        values.put(Constants.KEY_edit_price, item.getEdit_price());
        values.put(Constants.KEY_edit_produced, item.getEdit_produced());
        values.put(Constants.KEY_DATE_NAME, java.lang.System.currentTimeMillis());//timestamp of the system



        return db.update(Constants.DB_TABLE,
                values,
                Constants.KEY_ID + "=?",
                new String[]{String.valueOf(item.getId())}

        );


    }

    public void deleteItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constants.DB_TABLE,
                Constants.KEY_ID+"=?",
                new String[]{String.valueOf(id)});
        db.close();
    }

//    public int getItemCount () {
//       String countQuery= "SELECT * FROM "+Constants.DB_TABLE;
//       SQLiteDatabase db=this.getReadableDatabase();
//       Cursor cursor=db.rawQuery(countQuery, null);
//
//       return cursor.getCount();
//
//    }

    public int getItemsCount () {

        String countQuery="SELECT * FROM "+Constants.DB_TABLE;
        SQLiteDatabase db=this.getReadableDatabase();
        try {

            Cursor cursor = db.rawQuery(countQuery, null);
            if (cursor !=null){
                return cursor.getCount();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        final int i = 0;
        return i;

    }



}
