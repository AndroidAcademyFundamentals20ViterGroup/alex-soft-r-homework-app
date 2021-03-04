package com.s0l.movies.utils

import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.view.Window
import android.view.WindowManager
import androidx.annotation.AttrRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.use
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.platform.MaterialArcMotion
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import com.s0l.movies.R

/**
 * This method converts dp unit to equivalent pixels, depending on device density.
 *
 * @param dp      A value in dp (density independent pixels) unit. Which we need to convert into pixels
 * @param context Context to get resources and device specific display metrics
 * @return A float value to represent px equivalent to dp depending on device density
 */
fun Context.convertDpToPixel(dp: Float): Float {
    val metrics = this.resources.displayMetrics
    return dp * (metrics.densityDpi / 160f)
}

/**
 * This method converts device specific pixels to density independent pixels.
 *
 * @param px      A value in px (pixels) unit. Which we need to convert into db
 * @param context Context to get resources and device specific display metrics
 * @return A float value to represent dp equivalent to px value
 */
fun Context.convertPixelsToDp(px: Float): Float {
    val metrics = this.resources.displayMetrics
    return px / (metrics.densityDpi / 160f)
}

fun Context.getScreenHeight(): Int {
    val wm = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val display = wm.defaultDisplay
    val size = Point()
    display.getSize(size)
    return size.y
}

fun Context.getScreenWidth(): Int {
    val wm = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val display = wm.defaultDisplay
    val size = Point()
    display.getSize(size)
    return size.x
}

fun Fragment.showMessage(message: String) {
    view?.let {  Snackbar.make(it, message, Snackbar.LENGTH_LONG).show() }
}

fun Context.themeColor(
    @AttrRes themeAttrId: Int
): Int {
    return obtainStyledAttributes(
        intArrayOf(themeAttrId)
    ).use {
        it.getColor(0, Color.MAGENTA)
    }
}

/** get a material container arc transform. */
internal fun getContentTransform(context: Context): MaterialContainerTransform {
    return MaterialContainerTransform().apply {
        addTarget(android.R.id.content)
        duration = 450
        pathMotion = MaterialArcMotion()
        isElevationShadowEnabled = true
        startElevation = 9f
        endElevation = 9f
        startContainerColor = ContextCompat.getColor(context, R.color.black_500)
    }
}

/** apply material exit container transformation. */
fun AppCompatActivity.applyExitMaterialTransform() {
    window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
    setExitSharedElementCallback(MaterialContainerTransformSharedElementCallback())
    window.sharedElementsUseOverlay = false
}

/** apply material entered container transformation. */
fun AppCompatActivity.applyMaterialTransform(transitionName: String?) {
    window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
    ViewCompat.setTransitionName(findViewById(android.R.id.content), transitionName)

    // set up shared element transition
    setEnterSharedElementCallback(MaterialContainerTransformSharedElementCallback())
    window.sharedElementEnterTransition = getContentTransform(this)
    window.sharedElementReturnTransition = getContentTransform(this)
}