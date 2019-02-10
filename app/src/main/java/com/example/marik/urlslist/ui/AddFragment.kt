package com.example.marik.urlslist.ui

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.marik.urlslist.Injector
import com.example.marik.urlslist.databinding.AddFragmentBinding
import com.example.marik.urlslist.model.ItemUrl
import kotlinx.android.synthetic.main.add_fragment.*

class AddFragment : BottomSheetDialogFragment() {
    private lateinit var binding: AddFragmentBinding

    private val mBottomSheetBehaviorCallback = object : BottomSheetBehavior.BottomSheetCallback() {

        override fun onStateChanged(@NonNull bottomSheet: View, newState: Int) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss()
            }
        }

        override fun onSlide(@NonNull bottomSheet: View, slideOffset: Float) {}
    }


    companion object {
        fun newInstance() = AddFragment()
    }

    private lateinit var viewModel: UrlsListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddFragmentBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
       binding.imageAdd.setOnClickListener { onPlusClick() }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel =
            ViewModelProviders.of(this, Injector.provideViewModelFactory(requireContext())).get(
                UrlsListViewModel::class.java)
    }

    // add url and dismiss
    private fun onPlusClick() {
        viewModel.addUrl(ItemUrl(query()))
        dismiss()
    }

    private fun query(): String {
        val inputString =binding.urlTextInput.toString()
        val prefix = "http://"
        return if (inputString.contains(prefix, true)) inputString else prefix + inputString
    }
}
