object NoteService: CrudService<Note> {
    private var notes = ArrayList<Note>()

    override fun add(entity: Note): Long {
        val id = (notes.size + 1).toLong()
        notes.add(entity.copy(id = id))

        return id
    }

    override fun delete(id: Long) {
        notes.filter{it.id == id}[0].deleted = true
    }

    override fun edit(entity: Note) {
        notes.remove(getById(entity.id))
        notes.add(entity.copy(title = entity.title, text = entity.text))
    }

    override fun read(): List<Note> {
        return notes.filter{!it.deleted}
    }

    override fun getById(id: Long): Note {
        return notes.filter{it.id == id}[0]
    }

    override fun restore(id: Long) {
        notes.filter{it.id == id}[0].deleted = false
    }

    fun createComment(noteId: Long, ownerId: Long, replyTo: Long, message: String): Long {
        val comment = Comment(id = 0, noteId, ownerId, replyTo, message)
        return CommentService.add(comment)
    }

    fun deleteComment(id: Long) {
        CommentService.delete(id)
    }

    fun editComment(id: Long, ownerId: Long, message: String) {
        val comment = CommentService.getById(id).copy(ownerId = ownerId, message = message)
        CommentService.edit(comment)
    }

    fun getComments(noteId: Long): List<Comment> {
        return CommentService.read(getById(noteId))
    }
}