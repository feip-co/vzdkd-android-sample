package co.feip.vezdecode.presentation.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import co.feip.vezdecode.R
import co.feip.vezdecode.data.CartRepository
import co.feip.vezdecode.data.CatalogRepository
import co.feip.vezdecode.databinding.FragmentCartBinding
import co.feip.vezdecode.getNavigationContainer
import co.feip.vezdecode.presentation.CustomViewModelFactory
import co.feip.vezdecode.presentation.cart.adapter.CartAdapter
import kotlinx.coroutines.flow.collect

class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding: FragmentCartBinding
        get() = requireNotNull(_binding)

    private val cartAdapter by lazy { CartAdapter() }

    private val viewModel by lazy {
        CustomViewModelFactory {
            CartViewModel(
                CartRepository.INSTANCE,
                CatalogRepository.getInstance(requireContext().applicationContext)
            )
        }.create(CartViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            recyclerView.adapter = cartAdapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )

            toolbar.setNavigationOnClickListener {
                getNavigationContainer().getFlowFragmentManager().popBackStack()
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.cartItems.collect { cartAdapter.submitList(it) }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.totalPrice.collect {
                binding.btnPlaceOrder.text = getString(R.string.action_place_order_for, it)
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.showEmptyState.collect { show ->

            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}