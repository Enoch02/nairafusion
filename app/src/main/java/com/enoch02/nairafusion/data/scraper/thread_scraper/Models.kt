package com.enoch02.nairafusion.data.scraper.thread_scraper

data class Topic(
    val title: String,
    val url: String,
)

data class ThreadItem(
    val title: String,
    val user: String,
    val dateCreated: String,
    val content: List<ThreadItemElement>,
    val likes: String,
    val attachmentImageUrls: List<String>
)

sealed class ThreadItemElement

data class TextElement(val text: String) : ThreadItemElement()
data class BoldElement(val text: String) : ThreadItemElement()
data class ItalicElement(val text: String) : ThreadItemElement()
data class LinkElement(val text: String, val url: String) : ThreadItemElement()
object LineBreakElement : ThreadItemElement()