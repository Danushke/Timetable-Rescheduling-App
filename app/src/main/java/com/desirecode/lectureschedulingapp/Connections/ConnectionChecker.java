package com.desirecode.lectureschedulingapp.Connections;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionChecker {
    public static boolean checkConnectivity(Context context){
        ConnectivityManager connectivityManager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        //boolean networkInfo2=connectivityManager.getActiveNetworkInfo().isAvailable();
        //boolean networkInfo3=connectivityManager.getActiveNetworkInfo().isConnected();
        if (networkInfo!=null&&connectivityManager.getActiveNetworkInfo().isAvailable()&&connectivityManager.getActiveNetworkInfo().isConnected()){
            if (networkInfo.getType()==ConnectivityManager.TYPE_WIFI){
                return true;
            }else if (networkInfo.getType()==ConnectivityManager.TYPE_MOBILE){
                return true;
            }
        }else {
            return false;
        }
        return true;
    }
}
