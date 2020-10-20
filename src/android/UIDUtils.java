package com.joyo.cordova.uid;

//import android.annotation.SuppressLint;
//import android.content.ContentResolver;
//import android.content.ContentUris;
//import android.content.ContentValues;
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.database.Cursor;
//import android.net.Uri;
import android.os.Build;
//import android.os.Environment;
//import android.provider.MediaStore;
//import android.telephony.TelephonyManager;
//import android.text.TextUtils;
//import android.util.Log;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.util.UUID;


/**
 * 获取设备ID工具类
 *
 * Created By JoyoDuan On 2020-09-17
 */
public class UIDUtils {
//    private static final String TAG = UIDUtils.class.getSimpleName();
//
//    private static final String TEMP_DIR = "system_config";
//    private static final String TEMP_FILE_NAME = "system_file";
//    private static final String TEMP_FILE_NAME_MIME_TYPE = "application/octet-stream";
//    private static final String SP_NAME = "device_info";
//    private static final String SP_KEY_DEVICE_ID = "device_id";


    /**
     * 获取设备唯一标识
     */
//    public static String getDeviceId(Context context) {
//        SharedPreferences sharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
//        String deviceId = sharedPreferences.getString(SP_KEY_DEVICE_ID, null);
//        if (!TextUtils.isEmpty(deviceId)) {
//            return deviceId;
//        }
//        deviceId = getIMEI(context);
//        if (TextUtils.isEmpty(deviceId)) {
//            deviceId = createUUID(context);
//        }
//        sharedPreferences.edit()
//                .putString(SP_KEY_DEVICE_ID, deviceId)
//                .apply();
//        return deviceId;
//    }
//
//    /**
//     * 生成一个随机UUID，并存在磁盘
//     *
//     * Created By JoyoDuan On 2020-09-17
//     */
//    private static String createUUID(Context context) {
//        // 随机uuid
//        String uuid = UUID.randomUUID().toString().replace("-", "");
//
//        // Android Q
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//            Uri externalContentUri = MediaStore.Downloads.EXTERNAL_CONTENT_URI;
//            ContentResolver contentResolver = context.getContentResolver();
//            // 返回的字段
//            String[] projection = new String[]{
//                    MediaStore.Downloads._ID
//            };
//
//            // 相当于where条件
//            String selection = MediaStore.Downloads.TITLE + "=?";
//            String[] args = new String[]{
//                    TEMP_FILE_NAME
//            };
//            Cursor query = contentResolver.query(externalContentUri, projection, selection, args, null);
//            // 查到文件
//            if (query != null && query.moveToFirst()) {
//                Uri uri = ContentUris.withAppendedId(externalContentUri, query.getLong(0));
//                query.close();
//
//                InputStream inputStream = null;
//                BufferedReader bufferedReader = null;
//                try {
//                    // 读文件
//                    inputStream = contentResolver.openInputStream(uri);
//                    // 没有内容
//                    if (inputStream != null) {
//                        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//                        uuid = bufferedReader.readLine();
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } finally {
//                    if (bufferedReader != null) {
//                        try {
//                            bufferedReader.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    if (inputStream != null) {
//                        try {
//                            inputStream.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            } else { // 没有找到文件
//                ContentValues contentValues = new ContentValues();
//                // 文件名标题
//                contentValues.put(MediaStore.Downloads.TITLE, TEMP_FILE_NAME);
//                // 文件类型
//                contentValues.put(MediaStore.Downloads.MIME_TYPE, TEMP_FILE_NAME_MIME_TYPE);
//                // 显示的文件名
//                contentValues.put(MediaStore.Downloads.DISPLAY_NAME, TEMP_FILE_NAME);
//                // 保存文件的路径
//                contentValues.put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS + File.separator + TEMP_DIR);
//
//                Uri insert = contentResolver.insert(externalContentUri, contentValues);
//                if (insert != null) {
//                    OutputStream outputStream = null;
//                    try {
//                        outputStream = contentResolver.openOutputStream(insert);
//                        if (outputStream == null) {
//                            return uuid;
//                        }
//                        outputStream.write(uuid.getBytes());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    } finally {
//                        if (outputStream != null) {
//                            try {
//                                outputStream.close();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                }
//            }
//        } else { // Android版本小于Android Q
//            File externalDownloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
//            File applicationFileDir = new File(externalDownloadsDir, TEMP_DIR);
//            if (!applicationFileDir.exists()) {
//                if (!applicationFileDir.mkdirs()) {
//                    Log.e(TAG, "文件夹创建失败: " + applicationFileDir.getPath());
//                }
//            }
//            File file = new File(applicationFileDir, TEMP_FILE_NAME);
//            // 文件不存在
//            if (!file.exists()) {
//                FileWriter fileWriter = null;
//                try {
//                    // 创建文件成功
//                    if (file.createNewFile()) {
//                        // 写入内容
//                        fileWriter = new FileWriter(file, false);
//                        fileWriter.write(uuid);
//                    } else {
//                        Log.e(TAG, "文件创建失败：" + file.getPath());
//                    }
//                } catch (IOException e) {
//                    Log.e(TAG, "文件创建失败：" + file.getPath());
//                    e.printStackTrace();
//                } finally {
//                    if (fileWriter != null) {
//                        try {
//                            fileWriter.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            } else { // 文件存在
//                FileReader fileReader = null;
//                BufferedReader bufferedReader = null;
//                try {
//                    fileReader = new FileReader(file);
//                    bufferedReader = new BufferedReader(fileReader);
//                    uuid = bufferedReader.readLine();
//
//                    bufferedReader.close();
//                    fileReader.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } finally {
//                    if (bufferedReader != null) {
//                        try {
//                            bufferedReader.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    if (fileReader != null) {
//                        try {
//                            fileReader.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }
//        }
//
//        return uuid;
//    }
//
//    /**
//     * 获取设备IMEI
//     *
//     * Created By JoyoDuan On 2020-09-17
//     *
//     * @param context
//     * @return
//     */
//    private static String getIMEI(Context context) {
//        // Android Q获取不到
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//            return null;
//        }
//        try {
//            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//            if (telephonyManager == null) {
//                return null;
//            }
//            @SuppressLint({"MissingPermission", "HardwareIds"})
//            String imei = telephonyManager.getDeviceId();
//            return imei;
//        } catch (Exception e) {
//            return null;
//        }
//    }

    /**
     * 获取设备唯一ID
     * @return deviceId
     */
    public static String getUID() {
        String deviceId = "86" +
                Build.BOARD.length() % 10 + Build.BRAND.length() % 10 +
                Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 +
                Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 +
                Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 +
                Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 +
                Build.TAGS.length() % 10 + Build.TYPE.length() % 10 +
                Build.USER.length() % 10; //13 位
        return deviceId;
    }
}
