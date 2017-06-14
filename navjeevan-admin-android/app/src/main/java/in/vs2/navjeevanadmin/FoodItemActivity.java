package in.vs2.navjeevanadmin;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;

import in.vs2.navjeevanadmin.adapters.FoodItemAdapter;
import in.vs2.navjeevanadmin.models.Category;
import in.vs2.navjeevanadmin.models.Const;
import in.vs2.navjeevanadmin.models.FoodItem;
import in.vs2.navjeevanadmin.utils.JSONParser;
import in.vs2.navjeevanadmin.utils.Logcat;
import in.vs2.navjeevanadmin.utils.Utility;
import in.vs2.navjeevanadmin.views.GridSpacingItemDecoration;


public class FoodItemActivity extends BaseActivity implements FoodItemAdapter.FoodListener{

    RecyclerView listView;

    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;

    ArrayList<FoodItem> mItems;

    Category category;

    CoordinatorLayout containerLayout;
    FloatingActionButton fabAdd;

    static final String SAVED_ITEMS = "Items";
    int categoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fooditem);

        initViews(savedInstanceState);

    }


    private void initViews(Bundle savedInstanceState){

        if (getIntent().hasExtra("Category")){
            category = (Category)getIntent().getSerializableExtra("Category");
            setActionBarTitle(category.getCategory(),true);
        }else{
            setActionBarTitle(getResources().getString(R.string.app_name),true);
        }

        containerLayout = (CoordinatorLayout) findViewById(R.id.main_content);

        fabAdd = (FloatingActionButton)findViewById(R.id.fab_add);

        // Calling the RecyclerView
        listView = (RecyclerView) findViewById(R.id.list);
        listView.setHasFixedSize(false);

        // The number of Columns
        //mLayoutManager = new GridLayoutManager(getActivity(), 1);

        int spanCount = 2;
        int spacing = 10;
        boolean includeEdge = true;
        listView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));

        // The number of Columns
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            if (Utility.isTablet(FoodItemActivity.this)){
                mLayoutManager = new GridLayoutManager(FoodItemActivity.this, 2);
            }else{
                mLayoutManager = new GridLayoutManager(FoodItemActivity.this, 1);
            }

        }
        else{
            if (Utility.isTablet(FoodItemActivity.this)){
                mLayoutManager = new GridLayoutManager(FoodItemActivity.this, 3);
            }else{
                mLayoutManager = new GridLayoutManager(FoodItemActivity.this, 2);
            }

        }
        listView.setLayoutManager(mLayoutManager);


        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMenuDialog(-1);
            }
        });


        if (savedInstanceState != null) {
            mItems = (ArrayList<FoodItem>) savedInstanceState.getSerializable(SAVED_ITEMS);
        }



        if (mItems != null){
            fillGrid();
        }else{


            if (category != null){
                if (Utility.isOnline(FoodItemActivity.this)){
                    new GetFoodItem().execute();
                }else{
                    Snackbar.make(containerLayout, "No internet connection found!", Snackbar.LENGTH_LONG).show();
                }
            }

        }

    }

    private void fillGrid(){
        if (mItems != null) {
            mAdapter = new FoodItemAdapter(mItems,FoodItemActivity.this,FoodItemActivity.this);
            listView.setAdapter(mAdapter);
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

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(SAVED_ITEMS, mItems);
    }

    //Add Menu Dialog
    public void addMenuDialog(final int position) {


        final Dialog dialog = new Dialog(FoodItemActivity.this,R.style.Base_Theme_AppCompat_Light_Dialog);
        dialog.setContentView(R.layout.dialog_add_menu);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);


        final TextView textTitle = (TextView) dialog.findViewById(R.id.dialog_title);


        final EditText editTitle = (EditText) dialog.findViewById(R.id.edit_title);

        final EditText editPrice = (EditText) dialog.findViewById(R.id.edit_price);

        final EditText editDescription = (EditText) dialog.findViewById(R.id.edit_description);

        final Button buttonSave = (Button) dialog.findViewById(R.id.button_save);

        final Button buttonCancel = (Button) dialog.findViewById(R.id.button_cancel);

        if (position == -1){
            textTitle.setText("ADD MENU");
            buttonSave.setText("Save");
        }else{
            textTitle.setText("UPDATE MENU");
            buttonSave.setText("Update");

            if (mItems != null) {
                FoodItem foodItem = mItems.get(position);
                editTitle.setText(""+foodItem.getName());
                editDescription.setText(""+foodItem.getDescription());
                editPrice.setText(""+foodItem.getPrice());
            }
        }
        buttonSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                if (Utility.notBlank(editTitle, false) && Utility.notBlank(editPrice, false)) {

                    if (Utility.isOnline(FoodItemActivity.this)){

                        new AddFoodItem(position).execute(editTitle.getText().toString().trim()
                                ,editPrice.getText().toString().trim()
                                ,editDescription.getText().toString().trim());
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

    public class AddFoodItem extends AsyncTask<String,Integer,JSONObject>{

        ProgressDialog pd;

        int mPosition;
        FoodItem mItem;

        public AddFoodItem(int position){
            this.mPosition = position;
            if (mPosition != -1 && mItems != null){
                mItem = mItems.get(mPosition);

            }

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(FoodItemActivity.this);
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.setCanceledOnTouchOutside(false);
            pd.show();
        }

        @Override
        protected JSONObject doInBackground(String... strings) {
            try {
                JSONParser parser = new JSONParser(FoodItemActivity.this);
                //&categoryId=&name=&price=&description=
                String params = "&name="+ URLEncoder.encode(strings[0], "UTF-8")
                        +"&price="+ URLEncoder.encode(strings[1], "UTF-8")
                        +"&description="+URLEncoder.encode(strings[2],"UTF-8");
                String url = "";
                if (mPosition == -1){
                    url = Const.ADD_FOODITEM + category.getCategoryId() + params;
                }else {
                    if (mItem != null){
                        url = Const.UPDATE_FOODITEM + mItem.getMenuId() + "&categoryId=" + mItem.getCategoryId() + params;
                    }else {
                        return null;
                    }

                }

                return  parser.getJSONFromUrl(url,JSONParser.GET,null);

            }catch (Exception e){
                Logcat.e("AddFoodItem", "doInBackground Error : " + e.toString());
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
                                FoodItem foodItem = FoodItem.getFoodItemFromJson(result.getJSONObject("Data"));

                                if (mPosition == -1){
                                    if (mItems == null){
                                        mItems = new ArrayList<FoodItem>();
                                        mItems.add(foodItem);
                                        fillGrid();
                                    }else{
                                        mItems.add(foodItem);
                                        mAdapter.notifyDataSetChanged();
                                    }
                                }else {
                                    if (mItems != null){
                                        mItems.set(mPosition,foodItem);
                                        mAdapter.notifyDataSetChanged();

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
                Logcat.e("AddFoodItem","OnPost Error : " + e.toString());
            }
        }
    }

    public class GetFoodItem extends AsyncTask<String,Integer,JSONObject> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(FoodItemActivity.this);
            pd.setMessage("Loading...");
            pd.setCancelable(false);
            pd.setCanceledOnTouchOutside(false);
            pd.show();
        }

        @Override
        protected JSONObject doInBackground(String... strings) {
            try {
                JSONParser parser = new JSONParser(FoodItemActivity.this);
                return  parser.getJSONFromUrl(Const.GET_FOODITEM+category.getCategoryId(),JSONParser.GET,null);

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
                        mItems = FoodItem.getFoodItemsFromJson(result.getJSONArray("Data"));
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


    public class UpdateFoodItemStatus extends AsyncTask<String,Integer,JSONObject>{

        ProgressDialog pd;

        int mPosition;
        boolean foodStatus;
        FoodItem mItem;

        public UpdateFoodItemStatus(int position,boolean status){
            this.mPosition = position;
            this.foodStatus = status;
            if (mPosition != -1 && mItems != null){
                mItem = mItems.get(mPosition);

            }

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(FoodItemActivity.this);
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.setCanceledOnTouchOutside(false);
            pd.show();
        }

        @Override
        protected JSONObject doInBackground(String... strings) {
            try {
                JSONParser parser = new JSONParser(FoodItemActivity.this);

                String url = Const.UPDATE_FOODITEM_STATUS + mItem.getMenuId() + "&status=" + (foodStatus?1:0);

                return  parser.getJSONFromUrl(url,JSONParser.GET,null);

            }catch (Exception e){
                Logcat.e("AddFoodItem", "doInBackground Error : " + e.toString());
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
                            mItems.get(mPosition).setStatus((foodStatus ?1:0));
                        }else{
                            mItems.get(mPosition).setStatus((foodStatus ?0:1));
                            Snackbar.make(containerLayout, "" + result.getString("Message"), Snackbar.LENGTH_LONG)
                                    .show();
                        }
                    }else{
                        Snackbar.make(containerLayout, "Something went wrong", Snackbar.LENGTH_LONG).show();
                        mItems.get(mPosition).setStatus((foodStatus ?0:1));
                    }
                }
                if (mAdapter != null){
                    mAdapter.notifyDataSetChanged();
                }

            }catch (Exception e){
                Logcat.e("AddFoodItem","OnPost Error : " + e.toString());
            }
        }
    }

    @Override
    public void onEdit(int position) {
        if (mItems != null) {
            addMenuDialog(position);
        }
    }

    @Override
    public void onShowHide(int position, boolean isHidden) {
        Logcat.e("Position : " + position,""+isHidden);

        if (Utility.isOnline(FoodItemActivity.this)){
            new UpdateFoodItemStatus(position,isHidden).execute();
        }else{
            mItems.get(position).setStatus((isHidden ?0:1));
            if (mAdapter != null){
                mAdapter.notifyDataSetChanged();
            }
        }
        //&status=
    }



}