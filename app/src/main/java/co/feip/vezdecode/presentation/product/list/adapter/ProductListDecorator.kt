package co.feip.vezdecode.presentation.product.list.adapter

import android.graphics.Rect
import android.view.View
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.RecyclerView
import co.feip.vezdecode.dpToPx

class ProductListDecorator : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val childAdapterPosition = parent.getChildAdapterPosition(view)
        val (leftPadding, rightPadding) = if (childAdapterPosition % 2 == 0) {
            0f to 4.dpToPx
        } else {
            4.dpToPx to 0f
        }
        view.updatePadding(
            left = leftPadding.toInt(),
            right = rightPadding.toInt(),
            bottom = 8.dpToPx.toInt()
        )
    }
}