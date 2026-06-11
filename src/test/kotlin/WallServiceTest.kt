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

    @Test
    fun `should add post with text only`() {
        val post = Post(text = "Text only post")
        val addedPost = wallService.add(post)

        assertEquals(1, addedPost.id)
        assertEquals("Text only post", addedPost.text)
        assertFalse(addedPost.hasAttachments())
    }

    @Test
    fun `should add post with attachments only`() {
        val post = Post(
            text = "",
            attachments = listOf(PhotoAttachment(1, "url", 100, 100))
        )
        val addedPost = wallService.add(post)

        assertEquals(1, addedPost.id)
        assertTrue(addedPost.hasAttachments())
        assertEquals(1, addedPost.getAttachmentsCount())
    }

    @Test
    fun `should add post with both text and attachments`() {
        val post = Post(
            text = "Post with attachments",
            attachments = listOf(PhotoAttachment(1, "url", 100, 100))
        )
        val addedPost = wallService.add(post)

        assertEquals(1, addedPost.id)
        assertTrue(addedPost.hasAttachments())
        assertEquals("Post with attachments", addedPost.text)
    }

    @Test
    fun `should not add empty post without text and attachments`() {
        val emptyPost = Post(text = "")

        assertThrows(IllegalArgumentException::class.java) {
            wallService.add(emptyPost)
        }
    }

    @Test
    fun `should get posts with attachments only`() {
        wallService.add(Post(text = "Text only"))
        wallService.add(Post(text = "", attachments = listOf(PhotoAttachment(1, "url", 100, 100))))
        wallService.add(Post(text = "Another text only"))
        wallService.add(Post(text = "With video", attachments = listOf(VideoAttachment(1, "video", 60))))

        val postsWithAttachments = wallService.getPostsWithAttachments()

        assertEquals(2, postsWithAttachments.size)
        assertTrue(postsWithAttachments.all { it.hasAttachments() })
    }
}