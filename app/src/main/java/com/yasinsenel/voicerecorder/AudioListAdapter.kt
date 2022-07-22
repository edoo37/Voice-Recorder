package com.yasinsenel.voicerecorder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.player_sheet.view.*
import kotlinx.android.synthetic.main.recycler_row.view.*
import java.io.File




class AudioListAdapter(val files : Array<File>, val callBackInterface : MyItems) : RecyclerView.Adapter<AudioListAdapter.AudioList>(){



    val timeAgo = TimeAgo()
    class AudioList(view : View) : RecyclerView.ViewHolder(view) {



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AudioList {
        val layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.recycler_row,parent,false)

        return AudioList(layoutInflater)
    }

    override fun onBindViewHolder(holder: AudioList, position: Int) {

        holder.itemView.folder_name.text = files[position].name
        holder.itemView.list_date.text = timeAgo.getTimeAgo(files[position].lastModified())

        holder.itemView.setOnClickListener{
            callBackInterface.passData(files[holder.adapterPosition],holder.adapterPosition)
        }




    }

    override fun getItemCount(): Int {

      return files.size
        println(files.size)
    }


    interface MyItems{
        fun passData(file : File, position: Int)

    }



}