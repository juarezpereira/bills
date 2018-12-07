package br.com.juarez.bills.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import br.com.juarez.bills.App
import br.com.juarez.bills.R
import com.google.android.play.core.splitinstall.*
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus

class MainActivity : AppCompatActivity(), SplitInstallStateUpdatedListener {
    
    private val moduleExport by lazy { getString(R.string.title_export) }
    
    private lateinit var manager: SplitInstallManager

    private var dialogInstall: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        manager = SplitInstallManagerFactory.create(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_item_export -> loadAndLaunchModule(moduleExport)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        manager.registerListener(this)
    }

    override fun onPause() {
        super.onPause()
        manager.unregisterListener(this)
    }

    override fun onStateUpdate(state: SplitInstallSessionState) {
        state.moduleNames().forEach { name ->
            when (state.status()) {
                SplitInstallSessionStatus.DOWNLOADING -> {
                    updateProgressMessage("Downloading $name")
                }
                SplitInstallSessionStatus.REQUIRES_USER_CONFIRMATION -> {
                    val sender = state.resolutionIntent()?.intentSender
                    startIntentSender(sender, null, 0, 0, 0)
                }
                SplitInstallSessionStatus.INSTALLED -> {
                    onSuccessfulLoad(name, launch = state.moduleNames().size == 1)
                }
                SplitInstallSessionStatus.INSTALLING -> {
                    updateProgressMessage("Installing $name")
                }
                SplitInstallSessionStatus.FAILED -> {
                    toastError("Error: ${state.errorCode()} for module ${state.moduleNames()}")
                }
            }
        }
    }

    private fun loadAndLaunchModule(name: String) {
        updateProgressMessage("Loading module $name")

        if (manager.installedModules.contains(name)) {
            updateProgressMessage("Already installed")
            onSuccessfulLoad(name, launch = true)
            return
        }

        val request = SplitInstallRequest.newBuilder()
                .addModule(name)
                .build()

        manager.startInstall(request)
                .addOnCompleteListener {
                    if (it.isSuccessful) {

                        return@addOnCompleteListener
                    }
                }
    }

    private fun updateProgressMessage(message: String) {
        dialogInstall = dialogInstall ?: AlertDialog.Builder(this).create()
        dialogInstall?.setMessage(message)

        if (dialogInstall?.isShowing == false) {
            dialogInstall?.show()
        }
    }

    private fun onSuccessfulLoad(name: String, launch: Boolean) {
        when (name) {
            moduleExport -> if (launch) launchActivity(App.CLASS_NAME_EXPORT)
        }
    }

    private fun launchActivity(className: String) {
        val intent = Intent()
            intent.setClassName(App.PACKAGE_FEATURES, className)
        startActivity(intent)
    }

    private fun toastError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

}