package co.feip.vezdecode

import android.content.res.Resources
import android.util.TypedValue
import androidx.fragment.app.Fragment
import co.feip.vezdecode.presentation.NavigationContainer
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

val Number.dpToPx
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    )

fun Long.kopecksToRoublesString(): String {
    val roubles = if (this % 100 == 0L) (this / 100.0).formatPriceWithoutKopecks()
    else (this / 100.0).formatPriceWithKopecks()
    return "$roubles \u20bd"
}

fun Fragment.getNavigationContainer(): NavigationContainer =
    (parentFragment ?: requireActivity()) as NavigationContainer

private fun Double.formatPriceWithoutKopecks(): String {
    val dfs = DecimalFormatSymbols.getInstance().apply {
        groupingSeparator = ' '
        decimalSeparator = ','
    }
    val nf = DecimalFormat("#,##0", dfs).apply {
        isGroupingUsed = true
    }
    return nf.format(this)
}

private fun Double.formatPriceWithKopecks(): String {
    val dfs = DecimalFormatSymbols.getInstance().apply {
        groupingSeparator = ' '
        decimalSeparator = ','
    }
    val nf = DecimalFormat("#,##0.00", dfs).apply {
        isGroupingUsed = true
    }
    return nf.format(this)
}
