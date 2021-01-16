object CommentService: CrudService<Comment> {
    private var comments = ArrayList<Comment>()

    override fun add(entity: Comment): Long {
        val id = (comments.size + 1).toLong()
        comments.add(entity.copy(id = id))

        return id
    }

    override fun delete(id: Long) {
        comments.filter{it.id == id}[0].deleted = true
    }

    override fun edit(entity: Comment) {
        comments.remove(getById(entity.id))
        comments.add(entity.copy(message = entity.message))
    }

    override fun read(): List<Comment> {
        return comments.filter{!it.deleted}
    }

    override fun getById(id: Long): Comment {
        return comments.filter{it.id == id}[0]
    }

    override fun restore(id: Long) {
        comments.filter{it.id == id}[0].deleted = false
    }

    fun read(note: Note): List<Comment> {
        return comments.filter{!it.deleted && it.noteId == note.id}
    }
}