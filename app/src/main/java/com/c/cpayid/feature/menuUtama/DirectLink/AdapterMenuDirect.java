package com.c.cpayid.feature.menuUtama.DirectLink;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

public class AdapterMenuDirect extends RecyclerView.Adapter<AdapterMenuDirect.ViewHolder> {

    Context context;
    ArrayList<mDirectL.mData> menuUtamas;

    public AdapterMenuDirect(Context context, ArrayList<mDirectL.mData> menuUtamas) {
        this.context = context;
        this.menuUtamas = menuUtamas;
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

        mDirectL.mData mdirect = menuUtamas.get(position);
        Picasso.get().load(mdirect.getIcon()).into(holder.iconmenu);
        holder.titlemenu.setText(mdirect.getName());

        holder.linlistmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = mdirect.getUrl();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setPackage("com.android.chrome");
                try {
                    context.startActivity(intent);
                } catch (ActivityNotFoundException ex) {
                    // Chrome browser presumably not installed so allow user to choose instead

                }

            }
        });

    }


    @Override
    public int getItemCount() {
        return menuUtamas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
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
