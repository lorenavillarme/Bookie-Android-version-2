package com.example.bookie.ui.activity.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.bookie.ui.activity.fragments.ReseniaFragmentAjena
import com.example.bookie.ui.activity.fragments.favoritos.FavoritosFragmentAjenos
import com.example.bookie.ui.activity.fragments.libro.ajeno.LibrosPerfilAjenoFragment

class PerfilAdapterAjeno(fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> LibrosPerfilAjenoFragment()
            1 -> ReseniaFragmentAjena()
            else -> FavoritosFragmentAjenos()
        }
    }
}