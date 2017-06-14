package vs2.navjivanclient.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import vs2.navjivanclient.R;
import vs2.navjivanclient.objects.Address;

public class MyAddressAdapter extends BaseAdapter {

	@SuppressWarnings("unused")
	private Context context;
	private ArrayList<Address> mAddress;
	private LayoutInflater mInflater;

	public MyAddressAdapter(Context context, ArrayList<Address> addresses) {
		super();
		this.context = context;
		this.mAddress = addresses;
		this.mInflater = LayoutInflater.from(context);

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mAddress.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mAddress.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.list_item_address, null);
			holder = new ViewHolder();
			holder.textTitle = (TextView)convertView.findViewById(R.id.text_title);

		
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}



		holder.textTitle.setText("" + mAddress.get(position).getDisplayAddress());



		return convertView;
	}

	public class ViewHolder {
		TextView textTitle;

	}
	
	
	
}
