package in.vs2.navjeevanadmin.fragments;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

import dmax.dialog.SpotsDialog;
import in.vs2.navjeevanadmin.R;
import in.vs2.navjeevanadmin.adapters.ReportAdapter;
import in.vs2.navjeevanadmin.models.Const;
import in.vs2.navjeevanadmin.models.OrderReport;
import in.vs2.navjeevanadmin.utils.JSONParser;
import in.vs2.navjeevanadmin.utils.Logcat;
import in.vs2.navjeevanadmin.utils.Utility;

public class DailyReportFragment extends Fragment {

	private static final String tag = DailyReportFragment.class.getSimpleName();
	
	private View mView;

    ListView listView;
    CoordinatorLayout containerLayout;

    static final String SAVED_REPORTS = "Reports";

    ArrayList<OrderReport> mReports;


    String totalBill,deliveredBill,share;

    Button buttonDate,buttonShow;

    View footerView,headerView;

    TextView textTotalBill,textDelivered,textShare;

    ReportAdapter mAdapter;

    private Calendar cal;
    private int day;
    private int month;
    private int year;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {


        mView = inflater.inflate(R.layout.fragment_report, container, false);

        footerView = (View) inflater.inflate(R.layout.layout_report_footer, null);

        headerView = (View) inflater.inflate(R.layout.layout_daily_report_header, null);
        initViews();

        if (savedInstanceState != null) {
            mReports = (ArrayList<OrderReport>) savedInstanceState.getSerializable(SAVED_REPORTS);
        }


		return mView;
	}


    private void initViews(){

        containerLayout = (CoordinatorLayout) mView.findViewById(R.id.main_content);

        textTotalBill = (TextView)footerView.findViewById(R.id.text_total);
        textDelivered = (TextView)footerView.findViewById(R.id.text_delivered);
        textShare = (TextView)footerView.findViewById(R.id.text_share);

        buttonDate = (Button) mView.findViewById(R.id.button_date);
        buttonShow = (Button) mView.findViewById(R.id.button_show);


        cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);

        buttonDate.setText(day + " / " + (month + 1) + " / "
                + year);

        listView = (ListView) mView.findViewById(R.id.list);

        listView.addHeaderView(headerView);
        listView.addFooterView(footerView);
        buttonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getActivity(), datePickerListener, year, month, day).show();
            }
        });

        buttonShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Utility.isOnline(getActivity())){
                    String date = get2digit(month+1) + "/" +get2digit(day)+ "/" + get2digit(year);
                    new GetReport().execute(date);
                }else{
                    Snackbar.make(containerLayout, "No internet connection found!", Snackbar.LENGTH_LONG)
                            .show();
                }
            }
        });


        if (mReports != null){
            fillGrid();
        }else{
            if (Utility.isOnline(getActivity())){
                String date = get2digit(month+1) + "/" +get2digit(day)+ "/" + get2digit(year);
                new GetReport().execute(date);
            }else{
                Snackbar.make(containerLayout, "No internet connection found!", Snackbar.LENGTH_LONG)
                        .show();
            }
        }


	}

    private void fillGrid(){
        if (mReports != null && mReports.size() >0) {
            mAdapter = new ReportAdapter(getActivity(),mReports);
            listView.setAdapter(mAdapter);
        }else{
            textTotalBill.setText("");
            textDelivered.setText("");
            textShare.setText("");
            mReports = new ArrayList<OrderReport>();
            mAdapter = new ReportAdapter(getActivity(),mReports);
            listView.setAdapter(mAdapter);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(SAVED_REPORTS, mReports);
    }


    public class GetReport extends AsyncTask<String,Integer,JSONObject>{

        SpotsDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new SpotsDialog(getActivity(),R.style.CustomDialog);
            pd.show();
        }

        @Override
        protected JSONObject doInBackground(String... strings) {
            try {
                JSONParser parser = new JSONParser(getActivity());

                String date = strings[0];
                Logcat.e("Date : " ,date);
                return  parser.getJSONFromUrl(Const.GET_DAILY_REPORT+"&date="+strings[0],JSONParser.GET,null);

            }catch (Exception e){
                return  null;
            }

        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);

            try {
                pd.dismiss();
                if (result != null){
                    if (result.has("Data") && !result.isNull("Data")) {
                        //TotalBill
                        totalBill = result.getString("TotalBill");
                        deliveredBill =result.getString("DeliveredBill");
                        share = result.getString("Share");

                        textTotalBill.setText("Total Bill : " +getResources().getString(R.string.ruppee) + " " + totalBill);
                        textDelivered.setText("Delievered Bill : "+getResources().getString(R.string.ruppee) + " " + deliveredBill);
                        textShare.setText("Share : " +getResources().getString(R.string.ruppee) + " " + share);
                        mReports = OrderReport.getDailyReportsFromJson(result.getJSONArray("Data"));
                        fillGrid();
                    }else{
                        Snackbar.make(containerLayout, "" + result.getString("Message"), Snackbar.LENGTH_LONG)
                                .show();
                    }
                }else{
                    Snackbar.make(containerLayout,  "Something went wrong, try again later!", Snackbar.LENGTH_LONG)
                            .show();
                }

            }catch (Exception e){

            }
        }
    }


    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            day = selectedDay;
            month = selectedMonth;
            year = selectedYear;
            buttonDate.setText(day + " / " + (month + 1) + " / "
                    + year);
        }
    };


    private String get2digit(int value){
        try{
            //return String.format("%2d", value);
            return  new DecimalFormat("00").format(value);
        }catch (Exception e){
            return "0";
        }



    }


}