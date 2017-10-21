/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.view.fragment

import android.os.Bundle
import android.support.v7.preference.PreferenceFragmentCompat
import com.ayatk.biblio.BuildConfig
import com.ayatk.biblio.R
import com.ayatk.biblio.view.activity.WebActivity

class SettingFragment : PreferenceFragmentCompat() {

  override fun onCreatePreferences(bundle: Bundle?, s: String?) {
    addPreferencesFromResource(R.xml.pref)

    findPreference("oss_license")?.setOnPreferenceClickListener { _ ->
      startActivity(WebActivity.createIntent(
          activity, getString(R.string.pref_oss_license), "file:///android_asset/licenses.html"))
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
