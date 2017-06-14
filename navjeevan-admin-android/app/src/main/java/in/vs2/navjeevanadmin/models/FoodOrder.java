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
public class FoodOrder implements Serializable{

    private int status;
    private int orderId;
    private double amount;
    private  double deliveryCharge;
    private String orderedOn;
    private int userId;
    private String userName;
    private String userImage;
    private String mobile;
    private String remarks;
    private Address address;
    private boolean isToday;
    private String reason;


    private int OrderType;


    private ArrayList<OrderDetail> orderDetails;
    private ArrayList<Offer> offerDetails;


    //ORDER STATUS
    public static final int USER_ORDERED = 1;
    public static final int HOTEL_ACCEPTED = 2;
    public static final int HOTEL_CANCELLED = 3;
    public static final int USER_CANCELLED = 4;

    public FoodOrder() {
        super();
    }


    public int getOrderType() {
        return OrderType;
    }

    public void setOrderType(int orderType) {
        OrderType = orderType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }


    public double getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(double deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }


    public String getOrderedOn() {
        return orderedOn;
    }

    public void setOrderedOn(String orderedOn) {
        this.orderedOn = orderedOn;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }


    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public boolean isToday() {
        return isToday;
    }

    public void setIsToday(boolean isToday) {
        this.isToday = isToday;
    }


    public ArrayList<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(ArrayList<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public ArrayList<Offer> getOfferDetails() {
        return offerDetails;
    }

    public void setOfferDetails(ArrayList<Offer> offerDetails) {
        this.offerDetails = offerDetails;
    }

    public String getAmountRound(){
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(amount);
    }

    public String getDeliveryChargeRound(){
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(deliveryCharge);
    }

    @Override
    public String toString() {
        return "FoodOrder{" +
                "status=" + status +
                ", orderId=" + orderId +
                ", amount=" + amount +
                ", orderedOn='" + orderedOn + '\'' +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userImage='" + userImage + '\'' +
                ", mobile='" + mobile + '\'' +
                ", remarks='" + remarks + '\'' +
                ", address=" + address +
                ", orderDetails=" + orderDetails +
                '}';
    }

    public static ArrayList<FoodOrder> getOrdersFromJson(JSONArray jsonArray) {
        if (jsonArray != null) {
            ArrayList<FoodOrder> orders = new ArrayList<FoodOrder>();
            try {
                for (int i = 0; i < jsonArray.length(); i++) {
                    orders.add(getOrderFromJson(jsonArray.getJSONObject(i)));
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
            return orders;
        }
        return null;
    }

    public static FoodOrder getOrderFromJson(JSONObject json) {
        FoodOrder order = new FoodOrder();
        try {

            if (json.has("orderId") && !json.isNull("orderId"))
                order.setOrderId(json.getInt("orderId"));

            if (json.has("orderOn") && !json.isNull("orderOn"))
                order.setOrderedOn(json.getString("orderOn"));

            if (json.has("totalBill") && !json.isNull("totalBill"))
                order.setAmount(json.getDouble("totalBill"));

            if (json.has("deliveryCharge") && !json.isNull("deliveryCharge"))
                order.setDeliveryCharge(json.getDouble("deliveryCharge"));

            if (json.has("orderStatus") && !json.isNull("orderStatus"))
                order.setStatus(json.getInt("orderStatus"));

            if (json.has("remarks") && !json.isNull("remarks"))
                order.setRemarks(json.getString("remarks"));

            if (json.has("reason") && !json.isNull("reason"))
                order.setReason(json.getString("reason"));

            if (json.has("isToday") && !json.isNull("isToday"))
                order.setIsToday(json.getBoolean("isToday"));


            if (json.has("userId") && !json.isNull("userId"))
                order.setUserId(json.getInt("userId"));

            if (json.has("userName") && !json.isNull("userName"))
                order.setUserName(json.getString("userName"));

            if (json.has("mobile") && !json.isNull("mobile"))
                order.setMobile(json.getString("mobile"));

            if (json.has("imageUrl") && !json.isNull("imageUrl"))
                order.setUserImage(json.getString("imageUrl"));

            if (json.has("Address") && !json.isNull("Address"))
                order.setAddress(Address.getAddressFromJson(json.getJSONObject("Address")));

            if (json.has("deliveryType") && !json.isNull("deliveryType"))
                order.setOrderType(json.getInt("deliveryType"));

            if (json.has("orderDetail") && !json.isNull("orderDetail"))
                order.setOrderDetails(OrderDetail.getOrderDetailsFromJson(json.getJSONArray("orderDetail")));

            if (json.has("Offers") && !json.isNull("Offers"))
                order.setOfferDetails(Offer.getOfferListFromOrder(json.getJSONArray("Offers")));

            return order;

        } catch (Exception e) {
            // TODO: handle exception
            Logcat.e("getOrderDetailFromJson", "Error : " + e.toString());
            return null;
        }
    }

}
