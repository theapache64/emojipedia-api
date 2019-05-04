package com.emojipedia

import com.emojipedia.models.Category
import com.emojipedia.models.Emoji
import com.emojipedia.models.MostPopular
import java.net.URL
import java.util.regex.Matcher
import java.util.regex.Pattern

object EmojiPedia {

    private const val BASE_URL = "https://emojipedia.org/"
    private const val SEARCH_URL = "${BASE_URL}search/?q="

    // DELIMITERS
    private const val CATEGORIES_START_DELIMITER = "<h2>Categories</h2>"
    private const val MOST_POPULAR_START_DELIMITER = "<h2>Most Popular</h2>"
    private const val SEARCH_START_DELIMITER = "<ol class=\"search-results\">"
    private const val SEARCH_END_DELIMITER = "</ol>"

    private const val CATEGORIES_END_DELIMITER = "</div>"
    private const val MOST_POPULAR_END_DELIMITER = "</div>"

    // REGEX
    private val EMOJI_ITEM_REGEX by lazy {
        Pattern.compile("<li><a href=\"/(?<emojiCode>.+)/\"><span class=\"emoji\">(?<emoji>.+)</span>(?<title>.+)</a></li>")
    }

    private val SEARCH_ITEM_REGEX by lazy {
        Pattern.compile(
            "<li>\n" +
                    "<h2><a href=\"\\/(?<emojiCode>.+)\\/\"><span class=\"emoji\">(?<emoji>.+)<\\/span> (?<title>.+)<\\/a><\\/h2>\n" +
                    "<p>(?<description>.+)<\\/p>\n" +
                    "<\\/li>"
        )
    }


    fun search(keyword: String): List<Emoji> {
        return get<Emoji>(
            "$SEARCH_URL$keyword",
            SEARCH_START_DELIMITER,
            SEARCH_END_DELIMITER,
            SEARCH_ITEM_REGEX
        ) { matcher ->
            val emoji = matcher.group("emoji")
            val title = matcher.group("title")
            val emojiCode = matcher.group("emojiCode")
            val description = matcher.group("description")
            Emoji(emoji, title, emojiCode, description)
        }
    }

    /**
     * Return categories
     */
    fun getCategories(): List<Category> {
        return get<Category>(
            BASE_URL,
            CATEGORIES_START_DELIMITER,
            CATEGORIES_END_DELIMITER,
            EMOJI_ITEM_REGEX
        ) { matcher ->
            val emoji = matcher.group("emoji")
            val title = matcher.group("title")
            val emojiCode = matcher.group("emojiCode")
            Category(emoji, title, emojiCode)
        }
    }

    /**
     * Returns most popular emojis
     */
    fun getMostPopular(): List<MostPopular> {
        return get<MostPopular>(
            BASE_URL,
            MOST_POPULAR_START_DELIMITER,
            MOST_POPULAR_END_DELIMITER,
            EMOJI_ITEM_REGEX
        ) { matcher ->
            val emoji = matcher.group("emoji")
            val title = matcher.group("title")
            val emojiCode = matcher.group("emojiCode")
            MostPopular(emoji, title, emojiCode)
        }
    }


    /**
     *  Special function to return data between startDelimiter and endDelimiter from givenUrl with given pattern using
     *  given parse function
     */
    fun <T> get(
        fromUrl: String,
        startDelimiter: String,
        endDelimiter: String,
        regex: Pattern,
        parseItem: (matcher: Matcher) -> T
    ): List<T> {
        val response = get(fromUrl)
        val categoriesHtml = getBetween(response, startDelimiter, endDelimiter)
        val matcher = regex.matcher(categoriesHtml)
        val items = mutableListOf<T>()
        while (matcher.find()) {
            items.add(parseItem(matcher))
        }
        return items
    }


    /**
     * Return string between startDelimiter and endDelimiter
     */
    private fun getBetween(response: String, startDelimiter: String, endDelimiter: String): String {
        val a = response.split(startDelimiter)
        require(a.size > 1) { "Structure changed. Failed to find $startDelimiter delimiter" }
        val b = a[1].split(endDelimiter)
        return b[0]
    }

    /**
     * Get response from given URL
     */
    private fun get(url: String): String {
        return URL(url).openConnection().apply {
            setRequestProperty(
                "User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:56.0) Gecko/20100101 Firefox/56.0"
            )
        }.getInputStream().reader().readText()
    }

}