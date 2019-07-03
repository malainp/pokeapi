package mx.malain.pokeapi.activities

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import me.sargunvohra.lib.pokekotlin.client.PokeApiClient
import me.sargunvohra.lib.pokekotlin.model.NamedApiResource
import me.sargunvohra.lib.pokekotlin.model.NamedApiResourceList
import mx.malain.pokeapi.R
import mx.malain.pokeapi.adapters.GenericAdapter
import mx.malain.pokeapi.adapters.PokeListAdapter
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class MainActivity : AppCompatActivity() {

    var adapter: PokeListAdapter? = null

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.elevation = 0.0f
        adapter = PokeListAdapter(
            this,
            object : GenericAdapter.OnViewHolderClickListener<NamedApiResource> {
                override fun onClick(view: View, position: Int, item: NamedApiResource?) {
                    Toast.makeText(this@MainActivity, item?.name, Toast.LENGTH_LONG).show()
                    val intent = Intent(this@MainActivity, PokemonActivity::class.java)
                    intent.putExtra(PokemonActivity.EXTRA_ID_POKEMON, item?.id)
                    startActivity(intent)
                }
            })
        rvPokemons.adapter = adapter
        rvPokemons.layoutManager = LinearLayoutManager(this)

        PokedexTask().execute()

    }

    inner class PokedexTask : AsyncTask<Void, Void, NamedApiResourceList>() {
        override fun doInBackground(vararg params: Void?): NamedApiResourceList {
            val pokeApi = PokeApiClient()
            val pokemons = pokeApi.getPokemonList(0, 10)
            return pokemons
        }

        override fun onPostExecute(result: NamedApiResourceList?) {
            super.onPostExecute(result)
            lyCargando.visibility = View.GONE
            rvPokemons.visibility = View.VISIBLE
            adapter?.addAll(result?.results ?: ArrayList())
        }
    }
}
