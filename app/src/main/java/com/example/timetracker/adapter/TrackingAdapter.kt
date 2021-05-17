package com.example.timetracker.adapter

import android.content.Context
import android.os.Handler
import android.os.SystemClock
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Chronometer
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.timetracker.R


class TrackingAdapter (var mcontext: Context): RecyclerView.Adapter<TrackingAdapter.TrackingHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackingHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.raw_time_list, parent, false)
        return TrackingHolder(
            v,
            parent.context
        )

    }

    override fun getItemCount() = 5

    override fun onBindViewHolder(holder: TrackingHolder, position: Int) {
//        holder.setIsRecyclable(false)
        holder.bindItems()
    }

    class TrackingHolder(v: View, val mcontext: Context) : RecyclerView.ViewHolder(v) {
        var isWorking = false
        var timeInSeconds: Int = 0
        var str_time :String = ""
        var edt_time = itemView.findViewById<AppCompatEditText>(R.id.edt_time)
        var chronometer = itemView.findViewById<Chronometer>(R.id.choronmeter)
        var tv_timer = itemView.findViewById<AppCompatTextView>(R.id.tv_timer)


        fun bindItems() {
            if (str_time.equals("")){
            chronometer.setText(mcontext.getString(R.string.timer))}
            else{
                chronometer.setText(str_time)
            }

            edt_time.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                     if (s.toString() != ""){
                       timeInSeconds = s.toString().toIntOrNull()!!
                        val hours = timeInSeconds / 3600
                        val secondsLeft = timeInSeconds - hours * 3600
                        val minutes = secondsLeft / 60
                        val seconds = secondsLeft - minutes * 60
                        var formattedTime = ""
                        if (hours < 10) formattedTime += "0"
                        formattedTime += "$hours:"
                        if (minutes < 10) formattedTime += "0"
                        formattedTime += "$minutes:"
                        if (seconds < 10) formattedTime += "0"
                        formattedTime += seconds
                        chronometer.setText(formattedTime)
                    }else{
                         chronometer.stop()
                         tv_timer.text = mcontext.getString(R.string.start)
                         chronometer.setText(mcontext.getString(R.string.timer))
                        Toast.makeText(
                                mcontext,
                                mcontext.getString(R.string.please_enter_timer) ,
                                Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }
            })


            tv_timer.setOnClickListener(View.OnClickListener {
                    if (!edt_time.text.toString().equals("")){
                     if (!isWorking) {
                        isWorking = true
                        var stoppedMilliseconds = 0
                        val chronoText: String = chronometer.getText().toString()
                        val array =
                            chronoText.split(":").toTypedArray()
                        if (array.size == 2) {
                            stoppedMilliseconds = (array[0].toInt() * 60 * 1000
                                    + array[1].toInt() * 1000)
                        } else if (array.size == 3) {
                            stoppedMilliseconds =
                                array[0].toInt() * 60 * 60 * 1000 + array[1].toInt() * 60 * 1000 + array[2].toInt() * 1000

                        }

                        chronometer.base = SystemClock.elapsedRealtime() - stoppedMilliseconds
                        chronometer.setOnChronometerTickListener(object :
                            Chronometer.OnChronometerTickListener {
                            override fun onChronometerTick(cArg: Chronometer) {
                                val hours =
                                    ((SystemClock.elapsedRealtime() - chronometer.getBase()).toInt() / 3600000)
                                val minutes =
                                    ((SystemClock.elapsedRealtime() - chronometer.getBase()).toInt() - hours * 3600000) as Int / 60000
                                val seconds =
                                    ((SystemClock.elapsedRealtime() - chronometer.getBase()).toInt() - hours * 3600000 - minutes * 60000) as Int / 1000
                                val t =
                                    (if (hours < 10) "0$hours" else hours).toString() + ":" + (if (minutes < 10) "0$minutes" else minutes) + ":" + if (seconds < 10) "0$seconds" else seconds
                                cArg.text = t
                                str_time = t

                            }
                        })
                        chronometer.start()
                        tv_timer.text = mcontext.getString(R.string.pause)

                    } else {
                         chronometer.stop()
                        isWorking = false
                        tv_timer.text = mcontext.getString(R.string.resume)
                        }
                    }
                    else{
                        Toast.makeText(
                                mcontext,
                                mcontext.getString(R.string.please_enter_timer)  ,
                                Toast.LENGTH_SHORT
                        ).show()

                    }
            })
        }
    }
}