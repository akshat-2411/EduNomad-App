package com.example.edunomad

import android.widget.Filter
import android.widget.Filterable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class CourseAdapter(
    private val allCourses: List<Course>,
    private val onItemClick: (Course) -> Unit
) : RecyclerView.Adapter<CourseAdapter.CourseViewHolder>(), Filterable {

    private var filteredCourses: List<Course> = allCourses

    inner class CourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val courseTitle: TextView = itemView.findViewById(R.id.courseTitle)
        private val courseImage: ImageView = itemView.findViewById(R.id.courseImage)

        fun bind(course: Course) {
            courseTitle.text = course.title
            courseImage.setImageResource(course.imageResId)

            itemView.setOnClickListener {
                onItemClick(course)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_course, parent, false)
        return CourseViewHolder(view)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        holder.bind(filteredCourses[position])
    }

    override fun getItemCount(): Int = filteredCourses.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val query = constraint?.toString()?.lowercase(Locale.ROOT)?.trim()
                val results = FilterResults()

                results.values = if (query.isNullOrEmpty()) {
                    allCourses
                } else {
                    allCourses.filter {
                        it.title.lowercase(Locale.ROOT).contains(query)
                    }
                }

                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredCourses = results?.values as List<Course>
                notifyDataSetChanged()
            }
        }
    }
}
