package com.gloobe.survey;

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
        if (netInfo != null && netInfo.getType() == ConnectivityManager.TYPE_WIFI)
            Actividad_Principal.llconexion.setVisibility(View.GONE);
        else
            Actividad_Principal.llconexion.setVisibility(View.VISIBLE);

    }

}
