package com.kaankaplan.recapproject.adapters

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import android.widget.Toast
import com.kaankaplan.recapproject.R

class ExpandableListAdapter(var listGroup: ArrayList<String>, var listChild: HashMap<String, ArrayList<String>>) : BaseExpandableListAdapter() {

    override fun getGroupCount(): Int {
        return listGroup.size
    }

    override fun getChildrenCount(p0: Int): Int {
        return listChild[listGroup[p0]]!!.size
    }

    override fun getGroup(p0: Int): Any {
        return listGroup[p0]
    }

    override fun getChild(p0: Int, p1: Int): Any {
        return listChild[listGroup[p0]]!!.get(p1)
    }

    override fun getGroupId(p0: Int): Long {
        return 0
    }

    override fun getChildId(p0: Int, p1: Int): Long {
        return 0
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(p0: Int, p1: Boolean, view: View?, viewGroup: ViewGroup?): View {

        val view = LayoutInflater.from(viewGroup!!.context).inflate(android.R.layout.simple_expandable_list_item_1, viewGroup, false)

        val textView = view.findViewById<TextView>(android.R.id.text1)
        textView.setTypeface(null, Typeface.BOLD)
        textView.textSize = 20F
        val stringGroup = getGroup(p0).toString()
        textView.text = stringGroup

        return view
    }

    override fun getChildView(p0: Int, p1: Int, p2: Boolean, p3: View?, viewGroup: ViewGroup?): View {
        val view = LayoutInflater.from(viewGroup!!.context).inflate(android.R.layout.simple_selectable_list_item, viewGroup ,false)

        val textView = view.findViewById<TextView>(android.R.id.text1)
        val stringChild = getChild(p0, p1).toString()

        textView.text = stringChild

        return view
    }

    override fun isChildSelectable(p0: Int, p1: Int): Boolean {
        return true
    }
}