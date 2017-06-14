package in.vs2.navjeevanadmin.adapters;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import in.vs2.navjeevanadmin.OrderDetailActivity;
import in.vs2.navjeevanadmin.R;
import in.vs2.navjeevanadmin.models.FoodOrder;
import in.vs2.navjeevanadmin.utils.Logcat;
import in.vs2.navjeevanadmin.views.CircleImageView;


public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    ArrayList<FoodOrder> mOrders;

    private Context mContext;

    // Allows to remember the last item shown on screen
    private int lastPosition = -1;

    int COLOR_ACCEPTED, COLOR_CANCELLED, COLOR_PENDING;

    boolean isPending;

    public OrderAdapter(ArrayList<FoodOrder> orders, Context context, boolean isPending) {
        super();
        this.mOrders = orders;
        this.mContext = context;
        this.isPending = isPending;

        COLOR_ACCEPTED = mContext.getResources().getColor(R.color.accent);
        COLOR_CANCELLED = Color.RED;
        COLOR_PENDING = Color.GREEN;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_order, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        //EndangeredItem nature = mItems.get(i);


        /*

        viewHolder.cardView.setCardBackgroundColor(Color.parseColor(colors.get(n)));

        */

        viewHolder.textName.setText("" + mOrders.get(i).getUserName());
        viewHolder.textMobile.setText("" + mOrders.get(i).getMobile());
        viewHolder.textAmount.setText(mContext.getString(R.string.ruppee) + " " + mOrders.get(i).getAmountRound());
        viewHolder.textOrderedOn.setText("" + mOrders.get(i).getOrderedOn());

        viewHolder.textOrderNumber.setText("Order #" + mOrders.get(i).getOrderId());
        if (mOrders.get(i).getOrderType() == 1) {
            viewHolder.textOrderType.setText("Home Delivery");
        } else if (mOrders.get(i).getOrderType() == 2) {
            viewHolder.textOrderType.setText("Take Away");
            viewHolder.textOrderType.setTextColor(Color.parseColor("#aa14ad"));
        }
        /*
        try {
            Picasso.with(mContext)
                    .load(mOrders.get(i).getUserImage())
                    .placeholder(R.drawable.default_user)
                    .resize(60,60)
                    .into(viewHolder.imageUser);
        }catch (Exception e){
            Logcat.e("ShowImage", "Error : " + e.toString());
        }
        */

        if (isPending) {
            viewHolder.layoutStatus.setVisibility(View.GONE);
        } else {

            viewHolder.layoutStatus.setVisibility(View.VISIBLE);

            final int status = mOrders.get(i).getStatus();
            if (status == FoodOrder.HOTEL_ACCEPTED) {
                viewHolder.layoutStatus.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                viewHolder.textStatus.setText(mContext.getResources().getString(R.string.accept_icon) + " ACCEPTED");
                viewHolder.textStatus.setTextColor(COLOR_ACCEPTED);
            } else if (status == FoodOrder.HOTEL_CANCELLED) {
                viewHolder.layoutStatus.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                viewHolder.textStatus.setText(mContext.getResources().getString(R.string.cancel_icon) + " HOTEL CANCELLED");
                viewHolder.textStatus.setTextColor(COLOR_CANCELLED);
            } else if (status == FoodOrder.USER_CANCELLED) {
                viewHolder.layoutStatus.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                viewHolder.textStatus.setText(mContext.getResources().getString(R.string.cancel_icon) + " USER CANCELLED");
                viewHolder.textStatus.setTextColor(COLOR_CANCELLED);
            } else {
                viewHolder.layoutStatus.setBackgroundColor(mContext.getResources().getColor(R.color.primary));
                viewHolder.textStatus.setText("PENDING");
                viewHolder.textStatus.setTextColor(mContext.getResources().getColor(R.color.white));
            }
        }


        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FoodOrder order = mOrders.get(i);
                Intent intent = new Intent(mContext, OrderDetailActivity.class);
                intent.putExtra("FoodOrder", order);
                mContext.startActivity(intent);
            }
        });

        viewHolder.buttonCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                call(mOrders.get(i).getMobile());
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
    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated

        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.up_from_bottom);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;
        public ImageView imageUser;
        public TextView textName, textMobile, textOrderedOn, textAmount, textStatus, textOrderNumber, textOrderType;

        ImageButton buttonCall;

        LinearLayout layoutStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cardview);
            imageUser = (CircleImageView) itemView.findViewById(R.id.image_user);

            buttonCall = (ImageButton) itemView.findViewById(R.id.button_call);

            layoutStatus = (LinearLayout) itemView.findViewById(R.id.layout_status);

            textName = (TextView) itemView.findViewById(R.id.text_name);
            textMobile = (TextView) itemView.findViewById(R.id.text_mobile);
            textOrderedOn = (TextView) itemView.findViewById(R.id.text_order_on);
            textAmount = (TextView) itemView.findViewById(R.id.text_amount);
            textStatus = (TextView) itemView.findViewById(R.id.text_status);
            textOrderNumber = (TextView) itemView.findViewById(R.id.text_order_number);
            textOrderType = (TextView) itemView.findViewById(R.id.text_order_type);
        }
    }


    private void call(String phone) {
        try {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + phone));
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mContext.startActivity(callIntent);
        } catch (ActivityNotFoundException activityException) {
            Logcat.e("myphone dialer", "Call failed :"+ activityException.toString());
        }

    }
}
