package co.feip.vezdecode.presentation.models

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.StrikethroughSpan
import android.text.style.TextAppearanceSpan
import co.feip.vezdecode.R
import co.feip.vezdecode.data.models.Product
import co.feip.vezdecode.kopecksToRoublesString

data class ProductModel(
    val product: Product,
    val quantity: Int // cart quantity
) {

    val totalPrice: Long = product.currentPrice * quantity

    val formattedMeasure: String = "${product.measure} ${product.measureUnit}"

    val formattedItemPrice: CharSequence = product.currentPrice.kopecksToRoublesString()

    fun getFormattedPrice(context: Context): CharSequence {
        val builder = SpannableStringBuilder(product.currentPrice.kopecksToRoublesString())
        if (product.priceOld != null && product.priceOld > product.currentPrice) {
            builder.append("  ")
            val oldSpan = SpannableString(product.priceOld.kopecksToRoublesString())
            oldSpan.setSpan(
                TextAppearanceSpan(context, R.style.OldPriceAppearance),
                0,
                oldSpan.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            oldSpan.setSpan(
                StrikethroughSpan(),
                0,
                oldSpan.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            builder.append(oldSpan)
        }
        return builder
    }

}
