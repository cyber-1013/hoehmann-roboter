package com.hoehmann.salesbot.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hoehmann.salesbot.data.ServiceItem
import com.hoehmann.salesbot.databinding.ItemServiceBinding

class ServiceAdapter(
    private val services: List<ServiceItem>,
    private val onClick: (ServiceItem) -> Unit
) : RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder>() {

    inner class ServiceViewHolder(private val binding: ItemServiceBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(service: ServiceItem) {
            binding.textIcon.text = service.icon
            binding.textTitle.text = service.title
            binding.textSubtitle.text = service.subtitle
            binding.root.setOnClickListener { onClick(service) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        val binding = ItemServiceBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ServiceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        holder.bind(services[position])
    }

    override fun getItemCount(): Int = services.size
}
