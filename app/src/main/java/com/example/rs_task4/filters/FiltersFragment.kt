package com.example.rs_task4.filters

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.rs_task4.R

class FiltersFragment: PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        return
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.filters)

    }
}