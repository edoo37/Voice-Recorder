package com.yasinsenel.voicerecorder

import java.util.*
import java.util.concurrent.TimeUnit

class TimeAgo {

    fun  getTimeAgo(duration : Long) : String {

        val now = Date()

        var seconds = TimeUnit.MILLISECONDS.toSeconds(now.time-duration)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(now.time-duration)
        val hours = TimeUnit.MILLISECONDS.toHours(now.time-duration)
        val days = TimeUnit.MILLISECONDS.toDays(now.time-duration)

        if(seconds<60){

            return "just now"
        }
        else if(minutes==1L){

            return "a minute ago"
        }

        else if(minutes>1 && minutes <24){

            return "$minutes minutes ago"
        }

        else if(hours==1L){

            return "a hour ago"
        }
        else if(hours > 1 && hours < 24){

            return "$hours hours ago"
        }
        else if(days==1L){

            return "a day ago"
        }
        else{

            return "$days days ago"
        }

    }

}


