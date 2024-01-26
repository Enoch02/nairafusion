package com.enoch02.nairafusion.ui.screen.thread

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enoch02.nairafusion.data.scraper.thread_scraper.ThreadItem
import com.enoch02.nairafusion.data.scraper.thread_scraper.ThreadScraper
import kotlinx.coroutines.launch

class ThreadScreenViewModel : ViewModel() {
    private val threadScraper = ThreadScraper()
    val threadItems = mutableStateListOf<ThreadItem>()

    fun getThread(id: String) {
        viewModelScope.launch {
            threadItems.addAll(threadScraper.getThread(id))
        }
    }
}