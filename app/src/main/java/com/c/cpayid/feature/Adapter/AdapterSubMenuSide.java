package com.c.cpayid.feature.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.c.cpayid.feature.CetakStruk.CetakStruk;
import com.c.cpayid.feature.DaftarHarga.DaftarHargaActivity;
import com.c.cpayid.feature.DrawerActivity;
import com.c.cpayid.feature.Fragment.TransaksiFragment;
import com.c.cpayid.feature.KodeProduk.KodeProdukAct;
import com.c.cpayid.feature.KomisiSales.KomisiSales;
import com.c.cpayid.feature.MarkUP.markupSpesifik.MarkUpSpesifikActi;
import com.c.cpayid.feature.Model.MSubMenu;
import com.c.cpayid.feature.Notifikasi.NotifikasiActivity;
import com.c.cpayid.feature.PengajuanLimit.PengajuanDompet;
import com.c.cpayid.feature.PersetujuanSaldoSales.PersetujuanSaldoSales;
import com.c.cpayid.feature.Profil.Poin.Point;
import com.c.cpayid.feature.R;
import com.c.cpayid.feature.TagihanKonter.TagihanKonter;
import com.c.cpayid.feature.TagihanKonterSales.TagihanKonterBySales;
import com.c.cpayid.feature.TambahKonter.AddKonter;
import com.c.cpayid.feature.TarikKomisi.TarikKomisiSalesActivity;
import com.c.cpayid.feature.konter.KonterActivity;
import com.c.cpayid.feature.reseller.PersetujuanSaldokuReseller;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterSubMenuSide extends RecyclerView.Adapter<AdapterSubMenuSide.ViewHolder> {

    Context context;
    private List<MSubMenu> subMenus;
    DrawerActivity drawer_activity;

    public AdapterSubMenuSide(Context context, ArrayList<MSubMenu> subMenus, DrawerActivity drawer_activity) {
        this.context = context;
        this.subMenus = subMenus;
        this.drawer_activity = drawer_activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.listmenu_sidebar, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MSubMenu mSubMenu = subMenus.get(position);
        holder.namesub.setText(mSubMenu.getName().trim());

        if (mSubMenu.getIcon().equals("")) {
            Picasso.get().load("http").into(holder.iconsub);
        } else {
            Picasso.get().load(mSubMenu.getIcon()).into(holder.iconsub);
        }

        holder.linside.setOnClickListener(v -> {
            switch (mSubMenu.getLink()) {
                case "daftar_harga": {
                    Intent intent = new Intent(context, DaftarHargaActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    drawer_activity.LinDaftarHarga();
                    break;
                }
                case "pengajuan_dompet": {
                    Intent intent = new Intent(context, PengajuanDompet.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    drawer_activity.LinDaftarHarga();
                    break;
                }
                case "poin_reward": {

                    Intent intent = new Intent(context, Point.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    drawer_activity.LinDaftarHarga();
                    break;
                }
                case "notifikasi": {
                    Intent intent = new Intent(context, NotifikasiActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    drawer_activity.LinDaftarHarga();
                    break;
                }
                case "cetak_struct": {
                    Intent intent = new Intent(context, CetakStruk.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    drawer_activity.LinDaftarHarga();
                    break;
                }
                case "kode_semua_produk": {
                    Intent intent = new Intent(context, KodeProdukAct.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    drawer_activity.LinDaftarHarga();

                    break;
                }
                case "persetujuan_saldo_server": {
                    Intent intent = new Intent(context, PersetujuanSaldoSales.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    drawer_activity.LinDaftarHarga();

                    break;
                }
                case "konter_tambah": {
                    Intent intent = new Intent(context, AddKonter.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    drawer_activity.LinDaftarHarga();
                    break;
                }
                case "riwayat_transaksi": {
                    TransaksiFragment fragment1 = new TransaksiFragment();
                    FragmentManager fm3 = drawer_activity.getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction2 = fm3.beginTransaction();
                    fragmentTransaction2.replace(R.id.fLayout, fragment1);
                    fragmentTransaction2.commit(); // save the change
                    drawer_activity.LinDaftarHarga();
                    break;
                }
                case "mark_up": {
                    Intent intent = new Intent(context, MarkUpSpesifikActi.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    drawer_activity.LinDaftarHarga();
                    break;
                }
                case "konter_tagihan_pembayaran": {
                    Intent intent = new Intent(context, TagihanKonter.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    drawer_activity.LinDaftarHarga();
                    break;
                }
                case "konter": {
                    Intent intent = new Intent(context, KonterActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    drawer_activity.LinDaftarHarga();
                    break;
                }
                case "riwayat_komisi": {
                    Intent intent = new Intent(context, KomisiSales.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    drawer_activity.LinDaftarHarga();
                    break;
                }
                case "konter_tagihan": {
                    Intent intent = new Intent(context, TagihanKonterBySales.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    drawer_activity.LinDaftarHarga();
                    break;
                }
                case "persetujuan_saldoku_reseller": {
                    Intent intent = new Intent(context, PersetujuanSaldokuReseller.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    drawer_activity.LinDaftarHarga();
                    break;
                }
                case "tarik_komisi": {
                    Intent intent = new Intent(context, TarikKomisiSalesActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    drawer_activity.LinDaftarHarga();
                    break;
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return subMenus.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView namesub;
        ImageView iconsub;
        LinearLayout linside;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            namesub = itemView.findViewById(R.id.name_list_sub);
            iconsub = itemView.findViewById(R.id.icon_list_sub);
            linside = itemView.findViewById(R.id.Linierside);
        }
    }
}


