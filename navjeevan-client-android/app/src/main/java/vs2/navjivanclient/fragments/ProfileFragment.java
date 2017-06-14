package vs2.navjivanclient.fragments;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

import dmax.dialog.SpotsDialog;
import vs2.navjivanclient.AddAddress;
import vs2.navjivanclient.MyAddressActivity;
import vs2.navjivanclient.R;
import vs2.navjivanclient.models.Const;
import vs2.navjivanclient.objects.Address;
import vs2.navjivanclient.objects.User;
import vs2.navjivanclient.preference.PreferenceData;
import vs2.navjivanclient.utils.JSONParser;
import vs2.navjivanclient.utils.Logcat;
import vs2.navjivanclient.utils.Utility;


public class ProfileFragment extends Fragment {

    private static final String tag = ProfileFragment.class.getSimpleName();

    private View mView;

    CoordinatorLayout containerLayout;

    TextView textMobile,textAddress;

    Button buttonChangeAddress,buttonUpdate;

    EditText editName;

    User user;

    public static final int PICK_ADDRESS = 101;

    Address currentAddress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        mView = inflater.inflate(R.layout.fragment_profile, container, false);

        user = User.getUserDetailFromPreference(getActivity());
        initViews();

        return mView;
    }


    private void initViews() {

        containerLayout = (CoordinatorLayout) mView.findViewById(R.id.main_content);




        textMobile = (TextView) mView.findViewById(R.id.text_mobile);
        textAddress = (TextView) mView.findViewById(R.id.text_address);
        editName = (EditText)mView.findViewById(R.id.edit_name);
        buttonChangeAddress = (Button) mView.findViewById(R.id.button_change_address);
        buttonUpdate = (Button) mView.findViewById(R.id.button_update);



        buttonChangeAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user.getAddressArrayList().size() > 1){
                    openActionDialog();
                }else{
                    createNewAddress();
                }

            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utility.isOnline(getActivity())){

                    if (Utility.notBlank(editName,false)){
                        new UpdateProfile().execute(editName.getText().toString());
                    }
                }else{
                    Snackbar.make(containerLayout, "No internet connection found!", Snackbar.LENGTH_LONG)
                            .show();
                }
            }
        });


        setValues();
    }

    private void setValues(){
        if (user != null){
            currentAddress = user.getAddressArrayList().get(0);

            textMobile.setText("Mobile : " + user.getMobileNumber());
            editName.setText(""+user.getUserName());
            textAddress.setText(""+currentAddress.getDisplayAddress());

        }
    }


    public class UpdateProfile extends AsyncTask<String, Integer, JSONObject> {

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

                if (user != null) {
                    String params = "&name=" + URLEncoder.encode(strings[0],"UTF-8")+"&addressId="+currentAddress.getAddressId();
                    return parser.getJSONFromUrl(Const.UPDATE_PROFILE + user.getUserId()+params, JSONParser.GET, null);
                } else {
                    return null;
                }


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
                    if (result.has("Status")){
                        int status = result.getInt("Status");

                        if (status == 1){
                            if (result.has("Data")){
                                PreferenceData.setStringPref(PreferenceData.KEY_USER_JSON, getActivity(), result.getJSONObject("Data").toString());

                                user = User.getUserDetailFromPreference(getActivity());

                                setValues();

                            }
                        }
                        Snackbar.make(containerLayout,result.getString("Message"), Snackbar.LENGTH_LONG)
                                .show();

                    }
                    if (result.has("Data") && !result.isNull("Data")) {

                    } else {
                        Toast.makeText(getActivity(), "" + result.getString("Message"), Toast.LENGTH_LONG).show();
                    }
                }

            } catch (Exception e) {

            }
        }
    }


    public void createNewAddress(){
        Intent intent = new Intent(getActivity(),AddAddress.class);
        startActivityForResult(intent,PICK_ADDRESS);

    }

    public void pickAddress(){
        Intent intent = new Intent(getActivity(),MyAddressActivity.class);
        startActivityForResult(intent,PICK_ADDRESS);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==PICK_ADDRESS){
            try {
                if (data != null && data.hasExtra("Address")){
                    currentAddress = (Address)data.getSerializableExtra("Address");
                    textAddress.setText(""+currentAddress.getDisplayAddress());

                }else{
                    Snackbar.make(containerLayout, "No addressed picked", Snackbar.LENGTH_LONG)
                            .show();
                }

            }catch (Exception e){
                Logcat.e("error", "error in reading activity result" + e.toString());
            }
        }
    }

    //Choose item from list
    private void openActionDialog() {

        List<String> list = Arrays.asList("New Address", "Pick from existing addresses");

        final CharSequence[] items = list.toArray(new CharSequence[list.size()]);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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

}