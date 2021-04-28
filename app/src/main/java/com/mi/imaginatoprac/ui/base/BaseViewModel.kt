package com.mi.imaginatoprac.ui.base

import androidx.annotation.CallSuper
import androidx.databinding.Observable
import androidx.databinding.Observable.OnPropertyChangedCallback
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.ViewModel


abstract class BaseViewModel : ViewModel(), Observable {
    private var isFirst = true

    private val callbacks = PropertyChangeRegistry()


    /**
     * Only can call once per lifecycle
     * @param multipleTimes (OPTIONAL) set it to true to make multiple call capability
     */
    @CallSuper
    open fun loadPage(multipleTimes: Boolean = false): Boolean {
        if (isFirst) {
            isFirst = false
            return true
        }

        return isFirst || multipleTimes
    }

    override fun onCleared() {
        super.onCleared()
    }

    override fun addOnPropertyChangedCallback(
        callback: OnPropertyChangedCallback?
    ) {
        callbacks.add(callback)
    }

    override fun removeOnPropertyChangedCallback(
        callback: OnPropertyChangedCallback?
    ) {
        callbacks.remove(callback)
    }

    /**
     * Notifies observers that all properties of this instance have changed.
     */
    open fun notifyChange() {
        callbacks.notifyCallbacks(this, 0, null)
    }

    /**
     * Notifies observers that a specific property has changed. The getter for the
     * property that changes should be marked with the @Bindable annotation to
     * generate a field in the BR class to be used as the fieldId parameter.
     *
     * @param fieldId The generated BR id for the Bindable field.
     */
    open fun notifyPropertyChanged(fieldId: Int) {
        callbacks.notifyCallbacks(this, fieldId, null)
    }
}
