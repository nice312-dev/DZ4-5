import org.junit.Test

import org.junit.Assert.*

class NoteServiceTest {

    @Test
    fun add() {
        val actual = NoteService.add(Note(title = "title", text = "text"))

        assertFalse("Ожидалось, что добавленная заметка будет иметь id отличный от нуля", actual == 0L)
    }

    @Test
    fun delete() {
        val noteId = NoteService.add(Note(title = "title", text = "text"))
        val expected = NoteService.read().size - 1

        NoteService.delete(noteId)
        val actual = NoteService.read().size

        assertEquals("Ожидалось, что после удаления заметки количество заметок уменьшится.", expected, actual)
    }

    @Test
    fun edit() {
        val noteId = NoteService.add(Note(title = "title", text = "text"))
        val newNote = Note(id = noteId, title = "new_title", text = "new_text")
        NoteService.edit(newNote)

        val expected = "new_title"
        val actual = NoteService.getById(noteId).title

        assertEquals("Ожидалось, что заметка будет изменена.", expected, actual)
    }

    @Test
    fun read() {
        NoteService.add(Note(title = "title", text = "text"))

        assertTrue("Ожидалось, что после добавления заметки количество заметок будет больше нуля",
            NoteService.read().isNotEmpty()
        )
    }

    @Test
    fun getById() {
        val expected = NoteService.add(Note(title = "title", text = "text"))
        val actual = NoteService.getById(expected).id

        assertEquals("Ожидалось, что добавленная заметка будет найдена по id.", expected, actual)
    }

    @Test
    fun restore() {
        val noteId = NoteService.add(Note(title = "title", text = "text"))
        val expected = NoteService.read().size

        NoteService.delete(noteId)
        NoteService.restore(noteId)
        val actual = NoteService.read().size

        assertEquals("Ожидалось, что после удаления и восстановления заметки количество заметок не изменится.",
            expected, actual)
    }

    @Test
    fun createComment() {
        val noteId = NoteService.add(Note(title = "title", text = "text"))

        val expected = NoteService.createComment(noteId = noteId, ownerId = 0L, replyTo = 0L, message = "message")
        val actual = CommentService.getById(expected).id

        assertEquals("Ожидалось, что будет добавлен комментарий.", expected, actual)
    }

    @Test
    fun deleteComment() {
        val commentId = NoteService.createComment(noteId = 0L, ownerId = 0L, replyTo = 0L, message = "message")
        val expected = CommentService.read().size - 1

        NoteService.deleteComment(commentId)
        val actual = CommentService.read().size

        assertEquals("Ожидалось, что сообщение будет удалено.", expected, actual)
    }

    @Test
    fun editComment() {
        val commentId = NoteService.createComment(noteId = 0L, ownerId = 0L, replyTo = 0L, message = "message")
        NoteService.editComment(id = commentId, ownerId = 0L, message = "new_message")

        val expected = "new_message"
        val actual = CommentService.getById(commentId).message

        assertEquals("Ожидалось, что комментарий будет изменён.", expected, actual)
    }

    @Test
    fun getComments() {
        val noteId = NoteService.add(Note(title = "title", text = "text"))
        NoteService.createComment(noteId = noteId, ownerId = 0L, replyTo = 0L, message = "message")

        val expected  = 1
        val actual = NoteService.getComments(noteId).size

        assertEquals("Ожидалось, что будет получен один комментарий.", expected, actual)
    }
}