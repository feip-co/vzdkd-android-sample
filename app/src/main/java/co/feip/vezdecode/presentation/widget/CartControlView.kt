package co.feip.vezdecode.presentation.widget

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import co.feip.vezdecode.R
import co.feip.vezdecode.databinding.CartControlBinding

class CartControlView : ConstraintLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        attrs?.let { initAttr(it) }
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        attrs?.let { initAttr(it) }
    }

    private val binding: CartControlBinding by lazy { CartControlBinding.bind(this) }

    private var addToCartClickListener: () -> Unit = {}
    private var increaseClickListener: () -> Unit = {}
    private var decreaseClickListener: () -> Unit = {}

    init {
        LayoutInflater.from(context).inflate(R.layout.cart_control, this, true)

        binding.btnAddToCart.setOnClickListener { addToCartClickListener.invoke() }
        binding.btnMinus.setOnClickListener { decreaseClickListener.invoke() }
        binding.btnPlus.setOnClickListener { increaseClickListener.invoke() }
    }

    fun setText(text: CharSequence) {
        binding.btnAddToCart.text = text
    }

    fun setQuantity(quantity: Int) {
        println(quantity == 0)
        with(binding) {
            tvQuantity.text = quantity.toString()
            groupQuantity.isVisible = quantity > 0
            btnAddToCart.isInvisible = quantity != 0
        }
    }

    fun setupListeners(
        addToCartClickListener: () -> Unit,
        increaseClickListener: () -> Unit,
        decreaseClickListener: () -> Unit
    ) {
        this.addToCartClickListener = addToCartClickListener
        this.increaseClickListener = increaseClickListener
        this.decreaseClickListener = decreaseClickListener
    }

    private fun initAttr(attr: AttributeSet) {
        val attributes = context.obtainStyledAttributes(attr, R.styleable.CartControlView)
        val buttonBackground = attributes.getColor(R.styleable.CartControlView_buttonBackground, -1)
        val buttonElevation =
            attributes.getBoolean(R.styleable.CartControlView_enableElevation, true)

        if (buttonBackground != -1) {
            with(binding) {
                btnMinus.backgroundTintList = ColorStateList.valueOf(buttonBackground)
                btnPlus.backgroundTintList = ColorStateList.valueOf(buttonBackground)
                btnAddToCart.backgroundTintList = ColorStateList.valueOf(buttonBackground)
            }
        }
        if (!buttonElevation) {
            with(binding) {
                btnMinus.stateListAnimator = null
                btnPlus.stateListAnimator = null
                btnAddToCart.stateListAnimator = null
            }
        }
        attributes.recycle()
    }
}