package co.feip.vezdecode.presentation.catalog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import co.feip.vezdecode.R
import co.feip.vezdecode.data.CartRepository
import co.feip.vezdecode.data.CatalogRepository
import co.feip.vezdecode.data.models.Category
import co.feip.vezdecode.databinding.FragmentMainBinding
import co.feip.vezdecode.getNavigationContainer
import co.feip.vezdecode.presentation.CustomViewModelFactory
import co.feip.vezdecode.presentation.NavigationContainer
import co.feip.vezdecode.presentation.cart.CartFragment
import co.feip.vezdecode.presentation.catalog.adapter.CategoriesPagerAdapter
import co.feip.vezdecode.presentation.filter.FilterDialogFragment
import co.feip.vezdecode.presentation.search.SearchFragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.flow.collect

class MainFragment : Fragment(), NavigationContainer {

    private val viewModel: MainViewModel by lazy {
        CustomViewModelFactory {
            MainViewModel(
                CatalogRepository.getInstance(requireContext().applicationContext),
                CartRepository.INSTANCE
            )
        }.create(MainViewModel::class.java)
    }

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = requireNotNull(_binding)

    private var tabMediator: TabLayoutMediator? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenStarted {
            viewModel.categories.collect { initTabLayout(it) }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.totalPrice.collect {
                binding.containerCart.isVisible = it.isNotBlank()
                binding.btnGoToCart.text = it
            }
        }

        binding.btnGoToCart.setOnClickListener {
            getNavigationContainer().getFlowFragmentManager().beginTransaction()
                .replace(R.id.container, CartFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.toolbar.menu.findItem(R.id.action_search).setOnMenuItemClickListener {
            getNavigationContainer().getFlowFragmentManager().beginTransaction()
                .replace(R.id.container, SearchFragment())
                .addToBackStack(null)
                .commit()
            true
        }

        binding.toolbar.setNavigationOnClickListener {
            FilterDialogFragment().show(childFragmentManager, null)
        }
    }

    override fun getFlowFragmentManager(): FragmentManager =
        requireActivity().supportFragmentManager

    private fun initTabLayout(categories: List<Category>) {
        binding.viewPager.adapter =
            CategoriesPagerAdapter(categories, childFragmentManager, viewLifecycleOwner.lifecycle)
        tabMediator?.detach()
        tabMediator = TabLayoutMediator(
            binding.tabLayout,
            binding.viewPager
        ) { tab, position ->
            tab.text = categories[position].name
            // if bg set as tabBackground in xml there is some bug with it & paddings
            tab.view.setBackgroundResource(R.drawable.bg_tab)
        }.also { it.attach() }
    }
}