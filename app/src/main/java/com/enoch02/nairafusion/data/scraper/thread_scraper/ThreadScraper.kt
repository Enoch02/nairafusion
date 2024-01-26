package com.enoch02.nairafusion.data.scraper.thread_scraper

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.nodes.TextNode

const val TAG = "ThreadScraper"

//TODO: Use DI to share th connection object
class ThreadScraper {
    private val baseUrl = "https://www.nairaland.com"
    private val connection = Jsoup
        .connect(baseUrl)
        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:109.0) Gecko/20100101 Firefox/118.0")

    suspend fun getThread(threadID: String): MutableList<ThreadItem> {
        return withContext(Dispatchers.IO) {
            val threadItems = mutableListOf<ThreadItem>()
            val threadDoc = connection.url("$baseUrl/$threadID").get()
            val postsTable = threadDoc.select("[summary=posts]")
            val posts = postsTable.select("tr")

            println(posts.size)

            try {
                for (index in 0 until posts.size step 2) {
                    val threadItemHeader = posts[index]
                    val threadItemBody = posts[index + 1]
                    val threadBodyElements = threadItemBody.select("div.narrow *")

                    val item = ThreadItem(
                        title = getTitle(threadItemHeader),
                        user = threadItemHeader.select("td.bold.l.pu a.user").text(),
                        dateCreated = getDateCreated(threadItemHeader),
                        content = parseThreadBodyElements(threadBodyElements),
                        likes = getLikes(threadItemBody),
                        attachmentImageUrls = emptyList()  //TODO
                    )

                    threadItems.add(item)
                }
            } catch (e: IndexOutOfBoundsException) {
                Log.d(TAG, "${e.message}\nThis is fine, i.e., an expected behavior.")
            }

            return@withContext threadItems
        }
    }

    private fun parseElement(element: Element): ThreadItemElement {
        return when (element.tagName()) {
            "b" -> BoldElement(element.text())
            "i" -> ItalicElement(element.text())
            "a" -> LinkElement(element.text(), element.attr("href"))
            "br" -> LineBreakElement
            else -> TextElement(element.text())
        }
    }

    private fun parseThreadBodyElements(contentElements: List<Element>): List<ThreadItemElement> {
        val parsedElements = mutableListOf<ThreadItemElement>()

        contentElements.forEach { element ->
            if (element.tagName() == "blockquote") {
                val subElements = element.childNodes()

                subElements.forEach { node ->
                    if (node is Element) {
                        parsedElements.add(parseElement(node))
                    }

                    if (node is TextNode) {
                        parsedElements.add(parseElement(Element("p").text(node.text())))
                    }
                }
            } else {
                parsedElements.add(parseElement(element))
            }
        }

        return parsedElements
    }
}