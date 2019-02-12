package com.example.marik.urlslist.ui

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import com.example.marik.urlslist.Injector
import com.example.marik.urlslist.R
import com.example.marik.urlslist.model.ItemUrl

class AddFragment : BottomSheetDialogFragment() {

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
    private lateinit var imgPlus: ImageView
    private lateinit var inputText: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.add_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        inputText = view.findViewById(R.id.url_text_input)
        imgPlus = view.findViewById(R.id.image_add)
        imgPlus.setOnClickListener { onPlusClick() }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel =
            ViewModelProviders.of(this, Injector.provideViewModelFactory(requireContext())).get(
                UrlsListViewModel::class.java
            )
    }

    // add url and dismiss
    private fun onPlusClick() {
        viewModel.addUrl(ItemUrl(query()))
        dismiss()
    }

    private fun query(): String {
        val inputString = inputText.text.toString()
        val prefix = "http://"
        return if (inputString.contains(prefix, true)) inputString else prefix + inputString
    }
}
