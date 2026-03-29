package org.lineageos.settings.utils;

import java.io.FileOutputStream;
import java.io.IOException;

public class FastChargeUtils {
    public static final String FAST_CHARGE_PATH = "/sys/class/qcom-battery/force_fast_charge";

    public static void setFastChargeEnabled(boolean enabled) {
        try {
            FileOutputStream fos = new FileOutputStream(FAST_CHARGE_PATH);
            fos.write((enabled ? "1" : "0").getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
