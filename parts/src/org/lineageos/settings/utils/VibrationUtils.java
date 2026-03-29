package org.lineageos.settings.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import android.os.SystemProperties;
import android.util.Log;

public class VibrationUtils {
    public static final String VIB_PATH = "/sys/class/leds/vibrator/vmax_mv";
    private static final String TAG = "VibrationUtils";
    
    public static final int MAX_VOLTAGE = 3000; 
    public static final int MIN_VOLTAGE = 2000; 

    public static void setVibrationStrength(int percent) {
        if (percent > 100) percent = 100;
        if (percent < 10) percent = 10;

        int voltage = MIN_VOLTAGE + ((MAX_VOLTAGE - MIN_VOLTAGE) * percent / 100);
        
        try {
            FileOutputStream fos = new FileOutputStream(VIB_PATH);
            fos.write((String.valueOf(voltage) + "\n").getBytes());
            fos.close();
            Log.d(TAG, "Vibration set to: " + voltage + "mV");

            // BOUNCE THE HAL: Force Qualcomm's service to restart and re-read the node
            SystemProperties.set("ctl.restart", "vendor.qti.vibrator");
            
        } catch (IOException e) {
            Log.e(TAG, "Failed to write to vibrator node", e);
        }
    }
}
