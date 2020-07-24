package tk.taxhidinkadiri.shopping.model;

public class Item {
//     private EditText edit_enter_item, edit_quantity,
//     edit_measure_quantity_other, edit_price, edit_produced;

    private int id;
    private String edit_enter_item;
    private double edit_quantity;
    private String edit_measure_quantity_other;
    private double edit_price;
    private String edit_produced;
    private String dateItemAdded;

    public Item(int id, String edit_enter_item, double edit_quantity, String edit_measure_quantity_other, double edit_price, String edit_produced, String dateItemAdded) {
        this.id = id;
        this.edit_enter_item = edit_enter_item;
        this.edit_quantity = edit_quantity;
        this.edit_measure_quantity_other = edit_measure_quantity_other;
        this.edit_price = edit_price;
        this.edit_produced = edit_produced;
        this.dateItemAdded = dateItemAdded;
    }

    public Item(String edit_enter_item, double edit_quantity, String edit_measure_quantity_other, double edit_price, String edit_produced, String dateItemAdded) {
        this.edit_enter_item = edit_enter_item;
        this.edit_quantity = edit_quantity;
        this.edit_measure_quantity_other = edit_measure_quantity_other;
        this.edit_price = edit_price;
        this.edit_produced = edit_produced;
        this.dateItemAdded = dateItemAdded;
    }

    public Item() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEdit_enter_item() {
        return edit_enter_item;
    }

    public void setEdit_enter_item(String edit_enter_item) {
        this.edit_enter_item = edit_enter_item;
    }

    public double getEdit_quantity() {
        return edit_quantity;
    }

    public void setEdit_quantity(double edit_quantity) {
        this.edit_quantity = edit_quantity;
    }

    public String getEdit_measure_quantity_other() {
        return edit_measure_quantity_other;
    }

    public void setEdit_measure_quantity_other(String edit_measure_quantity_other) {
        this.edit_measure_quantity_other = edit_measure_quantity_other;
    }

    public double getEdit_price() {
        return edit_price;
    }

    public void setEdit_price(double edit_price) {
        this.edit_price = edit_price;
    }

    public String getEdit_produced() {
        return edit_produced;
    }

    public void setEdit_produced(String edit_produced) {
        this.edit_produced = edit_produced;
    }

    public String getDateItemAdded() {
        return dateItemAdded;
    }

    public void setDateItemAdded(String dateItemAdded) {
        this.dateItemAdded = dateItemAdded;
    }
}
