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
import com.example.nbaapp.adapters.PlayerAdapter
import com.example.nbaapp.data.Player
import com.example.nbaapp.data.PlayerResponse
import com.example.nbaapp.databinding.FragmentPlayersBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlayersFragment : Fragment() {

    private var _binding: FragmentPlayersBinding? = null
    private val binding by lazy { _binding!! }
    private lateinit var adapter: PlayerAdapter
    private var players = emptyList<Player>()

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
        _binding = FragmentPlayersBinding.inflate(inflater)

        val request = ServiceBuilder.buildService(Endpoints::class.java)

        adapter = PlayerAdapter { playerId ->
            findNavController().navigate(PlayersFragmentDirections.actionPlayersFragmentToPlayerDetailsFragment(playerId))
        }
        binding.players.adapter = adapter
        binding.players.layoutManager = LinearLayoutManager(requireContext())


        binding.playerSearch.doOnTextChanged { text, _, _, _ ->
            if (text?.isBlank() == true) {
                adapter.submitList(players)
            } else {
                request.searchPlayers(text.toString().lowercase().trim()).enqueue(searchCallback)
            }
        }

        request.players().enqueue(object : Callback<PlayerResponse> {
            override fun onResponse(call: Call<PlayerResponse>, response: Response<PlayerResponse>) {
                players = response.body()?.data ?: emptyList()
                adapter.submitList(players)
            }

            override fun onFailure(call: Call<PlayerResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "An error occurred please try again!", Toast.LENGTH_LONG).show()
            }
        })

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigateUp()
            }
        })

        return binding.root
    }

    private val searchCallback = object : Callback<PlayerResponse> {
        override fun onResponse(call: Call<PlayerResponse>, response: Response<PlayerResponse>) {
            adapter.submitList(response.body()?.data)
        }

        override fun onFailure(call: Call<PlayerResponse>, t: Throwable) {
            Toast.makeText(requireContext(), "An error occurred please try again!", Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
