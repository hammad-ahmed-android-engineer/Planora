package com.example.planora.domain.usecase

import com.example.planora.domain.model.Priority
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

data class ParsedTaskDetails(
    val title: String,
    val dueDate: String? = null,
    val dueTime: String? = null,
    val priority: Priority = Priority.NONE,
    val tagNames: List<String> = emptyList()
)

object NaturalLanguageTaskParser {

    private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    fun parse(rawInput: String): ParsedTaskDetails {
        var text = rawInput.trim()

        var priority = Priority.NONE
        val tagNames = mutableListOf<String>()
        var dueDate: String? = null
        var dueTime: String? = null

        // 1. Extract Priority (!urgent, !high, !medium, !low)
        val priorityRegex = Regex("""!(urgent|high|medium|low|none)""", RegexOption.IGNORE_CASE)
        priorityRegex.find(text)?.let { match ->
            priority = when (match.groupValues[1].lowercase()) {
                "urgent" -> Priority.URGENT
                "high" -> Priority.HIGH
                "medium" -> Priority.MEDIUM
                "low" -> Priority.LOW
                else -> Priority.NONE
            }
            text = text.replace(match.value, "").trim()
        }

        // 2. Extract Tags (#TagName)
        val tagRegex = Regex("""#(\w+)""")
        tagRegex.findAll(text).forEach { match ->
            tagNames.add(match.groupValues[1])
            text = text.replace(match.value, "").trim()
        }

        // 3. Extract Time (at 5 PM, at 17:00, 3pm, 10:30am)
        val timeRegex = Regex("""\b(?:at\s+)?(\d{1,2})(?::(\d{2}))?\s*(am|pm)\b""", RegexOption.IGNORE_CASE)
        timeRegex.find(text)?.let { match ->
            val hourStr = match.groupValues[1]
            val minStr = match.groupValues[2]
            val amPm = match.groupValues[3].lowercase()

            var hour = hourStr.toIntOrNull() ?: 9
            val min = if (minStr.isNotEmpty()) minStr.toIntOrNull() ?: 0 else 0

            if (amPm == "pm" && hour < 12) hour += 12
            if (amPm == "am" && hour == 12) hour = 0

            dueTime = LocalTime.of(hour, min).format(timeFormatter)
            text = text.replace(match.value, "").trim()
        }

        // 4. Extract Relative Date (today, tomorrow, next monday, etc.)
        val today = LocalDate.now()
        val lowerText = text.lowercase()

        when {
            lowerText.contains("tomorrow") -> {
                dueDate = today.plusDays(1).toString()
                text = text.replace(Regex("""\btomorrow\b""", RegexOption.IGNORE_CASE), "").trim()
            }
            lowerText.contains("tonight") || lowerText.contains("today") -> {
                dueDate = today.toString()
                text = text.replace(Regex("""\b(today|tonight)\b""", RegexOption.IGNORE_CASE), "").trim()
            }
            lowerText.contains("next monday") -> {
                dueDate = getNextDayOfWeek(today, DayOfWeek.MONDAY).toString()
                text = text.replace(Regex("""\bnext monday\b""", RegexOption.IGNORE_CASE), "").trim()
            }
            lowerText.contains("next tuesday") -> {
                dueDate = getNextDayOfWeek(today, DayOfWeek.TUESDAY).toString()
                text = text.replace(Regex("""\bnext tuesday\b""", RegexOption.IGNORE_CASE), "").trim()
            }
            lowerText.contains("next wednesday") -> {
                dueDate = getNextDayOfWeek(today, DayOfWeek.WEDNESDAY).toString()
                text = text.replace(Regex("""\bnext wednesday\b""", RegexOption.IGNORE_CASE), "").trim()
            }
            lowerText.contains("next thursday") -> {
                dueDate = getNextDayOfWeek(today, DayOfWeek.THURSDAY).toString()
                text = text.replace(Regex("""\bnext thursday\b""", RegexOption.IGNORE_CASE), "").trim()
            }
            lowerText.contains("next friday") -> {
                dueDate = getNextDayOfWeek(today, DayOfWeek.FRIDAY).toString()
                text = text.replace(Regex("""\bnext friday\b""", RegexOption.IGNORE_CASE), "").trim()
            }
            lowerText.contains("next saturday") -> {
                dueDate = getNextDayOfWeek(today, DayOfWeek.SATURDAY).toString()
                text = text.replace(Regex("""\bnext saturday\b""", RegexOption.IGNORE_CASE), "").trim()
            }
            lowerText.contains("next sunday") -> {
                dueDate = getNextDayOfWeek(today, DayOfWeek.SUNDAY).toString()
                text = text.replace(Regex("""\bnext sunday\b""", RegexOption.IGNORE_CASE), "").trim()
            }
        }

        // Clean up multiple spaces in title
        val cleanedTitle = text.replace(Regex("""\s+"""), " ").trim()

        return ParsedTaskDetails(
            title = if (cleanedTitle.isEmpty()) rawInput else cleanedTitle,
            dueDate = dueDate,
            dueTime = dueTime,
            priority = priority,
            tagNames = tagNames
        )
    }

    private fun getNextDayOfWeek(today: LocalDate, dayOfWeek: DayOfWeek): LocalDate {
        var date = today.plusDays(1)
        while (date.dayOfWeek != dayOfWeek) {
            date = date.plusDays(1)
        }
        return date
    }
}
