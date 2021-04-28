package com.mi.imaginatoprac.common.extension

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mi.imaginatoprac.domain.base.UiState
import timber.log.Timber

fun <T> LiveData<T>.safeObserve(owner: LifecycleOwner, observer: (T) -> Unit) {
    observe(owner, { it?.let(observer) ?: Timber.d("Live data value is null") })
}

fun <T> MutableLiveData<UiState<T>>.setSuccess(data: T) = postValue(UiState.Success(data))

fun <T> MutableLiveData<UiState<T>>.setLoading() = postValue(UiState.Loading())

fun <T> MutableLiveData<UiState<T>>.setError(throwable: Throwable) =
    postValue(UiState.Error(throwable))

fun <T> MutableLiveData<UiState<T>>.setApiResponse(apiResponse: T?) {
    // manage other condition if required eg. if user is active or need otp verification
    if (apiResponse != null) {
        setSuccess(apiResponse)
    } else {
        setError(Throwable("Internal Server Error"))
    }
}

fun <T> MutableLiveData<UiState<T>>.isLoading() = value is UiState.Loading<T>

fun <T> LiveData<UiState<T>>.isLoading() = value is UiState.Loading<T>
