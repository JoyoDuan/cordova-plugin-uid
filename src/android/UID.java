package com.joyo.cordova.uid;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * 获取设备唯一识别码
 *
 * Created By JoyoDuan On 2020-09-17
 */
public class UID extends CordovaPlugin {
    Context context = null;

    public static String uuid; // Device UUID
    public static String imei; // Device IMEI
    public static String imsi; // Device IMSI
    public static String iccid; // Sim IMSI
    public static String mac; // MAC address

    public UID() {

    }


    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);

        // 上下文
        context = cordova.getActivity().getApplicationContext();
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        UID.imei = getIMEI(context);
        UID.uuid = getUUID(context);
        UID.imsi = getIMSI(context);
        UID.iccid = getICCID(context);
        UID.mac = getMAC(context);

        if (action.equals("getUID")) {
            JSONObject result = new JSONObject();
            result.put("UUID", UID.uuid);
            result.put("IMEI", UID.imei);
            result.put("IMSI", UID.imsi);
            result.put("ICCID", UID.iccid);
            result.put("MAC", UID.mac);

            // 返回结果给Cordova
            callbackContext.success(result);
            return true;
        }
        return false;
    }


    /**
     * 获取设备UUID
     *
     * @return uuid
     */
    public String getUUID(Context context) {
        String uuid = Settings.System.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return uuid;
    }


    /**
     * 获取设备IMEI，Android Q获取不到，使用UUID存储磁盘代替
     *
     * @return IMEI
     */
    public String getIMEI(Context context) {
        return UIDUtils.getDeviceId(context);
    }


    public String getIMSI(Context context) {
        final TelephonyManager mTelephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (mTelephony == null) {
            return null;
        }

        @SuppressLint({"MissingPermission", "HardwareIds"})
        String imsi = mTelephony.getSubscriberId();
        return imsi;
    }


    public String getICCID(Context context) {
        final TelephonyManager mTelephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (mTelephony == null) {
            return null;
        }

        @SuppressLint({"MissingPermission", "HardwareIds"})
        String iccid = mTelephony.getSimSerialNumber();
        return iccid;
    }


    public String getMAC(Context context) {
        final WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifiManager == null) {
            return null;
        }
        @SuppressLint("MissingPermission")
        final WifiInfo wInfo = wifiManager.getConnectionInfo();

        @SuppressLint("HardwareIds")
        String mac = wInfo.getMacAddress();
        return mac;
    }
}
