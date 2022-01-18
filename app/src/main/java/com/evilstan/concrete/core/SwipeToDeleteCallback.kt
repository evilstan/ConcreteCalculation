package com.evilstan.concrete.core

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.evilstan.concrete.ui.core.ConstructionsAdapter

class SwipeToDeleteCallback(dragDirs: Int, swipeDirs: Int) :
    ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs) {

    private lateinit var constructionsAdapter: ConstructionsAdapter

    constructor(constructionsAdapter: ConstructionsAdapter) : this(
        0,
        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
    ) {
        this.constructionsAdapter = constructionsAdapter
    }


    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        constructionsAdapter.deleteItem(position)
    }

}