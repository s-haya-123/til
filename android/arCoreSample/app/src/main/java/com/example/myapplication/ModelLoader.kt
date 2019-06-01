package com.example.myapplication

import android.net.Uri
import android.util.Log
import com.google.ar.sceneform.rendering.ModelRenderable
import androidx.core.view.accessibility.AccessibilityRecordCompat.setSource
import com.google.ar.core.Anchor
import java.lang.ref.WeakReference


class ModelLoader(private val owner: WeakReference<MainActivity>) {
    private val TAG = "ModelLoader"

    fun loadModel(anchor: Anchor, uri: Uri) {
        if ( owner.get() == null) {
            Log.d(TAG, "Activity is null.  Cannot load model.")
            return
        }
        ModelRenderable.builder()
            .setSource(owner.get(), uri)
            .build()
            .handle({ renderable, throwable ->
                val activity = owner.get()
                if (activity == null) {
                    return@handle null
                } else if (throwable != null) {
                    activity.onException(throwable)
                } else {
                    activity.addNodeToScene(anchor, renderable)
                }
                null
            })

        return
    }
}