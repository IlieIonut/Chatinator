package com.example.chatinator

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import org.w3c.dom.Text

class ProjectsAdapter(context: Context, private val resource: Int, private val projects: List<Project>) : ArrayAdapter<Project>(context,resource) {
    private val inflater = LayoutInflater.from(context)

    override fun getCount(): Int {
        return projects.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = inflater.inflate(resource,parent,false)

        val textView: TextView = view.findViewById(R.id.projectTextView)

        val currentProject = projects[position]

        textView.text = currentProject.toString()

        return view
    }
}