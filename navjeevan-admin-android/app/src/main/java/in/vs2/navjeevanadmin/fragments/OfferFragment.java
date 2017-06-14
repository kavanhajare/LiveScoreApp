package in.vs2.navjeevanadmin.fragments;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONObject;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import dmax.dialog.SpotsDialog;
import in.vs2.navjeevanadmin.R;
import in.vs2.navjeevanadmin.adapters.OffersAdapter;
import in.vs2.navjeevanadmin.models.Const;
import in.vs2.navjeevanadmin.models.Offer;
import in.vs2.navjeevanadmin.utils.JSONParser;
import in.vs2.navjeevanadmin.utils.Logcat;
import in.vs2.navjeevanadmin.utils.Utility;

public class OfferFragment extends Fragment {

	private static final String tag = OfferFragment.class.getSimpleName();
	private View mView;
    ListView listView;
    OffersAdapter mAdapter;
    CoordinatorLayout containerLayout;
    FloatingActionButton fabAdd;
    private TextView mTextViewEmpty;

    private ArrayList<Offer> mOfferList;

    static final String SAVED_OFFER = "offers";
    static final int DATE_DIALOG_START = 999;
    static final int DATE_DIALOG_END = 888;
    private DatePickerDialog mDatePickerStart,mDatePickerEnd;
    private int mYearStart,mYearEnd, mMonthStart,mMonthEnd,mDayStart,mDayEnd;
    Button buttonStartDate,buttonEndDate;
    private LinearLayout mLayoutMain;
    private int mOfferId = 0;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {


        mView = inflater.inflate(R.layout.fragment_offer, container, false);

        initViews();
        getTodayDate();
        if (savedInstanceState != null) {
            mOfferList = (ArrayList<Offer>) savedInstanceState.getSerializable(SAVED_OFFER);
        }

        if (mOfferList != null){
            fillGrid();
        }else{
            if (Utility.isOnline(getActivity())){
                new GetOffers().execute();
            }else{
                Snackbar.make(containerLayout, "No internet connection found!", Snackbar.LENGTH_LONG)
                        //.setAction("Undo", mOnClickListener)
                        //.setActionTextColor(Color.RED)
                        .show();
            }
        }
		return mView;
	}
    public void getTodayDate() {
        Calendar c = Calendar.getInstance();
        mYearStart = c.get(Calendar.YEAR);
        mMonthStart = c.get(Calendar.MONTH);
        mDayStart = c.get(Calendar.DAY_OF_MONTH);

        mYearEnd = mYearStart;
        mMonthEnd = mMonthStart;
        mDayEnd = mDayStart;

    }

    private void initViews(){

        containerLayout = (CoordinatorLayout) mView.findViewById(R.id.main_content);
        fabAdd = (FloatingActionButton) mView.findViewById(R.id.fab_add);
		listView = (ListView) mView.findViewById(R.id.list);
        mTextViewEmpty = (TextView)mView.findViewById(R.id.textview_empty_list);
        listView.setEmptyView(mTextViewEmpty);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCategoryDialog(-1);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                chooseAction(getActivity(),"What you want to do with this offer ?",position);
            }
        });

	}
    public void chooseAction(final Context context, String message,final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("" + message);
        builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showConfirmDialog(context, position);
            }
        });
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addCategoryDialog(position);
            }
        });
        builder.create().show();
    }

    public void showConfirmDialog(Context context,final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Are you sure ?");
        builder.setMessage("Do you want to delete this offer ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new DeleteOffers(position).execute(""+mOfferList.get(position).getId());
            }
        });
        builder.setNegativeButton("No", null);
        builder.create().show();
    }



    private void fillGrid(){
        if (mOfferList != null) {
            mAdapter = new OffersAdapter(getActivity(),mOfferList);
            listView.setAdapter(mAdapter);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(SAVED_OFFER, mOfferList);
    }

    public class GetOffers extends AsyncTask<String,Integer,JSONObject>{

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

                return  parser.getJSONFromUrl(Const.GET_OFFERS,JSONParser.GET,null);

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
                    int status  = 0;
                    status = result.getInt("Status");
                    if (status == 1) {
                        mOfferList = Offer.getOfferListFromServer(result);
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

    public class DeleteOffers extends AsyncTask<String,Integer,JSONObject>{

        SpotsDialog pd;
        int mPosition;

        public  DeleteOffers(int position){
            this.mPosition = position;
        }

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

                return  parser.getJSONFromUrl(Const.DELETE_OFFERS+strings[0],JSONParser.GET,null);

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
                    int status  = 0;
                    status = result.getInt("Status");
                    if (status == 1) {
                        mOfferList.remove(mPosition);
                        mAdapter.notifyDataSetChanged();

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

    //Add Category Dialog
    public void addCategoryDialog(final int position) {

        //final Dialog dialog = new Dialog(getActivity());
      //  final Dialog dialog = new Dialog(getActivity(),R.style.Base_Theme_AppCompat_Light_Dialog);
       // final Dialog dialog = new Dialog(getActivity(),android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);
        final Dialog dialog = new Dialog(getActivity(),android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar);
        dialog.setContentView(R.layout.dialog_offer);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        mLayoutMain = (LinearLayout)dialog.findViewById(R.id.layout_main);

        final TextView textTitle = (TextView) dialog.findViewById(R.id.dialog_title);
        final EditText editTitle = (EditText) dialog.findViewById(R.id.edit_title);
        final EditText editDescription = (EditText) dialog.findViewById(R.id.edit_description);
        final EditText editMinAmount = (EditText)dialog.findViewById(R.id.edit_min_amount);
        final EditText editDiscountRate = (EditText)dialog.findViewById(R.id.edit_discount_rate);
        final EditText editFreeItem = (EditText)dialog.findViewById(R.id.edit_free_item);

        final LinearLayout layoutCashTakeAway = (LinearLayout)dialog.findViewById(R.id.layout_cash_take_away);
        final LinearLayout layoutFreeItem = (LinearLayout)dialog.findViewById(R.id.layout_free_item);

        final Spinner spinnerOfferType = (Spinner)dialog.findViewById(R.id.spinner_offer_type);

        final Button buttonSave = (Button) dialog.findViewById(R.id.button_save);
        final Button buttonCancel = (Button) dialog.findViewById(R.id.button_cancel);

        buttonStartDate = (Button) dialog.findViewById(R.id.button_from_date);
        buttonEndDate = (Button) dialog.findViewById(R.id.button_to_date);

        buttonStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatePickerStart = new DatePickerDialog(
                        getActivity(), datePickerStart, mYearStart, mMonthStart, mDayStart);
                mDatePickerStart.show();
            }
        });

        buttonEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatePickerEnd = new DatePickerDialog(
                        getActivity(), datePickerEnd, mYearEnd, mMonthEnd, mDayEnd);
                mDatePickerEnd.show();
            }
        });


        try {
            final String[] offerTypes = Const.getOfferTypeList();
            ArrayAdapter<String> strAdapter = new ArrayAdapter<String>(
                    getActivity(),
                    R.layout.support_simple_spinner_dropdown_item, offerTypes);
            strAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
            spinnerOfferType.setAdapter(strAdapter);
            layoutCashTakeAway.setVisibility(View.GONE);
            layoutFreeItem.setVisibility(View.GONE);
        }catch (Exception e){

        }
        spinnerOfferType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1 || position == 2){
                    layoutCashTakeAway.setVisibility(View.VISIBLE);
                    layoutFreeItem.setVisibility(View.GONE);
                }else if (position == 3){
                    layoutCashTakeAway.setVisibility(View.GONE);
                    layoutFreeItem.setVisibility(View.VISIBLE);
                }else{
                    layoutCashTakeAway.setVisibility(View.GONE);
                    layoutFreeItem.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (position == -1){
            textTitle.setText("Add Offer");
            buttonSave.setText("Save");
        }else{
            textTitle.setText("Update Offer");
            buttonSave.setText("Update");
            try {
                if (mOfferList != null) {
                    Offer offer = mOfferList.get(position);
                    mOfferId = offer.getId();
                    editTitle.setText(""+offer.getTitle());
                    editDescription.setText("" + offer.getDescription());
                    editMinAmount.setText(""+offer.getMinimumAmount());
                    spinnerOfferType.setSelection(offer.getOfferType());

                    buttonStartDate.setText(offer.getFromDate());
                    buttonEndDate.setText(offer.getToDate());
                    Logcat.e("Tag", "Date: " + offer.getFromDate());
                    mYearStart = Integer.parseInt(offer.getFromDate().substring(0,4));
                    mMonthStart = Integer.parseInt(offer.getFromDate().substring(5,7)) -1;
                    mDayStart = Integer.parseInt(offer.getFromDate().substring(8,offer.getFromDate().length()));

                    mYearEnd = Integer.parseInt(offer.getToDate().substring(0,4));
                    mMonthEnd = Integer.parseInt(offer.getToDate().substring(5,7)) -1;
                    mDayEnd = Integer.parseInt(offer.getToDate().substring(8,offer.getFromDate().length()));

                    if (offer.getOfferType() == Const.OFFER_ITEM_FREE){
                        layoutCashTakeAway.setVisibility(View.GONE);
                        layoutFreeItem.setVisibility(View.VISIBLE);
                        editFreeItem.setText(""+offer.getItemFree());

                    }else{
                        layoutCashTakeAway.setVisibility(View.VISIBLE);
                        layoutFreeItem.setVisibility(View.GONE);
                        editMinAmount.setText(""+offer.getMinimumAmount());
                        editDiscountRate.setText(""+offer.getDiscountRate());

                    }

                }
            }catch (Exception e){

            }



        }
        buttonSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Logcat.e("TAG", "ID " + spinnerOfferType.getSelectedItemId());

                if (checkNullPointer(buttonStartDate,buttonEndDate,editTitle,editDescription,spinnerOfferType,editMinAmount,editDiscountRate,editFreeItem)) {

                           if (Utility.isOnline(getActivity())){
                             //  Snackbar.make(containerLayout, "Done !", Snackbar.LENGTH_LONG).show();

                               new AddOffer(position,mOfferId).execute(editTitle.getText().toString(), editDescription.getText().toString()
                                       ,spinnerOfferType.getSelectedItemPosition()+"", buttonStartDate.getText().toString(), buttonEndDate.getText().toString()
                                       , editMinAmount.getText().toString(), editDiscountRate.getText().toString(), editFreeItem.getText().toString(),"1");
                           }else{
                               Snackbar.make(containerLayout, "No internet connection found!", Snackbar.LENGTH_LONG).show();
                           }
                           dialog.dismiss();

                }

            }

        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }

        });

        dialog.show();



    }

    public boolean checkNullPointer(Button Sdate,Button Edate,EditText editTitle,EditText editDesc,Spinner spinnerType,EditText editMinAmount,EditText editDiscountRate,EditText editItemFree){
        if (Utility.notBlank(editTitle, false)) {
            if (Utility.notBlank(editDesc, false)) {
                if (!Sdate.getText().toString().equals("Start date")){
                    if (!Edate.getText().toString().equals("End date")){
                       if (spinnerType.getSelectedItemPosition() != 0){
                           if (spinnerType.getSelectedItemPosition() == 3){
                               if (Utility.notBlank(editItemFree, false)) {
                                   return true;
                               }
                               return false;
                           }else{
                               if (Utility.notBlank(editMinAmount, false)) {
                                   if (Utility.notBlank(editDiscountRate, false)) {
                                       return true;
                                   }
                                   return false;
                               }
                               return false;
                           }

                       }else{
                           TextView errorText = (TextView) spinnerType.getSelectedView();
                           errorText.setError("Reqiured");
                           errorText.setTextColor(Color.RED);
                           return false;
                       }
                    }
                    Snackbar.make(mLayoutMain, "Please select End Date", Snackbar.LENGTH_SHORT).show();
                    return false;
                }
                Snackbar.make(mLayoutMain, "Please select Start Date", Snackbar.LENGTH_SHORT).show();
                return false;
            }
            return false;
        }
        return false;
    }

    private DatePickerDialog.OnDateSetListener datePickerStart = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            try {
                mYearStart = selectedYear;
                mMonthStart = selectedMonth;
                mDayStart = selectedDay;
                String birthdate = new StringBuilder().append(mYearStart).append("-")
                        .append(mMonthStart + 1).append("-").append(mDayStart).append(" ")
                        + "";

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
                date = dateFormat.parse(birthdate);
                Calendar calender = Calendar.getInstance();
                calender.setTime(date);
                SimpleDateFormat month_date = new SimpleDateFormat("yyyy-MM-dd");
                String month_name = month_date.format(calender.getTime());
                buttonStartDate.setText(month_name);

            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    };

    private DatePickerDialog.OnDateSetListener datePickerEnd = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            try {
                mYearEnd = selectedYear;
                mMonthEnd = selectedMonth;
                mDayEnd = selectedDay;
                String birthdate = new StringBuilder().append(mYearEnd).append("-")
                        .append(mMonthEnd + 1).append("-").append(mDayEnd).append(" ")
                        + "";

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
                date = dateFormat.parse(birthdate);
                Calendar calender = Calendar.getInstance();
                calender.setTime(date);
                SimpleDateFormat month_date = new SimpleDateFormat("yyyy-MM-dd");
                String month_name = month_date.format(calender.getTime());
                buttonEndDate.setText(month_name);
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    };

    public class AddOffer extends AsyncTask<String,Integer,JSONObject>{

        SpotsDialog pd;

        int mPosition,mOfferId;
      //  Category mCategory;

        public AddOffer(int position,int offerId){
            this.mPosition = position;
            this.mOfferId = offerId;
          //  if (mPosition != -1 && mCategories != null){
               // mCategory = mCategories.get(mPosition);

          //  }

        }

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
                String params = "Title="+ URLEncoder.encode(strings[0], "UTF-8") +"&Description="+URLEncoder.encode(strings[1],"UTF-8") +
                        "&OfferType="+ URLEncoder.encode(strings[2], "UTF-8") +"&FromDate="+URLEncoder.encode(strings[3],"UTF-8") +
                        "&ToDate="+ URLEncoder.encode(strings[4], "UTF-8") +"&MinimumAmount="+URLEncoder.encode(strings[5],"UTF-8") +
                        "&DiscountRate="+ URLEncoder.encode(strings[6], "UTF-8") +"&ItemFree="+URLEncoder.encode(strings[7],"UTF-8") +
                        "&OfferFrom="+ URLEncoder.encode(strings[8], "UTF-8");
                String url = "";
                if (mPosition == -1){
                    url = Const.ADD_OFFERS + params;
                }else {
                        url = Const.UPDATE_OFFERS + "OfferId=" + mOfferId + "&" +params;
                }
                Logcat.e("URL","URL : "+url);
                return  parser.getJSONFromUrl(url,JSONParser.GET,null);

            }catch (Exception e){
                Logcat.e("AddCAtegory","doInBackground Error : " + e.toString());
                return  null;
            }

        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);

            try {
                pd.dismiss();
                if (result != null){
                    if (result.has("Status") && !result.isNull("Status")) {
                        int status = result.getInt("Status");
                        if (status == 1) {
                            if (result.has("Data") && !result.isNull("Data")) {
                                Offer offer = Offer.getOfferFromJson(result.getJSONObject("Data"));

                                if (mPosition == -1){
                                    if (mOfferList == null){
                                        mOfferList = new ArrayList<Offer>();
                                        mOfferList.add(offer);
                                        fillGrid();
                                    }else{
                                        mOfferList.add(offer);
                                        mAdapter.notifyDataSetChanged();
                                    }
                                }else {
                                    if (mOfferList != null){
                                        mOfferList.set(mPosition,offer);
                                        mAdapter.notifyDataSetChanged();
                                        //fillGrid();
                                    }
                                }


                            }else{
                                Snackbar.make(containerLayout, "Unable to process", Snackbar.LENGTH_LONG).show();
                            }
                        }else{
                            Snackbar.make(containerLayout, "" + result.getString("Message"), Snackbar.LENGTH_LONG)
                                    .show();
                        }
                    }else{
                        Snackbar.make(containerLayout, "Something went wrong", Snackbar.LENGTH_LONG).show();
                    }
                }

            }catch (Exception e){
                Logcat.e("AddCAtegory","OnPost Error : " + e.toString());
            }
        }
    }

}