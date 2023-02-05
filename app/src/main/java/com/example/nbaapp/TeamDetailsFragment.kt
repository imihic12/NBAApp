package com.example.nbaapp

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.nbaapp.api.Endpoints
import com.example.nbaapp.api.ServiceBuilder
import com.example.nbaapp.data.Team
import com.example.nbaapp.databinding.FragmentTeamDetailsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TeamDetailsFragment : Fragment() {

    private var _binding: FragmentTeamDetailsBinding? = null
    private val binding by lazy { _binding!! }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = TransitionInflater.from(requireContext())
        exitTransition = inflater.inflateTransition(R.transition.fade)
        enterTransition = inflater.inflateTransition(R.transition.slide_in)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentTeamDetailsBinding.inflate(inflater)

        val args = navArgs<TeamDetailsFragmentArgs>()
        val request = ServiceBuilder.buildService(Endpoints::class.java)
        request.specificTeam(args.value.teamId).enqueue(specificTeamCallback)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigateUp()
            }
        })

        binding.backButton.setOnClickListener { findNavController().navigateUp() }

        return binding.root
    }

    private fun setupUI(team: Team) = with(binding) {
        teamName.text = team.full_name + " (${team.abbreviation})"
        teamCity.text = "City: ${team.city}"
        teamDivision.text = "Division: ${team.division}"
        teamConference.text = "Conferece: ${team.conference}"
    }

    private val specificTeamCallback = object : Callback<Team> {
        override fun onResponse(call: Call<Team>, response: Response<Team>) {
            response.body()?.let { setupUI(it) }
        }

        override fun onFailure(call: Call<Team>, t: Throwable) {
            Toast.makeText(requireContext(), "An error occurred please try again!", Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
