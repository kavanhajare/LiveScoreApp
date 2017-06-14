package vs2.navjivanclient.objects;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import vs2.navjivanclient.preference.PreferenceData;
import vs2.navjivanclient.utils.Logcat;

/**
 * Created by Mitesh Patel on 9/18/2015.
 */
public class User {

    int userId;
    String userName, mobileNumber, imageURL, deviceTokan, latitude, longitude;

    ArrayList<Address> addressArrayList;

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

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getDeviceTokan() {
        return deviceTokan;
    }

    public void setDeviceTokan(String deviceTokan) {
        this.deviceTokan = deviceTokan;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public User() {
    }

    public ArrayList<Address> getAddressArrayList() {
        return addressArrayList;
    }

    public void setAddressArrayList(ArrayList<Address> addressArrayList) {
        this.addressArrayList = addressArrayList;
    }

    public User(int userId, String userName, String mobileNumber, String imageURL, String deviceTokan, String latitude, String longitude, ArrayList<Address> addressArrayList) {
        this.userId = userId;
        this.userName = userName;
        this.mobileNumber = mobileNumber;
        this.imageURL = imageURL;
        this.deviceTokan = deviceTokan;
        this.latitude = latitude;
        this.longitude = longitude;
        this.addressArrayList = addressArrayList;
    }

    public static User getUserDetailsFromJSON(JSONObject json) {
        User user = new User();
        try {
            if (json != null) {
                if (json.has("UserId")) {
                    user.setUserId(json.getInt("UserId"));
                }
                if (json.has("Name")) {
                    user.setUserName(json.getString("Name"));
                }
                if (json.has("Mobile")) {
                    user.setMobileNumber(json.getString("Mobile"));
                }
                if (json.has("ImageUrl")) {
                    user.setImageURL(json.getString("ImageUrl"));
                }
                if (json.has("DeviceToken")) {
                    user.setDeviceTokan(json.getString("DeviceToken"));
                }
                if (json.has("Latitude")) {
                    user.setLatitude(json.getString("Latitude"));
                }
                if (json.has("Longitude")) {
                    user.setLongitude(json.getString("Longitude"));
                }
                if (json.has("Address") && !json.isNull("Address")) {
                    try {
                        JSONArray jsonArray = json.getJSONArray("Address");
                        user.setAddressArrayList(Address.getAddressArrayFromJSONArray(jsonArray));
                    } catch (Exception e) {
                        Logcat.e("setAddressArrayList", "Exception : " + e.toString());
                    }

                }
            }

        } catch (Exception e) {
            Logcat.e("getUserDetailsFromJSON", "Exception : " + e.toString());
        }
        return user;

    }

    public static User getUserDetailFromPreference(Context context) {
        User user;
        try {
            String jsonString = PreferenceData.getStringPref(PreferenceData.KEY_USER_JSON, context);

            if (jsonString == null){
                return null;
            }
            JSONObject json = new JSONObject(jsonString);
            user = User.getUserDetailsFromJSON(json);
            return user;
        } catch (Exception e) {
            Logcat.e("getUserDetailFromPreference", "Exception : " + e.toString());
            return null;
        }

    }

    public static boolean userExist(Context context){
        String jsonString = PreferenceData.getStringPref(PreferenceData.KEY_USER_JSON, context);

        if (jsonString != null){
            return true;
        }

        return false;
    }

}
