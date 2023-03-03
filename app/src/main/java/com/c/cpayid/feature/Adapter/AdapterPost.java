package com.c.cpayid.feature.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.c.cpayid.feature.Modal.ModalKodePos;
import com.c.cpayid.feature.Model.ModelPost;
import com.c.cpayid.feature.R;
import com.c.cpayid.feature.sharePreference.Preference;

import java.util.ArrayList;
import java.util.List;

public class AdapterPost extends RecyclerView.Adapter<AdapterPost.ViewHolder>  {

    private Context context;
    private List<ModelPost> modelPosts;
    private int selectedPosition = 0;
    ModalKodePos modalKodePos;

    private ArrayList<Integer> selectCheck = new ArrayList<>();
    public AdapterPost(ModalKodePos modalKodePos, Context context, List<ModelPost> modelPosts) {
        this.context = context;
        this.modelPosts = modelPosts;
        this.modalKodePos = modalKodePos;

        for (int i = 0; i < modelPosts.size(); i++) {
            selectCheck.add(0);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {


        ModelPost modelPost = modelPosts.get(position);
        holder.name.setText(modelPost.getPostal_code());

        if (selectCheck.get(position) == 1) {
            holder.chekP.setChecked(true);
        } else {
            holder.chekP.setChecked(false);
        }


        holder.klik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Preference.getSharedPreference(context);
                Preference.setName(context,modelPost.getPostal_code());
                Preference.setID(context,modelPost.getId());

                String id = Preference.getID(v.getContext());
                String name = Preference.getName(v.getContext());

                modalKodePos.bottomSheetListenerPost.onButtonClickPost(name, id);
                modalKodePos.dismiss();


            }

        });



    }

    @Override
    public int getItemCount() {
        return modelPosts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        CheckBox chekP;
        LinearLayout klik;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameList);
            chekP = itemView.findViewById(R.id.chekProvinsi);
            klik = itemView.findViewById(R.id.linearKlikk);

        }
    }


}
