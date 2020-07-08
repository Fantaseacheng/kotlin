package com.zengcheng.getyourshittogether.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.zengcheng.getyourshittogether.R
import com.zengcheng.getyourshittogether.ui.CollectionActivity
import com.zengcheng.getyourshittogether.ui.entry.LoginActivity
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        profileViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_profile, container, false)
        profileViewModel.text.observe(viewLifecycleOwner, Observer {
            profile_username.text = activity?.intent?.getStringExtra("name")
            collection.setOnClickListener {
                var intent = Intent(this.requireContext(), CollectionActivity::class.java)
                startActivity(intent)
            }
            button_logout.setOnClickListener {
                val intent1 = Intent(this.requireContext(),LoginActivity::class.java)
                startActivity(intent1)
                activity?.finish()
            }
        })
        return root
    }
}
