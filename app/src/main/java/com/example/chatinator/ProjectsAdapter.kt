package com.example.chatinator

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class ViewHolder(v : View){
    val textView : TextView = v.findViewById(R.id.projectTextView)
}

class ProjectsAdapter(context: Context, private val resource: Int, private val projects: List<Project>) : ArrayAdapter<Project>(context,resource) {
    private val inflater = LayoutInflater.from(context)

    override fun getCount(): Int {
        return projects.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val view : View
        val viewHolder : ViewHolder

        if(convertView == null)
        {
            view = inflater.inflate(resource,parent,false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        }
        else
        {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        val currentProject = projects[position]

        viewHolder.textView.text = currentProject.toString()

        return view
    }
}