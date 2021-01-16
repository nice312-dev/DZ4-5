data class Comment (val id: Long = 0L,val noteId: Long = 0L, val ownerId: Long = 0L, val replyTo: Long = 0L, val message: String) {
    var deleted: Boolean = false
}