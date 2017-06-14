package vs2.navjivanclient.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import vs2.navjivanclient.R;
import vs2.navjivanclient.objects.Area;

public class AreaSpinnerAdapter extends BaseAdapter {
	@SuppressWarnings("unused")
	private Context context;
	private ArrayList<Area> mClassMasterList;
	private LayoutInflater mInflater;

	public AreaSpinnerAdapter(Context context, ArrayList<Area> entries) {

		// TODO Auto-generated constructor stub
		this.context = context;
		this.mClassMasterList = entries;
		this.mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mClassMasterList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mClassMasterList.get(position);
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
					convertView = mInflater.inflate(R.layout.list_item_area_spinner, null);
					holder = new ViewHolder();
					holder.textAreaName = (TextView) convertView
							.findViewById(R.id.text_area_name);

					convertView.setTag(holder);
				} else
					holder = (ViewHolder) convertView.getTag();

				holder.textAreaName.setText(mClassMasterList.get(position).getAreaName());

			} catch (Exception e) {
				// TODO: handle exception
			}
		return convertView;
	}

	private class ViewHolder {
		TextView textAreaName;

	}

}
