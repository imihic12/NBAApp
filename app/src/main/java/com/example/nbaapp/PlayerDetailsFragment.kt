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
import com.example.nbaapp.data.Player
import com.example.nbaapp.databinding.FragmentPlayerDetailsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlayerDetailsFragment : Fragment() {

    private var _binding: FragmentPlayerDetailsBinding? = null
    private val binding by lazy { _binding!! }

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
        _binding = FragmentPlayerDetailsBinding.inflate(inflater)

        val args = navArgs<PlayerDetailsFragmentArgs>()
        val request = ServiceBuilder.buildService(Endpoints::class.java)
        request.specificPlayer(args.value.playerId).enqueue(specificPlayerCallback)

        binding.backButton.setOnClickListener { findNavController().navigateUp() }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigateUp()
            }
        })

        return binding.root
    }

    private val specificPlayerCallback = object : Callback<Player> {
        override fun onResponse(call: Call<Player>, response: Response<Player>) {
            response.body()?.let { setUpUI(it) }
        }

        override fun onFailure(call: Call<Player>, t: Throwable) {
            Toast.makeText(requireContext(), "An error occurred please try again!", Toast.LENGTH_LONG).show()
        }
    }

    fun setUpUI(player: Player) = with(binding) {
        playerName.text = player.first_name + " " + player.last_name
        playerTeam.text = "Team: ${player.team.name}"
        playerHeight.text = "Height: ${player.height_feet}'${player.height_inches}''"
        playerWeight.text = "Weight: ${player.weight_pounds} lbs"
        playerPosition.text = "Position: ${player.position}"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
