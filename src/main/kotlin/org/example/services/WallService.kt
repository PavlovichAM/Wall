package org.example.services

import org.example.models.Post

class WallService {
    private val posts = mutableListOf<Post>()
    private var nextId = 1

    fun add(post: Post): Post {
        require(post.text.isNotBlank() || post.attachments.isNotEmpty()) {
            "Пост должен содержать текст или вложения"
        }
        val newPost = post.copy(id = nextId)
        posts.add(newPost)
        nextId++
        return newPost
    }

    fun update(post: Post): Boolean {
        val index = posts.indexOfFirst { it.id == post.id }
        return if (index != -1) {
            posts[index] = post
            true
        } else {
            false
        }
    }

    // Остальные методы остаются без изменений
    fun getById(id: Int): Post? = posts.find { it.id == id }
    fun getAll(): List<Post> = posts.toList()
    fun removeById(id: Int): Boolean {
        val removed = posts.removeIf { it.id == id }
        if (removed && posts.isEmpty()) nextId = 1
        return removed
    }
    fun clear() {
        posts.clear()
        nextId = 1
    }
    fun size(): Int = posts.size
    fun getLatest(count: Int): List<Post> = posts.takeLast(count)
    fun getByOwnerId(ownerId: Int): List<Post> = posts.filter { it.ownerId == ownerId }
    fun getPostsWithAttachments(): List<Post> = posts.filter { it.hasAttachments() }
}