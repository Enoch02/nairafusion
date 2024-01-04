package com.enoch02.nairafusion.data.scraper

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

class NairalandScraper {
    private val baseUrl = "https://www.nairaland.com"

    suspend fun getFeaturedTopics(): Result<List<Topic>> {
        return withContext(Dispatchers.IO) {
            try {
                val featuredDoc = Jsoup.connect(baseUrl).get()
                val tables = featuredDoc.select("table")
                val tableRows = tables[2].select("tr")
                val topicElements = tableRows[1].select("a")
                val topics = topicElements.map { postElement ->
                    Topic(
                        title = postElement.text(),
                        url = postElement.attr("href")
                    )
                }
                Result.success(topics)

            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    suspend fun getTopics(pageNumber: Int): Result<List<Topic>> {
        return withContext(Dispatchers.IO) {
            try {
                val newsUrl = "$baseUrl/news/$pageNumber"
                val newsDoc = Jsoup.connect(newsUrl).get()

                /**
                 * Table 0 - header
                 * Table 1 - posts
                 * Table 2 - ?
                 * Table 3 - member stats
                 * Table 4 - footer
                 */
                val tables = newsDoc.select("table")

                /**
                 * Row 0 ->
                 * Row 1 -> posts
                 * Row -> other pages
                 */
                val tableRows = tables[1].select("tr")
                val topicElements = tableRows[0].select("a")
                val topics = topicElements.map { postElement ->
                    Topic(
                        title = postElement.text(),
                        url = postElement.attr("href")
                    )
                }
                Result.success(topics)

            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    data class Topic(
        val title: String,
        val url: String,
    )
}