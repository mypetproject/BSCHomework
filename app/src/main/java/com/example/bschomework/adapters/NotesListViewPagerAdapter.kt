package com.example.bschomework.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.bschomework.fragments.NoteDataFragment
import com.example.bschomework.room.NoteData

class NotesListViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    var items: List<NoteData> = emptyList()

    override fun getItemCount(): Int = items.size

    override fun createFragment(position: Int): Fragment {

        return NoteDataFragment.newInstance(items[position].id)
    }
}