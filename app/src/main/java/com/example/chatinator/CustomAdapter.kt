package com.example.chatinator

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class ViewHolder(view : View){
    val view = view
    var projectTextView : TextView? = null
    var companyTextView : TextView? = null
    var userTextView : TextView? = null

    fun decideTypeOfHolder(typeOfHolder : Int){
        when(typeOfHolder)
        {
            1 -> {
                projectTextView = view.findViewById(R.id.projectTextView)
            }
            2 -> {
                companyTextView = view.findViewById(R.id.companyTextView)
            }
            3 ->{
                userTextView = view.findViewById(R.id.collaboratorTextView)
            }
        }
    }
}

class CustomAdapter(context: Context, private val resource: Int, private val arrayOfItems: List<DatabaseElement>, private val typeOfAdapter : Int) : ArrayAdapter<Project>(context,resource) {
    private val inflater = LayoutInflater.from(context)
    private val TAG = "CustomAdapter"

    override fun getCount(): Int {
        return arrayOfItems.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val view : View
        val viewHolder : ViewHolder

        Log.d(TAG,"getView: called")

        if(convertView == null)
        {
            view = inflater.inflate(resource,parent,false)
            viewHolder = ViewHolder(view)
            viewHolder.decideTypeOfHolder(typeOfAdapter)
            view.tag = viewHolder
        }
        else
        {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        val currentItem = arrayOfItems[position]

        Log.d(TAG,"Deciding type of adapter")

        when(typeOfAdapter){
            1 -> {
                viewHolder.projectTextView?.text = currentItem.toString()
            }
            2 ->{
                viewHolder.companyTextView?.text = currentItem.toString()
            }
            3 -> {
                viewHolder.userTextView?.text = currentItem.toString()
            }
        }
        return view
    }
}