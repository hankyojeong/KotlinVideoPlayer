package io.github.superbderrick.kotlinvideoplayer.VideoPlayer

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.widget.SeekBar
import io.github.superbderrick.kotlinvideoplayer.R

class VideoPlayerActivity : AppCompatActivity(), View.OnClickListener, SurfaceHolder.Callback, SeekBar.OnSeekBarChangeListener{

    private lateinit var mSurfaceView: SurfaceView
    private lateinit var mSurfaceHolder: SurfaceHolder
    private lateinit var mVideoController: VideoController
    private lateinit var mSeekbarVideo: SeekBar
    private var mUserSelectedPosition: Int = 0
    private var mUserIsSeeking: Boolean = false

    private val mVideoPath: String = "https://bitdash-a.akamaihd.net/content/MI201109210084_1/m3u8s/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.m3u8"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_playback)

        setUIComponents();

        mSurfaceView.setOnClickListener(this)
        mSeekbarVideo.setOnSeekBarChangeListener(this)

        mSurfaceHolder = mSurfaceView.holder
        mSurfaceHolder.addCallback(this)
    }

    private fun setUIComponents(){
        mSurfaceView = findViewById(R.id.surfaceview)
        mSeekbarVideo = findViewById(R.id.seekbar_video)
    }

    override fun surfaceCreated(surfaceHolder: SurfaceHolder?) {
        mVideoController = VideoController()
        mVideoController.openVideo(mVideoPath, surfaceHolder)
        mSeekbarVideo.max = mVideoController.durationVideo()
    }

    override fun surfaceChanged(p0: SurfaceHolder?, p1: Int, p2: Int, p3: Int) {

    }

    override fun surfaceDestroyed(p0: SurfaceHolder?) {

    }

    /*
    Seekbar callback
     */
    override fun onStartTrackingTouch(p0: SeekBar?) {
        mUserIsSeeking = true
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        if(fromUser){
            mUserSelectedPosition = progress;
        }
    }

    override fun onStopTrackingTouch(p0: SeekBar?) {
        mUserIsSeeking = false
        mVideoController.seekVideo(mUserSelectedPosition)
    }
    //Seekbar callback

    override fun onClick(view: View?) {
        if(view != null){
            when(view.id){
                R.id.surfaceview -> {
                    mVideoController.handleVideo()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mVideoController.releaseVideo()
    }

    override fun onStop() {
        super.onStop()
        mVideoController.pauseVideo()
    }
}