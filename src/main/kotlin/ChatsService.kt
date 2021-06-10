package ru.netology

object ChatsService{

    fun clear(){
        usersChats.clear()
    }

    private val usersChats = mutableListOf<Chat>()

    fun getUsersChats(): MutableList<Chat>{
        return usersChats
    }

    private fun unread(messages: MutableList<Message>) = messages.count { it.unread } > 0
    val short = :: unread


     private fun chatsCopy(userId: Int): MutableList<Chat> {
         val copy = mutableListOf<Chat>()
         usersChats.filter{chat -> chat.containsUserId(userId) }
             .forEach { copy.add(it.copy(it.id,it.userIds,it.messages)) }
         return copy
     }



    fun getUnreadChatsCount(userId: Int): Int  = chatsCopy(userId)
        .count { short(it.messages)}

    fun getChats(userId: Int): MutableList<Chat> =
        chatsCopy(userId).onEach { it.messages = mutableListOf(it.messages.last()) }

    fun getMessagesFromChat(
        chatId: Int, fromMessageId: Int,
        messageCount: Int
    ): MutableList<Message>  = (usersChats.find { chat ->  chat.id == chatId } ?:
        throw ChatDoesNotExistException)
        .messages.filter { chat -> chat.id in fromMessageId until fromMessageId + messageCount }
        .toMutableList()
        .also { list -> list.replaceAll { message -> message.copy(unread = false)} }

    fun addMessage(
        currentUserId: Int, toId: Int,
        text: String
    ): Boolean{
        val currentChat = (usersChats.find {
            it.containsPair( Pair(currentUserId,toId) ) }
                ) ?: addChat(currentUserId, toId)
        currentChat.messages += Message(
            id = when {
                currentChat.messages.isEmpty() -> 1
                else -> currentChat.messages.size +1
            }, fromId = currentUserId, toId = toId, text = text)
        return true
    }

    fun deleteMessage(chatId: Int, messageId: Int): Boolean{
        (usersChats.find { chat ->   chat.id == chatId } ?:
        throw ChatDoesNotExistException)
            .also { chat -> chat.messages
                .find { message -> message.id == messageId } ?:
                throw MessageDoesNotExistException}
            .also{ chat -> chat.messages
                .removeIf { message -> message.id == messageId }
                .let { usersChats.removeIf { it.messages.isEmpty() } }
                .let { return true } }
    }

    private fun addChat(currentUserId: Int, toId: Int): Chat{
        usersChats += Chat(id = usersChats.size+1,
            userIds = Pair(currentUserId, toId))
        return usersChats.last()
    }

    fun deleteChat(chatId: Int): Boolean{
        if(usersChats.removeIf { it.id ==chatId } ) return true
        throw ChatDoesNotExistException
    }
}

object MessageDoesNotExistException : java.lang.RuntimeException("Message whit this ID in this Chat doesn't exist")

object ChatDoesNotExistException : RuntimeException("Chat with this ID doesn't exist")

