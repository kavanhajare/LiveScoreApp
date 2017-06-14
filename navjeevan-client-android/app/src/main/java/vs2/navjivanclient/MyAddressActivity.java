package vs2.navjivanclient;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import vs2.navjivanclient.adapters.MyAddressAdapter;
import vs2.navjivanclient.objects.Address;
import vs2.navjivanclient.objects.User;


public class MyAddressActivity extends BaseActivity {

    ListView listView;

    ArrayList<Address> mAddress;

    private MyAddressAdapter mAdapter;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myaddress);

        setActionBarTitle("Pick Address",true);
        initViews(savedInstanceState);

    }

    private void initViews(Bundle savedInstanceState){

        user = User.getUserDetailFromPreference(MyAddressActivity.this);
        if (user != null){
            mAddress = user.getAddressArrayList();
        }


        listView = (ListView) findViewById(R.id.list);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Address address = (Address) mAdapter.getItem(i);
                Intent returnIntent = new Intent();
                returnIntent.putExtra("Address",address);
                setResult(MainActivity.PICK_ADDRESS, returnIntent);
                finish();
            }
        });

        fillGrid();

    }

    private void fillGrid(){

        if (mAddress != null) {
            mAdapter = new MyAddressAdapter(MyAddressActivity.this,mAddress);
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



}