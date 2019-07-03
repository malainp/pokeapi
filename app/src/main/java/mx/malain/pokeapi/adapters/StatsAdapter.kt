package mx.malain.pokeapi.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import me.sargunvohra.lib.pokekotlin.model.PokemonStat
import mx.malain.pokeapi.R

class StatsAdapter(context: Context, listener: OnViewHolderClickListener<PokemonStat>) :
    GenericAdapter<PokemonStat>(context, listener) {
    override fun createView(context: Context, viewGroup: ViewGroup, viewType: Int): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.layout_stat, viewGroup, false)
        return view
    }

    override fun bindView(item: PokemonStat?, position: Int, viewHolder: ViewHolder) {
        if (item != null) {
            val tvStatName = viewHolder.getView(R.id.tvStatName) as TextView
            val tvStatBaseValue = viewHolder.getView(R.id.tvStatBaseValue) as TextView
            val tvStatEffortValue = viewHolder.getView(R.id.tvStatEffortValue) as TextView

            tvStatName.text = item.stat.name

            tvStatBaseValue.text = item.baseStat.toString()
            tvStatEffortValue.text = item.effort.toString()

        }
    }
}