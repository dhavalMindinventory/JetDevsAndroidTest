package com.mi.imaginatoprac.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

abstract class BaseFragment<T : ViewDataBinding>(@LayoutRes val layoutRes: Int) : Fragment() {

    protected abstract fun getClassName(): String

    lateinit var binding: T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    @CallSuper
    protected open fun initViews() {
    }

    internal fun onSupportNavigateUp() = onBackPressed()

    private fun getParentBaseFragment(): BaseFragment<*>? {
        var fragment = parentFragment
        while (fragment != null) {
            if (fragment is BaseFragment<*>) {
                return fragment
            } else {
                fragment = fragment.parentFragment
            }
        }
        return fragment
    }

    protected open fun onBackPressed(): Boolean {
        val canPopBack =
            getParentBaseFragment()?.onBackPressed() ?: findNavController().popBackStack()
        return if (!canPopBack) {
            activity?.finish()
            true
        } else {
            true
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        if (::binding.isInitialized) {
            binding.unbind()
        }
    }

}
