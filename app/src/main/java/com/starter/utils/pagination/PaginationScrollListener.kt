package com.starter.utils.pagination

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
Created by Mohammad Zaki
on May,25 2021
 **/
abstract class PaginationScrollListener(var layoutManager: LinearLayoutManager) :
    RecyclerView.OnScrollListener() {


    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

        if (!isLoading() && !isLastPage()) {
            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                && firstVisibleItemPosition >= 0
            ) {
                loadMoreItems()
            }
        }

    }

    protected abstract fun loadMoreItems()
    abstract fun isLastPage(): Boolean
    abstract fun isLoading(): Boolean

}