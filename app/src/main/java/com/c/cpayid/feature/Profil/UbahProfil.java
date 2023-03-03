package com.c.cpayid.feature.Profil;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.c.cpayid.feature.Api.Api;
import com.c.cpayid.feature.BaseActivity;
import com.c.cpayid.feature.Helper.RetroClient;
import com.c.cpayid.feature.Modal.ModalKabupaten;
import com.c.cpayid.feature.Modal.ModalKecamatan;
import com.c.cpayid.feature.Modal.ModalKelurahan;
import com.c.cpayid.feature.Modal.ModalKodePos;
import com.c.cpayid.feature.Modal.ModalProvinsi;
import com.c.cpayid.feature.Model.Responphoto;
import com.c.cpayid.feature.R;
import com.c.cpayid.feature.Respon.ResponEditKec;
import com.c.cpayid.feature.Respon.ResponEditLokasi;
import com.c.cpayid.feature.Respon.ResponEditPost;
import com.c.cpayid.feature.Respon.ResponEditkel;
import com.c.cpayid.feature.Respon.ResponKEditKab;
import com.c.cpayid.feature.Respon.ResponProfil;
import com.c.cpayid.feature.engine.ApplicationVariable;
import com.c.cpayid.feature.engine.ColorMap;
import com.c.cpayid.feature.engine.DrawableMap;
import com.c.cpayid.feature.sharePreference.Preference;
import com.github.dhaval2404.imagepicker.ImagePicker;
import io.github.muddz.styleabletoast.StyleableToast;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UbahProfil extends BaseActivity implements ModalProvinsi.BottomSheetListener, ModalKabupaten.BottomSheetListenerKabupaten, ModalKecamatan.BottomSheetListenerKecamatan, ModalKelurahan.BottomSheetListenerKelurahan, ModalKodePos.BottomSheetListenerPost{

    EditText editnama,editnamakonter,editalamat,editnomor,editemail,editprovinsi,editkecamatan,editkelurahan,editkabupaten,editkodepos;
    Button saveedit;
    ImageView UbahProfil,iconProfil;
    private Bitmap photo;
    int provinsiID,kabupatenID,kecamatanID,kelurahanID,kodePOSID;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_profil);

        String color = Integer.toHexString(ColorMap.getColor(ApplicationVariable.applicationId, "green")).toUpperCase();
        String color2 = "#" + color.substring(1);
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Ubah Profil <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(DrawableMap.changeColorVector(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24), "green"));

        editnama = findViewById(R.id.editnamaprofil);
        editnamakonter = findViewById(R.id.editnamakonter);
        editnomor = findViewById(R.id.editnomortelpon);
        editemail = findViewById(R.id.editemail);
        editalamat = findViewById(R.id.editalamat);
        editprovinsi = findViewById(R.id.editprovinsi);
        editkabupaten = findViewById(R.id.editkabupaten);
        editkecamatan = findViewById(R.id.editkecamatan);
        editkelurahan = findViewById(R.id.editkelurahan);
        editkodepos = findViewById(R.id.editkodepos);
        iconProfil =findViewById(R.id.iconprofile);

        UbahProfil = findViewById(R.id.UbahProfil);
        UbahProfil.setOnClickListener( view -> ImagePicker.with(com.c.cpayid.feature.Profil.UbahProfil.this)
                .crop()	    			//Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)//Final image resolution will be less than 1080 x 1080(Optional)
                .start());

        saveedit = findViewById(R.id.saveedit);
        getContentProfil();

        editprovinsi.setOnClickListener(view -> {
            ModalProvinsi modalProvinsi = new ModalProvinsi();
            modalProvinsi.show(getSupportFragmentManager(), "Modalprovinsi");
        });

        editkabupaten.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            ModalKabupaten modalKabupaten = new ModalKabupaten();
            bundle.putString("provinsikey", Preference.getIDProvinsi(getApplicationContext()));
            modalKabupaten.setArguments(bundle);
            modalKabupaten.show(getSupportFragmentManager(), "Modalkabupaten");
        });

        editkecamatan.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            ModalKecamatan modalKecamatan = new ModalKecamatan();
            bundle.putString("kabupatenkey", Preference.getIDKabupaten(getApplicationContext()));
            modalKecamatan.setArguments(bundle);
            modalKecamatan.show(getSupportFragmentManager(), "Modalkecamatan");
        });

        editkelurahan.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            ModalKelurahan modalKelurahan = new ModalKelurahan();
            bundle.putString("kecamatankey", Preference.getIDKecamatan(getApplicationContext()));
            modalKelurahan.setArguments(bundle);
            modalKelurahan.show(getSupportFragmentManager(), "Modalkelurahan");
        });

        editkodepos.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            ModalKodePos modalKodePos = new ModalKodePos();
            bundle.putString("kelurahankey", Preference.getIDKelurahan(getApplicationContext()));
            modalKodePos.setArguments(bundle);
            modalKodePos.show(getSupportFragmentManager(), "Modalkelurahan");
        });

        saveedit.setOnClickListener(view -> edit());
    }

    @Override
    public void settingLayout() {
        super.settingLayout();

        ((TextView) findViewById(R.id.tvName)).setTextColor(ColorMap.getColor(ApplicationVariable.applicationId, "green"));
        ((TextView) findViewById(R.id.tvKonterName)).setTextColor(ColorMap.getColor(ApplicationVariable.applicationId, "green"));
        ((TextView) findViewById(R.id.tvPhoneNumber)).setTextColor(ColorMap.getColor(ApplicationVariable.applicationId, "green"));
        ((TextView) findViewById(R.id.tvEmail)).setTextColor(ColorMap.getColor(ApplicationVariable.applicationId, "green"));
        ((TextView) findViewById(R.id.tvAddress)).setTextColor(ColorMap.getColor(ApplicationVariable.applicationId, "green"));

        DrawableMap.changeColor(saveedit.getBackground(), "green");
        DrawableMap.changeColorStroke(saveedit.getBackground(), "green");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ( resultCode == RESULT_OK) {
            assert data != null;
            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                photo = bitmap;
//                    uploadKTP.setImageBitmap(photo);
                uploadFile(photo);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadFile(Bitmap photo) {
        RequestBody type = RequestBody.create(MediaType.parse("text/plain"), "photo_profile");
        RequestBody primary_id = RequestBody.create(MediaType.parse("text/plain"), Preference.getKeyUserCode(getApplicationContext()));
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), getFileDataFromDrawable(photo));
        MultipartBody.Part body = MultipartBody.Part.createFormData("photo", "image.jpg", requestFile);

        //creating retrofit object
        Api api = RetroClient.getApiServices();
        Call<Responphoto> call = api.uploadProfil("Bearer " + Preference.getToken(getApplicationContext()),
                body, primary_id,type);
        call.enqueue(new Callback<Responphoto>() {
            @Override
            public void onResponse(@NonNull Call<Responphoto> call, @NonNull Response<Responphoto> response) {
                assert response.body() != null;
                String code = response.body().getCode();
                if (code.equals("200")) {
                    Toast.makeText(getApplicationContext(), "Berhasil", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), response.body().getError(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Responphoto> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), "Periksa Koneksi, yuk upload lagi", Toast.LENGTH_LONG).show();
            }
        });
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void getContentProfil() {
        Api api = RetroClient.getApiServices();
        Call<ResponProfil> call = api.getProfileDas("Bearer " + Preference.getToken(getApplicationContext()));
        call.enqueue(new Callback<ResponProfil>() {
            @Override
            public void onResponse(@NonNull Call<ResponProfil> call, @NonNull Response<ResponProfil> response) {
                assert response.body() != null;
                editnama.setText(response.body().getData().getName());
                editnomor.setText(response.body().getData().getPhone());
                editnamakonter.setText(response.body().getData().getStore_name());
                editalamat.setText(response.body().getData().getAddress());
                editemail.setText(response.body().getData().getEmail());
                Picasso.get().load(response.body().getData().getAvatar()).into(iconProfil);
//                getProvinsi(response.body().getData().getProvince_id());
//                setProvinsiID(Integer.parseInt(response.body().getData().getProvince_id()));
//                Preference.setIDProvinsi(getApplicationContext(),response.body().getData().getProvince_id());
//                getKabupaten(response.body().getData().getRegencies_id());
//                setKabupatenID(Integer.parseInt(response.body().getData().getRegencies_id()));
//                Preference.setIDKabupaten(getApplicationContext(),response.body().getData().getRegencies_id());
//                getKecamatan(response.body().getData().getDistricts_id());
//                Preference.setIDKecamatan(getApplicationContext(),response.body().getData().getDistricts_id());
//                getKelurahan(response.body().getData().getSub_districts_id());
//                Preference.setIDKelurahan(getApplicationContext(),response.body().getData().getSub_districts_id());
//                getPost(response.body().getData().getPostal_code_id());
//                Preference.setIDPost(getApplicationContext(),response.body().getData().getPostal_code_id());
            }

            @Override
            public void onFailure(@NonNull Call<ResponProfil> call, @NonNull Throwable t) {

            }
        });
    }

    public void edit(){
        String token = Preference.getToken(getApplicationContext());
        MProfilEdit edit = new MProfilEdit(editnama.getText().toString(),editnamakonter.getText().toString(),editalamat.getText().toString(),0,0,
                0,0,0);
        Api api = RetroClient.getApiServices();
        Call<ResEdit> call = api.editProfil("Bearer "+token,edit);
        call.enqueue(new Callback<ResEdit>() {
            @Override
            public void onResponse(@NonNull Call<ResEdit> call, @NonNull Response<ResEdit> response) {
                assert response.body() != null;
                String code = response.body().getCode();
                if (code.equals("200")) {
                    StyleableToast.makeText(getApplicationContext(),"Berhasil",Toast.LENGTH_SHORT,R.style.mytoast).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(),response.body().getError(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResEdit> call, @NonNull Throwable t) {

            }
        });
    }

    public void getProvinsi(String id){
        long idp = Long.parseLong(id);
        Api api = RetroClient.getApiServices();
        Call<ResponEditLokasi> call = api.getProvinsiByIdd(idp);
        call.enqueue(new Callback<ResponEditLokasi>() {
            @Override
            public void onResponse(@NonNull Call<ResponEditLokasi> call, @NonNull Response<ResponEditLokasi> response) {
                assert response.body() != null;
                editprovinsi.setText(response.body().getData().getName());
            }

            @Override
            public void onFailure(@NonNull Call<ResponEditLokasi> call, @NonNull Throwable t) {
                StyleableToast.makeText(getApplicationContext(), "Periksa Sambungan internet", Toast.LENGTH_SHORT, R.style.mytoast2).show();
            }
        });

    }

    public void getKabupaten(String id){
        long idp = Long.parseLong(id);

        Api api = RetroClient.getApiServices();
        Call<ResponKEditKab> call = api.getKabupatenById(idp);
        call.enqueue(new Callback<ResponKEditKab>() {
            @Override
            public void onResponse(@NonNull Call<ResponKEditKab> call, @NonNull Response<ResponKEditKab> response) {
                assert response.body() != null;
                editkabupaten.setText(response.body().getData().getName());
            }

            @Override
            public void onFailure(@NonNull Call<ResponKEditKab> call, @NonNull Throwable t) {
                StyleableToast.makeText(getApplicationContext(), "Periksa Sambungan internet", Toast.LENGTH_SHORT, R.style.mytoast2).show();
            }
        });

    }

    public void getKecamatan(String id){
        long idp = Long.parseLong(id);
        setKecamatanID(Integer.parseInt(id));
        Api api = RetroClient.getApiServices();
        Call<ResponEditKec> call = api.getKecamatanById(idp);
        call.enqueue(new Callback<ResponEditKec>() {
            @Override
            public void onResponse(@NonNull Call<ResponEditKec> call, @NonNull Response<ResponEditKec> response) {
                assert response.body() != null;
                editkecamatan.setText(response.body().getData().getName());
            }

            @Override
            public void onFailure(@NonNull Call<ResponEditKec> call, @NonNull Throwable t) {
                StyleableToast.makeText(getApplicationContext(), "Periksa Sambungan internet", Toast.LENGTH_SHORT, R.style.mytoast2).show();
            }
        });
    }

    public void getKelurahan(String id){
        setKelurahanID(Integer.parseInt(id));
        long idp = Long.parseLong(id);
        Api api = RetroClient.getApiServices();
        Call<ResponEditkel> call = api.getKelurahanById(idp);
        call.enqueue(new Callback<ResponEditkel>() {
            @Override
            public void onResponse(@NonNull Call<ResponEditkel> call, @NonNull Response<ResponEditkel> response) {
                assert response.body() != null;
                editkelurahan.setText(response.body().getData().getName());
            }

            @Override
            public void onFailure(@NonNull Call<ResponEditkel> call, @NonNull Throwable t) {
                StyleableToast.makeText(getApplicationContext(), "Periksa Sambungan internet", Toast.LENGTH_SHORT, R.style.mytoast2).show();
            }
        });
    }

    public void getPost(String id){
        setKodePOSID(Integer.parseInt(id));
        long idp = Long.parseLong(id);
        Api api = RetroClient.getApiServices();
        Call<ResponEditPost> call = api.getPostById(idp);
        call.enqueue(new Callback<ResponEditPost>() {
            @Override
            public void onResponse(@NonNull Call<ResponEditPost> call, @NonNull Response<ResponEditPost> response) {
                assert response.body() != null;
                editkodepos.setText(response.body().getData().getPostal_code());
            }

            @Override
            public void onFailure(@NonNull Call<ResponEditPost> call, @NonNull Throwable t) {
                StyleableToast.makeText(getApplicationContext(), "Periksa Sambungan internet", Toast.LENGTH_SHORT, R.style.mytoast2).show();
            }
        });
    }

    @Override
    public void onButtonClick(String name, String id) {
        editprovinsi.setText(name);
      Preference.setIDProvinsi(getApplicationContext(),id);
    }

    @Override
    public void onButtonClickKabupaten(String name, String id) {
        editkabupaten.setText(name);
        Preference.setIDKabupaten(getApplicationContext(),id);
    }

    @Override
    public void onButtonClickKecamatan(String name, String id) {
        editkecamatan.setText(name);
        Preference.setIDKecamatan(getApplicationContext(),id);
    }

    @Override
    public void onButtonClickKelurahan(String name, String id) {
        editkelurahan.setText(name);
        Preference.setIDKelurahan(getApplicationContext(),id);
    }

    @Override
    public void onButtonClickPost(String postalcode, String id) {
        editkodepos.setText(postalcode);
        Preference.setIDPost(getApplicationContext(),id);
    }

    public int getProvinsiID() {
        return provinsiID;
    }

    public void setProvinsiID(int provinsiID) {
        this.provinsiID = provinsiID;
    }

    public int getKabupatenID() {
        return kabupatenID;
    }

    public void setKabupatenID(int kabupatenID) {
        this.kabupatenID = kabupatenID;
    }

    public int getKecamatanID() {
        return kecamatanID;
    }

    public void setKecamatanID(int kecamatanID) {
        this.kecamatanID = kecamatanID;
    }

    public int getKelurahanID() {
        return kelurahanID;
    }

    public void setKelurahanID(int kelurahanID) {
        this.kelurahanID = kelurahanID;
    }

    public int getKodePOSID() {
        return kodePOSID;
    }

    public void setKodePOSID(int kodePOSID) {
        this.kodePOSID = kodePOSID;
    }
}