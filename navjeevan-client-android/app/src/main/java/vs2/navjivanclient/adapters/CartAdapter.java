package vs2.navjivanclient.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import vs2.navjivanclient.R;
import vs2.navjivanclient.databases.DatabaseFunctions;
import vs2.navjivanclient.models.FoodCart;
import vs2.navjivanclient.models.FoodItem;
import vs2.navjivanclient.utils.Logcat;

public class CartAdapter extends BaseAdapter {

	@SuppressWarnings("unused")
	private Context context;
	private ArrayList<FoodCart> mItems;
	private LayoutInflater mInflater;

	CartListener listener;
	public interface CartListener{
		public void onUpdate();
	}

	public CartAdapter(Context context, ArrayList<FoodCart> cartItems,CartListener listener) {
		super();
		this.context = context;
		this.mItems = cartItems;
		this.mInflater = LayoutInflater.from(context);
		this.listener = listener;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mItems.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mItems.get(position);
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
			convertView = mInflater.inflate(R.layout.list_item_cart, null);
			holder = new ViewHolder();
			//holder.imageThumb = (ImageView)convertView.findViewById(R.id.image_thumb);
			holder.textTitle = (TextView)convertView.findViewById(R.id.text_title);
			holder.textPrice = (TextView)convertView.findViewById(R.id.text_price);

			//Cart addition
			holder.textCount = (TextView)convertView.findViewById(R.id.text_count);
			holder.buttonAdd = (FloatingActionButton) convertView.findViewById(R.id.button_add);
			holder.buttonRemove = (FloatingActionButton) convertView.findViewById(R.id.button_remove);
		
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final FoodItem foodItem = mItems.get(position).getItem();

		holder.textTitle.setText("" + foodItem.getName());
		final double total = foodItem.getPrice() * mItems.get(position).getQuantity();

		holder.textPrice.setText( context.getResources().getString(R.string.ruppee)+" " + foodItem.getPriceRound() + " X "
				+ mItems.get(position).getQuantity() + "\n" +
				context.getResources().getString(R.string.ruppee)+" " + getRound(total));



		/*
		try {
			Picasso.with(context)
					.load(foodItem.getImageUrl())
					.placeholder(R.drawable.default_item)
					.transform(new RoundedTransformation(4, 0))
					.fit()
					.into(holder.imageThumb);
		}catch (Exception e){
			Logcat.e("ShowImage", "Error : " + e.toString());
		}

		*/


		holder.textCount.setText(""+ mItems.get(position).getQuantity());

		holder.buttonAdd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				int count = 0;
				try {
					count = Integer.parseInt(holder.textCount.getText().toString().trim());
					count = count +1;

				}catch (Exception e){
					Logcat.e("Add Count " ,"Error : " + e.toString());
				}

				FoodCart cart = new FoodCart(foodItem,count);

				mItems.set(position,cart);

				DatabaseFunctions.addToCart(cart);

				listener.onUpdate();

				notifyDataSetChanged();
				holder.textCount.setText(""+ DatabaseFunctions.getQuantity(foodItem.getMenuId()));


			}
		});

		holder.buttonRemove.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				int count = 0;
				try {
					count = Integer.parseInt(holder.textCount.getText().toString().trim());
					if (count > 0){
						count = count -1;

						FoodCart cart = new FoodCart(foodItem,count);
						if (count == 0){
							DatabaseFunctions.deleteMenu(foodItem.getMenuId());
							mItems.remove(position);
						}else {
							DatabaseFunctions.addToCart(cart);
							mItems.set(position,cart);
						}


						notifyDataSetChanged();
						holder.textCount.setText(""+ DatabaseFunctions.getQuantity(foodItem.getMenuId()));


					}

					listener.onUpdate();

				}catch (Exception e){
					Logcat.e("Add Count " ,"Error : " + e.toString());
				}


			}
		});
		return convertView;
	}


	public class ViewHolder {
		ImageView imageThumb;
		TextView textTitle,textPrice,textCount;
		FloatingActionButton buttonAdd,buttonRemove;
	}


	public String getRound(double value){
		DecimalFormat df = new DecimalFormat("#.00");
		return df.format(value);
	}
	
}
