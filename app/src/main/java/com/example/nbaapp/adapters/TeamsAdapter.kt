package com.example.nbaapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nbaapp.data.Team
import com.example.nbaapp.databinding.VhTeamBinding

class TeamsAdapter(
    inline val onClick: (String) -> Unit,
) : ListAdapter<Team, TeamsAdapter.TeamViewHolder>(TeamDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder =
        TeamViewHolder(VhTeamBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    inner class TeamViewHolder(private val binding: VhTeamBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(team: Team) = with(binding) {
            binding.team.setOnClickListener { team.id?.let { it1 -> onClick(it1) } }
            teamName.text = team.full_name
            teamConference.text = team.conference
        }
    }
}

class TeamDiffCallback : DiffUtil.ItemCallback<Team>() {

    override fun areItemsTheSame(oldItem: Team, newItem: Team): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Team, newItem: Team): Boolean = oldItem == newItem
}
