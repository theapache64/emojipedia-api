package com.emojipedia

import com.emojipedia.models.Category
import java.net.URL

object EmojiPedia {
    private const val BASE_URL = "https://emojipedia.org/"

    fun getCategories(): List<Category>? {
        val response = get(BASE_URL)
        println(response)
        return null
    }

    private fun get(url: String): Any {
        return URL(url).openConnection().apply {
            setRequestProperty(
                "User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:56.0) Gecko/20100101 Firefox/56.0"
            )
        }.getInputStream().reader().readText()
    }

}