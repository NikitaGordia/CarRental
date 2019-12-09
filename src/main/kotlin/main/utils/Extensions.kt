package main.utils

fun <T> guard(task: () -> T): T? = try {
    task()
} catch (e: Exception) {
    null
}

suspend fun <T> guardAsync(task: suspend () -> T): T? = try {
    task()
} catch (e: Exception) {
    null
}

suspend fun <T> guardSafe(task: suspend () -> T): T? = try {
    task()
} catch (e: Exception) {
    println("Execution failed: ${e.message}")
    null
}