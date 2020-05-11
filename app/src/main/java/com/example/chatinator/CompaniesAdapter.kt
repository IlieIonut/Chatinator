package com.example.chatinator

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import org.w3c.dom.Text

class ViewHolderCompanies(v : View){
    val textView : TextView = v.findViewById(R.id.companyTextView)
}

class CompaniesAdapter(context: Context, private val resource: Int, private val companies: List<Company>) : ArrayAdapter<Company>(context,resource) {
    private val inflater = LayoutInflater.from(context)

    override fun getCount(): Int {
        return companies.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val view : View
        val viewHolder : ViewHolderCompanies


        ///ecran initial
        if(convertView == null)
        {
            view = inflater.inflate(resource,parent,false)
            viewHolder = ViewHolderCompanies(view)
            view.tag = viewHolder
        }

        ///scroll
        else
        {
            view = convertView
            viewHolder = view.tag as ViewHolderCompanies
        }

        val currentCompany = companies[position]

        viewHolder.textView.text = currentCompany.toString()

        return view
    }
}