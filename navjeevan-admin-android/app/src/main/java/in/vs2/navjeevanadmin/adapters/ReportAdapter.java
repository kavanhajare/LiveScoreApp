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
import in.vs2.navjeevanadmin.models.OrderReport;


public class ReportAdapter extends BaseAdapter {

	@SuppressWarnings("unused")
	private Context context;
	private ArrayList<OrderReport> mReports;
	private LayoutInflater mInflater;

	public ReportAdapter(Context context, ArrayList<OrderReport> reports) {
		super();
		this.context = context;
		this.mReports = reports;
		this.mInflater = LayoutInflater.from(context);

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mReports.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mReports.get(position);
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
			convertView = mInflater.inflate(R.layout.list_item_report, null);
			holder = new ViewHolder();

			holder.textTitle = (TextView)convertView.findViewById(R.id.text_order);
			holder.textPrice = (TextView)convertView.findViewById(R.id.text_amount);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.textTitle.setText(mReports.get(position).getOrderNo());
		holder.textPrice.setText(context.getResources().getString(R.string.ruppee) + " " +mReports.get(position).getAmount());

		return convertView;
	}

	public class ViewHolder {
		TextView textTitle,textPrice;
	}
	
	
	
}
