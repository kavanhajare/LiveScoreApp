package vs2.navjivanclient;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import vs2.navjivanclient.models.Const;


public class OfferDetailActivity extends BaseActivity {

    private TextView mTextViewOfferType,mTextViewDate,mTextViewDescription,mTextViewMinAmount,mTextViewDiscountRate,mTextViewFreeItem;
    private LinearLayout mLayoutMinAmount,mLayoutDiscountRate,mLayoutFreeItem;
    private String mTitle = "Offer",mDescription="",mOfferType="",
            mItemFree="",mFromDate="",mToDate="",mMinimumAmount="",mDiscountRate="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_detail);
        getBundleData();
        setActionBarTitle(mTitle, true);
        initGloble();
    }
    public void getBundleData() {
        try {
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                mTitle = bundle.getString("Title");
                mDescription = bundle.getString("Description");
                mOfferType = bundle.getString("OfferType");
                mFromDate = bundle.getString("FromDate");
                mToDate = bundle.getString("ToDate");
                mMinimumAmount = bundle.getString("MinimumAmount");
                mDiscountRate = bundle.getString("DiscountRate");
                mItemFree = bundle.getString("FreeItem");

            }
        }catch (Exception e){

        }
    }
    public void initGloble() {
        mTextViewOfferType = (TextView)findViewById(R.id.textview_title);
        mTextViewDate = (TextView)findViewById(R.id.textview_date_value);
        mTextViewDescription = (TextView)findViewById(R.id.textview_desc_value);
        mTextViewMinAmount = (TextView)findViewById(R.id.textview_min_amount_value);
        mTextViewDiscountRate = (TextView)findViewById(R.id.textview_discount_rate_value);
        mTextViewFreeItem   = (TextView)findViewById(R.id.textview_free_item_value);

        mLayoutMinAmount = (LinearLayout)findViewById(R.id.layout_min_amount);
        mLayoutDiscountRate = (LinearLayout)findViewById(R.id.layout_discount_rate);
        mLayoutFreeItem = (LinearLayout)findViewById(R.id.layout_free_item);

       setOfferValues();
    }

    public void setOfferValues(){
        try {
            if (Integer.parseInt(mOfferType) == Const.OFFER_CASH_DISCOUNT){
                mTextViewOfferType.setText("Cash Discount");
            }else if (Integer.parseInt(mOfferType) == Const.OFFER_ITEM_FREE){
                mTextViewOfferType.setText("Item Free");

            }else if (Integer.parseInt(mOfferType) == Const.OFFER_TAKE_AWAY){
                mTextViewOfferType.setText("Take Away");
            }



            if(mFromDate.equals(mToDate)) {
                mTextViewDate.setText(" Today's special offer");
            }else{
                mTextViewDate.setText(" "+mFromDate +" to " +mToDate);
            }

            mTextViewDescription.setText("          "+mDescription);
            mTextViewMinAmount.setText(getResources().getString(R.string.ruppee)+" "+mMinimumAmount);

            if (Integer.parseInt(mOfferType) == Const.OFFER_ITEM_FREE){

                mLayoutDiscountRate.setVisibility(View.GONE);
                mLayoutFreeItem.setVisibility(View.VISIBLE);
                mTextViewFreeItem.setText(""+mItemFree);

            }else{
                mLayoutFreeItem.setVisibility(View.GONE);
                mLayoutDiscountRate.setVisibility(View.VISIBLE);
                mTextViewDiscountRate.setText(""+mDiscountRate +" %");
            }


        }catch (Exception e){

        }
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
}