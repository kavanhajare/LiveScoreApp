package in.vs2.navjeevanadmin.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import in.vs2.navjeevanadmin.R;
import in.vs2.navjeevanadmin.models.FoodItem;
import in.vs2.navjeevanadmin.utils.Logcat;
import in.vs2.navjeevanadmin.views.RoundedTransformation;

public class FoodItemAdapter extends RecyclerView.Adapter<FoodItemAdapter.ViewHolder> {

    ArrayList<FoodItem> mItems;

    private  Context mContext;

    private FoodListener mListener;

    // Allows to remember the last item shown on screen
    private int lastPosition = -1;

    public interface FoodListener{
        public void onEdit(int position);
        public void onShowHide(int position,boolean isHidden);
    }

    public FoodItemAdapter(ArrayList<FoodItem> foodItems, Context context, FoodListener listener) {
        super();
        this.mItems = foodItems;
        this.mContext= context;
        this.mListener = listener;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_menu, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder,final int i) {
        FoodItem foodItem = mItems.get(i);

        viewHolder.textTitle.setText("" + foodItem.getName());
        viewHolder.textPrice.setText( mContext.getResources().getString(R.string.ruppee)+" " + foodItem.getPriceRound());

        try {
            Picasso.with(mContext)
                    .load(foodItem.getImageUrl())
                    .placeholder(R.drawable.default_item)
                    .transform(new RoundedTransformation(4, 0))
                    .fit()
                    .into(viewHolder.imageThumb);
        }catch (Exception e){
            Logcat.e("ShowImage","Error : " + e.toString());
        }

        viewHolder.switchEdit.setChecked(foodItem.getStatus() == 1?true:false);

        viewHolder.switchEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean isChecked = viewHolder.switchEdit.isChecked();

                mItems.get(i).setStatus(isChecked?1:0);
                if (mListener != null){
                    mListener.onShowHide(i,isChecked);
                }
            }
        });


        viewHolder.imageEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null){
                    mListener.onEdit(i);
                }
            }
        });

        // Here you apply the animation when the view is bound
        setAnimation(viewHolder.cardView, i);

    }

    @Override
    public int getItemCount() {

        return mItems.size();

    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public CardView cardView;
        public ImageView imageThumb,imageEdit;
        public TextView textTitle,textPrice;
        public Switch switchEdit;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView)itemView.findViewById(R.id.cardview);
            imageThumb = (ImageView)itemView.findViewById(R.id.image_thumb);
            imageEdit = (ImageView)itemView.findViewById(R.id.image_edit);
            textTitle = (TextView)itemView.findViewById(R.id.text_title);
            textPrice = (TextView)itemView.findViewById(R.id.text_price);
            switchEdit = (Switch)itemView.findViewById(R.id.switch_edit);
        }
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

}