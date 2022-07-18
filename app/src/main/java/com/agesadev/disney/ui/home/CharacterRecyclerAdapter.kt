package com.agesadev.disney.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.agesadev.disney.R
import com.agesadev.disney.domain.model.Character
import com.agesadev.disney.utils.CharacterItemClick
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade

class CharacterRecyclerAdapter(private val characterItemClickListener: CharacterItemClick) :
    PagingDataAdapter<Character, CharacterRecyclerAdapter.CharacterViewHolder>(
        characterDifferCallback
    ) {

    companion object {
        private val characterDifferCallback = object : DiffUtil.ItemCallback<Character>() {
            override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = getItem(position)
        character?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_character_card, parent, false)
        return CharacterViewHolder(view)
    }

    inner class CharacterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        private val characterImage: ImageView = itemView.findViewById(R.id.characterImageHere)
        private val characterName: TextView = itemView.findViewById(R.id.characterNameText)


        fun bind(character: Character) {
            characterName.text = character.name
            Glide.with(itemView.context)
                .load(character.imageUrl)
                .placeholder(R.drawable.disney_placeholder)
                .error(R.drawable.disney_placeholder)
                .transition(withCrossFade())
                .into(characterImage)
        }

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            getItem(absoluteAdapterPosition)?.let {
                characterItemClickListener.clickedCharacter(it)
            }
        }


    }

}





