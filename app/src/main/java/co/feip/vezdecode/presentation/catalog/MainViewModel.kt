package co.feip.vezdecode.presentation.catalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.feip.vezdecode.data.CartRepository
import co.feip.vezdecode.data.CatalogRepository
import co.feip.vezdecode.data.models.Category
import co.feip.vezdecode.kopecksToRoublesString
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainViewModel(
    private val catalogRepository: CatalogRepository,
    private val cartRepository: CartRepository
) : ViewModel() {

    private val _categories = MutableStateFlow<List<Category>>(emptyList())

    val categories = _categories.asStateFlow()
    val totalPrice = cartRepository.cartFlow.map { it.totalPriceKopecks }
        .map { if (it > 0) it.kopecksToRoublesString() else "" }

    init {
        viewModelScope.launch {
            loadCategories()
        }
    }

    private suspend fun loadCategories() {
        _categories.value = catalogRepository.getCategories()
    }

}