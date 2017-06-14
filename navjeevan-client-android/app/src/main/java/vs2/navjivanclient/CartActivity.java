package vs2.navjivanclient;


import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import dmax.dialog.SpotsDialog;
import vs2.navjivanclient.adapters.CartAdapter;
import vs2.navjivanclient.databases.DatabaseFunctions;
import vs2.navjivanclient.models.Const;
import vs2.navjivanclient.models.FoodCart;
import vs2.navjivanclient.objects.Address;
import vs2.navjivanclient.objects.Offer;
import vs2.navjivanclient.objects.User;
import vs2.navjivanclient.preference.PreferenceData;
import vs2.navjivanclient.utils.JSONParser;
import vs2.navjivanclient.utils.Logcat;
import vs2.navjivanclient.utils.Utility;


public class CartActivity extends BaseActivity implements CartAdapter.CartListener {

    ListView listView;

    ArrayList<FoodCart> mItems;

    CartAdapter mAdapter;

    View footerView;

    TextView textTotal,textGrandTotal,textAddress,textHotelStatus;

    double total,grandTotal,delieveryCharge,mCashDiscountAmount = 0, mTakeAwayDiscountAmount=0;

    EditText editRemarks;

    User user;

    Button buttonChangeAddress,buttonOrder,buttonTakeAway;

    Address currentAddress;


    LinearLayout layoutPlaceOrder;



    CoordinatorLayout containerLayout;
    private Tracker mTracker;
    public static final int UPDATE_ADDRESS = 1122;


    private ArrayList<Offer> mOfferList;
    ArrayList<Offer> mOffersCashDiscount ;
    ArrayList<Offer> mOffersTakeAwayDiscount;
    ArrayList<Offer> mOffersItemFree;
    private int mOfferCashIndex,mOfferItemFreeIndex,mOfferTakeAwayIndex;
    private boolean mIsCashDiscount=false,mIsItemFree=false,mIsTakeAway=false;
    private TextView mTextViewItemFree,mTextViewCashDiscount,mTextViewTakeAway;
    private RelativeLayout mLayoutCashDiscount,mLayoutTakeAway,mLayoutGrandTotal;
    private LinearLayout mLayoutItemFree;
    private TextView mTextViewCashDiscountValue,mTextViewTakeAwayDiscountValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        setActionBarTitle("Cart", true);
        sendTracking();
        getOffer();

    }

    public void getOffer(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c.getTime());
        new OfferList().execute(formattedDate);
    }
    private void sendTracking()
    {
        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        mTracker.setScreenName("Cart Activity");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }
    public boolean isOldPinCode(String pincode){
       try {
           if (pincode.length() > 3){
               return  true;
           }
       }catch (Exception e){

       }
        return  false;
    }

    private void initViews(){

        containerLayout = (CoordinatorLayout) findViewById(R.id.main_content);

        user = User.getUserDetailFromPreference(CartActivity.this);
        if (user != null){
            currentAddress = user.getAddressArrayList().get(0);
            Logcat.e("Pincode","Pincode length: "+currentAddress.getPincode().length());
            if (isOldPinCode(currentAddress.getPincode())){
                Intent intent = new Intent(CartActivity.this,UpdateAddress.class);
                intent.putExtra("AddressId",""+currentAddress.getAddressId());
                intent.putExtra("Address",""+currentAddress.getAddress());
                intent.putExtra("Landmark",""+currentAddress.getLandMark());
                startActivityForResult(intent, UPDATE_ADDRESS);
                Logcat.e("Pincode","Pincode is old");
            }else{
                Logcat.e("Pincode","Pincode is new");
            }
        }

        footerView = (View) getLayoutInflater().inflate(R.layout.layout_cart_footer, null);


        layoutPlaceOrder = (LinearLayout) footerView.findViewById(R.id.layout_place_order);
        textTotal = (TextView) footerView.findViewById(R.id.text_bill_total);

        mTextViewItemFree = (TextView) footerView.findViewById(R.id.textview_item_free_value);
        mTextViewCashDiscount = (TextView) footerView.findViewById(R.id.text_cash_discounts);
        mTextViewTakeAway = (TextView) footerView.findViewById(R.id.text_take_away_discounts);

        mTextViewCashDiscountValue = (TextView) footerView.findViewById(R.id.textview_cash_discount);
        mTextViewTakeAwayDiscountValue = (TextView) footerView.findViewById(R.id.textview_take_discount);

        mLayoutCashDiscount = (RelativeLayout) footerView.findViewById(R.id.layout_cash_discount);
        mLayoutTakeAway = (RelativeLayout) footerView.findViewById(R.id.layout_take_away_discount);
        mLayoutGrandTotal = (RelativeLayout) footerView.findViewById(R.id.layout_grand_total);
        mLayoutItemFree = (LinearLayout) footerView.findViewById(R.id.layout_item_free);

        textGrandTotal = (TextView) footerView.findViewById(R.id.text_grand_total);

        textHotelStatus  = (TextView) footerView.findViewById(R.id.text_hotel_status);

        textAddress = (TextView) footerView.findViewById(R.id.text_address);
        editRemarks = (EditText)footerView.findViewById(R.id.edit_remarks);
        buttonChangeAddress = (Button) footerView.findViewById(R.id.button_change_address);
        buttonOrder = (Button) footerView.findViewById(R.id.button_place_order);
        buttonTakeAway=(Button)footerView.findViewById(R.id.button_take_away);

        listView = (ListView) findViewById(R.id.list);

        listView.addFooterView(footerView);

        fillGrid();

        buttonChangeAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user.getAddressArrayList().size() > 1) {
                    openActionDialog();
                } else {
                    createNewAddress();
                }

            }
        });

        buttonOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utility.isOnline(CartActivity.this)){
                   if (Double.parseDouble(getRound(total)) >= currentAddress.getMinimumBill()){
                       String json = getOderDetails(Const.HOME_DELIVERY);
                       //  Logcat.e("TAG", "json: " + json);
                       new PlaceOrder(json).execute();
                       mTracker.send(new HitBuilders.EventBuilder()
                               .setCategory("Order")
                               .setAction("Home Delivery")
                               .build());
                   }else {
                       Snackbar.make(containerLayout, "Minimum order must be "+getResources().getString(R.string.ruppee)+" " +getRound(currentAddress.getMinimumBill()), Snackbar.LENGTH_LONG)
                               .show();
                   }
                }else{
                    Snackbar.make(containerLayout, "No internet connection found!", Snackbar.LENGTH_LONG)
                            .show();
                }
            }
        });
      buttonTakeAway.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utility.isOnline(CartActivity.this)){
                    String json = getOderDetails(Const.TAKE_AWAY);
                    //Logcat.e("TAG","json: "+json);
                    new PlaceOrder(json).execute();
                    mTracker.send(new HitBuilders.EventBuilder()
                            .setCategory("Order")
                            .setAction("Take Away")
                            .build());
                }else{
                    Snackbar.make(containerLayout, "No internet connection found!", Snackbar.LENGTH_LONG)
                            .show();
                }
            }
        });


        setHotelStatus();
    }


    private void setHotelStatus(){
        int hotelStatus = PreferenceData.getHotelStatus(CartActivity.this);
        if (hotelStatus == 0){
            layoutPlaceOrder.setVisibility(View.VISIBLE);
            textHotelStatus.setVisibility(View.GONE);
        }else{
            layoutPlaceOrder.setVisibility(View.GONE);

            textHotelStatus.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==MainActivity.PICK_ADDRESS)
        {
            try {
                if (data != null && data.hasExtra("Address")){
                    currentAddress = (Address)data.getSerializableExtra("Address");
                    if (isOldPinCode(currentAddress.getPincode())){
                        Intent intent = new Intent(CartActivity.this,UpdateAddress.class);
                        intent.putExtra("AddressId",""+currentAddress.getAddressId());
                        intent.putExtra("Address",""+currentAddress.getAddress());
                        intent.putExtra("Landmark",""+currentAddress.getLandMark());
                        startActivityForResult(intent, UPDATE_ADDRESS);
                        Logcat.e("Pincode","Pincode is old");
                    }else{
                        Logcat.e("Pincode","Pincode is new");
                    }
                    setValues();
                }else{
                    Snackbar.make(containerLayout, "No address picked!", Snackbar.LENGTH_LONG)
                            .show();
                }

            }catch (Exception e)
            {
                Log.e("PICK_ADDRESS", "error in reading activity result" + e.toString());
            }
        }
        if(resultCode == UPDATE_ADDRESS){
            try {
                currentAddress = (Address)data.getSerializableExtra("Address");
                setValues();
            }catch (Exception e)
            {
                Log.e("UPDATE_ADDRESS", "error in reading activity result" + e.toString());
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
    public void createNewAddress(){

        Intent intent = new Intent(CartActivity.this,AddAddress.class);
        startActivityForResult(intent, MainActivity.PICK_ADDRESS);

    }

    public void pickAddress(){
        Intent intent = new Intent(CartActivity.this,MyAddressActivity.class);
        startActivityForResult(intent, MainActivity.PICK_ADDRESS);
    }

    private void fillGrid(){

        mItems = DatabaseFunctions.getCartItems();

        if (mItems != null) {
            mAdapter = new CartAdapter(CartActivity.this,mItems,CartActivity.this);
            listView.setAdapter(mAdapter);
        }else{
            mAdapter = new CartAdapter(CartActivity.this,new ArrayList<FoodCart>(),CartActivity.this);
            listView.setAdapter(mAdapter);
        }

        setValues();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

/*    private void setValues(){

        total = 0;
        delieveryCharge= 0;
        grandTotal = 0;

        if (user != null){


            if (mItems != null && mItems.size() > 0){

                double  minBill = currentAddress.getMinimumBill();
                double charge =  currentAddress.getDeliveryCharge();

                for (FoodCart cart :  mItems) {
                    total += cart.getItem().getPrice() * cart.getQuantity();
                }

                if (total < minBill){
                    delieveryCharge = charge;
                }else{
                    delieveryCharge = 0;
                }
                grandTotal = total + delieveryCharge;
                textTotal.setText(getResources().getString(R.string.ruppee) + " " + getRound(total));
                textGrandTotal.setText(getResources().getString(R.string.ruppee) + " " + getRound(grandTotal));

                textDelieveryCharge.setText(getResources().getString(R.string.ruppee) + " " + getRound(delieveryCharge));
            }else{
                textTotal.setText(getResources().getString(R.string.ruppee) + " 0.00");
                textGrandTotal.setText(getResources().getString(R.string.ruppee) + " 0.00");
                textDelieveryCharge.setText(getResources().getString(R.string.ruppee) + " 0.00");
            }

            textAddress.setText("" + currentAddress.getDisplayAddress());
            if(delieveryCharge==0)
            {
                textDelieveryCharge.setText("Free");
            }
            // textDelieveryCharge.setText("Free");
        }

    }*/

    private void setValues(){

        total = 0;
        delieveryCharge= 0;
        grandTotal = 0;
        mCashDiscountAmount = 0;
        mTakeAwayDiscountAmount =0;

        if (user != null){


            if (mItems != null && mItems.size() > 0){

                double  minBill = currentAddress.getMinimumBill();
               // double charge =  currentAddress.getDeliveryCharge();

                for (FoodCart cart :  mItems) {
                    total += cart.getItem().getPrice() * cart.getQuantity();
                }
                Logcat.e("total","total: "+total);

                try {

                    if (mOffersCashDiscount != null){
                        for (int i = 0; i< mOffersCashDiscount.size(); i++) {
                            int index = i + 1;
                            if (index < mOffersCashDiscount.size()) {
                                if (total >= mOffersCashDiscount.get(i).getMinimumAmount() && total <= mOffersCashDiscount.get(index).getMinimumAmount()) {
                                    mIsCashDiscount = true;
                                    mOfferCashIndex = i;
                                    break;
                                } else {
                                    mIsCashDiscount = false;
                                }
                            } else {
                                if (total >= mOffersCashDiscount.get(i).getMinimumAmount()) {
                                    mIsCashDiscount = true;
                                    mOfferCashIndex = i;
                                    break;
                                } else {
                                    mIsCashDiscount = false;
                                }

                            }
                        }
                    }

                }catch (Exception e){
                        Logcat.e("mIsCashDiscount","Exception : "+e.toString());
                }
                try {
                    if (mOffersTakeAwayDiscount != null){
                        for (int j = 0; j< mOffersTakeAwayDiscount.size(); j++){
                            int index = j+1;
                            if (index < mOffersTakeAwayDiscount.size()){
                                if (total >= mOffersTakeAwayDiscount.get(j).getMinimumAmount() && total <= mOffersTakeAwayDiscount.get(index).getMinimumAmount()){
                                    mIsTakeAway = true;
                                    mOfferTakeAwayIndex = j;
                                    break;
                                }else{
                                    mIsTakeAway = false;
                                }
                            }else{
                                if (total >= mOffersTakeAwayDiscount.get(j).getMinimumAmount()){
                                    mIsTakeAway = true;
                                    mOfferTakeAwayIndex = j;
                                    break;
                                }else{
                                    mIsTakeAway = false;
                                }

                            }
                        }
                    }
                }catch (Exception e){
                    Logcat.e("mIsTakeAway","Exception : "+e.toString());
                }

                try {
                    if (mOffersItemFree != null){
                        for (int k = 0; k< mOffersItemFree.size(); k++){
                            int index = k+1;
                            if (index < mOffersItemFree.size()){
                                if (total >= mOffersItemFree.get(k).getMinimumAmount() && total <= mOffersItemFree.get(index).getMinimumAmount()){
                                    mIsItemFree =true;
                                    mOfferItemFreeIndex = k;
                                    break;
                                }else{
                                    mIsItemFree =false;
                                }
                            }else{
                                if (total >= mOffersItemFree.get(k).getMinimumAmount()){
                                    mIsItemFree =true;
                                    mOfferItemFreeIndex = k;
                                    break;
                                }else{
                                    mIsItemFree =false;
                                }

                            }
                        }
                    }
                }catch (Exception e){
                    Logcat.e("mIsItemFree","Exception : "+e.toString());

                }

                textTotal.setText(getResources().getString(R.string.ruppee) + " " + getRound(total));

                if (mIsCashDiscount){
                    Logcat.e("Tag", "mIsCashDiscount : " + total);
                    mLayoutCashDiscount.setVisibility(View.VISIBLE);
                    int cashDiscount = mOffersCashDiscount.get(mOfferCashIndex).getDiscountRate();
                    mCashDiscountAmount = total *  cashDiscount;
                    mCashDiscountAmount = mCashDiscountAmount / 100;
                    mTextViewCashDiscountValue.setText("("+cashDiscount +" % discount)");
                    mTextViewCashDiscount.setText(getResources().getString(R.string.ruppee) + " " + getRound(mCashDiscountAmount));

                }else{
                    mLayoutCashDiscount.setVisibility(View.GONE);
                }

                if (mIsTakeAway){
                    Logcat.e("Tag","mIsTakeAway : "+ total);
                    Logcat.e("Tag", "mIsTakeAway getDiscountRate : " + mOfferList.get(mOfferTakeAwayIndex).getDiscountRate());
                    mLayoutTakeAway.setVisibility(View.VISIBLE);
                    int takeAwayDiscount = mOffersTakeAwayDiscount.get(mOfferTakeAwayIndex).getDiscountRate();
                    mTakeAwayDiscountAmount = total *  takeAwayDiscount;
                    mTakeAwayDiscountAmount = mTakeAwayDiscountAmount / 100;
                    mTextViewTakeAwayDiscountValue.setText("("+takeAwayDiscount +" % discount)");
                    mTextViewTakeAway.setText(getResources().getString(R.string.ruppee) + " " + getRound(mTakeAwayDiscountAmount));

                }else {
                    mLayoutTakeAway.setVisibility(View.GONE);

                }

                if (mIsItemFree){
                    mLayoutItemFree.setVisibility(View.VISIBLE);
                    mTextViewItemFree.setText(""+mOffersItemFree.get(mOfferItemFreeIndex).getItemFree());

                }else{
                    mLayoutItemFree.setVisibility(View.GONE);
                }

               if (mIsCashDiscount || mIsTakeAway){
                   double discounts =0;
                   discounts = total - mCashDiscountAmount;
                   grandTotal = discounts - mTakeAwayDiscountAmount;
                   mLayoutGrandTotal.setVisibility(View.VISIBLE);
                   textGrandTotal.setText(getResources().getString(R.string.ruppee) + " " + getRound(grandTotal));
               }else{

                   mLayoutGrandTotal.setVisibility(View.GONE);
               }

            }else{
                mLayoutCashDiscount.setVisibility(View.GONE);
                mLayoutTakeAway.setVisibility(View.GONE);
                mTextViewItemFree.setVisibility(View.GONE);
                textTotal.setText(getResources().getString(R.string.ruppee) + " 0.00");
                textGrandTotal.setText(getResources().getString(R.string.ruppee) + " 0.00");
            }

            textAddress.setText(""+ currentAddress.getDisplayAddress());

        }

    }

    public String getOderDetails(int orderType){
        JSONObject json = new JSONObject();

        try {

            JSONArray array = new JSONArray();

            for (FoodCart cart :  mItems) {
                double itemTotal = cart.getItem().getPrice() * cart.getQuantity();

                JSONObject order = new JSONObject();
                order.put("menuId",cart.getItem().getMenuId());
                order.put("qty",cart.getQuantity());
                order.put("amount",itemTotal);


                array.put(order);
            }
            if(orderType == Const.TAKE_AWAY){
                json.put("deliveryCharge", 0);
            }else{
                json.put("deliveryCharge", delieveryCharge);
            }


            json.put("userId", user.getUserId());
            json.put("hotelId", Const.HOTEL_ID);
            json.put("addressId", currentAddress.getAddressId());
            json.put("remarks", editRemarks.getText().toString().trim());
           // json.put("deliveryCharge", delieveryCharge);
            json.put("OrderDetail", array);
            json.put("orderType",orderType);

            if (mIsCashDiscount || mIsTakeAway || mIsItemFree){
                json.put("grandTotal", grandTotal);
                json.put("Offer",true);
                  if (mIsCashDiscount){
                      try {
                          json.put("CashOfferId",mOffersCashDiscount.get(mOfferCashIndex).getId());
                          json.put("CashDiscountAmount",mCashDiscountAmount);
                      }catch (Exception e){
                          Logcat.e("CashOfferId" , "Error : " +  e.toString());
                      }
                  }else {
                      json.put("CashOfferId",0);
                      json.put("CashDiscountAmount",0);
                  }
                if (mIsTakeAway){
                    try {
                        json.put("TakeAwayOfferId",mOffersTakeAwayDiscount.get(mOfferTakeAwayIndex).getId());
                        json.put("TakeAwayDiscountAmount",mTakeAwayDiscountAmount);
                    }catch (Exception e){
                        Logcat.e("TakeAwayOfferId" , "Error : " +  e.toString());
                    }
                }else{
                    json.put("TakeAwayOfferId",0);
                    json.put("TakeAwayDiscountAmount",0);
                }
                if (mIsItemFree){
                    try {
                        json.put("ItemFreeOfferId",mOffersItemFree.get(mOfferItemFreeIndex).getId());
                    }catch (Exception e){
                        Logcat.e("TakeAwayOfferId" , "Error : " +  e.toString());
                    }
                }else{
                    json.put("ItemFreeOfferId",0);
                }
            }else{
                json.put("grandTotal", total);
                json.put("Offer",false);
            }



        }catch (Exception e){
            Logcat.e("GetOderString" , "Error : " +  e.toString());
        }


        return json.toString();
    }

    public String getRound(double value){
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(value);
    }

    @Override
    public void onUpdate() {
        setValues();
    }



    public class PlaceOrder extends AsyncTask<String,Integer,JSONObject> {

        SpotsDialog pd;
        String mString;

        public PlaceOrder(String jsonString){
            this.mString = jsonString;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new SpotsDialog(CartActivity.this,R.style.CustomDialog);
            pd.show();
        }

        @Override
        protected JSONObject doInBackground(String... strings) {
            try {
                JSONParser parser = new JSONParser(CartActivity.this);

                ContentValues value =  new ContentValues();
                Logcat.e("POST_JOSN",""+ mString);
                value.put("json", URLEncoder.encode(mString,"UTF-8"));

                return  parser.getJSONFromUrl(Const.PLACE_ORDER, JSONParser.POST,value,null);

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
                    if (result.has("Status")){
                        int status = result.getInt("Status");
                        if (status == 1){
                            if (result.has("Data") && !result.isNull("Data")) {

                                PreferenceData.setStringPref(PreferenceData.KEY_USER_JSON, CartActivity.this, result.getJSONObject("Data").toString());
                                user = User.getUserDetailFromPreference(CartActivity.this);

                                DatabaseFunctions.deleteCart();

                                startActivity(new Intent(CartActivity.this,ThankYouActivity.class));
                                finish();
                            }
                        }else if (status == 2){
                            PreferenceData.setHotelStatus(CartActivity.this,0);
                            setHotelStatus();
                        }


                    }
                    Snackbar.make(containerLayout, "" + result.getString("Message"), Snackbar.LENGTH_LONG)
                            .show();

                }else{
                    Snackbar.make(containerLayout,"Something went wrong at server", Snackbar.LENGTH_LONG)
                            .show();
                }



            }catch (Exception e){

            }
        }
    }


    //Choose item from list
    private void openActionDialog() {

        List<String> list = Arrays.asList("New Address", "Pick from existing addresses");

        final CharSequence[] items = list.toArray(new CharSequence[list.size()]);
        AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
        builder.setTitle("Address");

        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("New Address")) {
                    createNewAddress();
                }

                if (items[item].equals("Pick from existing addresses")) {
                    pickAddress();
                }

            }
        }).create().show();
    }

    private class OfferList extends
            AsyncTask<String, Void, ArrayList<Offer>> {

        SpotsDialog pd;
        Context c;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            try {
                c= CartActivity.this;
                pd = new SpotsDialog(CartActivity.this,R.style.CustomDialog);
                pd.show();

            }catch (Exception e) {
                // TODO: handle exception
            }
        }

        @Override
        protected ArrayList<Offer> doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                return Offer.getOfferListFromServer(c,params[0]);

            } catch (Exception e) {
                // TODO: handle exception
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Offer> result) {
            // TODO Auto-generated method stub
            try {

                if (result != null && result.size() > 0) {
                    mOfferList = result;

                    mOffersCashDiscount = new ArrayList<Offer>();
                    mOffersTakeAwayDiscount = new ArrayList<Offer>();
                    mOffersItemFree = new ArrayList<Offer>();

                    for (int i = 0; i< mOfferList.size(); i++) {

                        if (mOfferList.get(i).getOfferType() == Const.OFFER_CASH_DISCOUNT) {
                            mOffersCashDiscount.add(mOfferList.get(i));
                        }
                        if (mOfferList.get(i).getOfferType() == Const.OFFER_TAKE_AWAY){
                            mOffersTakeAwayDiscount.add(mOfferList.get(i));
                        }
                        if (mOfferList.get(i).getOfferType() == Const.OFFER_ITEM_FREE){
                            mOffersItemFree.add(mOfferList.get(i));
                        }
                    }
                }else{
                    mOfferList = new ArrayList<Offer>();
                }
            } catch (Exception e) {
                // TODO: handle exception
                mOfferList = new ArrayList<Offer>();
            }
            initViews();
            if (pd != null && pd.isShowing()){
                pd.dismiss();
            }
            super.onPostExecute(result);
        }
    }
}