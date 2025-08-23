package com.smartmedicinedoctor.app.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.smartmedicinedoctor.app.data.Medicine
import com.smartmedicinedoctor.app.databinding.ItemMedicineBinding

class MedicineAdapter(
    private val onMedicineClick: (Medicine) -> Unit
) : ListAdapter<Medicine, MedicineAdapter.MedicineViewHolder>(MedicineDiffCallback()) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineViewHolder {
        val binding = ItemMedicineBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MedicineViewHolder(binding, onMedicineClick)
    }
    
    override fun onBindViewHolder(holder: MedicineViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    
    class MedicineViewHolder(
        private val binding: ItemMedicineBinding,
        private val onMedicineClick: (Medicine) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        
        fun bind(medicine: Medicine) {
            binding.apply {
                tvMedicineName.text = medicine.name
                tvDosage.text = medicine.dosage
                tvFrequency.text = medicine.frequency
                tvDuration.text = medicine.duration
                tvInstructions.text = medicine.instructions
                
                root.setOnClickListener {
                    onMedicineClick(medicine)
                }
            }
        }
    }
    
    private class MedicineDiffCallback : DiffUtil.ItemCallback<Medicine>() {
        override fun areItemsTheSame(oldItem: Medicine, newItem: Medicine): Boolean {
            return oldItem.id == newItem.id
        }
        
        override fun areContentsTheSame(oldItem: Medicine, newItem: Medicine): Boolean {
            return oldItem == newItem
        }
    }
}