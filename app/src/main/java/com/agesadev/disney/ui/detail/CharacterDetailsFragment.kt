package com.agesadev.disney.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.agesadev.disney.R
import com.agesadev.disney.domain.model.Character
import com.agesadev.disney.ui.home.HomeCharacterViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.appbar.MaterialToolbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterDetailsFragment : Fragment() {

    private lateinit var characterDetailToolbar: MaterialToolbar
    private lateinit var detailsViewModel: CharacterDetailsViewModel
    private var characterId: Int? = null
    private lateinit var characterNameDeail: TextView
    private lateinit var moviesList: TextView
    private lateinit var gamesList: TextView
    private lateinit var characterImageDetail: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailsViewModel = ViewModelProvider(this).get(CharacterDetailsViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val characterDetailView =
            inflater.inflate(R.layout.fragment_character_details, container, false)
        setUpViews(characterDetailView)
        getCharacterDetails()

        return characterDetailView
    }

    private fun getCharacterDetails() {
        val bundle = arguments
        if (bundle != null) {
            characterId = bundle.getInt("character_id")
            detailsViewModel.character().observe(viewLifecycleOwner) { character ->
                val movieFilms = character.films?.joinToString(" , ")
                val videoGames = character.videoGames?.joinToString(" , ")
                characterNameDeail.text = character.name

                displayCharacterImage(character)
                displayMovieFilms(movieFilms)
                displayVideoGames(videoGames)

            }
            characterId?.let { detailsViewModel.getSingleCharacter(it) }

        } else {
            Toast.makeText(context, "Error Retrieving Data", Toast.LENGTH_SHORT).show()
        }
    }

    private fun displayCharacterImage(character: Character) {
        Glide.with(this)
            .load(character.imageUrl)
            .placeholder(R.drawable.disney_placeholder)
            .error(R.drawable.disney_placeholder)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(characterImageDetail)
    }

    private fun setUpViews(characterDetailView: View) {
        characterDetailToolbar = characterDetailView.findViewById(R.id.characterDetailToolbar)
        characterNameDeail = characterDetailView.findViewById(R.id.characterNameDeail)
        moviesList = characterDetailView.findViewById(R.id.moviesList)
        gamesList = characterDetailView.findViewById(R.id.gamesList)
        characterImageDetail = characterDetailView.findViewById(R.id.characterImageDetail)
    }

    private fun displayVideoGames(videoGames: String?) {
        if (videoGames.isNullOrEmpty()) {
            gamesList.text = "No Video Games"
        } else {
            gamesList.text = videoGames.split(",").joinToString("\n") {
                "- $it"
            }
        }
    }


    private fun displayMovieFilms(movieFilms: String?) {
        if (movieFilms.isNullOrEmpty()) {
            moviesList.text = "No TV Shows"
        } else {
            moviesList.text = movieFilms.split(",").joinToString("\n") {
                "- $it"
            }
        }
    }


}