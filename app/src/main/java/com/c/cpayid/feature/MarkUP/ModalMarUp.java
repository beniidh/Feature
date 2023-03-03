package com.c.cpayid.feature.MarkUP;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.c.cpayid.feature.R;
import com.c.cpayid.feature.sharePreference.Preference;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

public class ModalMarUp extends BottomSheetDialogFragment {


    RecyclerView recyclerViewK;
    AdapterMarkUp adapterMarkUp;
    ArrayList<mMarkUp> markUps = new ArrayList<>();
    Button tutup, pilih;
    private BottomSheetListenerKabupaten bottomSheetListenerKabupaten;
    SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.modal_layout_markup, container, false);

        recyclerViewK = v.findViewById(R.id.ReyMarkup);
        tutup = v.findViewById(R.id.tutupMarkup);
        pilih = v.findViewById(R.id.pilihMarkup);
        searchView = v.findViewById(R.id.search_markup);

        markUps.add(new mMarkUp("1","Global"));
        markUps.add(new mMarkUp("2","Spesifik"));


        adapterMarkUp = new AdapterMarkUp(getContext(), markUps);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewK.setLayoutManager(mLayoutManager);
        recyclerViewK.setAdapter(adapterMarkUp);

//        getKabupaten();

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.onActionViewExpanded();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapterMarkUp.getFilter().filter(newText);
                return false;
            }
        });

        tutup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        pilih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String nameid[][] = AdapterMarkUp.getNameid();

                String name = nameid[0][0];
                String id = nameid[0][1];

                bottomSheetListenerKabupaten.onButtonClickKabupaten(name, id);
                Preference.setName(getContext(),"");
                dismiss();
            }
        });

        return v;
    }



    public interface BottomSheetListenerKabupaten {
        void onButtonClickKabupaten(String name, String id);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            bottomSheetListenerKabupaten = (BottomSheetListenerKabupaten) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement bottomsheet Listener");
        }
    }

}
