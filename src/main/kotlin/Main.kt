import com.emojipedia.api.EmojiPedia

fun main() {

    // Getting categories
    val categories = EmojiPedia.getCategories()

    // Getting emojis from category
    val categoryEmojis = EmojiPedia.getEmojis(categories[0])

    // Getting popular emojis
    val mostPopular = EmojiPedia.getMostPopular()

    // Searching emojis
    val searchItems = EmojiPedia.search("angry")
}