/*
 * Copyright (C) 2015 The CyanogenMod Project
 *               2017-2020 The LineageOS Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.aospextended.settings.asusparts;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemProperties;
import androidx.preference.PreferenceFragment;
import androidx.preference.Preference;
import androidx.preference.ListPreference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceManager;
import androidx.preference.PreferenceScreen;
import androidx.preference.SwitchPreference;
import androidx.preference.TwoStatePreference;
import android.provider.Settings;

import org.aospextended.settings.asusparts.doze.DozeSettingsActivity;

public class AsusParts extends PreferenceFragment implements
        Preference.OnPreferenceChangeListener {

    public static final String KEY_QHDR_SWITCH = "qhdr";
    public static final String QHDR_SYSTEM_PROPERTY = "persist.vendor.camera.StreamModeIndex";

    public static final String KEY_GLOVE_SWITCH = "glove";
    public static final String GLOVE_PATH = "/proc/driver/glove";

    public static final String KEY_SWIPEUP_SWITCH = "swipeup";
    public static final String SWIPEUP_PATH = "/proc/driver/swipeup";

    private TwoStatePreference mQhdrSwitch;
    private TwoStatePreference mGloveSwitch;
    private TwoStatePreference mSwipeUpSwitch;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.asusparts, rootKey);
        Preference mDozePref = findPreference("doze");
        mDozePref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(getContext(), DozeSettingsActivity.class);
                startActivity(intent);
                return true;
            }
        });

        mQhdrSwitch = (TwoStatePreference) findPreference(KEY_QHDR_SWITCH);
        mQhdrSwitch.setChecked(Settings.System.getInt(getContext().getContentResolver(),
        KEY_QHDR_SWITCH, 1) != 0);

        mGloveSwitch = (TwoStatePreference) findPreference(KEY_GLOVE_SWITCH);
        mGloveSwitch.setChecked(Settings.System.getInt(getContext().getContentResolver(),
        KEY_GLOVE_SWITCH, 1) != 0);

        mSwipeUpSwitch = (TwoStatePreference) findPreference(KEY_SWIPEUP_SWITCH);
        mSwipeUpSwitch.setChecked(Settings.System.getInt(getContext().getContentResolver(),
        KEY_SWIPEUP_SWITCH, 1) != 0);

    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {
        if (preference == mQhdrSwitch) {
            Settings.System.putInt(getContext().getContentResolver(), KEY_QHDR_SWITCH, mQhdrSwitch.isChecked() ? 1 : 0);
            SystemProperties.set(QHDR_SYSTEM_PROPERTY, mQhdrSwitch.isChecked() ? "9" : "");
            return true;
        }
        if (preference == mGloveSwitch) {
            Settings.System.putInt(getContext().getContentResolver(), KEY_GLOVE_SWITCH, mGloveSwitch.isChecked() ? 1 : 0);
            FileUtils.setValue(GLOVE_PATH, mGloveSwitch.isChecked() ? "1" : "0");
            return true;
        }
        if (preference == mSwipeUpSwitch) {
            Settings.System.putInt(getContext().getContentResolver(), KEY_SWIPEUP_SWITCH, mSwipeUpSwitch.isChecked() ? 1 : 0);
            FileUtils.setValue(SWIPEUP_PATH, mSwipeUpSwitch.isChecked() ? "1" : "0");
            return true;
        }
        return super.onPreferenceTreeClick(preference);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        final String key = preference.getKey();
        return true;
    }
}
