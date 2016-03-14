/*
 * about.java
 * 
 * Copyright 2014 westcrip <westcrip@westcrip-altankrk>
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301, USA.
 * 
 * 
 */
package com.android.settings.arsenic.about;

import android.app.Activity;
import android.app.ActivityManagerNative;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;
import com.android.internal.logging.MetricsLogger;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceGroup;
import android.preference.PreferenceScreen;
import android.preference.Preference.OnPreferenceChangeListener;
import android.provider.MediaStore;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.util.Log;
import android.view.IWindowManager;
import android.view.Display;
import android.view.Window;
import android.widget.Toast;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.R;
import com.android.settings.Utils;
    
public class about extends SettingsPreferenceFragment implements
        Preference.OnPreferenceChangeListener {
			
public static final String TAG = "about";
    
private static final String ARSENIC_ROM_SHARE = "share";
    
    Preference mForumUrl;
    Preference mForumUrl2;
    Preference mSourceUrl;
    Preference mDonateUrl;
    Preference mSourcebaseUrl;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.arsenic_about);
        PreferenceScreen prefSet = getPreferenceScreen();
        ContentResolver resolver = getContentResolver();
        mForumUrl = findPreference("arsenic_forum");
        mForumUrl2 = findPreference("arsenic_forum_2");
        mSourceUrl = findPreference("arsenic_source");
        mDonateUrl = findPreference("arsenic_donate");
        mSourcebaseUrl = findPreference("arsenic_sourcebase");
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object value) {
        return false;
    }
    
    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        if (preference == mForumUrl) {
            launchUrl("http://www.htcmania.com/showthread.php?t=1102848");
        } else if (preference == mForumUrl2) {
            launchUrl("http://www.darksideteam.com/threads/2350-6-0-arsenic-rom-zero-gapps");
        } else if (preference == mSourceUrl) {
            launchUrl("https://github.com/Juancarlospr5");
        } else if (preference == mDonateUrl) {
            launchUrl("https://www.paypal.com/us/cgi-bin/webscr?cmd=_flow&SESSION=wcCfCUOkZcwcn0lcFPhhMjxwdUYciHzbDa0bLgbNV0VRgZUorosFGZ6k8hy&dispatch=5885d80a13c0db1f8e263663d3faee8d64ad11bbf4d2a5a1a0d303a50933f9b2");
        } else if (preference == mSourcebaseUrl) {
            launchUrl("https://github.com/CyanogenMod");
        } else if (preference.getKey().equals(ARSENIC_ROM_SHARE)) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("text/plain");
            //intent.putExtra(Intent.EXTRA_TEXT, String.format(
            //     getActivity().getString(R.string.share_message)));
            //startActivity(Intent.createChooser(intent, getActivity().getString(R.string.share_chooser_title)));
        }  else {
            // If not handled, let preferences handle it.
            return super.onPreferenceTreeClick(preferenceScreen, preference);
    }
         return true; 
    }
    private void launchUrl(String url) {
        Uri uriUrl = Uri.parse(url);
        Intent donate = new Intent(Intent.ACTION_VIEW, uriUrl);
        getActivity().startActivity(donate);
    }
   
   @Override
   protected int getMetricsCategory() {
   return 1;
   }
}
