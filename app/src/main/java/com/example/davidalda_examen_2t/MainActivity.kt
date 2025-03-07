package com.example.davidalda_examen_2t

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.davidalda_examen_2t.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Repositorio de GitHub del proyecto:

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://peticiones.online/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun fetchProducts() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val apiService = getRetrofit().create(ProductAPI::class.java)
                val response = apiService.getProducts()

                runOnUiThread {
                    displayData(response.results)

                    val recyclerView = findViewById<RecyclerView>(R.id.rvProducts)
                    recyclerView.adapter = ProductAdapter(response.results)
                    recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                }
            } catch (e: Exception) {
                runOnUiThread {
                    showError("Error al cargar los productos: ${e.localizedMessage}")
                }
            }
        }
    }

    private fun displayData(products: List<Product>) {
        binding.rvProducts.adapter = ProductAdapter(products)
        binding.rvProducts.layoutManager = LinearLayoutManager(this)
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fetchProducts()
    }
}
