package com.davinci42.androidutils

import android.app.Activity
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.TextView
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * Created by DaVinci42 on 2017/9/20.
 */
class CommandExecutor(private val activity: Activity, private val command: String, private val customObserver: Observer<String>? = null, private val requireRoot: Boolean = false) {

    fun execute() {

        val observer = customObserver ?: DefaultObserver(activity)
        try {
            val processBuilder = if (!requireRoot) ProcessBuilder(command) else ProcessBuilder("su", "-c", command)
            Observable.defer({
                OutputObservableSource(processBuilder)
            })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer)

        } catch (e: Exception) {
            e.printStackTrace()
            observer.onError(e)
        }
    }

    private class DefaultObserver(activity: Activity) : Observer<String> {

        val view: View = activity.window.decorView.findViewById<View>(android.R.id.content)
        private val snackBar = Snackbar.make(view, "", Snackbar.LENGTH_SHORT)
        private val stringBuilder = StringBuilder()

        override fun onSubscribe(d: Disposable) {
            val textView = snackBar.view.findViewById<TextView>(android.support.design.R.id.snackbar_text)
            textView.maxLines = Int.MAX_VALUE
        }

        override fun onNext(t: String) {

            if (!stringBuilder.isEmpty()) {
                stringBuilder.append("\n")
            }
            stringBuilder.append(t)
            snackBar.setText(stringBuilder.toString())
                    .setDuration(Snackbar.LENGTH_INDEFINITE)
                    .show()
        }

        override fun onComplete() {
            snackBar.setDuration(Snackbar.LENGTH_LONG)
                    .show()
        }

        override fun onError(e: Throwable) {
            snackBar.setText(e.message ?: "Shit Happens!")
                    .setDuration(Snackbar.LENGTH_LONG)
                    .show()
        }
    }

    private class OutputObservableSource(val processBuilder: ProcessBuilder) : ObservableSource<String> {

        override fun subscribe(observer: Observer<in String>) {

            var inputReader: BufferedReader? = null
            var errorReader: BufferedReader? = null
            try {
                val process = processBuilder.start()
                inputReader = BufferedReader(InputStreamReader(process.inputStream))
                errorReader = BufferedReader(InputStreamReader(process.errorStream))

                if (errorReader.ready()) {
                    errorReader.useLines { it.forEach { observer.onNext(it) } }
                } else {
                    inputReader.useLines { it.forEach { observer.onNext(it) } }
                }
                observer.onComplete()

            } catch (e: Exception) {
                e.printStackTrace()
                observer.onError(e)
            } finally {
                try {
                    inputReader?.close()
                    errorReader?.close()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}