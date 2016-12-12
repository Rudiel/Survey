package com.gloobe.survey.Utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;

import com.gloobe.survey.Actividades.Actividad_Principal;

/**
 * Created by rudielavilaperaza on 08/08/16.
 */
public class BroadcastWifiStatus extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMan.getActiveNetworkInfo();
        if (netInfo != null && netInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            try {
                Actividad_Principal.llconexion.setVisibility(View.GONE);
                Actividad_Principal.eliminarEncuesta(context);

            } catch (Exception e) {
            }
            Actividad_Principal.wifiActive = true;
        } else {
            try {
                Actividad_Principal.llconexion.setVisibility(View.VISIBLE);
            } catch (Exception e) {

            }
            Actividad_Principal.wifiActive = false;
        }

    }
}
