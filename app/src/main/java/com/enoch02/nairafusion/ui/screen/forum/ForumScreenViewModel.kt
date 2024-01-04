package com.enoch02.nairafusion.ui.screen.forum

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enoch02.nairafusion.data.scraper.NairalandScraper
import kotlinx.coroutines.launch

class ForumScreenViewModel : ViewModel() {
    private val scraper = NairalandScraper()
    val currentForumScreenState = mutableStateOf(ForumScreenState.LOADING)
    val currentPage = mutableStateOf(0)
    val topics = mutableStateListOf<NairalandScraper.Topic>()

    //TODO: create a list that holds previous topics list so that it navigating to previous page is faster

    fun getFeaturedTopics() = viewModelScope.launch {
        scraper.getFeaturedTopics()
            .onSuccess {
                topics.clear()
                currentForumScreenState.value = ForumScreenState.LOADING_COMPLETE
                topics.addAll(it)
            }
            .onFailure {
                currentForumScreenState.value = ForumScreenState.FAILURE
                //TODO: create callback that will trigger a message at ForumScreen
            }
    }

    fun getTopics(page: Int) = viewModelScope.launch {
        scraper.getTopics(page)
            .onSuccess {
                topics.clear()
                currentForumScreenState.value = ForumScreenState.LOADING_COMPLETE
                topics.addAll(it)
            }
            .onFailure {
                currentForumScreenState.value = ForumScreenState.FAILURE
                //TODO
            }
    }

    fun nextPage() {
        currentForumScreenState.value = ForumScreenState.LOADING
        currentPage.value++
    }

    fun previousPage() {
        currentForumScreenState.value = ForumScreenState.LOADING
        if (currentPage.value > 0) {
            currentPage.value--
        }
    }

    fun refreshPage() {
        currentForumScreenState.value = ForumScreenState.LOADING

        if (currentPage.value == 0) {
            getFeaturedTopics()
        } else {
            getTopics(currentPage.value)
        }
    }

    fun goHome() {
        currentForumScreenState.value = ForumScreenState.LOADING
        currentPage.value = 0
    }

    enum class ForumScreenState {
        LOADING,
        LOADING_COMPLETE,
        FAILURE
    }
}