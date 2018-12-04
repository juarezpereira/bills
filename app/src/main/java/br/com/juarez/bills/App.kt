package br.com.juarez.bills

import android.app.Application
import android.content.Context
import com.google.android.play.core.splitcompat.SplitCompat

class App: Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        SplitCompat.install(this)
    }

    companion object {
        const val PACKAGE_FEATURES = "${BuildConfig.APPLICATION_ID}.features"
        const val CLASS_NAME_EXPORT = "ExportActivity"
    }

}