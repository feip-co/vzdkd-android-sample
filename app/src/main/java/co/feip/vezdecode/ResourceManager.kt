package co.feip.vezdecode

import android.content.Context
import androidx.annotation.StringRes

class ResourceManager(private val context: Context) {

    fun getString(@StringRes id: Int, vararg formatArgs: Any) =
        context.getString(id, *formatArgs)

    fun getString(@StringRes id: Int) =
        context.getString(id)

}