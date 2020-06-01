package com.example.panaderosvm._view_ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.panaderosvm.R
import com.example.panaderosvm._view_ui.Base.BaseActivity
import com.example.panaderosvm._view_ui.Base.BasicMethods
import com.example.panaderosvm._view_ui.home.HomeFragment
import com.example.panaderosvm._view_ui.login.LoginActivity
import com.example.panaderosvm._view_ui.panaderias.PanaderiasFragment
import com.example.panaderosvm._view_ui.pueblos.PueblosFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.indra.applicability.utils.connectivity.ConnectionCallback
import com.indra.applicability.utils.connectivity.ConnectionListener
import com.indra.applicability.utils.connectivity.NetworkChecker
import kotlinx.android.synthetic.main.activity_navigation_drawer.*
import kotlinx.android.synthetic.main.app_bar_navigation_drawer.*
import kotlinx.android.synthetic.main.content_navigation_drawer.*

class MainActivity : BaseActivity(), BasicMethods, ConnectionCallback, ConnectionListener {

    override val TAG = this.javaClass.simpleName
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private lateinit var networkChecker : NetworkChecker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation_drawer)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        /*val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }*/

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view_principal)
        navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_panaderias,
                R.id.nav_pueblos,
                R.id.nav_logOut
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        initObservables()
        init()
        initListeners()


    }

    override fun initObservables() {}

    override fun init() {
        initNetworkChecker()
    }

    override fun initListeners() {
        nav_view_principal.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    if (nav_host_fragment.childFragmentManager.fragments[0] !is HomeFragment) {
                        Toast.makeText(this, "HOME", Toast.LENGTH_SHORT).show()
                        gotoHome()
                    }
                }
                R.id.nav_pueblos -> {
                    if (nav_host_fragment.childFragmentManager.fragments[0] !is PueblosFragment) {
                        Toast.makeText(this, "PUEBLOS", Toast.LENGTH_SHORT).show()
                        goToPueblos()
                    }
                }
                R.id.nav_panaderias -> {
                    if (nav_host_fragment.childFragmentManager.fragments[0] !is PanaderiasFragment) {
                        Toast.makeText(this, "PANADERIAS", Toast.LENGTH_SHORT).show()
                        goToPanaderias()
                    }
                }
                R.id.nav_logOut -> {
                    logout()
                }
            }
            return@setNavigationItemSelectedListener false
        }
    }

    private fun initNetworkChecker() {
        networkChecker = NetworkChecker()
        networkChecker.init(this, this, this)
        networkChecker.checkNetworkConnection(this, this)
    }

    private fun gotoHome() {
        try {
            navController.navigate(R.id.action_nav_home_self)
            //onBackPressed()
            if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
                drawerLayoutBackPressed()
            }
        } catch (e: Exception) {
            Log.e(this.TAG, e.toString())
        }
    }

    private fun goToPanaderias() {
        try {
            navController.navigate(R.id.action_global_PanaderiasFragment)
            if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
                drawerLayoutBackPressed()
            }
        } catch (e: Exception) {
            Log.e(this.TAG, e.toString())
        }
    }

    private fun goToPueblos() {
        try {
            navController.navigate(R.id.action_global_PueblosFragment)
            if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
                drawerLayoutBackPressed()
            }
        } catch (e: Exception) {
            Log.e(this.TAG, e.toString())
        }
    }

    private fun logout() {
        // Dialog consultando si realmente quiere desloguearse y si dice si ejecutamos el metodo:
        goToLogin()
    }

    private fun goToLogin() {
        navController.navigate(R.id.action_global_LoginActivity)

        /*val nIntent = Intent(this, LoginActivity::class.java)
        startActivity(nIntent)
        finish()*/
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.activity_navigation_drawer_drawer, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawerLayoutBackPressed()
        } else {
            val fragment: List<Fragment> = nav_host_fragment.childFragmentManager.fragments
            if (fragment.isNotEmpty()) {
                if (fragment[0] is HomeFragment) {
                    //SI ESTOY EN HOME CIERRA APP
                    finish()
                } else if (fragment[0] is PueblosFragment) {
                    //SI ESTOY EN PUEBLOFRAGMENT SIEMPRE VUELVE A LA PANTALLA ANTERIOR
                    super.onBackPressed()
                    init()
                } else if (fragment[0] is PanaderiasFragment) {
                    //SI ESTOY EN PANADERIAFRAGMENT VUELVE A LA PANTALLA ANTERIOR
                    super.onBackPressed()
                    init()
                    /*} else if (fragment[0] is DetailProfessionalFragment) {
                        detailProfessionalHospitalBackPressed()*/
                } else {
                    if (fragment[0] !is HomeFragment) {
                        super.onBackPressed()
                        init()
                    } else {
                        super.onBackPressed()
                    }
                }
            }
        }
        super.onBackPressed()
    }

    fun drawerLayoutBackPressed() {
        drawer_layout.closeDrawer(GravityCompat.START)
    }

    override fun hasActiveConnection(hasActiveConnection: Boolean) {
//        val textSinConexion = Ac

        if (!hasActiveConnection) {
            // TODO: Aca hacer q cambie de color la barra de notificaciones
            areaSinConexion.visibility = View.VISIBLE
        } else {
            // TODO: Aca hacer q vuelva a la normalidad la barra de notificaciones
            areaSinConexion.visibility = View.GONE
        }
    }

    override fun onWifiTurnedOn() {}

    override fun onWifiTurnedOff() {}
}
