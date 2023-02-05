package com.example.nbaapp

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.nbaapp.databinding.FragmentTeamsOrPlayersBinding


class TeamsOrPlayersFragment : Fragment() {

    private var _binding: FragmentTeamsOrPlayersBinding? = null
    private val binding by lazy { _binding!! }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = TransitionInflater.from(requireContext())
        exitTransition = inflater.inflateTransition(R.transition.fade)
        enterTransition = inflater.inflateTransition(R.transition.slide_in)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentTeamsOrPlayersBinding.inflate(inflater)
        
        Glide
            .with(requireContext())
            .load(R.drawable.nba_players)
            .into(binding.playerPick)

        Glide
            .with(requireContext())
            .load(R.drawable.nba_teams)
            .into(binding.teamPick)

        binding.playerPick.setOnClickListener {
            findNavController().navigate(TeamsOrPlayersFragmentDirections.actionTeamsOrPlayersFragmentToPlayersFragment())
        }
        binding.teamPick.setOnClickListener {
            findNavController().navigate(TeamsOrPlayersFragmentDirections.actionTeamsOrPlayersFragmentToChooseTeamFragment())
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigateUp()
            }
        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
