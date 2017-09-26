package com.eduu.bang.app

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.eduu.bang.R

/**
 * Created by wikipeng on 2017/9/26.
 */
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val iView = SplashView ()
        iView.hello()
    }

    open interface IView {
        fun hello()
    }

    open class SplashView : IView {
        override fun hello() {
            Log.i("jzb", "hello------")
        }
    }
}