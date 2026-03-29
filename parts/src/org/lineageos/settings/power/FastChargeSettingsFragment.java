package org.lineageos.settings.power;

import android.os.Bundle;
import androidx.preference.PreferenceFragment;
import androidx.preference.SwitchPreference;
import androidx.preference.Preference;
import java.io.FileOutputStream;
import java.io.IOException;

public class FastChargeSettingsFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

    private static final String FAST_CHARGE_PATH = "/sys/class/qcom-battery/force_fast_charge";
    private SwitchPreference mFastChargePref;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(org.lineageos.settings.R.xml.fast_charge);
        mFastChargePref = (SwitchPreference) findPreference("fast_charge_enable");
        if (mFastChargePref != null) {
            mFastChargePref.setOnPreferenceChangeListener(this);
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference == mFastChargePref) {
            boolean enabled = (Boolean) newValue;
            try {
                FileOutputStream fos = new FileOutputStream(FAST_CHARGE_PATH);
                fos.write((enabled ? "1" : "0").getBytes());
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }
}
