package com.example.bookie.ui.activity.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.bookie.ui.activity.fragments.favoritos.FavoritosFragment
import com.example.bookie.ui.activity.fragments.ReseniaFragmentPropio
import com.example.bookie.ui.activity.fragments.libro.propio.MisLibrosFragment

class PerfilAdapterPropio(fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> MisLibrosFragment()
            1 -> ReseniaFragmentPropio()
            else -> FavoritosFragment()
        }
    }
}