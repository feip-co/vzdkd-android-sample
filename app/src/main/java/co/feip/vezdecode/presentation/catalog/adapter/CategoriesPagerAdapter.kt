package co.feip.vezdecode.presentation.catalog.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import co.feip.vezdecode.data.models.Category
import co.feip.vezdecode.presentation.product.list.ProductListFragment

class CategoriesPagerAdapter(
    private val categories: List<Category>,
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = categories.size

    override fun createFragment(position: Int): Fragment =
        ProductListFragment.newInstance(categories[position].id)
}