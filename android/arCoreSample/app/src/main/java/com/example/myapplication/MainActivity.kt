package com.example.myapplication

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.FragmentManager

import kotlinx.android.synthetic.main.activity_main.*
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.core.TrackingState
import com.google.ar.core.Trackable
import com.google.ar.core.HitResult
import com.google.ar.core.Plane
import androidx.core.view.MenuItemCompat.setContentDescription
import android.net.Uri
import android.os.Environment
import android.os.Handler
import android.os.HandlerThread
import android.view.PixelCopy
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import kotlinx.android.synthetic.main.content_main.*
import java.lang.ref.WeakReference
import com.google.ar.sceneform.ux.TransformableNode
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.core.Anchor
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.animation.ModelAnimator
import com.google.ar.sceneform.rendering.AnimationData
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    lateinit private var fragment: ArFragment
    private val pointer = PointerDrawable()
    private var isTracking: Boolean = false
    private var isHitting: Boolean = false
    lateinit private var modelLoader: ModelLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        modelLoader = ModelLoader(WeakReference(this))
        fragment = supportFragmentManager.findFragmentById(R.id.sceneform_fragment) as ArFragment

        fab.setOnClickListener { view ->
            takePhoto()
        }
        fragment.arSceneView.scene.addOnUpdateListener { frameTime ->
            fragment.onUpdate(frameTime)
            onUpdate()
        }
        initializeGallery()
    }
    private fun takePhoto() {
        val filename = generateFilename();
        val view = fragment.getArSceneView();
        val bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),
            Bitmap.Config.ARGB_8888)
        val handlerThread = HandlerThread("PixelCopier");
        handlerThread.start()
        PixelCopy.request(view, bitmap, {copyResult ->
            if (copyResult == PixelCopy.SUCCESS) {
                saveBitmapToDisk(bitmap, filename)
                val snackbar = Snackbar.make(findViewById(android.R.id.content),
                "Photo saved", Snackbar.LENGTH_LONG)
                snackbar.setAction("Open in Photos"){
                    val photoFile = File(filename);

                    val photoURI = FileProvider.getUriForFile(this,
                    this.getPackageName() + ".ar.codelab.name.provider",
                    photoFile);
                    val intent = Intent(Intent.ACTION_VIEW, photoURI);
                    intent.setDataAndType(photoURI, "image/*");
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivity(intent)
                }
                snackbar.show()
            }else {
                val toast = Toast.makeText(this,
                "Failed to copyPixels: " + copyResult, Toast.LENGTH_LONG);
                toast.show();
            }
            handlerThread.quitSafely();

        }, Handler(handlerThread.getLooper()))
    }
    private fun generateFilename(): String {
        val date = SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(Date())
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + File.separator + "Sceneform/" + date + "_screenshot.jpg"
    }
    private fun saveBitmapToDisk(bitmap: Bitmap, filename: String) {
        val out = File(filename)
        if (!out.getParentFile().exists()) {
            out.getParentFile().mkdirs();
        }
        ByteArrayOutputStream().use { outputData->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputData)
            FileOutputStream(filename).use {outputStream ->
                outputData.writeTo(outputStream)
                outputStream.flush()
                outputStream.close()
            }
        }
    }

    fun addNodeToScene(anchor: Anchor, renderable: ModelRenderable) {
        val anchorNode = AnchorNode(anchor)
        val node = TransformableNode(fragment.transformationSystem)
        node.renderable = renderable
        node.setParent(anchorNode)
        fragment.arSceneView.scene.addChild(anchorNode)
        node.select()
        startAnimation(node, renderable)
    }

    fun onException(throwable: Throwable) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(throwable.message)
            .setTitle("Codelab error!")
        val dialog = builder.create()
        dialog.show()
        return
    }

    fun startAnimation(node: TransformableNode, renderable: ModelRenderable?) {
        if (renderable == null || renderable.animationDataCount == 0) {
            return
        }
        for (i in 0 until renderable.animationDataCount) {
            val animationData = renderable.getAnimationData(i)
        }
        val animator = ModelAnimator(renderable.getAnimationData(0), renderable)
        animator.start()
        node.setOnTapListener{ hitTestResult, motionEvent ->
            togglePauseAndResume(animator)
        }
    }

    fun togglePauseAndResume(animator: ModelAnimator) {
        if (animator.isPaused) {
            animator.resume()
        } else if (animator.isStarted) {
            animator.pause()
        } else {
            animator.start()
        }
    }

    private fun onUpdate() {
        val trackingChanged = updateTracking()
        val contentView = findViewById<View>(android.R.id.content)
        if (trackingChanged) {
            if (isTracking) {
                contentView.getOverlay().add(pointer)
            } else {
                contentView.getOverlay().remove(pointer)
            }
            contentView.invalidate()
        }

        if (isTracking) {
            val hitTestChanged = updateHitTest()
            if (hitTestChanged) {
                pointer.enabled = isHitting
                contentView.invalidate()
            }
        }
    }

    private fun updateTracking(): Boolean {
        val frame = fragment.arSceneView.arFrame
        val wasTracking = isTracking
        isTracking = frame != null && frame.camera.trackingState === TrackingState.TRACKING
        return isTracking !== wasTracking
    }

    private fun updateHitTest(): Boolean {
        val frame = fragment.arSceneView.arFrame
        val pt = getScreenCenter()
        val hits: List<HitResult>
        val wasHitting = isHitting
        isHitting = false
        if (frame != null) {
            hits = frame.hitTest(pt.x.toFloat(), pt.y.toFloat())
            for (hit in hits) {
                val trackable = hit.trackable
                if (trackable is Plane && trackable.isPoseInPolygon(hit.hitPose)) {
                    isHitting = true
                    break
                }
            }
        }
        return wasHitting != isHitting
    }

    private fun getScreenCenter(): android.graphics.Point {
        val vw = findViewById<View>(android.R.id.content)
        return android.graphics.Point(vw.width / 2, vw.height / 2)
    }

    private fun initializeGallery() {
        val gallery = findViewById<LinearLayout>(R.id.gallery_layout)

        val andy = ImageView(this)
        andy.setImageResource(R.drawable.droid_thumb)
        andy.setContentDescription("andy")
        andy.setOnClickListener({ view -> addObject(Uri.parse("andy_dance.sfb")) })
        gallery.addView(andy)

        val cabin = ImageView(this)
        cabin.setImageResource(R.drawable.cabin_thumb)
        cabin.setContentDescription("cabin")
        cabin.setOnClickListener({ view -> addObject(Uri.parse("Cabin.sfb")) })
        gallery.addView(cabin)

        val house = ImageView(this)
        house.setImageResource(R.drawable.house_thumb)
        house.setContentDescription("house")
        house.setOnClickListener({ view -> addObject(Uri.parse("House.sfb")) })
        gallery.addView(house)

        val igloo = ImageView(this)
        igloo.setImageResource(R.drawable.igloo_thumb)
        igloo.setContentDescription("igloo")
        igloo.setOnClickListener({ view -> addObject(Uri.parse("igloo.sfb")) })
        gallery.addView(igloo)
    }

    private fun addObject(model: Uri) {
        val frame = fragment.arSceneView.arFrame
        val pt = getScreenCenter()
        val hits: List<HitResult>
        if (frame != null) {
            hits = frame.hitTest(pt.x.toFloat(), pt.y.toFloat())
            for (hit in hits) {
                val trackable = hit.trackable
                if (trackable is Plane && trackable.isPoseInPolygon(hit.hitPose)) {
                    modelLoader?.loadModel(hit.createAnchor(), model)
                    break

                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
