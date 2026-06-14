package org.example.services

import org.example.models.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class WallServiceTest {

    @Test
    fun `should add post with non-zero id`() {
        // Given: создаём сервис
        val service = WallService()

        // When: добавляем пост
        val post = Post(text = "Test post")
        val addedPost = service.add(post)

        // Then: проверяем, что ID не равен 0
        assertNotEquals(0, addedPost.id)
    }

    @Test
    fun `should return true when updating existing post`() {
        // Given: создаём сервис и добавляем пост
        val service = WallService()
        val originalPost = service.add(Post(text = "Original post"))

        // создаём обновлённую версию
        val updatedPost = originalPost.copy(text = "Updated text")

        // When: обновляем существующий пост
        val result = service.update(updatedPost)

        // Then: проверяем, что метод вернул true
        assertTrue(result)
    }

    @Test
    fun `should return false when updating non-existing post`() {
        // Given: создаём сервис (без постов)
        val service = WallService()

        // создаём пост с несуществующим ID
        val nonExistingPost = Post(id = 999, text = "Non-existing post")

        // When: пытаемся обновить несуществующий пост
        val result = service.update(nonExistingPost)

        // Then: проверяем, что метод вернул false
        assertFalse(result)
    }
}