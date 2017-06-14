package in.vs2.navjeevanadmin.models;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;

import in.vs2.navjeevanadmin.utils.Logcat;


/**
 * Created by Mitesh Patel on 9/18/2015.
 */
public class Address implements Serializable {

    int addressId;
    String address,pincode,landMark,longitude,latitude;

    double minimumBill;
    double deliveryCharge;

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getLandMark() {
        return landMark;
    }

    public void setLandMark(String landMark) {
        this.landMark = landMark;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }


    public String getMinimumBillRound(){
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(minimumBill);
    }

    public String getDelieveryChargeRound(){
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(deliveryCharge);
    }

    public double getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(double deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    public double getMinimumBill() {
        return minimumBill;
    }

    public void setMinimumBill(double minimumBill) {
        this.minimumBill = minimumBill;
    }


    public  String getDisplayAddress(){
        return address + ", " + landMark + ", " + pincode;
    }

    public Address() {
    }

    public Address(int addressId, String address, String pincode, String landMark, String longitude, String latitude) {
        this.addressId = addressId;
        this.address = address;
        this.pincode = pincode;
        this.landMark = landMark;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public static ArrayList<Address> getAddressArrayFromJSONArray(JSONArray jsonArray){
        ArrayList<Address> addressArrayList = new ArrayList<Address>();
        try {
            if (jsonArray != null && jsonArray.length() > 0){
                for (int i = 0; i < jsonArray.length(); i++) {
                    Address address = new Address();

                    JSONObject json = jsonArray.getJSONObject(i);
                    if (json != null){
                        if (json.has("AddressId")){
                            address.setAddressId(json.getInt("AddressId"));
                        }
                        if (json.has("Address")){
                            address.setAddress(json.getString("Address"));
                        }
                        if (json.has("Pincode")){
                            address.setPincode(json.getString("Pincode"));
                        }
                        if (json.has("LandMark")){
                            address.setLandMark(json.getString("LandMark"));
                        }
                        if (json.has("Longitude")){
                            address.setLongitude(json.getString("Longitude"));
                        }
                        if (json.has("Latitude")){
                            address.setLatitude(json.getString("Latitude"));
                        }

                        if (json.has("MinimumBill")){
                            address.setMinimumBill(json.getDouble("MinimumBill"));
                        }

                        if (json.has("DeliveryCharge")){
                            address.setDeliveryCharge(json.getDouble("DeliveryCharge"));
                        }

                        addressArrayList.add(address);
                    }
                }
            }

        }catch (Exception e){
            Logcat.e("getAddressArrayFromJSONArray", "Exception : " + e.toString());
        }
        return  addressArrayList;
    }

    public static Address getAddressFromJson(JSONObject json) {
        Address address = new Address();
        try {

            if (json.has("AddressId")){
                address.setAddressId(json.getInt("AddressId"));
            }
            if (json.has("Address")){
                address.setAddress(json.getString("Address"));
            }
            if (json.has("Pincode")){
                address.setPincode(json.getString("Pincode"));
            }
            if (json.has("LandMark")){
                address.setLandMark(json.getString("LandMark"));
            }
            if (json.has("Longitude")){
                address.setLongitude(json.getString("Longitude"));
            }
            if (json.has("Latitude")){
                address.setLatitude(json.getString("Latitude"));
            }

            if (json.has("MinimumBill")){
                address.setMinimumBill(json.getDouble("MinimumBill"));
            }

            if (json.has("DeliveryCharge")){
                address.setDeliveryCharge(json.getDouble("DeliveryCharge"));
            }

            return address;

        } catch (Exception e) {
            // TODO: handle exception
            Logcat.e("getCategoryFromJson", "Error : " + e.toString());
            return null;
        }
    }
}

