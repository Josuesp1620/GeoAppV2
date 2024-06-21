package com.geosolution.geolocation.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
 * LiveData extension to observer it in a null safe way
 * */
fun <T : Any> LiveData<T?>.watch(owner: LifecycleOwner, func: (T) -> Unit) {
    this.observe(owner) { result -> result?.apply(func) }
}

/**
 * Observes LiveData for only one time in lifecycle aware way and then removes the observer
 * @receiver LiveData<T?>
 * @param lifecycleOwner LifecycleOwner
 * @param func Function1<T, Unit> will be called upon getting data in observer
 */
fun <T : Any> LiveData<T?>.observeOnce(lifecycleOwner: LifecycleOwner, func: (T) -> Unit) {
    observe(lifecycleOwner, object : Observer<T?> {
        override fun onChanged(value: T?) {
            if (value != null) {
                func(value)
                removeObserver(this)
            }
        }
    })
}

/**
 * Observes LiveData for only one time in and then removes the observer
 * @receiver LiveData<T?>
 * @param func Function1<T, Unit> will be called upon getting data in observer
 */
fun <T : Any> LiveData<T?>.observeOnce(func: (T) -> Unit) {
    observeForever(object : Observer<T?> {
        override fun onChanged(value: T?) {
            if (value != null) {
                func(value)
                removeObserver(this)
            }
        }
    })
}
