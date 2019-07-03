package mx.malain.pokeapi

import android.app.Application
import uk.co.chrisjenx.calligraphy.CalligraphyConfig

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        CalligraphyConfig.initDefault(
            CalligraphyConfig.Builder()
                .setDefaultFontPath(getString(R.string.default_font_path))
                .setFontAttrId(R.attr.fontPath)
                .build()
        )
    }
}