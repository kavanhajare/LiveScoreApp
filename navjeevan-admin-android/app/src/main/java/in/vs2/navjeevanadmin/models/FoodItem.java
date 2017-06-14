package in.vs2.navjeevanadmin.models;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;

import in.vs2.navjeevanadmin.utils.Logcat;

public class FoodItem implements Serializable
{
    private String Name;

    private int Status;

    private String Description;

    private int CategoryId;

    private double Price;

    private String ImageUrl;

    private int MenuId;

    public String getName ()
    {
        return Name;
    }

    public void setName (String Name)
    {
        this.Name = Name;
    }

    public int getStatus ()
    {
        return Status;
    }

    public void setStatus (int Status)
    {
        this.Status = Status;
    }

    public String getDescription ()
    {
        return Description;
    }

    public void setDescription (String Description)
    {
        this.Description = Description;
    }

    public int getCategoryId ()
    {
        return CategoryId;
    }

    public void setCategoryId (int CategoryId)
    {
        this.CategoryId = CategoryId;
    }

    public double getPrice ()
    {
        return Price;
    }

    public String getPriceRound(){
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(Price);
    }

    public void setPrice (double Price)
    {
        this.Price = Price;
    }

    public String getImageUrl ()
    {
        return ImageUrl;
    }

    public void setImageUrl (String ImageUrl)
    {
        this.ImageUrl = ImageUrl;
    }

    public int getMenuId ()
    {
        return MenuId;
    }

    public void setMenuId (int MenuId)
    {
        this.MenuId = MenuId;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Name = "+Name+", Status = "+Status+", Description = "+Description+", CategoryId = "+CategoryId+", Price = "+Price+", ImageUrl = "+ImageUrl+", MenuId = "+MenuId+"]";
    }


    public static ArrayList<FoodItem> getFoodItemsFromJson(JSONArray jsonArray) {
        if (jsonArray != null) {
            ArrayList<FoodItem> foodItems = new ArrayList<FoodItem>();
            try {
                for (int i = 0; i < jsonArray.length(); i++) {
                    foodItems.add(getFoodItemFromJson(jsonArray.getJSONObject(i)));
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
            return foodItems;
        }
        return null;
    }

    public static FoodItem getFoodItemFromJson(JSONObject json) {

        try {
            FoodItem item = new FoodItem();

            if (json.has("Status") && !json.isNull("Status"))
                item.setStatus(json.getInt("Status"));

            if (json.has("CategoryId") && !json.isNull("CategoryId"))
                item.setCategoryId(json.getInt("CategoryId"));

            if (json.has("MenuId") && !json.isNull("MenuId"))
                item.setMenuId(json.getInt("MenuId"));

            if (json.has("Price") && !json.isNull("Price"))
                item.setPrice(json.getDouble("Price"));

            if (json.has("Description") && !json.isNull("Description"))
                item.setDescription(json.getString("Description"));

            if (json.has("Name") && !json.isNull("Name"))
                item.setName(json.getString("Name"));

            if (json.has("ImageUrl") && !json.isNull("ImageUrl"))
                item.setImageUrl(json.getString("ImageUrl"));

            return item;

        } catch (Exception e) {
            // TODO: handle exception
            Logcat.e("getFoodItemFromJson", "Error : " + e.toString());
            return null;
        }
    }

}