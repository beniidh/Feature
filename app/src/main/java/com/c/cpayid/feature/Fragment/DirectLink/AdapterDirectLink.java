package com.c.cpayid.feature.Fragment.DirectLink;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.c.cpayid.feature.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterDirectLink extends RecyclerView.Adapter<AdapterDirectLink.ViewHolder> {

    Context context;
    ArrayList<mDirect.Data> menuDirect;

    public AdapterDirectLink(Context context, ArrayList<mDirect.Data> menuDirect) {
        this.context = context;
        this.menuDirect = menuDirect;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listmenu_utama, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        mDirect.Data data = menuDirect.get(position);
        holder.titlemenu.setText(data.getName());
        String path = data.getIcon();

        if(!path.isEmpty()){
            Picasso.get().load(data.icon).into(holder.iconmenu);
        }

        holder.linlistmenu.setOnClickListener(v -> {

            Intent intent = new Intent(context, WebDirectActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("url",data.getUrl());
            context.startActivity(intent);

        });

    }

    @Override
    public int getItemCount() {
        return menuDirect.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iconmenu;
        TextView titlemenu;
        LinearLayout linlistmenu;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iconmenu = itemView.findViewById(R.id.iconmenuutama);
            titlemenu = itemView.findViewById(R.id.titlemenuutama);
            linlistmenu = itemView.findViewById(R.id.Linlistmenu);
        }
    }

}
