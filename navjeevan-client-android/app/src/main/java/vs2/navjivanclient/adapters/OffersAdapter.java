package vs2.navjivanclient.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import vs2.navjivanclient.R;
import vs2.navjivanclient.models.Const;
import vs2.navjivanclient.objects.Offer;

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

					convertView.setTag(holder);
				} else
					holder = (ViewHolder) convertView.getTag();

				holder.textTitle.setText(mOfferList.get(position).getTitle());

				try {
					if (mOfferList.get(position).getOfferType() == Const.OFFER_CASH_DISCOUNT){
						holder.textOfferType.setText("( Cash Discount )");

					}else if (mOfferList.get(position).getOfferType() == Const.OFFER_ITEM_FREE){
						holder.textOfferType.setText("Item Free");

					}else if (mOfferList.get(position).getOfferType() == Const.OFFER_TAKE_AWAY) {
						holder.textOfferType.setText("Take Away");
					}
					}catch (Exception e){

				}

				holder.textDesc.setText(mOfferList.get(position).getDescription());
				if(mOfferList.get(position).getFromDate().equals(mOfferList.get(position).getToDate())){
					holder.textDate.setText("Today's special offer");
				}else{
					holder.textDate.setText("Date : "+mOfferList.get(position).getFromDate() + " to "+mOfferList.get(position).getToDate());
				}

			} catch (Exception e) {
				// TODO: handle exception
			}
		return convertView;
	}

	private class ViewHolder {
		TextView textTitle,textDesc,textDate,textOfferType;

	}

}
