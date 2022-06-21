package com.agesadev.disney.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.agesadev.disney.R
import com.agesadev.disney.domain.model.Character
import com.agesadev.disney.utils.CharacterItemClick
import com.agesadev.disney.utils.Resource
import com.facebook.shimmer.ShimmerFrameLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeCharacterFragment : Fragment(), CharacterItemClick {

    private lateinit var homeCharacterRecyclerView: RecyclerView
    private lateinit var homeCharacterAdapter: CharacterRecyclerAdapter
    private lateinit var homeCharacterFragmentViewModel: HomeCharacterViewModel
    private lateinit var shimmerContainer: ShimmerFrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeCharacterFragmentViewModel = ViewModelProvider(this)[HomeCharacterViewModel::class.java]
        shimmerContainer = view.findViewById(R.id.shimmer_view_container)

        displayCharacters()

    }

    private fun displayCharacters() {
        viewLifecycleOwner.lifecycleScope.launch {
            homeCharacterFragmentViewModel.getCharacters().collect { resources ->
                when (resources) {
                    is Resource.Loading -> {
                        Log.d("HomeCharacterFragment", "Loading")
                    }
                    is Resource.Success -> {
                        Log.d("HomeCharacterFragment", "Success")
                        resources.data?.let { data ->
                            data.collect {
                                homeCharacterAdapter.submitData(it)
                                shimmerContainer.stopShimmer()
                                shimmerContainer.visibility = View.GONE
                            }
                        }
                    }
                    is Resource.Error -> {
                        Log.d("HomeCharacterFragment", "Error")
                        Toast.makeText(context, resources.message, Toast.LENGTH_LONG).show()
                        shimmerContainer.startShimmer()
                        shimmerContainer.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val homeCharacterView =
            inflater.inflate(R.layout.fragment_home_character, container, false)
        setUpRecyclerView(homeCharacterView)

        return homeCharacterView
    }

    private fun setUpRecyclerView(homeCharacterView: View) {
        homeCharacterRecyclerView =
            homeCharacterView.findViewById(R.id.homeCharacterRecyclerView)
        homeCharacterAdapter = CharacterRecyclerAdapter(this)
        homeCharacterRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = homeCharacterAdapter
        }
    }

    override fun clickedCharacter(character: Character) {
        Log.d("Click", "clickedCharacter:clicked ${character.id}")
        val characterId: Bundle = passCharacterUrl(character)
        navigateToCharacterDetails(characterId)

    }

    private fun navigateToCharacterDetails(disneyCharacterUrl: Bundle) {
        Navigation.findNavController(homeCharacterRecyclerView).navigate(
            R.id.action_homeCharacterFragment_to_characterDetailsFragment,
            disneyCharacterUrl
        )
    }

    private fun passCharacterUrl(character: Character): Bundle {
        val disneyCharacterUrl: Bundle = Bundle()
        disneyCharacterUrl.putInt("character_id", character.id)
        return disneyCharacterUrl
    }


    override fun onResume() {
        shimmerContainer.startShimmer()
        super.onResume()

    }

    override fun onPause() {
        shimmerContainer.stopShimmer()
        super.onPause()

    }
}