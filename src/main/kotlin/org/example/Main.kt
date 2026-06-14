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

    // Пример 5: Обновление поста (исправлено)
    println("\n🔄 Обновление поста:")
    val postToUpdate = wallService.getById(addedPost1.id)!!
    println("До обновления: ${postToUpdate.text}")

    val updatedPost = postToUpdate.copy(
        text = "Обновлённый текст первого поста",
        isPinned = true,
        likes = Likes(count = 15, userLikes = true)
    )
    val updateSuccess = wallService.update(updatedPost)
    println("Обновление прошло успешно: $updateSuccess")

    val retrievedUpdatedPost = wallService.getById(addedPost1.id)
    println("После обновления: ${retrievedUpdatedPost?.text}")
    println("Закреплён: ${retrievedUpdatedPost?.isPinned}")
    println("Количество лайков: ${retrievedUpdatedPost?.likes?.count}")


    // Пример 6: Попытка обновить несуществующий пост
    println("\n🛑 Попытка обновить несуществующий пост:")
    val nonExistingUpdate = Post(id = 999, text = "Не существующий пост")
    val updateFailed = wallService.update(nonExistingUpdate)
    println("Обновление прошло успешно: $updateFailed")

    // Пример 7: Проверка обновлённого списка постов
    println("\n🔎 Проверка обновлённого списка постов:")
    wallService.getAll().forEach { post ->
        println("  #${post.id}: ${post.text} | Лайки: ${post.likes.count} | Закреплён: ${post.isPinned}")
    }

    // Пример 8: Получение последних постов
    println("\n🕒 Последние 2 поста:")
    wallService.getLatest(2).forEach { post ->
        println("  #${post.id}: ${post.text}")
    }

    // Пример 9: Получение постов конкретного владельца
    println("\n👨‍👦 Посты владельца с ID=123:")
    val ownerPost = Post(text = "Пост от владельца 123", ownerId = 123)
    wallService.add(ownerPost)

    wallService.getByOwnerId(123).forEach { post ->
        println("  #${post.id}: ${post.text} (владелец: ${post.ownerId})")
    }
}