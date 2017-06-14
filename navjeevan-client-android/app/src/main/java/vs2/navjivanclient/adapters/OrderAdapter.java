package vs2.navjivanclient.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.sql.Date;
import java.util.ArrayList;

import vs2.navjivanclient.R;
import vs2.navjivanclient.models.FoodOrder;
import vs2.navjivanclient.utils.Logcat;
import vs2.navjivanclient.views.CircleImageView;


public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    ArrayList<FoodOrder> mOrders;

    private Context mContext;

    // Allows to remember the last item shown on screen
    private int lastPosition = -1;


    public OrderAdapter(ArrayList<FoodOrder> orders, Context context) {
        super();
        this.mOrders = orders;
        this.mContext= context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_order, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder,final int i) {
        //EndangeredItem nature = mItems.get(i);


        /*

        viewHolder.cardView.setCardBackgroundColor(Color.parseColor(colors.get(n)));

        */
        long now = System.currentTimeMillis();
        Long time= Date.parse(mOrders.get(i).getOrderedOn());

        String relativeTime=""+ DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS);
        viewHolder.textName.setText("" + mOrders.get(i).getUserName());
        viewHolder.textMobile.setText("" + mOrders.get(i).getMobile());
        viewHolder.textAmount.setText(mContext.getString(R.string.ruppee) + " " + mOrders.get(i).getAmountRound());
      // viewHolder.textOrderedOn.setText("" + mOrders.get(i).getOrderedOn());
        viewHolder.textOrderedOn.setText(relativeTime);

        try {
            Picasso.with(mContext)
                    .load(mOrders.get(i).getUserImage())
                    .placeholder(R.drawable.default_user)
                    .resize(60,60)
                    .into(viewHolder.imageUser);
        }catch (Exception e){
            Logcat.e("ShowImage", "Error : " + e.toString());
        }




        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Category category = mCategories.get(i);
                Intent intent = new Intent(mContext, FoodItemActivity.class);
                intent.putExtra("Category",category);
                mContext.startActivity(intent);
                */
            }
        });

        // Here you apply the animation when the view is bound
        setAnimation(viewHolder.cardView, i);

    }

    @Override
    public int getItemCount() {

        return mOrders.size();

    }

    /**
     * Here is the key method to apply the animation
     */
    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated

        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.up_from_bottom);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }

    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public CardView cardView;
        public ImageView imageUser;
        public TextView textName,textMobile,textOrderedOn,textAmount;


        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView)itemView.findViewById(R.id.cardview);
            imageUser = (CircleImageView)itemView.findViewById(R.id.image_user);

            textName = (TextView)itemView.findViewById(R.id.text_name);
            textMobile = (TextView)itemView.findViewById(R.id.text_mobile);
            textOrderedOn = (TextView)itemView.findViewById(R.id.text_order_on);
            textAmount = (TextView)itemView.findViewById(R.id.text_amount);
        }
    }


}