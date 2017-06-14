package in.vs2.navjeevanadmin.fragments;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;

import dmax.dialog.SpotsDialog;
import in.vs2.navjeevanadmin.R;
import in.vs2.navjeevanadmin.adapters.CategoryAdapter;
import in.vs2.navjeevanadmin.models.Category;
import in.vs2.navjeevanadmin.models.Const;
import in.vs2.navjeevanadmin.utils.JSONParser;
import in.vs2.navjeevanadmin.utils.Logcat;
import in.vs2.navjeevanadmin.utils.Utility;
import in.vs2.navjeevanadmin.views.GridSpacingItemDecoration;

public class HomeFragment extends Fragment implements CategoryAdapter.CategoryListener{

	private static final String tag = HomeFragment.class.getSimpleName();
	
	private View mView;

	RecyclerView listView;

	RecyclerView.LayoutManager mLayoutManager;
	RecyclerView.Adapter mAdapter;

    ArrayList<Category> mCategories;


    CoordinatorLayout containerLayout;
    FloatingActionButton fabAdd;

    static final String SAVED_CATEGORY = "categories";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {


        mView = inflater.inflate(R.layout.fragment_home, container, false);

        initViews();

        if (savedInstanceState != null) {
            mCategories = (ArrayList<Category>) savedInstanceState.getSerializable(SAVED_CATEGORY);
        }

        if (mCategories != null){
            fillGrid();
        }else{
            if (Utility.isOnline(getActivity())){
                new GetCategory().execute();
            }else{
                Snackbar.make(containerLayout, "No internet connection found!", Snackbar.LENGTH_LONG)
                        //.setAction("Undo", mOnClickListener)
                        //.setActionTextColor(Color.RED)
                        .show();
            }
        }
		return mView;
	}


    private void initViews(){

        containerLayout = (CoordinatorLayout) mView.findViewById(R.id.main_content);

        fabAdd = (FloatingActionButton) mView.findViewById(R.id.fab_add);

		// Calling the RecyclerView
		listView = (RecyclerView) mView.findViewById(R.id.list);
		listView.setHasFixedSize(false);

		// The number of Columns
		//mLayoutManager = new GridLayoutManager(getActivity(), 1);

        int spanCount = 2;
        int spacing = 10;
        boolean includeEdge = true;
        listView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));

        // The number of Columns
        if(getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            if (Utility.isTablet(getActivity())){
                mLayoutManager = new GridLayoutManager(getActivity(), 2);
            }else{
                mLayoutManager = new GridLayoutManager(getActivity(), 1);
            }

        }
        else{
            if (Utility.isTablet(getActivity())){
                mLayoutManager = new GridLayoutManager(getActivity(), 3);
            }else{
                mLayoutManager = new GridLayoutManager(getActivity(), 2);
            }

        }
		listView.setLayoutManager(mLayoutManager);


        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCategoryDialog(-1);
            }
        });

	}

    private void fillGrid(){
        if (mCategories != null) {
            mAdapter = new CategoryAdapter(mCategories,getActivity(),HomeFragment.this);
            listView.setAdapter(mAdapter);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(SAVED_CATEGORY, mCategories);
    }

    /*

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        mCategories = (ArrayList<Category>) savedInstanceState.getSerializable(SAVED_CATEGORY);
    }

    */


    @Override
    public void onEdit(int position) {
        if (mCategories != null && mCategories.size() > 0) {
            addCategoryDialog(position);
        }else {
            Snackbar.make(containerLayout,  "No categories found!", Snackbar.LENGTH_LONG)
                    .show();
        }

    }

    public class GetCategory extends AsyncTask<String,Integer,JSONObject>{

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

                return  parser.getJSONFromUrl(Const.GET_CATEGORY,JSONParser.GET,null);

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
                        mCategories = Category.getCategoriesFromJson(result.getJSONArray("Data"));
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



    //Add Category Dialog
    public void addCategoryDialog(final int position) {


        final Dialog dialog = new Dialog(getActivity(),R.style.Base_Theme_AppCompat_Light_Dialog);
        dialog.setContentView(R.layout.dialog_add_category);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);


        final TextView textTitle = (TextView) dialog.findViewById(R.id.dialog_title);


        final EditText editTitle = (EditText) dialog.findViewById(R.id.edit_title);

        final EditText editDescription = (EditText) dialog.findViewById(R.id.edit_description);

        final Button buttonSave = (Button) dialog.findViewById(R.id.button_save);

        final Button buttonCancel = (Button) dialog.findViewById(R.id.button_cancel);

        if (position == -1){
            textTitle.setText("ADD CATEGORY");
            buttonSave.setText("Save");
        }else{
            textTitle.setText("UPDATE CATEGORY");
            buttonSave.setText("Update");

            if (mCategories != null) {
                Category category = mCategories.get(position);
                editTitle.setText(""+category.getCategory());
                editDescription.setText(""+category.getDescription());
            }
        }
        buttonSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                if (Utility.notBlank(editTitle, false)) {

                    if (Utility.isOnline(getActivity())){

                        new AddCategory(position).execute(editTitle.getText().toString(),editDescription.getText().toString());
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

    public class AddCategory extends AsyncTask<String,Integer,JSONObject>{

        SpotsDialog pd;

        int mPosition;
        Category mCategory;

        public AddCategory(int position){
            this.mPosition = position;
            if (mPosition != -1 && mCategories != null){
                mCategory = mCategories.get(mPosition);

            }

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
                String params = "&category="+ URLEncoder.encode(strings[0],"UTF-8") +"&description="+URLEncoder.encode(strings[1],"UTF-8");
                String url = "";
                if (mPosition == -1){
                    url = Const.ADD_CATEGORY + params;
                }else {
                    if (mCategory != null){
                        url = Const.UPDATE_CATEGORY + "&categoryId=" + mCategory.getCategoryId() + params;
                    }else {
                        return null;
                    }

                }

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
                                Category category = Category.getCategoryFromJson(result.getJSONObject("Data"));

                                if (mPosition == -1){
                                    if (mCategories == null){
                                        mCategories = new ArrayList<Category>();
                                        mCategories.add(category);
                                        fillGrid();
                                    }else{
                                        mCategories.add(category);
                                        mAdapter.notifyDataSetChanged();
                                    }
                                }else {
                                    if (mCategories != null){
                                        mCategories.set(mPosition,category);
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