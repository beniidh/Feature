package com.c.cpayid.feature.Helper;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.c.cpayid.feature.R;

public class Maintenance {

// create adapter template

    //view Holder
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        CheckBox chekP;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameList);
            chekP = itemView.findViewById(R.id.chekProvinsi);

        }
    }

    //adapter oncreate view
//    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list, parent, false);
//    AdapterKabupaten.ViewHolder holder = new AdapterKabupaten.ViewHolder(v);
//        return holder;

}
