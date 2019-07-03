package mx.malain.pokeapi.activities

import android.content.Context
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_pokemon.*
import me.sargunvohra.lib.pokekotlin.client.PokeApiClient
import me.sargunvohra.lib.pokekotlin.model.Pokemon
import me.sargunvohra.lib.pokekotlin.model.PokemonAbility
import me.sargunvohra.lib.pokekotlin.model.PokemonMove
import me.sargunvohra.lib.pokekotlin.model.PokemonStat
import mx.malain.pokeapi.R
import mx.malain.pokeapi.adapters.AbilityAdapter
import mx.malain.pokeapi.adapters.GenericAdapter
import mx.malain.pokeapi.adapters.MovesAdapter
import mx.malain.pokeapi.adapters.StatsAdapter
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import java.util.*
import kotlin.collections.ArrayList

class PokemonActivity : AppCompatActivity() {

    lateinit var pokemon: Pokemon
    val pokeApi = PokeApiClient()
    var actual = 0

    var sprites = ArrayList<String>()
    lateinit var abilitiesAdapter: AbilityAdapter
    lateinit var movesAdapter: MovesAdapter
    lateinit var statsAdapter: StatsAdapter

    companion object {
        val EXTRA_ID_POKEMON = "POKEID"
    }

    inner class PokemonTask : AsyncTask<Int, Void, Pokemon?>() {
        override fun doInBackground(vararg p0: Int?): Pokemon? {
            try {
                pokemon = pokeApi.getPokemon(p0.first() ?: 0)

                if (!pokemon.sprites.frontDefault.isNullOrEmpty())
                    sprites.add(pokemon.sprites.frontDefault ?: "")

                if (!pokemon.sprites.backDefault.isNullOrEmpty())
                    sprites.add(pokemon.sprites.backDefault ?: "")

                if (!pokemon.sprites.frontShiny.isNullOrEmpty())
                    sprites.add(pokemon.sprites.frontShiny ?: "")

                if (!pokemon.sprites.backShiny.isNullOrEmpty())
                    sprites.add(pokemon.sprites.backShiny ?: "")

                if (!pokemon.sprites.frontFemale.isNullOrEmpty())
                    sprites.add(pokemon.sprites.frontFemale ?: "")

                if (!pokemon.sprites.backFemale.isNullOrEmpty())
                    sprites.add(pokemon.sprites.backFemale ?: "")

                if (!pokemon.sprites.frontShinyFemale.isNullOrEmpty())
                    sprites.add(pokemon.sprites.frontShinyFemale ?: "")

                if (!pokemon.sprites.backShinyFemale.isNullOrEmpty())
                    sprites.add(pokemon.sprites.backShinyFemale ?: "")
                return pokemon
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
            return null
        }

        override fun onPostExecute(result: Pokemon?) {
            super.onPostExecute(result)
            //
            if (result != null)
                initViews()
            else finish()
        }
    }

    private inner class SliderTimer : TimerTask() {
        override fun run() {

                this@PokemonActivity.runOnUiThread {
                    try {
                        Glide.with(this@PokemonActivity)
                            .load(sprites[actual++])
                            .into(ivPokemon)
                        if (actual >= sprites.size)
                            actual = 0
                    }catch (ex:Exception){
                        ex.printStackTrace()
                    }
                }
        }
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon)
        PokemonTask().execute(intent.extras.getInt(EXTRA_ID_POKEMON))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.elevation = 0.0f
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun initViews() {
        lySinDatos.visibility = View.GONE
        ly.visibility = View.VISIBLE
        val timer = Timer()
        timer.scheduleAtFixedRate(SliderTimer(), 2000, 4000)
        Glide.with(this)
            .load(pokemon.sprites.frontDefault)
            .into(ivPokemon)
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            supportActionBar?.title = pokemon.name
        }, 1000)


        abilitiesAdapter = AbilityAdapter(
            this,
            object : GenericAdapter.OnViewHolderClickListener<PokemonAbility> {
                override fun onClick(view: View, position: Int, item: PokemonAbility?) {

                }
            })

        movesAdapter = MovesAdapter(
            this,
            object : GenericAdapter.OnViewHolderClickListener<PokemonMove> {
                override fun onClick(view: View, position: Int, item: PokemonMove?) {

                }
            })

        statsAdapter = StatsAdapter(
            this,
            object : GenericAdapter.OnViewHolderClickListener<PokemonStat> {
                override fun onClick(view: View, position: Int, item: PokemonStat?) {

                }
            })

        abilitiesAdapter.addAll(pokemon.abilities)
        movesAdapter.addAll(pokemon.moves)
        statsAdapter.addAll(pokemon.stats)

        rvAbilities.adapter = abilitiesAdapter
        rvAbilities.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        rvMovimientos.adapter = movesAdapter
        rvMovimientos.layoutManager =  StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)// LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        rvStats.adapter = statsAdapter
        rvStats.layoutManager =LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        tvExperiencia.text = "Experiencia " + pokemon.baseExperience.toString()
        tvAltura.text = "Altura " + pokemon.height.toString()
        tvOrden.text = "No. " + pokemon.order.toString()
        tvPeso.text = "Peso " + pokemon.weight.toString()
    }
}
