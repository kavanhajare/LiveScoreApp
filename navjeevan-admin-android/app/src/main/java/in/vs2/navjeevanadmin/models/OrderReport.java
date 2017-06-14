package in.vs2.navjeevanadmin.models;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import in.vs2.navjeevanadmin.utils.Logcat;

/**
 * Created by antarix on 26/09/15.
 */
public class OrderReport implements Serializable {

    private String orderNo;
    private String amount;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public static ArrayList<OrderReport> getDailyReportsFromJson(JSONArray jsonArray) {
        if (jsonArray != null) {
            ArrayList<OrderReport> details = new ArrayList<OrderReport>();
            try {
                for (int i = 0; i < jsonArray.length(); i++) {
                    details.add(getDailyReportFromJson(jsonArray.getJSONObject(i)));
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
            return details;
        }
        return null;
    }

    public static OrderReport getDailyReportFromJson(JSONObject json) {
        OrderReport report = new OrderReport();
        try {

            if (json.has("Amount") && !json.isNull("Amount"))
                report.setAmount(json.getString("Amount"));

            if (json.has("OrderDate") && !json.isNull("OrderDate"))
                report.setOrderNo(json.getString("OrderDate"));

            return report;

        } catch (Exception e) {
            // TODO: handle exception
            Logcat.e("getDailyReportFromJson", "Error : " + e.toString());
            return null;
        }
    }


}
