package com.c.cpayid.feature.Fragment.RekapsaldoFragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.c.cpayid.feature.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class modalFilter extends BottomSheetDialogFragment {

    EditText tanggalstartR, tanggalendR;
    Button ButtonfilterR;
    Spinner spinner;
    public BottomSheetListenerFilter bottomSheetListenerFilter;

    final Calendar myCalendar = Calendar.getInstance();
    final Calendar myCalendar3 = Calendar.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.modal_filter_rekap, container, false);

        tanggalstartR = v.findViewById(R.id.tanggalstartR);
        tanggalendR =  v.findViewById(R.id.tanggalendR);
        ButtonfilterR =  v.findViewById(R.id.ButtonfilterR);
        spinner = v.findViewById(R.id.pilihType);



        ButtonfilterR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle result = new Bundle();
                result.putString("bundleKey", "result");
                result.putString("Tanggalmulai", tanggalstartR.getText().toString());
                result.putString("Tanggalselesai", tanggalendR.getText().toString());
                result.putString("jenissaldo", spinner.getSelectedItem().toString());
                // The child fragment needs to still set the result on its parent fragment manager
                getParentFragmentManager().setFragmentResult("requestKey", result);
                dismiss();

            }
        });

        DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        };
        DatePickerDialog.OnDateSetListener date2 = (view, year, monthOfYear, dayOfMonth) -> {
            // TODO Auto-generated method stub
            myCalendar3.set(Calendar.YEAR, year);
            myCalendar3.set(Calendar.MONTH, monthOfYear);
            myCalendar3.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel2();
        };

        tanggalstartR.setOnClickListener(view -> {

            final Calendar myCalendar2 = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.getDatePicker().setMaxDate(myCalendar2.getTimeInMillis());
            datePickerDialog.show();

        });


        tanggalendR.setOnClickListener(view -> {

            final Calendar myCalendar2 = Calendar.getInstance();
            DatePickerDialog datePickerDialog2 = new DatePickerDialog(getContext(), date2, myCalendar3
                    .get(Calendar.YEAR), myCalendar3.get(Calendar.MONTH),
                    myCalendar3.get(Calendar.DAY_OF_MONTH));
            datePickerDialog2.getDatePicker().setMaxDate(myCalendar2.getTimeInMillis());
            datePickerDialog2.show();


        });




        return v;
    }

    public interface BottomSheetListenerFilter {
        void onButtonfilter(String name, String id);
    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        tanggalstartR.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateLabel2() {
        String myFormat2 = "yyyy-MM-dd"; //In which you need put here
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf2 = new SimpleDateFormat(myFormat2);
        tanggalendR.setText(sdf2.format(myCalendar3.getTime()));
    }




}
