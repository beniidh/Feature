package com.c.cpayid.feature.Notifikasi;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.c.cpayid.feature.Notifikasi.Pesan.DetailPesanActivity;
import com.c.cpayid.feature.R;
import com.c.cpayid.feature.menuUtama.PulsaPrabayar.TransaksiPending;
import com.c.cpayid.feature.sharePreference.Preference;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyService extends FirebaseMessagingService {

    LocalBroadcastManager localBroadcastManager;

    @Override
    public void onCreate() {
        super.onCreate();
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
    }

    public void createNotification(String judul, String isi, Context context, Intent intent, String transaksi, String id, String applicationId) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        Intent resultIntent;
        if (transaksi.equals("NOTIF")) {
            resultIntent = new Intent(this, DetailPesanActivity.class);
            resultIntent.putExtra("id", id);
            resultIntent.putExtra("applicationId", applicationId);
        } else {
            resultIntent = new Intent(this, TransaksiPending.class);
            resultIntent.putExtra("transaksid", transaksi);
            resultIntent.putExtra("applicationId", applicationId);
        }

        PendingIntent PI = PendingIntent.getActivity(this, 1, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        int NotificationID = 0;
        String ChanelID = "Abata";
        String ChanelName = "AbataChannel";
        int Impoortance = NotificationManager.IMPORTANCE_HIGH;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(ChanelID, ChanelName, Impoortance);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder MBuilder = new NotificationCompat.Builder(context, ChanelID)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.icon_notification)
                .setContentTitle(judul)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(isi))
                .setPriority(Notification.PRIORITY_MAX)
                .setContentIntent(PI)
                .setContentText(isi);

        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(context);
        taskStackBuilder.addNextIntent(intent);
        notificationManager.notify(NotificationID++, MBuilder.build());

        Intent refresh = new Intent("refresh");
        sendBroadcast(refresh);
    }

    @Override
    public void handleIntent(@NonNull Intent intent) {
        Bundle bundle = intent.getExtras();

        String judul = "";
        String isi = "";
        String trx = "";
        String url = "";
        String applicationId = "";

        assert bundle != null;
        for (String key : bundle.keySet()) {
            System.out.println(key + " -> " + bundle.get(key));
            switch (key) {
                case "gcm.notification.title":
                    judul = bundle.getString(key);
                    break;
                case "gcm.notification.body":
                    isi = bundle.getString(key);
                    break;
                case "gcm.notification.tag":
                    trx = bundle.getString(key);
                    break;
                case "id":
                    url = bundle.getString(key);
                    break;
                case "applicationId":
                    applicationId = bundle.getString(key);
                    break;
            }
        }

        Log.v("iddanpesan", trx + url);

//        localBroadcastManager.sendBroadcast( new Intent("kirim"));
        Preference.setNilaiNotif(getApplicationContext(), 1 + Preference.getNilaiNotif(getApplicationContext()));
        createNotification(judul, isi, getApplicationContext(), new Intent(), trx, url, applicationId);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
//        String judul = remoteMessage.getNotification().getTitle();
//        String isi = remoteMessage.getNotification().getBody();
//        String trx = remoteMessage.getNotification().getTag();
//        String url = remoteMessage.getData().get("id");
//        String applicationId = remoteMessage.getData().get("applicationId");
//
//        for (Map.Entry<String, String> data : remoteMessage.getData().entrySet()) {
//            System.out.println(data.getKey() + " -> " + data.getValue());
//        }
//
//        Log.v("iddanpesan", trx + url);
//
////        localBroadcastManager.sendBroadcast( new Intent("kirim"));
//        Preference.setNilaiNotif(getApplicationContext(), 1 + Preference.getNilaiNotif(getApplicationContext()));
//        createNotification(judul, isi, getApplicationContext(), new Intent(), trx, url, applicationId);
//        "NOTIF"

    }
}
