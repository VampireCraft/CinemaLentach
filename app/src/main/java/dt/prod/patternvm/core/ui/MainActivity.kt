package dt.prod.patternvm.core.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import dt.prod.patternvm.R
import dt.prod.patternvm.cinemaView.ui.FragmentCinemaView
import dt.prod.patternvm.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        openCinemaViewFragment()
    }

    private fun openCinemaViewFragment() {
        supportFragmentManager
            .beginTransaction()
            .add(binding.root.id, FragmentCinemaView())
            .commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.fragments.size > 0)
            supportFragmentManager.popBackStack()
        else
            super.onBackPressed()
    }
}