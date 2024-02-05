package com.enoch02.nairafusion.ui.screen.thread.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.UrlAnnotation
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.enoch02.nairafusion.data.scraper.thread_scraper.BoldElement
import com.enoch02.nairafusion.data.scraper.thread_scraper.ItalicElement
import com.enoch02.nairafusion.data.scraper.thread_scraper.LineBreakElement
import com.enoch02.nairafusion.data.scraper.thread_scraper.LinkElement
import com.enoch02.nairafusion.data.scraper.thread_scraper.TextElement
import com.enoch02.nairafusion.data.scraper.thread_scraper.ThreadItemElement

@Composable
fun ThreadItem(
    title: String,
    user: String,
    dateCreated: String,
    content: List<ThreadItemElement>,
    likes: String,
    attachmentImages: List<String>
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        content = {
            Text(
                text = buildAnnotatedString {
                    //TODO: replace with proper color
                    pushStyle(SpanStyle(color = Color.Blue))
                    append(title)
                    pop()

                    append(" by ")

                    //TODO: replace with proper color
                    pushStyle(SpanStyle(color = Color.Red))
                    append(user)
                    pop()

                    append(" at ")
                    //TODO: replace with proper color
                    pushStyle(SpanStyle(color = Color.Green))
                    append(dateCreated)

                    toAnnotatedString()
                },
                modifier = Modifier.padding(4.dp),
                textAlign = TextAlign.Justify
            )
            Spacer(modifier = Modifier.height(4.dp))
            Divider()

            Text(
                text = buildAnnotatedString {
                    content.forEach { element ->
                        when (element) {
                            is BoldElement -> {
                                pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
                                append(element.text)
                                pop()
                            }

                            is ItalicElement -> {
                                pushStyle(SpanStyle(fontStyle = FontStyle.Italic))
                                append(element.text)
                                pop()
                            }

                            is LineBreakElement -> {
                                /*pushStyle(ParagraphStyle(lineBreak = LineBreak.Paragraph))*/
                                append("\n")
                            }

                            is LinkElement -> {
                                //TODO: find a way to make the link also clickable
                                //pushUrlAnnotation(UrlAnnotation(element.url))
                                pushStyle(SpanStyle(color = Color.Blue))
                                append(element.text)
                                pop()
                            }

                            is TextElement -> {
                                append(element.text)
                            }
                        }
                    }

                    toAnnotatedString()
                },
                modifier = Modifier.padding(4.dp)
            )
        }
    )
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun Preview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        content = {
            ThreadItem(
                title = "Hello World",
                user = "Enoch",
                dateCreated = "10 October 2023",
                content = listOf(
                    BoldElement("This text should be bold"),
                    LineBreakElement,
                    ItalicElement("I am italic!!!"),
                    LineBreakElement,
                    LineBreakElement,
                    LineBreakElement,
                    TextElement("Just your friendly neighbourhood text"),
                    LineBreakElement,
                    BoldElement("I'm bold again!!!"),
                    LinkElement(" google", "google.com")
                ),
                likes = "10",
                attachmentImages = emptyList()
            )
        }
    )
}