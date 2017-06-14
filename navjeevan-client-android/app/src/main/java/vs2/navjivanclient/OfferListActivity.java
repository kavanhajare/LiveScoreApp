package vs2.navjivanclient;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import dmax.dialog.SpotsDialog;
import vs2.navjivanclient.adapters.OffersAdapter;
import vs2.navjivanclient.objects.Offer;
import vs2.navjivanclient.utils.Logcat;


public class OfferListActivity extends BaseActivity {


    private ListView mListViewOffer;
    private ArrayList<Offer> mOfferList;
    private OffersAdapter mAdapter;
    private TextView mTextViewEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_list);
        setActionBarTitle("Offers", true);
        initGloble();
    }
    public void getOffers(){
        Calendar c = Calendar.getInstance();
        Logcat.e("Date", "Current time => " + c.getTime());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c.getTime());
        Logcat.e("Date", "formattedDate=> " + formattedDate);
        new OfferList().execute(formattedDate);
    }
    public  void showAlertDialog(Context context, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("" + message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getOffers();
            }
        });
        builder.create().show();
    }

    public void initGloble() {
        mListViewOffer = (ListView)findViewById(R.id.listview_offer);
        mTextViewEmpty = (TextView)findViewById(R.id.textview_empty_list);
        mListViewOffer.setEmptyView(mTextViewEmpty);

       /* if (getIntent().hasExtra("Message")){
            showAlertDialog(OfferListActivity.this, "" + getIntent().getExtras().getString("Message"));

        }else{
            getOffers();
        }*/
        getOffers();

        mListViewOffer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                Intent intentOffer = new Intent(OfferListActivity.this, OfferDetailActivity.class);
                intentOffer.putExtra("Title", "" + mOfferList.get(position).getTitle());
                intentOffer.putExtra("Description", "" + mOfferList.get(position).getDescription());
                intentOffer.putExtra("OfferType", "" + mOfferList.get(position).getOfferType());
                intentOffer.putExtra("FromDate", "" + mOfferList.get(position).getFromDate());
                intentOffer.putExtra("ToDate", "" + mOfferList.get(position).getToDate());
                intentOffer.putExtra("MinimumAmount", "" + mOfferList.get(position).getMinimumAmount());
                try {
                    intentOffer.putExtra("DiscountRate", "" + mOfferList.get(position).getDiscountRate());
                    intentOffer.putExtra("FreeItem",""+mOfferList.get(position).getItemFree());
                }catch (Exception e){

                }

                startActivity(intentOffer);
            }
        });

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

    private class OfferList extends
            AsyncTask<String, Void, ArrayList<Offer>> {

        SpotsDialog pd;
        Context c;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            try {
                c= OfferListActivity.this;
                pd = new SpotsDialog(OfferListActivity.this,R.style.CustomDialog);
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
                pd.dismiss();
                if (result != null && result.size() > 0) {
                    Logcat.e("Date","onPostExecute");
                    mOfferList = result;
                    loadOffers(mOfferList);
                }
            } catch (Exception e) {
                // TODO: handle exception
                mOfferList.clear();
            }
            super.onPostExecute(result);
        }
    }
    public void loadOffers(ArrayList<Offer> list){
        Logcat.e("Date","loadOffers");
        mAdapter = new OffersAdapter(
                OfferListActivity.this, list);
        mListViewOffer.setAdapter(mAdapter);
    }


}