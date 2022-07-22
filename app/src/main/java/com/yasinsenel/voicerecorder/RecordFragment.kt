package com.yasinsenel.voicerecorder

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Environment
import android.os.SystemClock
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_record.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.jar.Manifest


class RecordFragment : Fragment() {

    private var isRecording : Boolean = false

    lateinit var mr : MediaRecorder

    var path : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_record, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(ContextCompat.checkSelfPermission(view.context,android.Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)
            requestPermissions(arrayOf(android.Manifest.permission.RECORD_AUDIO,android.Manifest.permission.WRITE_EXTERNAL_STORAGE),111)

        list_button.setOnClickListener {

            val action = RecordFragmentDirections.actionRecordFragmentToAudioListFragment()

            Navigation.findNavController(it).navigate(action)
        }

        imageView_record_button.setOnClickListener {




                    if(isRecording){


                        imageView_record_button.setImageDrawable(resources.getDrawable(R.drawable.record_stop))
                        println(isRecording)
                        stopRecording()
                        isRecording = false;
                        println("1")

                }

                else{
                        startRecord()
                        imageView_record_button.setImageDrawable(resources.getDrawable(R.drawable.record_play))
                        println(isRecording)
                        isRecording = true;
                        println("2")
                    }





            }
        }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {


        if (requestCode==111){

            if(grantResults.size>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){



            }

        }



        super.onRequestPermissionsResult(requestCode, permissions, grantResults)


    }

    private fun startRecord(){


        record_timer.base = SystemClock.elapsedRealtime()
        record_timer.start()

        mr = MediaRecorder()

        path = activity?.getExternalFilesDir("/")?.absolutePath

        val formatter = SimpleDateFormat("yyyy_MM_dd_hh_mm_ss", Locale.CANADA)
        val now = Date()

        val recordFile = "Recording..." + formatter.format(now) + ".mp4"



        mr.setAudioSource(MediaRecorder.AudioSource.MIC)
        mr.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
        mr.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB)
        mr.setOutputFile(path+ "/" +recordFile)
        mr.prepare()
        mr.start()

    }

    fun stopRecording(){
        record_timer.stop()
        mr.stop()

    }

    }


