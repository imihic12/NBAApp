package com.example.nbaapp

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nbaapp.api.Endpoints
import com.example.nbaapp.api.ServiceBuilder
import com.example.nbaapp.adapters.TeamsAdapter
import com.example.nbaapp.data.Team
import com.example.nbaapp.data.TeamResponse
import com.example.nbaapp.databinding.FragmentChooseTeamBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChooseTeamFragment : Fragment() {

    private var _binding: FragmentChooseTeamBinding? = null
    private val binding by lazy { _binding!! }
    private lateinit var adapter: TeamsAdapter
    private var teams = emptyList<Team>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = TransitionInflater.from(requireContext())
        exitTransition = inflater.inflateTransition(R.transition.fade)
        enterTransition = inflater.inflateTransition(R.transition.slide_in)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChooseTeamBinding.inflate(inflater)
        adapter = TeamsAdapter { teamId ->
            findNavController().navigate(ChooseTeamFragmentDirections.actionChooseTeamFragmentToTeamDetailsFragment(teamId))
        }
        binding.teams.adapter = adapter
        binding.teams.layoutManager = LinearLayoutManager(requireContext())

        binding.teamsSearch.doOnTextChanged { text, _, _, _ ->
            if (text?.isBlank() == true) {
                adapter.submitList(teams)
            } else {
                val tempTeams = teams.filter { team -> team.full_name?.lowercase()?.contains(text.toString().lowercase()) == true }
                adapter.submitList(tempTeams)
            }
        }

        val request = ServiceBuilder.buildService(Endpoints::class.java)
        request.teams().enqueue(object : Callback<TeamResponse> {
            override fun onResponse(call: Call<TeamResponse>, response: Response<TeamResponse>) {
                teams = response.body()?.data ?: emptyList()
                adapter.submitList(teams)
            }

            override fun onFailure(call: Call<TeamResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "An error occured please try again!", Toast.LENGTH_LONG).show()
            }
        })

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
