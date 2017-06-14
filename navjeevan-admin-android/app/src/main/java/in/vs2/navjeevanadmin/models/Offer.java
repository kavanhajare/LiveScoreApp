package in.vs2.navjeevanadmin.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import in.vs2.navjeevanadmin.utils.Logcat;

/**
 * Created by Mitesh Patel on 11/20/2015.
 */
public class Offer implements Serializable {


    //int orderId;
    int id,offerType,minimumAmount,discountRate,offerFrom;
    String Title,Description,FromDate,ToDate,itemFree;
    String discountAmount;

   /* public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }*/

    public String getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(String discountAmount) {
        this.discountAmount = discountAmount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOfferType() {
        return offerType;
    }

    public void setOfferType(int offerType) {
        this.offerType = offerType;
    }

    public int getMinimumAmount() {
        return minimumAmount;
    }

    public void setMinimumAmount(int minimumAmount) {
        this.minimumAmount = minimumAmount;
    }

    public int getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(int discountRate) {
        this.discountRate = discountRate;
    }

    public int getOfferFrom() {
        return offerFrom;
    }

    public void setOfferFrom(int offerFrom) {
        this.offerFrom = offerFrom;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getFromDate() {
        return FromDate;
    }

    public void setFromDate(String fromDate) {
        FromDate = fromDate;
    }

    public String getToDate() {
        return ToDate;
    }

    public void setToDate(String toDate) {
        ToDate = toDate;
    }

    public String getItemFree() {
        return itemFree;
    }

    public void setItemFree(String itemFree) {
        this.itemFree = itemFree;
    }

    public Offer() {
    }
    public static ArrayList<Offer> getOfferListFromOrder(JSONArray jsonArray) {
        ArrayList<Offer> offerList = new ArrayList<Offer>();
        try {
                if (jsonArray != null && jsonArray.length() > 0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jObj = jsonArray.getJSONObject(i);
                        Offer offer = new Offer();
                        if (jObj.has("OfferId"))
                            offer.setId(jObj.getInt("OfferId"));
                        if (jObj.has("DiscountAmount"))
                            offer.setDiscountAmount(jObj.getString("DiscountAmount"));
                        if (jObj.has("OfferType"))
                            offer.setOfferType(jObj.getInt("OfferType"));
                        if (jObj.has("DiscountRate"))
                            offer.setDiscountRate(jObj.getInt("DiscountRate"));
                        if (jObj.has("ItemFree"))
                            offer.setItemFree(jObj.getString("ItemFree"));
                        offerList.add(offer);
                    }
                }
            return offerList;
        } catch (Exception e) {
            // TODO: handle exception
            Log.e("Offer List", "Error : " + e.toString());
            return offerList;
        }
    }

    public static ArrayList<Offer> getOfferListFromServer(JSONObject jsonObject) {
        ArrayList<Offer> offerList = new ArrayList<Offer>();
        try {
                        if (jsonObject != null) {
                JSONArray jsonArray = jsonObject.getJSONArray("Data");
                if (jsonArray != null && jsonArray.length() > 0) {

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jObj = jsonArray.getJSONObject(i);
                        Offer offer = new Offer();
                        if (jObj.has("Id"))
                            offer.setId(jObj.getInt("Id"));
                        if (jObj.has("Title"))
                            offer.setTitle(jObj.getString("Title"));
                        if (jObj.has("Description"))
                            offer.setDescription(jObj.getString("Description"));
                        if (jObj.has("OfferType"))
                            offer.setOfferType(jObj.getInt("OfferType"));
                        if (jObj.has("FromDate"))
                            offer.setFromDate(jObj.getString("FromDate"));
                        if (jObj.has("ToDate"))
                            offer.setToDate(jObj.getString("ToDate"));
                        if (jObj.has("MinimumAmount"))
                            offer.setMinimumAmount(jObj.getInt("MinimumAmount"));
                        if (jObj.has("DiscountRate"))
                            offer.setDiscountRate(jObj.getInt("DiscountRate"));
                        if (jObj.has("OfferFrom"))
                            offer.setOfferFrom(jObj.getInt("OfferFrom"));
                        if (jObj.has("ItemFree"))
                            offer.setItemFree(jObj.getString("ItemFree"));

                        offerList.add(offer);
                    }
                }
            }
            return offerList;
        } catch (Exception e) {
            // TODO: handle exception
            Log.e("Offer List", "Error : " + e.toString());
            return offerList;
        }
    }

    public static Offer getOfferFromJson(JSONObject jObj) {
        Offer offer = new Offer();
        try {
            if (jObj.has("Id"))
                offer.setId(jObj.getInt("Id"));
            if (jObj.has("Title"))
                offer.setTitle(jObj.getString("Title"));
            if (jObj.has("Description"))
                offer.setDescription(jObj.getString("Description"));
            if (jObj.has("OfferType"))
                offer.setOfferType(jObj.getInt("OfferType"));
            if (jObj.has("FromDate"))
                offer.setFromDate(jObj.getString("FromDate"));
            if (jObj.has("ToDate"))
                offer.setToDate(jObj.getString("ToDate"));
            if (jObj.has("MinimumAmount"))
                offer.setMinimumAmount(jObj.getInt("MinimumAmount"));
            if (jObj.has("DiscountRate"))
                offer.setDiscountRate(jObj.getInt("DiscountRate"));
            if (jObj.has("OfferFrom"))
                offer.setOfferFrom(jObj.getInt("OfferFrom"));
            if (jObj.has("ItemFree"))
                offer.setItemFree(jObj.getString("ItemFree"));

            return offer;

        } catch (Exception e) {
            // TODO: handle exception
            Logcat.e("getOfferFromJson", "Error : " + e.toString());
            return null;
        }
    }
}
