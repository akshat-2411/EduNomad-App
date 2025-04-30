package com.example.edunomad

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class FirebaseSyllabusAdapter(private val syllabusList: List<CourseTopic>) :
    RecyclerView.Adapter<FirebaseSyllabusAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.syllabusTitle)
        val videoButton: Button = itemView.findViewById(R.id.watchVideoButton)
        val pdfButton: Button = itemView.findViewById(R.id.downloadPdfButton)
        val completedCheckbox: CheckBox = itemView.findViewById(R.id.completedCheckbox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_syllabus_topic, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = syllabusList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = syllabusList[position]

        // Set title and checkbox state
        holder.titleTextView.text = item.title
        holder.completedCheckbox.isChecked = item.isCompleted

        // Set button click listeners for video and PDF
        holder.videoButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.videoUrl))
            holder.itemView.context.startActivity(intent)
        }

        holder.pdfButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.pdfUrl))
            holder.itemView.context.startActivity(intent)
        }

        // Listen for changes in the checkbox state
        holder.completedCheckbox.setOnCheckedChangeListener { _, isChecked ->
            item.isCompleted = isChecked

            val userId = FirebaseAuth.getInstance().currentUser?.uid
            if (userId != null) {
                // Get course title from the context (assuming it's passed in the Intent)
                val courseTitle = (holder.itemView.context as? CourseDetailsActivity)?.intent?.getStringExtra("courseTitle")

                if (courseTitle != null) {
                    val topicRef = FirebaseDatabase.getInstance().reference
                        .child("enrollments")
                        .child(userId)
                        .child(courseTitle)
                        .child("syllabus")
                        .child(item.title)

                    // Update the completion status in Firebase
                    topicRef.child("isCompleted").setValue(isChecked)
                        .addOnSuccessListener {
                            // Optionally show a success message (toast or log)
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(holder.itemView.context, "Error updating completion: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                }
            } else {
                Toast.makeText(holder.itemView.context, "User not logged in", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
