package co.feip.vezdecode.presentation.product.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import co.feip.vezdecode.ResourceManager
import co.feip.vezdecode.data.CatalogRepository
import co.feip.vezdecode.databinding.FragmentProductDetailsBinding
import co.feip.vezdecode.getNavigationContainer
import co.feip.vezdecode.presentation.CustomViewModelFactory
import co.feip.vezdecode.presentation.product.details.adapter.ProductDetailsAdapter

class ProductDetailsFragment : Fragment() {

    companion object {
        private const val EXTRA_PRODUCT_ID = "product_id"
        fun newInstance(productId: Int) = ProductDetailsFragment().apply {
            arguments = bundleOf(EXTRA_PRODUCT_ID to productId)
        }
    }

    private val productId by lazy { requireArguments().getInt(EXTRA_PRODUCT_ID) }

    private val viewModel by lazy {
        CustomViewModelFactory {
            ProductDetailsViewModel(
                CatalogRepository.getInstance(requireContext().applicationContext),
                ProductDetailsMapper(ResourceManager(requireContext().applicationContext)),
                productId
            )
        }.create(ProductDetailsViewModel::class.java)
    }

    private var _binding: FragmentProductDetailsBinding? = null
    private val binding: FragmentProductDetailsBinding
        get() = requireNotNull(_binding)

    private val detailsAdapter by lazy { ProductDetailsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = detailsAdapter
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }

        binding.btnBack.setOnClickListener {
            getNavigationContainer().getFlowFragmentManager().popBackStack()
        }

        lifecycleScope.launchWhenStarted {
            viewModel.product.collect { detailsAdapter.submitList(it) }
        }
    }
}