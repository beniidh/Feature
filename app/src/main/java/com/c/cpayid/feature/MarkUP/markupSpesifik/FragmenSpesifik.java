package com.c.cpayid.feature.MarkUP.markupSpesifik;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.c.cpayid.feature.Api.Api;
import com.c.cpayid.feature.BaseFragment;
import com.c.cpayid.feature.DaftarHarga.AdapterProdukDHList;
import com.c.cpayid.feature.DaftarHarga.ResponProdukList;
import com.c.cpayid.feature.Helper.RetroClient;
import com.c.cpayid.feature.R;
import com.c.cpayid.feature.engine.DrawableMap;
import com.c.cpayid.feature.sharePreference.Preference;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmenSpesifik extends BaseFragment implements ModalProdukDHS.BottomSheetListenerProduk, ModalSubProdukDHS.BottomSheetListenerProdukSub {

    EditText idprodukDaftarHarga,idProviderDaftarHarga;
    RecyclerView reyIDDaftarHarga;
    AdapterProdukDHList adapterProdukDHList;
    ArrayList<ResponProdukList.mData.Product> mData = new ArrayList<>();
    String rubah;
    String idDH;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_fragmen_spesifik, container, false);

        idprodukDaftarHarga = v.findViewById(R.id.idprodukDaftarHarga);
        idprodukDaftarHarga.setFocusable(false);
        idProviderDaftarHarga = v.findViewById(R.id.idProviderDaftarHarga);
        reyIDDaftarHarga = v.findViewById(R.id.idRecycleDaftarHarga);

        adapterProdukDHList = new AdapterProdukDHList(getContext(), mData);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        reyIDDaftarHarga.setLayoutManager(mLayoutManager);
        reyIDDaftarHarga.setAdapter(adapterProdukDHList);

        idprodukDaftarHarga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModalProdukDHS modalProdukDH = new ModalProdukDHS();
                modalProdukDH.show(getParentFragmentManager(),"produk");
            }
        });

        idProviderDaftarHarga.setOnClickListener(v1 -> {

            ModalSubProdukDHS modalSubProdukDH = new ModalSubProdukDHS();
            Bundle bundle = new Bundle();
            bundle.putString("ID",getIdDH());
            modalSubProdukDH.setArguments(bundle);
            modalSubProdukDH.show(getParentFragmentManager(),"produkDH");

        });


        return v;
    }

    @Override
    public void settingLayout(View view) {
        super.settingLayout(view);

        DrawableMap.changeColorStroke(idprodukDaftarHarga.getBackground(), "green");
        DrawableMap.changeColorStroke(idprodukDaftarHarga.getBackground(), "idProviderDaftarHarga");
    }

    public void getDaftarHarga(String id){
        String token = "Bearer " + Preference.getToken(getContext());
        Api api = RetroClient.getApiServices();
        Call<ResponProdukList> call = api.getProdukDHList(token,id);
        call.enqueue(new Callback<ResponProdukList>() {
            @Override
            public void onResponse(Call<ResponProdukList> call, Response<ResponProdukList> response) {
                String respon = response.body().getCode();
                if (respon.equals("200")){

                    mData = response.body().getData().getDataProduct();
                    adapterProdukDHList = new AdapterProdukDHList(getContext(),mData);
                    reyIDDaftarHarga.setAdapter(adapterProdukDHList);
                }
            }

            @Override
            public void onFailure(Call<ResponProdukList> call, Throwable t) {

            }
        });
    }




    @Override
    public void onButtonClick(String name, String id) {
        idprodukDaftarHarga.setText(name);
        setIdDH(id);

    }

    public String getIdDH() {
        return idDH;
    }

    public void setIdDH(String idDH) {
        this.idDH = idDH;
    }

    @Override
    public void onButtonClicksub(String name, String id) {
        idProviderDaftarHarga.setText(name);
        mData.clear();
        getDaftarHarga(id);

    }
}