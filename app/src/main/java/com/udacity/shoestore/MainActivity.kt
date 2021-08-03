package com.udacity.shoestore

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.udacity.shoestore.databinding.ActivityMainBinding
import com.udacity.shoestore.vm.ShoeVM
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private var logoutMenu: MenuItem? = null

    private lateinit var navController: NavController
    lateinit var shoeVM: ShoeVM
    lateinit var bind: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = DataBindingUtil.setContentView(this, R.layout.activity_main)
        shoeVM = ViewModelProvider(this).get(ShoeVM::class.java)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(
            topLevelDestinationIds = setOf(R.id.loginFragment),
            fallbackOnNavigateUpListener = ::onSupportNavigateUp
        )
        setSupportActionBar(bind.toolbar)
        bind.toolbar.setupWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            Timber.d("destination: ${destination.label}")
            logoutMenu?.isVisible = destination.id == R.id.listingFragment
        }
        Timber.plant(Timber.DebugTree())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu, menu)
        logoutMenu = menu?.findItem(R.id.menu_logout)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_logout) {
            navController.navigate(R.id.loginFragment, null, NavOptions.Builder().apply {
                this.setLaunchSingleTop(true)
                this.setPopUpTo(R.id.loginFragment, false)
                this.setEnterAnim(android.R.anim.slide_in_left)
                this.setExitAnim(android.R.anim.slide_out_right)
            }.build())
        }
        return super.onOptionsItemSelected(item)
    }
}
