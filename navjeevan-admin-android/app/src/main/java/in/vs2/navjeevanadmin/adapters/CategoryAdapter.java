package in.vs2.navjeevanadmin.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import in.vs2.navjeevanadmin.FoodItemActivity;
import in.vs2.navjeevanadmin.R;
import in.vs2.navjeevanadmin.models.Category;
import in.vs2.navjeevanadmin.utils.Logcat;
import in.vs2.navjeevanadmin.views.RoundedTransformation;

public class  CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    ArrayList<Category> mCategories;

    ArrayList<String> colors;

    private  Context mContext;

    // Allows to remember the last item shown on screen
    private int lastPosition = -1;

    private CategoryListener mListener;

    public interface CategoryListener{
        public void onEdit(int position);
    }

    public CategoryAdapter(ArrayList<Category> categories,Context context,CategoryListener listener) {
        super();
        this.mCategories = categories;
        this.mContext= context;
        this.mListener = listener;

    }

    private void initColors(){
        colors = new ArrayList<String>();
        colors.add("#F44336");
        colors.add("#E91E63");
        colors.add("#009688");
        colors.add("#4CAF50");
        colors.add("#3F51B5");
        colors.add("#03A9F4");
        colors.add("#795548");
        colors.add("#00BCD4");
        colors.add("#67BA37");
        colors.add("#607D8B");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_category, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder,final int i) {
        //EndangeredItem nature = mItems.get(i);


        /*
        Random rand = new Random();
        int  n = rand.nextInt(10) + 1;
        n = n-1;

        viewHolder.cardView.setCardBackgroundColor(Color.parseColor(colors.get(n)));

        */

        viewHolder.textTitle.setText("" + mCategories.get(i).getCategory());

        try {
            Picasso.with(mContext)
                    .load(mCategories.get(i).getImageUrl())
                    .placeholder(R.drawable.default_item)
                    .transform(new RoundedTransformation(4, 0))
                    .fit()
                    .into(viewHolder.imageThumb);
        }catch (Exception e){
            Logcat.e("ShowImage","Error : " + e.toString());
        }


        viewHolder.imageEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null){
                    mListener.onEdit(i);
                }
            }
        });


        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Category category = mCategories.get(i);
                Intent intent = new Intent(mContext, FoodItemActivity.class);
                intent.putExtra("Category",category);
                mContext.startActivity(intent);
            }
        });

        // Here you apply the animation when the view is bound
        setAnimation(viewHolder.cardView, i);
    }

    @Override
    public int getItemCount() {

        return mCategories.size();

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
        public ImageView imageThumb,imageEdit;
        public TextView textTitle;


        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView)itemView.findViewById(R.id.cardview);
            imageThumb = (ImageView)itemView.findViewById(R.id.image_thumb);
            imageEdit = (ImageView)itemView.findViewById(R.id.image_edit);
            textTitle = (TextView)itemView.findViewById(R.id.text_title);
        }
    }


}