package com.example.statelayout

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.statelayout.databinding.ActivityMainBinding
import com.rounx.statelayout.State

/**
 * @author Perry Lance
 * @since 2010-10-17 Created
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_content -> binding.stateLayout.setState(State.CONTENT)
            R.id.action_loading -> binding.stateLayout.setState(State.LOADING)
            R.id.action_info -> {
                binding.stateLayout.apply {
                    setState(State.INFO)
                    title("Failed to load")
                    message("Network connection failed, please try again later")
                    button {
                        Toast.makeText(this@MainActivity, "Retry", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            R.id.action_empty -> binding.stateLayout.setState(State.EMPTY)
        }
        return super.onOptionsItemSelected(item)
    }
}