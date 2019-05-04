import com.emojipedia.EmojiPedia

fun main() {
    val categories = EmojiPedia.getCategories()
    println(categories)

    val mostPopular = EmojiPedia.getMostPopular()
    println(mostPopular)

    val searchItems = EmojiPedia.search("angry")
    println(searchItems)
}