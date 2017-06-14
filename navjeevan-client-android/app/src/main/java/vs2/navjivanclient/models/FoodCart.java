package vs2.navjivanclient.models;

/**
 * Created by antarix on 18/09/15.
 */
public class FoodCart {

    private FoodItem item;
    private int quantity;

    public FoodCart(FoodItem item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public FoodItem getItem() {
        return item;
    }

    public void setItem(FoodItem item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


}
