package org.example.services

import org.example.models.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class WallServiceTest {
    private lateinit var wallService: WallService

    @BeforeEach
    fun setUp() {
        wallService = WallService()
    }

    // Существующие тесты (оставляем без изменений)
    @Test
    fun `should add post with text only`() { /* ... */ }

    @Test
    fun `should add post with attachments only`() { /* ... */ }

    @Test
    fun `should add post with both text and attachments`() { /* ... */ }

    @Test
    fun `should not add empty post without text and attachments`() { /* ... */ }

    @Test
    fun `should get posts with attachments only`() { /* ... */ }

    // Новые тесты для метода add()
    @Test
    fun `should assign unique sequential ids to posts`() {
        val post1 = wallService.add(Post(text = "Post 1"))
        val post2 = wallService.add(Post(text = "Post 2"))

        assertEquals(1, post1.id)
        assertEquals(2, post2.id)
    }

    @Test
    fun `should preserve all post properties when adding`() {
        val originalPost = Post(
            text = "Test post",
            ownerId = 123,
            comments = Comments(count = 5),
            likes = Likes(count = 10)
        )
        val addedPost = wallService.add(originalPost)

        assertEquals("Test post", addedPost.text)
        assertEquals(123, addedPost.ownerId)
        assertEquals(5, addedPost.comments.count)
        assertEquals(10, addedPost.likes.count)
    }

    // Новые тесты для метода update()
    @Test
    fun `should update existing post and return true`() {
        // Given
        val originalPost = wallService.add(Post(text = "Original"))
        val updatedPost = originalPost.copy(text = "Updated", likes = Likes(count = 5))

        // When
        val result = wallService.update(updatedPost)

        // Then
        assertTrue(result)
        val retrievedPost = wallService.getById(1)
        assertNotNull(retrievedPost)
        assertEquals("Updated", retrievedPost?.text)
        assertEquals(5, retrievedPost?.likes?.count)
    }

    @Test
    fun `should return false when updating non-existing post`() {
        // Given
        val nonExistingPost = Post(id = 999, text = "Non-existing")

        // When
        val result = wallService.update(nonExistingPost)

        // Then
        assertFalse(result)
        assertNull(wallService.getById(999))
    }

    @Test
    fun `should update all properties of post`() {
        // Given
        val originalPost = wallService.add(Post(
            text = "Old text",
            ownerId = 1,
            isPinned = false,
            comments = Comments(count = 0)
        ))
        val updatedPost = originalPost.copy(
            text = "New text",
            ownerId = 2,
            isPinned = true,
            comments = Comments(count = 10)
        )

        // When
        wallService.update(updatedPost)

        // Then
        val retrievedPost = wallService.getById(1)
        assertNotNull(retrievedPost)
        assertEquals("New text", retrievedPost?.text)
        assertEquals(2, retrievedPost?.ownerId)
        assertTrue(retrievedPost?.isPinned ?: false)
        assertEquals(10, retrievedPost?.comments?.count)
    }
}