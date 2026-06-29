package org.example.models

import java.util.Date

data class Comments(
    val count: Int = 0,
    val canPost: Boolean = true,
    val groupsCanPost: Boolean = true,
    val canClose: Boolean = true,
    val canOpen: Boolean = true
)

data class Likes(
    val count: Int = 0,
    val userLikes: Boolean = false,
    val canLike: Boolean = true,
    val canPublish: Boolean = true
)

data class Reposts(
    val count: Int = 0,
    val userReposted: Boolean = false
)

data class Views(
    val count: Int = 0
)

data class Post(
    val id: Int = 0,
    val ownerId: Int = 0,
    val fromId: Int = 0,
    val createdBy: Int = 0,
    val date: Long = System.currentTimeMillis() / 1000,
    val text: String? = null,
    val replyOwnerId: Int? = null,
    val replyPostId: Int? = null,
    val friendsOnly: Boolean = false,
    val postType: String = "post",
    val signerId: Int? = null,
    val canPin: Boolean = true,
    val canDelete: Boolean = true,
    val canEdit: Boolean = true,
    val isPinned: Boolean = false,
    val markedAsAds: Boolean = false,
    val isFavorite: Boolean = false,
    val postponedId: Int? = null,
    val comments: Comments = Comments(),
    val likes: Likes = Likes(),
    val reposts: Reposts = Reposts(),
    val views: Views = Views(),
    val attachments: List<Attachment> = emptyList()
) {
    fun isFromCommunity(): Boolean = ownerId < 0 && fromId == ownerId

    fun isRepost(): Boolean = postType == "copy"

    fun getFormattedDate(): String = Date(date * 1000).toString()

    fun canCurrentUserEdit(): Boolean = canEdit && ownerId > 0

    fun hasAttachments(): Boolean = attachments.isNotEmpty()

    fun getAttachmentsCount(): Int = attachments.size
}