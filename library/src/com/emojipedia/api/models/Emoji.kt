package com.emojipedia.api.models

class Emoji(emoji: String, title: String, emojiCode: String, val description: String) :
    BaseEmoji(emoji, title, emojiCode) {
}