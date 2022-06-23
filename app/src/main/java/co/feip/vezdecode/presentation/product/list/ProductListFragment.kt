package co.feip.vezdecode.presentation.product.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import co.feip.vezdecode.R
import co.feip.vezdecode.data.CartRepository
import co.feip.vezdecode.data.CatalogRepository
import co.feip.vezdecode.databinding.FragmentProductsListBinding
import co.feip.vezdecode.getNavigationContainer
import co.feip.vezdecode.presentation.CustomViewModelFactory
import co.feip.vezdecode.presentation.product.details.ProductDetailsFragment
import co.feip.vezdecode.presentation.product.list.adapter.ProductListAdapter
import co.feip.vezdecode.presentation.product.list.adapter.ProductListDecorator

class ProductListFragment : Fragment() {

    companion object {
        private const val EXTRA_CATEGORY_ID = "category_id"
        fun newInstance(categoryId: Int) = ProductListFragment().apply {
            arguments = bundleOf(EXTRA_CATEGORY_ID to categoryId)
        }
    }

    private val categoryId by lazy { requireArguments().getInt(EXTRA_CATEGORY_ID) }

    private val viewModel: ProductListViewModel by lazy {
        CustomViewModelFactory {
            ProductListViewModel(
                CatalogRepository.getInstance(requireContext().applicationContext),
                CartRepository.INSTANCE,
                categoryId
            )
        }.create(ProductListViewModel::class.java)
    }

    private val productsAdapter by lazy {
        ProductListAdapter(
            viewModel::onProductClicked,
            viewModel::onAddToCartClicked,
            viewModel::onIncreaseClicked,
            viewModel::onDecreaseClicked
        )
    }

    private var _binding: FragmentProductsListBinding? = null
    private val binding: FragmentProductsListBinding
        get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding.recyclerView) {
            adapter = productsAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
            addItemDecoration(ProductListDecorator())
        }

        lifecycleScope.launchWhenStarted {
            viewModel.products.collect { productsAdapter.submitList(it) }
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