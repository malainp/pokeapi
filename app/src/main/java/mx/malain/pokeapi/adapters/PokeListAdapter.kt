package mx.malain.pokeapi.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import me.sargunvohra.lib.pokekotlin.model.NamedApiResource
import mx.malain.pokeapi.R

class PokeListAdapter(context: Context, listener: OnViewHolderClickListener<NamedApiResource>) :
    GenericAdapter<NamedApiResource>(context, listener) {

    override fun createView(context: Context, viewGroup: ViewGroup, viewType: Int): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.layout_pokemon_list, viewGroup, false)
        return view
    }

    override fun bindView(item: NamedApiResource?, position: Int, viewHolder: ViewHolder) {
        if (item != null) {
            val ivPokemon = viewHolder.getView(R.id.ivPokemon) as ImageView
            val tvNombre = viewHolder.getView(R.id.tvPokemonName) as TextView

            tvNombre.text = item.name

            //HARDCODE ROCKS!
            //La verdad no pero hace el truco :(
            val x = position + 1
            Glide.with(context)
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$x.png")
                .into(ivPokemon)
        }
    }
}