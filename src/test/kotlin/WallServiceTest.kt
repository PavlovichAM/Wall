package org.example.services

import org.example.models.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class WallServiceTest {

    @Test
    fun `should add post with non-zero id`() {
        val service = WallService()
        val post = Post(text = "Test post")
        val addedPost = service.add(post)
        assertNotEquals(0, addedPost.id)
    }

    @Test
    fun `should return true when updating existing post`() {
        val service = WallService()
        val originalPost = service.add(Post(text = "Original post"))
        val updatedPost = originalPost.copy(text = "Updated text")
        val result = service.update(updatedPost)
        assertTrue(result)
    }

    @Test
    fun `should return false when updating non-existing post`() {
        val service = WallService()
        val nonExistingPost = Post(id = 999, text = "Non-existing post")
        val result = service.update(nonExistingPost)
        assertFalse(result)
    }
}