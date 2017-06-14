package in.vs2.navjeevanadmin;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;

import in.vs2.navjeevanadmin.adapters.OrderDetailAdapter;
import in.vs2.navjeevanadmin.models.Const;
import in.vs2.navjeevanadmin.models.FoodOrder;
import in.vs2.navjeevanadmin.models.OrderDetail;
import in.vs2.navjeevanadmin.utils.JSONParser;
import in.vs2.navjeevanadmin.utils.Logcat;
import in.vs2.navjeevanadmin.utils.Utility;


public class OrderDetailActivity extends BaseActivity {

    ListView listView;

    ArrayList<OrderDetail> mItems;

    OrderDetailAdapter mAdapter;

    View footerView;

    TextView textTotal, textGrandTotal, textMessage, textAddress, textName, textMobile, textDeliveryType;
    Spinner spinReason;
    double total, grandTotal;
    private SharedPreferences permissionStatus;

    private static final int REQUEST_PERMISSION_SETTING = 101;
    LinearLayout layoutReason;

    Button buttonCancelOrder, buttonAcceptOrder;

    ImageButton buttonCall;
boolean sentToSettings;
    FoodOrder order;

    private TextView mTextViewItemFree, mTextViewCashDiscount, mTextViewTakeAway;
    private RelativeLayout mLayoutCashDiscount, mLayoutTakeAway, mLayoutGrandTotal;
    private LinearLayout mLayoutItemFree;
    private TextView mTextViewCashDiscountValue, mTextViewTakeAwayDiscountValue;
    private boolean mIsCashDiscount = false, mIsItemFree = false, mIsTakeAway = false;
    private int mOfferCashIndex, mOfferItemFreeIndex, mOfferTakeAwayIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        permissionStatus = getSharedPreferences("permissionStatus",MODE_PRIVATE);
        setActionBarTitle("Order Details", true);
        initViews(savedInstanceState);

    }

    private void initViews(Bundle savedInstanceState) {

        footerView = (View) getLayoutInflater().inflate(R.layout.layout_order_detail_footer, null);

        textTotal = (TextView) footerView.findViewById(R.id.text_bill_total);
        textGrandTotal = (TextView) footerView.findViewById(R.id.text_grand_total);

        mTextViewItemFree = (TextView) footerView.findViewById(R.id.textview_item_free_value);
        mTextViewCashDiscount = (TextView) footerView.findViewById(R.id.text_cash_discounts);
        mTextViewTakeAway = (TextView) footerView.findViewById(R.id.text_take_away_discounts);

        mTextViewCashDiscountValue = (TextView) footerView.findViewById(R.id.textview_cash_discount);
        mTextViewTakeAwayDiscountValue = (TextView) footerView.findViewById(R.id.textview_take_discount);

        mLayoutCashDiscount = (RelativeLayout) footerView.findViewById(R.id.layout_cash_discount);
        mLayoutTakeAway = (RelativeLayout) footerView.findViewById(R.id.layout_take_away_discount);
        mLayoutGrandTotal = (RelativeLayout) footerView.findViewById(R.id.layout_grand_total);
        mLayoutItemFree = (LinearLayout) footerView.findViewById(R.id.layout_item_free);


        textName = (TextView) footerView.findViewById(R.id.text_name);
        textMobile = (TextView) footerView.findViewById(R.id.text_mobile);

        textMessage = (TextView) footerView.findViewById(R.id.text_message);

        textAddress = (TextView) footerView.findViewById(R.id.text_address);
        spinReason = (Spinner) footerView.findViewById(R.id.spin_reason);

        buttonCancelOrder = (Button) footerView.findViewById(R.id.button_cancel_order);

        buttonCall = (ImageButton) footerView.findViewById(R.id.button_call);

        buttonAcceptOrder = (Button) footerView.findViewById(R.id.button_accept_order);

        layoutReason = (LinearLayout) footerView.findViewById(R.id.layout_reason);
        textDeliveryType = (TextView) footerView.findViewById(R.id.text_delivery_type);

        listView = (ListView) findViewById(R.id.list);


        listView.addFooterView(footerView);


        if (getIntent().hasExtra("FoodOrder")) {
            order = (FoodOrder) getIntent().getSerializableExtra("FoodOrder");

            if (order.getStatus() == FoodOrder.HOTEL_ACCEPTED) {
                textMessage.setVisibility(View.VISIBLE);
                layoutReason.setVisibility(View.GONE);
            } else if (order.getStatus() == FoodOrder.HOTEL_CANCELLED) {
                textMessage.setVisibility(View.VISIBLE);
                textMessage.setText(order.getReason());
                layoutReason.setVisibility(View.GONE);
            } else if (order.getStatus() == FoodOrder.USER_CANCELLED) {
                textMessage.setVisibility(View.VISIBLE);
                textMessage.setText("Cancelled by user : " + order.getReason());
                layoutReason.setVisibility(View.GONE);
            } else {
                textMessage.setVisibility(View.GONE);
                layoutReason.setVisibility(View.VISIBLE);
            }

            if (!order.isToday()) {
                buttonAcceptOrder.setVisibility(View.GONE);
            }

            setActionBarTitle("Order #" + order.getOrderId(), true);

            textAddress.setText("" + order.getAddress().getDisplayAddress());


            textName.setText("" + order.getUserName());
            textMobile.setText("" + order.getMobile());
            if (order.getOrderType() == 1) {
                textDeliveryType.setText("Home Delivery");
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                        this, R.array.home_delivery_remarks, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinReason.setAdapter(adapter);

            } else if (order.getOrderType() == 2) {
                textDeliveryType.setText("Take Away");
                textDeliveryType.setTextColor(Color.parseColor("#aa14ad"));
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                        this, R.array.take_away_remarks, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinReason.setAdapter(adapter);
            }
            fillGrid();

        }


        buttonCancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utility.isOnline(OrderDetailActivity.this)) {
                    String reason = spinReason.getItemAtPosition(9).toString();
                    if (reason != "") {
                        new CancelOrder().execute(reason);
                    }
                } else {
                    toast("No internet connection found!", true);
                }
            }
        });

        buttonAcceptOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utility.isOnline(OrderDetailActivity.this)) {

                    new AcceptOrder().execute(spinReason.getSelectedItem().toString());

                } else {
                    toast("No internet connection found!", true);
                }
            }
        });

        buttonCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + order.getMobile()));
                    if (ActivityCompat.checkSelfPermission(OrderDetailActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(OrderDetailActivity.this, Manifest.permission.CALL_PHONE)) {
                            //Show Information about why you need the permission
                            AlertDialog.Builder builder = new AlertDialog.Builder(OrderDetailActivity.this);
                            builder.setTitle("Need Call Permission");
                            builder.setMessage("This app needs call permission.");
                            builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    ActivityCompat.requestPermissions(OrderDetailActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                                }
                            });
                            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            builder.show();
                        } else if (permissionStatus.getBoolean(Manifest.permission.CALL_PHONE, false)) {
                            //Previously Permission Request was cancelled with 'Dont Ask Again',
                            // Redirect to Settings after showing Information about why you need the permission
                            AlertDialog.Builder builder = new AlertDialog.Builder(OrderDetailActivity.this);
                            builder.setTitle("Need Call Permission");
                            builder.setMessage("This app needs call permission.");
                            builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    sentToSettings = true;
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                                    intent.setData(uri);
                                    startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                                    Toast.makeText(getBaseContext(), "Go to Permissions to Grant Storage", Toast.LENGTH_LONG).show();
                                }
                            });
                            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            builder.show();
                        } else {
                            //just request the permission
                            ActivityCompat.requestPermissions(OrderDetailActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                        }


                        SharedPreferences.Editor editor = permissionStatus.edit();
                        editor.putBoolean(Manifest.permission.CALL_PHONE, true);
                        editor.commit();


                    } else {
                        //You already have the permission, just go ahead.
                        startActivity(callIntent);
                    }

                } catch (ActivityNotFoundException activityException) {
                    Logcat.e("myphone dialer", "Call failed :" + activityException.toString());
                }

            }
            });
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
        // for ActivityCompat#requestPermissions for more details.
          }



    private void fillGrid() {

        mItems = order.getOrderDetails();

        if (mItems != null) {
            mAdapter = new OrderDetailAdapter(OrderDetailActivity.this, mItems);
            listView.setAdapter(mAdapter);
        } else {
            mAdapter = new OrderDetailAdapter(OrderDetailActivity.this, new ArrayList<OrderDetail>());
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

   /* private void setValues() {

        total = 0;
        delieveryCharge = 0;
        grandTotal = 0;


        if (mItems != null && mItems.size() > 0) {

            total = order.getAmount() - order.getDeliveryCharge();
            grandTotal = order.getAmount();
            delieveryCharge = order.getDeliveryCharge();

            textTotal.setText(getResources().getString(R.string.ruppee) + " " + getRound(total));
            textGrandTotal.setText(getResources().getString(R.string.ruppee) + " " + getRound(grandTotal));
            textDelieveryCharge.setText(getResources().getString(R.string.ruppee) + " " + getRound(delieveryCharge));
        } else {
            textTotal.setText(getResources().getString(R.string.ruppee) + " 0.00");
            textGrandTotal.setText(getResources().getString(R.string.ruppee) + " 0.00");
            textDelieveryCharge.setText(getResources().getString(R.string.ruppee) + " 0.00");
        }


    }*/

    private void setValues() {

        total = 0;
        grandTotal = 0;


        if (mItems != null && mItems.size() > 0) {

            for (OrderDetail cart :  mItems) {
                total += cart.getTotalAmount();
            }
            grandTotal = order.getAmount();
            textTotal.setText(getResources().getString(R.string.ruppee) + " " + getRound(total));
            for (int i =0 ;i < order.getOfferDetails().size(); i++){
                if (order.getOfferDetails().get(i).getOfferType() == Const.OFFER_CASH_DISCOUNT) {
                    mIsCashDiscount = true;
                    mOfferCashIndex = i;
                    break;
                }else {
                    mIsCashDiscount = false;
                }
            }
            for (int i =0 ;i < order.getOfferDetails().size(); i++){
                if (order.getOfferDetails().get(i).getOfferType() == Const.OFFER_TAKE_AWAY) {
                    mIsTakeAway = true;
                    mOfferTakeAwayIndex = i;
                    break;
                }else {
                    mIsTakeAway = false;
                }
            }
            for (int i =0 ;i < order.getOfferDetails().size(); i++){
                if (order.getOfferDetails().get(i).getOfferType() == Const.OFFER_ITEM_FREE) {
                    mIsItemFree = true;
                    mOfferItemFreeIndex = i;
                    break;
                }else {
                    mIsItemFree = false;
                }
            }
            if (mIsCashDiscount){
                mLayoutCashDiscount.setVisibility(View.VISIBLE);
                int cashDiscount = order.getOfferDetails().get(mOfferCashIndex).getDiscountRate() ;
                mTextViewCashDiscountValue.setText("("+cashDiscount +" % discount)");
                mTextViewCashDiscount.setText(getResources().getString(R.string.ruppee) + " " + order.getOfferDetails().get(mOfferCashIndex).getDiscountAmount());
            }else{
                mLayoutCashDiscount.setVisibility(View.GONE);
            }

            if (mIsTakeAway){
                mLayoutTakeAway.setVisibility(View.VISIBLE);
                int takeawayDiscount = order.getOfferDetails().get(mOfferTakeAwayIndex).getDiscountRate() ;
                mTextViewTakeAwayDiscountValue.setText("("+takeawayDiscount +" % discount)");
                mTextViewTakeAway.setText(getResources().getString(R.string.ruppee) + " " + order.getOfferDetails().get(mOfferTakeAwayIndex).getDiscountAmount());
            }else{
                mLayoutTakeAway.setVisibility(View.GONE);
            }

            if (mIsItemFree){
                mLayoutItemFree.setVisibility(View.VISIBLE);
                mTextViewItemFree.setText(""+order.getOfferDetails().get(mOfferItemFreeIndex).getItemFree());

            }else{
                mLayoutItemFree.setVisibility(View.GONE);
            }
            if (mIsCashDiscount || mIsTakeAway){
                mLayoutGrandTotal.setVisibility(View.VISIBLE);
                textGrandTotal.setText(getResources().getString(R.string.ruppee) + " " + getRound(grandTotal));
            }else {
                mLayoutGrandTotal.setVisibility(View.GONE);
            }


        } else {
            textTotal.setText(getResources().getString(R.string.ruppee) + " 0.00");
            mLayoutCashDiscount.setVisibility(View.GONE);
            mLayoutGrandTotal.setVisibility(View.GONE);
            mLayoutTakeAway.setVisibility(View.GONE);
            mLayoutItemFree.setVisibility(View.GONE);
        }


    }


    public String getRound(double value) {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(value);
    }

    public class CancelOrder extends AsyncTask<String, Integer, JSONObject> {

        ProgressDialog pd;

        String message = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(OrderDetailActivity.this);
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.setCanceledOnTouchOutside(false);
            pd.show();
        }

        @Override
        protected JSONObject doInBackground(String... strings) {
            try {
                JSONParser parser = new JSONParser(OrderDetailActivity.this);

                message = strings[0];
                return parser.getJSONFromUrl(Const.CANCEL_ORDER + order.getOrderId() + "&reason=" + URLEncoder.encode(strings[0], "UTF-8"), JSONParser.GET, null);

            } catch (Exception e) {
                return null;
            }

        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);

            try {
                pd.dismiss();

                if (result != null) {
                    if (result.has("Status")) {
                        int status = result.getInt("Status");
                        if (status == 1) {
                            Intent intent = new Intent("RemoveOrder");
                            // You can also include some extra data.
                            toast("" + result.getString("Message"), true);
                            intent.putExtra("OrderId", order.getOrderId());
                            intent.putExtra("Status", FoodOrder.HOTEL_CANCELLED);

                            intent.putExtra("Reason", message);
                            LocalBroadcastManager.getInstance(OrderDetailActivity.this).sendBroadcast(intent);
                            finish();


                        } else {
                            toast("" + result.getString("Message"), true);
                        }
                    }

                } else {
                    toast("Something went wrong at server", true);
                }


            } catch (Exception e) {

            }
        }
    }

    public class AcceptOrder extends AsyncTask<String, Integer, JSONObject> {

        ProgressDialog pd;

        String message = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(OrderDetailActivity.this);
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.setCanceledOnTouchOutside(false);
            pd.show();
        }

        @Override
        protected JSONObject doInBackground(String... strings) {
            try {
                JSONParser parser = new JSONParser(OrderDetailActivity.this);

                message = strings[0];
                String url=Const.ACCEPT_ORDER + order.getOrderId() + "&reason=" + URLEncoder.encode(strings[0], "UTF-8");
                Logcat.e("url",url);
                return parser.getJSONFromUrl(url, JSONParser.GET, null);

            } catch (Exception e) {
                return null;
            }

        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);

            try {
                pd.dismiss();

                if (result != null) {
                    if (result.has("Status")) {
                        int status = result.getInt("Status");
                        if (status == 1) {
                            Intent intent = new Intent("RemoveOrder");
                            // You can also include some extra data.
                            toast("" + result.getString("Message"), true);
                            intent.putExtra("OrderId", order.getOrderId());
                            intent.putExtra("Status", FoodOrder.HOTEL_ACCEPTED);
                            intent.putExtra("Reason", message);
                            LocalBroadcastManager.getInstance(OrderDetailActivity.this).sendBroadcast(intent);
                            finish();


                        } else {
                            toast("" + result.getString("Message"), true);
                        }
                    }

                } else {
                    toast("Something went wrong at server", true);
                }


            } catch (Exception e) {

            }
        }
    }


}