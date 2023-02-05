package com.example.nbaapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nbaapp.data.Player
import com.example.nbaapp.databinding.VhPlayerBinding

class PlayerAdapter(
    inline val onClick: (String) -> Unit
) : ListAdapter<Player, PlayerAdapter.PlayerViewHolder>(PlayerDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder =
        PlayerViewHolder(VhPlayerBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class PlayerViewHolder(private val binding: VhPlayerBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(player: Player) = with(binding) {
            this.player.setOnClickListener { player.id?.let { it1 -> onClick(it1) } }
            playerName.text = "${player.first_name} ${player.last_name} (${player.position})"
            playerTeam.text = player.team.name
        }
    }
}

private class PlayerDiffCallback : DiffUtil.ItemCallback<Player>() {

    override fun areItemsTheSame(oldItem: Player, newItem: Player): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Player, newItem: Player): Boolean = oldItem == newItem
}
