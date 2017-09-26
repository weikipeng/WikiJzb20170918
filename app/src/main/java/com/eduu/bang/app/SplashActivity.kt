package com.eduu.bang.app

import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import com.eduu.bang.R


/**
 * Created by wikipeng on 2017/9/26.
 */
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val iView: SplashView = SplashView(window.decorView.findViewById(Window.ID_ANDROID_CONTENT))
        val presenter: IPresenter = SplashPresenter(iView)
        presenter.refresh(true)
    }

    open interface IView {
        fun updateCountDownTime(millisUntilFinished: Long)
    }

    open interface IPresenter {
        fun refresh(isForce: Boolean)
    }

    open class SplashView(val rootView: View) : IView {
        lateinit var mCountDownTimeText: TextView

        init {
            initView()
            addEvent()
        }

        fun initView() {
            mCountDownTimeText = rootView.findViewById(R.id.tv_countdown_time)
        }

        fun addEvent() {

        }

        override fun updateCountDownTime(millisUntilFinished: Long) {
            mCountDownTimeText.text = rootView.resources.getString(R.string.pattern_count_down_splash
                    , millisUntilFinished / 1000)
        }

    }

    open class SplashPresenter(val mvpView: IView) : IPresenter {
        lateinit var mCountDownTimer: CountDownTimer

        init {
            initData()
        }

        fun initData() {
            mCountDownTimer = object : CountDownTimer(5000, 1000) {

                /**
                 * Callback fired on regular interval.

                 * @param millisUntilFinished The amount of time until finished.
                 */
                override fun onTick(millisUntilFinished: Long) {
                    mvpView.updateCountDownTime(millisUntilFinished)
                }

                /**
                 * Callback fired when the time is up.
                 */
                override fun onFinish() {
                    mvpView.updateCountDownTime(0)
                    onCountDownFinish()
                }
            }
        }

        override fun refresh(isForce: Boolean) {
            mCountDownTimer.start()
        }

        fun onCountDownFinish() {

        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            var decorView = window.decorView
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN)
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                decorView.systemUiVisibility = decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            }
        }
    }
}