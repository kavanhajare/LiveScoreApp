package com.example.karanc.admission;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.karanc.allied1.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
public class Branches extends Fragment {
    ListView lv;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.activity_branches,container,false);
        String[] branches= getResources().getStringArray(R.array.Branch_Names);



       int[] branch_img={R.raw.mechanical,R.raw.electrical,R.raw.civil,R.raw.ec,
               R.raw.computer,R.raw.it,R.raw.chemical,R.raw.metallurgy,
               R.raw.mechatronics,R.raw.automobile,R.raw.ic,R.raw.aeronautics,R.raw.environ,
               R.raw.production,R.raw.mining,R.raw.biomedical,R.raw.plastic,R.raw.rubber,R.raw.power,
       R.raw.industrial,R.raw.textile,R.raw.water,R.raw.manufacturing,R.raw.ictlogo,R.raw.agri,
       R.raw.biotech,R.raw.maarine,R.raw.nanotech};

//ArrayAdapter<String> adapter= new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,branches);
 lv= (ListView) v.findViewById(R.id.BranchListView);
        lv.setAdapter(new adapter(getActivity(),branches,branch_img));


lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(position==0){
        Intent intent = new Intent(getActivity(),Mechanical.class);
            startActivity(intent);
        }
        if(position==1){
            Intent intent = new Intent(getActivity(),Electrical.class);
            startActivity(intent);
        }
        if(position==2){
            Intent intent = new Intent(getActivity(),Civil.class);
            startActivity(intent);
        }
        if(position==3){
            Intent intent = new Intent(getActivity(),Ec.class);
            startActivity(intent);
        }
        if(position==4){
            Intent intent = new Intent(getActivity(),Computer.class);
            startActivity(intent);
        }
        if(position==5){
            Intent intent = new Intent(getActivity(),It.class);
            startActivity(intent);
        }
        if(position==6){
            Intent intent = new Intent(getActivity(),Chemical.class);
            startActivity(intent);
        }
        if(position==7){
            Intent intent = new Intent(getActivity(),Metallurgy.class);
            startActivity(intent);
        }
        if(position==8){
            Intent intent = new Intent(getActivity(),Mechatronics.class);
            startActivity(intent);
        }
        if(position==9){
            Intent intent = new Intent(getActivity(),Automobile.class);
            startActivity(intent);
        }
        if(position==10){
            Intent intent = new Intent(getActivity(),Ic.class);
            startActivity(intent);
        }
        if(position==11){
            Intent intent = new Intent(getActivity(),Aeronautics.class);
            startActivity(intent);
        }
        if(position==12){
            Intent intent = new Intent(getActivity(),Env.class);
            startActivity(intent);
        }
        if(position==13){
            Intent intent = new Intent(getActivity(),Production.class);
            startActivity(intent);
        }
        if(position==14){
            Intent intent = new Intent(getActivity(),Mining.class);
            startActivity(intent);
        }
        if(position==15){
            Intent intent = new Intent(getActivity(),Biomedical.class);
            startActivity(intent);
        }
        if(position==16){
            Intent intent = new Intent(getActivity(),Plastic.class);
            startActivity(intent);
        }
        if(position==17){
            Intent intent = new Intent(getActivity(),Rubber.class);
            startActivity(intent);
        }
        if(position==18){
            Intent intent = new Intent(getActivity(),Power.class);
            startActivity(intent);
        }
        if(position==19){
            Intent intent = new Intent(getActivity(),Industrial.class);
            startActivity(intent);
        }
        if(position==20){
            Intent intent = new Intent(getActivity(),Textile.class);
            startActivity(intent);
        }
        if(position==21){
            Intent intent = new Intent(getActivity(),Water.class);
            startActivity(intent);
        }
        if(position==22){
            Intent intent = new Intent(getActivity(),Manufacturing.class);
            startActivity(intent);
        }
        if(position==23){
            Intent intent = new Intent(getActivity(),ICT.class);
            startActivity(intent);
        }
        if(position==24){
            Intent intent = new Intent(getActivity(),Agriculture.class);
            startActivity(intent);
        }
        if(position==25){
            Intent intent = new Intent(getActivity(),BioTech.class);
            startActivity(intent);
        }
        if(position==26){
            Intent intent = new Intent(getActivity(),Marine.class);
            startActivity(intent);
        }
        if(position==27){
            Intent intent = new Intent(getActivity(),Nano.class);
            startActivity(intent);
        }

    }
});
        return v;
    }



}

class adapter extends ArrayAdapter{
int [] imgs;
    String [] Branch_Name;
    adapter(Context context,String []branches, int[] images){
        super(context,R.layout.customlayout_branches,R.id.branchlistv,branches);
        this.Branch_Name=branches;
        this.imgs=images;

    }


    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater= (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v=inflater.inflate(R.layout.customlayout_branches,parent,false);
        CircleImageView civ= (CircleImageView) v.findViewById(R.id.branchiv);
        TextView tv1= (TextView) v.findViewById(R.id.branchlistv);
        civ.setImageResource(imgs[position]);
        tv1.setText(Branch_Name[position]);

        return v;
    }


}