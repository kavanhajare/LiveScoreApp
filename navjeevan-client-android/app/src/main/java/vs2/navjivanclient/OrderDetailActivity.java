package vs2.navjivanclient;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONObject;

import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;

import dmax.dialog.SpotsDialog;
import vs2.navjivanclient.adapters.OrderDetailAdapter;
import vs2.navjivanclient.models.Const;
import vs2.navjivanclient.models.FoodOrder;
import vs2.navjivanclient.models.OrderDetail;
import vs2.navjivanclient.objects.User;
import vs2.navjivanclient.utils.JSONParser;
import vs2.navjivanclient.utils.Utility;


public class OrderDetailActivity extends BaseActivity {

    ListView listView;

    ArrayList<OrderDetail> mItems;

    OrderDetailAdapter mAdapter;

    View footerView;

    TextView textTotal,textGrandTotal,textMessage,textAddress,textReasonTitle;

    double total,grandTotal;
    //double delieveryCharge;

    EditText editReason;

    LinearLayout layoutReason;

    User user;

    Button buttonCancelOrder;

    FoodOrder order;

    private TextView mTextViewItemFree,mTextViewCashDiscount,mTextViewTakeAway;
    private RelativeLayout mLayoutCashDiscount,mLayoutTakeAway,mLayoutGrandTotal;
    private LinearLayout mLayoutItemFree;
    private TextView mTextViewCashDiscountValue,mTextViewTakeAwayDiscountValue;
    private boolean mIsCashDiscount=false,mIsItemFree=false,mIsTakeAway=false;
    private int mOfferCashIndex,mOfferItemFreeIndex,mOfferTakeAwayIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        setActionBarTitle("Order Details",true);
        initViews(savedInstanceState);

    }

    private void initViews(Bundle savedInstanceState){

        user = User.getUserDetailFromPreference(OrderDetailActivity.this);


        footerView = (View) getLayoutInflater().inflate(R.layout.layout_order_detail_footer, null);

        textTotal = (TextView) footerView.findViewById(R.id.text_bill_total);
        textGrandTotal = (TextView) footerView.findViewById(R.id.text_grand_total);
       // textDelieveryCharge = (TextView) footerView.findViewById(R.id.text_delievery);

        mTextViewItemFree = (TextView) footerView.findViewById(R.id.textview_item_free_value);
        mTextViewCashDiscount = (TextView) footerView.findViewById(R.id.text_cash_discounts);
        mTextViewTakeAway = (TextView) footerView.findViewById(R.id.text_take_away_discounts);

        mTextViewCashDiscountValue = (TextView) footerView.findViewById(R.id.textview_cash_discount);
        mTextViewTakeAwayDiscountValue = (TextView) footerView.findViewById(R.id.textview_take_discount);

        mLayoutCashDiscount = (RelativeLayout) footerView.findViewById(R.id.layout_cash_discount);
        mLayoutTakeAway = (RelativeLayout) footerView.findViewById(R.id.layout_take_away_discount);
        mLayoutGrandTotal = (RelativeLayout) footerView.findViewById(R.id.layout_grand_total);
        mLayoutItemFree = (LinearLayout) footerView.findViewById(R.id.layout_item_free);

        textMessage = (TextView) footerView.findViewById(R.id.text_message);
        textReasonTitle=(TextView)footerView.findViewById(R.id.text_reason_title);

        textAddress = (TextView) footerView.findViewById(R.id.text_address);

        editReason = (EditText)footerView.findViewById(R.id.edit_reason);
        buttonCancelOrder = (Button) footerView.findViewById(R.id.button_cancel_order);


        layoutReason = (LinearLayout)footerView.findViewById(R.id.layout_reason);

        listView = (ListView) findViewById(R.id.list);


        listView.addFooterView(footerView);


        if (getIntent().hasExtra("FoodOrder")){
            order = (FoodOrder)getIntent().getSerializableExtra("FoodOrder");

            if(order.getStatus() == FoodOrder.HOTEL_ACCEPTED){
                textReasonTitle.setVisibility(View.VISIBLE);
                textMessage.setVisibility(View.VISIBLE);
                textMessage.setText(order.getReason());
                layoutReason.setVisibility(View.GONE);
            }else if(order.getStatus() == FoodOrder.HOTEL_CANCELLED){
                textReasonTitle.setVisibility(View.VISIBLE);
                textMessage.setVisibility(View.VISIBLE);
                textMessage.setText(order.getReason());
                layoutReason.setVisibility(View.GONE);
            }else if(order.getStatus() == FoodOrder.USER_CANCELLED){
                textMessage.setVisibility(View.VISIBLE);
                textMessage.setText("Cancelled by you : "+ order.getReason());
                layoutReason.setVisibility(View.GONE);
            }else{
                textMessage.setVisibility(View.GONE);
                layoutReason.setVisibility(View.VISIBLE);
            }

            setActionBarTitle("Order #" + order.getOrderId(),true);

            textAddress.setText(""+order.getAddress().getDisplayAddress());
            fillGrid();

        }



        buttonCancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utility.isOnline(OrderDetailActivity.this)){
                    if(Utility.notBlank(editReason,false)){
                        new CancelOrder().execute(editReason.getText().toString());
                    }
                }else{
                    toast("No internet connection found!",true);
                }
            }
        });


    }

    private void fillGrid(){

        mItems = order.getOrderDetails();

        if (mItems != null) {
            mAdapter = new OrderDetailAdapter(OrderDetailActivity.this,mItems);
            listView.setAdapter(mAdapter);
        }else{
            mAdapter = new OrderDetailAdapter(OrderDetailActivity.this,new ArrayList<OrderDetail>());
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

    private void setValues(){

        total = 0;
        //delieveryCharge= 0;
        grandTotal = 0;

        if (user != null){


            if (mItems != null && mItems.size() > 0){

               // total = order.getAmount() - order.getDeliveryCharge();
                for (OrderDetail cart :  mItems) {
                    total += cart.getTotalAmount();
                }
                grandTotal = order.getAmount();
               // delieveryCharge = order.getDeliveryCharge();

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


                // textDelieveryCharge.setText(getResources().getString(R.string.ruppee) + " " + getRound(delieveryCharge));
            }else{
                textTotal.setText(getResources().getString(R.string.ruppee) + " 0.00");
               // textGrandTotal.setText(getResources().getString(R.string.ruppee) + " 0.00");
                mLayoutCashDiscount.setVisibility(View.GONE);
                mLayoutGrandTotal.setVisibility(View.GONE);
                mLayoutTakeAway.setVisibility(View.GONE);
                mLayoutItemFree.setVisibility(View.GONE);



                // textDelieveryCharge.setText(getResources().getString(R.string.ruppee) + " 0.00");
            }

        }

    }

/*    private void setValues(){

        total = 0;
        delieveryCharge= 0;
        grandTotal = 0;

        if (user != null){


            if (mItems != null && mItems.size() > 0){

                total = order.getAmount() - order.getDeliveryCharge();
                grandTotal = order.getAmount();
                delieveryCharge = order.getDeliveryCharge();

                textTotal.setText(getResources().getString(R.string.ruppee) + " " + getRound(total));
                textGrandTotal.setText(getResources().getString(R.string.ruppee) + " " + getRound(grandTotal));
               // textDelieveryCharge.setText(getResources().getString(R.string.ruppee) + " " + getRound(delieveryCharge));
            }else{
                textTotal.setText(getResources().getString(R.string.ruppee) + " 0.00");
                textGrandTotal.setText(getResources().getString(R.string.ruppee) + " 0.00");
               // textDelieveryCharge.setText(getResources().getString(R.string.ruppee) + " 0.00");
            }

        }

    }*/


    public String getRound(double value){
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(value);
    }

    public class CancelOrder extends AsyncTask<String,Integer,JSONObject> {

        SpotsDialog pd;

        String message= "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new SpotsDialog(OrderDetailActivity.this,R.style.CustomDialog);
            pd.show();
        }

        @Override
        protected JSONObject doInBackground(String... strings) {
            try {
                JSONParser parser = new JSONParser(OrderDetailActivity.this);

                message = strings[0];
                return  parser.getJSONFromUrl(Const.CANCEL_ORDER+order.getOrderId()+"&reason=" + URLEncoder.encode(strings[0],"UTF-8"), JSONParser.GET,null);

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
                            Intent intent = new Intent("RemoveOrder");
                            // You can also include some extra data.
                            toast("" + result.getString("Message"),true);
                            intent.putExtra("OrderId",order.getOrderId());
                            intent.putExtra("Reason",message);
                            LocalBroadcastManager.getInstance(OrderDetailActivity.this).sendBroadcast(intent);
                            finish();


                        }else{
                            toast("" + result.getString("Message"),true);
                        }
                    }

                }else{
                    toast("Something went wrong at server",true);
                }



            }catch (Exception e){

            }
        }
    }


}