package org.example

import org.example.models.*
import org.example.services.WallService

fun main() {
    val wallService = WallService()

    // Пример 1: Пост только с текстом
    val textOnlyPost = Post(text = "Мой первый пост без вложений")
    val addedPost1 = wallService.add(textOnlyPost)
    println("✅ Добавлен пост #${addedPost1.id}: ${addedPost1.text}")

    // Пример 2: Пост с вложениями и null-текстом (допустимо, т.к. есть вложения)
    val postWithAttachments = Post(
        text = null,
        attachments = listOf(
            PhotoAttachment(1, "https://example.com/photo1.jpg", 1920, 1080),
            PhotoAttachment(2, "https://example.com/photo2.jpg", 1024, 768)
        )
    )
    val addedPost2 = wallService.add(postWithAttachments)
    println("✅ Добавлен пост #${addedPost2.id}: ${addedPost2.text ?: "(без текста)"}")
    println("   Вложений: ${addedPost2.getAttachmentsCount()}")

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
    println("   Всего вложений: ${addedPost3.getAttachmentsCount()}")

    // Пример 4: Попытка создать пустой пост (вызовет ошибку)
    try {
        val emptyPost = Post(text = "")
        wallService.add(emptyPost)
    } catch (e: IllegalArgumentException) {
        println("\n❌ Ошибка: ${e.message}")
    }

    // Демонстрация работы с вложениями
    println("\n���атистика по вложениям:")
    println("- Постов с вложениями: ${wallService.getPostsWithAttachments().size}")
    println("- Всего постов: ${wallService.size()}")

    // Вывод всех постов
    println("\n📝 Все посты:")
    wallService.getAll().forEach { post ->
        val attachmentsInfo = if (post.hasAttachments())
            " (${post.getAttachmentsCount()} влож.)"
        else
            " (без вложений)"
        val textDisplay = post.text?.take(40) ?: "(без текста)"
        println("  ${post.id}. $textDisplay...$attachmentsInfo")

        if (post.hasAttachments()) {
            post.attachments.forEachIndexed { index, attachment ->
                when (attachment.type) {
                    is AttachmentType.PHOTO -> {
                        val photo = (attachment as PhotoAttachment).photo
                        println("      📷 Фото ${index + 1}: ${photo.url}")
                    }
                    is AttachmentType.VIDEO -> {
                        val video = (attachment as VideoAttachment).video
                        println("      🎥 Видео ${index + 1}: ${video.title}")
                    }
                    is AttachmentType.DOC -> {
                        val doc = (attachment as DocumentAttachment).doc
                        println("      ���кумент ${index + 1}: ${doc.title}.${doc.ext}")
                    }
                    else -> {
                        // На случай, если добавятся новые типы — чтобы компилятор не ругался
                        println("      ❓ Неизвестный тип вложения")
                    }
                }
            }
        }
    }

    // Пример 5: Обновление поста
    println("\n🔄 Обновление поста:")
    val postToUpdate = wallService.getById(addedPost1.id)!!
    println("До обновления: ${postToUpdate.text}")

    val updatedPost = postToUpdate.copy(
        text = "Обновлённый текст первого поста",
        isPinned = true,
        likes = Likes(count = 15, userLikes = true),
        signerId = 777,
        replyOwnerId = 123,
        replyPostId = 456
    )
    val updateSuccess = wallService.update(updatedPost)
    println("Обновление прошло успешно: $updateSuccess")

    val retrievedUpdatedPost = wallService.getById(addedPost1.id)
    println("После обновления: ${retrievedUpdatedPost?.text}")
    println("Закреплён: ${retrievedUpdatedPost?.isPinned}")
    println("Количество лайков: ${retrievedUpdatedPost?.likes?.count}")
    println("Подпись: ${retrievedUpdatedPost?.signerId}")
    println("Ответ от владельца: ${retrievedUpdatedPost?.replyOwnerId}, ответ на пост: ${retrievedUpdatedPost?.replyPostId}")
}