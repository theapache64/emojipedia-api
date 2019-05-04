# emojipedia-api
An unofficial REST API for emojipedia.org

## Download

Download latest jar from [here](https://github.com/theapache64/emojipedia-api/releases)

## Usage

```kotlin
// Getting categories
val categories = EmojiPedia.getCategories()

// Getting emojis from category
val categoryEmojis = EmojiPedia.getEmojis(categories[0])

// Getting popular emojis
val mostPopular = EmojiPedia.getMostPopular()

// Searching emojis
val searchItems = EmojiPedia.search("angry")
```

