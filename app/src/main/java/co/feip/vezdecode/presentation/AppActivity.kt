package co.feip.vezdecode.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentManager
import co.feip.vezdecode.R
import co.feip.vezdecode.presentation.catalog.MainFragment

class AppActivity : AppCompatActivity(), NavigationContainer {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_app)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment())
                .commit()
        }
    }

    override fun getFlowFragmentManager(): FragmentManager = supportFragmentManager
}