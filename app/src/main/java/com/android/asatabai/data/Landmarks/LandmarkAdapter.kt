package com.android.asatabai.data.Landmarks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.asatabai.R
class LandmarkAdapter(

    private val landmarks: List<Landmark>,
    private val onItemClick: (Landmark) -> Unit
) : RecyclerView.Adapter<LandmarkAdapter.LandmarkViewHolder>() {

    inner class LandmarkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameText: TextView = itemView.findViewById(R.id.landmarkName)
        private val shortDescText: TextView = itemView.findViewById(R.id.landmarkShortDesc)
        private val photo: ImageView = itemView.findViewById(R.id.landmarkImage)
        private val checkbox: CheckBox = itemView.findViewById(R.id.landmarkCheckBox)
        private val viewMapButton: Button = itemView.findViewById(R.id.viewMapButton)

        fun bind(landmark: Landmark) {
            nameText.text = landmark.name
            shortDescText.text = landmark.shortDescription
            photo.setImageResource(landmark.photo)

            // Set listener for the "View Map" button
            viewMapButton.setOnClickListener {
                onItemClick(landmark)
            }

            // Checkbox state handling (for now it does nothing)
            checkbox.setOnCheckedChangeListener { _, isChecked ->
                // You can save the state of the checkbox here, but for now, do nothing
                if (isChecked) {
                    // Handle checked state if needed
                } else {
                    // Handle unchecked state if needed
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LandmarkViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_landmarks, parent, false)
        return LandmarkViewHolder(view)
    }

    override fun onBindViewHolder(holder: LandmarkViewHolder, position: Int) {
        holder.bind(landmarks[position])
    }

    override fun getItemCount(): Int = landmarks.size
}
