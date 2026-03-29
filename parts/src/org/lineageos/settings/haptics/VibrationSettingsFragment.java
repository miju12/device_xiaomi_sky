package org.lineageos.settings.haptics;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.os.VibrationEffect;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragment;
import androidx.preference.SeekBarPreference;

import org.lineageos.settings.R;
import org.lineageos.settings.utils.VibrationUtils;

public class VibrationSettingsFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

    private SeekBarPreference mVibrationPref;
    private Vibrator mVibrator;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(org.lineageos.settings.R.xml.vibration);
        
        mVibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
        mVibrationPref = (SeekBarPreference) findPreference("vibration_strength");
        
        if (mVibrationPref != null) {
            mVibrationPref.setOnPreferenceChangeListener(this);
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference == mVibrationPref) {
            int sliderValue = (Integer) newValue;
            
            // 1. Write the new voltage
            VibrationUtils.setVibrationStrength(sliderValue);
            
            // 2. Test vibration! (50ms duration)
            if (mVibrator != null && mVibrator.hasVibrator()) {
                mVibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
            }
            return true;
        }
        return false;
    }
}
