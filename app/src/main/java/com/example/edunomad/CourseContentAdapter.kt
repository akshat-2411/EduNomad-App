package com.example.edunomad
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.FirebaseStorage
import java.io.File
class CourseContentAdapter(private val topicList: List<CourseTopic>) :
    RecyclerView.Adapter<CourseContentAdapter.TopicViewHolder>() {

    inner class TopicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.syllabusTitle)
        val video: TextView = itemView.findViewById(R.id.watchVideoButton)
        val pdf: TextView = itemView.findViewById(R.id.downloadPdfButton)
        val checkbox: CheckBox = itemView.findViewById(R.id.completedCheckbox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_course_topic, parent, false)
        return TopicViewHolder(view)
    }

    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) {
        val topic = topicList[position]
        holder.title.text = topic.title
        holder.video.setOnClickListener {
            val intent = Intent(holder.itemView.context, VideoPlayerActivity::class.java)
            intent.putExtra("videoUrl", topic.videoUrl)
            holder.itemView.context.startActivity(intent)
        }

        // pdf
        holder.pdf.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, PdfViewerActivity::class.java)
            intent.putExtra("pdfUrl", topic.pdfUrl)
            context.startActivity(intent)
        }

        val quizButton = holder.itemView.findViewById<Button>(R.id.takeQuizButton)
        quizButton.setOnClickListener {
            val intent = Intent(holder.itemView.context, MainActivity3::class.java)
            holder.itemView.context.startActivity(intent)
        }
        holder.checkbox.isChecked = topic.isCompleted

    }

    override fun getItemCount(): Int = topicList.size
}
