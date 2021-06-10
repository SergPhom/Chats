import org.junit.Test

import org.junit.Assert.*
import ru.netology.ChatsService

class ChatsServiceTest {

    fun clear(){
        ChatsService.clear()
    }

    @Test
    fun getUsersChats() {
        ChatsService.addMessage(1,2, "test")

        val result = ChatsService.getUsersChats()

        assertFalse(result.isEmpty())
    }

    @Test
    fun getUnreadChatsCount() {
        clear()
        ChatsService.addMessage(1,2,"test")
        ChatsService.addMessage(2,3,"test")

        val result = ChatsService.getUnreadChatsCount(2)
        val moreResult = ChatsService.getUnreadChatsCount(1)

        assertEquals(2, result)
        assertEquals(1, moreResult)
    }

    @Test
    fun getChats() {
        clear()
        ChatsService.addMessage(1,2,"test first")
        ChatsService.addMessage(1,2,"test last")
        ChatsService.addMessage(1,3,"first")
        ChatsService.addMessage(1,3,"last")

        val result = ChatsService.getChats(1).map { it.messages }.map{it[0].text}

        assertEquals(listOf("test last" , "last"), result)
    }

    @Test
    fun getMessagesFromChat() {
        clear()
        ChatsService.addMessage(1,2,"first")
        ChatsService.addMessage(1,2,"second")
        ChatsService.addMessage(1,2,"third")
        ChatsService.addMessage(1,2,"forth")

        val result = ChatsService.getMessagesFromChat(1,2,3)
            .map { it.text }

        assertEquals(listOf("second", "third", "forth"), result)
    }

    @Test
    fun addMessage() {
        clear()
        val result = ChatsService.addMessage(1,2,"test")

        assertTrue(result)
    }

    @Test
    fun deleteMessage() {
        clear()
        ChatsService.addMessage(1,2, "test")
        ChatsService.deleteMessage(1,1)

        assertTrue(ChatsService.getUsersChats().isEmpty())
    }

    @Test
    fun deleteChat() {
        clear()
        ChatsService.addMessage(1,2, "test")
        val result = ChatsService.deleteChat(1)

        assertTrue(ChatsService.getUsersChats().isEmpty())
        assertTrue(result)
    }
}