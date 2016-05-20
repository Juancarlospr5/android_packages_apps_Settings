/*
* Copyright (C) 2016 RR
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.android.settings.arsenic;


import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.UserHandle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceScreen;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.SwitchPreference;
import android.provider.Settings;
import org.cyanogenmod.internal.util.CmLockPatternUtils;
import com.android.settings.Utils;
import android.provider.SearchIndexableResource;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.search.Indexable;

import com.android.internal.logging.MetricsLogger;
import cyanogenmod.providers.CMSettings;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

import java.util.List;
import java.util.ArrayList;

public class QsPanel extends SettingsPreferenceFragment  implements Preference.OnPreferenceChangeListener ,Indexable {
 private static final String PREF_NUM_COLUMNS = "sysui_qs_num_columns";

    private ListPreference mNumColumns;		

    private static final int MY_USER_ID = UserHandle.myUserId();
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        addPreferencesFromResource(R.xml.arsenic_qs_panel);
        PreferenceScreen prefSet = getPreferenceScreen();
        final ContentResolver resolver = getActivity().getContentResolver();
	final CmLockPatternUtils lockPatternUtils = new CmLockPatternUtils(getActivity());

 	Resources res = getResources();
	 // Number of QS Columns 3,4,5
            mNumColumns = (ListPreference) findPreference(PREF_NUM_COLUMNS);
            int numColumns = Settings.System.getIntForUser(resolver,
                    Settings.System.QS_NUM_TILE_COLUMNS, getDefaultNumColums(),
                    UserHandle.USER_CURRENT);
            mNumColumns.setValue(String.valueOf(numColumns));
            updateNumColumnsSummary(numColumns);
            mNumColumns.setOnPreferenceChangeListener(this);

    }

    @Override
    protected int getMetricsCategory() {
        return MetricsLogger.APPLICATION;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
	ContentResolver resolver = getActivity().getContentResolver();
	Resources res = getResources();
         if (preference == mNumColumns) {
                int numColumns = Integer.valueOf((String) newValue);
                Settings.System.putIntForUser(resolver, Settings.System.QS_NUM_TILE_COLUMNS,
                        numColumns, UserHandle.USER_CURRENT);
                updateNumColumnsSummary(numColumns);
                return true;
         }       
         return false;
	}

      private void updateNumColumnsSummary(int numColumns) {
            String prefix = (String) mNumColumns.getEntries()[mNumColumns.findIndexOfValue(String
                    .valueOf(numColumns))];
            mNumColumns.setSummary(getResources().getString(R.string.qs_num_columns_showing, prefix));
        }

        private int getDefaultNumColums() {
            try {
                Resources res = getActivity().getPackageManager()
                        .getResourcesForApplication("com.android.systemui");
                int val = res.getInteger(res.getIdentifier("quick_settings_num_columns", "integer",
                        "com.android.systemui")); // better not be larger than 5, that's as high as the
                                                  // list goes atm
                return Math.max(1, val);
            } catch (Exception e) {
                return 3;
            }
        }

    	    public static final Indexable.SearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new BaseSearchIndexProvider() {
                @Override
                public List<SearchIndexableResource> getXmlResourcesToIndex(Context context,
                                                                             boolean enabled) {
                     ArrayList<SearchIndexableResource> result =
                             new ArrayList<SearchIndexableResource>();
 
                     SearchIndexableResource sir = new SearchIndexableResource(context);
                    sir.xmlResId = R.xml.arsenic_qs_panel;
                     result.add(sir);
 
                     return result;
                 }
 
                 @Override
                 public List<String> getNonIndexableKeys(Context context) {
                     final List<String> keys = new ArrayList<String>();
                     return keys;
                 }
         };
}
