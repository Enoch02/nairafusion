package com.enoch02.nairafusion.data.scraper.thread_scraper

import org.jsoup.nodes.Element

fun getTitle(threadItemHeader: Element): String {
    var text = threadItemHeader.select("td.bold.l.pu a[href]").first()?.text()

    if (text.isNullOrEmpty()) {
        //TODO: handle posts that do not have the html for their header near the body😑
        /*println("********************")
        println(threadItemHeader)
        println("********************\n\n")*/
        text = threadItemHeader.select("td.bold.l.pu a[href]").first()?.text() ?: ""
    }

    return text
}

//TODO: Improve
fun getDateCreated(threadItemHeader: Element): String {
    val info = threadItemHeader.select("td.bold.l.pu span.s b")
    val timeCreated = info[0].text()
    val dateCreated = if (info.size > 1) info[1].text() else ""

    return if (dateCreated.isBlank()) {
        timeCreated
    } else {
        "$timeCreated on $dateCreated"
    }
}

fun getLikes(threadItemBody: Element): String = threadItemBody.select("td.l.w.pd p.s").text()
