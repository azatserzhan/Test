package kz.home.filemanager.common.base

import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import androidx.annotation.LayoutRes
import androidx.annotation.StyleRes
import androidx.fragment.app.Fragment

abstract class BaseFragment(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId) {
    override fun onGetLayoutInflater(savedInstanceState: Bundle?): LayoutInflater =
        super.onGetLayoutInflater(savedInstanceState).run {
            getOverrideThemeResId()?.let { cloneInContext(ContextThemeWrapper(context, it)) } ?: this
        }

    @StyleRes
    protected open fun getOverrideThemeResId(): Int? = null
}