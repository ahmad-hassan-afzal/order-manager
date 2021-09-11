package com.example.restaurantorderapp.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.restaurantorderapp.LoginActivity
import com.example.restaurantorderapp.R

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)

        val anim_up: Animation =
                AnimationUtils.loadAnimation(root.context, R.anim.slide_up)
        root.findViewById<Button>(R.id.btn_new_order).startAnimation(anim_up)
        root.findViewById<Button>(R.id.btn_new_product).startAnimation(anim_up)
        root.findViewById<Button>(R.id.btnLogout).startAnimation(anim_up)

        val anim_down = AnimationUtils.loadAnimation(root.context, R.anim.slide_down)
        root.findViewById<ImageView>(R.id.imageView3).startAnimation(anim_down)
        root.findViewById<TextView>(R.id.txt_name).startAnimation(anim_down)
        root.findViewById<TextView>(R.id.txt_username).startAnimation(anim_down)
        root.findViewById<TextView>(R.id.txt_10).startAnimation(anim_down)
        root.findViewById<TextView>(R.id.txt_4).startAnimation(anim_down)
        root.findViewById<TextView>(R.id.txt_completed).startAnimation(anim_down)
        root.findViewById<TextView>(R.id.txt_pending).startAnimation(anim_down)
        root.findViewById<ImageView>(R.id.order_type_thumb2).startAnimation(anim_down)
        root.findViewById<View>(R.id.divider3).startAnimation(anim_down)
        root.findViewById<TextView>(R.id.txt_username3).startAnimation(anim_down)

        return root
    }
}