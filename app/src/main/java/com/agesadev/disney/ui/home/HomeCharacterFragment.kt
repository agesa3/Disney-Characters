package com.agesadev.disney.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.*
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.agesadev.disney.R
import com.agesadev.disney.databinding.FragmentHomeCharacterBinding
import com.agesadev.disney.domain.model.Character
import com.agesadev.disney.utils.CharacterItemClick
import com.facebook.shimmer.ShimmerFrameLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeCharacterFragment : Fragment(), CharacterItemClick {

    private var _binding: FragmentHomeCharacterBinding? = null
    private val binding get() = _binding

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
        setUpRecyclerView()
        observeAndDisplayCharacters()

    }

//    private fun displayCharacters() {
//        viewLifecycleOwner.lifecycleScope.launch {
//            homeCharacterFragmentViewModel.characterList.observe(
//                viewLifecycleOwner
//            ) {
//
//            }
//        }
//
//    }

    private fun observeAndDisplayCharacters() {
        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeCharacterFragmentViewModel.character.collectLatest { state ->

                    state.data?.let {
                        shimmerContainer.stopShimmer()
                        shimmerContainer.visibility = View.GONE
                        homeCharacterAdapter.submitData(it)
                        Log.d("Home", "Data is : ${homeCharacterAdapter.snapshot()}")
                    }
                    state.isLoading.let {
                        shimmerContainer.visibility = if (it) View.VISIBLE else View.GONE
                        shimmerContainer.stopShimmer()
                    }
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeCharacterBinding.inflate(inflater, container, false)
        return binding?.root
    }

    private fun setUpRecyclerView() {
        homeCharacterAdapter = CharacterRecyclerAdapter(this)
        binding?.homeCharacterRecyclerView?.apply {
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
        super.onResume()
        shimmerContainer.startShimmer()

    }

    override fun onPause() {
        super.onPause()
        shimmerContainer.stopShimmer()

    }


}