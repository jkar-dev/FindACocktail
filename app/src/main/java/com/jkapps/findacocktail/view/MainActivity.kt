package com.jkapps.findacocktail.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jkapps.findacocktail.R
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            Timber.i("Creating MainFragment")
            supportFragmentManager
                .beginTransaction()
                .add(R.id.mainContainer, MainFragment.instance)
                .commit()
        }
    }
}