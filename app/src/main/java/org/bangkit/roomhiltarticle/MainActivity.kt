package org.bangkit.roomhiltarticle

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.bangkit.roomhiltarticle.data.model.DicodingEventModel
import org.bangkit.roomhiltarticle.databinding.ActivityMainBinding
import kotlin.random.Random

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvEvents.layoutManager = LinearLayoutManager(this)
        val adapter = DicodingEventAdapter()
        binding.rvEvents.adapter = adapter

        binding.btnAddEvent.setOnClickListener {
            addEvent()
        }

        lifecycleScope.launch {
            viewModel.eventList.collectLatest { eventList ->
                setEventList(eventList, adapter)
            }
        }

        lifecycleScope.launch {
            viewModel.isLoading.collectLatest { isLoading ->
                showLoading(isLoading)
            }
        }

        lifecycleScope.launch {
            viewModel.errorMessages.collectLatest { error ->
                showErrorSnackbar(error, binding.root)
            }
        }
    }

    private fun addEvent() {
        val dicodingEventModel = DicodingEventModel(
            id = Random.nextInt(1000, 9999),
            name = "Dicoding Event",
            mediaCover = "https://dicoding-web-img.sgp1.cdn.digitaloceanspaces.com/original/event/dos-offline_idcamp_connect_roadshow_solo_mc_091024131113.jpg"
        )
        viewModel.addEvent(dicodingEventModel)
    }

    private fun setEventList(eventList : List<DicodingEventModel>, adapter: DicodingEventAdapter) {
        adapter.submitList(eventList)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showErrorSnackbar(message: String, view: View) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
    }
}