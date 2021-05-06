package com.example.bluewagon.utils.extentions

import android.app.Activity
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.andrognito.flashbar.Flashbar
import com.example.bluewagon.R

fun ProgressBar.show(){
    visibility= View.VISIBLE
}

fun ProgressBar.hide(){
    visibility= View.INVISIBLE
}

fun TextView.showText(){
    visibility= View.VISIBLE
}

fun TextView.hideText() {
    visibility = View.INVISIBLE
}

fun Fragment.successFlashBar(message:String): Flashbar {

    return Flashbar.Builder(activity!!)
        .gravity(Flashbar.Gravity.TOP)
        .message(message)
        .backgroundColor(resources.getColor(R.color.success))
        .duration(2000)


        .titleSizeInPx(12f)
        .build()
}

fun Fragment.infoFlashBar(message:String): Flashbar {

    return Flashbar.Builder(activity!!)
        .gravity(Flashbar.Gravity.TOP)
        .message(message)
        .backgroundColor(resources.getColor(R.color.info))
        .duration(2000)
        .titleSizeInPx(12f)


        .build()
}

fun Fragment.warningFlashBar(message:String): Flashbar {

    return Flashbar.Builder(activity!!)
        .gravity(Flashbar.Gravity.TOP)
        .message(message)
        .backgroundColor(resources.getColor(R.color.warning))
        .duration(2000)
        .titleSizeInPx(12f)
        .build()
}

fun Fragment.dangerFlashBar(message:String): Flashbar {

    return Flashbar.Builder(activity!!)
        .gravity(Flashbar.Gravity.TOP)
        .message(message)
        .backgroundColor(resources.getColor(R.color.danger))
        .duration(2000)
        .titleSizeInPx(12f)
        .build()
}

fun Activity.successFlashBar(message:String): Flashbar {

    return Flashbar.Builder(this)
        .gravity(Flashbar.Gravity.TOP)
        .message(message)
        .backgroundColor(resources.getColor(R.color.success))
        .duration(2000)
        .titleSizeInPx(12f)
        .build()
}

fun Activity.infoFlashBar(message:String): Flashbar {

    return Flashbar.Builder(this)
        .gravity(Flashbar.Gravity.TOP)
        .message(message)
        .backgroundColor(resources.getColor(R.color.info))
        .duration(2000)
        .titleSizeInPx(12f)
        .build()
}

fun Activity.warningFlashBar(message:String): Flashbar {

    return Flashbar.Builder(this)
        .gravity(Flashbar.Gravity.TOP)
        .message(message)
        .backgroundColor(resources.getColor(R.color.warning))
        .duration(2000)
        .titleSizeInPx(12f)
        .build()
}

fun Activity.dangerFlashBar(message:String): Flashbar {

    return Flashbar.Builder(this)
        .gravity(Flashbar.Gravity.TOP)
        .message(message)
        .backgroundColor(resources.getColor(R.color.danger))
        .duration(2000)
        .titleSizeInPx(12f)
        .build()
}