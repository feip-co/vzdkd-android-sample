package co.feip.vezdecode.presentation.filter

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import co.feip.vezdecode.databinding.DialogFiltersBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class FilterDialogFragment : AppCompatDialogFragment() {

    private var _binding: DialogFiltersBinding? = null
    private val binding: DialogFiltersBinding
        get() = requireNotNull(_binding)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return BottomSheetDialog(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogFiltersBinding.inflate(inflater, container, false)


        return binding.root
    }

}