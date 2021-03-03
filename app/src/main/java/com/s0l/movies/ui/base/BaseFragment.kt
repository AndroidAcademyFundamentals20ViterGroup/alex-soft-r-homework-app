package com.s0l.movies.ui.base

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope

open class BaseFragment:Fragment() {

    fun launchOnLifecycleScope(execute: suspend () -> Unit) {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            execute()
        }
    }
}