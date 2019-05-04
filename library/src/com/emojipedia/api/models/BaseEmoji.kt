package com.emojipedia.api.models

open class BaseEmoji(
    val emoji: String,
    val title: String,
    val emojiCode: String
) {
    override fun toString(): String {
        return "BaseEmoji(emoji='$emoji', title='$title', emojiCode='$emojiCode')"
    }
}
