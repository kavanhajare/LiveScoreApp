package vs2.navjivanclient.models;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import vs2.navjivanclient.utils.Logcat;


public class Category  implements Serializable {

    private int Status;

    private String Description;

    private String Category;

    private int CategoryId;

    private int HotelId;

    private String ImageUrl;


    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String Category) {
        this.Category = Category;
    }

    public int getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(int CategoryId) {
        this.CategoryId = CategoryId;
    }

    public int getHotelId() {
        return HotelId;
    }

    public void setHotelId(int HotelId) {
        this.HotelId = HotelId;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String ImageUrl) {
        this.ImageUrl = ImageUrl;
    }


    @Override
    public String toString() {
        return "Category [Status = " + Status + ", Description = " + Description + ", Category = " + Category + ", CategoryId = " + CategoryId + ", HotelId = " + HotelId + ", ImageUrl = " + ImageUrl + "]";
    }


    public static ArrayList<Category> getCategoriesFromJson(JSONArray jsonArray) {
        if (jsonArray != null) {
            ArrayList<Category> friends = new ArrayList<Category>();
            try {
                for (int i = 0; i < jsonArray.length(); i++) {
                    friends.add(getCategoryFromJson(jsonArray.getJSONObject(i)));
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
            return friends;
        }
        return null;
    }

    public static Category getCategoryFromJson(JSONObject json) {
        Category category = new Category();
        try {

            if (json.has("Status") && !json.isNull("Status"))
                category.setStatus(json.getInt("Status"));

            if (json.has("Description") && !json.isNull("Description"))
                category.setDescription(json.getString("Description"));

            if (json.has("Category") && !json.isNull("Category"))
                category.setCategory(json.getString("Category"));

            if (json.has("CategoryId") && !json.isNull("CategoryId"))
                category.setCategoryId(json.getInt("CategoryId"));

            if (json.has("HotelId") && !json.isNull("HotelId"))
                category.setHotelId(json.getInt("HotelId"));

            if (json.has("ImageUrl") && !json.isNull("ImageUrl"))
                category.setImageUrl(json.getString("ImageUrl"));

            return category;

        } catch (Exception e) {
            // TODO: handle exception
            Logcat.e("getCategoryFromJson", "Error : " + e.toString());
            return null;
        }
    }

}