package vs2.navjivanclient.objects;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import vs2.navjivanclient.models.Const;
import vs2.navjivanclient.utils.JSONParser;
import vs2.navjivanclient.utils.Logcat;

/**
 * Created by Mitesh Patel on 11/19/2015.
 */
public class Area {

    int areaId;
    String areaName;

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Area() {
    }

    public Area(int aresId, String areaName) {
        this.areaId = aresId;
        this.areaName = areaName;
    }

    public static ArrayList<Area> getAreaListFromServer(Context c) {
        JSONParser parser = new JSONParser(c);
        ArrayList<Area> AreaList = new ArrayList<Area>();
        try {
            String url = Const.GET_AREA_LIST;
            JSONObject jsonObject = parser.getJSONFromUrl(url, JSONParser.GET,null);

            if (jsonObject != null) {
                JSONArray jsonArray = jsonObject.getJSONArray("Data");
                if (jsonArray != null && jsonArray.length() > 0) {
                    Area areas = new Area(0, "Select your area");
                    AreaList.add(areas);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jObj = jsonArray.getJSONObject(i);
                        int areaId = jObj.getInt("AreaId");
                        String areaName = jObj.getString("AreaName");
                        Area area = new Area(areaId, areaName);
                        AreaList.add(area);
                    }
                }
            }
            return AreaList;
        } catch (Exception e) {
            // TODO: handle exception
            Logcat.e("AreaList List", "Error : " + e.toString());
            return AreaList;
        }
    }
}
