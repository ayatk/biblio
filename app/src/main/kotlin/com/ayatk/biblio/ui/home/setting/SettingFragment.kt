/*
 * Copyright (c) 2016-2018 ayatk.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ayatk.biblio.ui.home.setting

import android.os.Bundle
import android.support.v7.preference.PreferenceFragmentCompat
import com.ayatk.biblio.BuildConfig
import com.ayatk.biblio.R
import com.ayatk.biblio.ui.license.LicenseActivity
import com.ayatk.biblio.util.Analytics

class SettingFragment : PreferenceFragmentCompat() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    lifecycle.addObserver(Analytics.ScreenLog(Analytics.Screen.SETTINGS))
  }

  override fun onCreatePreferences(bundle: Bundle?, s: String?) {
    addPreferencesFromResource(R.xml.pref)

    findPreference("oss_license")?.setOnPreferenceClickListener { _ ->
      startActivity(
          LicenseActivity.createIntent(
              activity, getString(R.string.pref_oss_license), "file:///android_asset/licenses.html"
          )
      )
      true
    }

    findPreference("app_version")?.apply {
      summary = "${BuildConfig.VERSION_NAME} #${BuildConfig.BUILD_NUM} (${BuildConfig.GIT_SHA})"
    }
  }

  companion object {
    fun newInstance(): SettingFragment = SettingFragment()
  }
}
