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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.agesadev.disney.R
import com.agesadev.disney.domain.model.Character
import com.agesadev.disney.utils.CharacterItemClick
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeCharacterFragment : Fragment(), CharacterItemClick {

    private lateinit var homeCharacterRecyclerView: RecyclerView
    private val disneyCharacters = arrayListOf<Character>()
    private lateinit var homeCharacterAdapter: CharacterRecyclerAdapter
    private lateinit var homeCharacterFragmentViewModel: HomeCharacterViewModel
    private var mJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mJob?.cancel()
        homeCharacterFragmentViewModel =
            ViewModelProvider(this).get(HomeCharacterViewModel::class.java)

        mJob = viewLifecycleOwner.lifecycleScope.launch {

            homeCharacterFragmentViewModel.getCharacters().collect { resources ->
                resources.data?.let {
                    resources.data?.let { data ->
                        data.collect {
                            homeCharacterAdapter.submitData(it)
                        }
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
        val homeCharacterView = inflater.inflate(R.layout.fragment_home_character, container, false)
        homeCharacterRecyclerView = homeCharacterView.findViewById(R.id.homeCharacterRecyclerView)
        homeCharacterAdapter = CharacterRecyclerAdapter(this)
        homeCharacterRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = homeCharacterAdapter
        }

        return homeCharacterView
    }

    override fun clickedCharacter(character: Character) {
        Toast.makeText(requireContext(), "I was clicked", Toast.LENGTH_SHORT).show()
        Log.d("Click", "clickedCharacter:clicked")
    }

}