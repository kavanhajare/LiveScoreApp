package vs2.navjivanclient.adapters;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import vs2.navjivanclient.FoodItemActivity;
import vs2.navjivanclient.R;
import vs2.navjivanclient.databases.DatabaseFunctions;
import vs2.navjivanclient.models.FoodCart;
import vs2.navjivanclient.models.FoodItem;
import vs2.navjivanclient.utils.Logcat;
import vs2.navjivanclient.views.RoundedTransformation;


public class FoodItemAdapter extends RecyclerView.Adapter<FoodItemAdapter.ViewHolder> {

    ArrayList<FoodItem> mItems;

    private  Context mContext;

    // Allows to remember the last item shown on screen
    private int lastPosition = -1;

    public FoodItemAdapter(ArrayList<FoodItem> foodItems, Context context) {
        super();
        this.mItems = foodItems;
        this.mContext= context;
        DatabaseFunctions.openDB(context);

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
        final FoodItem foodItem = mItems.get(i);

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
            Logcat.e("ShowImage", "Error : " + e.toString());
        }


        viewHolder.textCount.setText(""+ DatabaseFunctions.getQuantity(foodItem.getMenuId()));

        viewHolder.buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = 0;
                try {
                    count = Integer.parseInt(viewHolder.textCount.getText().toString().trim());
                    count = count +1;
                }catch (Exception e){
                    Logcat.e("Add Count " ,"Error : " + e.toString());
                }

                FoodCart cart = new FoodCart(foodItem,count);

                DatabaseFunctions.addToCart(cart);


                viewHolder.textCount.setText(""+ DatabaseFunctions.getQuantity(foodItem.getMenuId()));

                if(FoodItemActivity.textBadge != null){
                    FoodItemActivity.textBadge.setText(""+ DatabaseFunctions.getcartItemCount());
                }

            }
        });

        viewHolder.buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = 0;
                try {
                    count = Integer.parseInt(viewHolder.textCount.getText().toString().trim());
                    if (count > 0){
                        count = count -1;

                        FoodCart cart = new FoodCart(foodItem,count);
                        if (count == 0){
                            DatabaseFunctions.deleteMenu(foodItem.getMenuId());
                        }else {
                            DatabaseFunctions.addToCart(cart);
                        }

                        viewHolder.textCount.setText(""+ DatabaseFunctions.getQuantity(foodItem.getMenuId()));

                        if(FoodItemActivity.textBadge != null){
                            FoodItemActivity.textBadge.setText(""+ DatabaseFunctions.getcartItemCount());
                        }

                    }

                }catch (Exception e){
                    Logcat.e("Add Count " ,"Error : " + e.toString());
                }


            }
        });

        // Here you apply the animation when the view is bound
        setAnimation(viewHolder.cardView, i);

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

    @Override
    public int getItemCount() {

        return mItems.size();

    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public CardView cardView;
        public ImageView imageThumb;
        public TextView textTitle,textPrice,textCount;
        public FloatingActionButton buttonAdd,buttonRemove;


        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView)itemView.findViewById(R.id.cardview);
            imageThumb = (ImageView)itemView.findViewById(R.id.image_thumb);
            textTitle = (TextView)itemView.findViewById(R.id.text_title);
            textPrice = (TextView)itemView.findViewById(R.id.text_price);

            //Cart addition
            textCount = (TextView)itemView.findViewById(R.id.text_count);
            buttonAdd = (FloatingActionButton)itemView.findViewById(R.id.button_add);
            buttonRemove = (FloatingActionButton) itemView.findViewById(R.id.button_remove);

        }
    }


}