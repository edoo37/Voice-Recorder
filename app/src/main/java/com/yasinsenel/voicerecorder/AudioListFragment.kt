package com.yasinsenel.voicerecorder

import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.fragment_audio_list.*
import kotlinx.android.synthetic.main.player_sheet.*
import java.io.File
import java.lang.Exception


class AudioListFragment : Fragment()  , AudioListAdapter.MyItems {


    lateinit var mr : MediaPlayer

    var isPlaying : Boolean = false

    var fileToPlay : File? = null


    lateinit var seekbarHandler : Handler

    lateinit var updateSeekBar : Runnable



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

      mr = MediaPlayer()


        return inflater.inflate(R.layout.fragment_audio_list, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val path = context?.getExternalFilesDir("/")?.absolutePath


        var directory = File(path)

        var allFiles = directory.listFiles()


        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = AudioListAdapter(allFiles,this)




    }

      override fun passData(file: File, position: Int) {
        file_name_text.text = file.name

        fileToPlay = file

        // onceki sesi durdurma
        mr.stop()

        if(isPlaying){
            stopAudio()

            println("calmiyo")
        }

        else{
            playAudio(fileToPlay!!)

            println("caliyo")

        }
    }

    fun stopAudio(){
        audio_list_playbtn.setImageDrawable(resources.getDrawable(R.drawable.player_play_btn,null))
        println("iyi")
        mr.stop()
        isPlaying = false
    }

    fun playAudio(fileToPlay : File){
        mr = MediaPlayer()


        val bottomSheetBehavior = BottomSheetBehavior.from(include)

        println("naber")
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

        playerHeader.text = "Playing"


        try {
            mr.setDataSource(fileToPlay.absolutePath)
            mr.prepare()
            mr.start()
        }catch (e : Exception){
            e.printStackTrace()
        }

        audio_list_playbtn.setImageDrawable(resources.getDrawable(R.drawable.player_pause_btn,null))
        isPlaying = false

        mr.setOnCompletionListener(object  : MediaPlayer.OnCompletionListener{
            override fun onCompletion(mp: MediaPlayer?) {
                stopAudio()
                playerHeader.text = "Finished"
            }

        })


        playerSeekBar.max = mr.duration
        seekbarHandler = Handler()

        updateSeekBar= Runnable {

            playerSeekBar.progress = mr.currentPosition
            seekbarHandler.postDelayed(updateSeekBar,500)
        }

        seekbarHandler.postDelayed(updateSeekBar,0)
        }


    }

