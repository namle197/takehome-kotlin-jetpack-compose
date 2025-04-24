package com.namle197.common.converter

/**
 * An extension function to format follower count.
 */
fun Int.formatFollower(): String {
    return when {
        this > 1000000 -> {
            val millions = this / 1000000
            val remainingThousands = (this % 1000000) / 100000
            if (remainingThousands > 0) "${millions}M${remainingThousands}+" else "${millions}M+"
        }
        this > 1000 -> {
            val thousands = this / 1000
            val remainingHundreds = (this % 1000) / 100
            if (remainingHundreds > 0) "${thousands}k${remainingHundreds}+" else "${thousands}k+"
        }
        this > 100 -> "100+"
        else -> this.toString()
    }
}