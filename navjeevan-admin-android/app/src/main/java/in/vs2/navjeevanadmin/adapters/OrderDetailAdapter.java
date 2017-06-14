package in.vs2.navjeevanadmin.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import in.vs2.navjeevanadmin.R;
import in.vs2.navjeevanadmin.models.OrderDetail;


public class OrderDetailAdapter extends BaseAdapter {

	@SuppressWarnings("unused")
	private Context context;
	private ArrayList<OrderDetail> mDetails;
	private LayoutInflater mInflater;

	public OrderDetailAdapter(Context context, ArrayList<OrderDetail> orderDetails) {
		super();
		this.context = context;
		this.mDetails = orderDetails;
		this.mInflater = LayoutInflater.from(context);

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mDetails.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mDetails.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.list_item_order_detail, null);
			holder = new ViewHolder();

			holder.textTitle = (TextView)convertView.findViewById(R.id.text_title);
			holder.textPrice = (TextView)convertView.findViewById(R.id.text_price);


		
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.textTitle.setText("" + mDetails.get(position).getName()+ " ->("
				+ mDetails.get(position).getQuantity()+")");

		holder.textPrice.setText( context.getResources().getString(R.string.ruppee)+" " + mDetails.get(position).getAmountRound() + " X "
				+ mDetails.get(position).getQuantity() + "\n"
				+context.getResources().getString(R.string.ruppee)+" " + mDetails.get(position).getTotalAmountRound());

		return convertView;
	}

	public class ViewHolder {
		TextView textTitle,textPrice;
	}
	
	
	
}
