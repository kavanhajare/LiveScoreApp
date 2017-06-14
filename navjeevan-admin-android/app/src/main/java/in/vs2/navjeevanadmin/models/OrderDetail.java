package in.vs2.navjeevanadmin.models;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;

import in.vs2.navjeevanadmin.utils.Logcat;


/**
 * Created by antarix on 22/09/15.
 */
public class OrderDetail implements Serializable {

    private int orderDetailId;
    private int menuId;
    private String name;
    private int quantity;
    private double amount;
    private double totalAmount;

    public OrderDetail() {
        super();
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public int getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(int orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public String getAmountRound(){
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(amount);
    }

    public String getTotalAmountRound(){
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(totalAmount);
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "orderDetailId=" + orderDetailId +
                ", menuId=" + menuId +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", amount=" + amount +
                ", totalAmount=" + totalAmount +
                '}';
    }


    public static ArrayList<OrderDetail> getOrderDetailsFromJson(JSONArray jsonArray) {
        if (jsonArray != null) {
            ArrayList<OrderDetail> details = new ArrayList<OrderDetail>();
            try {
                for (int i = 0; i < jsonArray.length(); i++) {
                    details.add(getOrderDetailFromJson(jsonArray.getJSONObject(i)));
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
            return details;
        }
        return null;
    }

    public static OrderDetail getOrderDetailFromJson(JSONObject json) {
        OrderDetail detail = new OrderDetail();
        try {

            if (json.has("orderDetailId") && !json.isNull("orderDetailId"))
                detail.setOrderDetailId(json.getInt("orderDetailId"));

            if (json.has("menuId") && !json.isNull("menuId"))
                detail.setMenuId(json.getInt("menuId"));

            if (json.has("name") && !json.isNull("name"))
                detail.setName(json.getString("name"));

            if (json.has("qty") && !json.isNull("qty"))
                detail.setQuantity(json.getInt("qty"));

            if (json.has("price") && !json.isNull("price"))
                detail.setAmount(json.getDouble("price"));


            if (json.has("totalAmount") && !json.isNull("totalAmount"))
                detail.setTotalAmount(json.getDouble("totalAmount"));

            return detail;

        } catch (Exception e) {
            // TODO: handle exception
            Logcat.e("getOrderDetailFromJson", "Error : " + e.toString());
            return null;
        }
    }

}
