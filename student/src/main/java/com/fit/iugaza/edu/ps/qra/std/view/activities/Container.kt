package com.fit.iugaza.edu.ps.qra.std.view.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fit.iugaza.edu.ps.qra.constants.Constants
import com.fit.iugaza.edu.ps.qra.std.databinding.ActivityContainerBinding
import com.fit.iugaza.edu.ps.qra.std.view.fragments.Courses
import com.fit.iugaza.edu.ps.qra.std.view.fragments.Profile
import com.fit.iugaza.edu.ps.qra.std.view.fragments.Settings

class Container : AppCompatActivity() {
    private var _binding: ActivityContainerBinding? = null
    private val binding get() = _binding!!
    private val _fragmentKey = "FRAGMENT"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityContainerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val fragmentkey = savedInstanceState?.getInt(_fragmentKey)
        if (fragmentkey != null) {
            Constants().orientationTransaction(
                this,
                fragmentkey,
                binding.container.id,
                Courses(),
                Settings(),
                Profile()
            )
        }else{
            supportFragmentManager.beginTransaction().replace(binding.container.id, Courses()).commit()
        }
        Constants().statusBarColor(this)
        Constants().mainBottomNavigation(
            binding.bottomNav,
            courses = Courses(),
            settings = Settings(),
            profile = Profile(),
            this,
            binding.container.id,
            binding.title
        )

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(_fragmentKey, binding.bottomNav.selectedItemId)
    }

}