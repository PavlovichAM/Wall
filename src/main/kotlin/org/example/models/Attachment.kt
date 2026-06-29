package org.example.models

sealed class AttachmentType {
    object PHOTO : AttachmentType()
    object VIDEO : AttachmentType()
    object DOC : AttachmentType()
}

abstract class Attachment(val type: AttachmentType)

// --- Данные (контейнеры) ---
data class PhotoData(
    val photoId: Int,
    val url: String,
    val width: Int,
    val height: Int
)

data class VideoData(
    val videoId: Int,
    val title: String,
    val duration: Int
    // сюда можно добавить остальные поля из API по мере необходимости
)

data class DocData(
    val docId: Int,
    val title: String,
    val ext: String,
    val size: Long
)

// --- Вложения (наследники Attachment) ---
class PhotoAttachment(
    type: AttachmentType = AttachmentType.PHOTO,
    val photo: PhotoData
) : Attachment(type) {
    // Конструктор-обёртка для совместимости со старым кодом
    constructor(photoId: Int, url: String, width: Int, height: Int) : this(
        AttachmentType.PHOTO,
        PhotoData(photoId, url, width, height)
    )
}

class VideoAttachment(
    type: AttachmentType = AttachmentType.VIDEO,
    val video: VideoData
) : Attachment(type) {
    constructor(videoId: Int, title: String, duration: Int) : this(
        AttachmentType.VIDEO,
        VideoData(videoId, title, duration)
    )
}

class DocumentAttachment(
    type: AttachmentType = AttachmentType.DOC,
    val doc: DocData
) : Attachment(type) {
    constructor(docId: Int, title: String, ext: String, size: Long) : this(
        AttachmentType.DOC,
        DocData(docId, title, ext, size)
    )
}