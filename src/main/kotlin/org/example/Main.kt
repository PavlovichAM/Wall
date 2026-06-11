package org.example

import org.example.models.*
import org.example.services.WallService

fun main() {
    val wallService = WallService()

    // Пример 1: Пост только с текстом
    val textOnlyPost = Post(text = "Мой первый пост без вложений")
    val addedPost1 = wallService.add(textOnlyPost)
    println("✅ Добавлен пост #${addedPost1.id}: ${addedPost1.text}")

    // Пример 2: Пост с вложениями
    val postWithAttachments = Post(
        text = "Посмотрите мои фотографии!",
        attachments = listOf(
            PhotoAttachment(1, "https://example.com/photo1.jpg", 1920, 1080),
            PhotoAttachment(2, "https://example.com/photo2.jpg", 1024, 768)
        )
    )
    val addedPost2 = wallService.add(postWithAttachments)
    println("✅ Добавлен пост #${addedPost2.id}: ${addedPost2.text}")
    println("   📎 Вложений: ${addedPost2.getAttachmentsCount()}")

    // Пример 3: Пост с разными типами вложений
    val mixedAttachmentsPost = Post(
        text = "Разнообразные вложения",
        attachments = listOf(
            PhotoAttachment(3, "https://example.com/photo3.jpg", 800, 600),
            VideoAttachment(1, "Забавное видео", 120),
            DocumentAttachment(1, "Важный документ", "pdf", 1024000)
        )
    )
    val addedPost3 = wallService.add(mixedAttachmentsPost)
    println("✅ Добавлен пост #${addedPost3.id}: ${addedPost3.text}")
    println("   📎 Всего вложений: ${addedPost3.getAttachmentsCount()}")

    // Пример 4: Попытка создать пустой пост (вызовет ошибку)
    try {
        val emptyPost = Post(text = "")
        wallService.add(emptyPost)
    } catch (e: IllegalArgumentException) {
        println("\n❌ Ошибка: ${e.message}")
    }

    // Демонстрация работы с вложениями
    println("\n📊 Статистика по вложениям:")
    println("- Постов с вложениями: ${wallService.getPostsWithAttachments().size}")
    println("- Всего постов: ${wallService.size()}")

    // Вывод всех постов
    println("\n📝 Все посты:")
    wallService.getAll().forEach { post ->
        val attachmentsInfo = if (post.hasAttachments())
            " (${post.getAttachmentsCount()} влож.)"
        else
            " (без вложений)"
        println("  ${post.id}. ${post.text.take(40)}...$attachmentsInfo")

        // Детальная информация о вложениях
        if (post.hasAttachments()) {
            post.attachments.forEachIndexed { index, attachment ->
                when (attachment) {
                    is PhotoAttachment -> println("      📷 Фото ${index + 1}: ${attachment.url}")
                    is VideoAttachment -> println("      🎥 Видео ${index + 1}: ${attachment.title}")
                    is DocumentAttachment -> println("      📄 Документ ${index + 1}: ${attachment.title}.${attachment.ext}")
                }
            }
        }
    }
}