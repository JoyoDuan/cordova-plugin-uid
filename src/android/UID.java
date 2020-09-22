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

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        // 上下文
        context = cordova.getActivity().getApplicationContext();

        super.initialize(cordova, webView);
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("getUID")) {
            JSONObject result = new JSONObject();
            result.put("UID", UIDUtils.getUID());
//            result.put("IMEI", getIMEI(context));
            // Android10已经不允许获取硬件唯一标识
//            result.put("UUID", getUUID(context));
//            result.put("IMSI", getIMSI(context));
//            result.put("ICCID", getICCID(context));
//            result.put("MAC", getMAC(context));

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
        try {
            String uuid = Settings.System.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            return uuid;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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
        try {
            final TelephonyManager mTelephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (mTelephony == null) {
                return null;
            }

            @SuppressLint({"MissingPermission", "HardwareIds"})
            String imsi = mTelephony.getSubscriberId();
            return imsi;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public String getICCID(Context context) {
        try {
            final TelephonyManager mTelephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (mTelephony == null) {
                return null;
            }

            @SuppressLint({"MissingPermission", "HardwareIds"})
            String iccid = mTelephony.getSimSerialNumber();
            return iccid;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public String getMAC(Context context) {
        try {
            final WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            if (wifiManager == null) {
                return null;
            }
            @SuppressLint("MissingPermission")
            final WifiInfo wInfo = wifiManager.getConnectionInfo();

            @SuppressLint("HardwareIds")
            String mac = wInfo.getMacAddress();
            return mac;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
