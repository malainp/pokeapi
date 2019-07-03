package mx.malain.pokeapi.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import me.sargunvohra.lib.pokekotlin.model.PokemonMove
import mx.malain.pokeapi.R

class MovesAdapter (context: Context, listener: OnViewHolderClickListener<PokemonMove>)
    : GenericAdapter<PokemonMove>(context, listener){
    override fun createView(context: Context, viewGroup: ViewGroup, viewType: Int): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.layout_list_ability, viewGroup, false)
        return view
    }

    override fun bindView(item: PokemonMove?, position: Int, viewHolder: ViewHolder) {
        if (item !=null){

            val tvAbility = viewHolder.getView(R.id.tvAbilityName) as TextView

            tvAbility.text = item.move.name
        }
    }
}