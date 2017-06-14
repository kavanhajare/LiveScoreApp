package vs2.navjivanclient.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.sql.Date;
import java.util.ArrayList;

import vs2.navjivanclient.OrderDetailActivity;
import vs2.navjivanclient.R;
import vs2.navjivanclient.models.FoodOrder;
import vs2.navjivanclient.models.OrderDetail;


public class UserOrderAdapter extends RecyclerView.Adapter<UserOrderAdapter.ViewHolder> {

    ArrayList<FoodOrder> mOrders;

    private Context mContext;

    // Allows to remember the last item shown on screen
    private int lastPosition = -1;


    int COLOR_ACCEPTED,COLOR_CANCELLED,COLOR_PENDING;

    public UserOrderAdapter(ArrayList<FoodOrder> orders, Context context) {
        super();
        this.mOrders = orders;
        this.mContext= context;

        COLOR_ACCEPTED = mContext.getResources().getColor(R.color.accent);
        COLOR_CANCELLED = Color.RED;
        COLOR_PENDING = Color.DKGRAY;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_user_order, viewGroup, false);
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

        String relativeTime=""+ DateUtils.getRelativeTimeSpanString(time, now, DateUtils.DAY_IN_MILLIS);

        viewHolder.textItems.setText("" + getOrderedItems(mOrders.get(i)));
        viewHolder.textAmount.setText(mContext.getString(R.string.ruppee) + " " + mOrders.get(i).getAmountRound());
       // viewHolder.textOrderedOn.setText("" + mOrders.get(i).getOrderedOn());
        viewHolder.textOrderedOn.setText(relativeTime);
        if(mOrders.get(i).getOrderType()==1) {
            viewHolder.textOrderType.setText("Home Delivery");
        }
        else if(mOrders.get(i).getOrderType()==2) {
            viewHolder.textOrderType.setText("Take Away");
            viewHolder.textOrderType.setTextColor(Color.parseColor("#aa14ad"));
        }


        viewHolder.textOrderNumber.setText("Order #"+mOrders.get(i).getOrderId());

        final int status =  mOrders.get(i).getStatus();
        if(status == FoodOrder.HOTEL_ACCEPTED){
            viewHolder.textStatus.setText(mContext.getResources().getString(R.string.accept_icon) + " ACCEPTED");
            viewHolder.textStatus.setTextColor(COLOR_ACCEPTED);
            viewHolder.textReasonText.setVisibility(View.VISIBLE);
            viewHolder.textReasonText.setText("Remark: " + mOrders.get(i).getReason());
        }else if(status == FoodOrder.HOTEL_CANCELLED){
            viewHolder.textStatus.setText(mContext.getResources().getString(R.string.cancel_icon)+" CANCELLED BY HOTEL");
            viewHolder.textStatus.setTextColor(COLOR_CANCELLED);
            viewHolder.textReasonText.setVisibility(View.VISIBLE);
            viewHolder.textReasonText.setText("Remark: "+mOrders.get(i).getReason());
        }else if(status == FoodOrder.USER_CANCELLED) {
            viewHolder.textStatus.setText(mContext.getResources().getString(R.string.cancel_icon)+" YOU CANCELLED");
            viewHolder.textStatus.setTextColor(COLOR_CANCELLED);
        }else{
            viewHolder.textStatus.setText("PENDING");
            viewHolder.textStatus.setTextColor(COLOR_PENDING);
        }

        viewHolder.textItems.setText("" + getOrderedItems(mOrders.get(i)));

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FoodOrder order = mOrders.get(i);
                Intent intent = new Intent(mContext, OrderDetailActivity.class);
                intent.putExtra("FoodOrder",order);
                mContext.startActivity(intent);

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
        public TextView textItems,textOrderedOn,textAmount,textStatus,textOrderNumber,textReasonText,textOrderType;


        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView)itemView.findViewById(R.id.cardview);

            textItems = (TextView)itemView.findViewById(R.id.text_items);
            textOrderedOn = (TextView)itemView.findViewById(R.id.text_order_on);
            textAmount = (TextView)itemView.findViewById(R.id.text_amount);

            textStatus = (TextView)itemView.findViewById(R.id.text_status);
            textOrderNumber = (TextView)itemView.findViewById(R.id.text_order_number);
            textReasonText= (TextView)itemView.findViewById(R.id.text_reason_text);
            textOrderType=(TextView)itemView.findViewById(R.id.text_order_type);

        }
    }

    private String getOrderedItems(FoodOrder order){
        ArrayList<OrderDetail> details = order.getOrderDetails();

        String items = "";
        for (OrderDetail orderDetail:details) {
            items += orderDetail.getName()+"("+orderDetail.getQuantity()+")"+", ";
        }
        if (items.length() > 2){
            items = items.substring(0, items.length()-2);
        }
        return  items;
    }

}