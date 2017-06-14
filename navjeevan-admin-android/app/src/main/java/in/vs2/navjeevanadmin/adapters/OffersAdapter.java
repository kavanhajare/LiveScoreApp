package in.vs2.navjeevanadmin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import in.vs2.navjeevanadmin.R;
import in.vs2.navjeevanadmin.models.Const;
import in.vs2.navjeevanadmin.models.Offer;


public class OffersAdapter extends BaseAdapter {
	@SuppressWarnings("unused")
	private Context context;
	private ArrayList<Offer> mOfferList;
	private LayoutInflater mInflater;

	public OffersAdapter(Context context, ArrayList<Offer> entries) {

		// TODO Auto-generated constructor stub
		this.context = context;
		this.mOfferList = entries;
		this.mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mOfferList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mOfferList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;

			try {
				if (convertView == null) {
					convertView = mInflater.inflate(R.layout.list_item_offer, null);
					holder = new ViewHolder();
					holder.textTitle = (TextView) convertView
							.findViewById(R.id.textview_title);

					holder.textOfferType = (TextView) convertView
							.findViewById(R.id.textview_offer_type);

					holder.textDesc = (TextView) convertView
							.findViewById(R.id.textview_desc);

					holder.textDate = (TextView) convertView
							.findViewById(R.id.textview_date);

					holder.textIsActive = (TextView) convertView
							.findViewById(R.id.textview_offer_isactive);

					convertView.setTag(holder);
				} else
					holder = (ViewHolder) convertView.getTag();

				holder.textTitle.setText(mOfferList.get(position).getTitle());

				try {
					if (mOfferList.get(position).getOfferType() == Const.OFFER_CASH_DISCOUNT){
						holder.textOfferType.setText("(Cash Discount)");

					}else if (mOfferList.get(position).getOfferType() == Const.OFFER_ITEM_FREE){
						holder.textOfferType.setText("(Item Free)");

					}else if (mOfferList.get(position).getOfferType() == Const.OFFER_TAKE_AWAY) {
						holder.textOfferType.setText("(Take Away)");
					}
					}catch (Exception e){

				}

				/*SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date date = new Date();
				date = dateFormat.parse(birthdate);
				Calendar calender = Calendar.getInstance();
				calender.setTime(date);
				SimpleDateFormat month_date = new SimpleDateFormat("yyyy-MM-dd");
				String month_name = month_date.format(calender.getTime());*/

				Calendar c = Calendar.getInstance();
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				String formattedDate = df.format(c.getTime());

				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date fromDate = new Date();
				Date toDate = new Date();
				fromDate = dateFormat.parse(mOfferList.get(position).getFromDate());
				toDate = dateFormat.parse(mOfferList.get(position).getToDate());
				if (mOfferList.get(position).getFromDate().equals(formattedDate) || mOfferList.get(position).getToDate().equals(formattedDate) ){
					//Logcat.e("TAg","date matched");
					holder.textIsActive.setVisibility(View.VISIBLE);
				}else{
					if (new Date().after(fromDate) && !new Date().after(toDate)) {
					//	Logcat.e("TAg","date in Range");
						holder.textIsActive.setVisibility(View.VISIBLE);
					}else{
					//	Logcat.e("TAg","date out Range");
						holder.textIsActive.setVisibility(View.GONE);
					}
				}
				holder.textDesc.setText(mOfferList.get(position).getDescription());
				holder.textDate.setText("Date : "+mOfferList.get(position).getFromDate() + " to "+mOfferList.get(position).getToDate());

			} catch (Exception e) {
				// TODO: handle exception
			}
		return convertView;
	}

	private class ViewHolder {
		TextView textTitle,textDesc,textDate,textOfferType,textIsActive;

	}

}
