package co.feip.vezdecode.presentation.search

import android.os.Bundle
import android.text.style.StyleSpan
import android.text.style.TextAppearanceSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import co.feip.vezdecode.R
import co.feip.vezdecode.data.CartRepository
import co.feip.vezdecode.data.CatalogRepository
import co.feip.vezdecode.databinding.FragmentSearchBinding
import co.feip.vezdecode.getNavigationContainer
import co.feip.vezdecode.presentation.CustomViewModelFactory
import co.feip.vezdecode.presentation.product.details.ProductDetailsFragment
import co.feip.vezdecode.presentation.product.list.adapter.ProductListAdapter
import co.feip.vezdecode.presentation.product.list.adapter.ProductListDecorator

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding
        get() = requireNotNull(_binding)

    private val viewModel by lazy {
        CustomViewModelFactory {
            SearchViewModel(
                CartRepository.INSTANCE,
                CatalogRepository.getInstance(requireContext().applicationContext)
            )
        }.create(SearchViewModel::class.java)
    }

    private val searchAdapter by lazy {
        ProductListAdapter(
            viewModel::onProductClicked,
            viewModel::onAddToCartClicked,
            viewModel::onIncreaseClicked,
            viewModel::onDecreaseClicked
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            recyclerView.adapter = searchAdapter
            recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
            recyclerView.addItemDecoration(ProductListDecorator())

            etSearch.addTextChangedListener { viewModel.onQueryChanged(it.toString()) }

            toolbar.setNavigationOnClickListener {
                getNavigationContainer().getFlowFragmentManager().popBackStack()
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.products.collect { searchAdapter.submitList(it) }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.openDetailsEvent.collect {
                getNavigationContainer().getFlowFragmentManager().beginTransaction()
                    .replace(R.id.container, ProductDetailsFragment.newInstance(it))
                    .addToBackStack(null)
                    .commit()
            }
        }
    }
}