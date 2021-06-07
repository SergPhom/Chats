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

    private fun chatsCopy (userId: Int): MutableList<Chat> = usersChats
        .filter { it.userIds.first == userId || it.userIds.second == userId }.toMutableList()

    fun getUnreadChatsCount(userId: Int): Int {
        val chatsCopy = chatsCopy(userId)
        return chatsCopy.count { short(it.messages)}
    }

    fun getChats(userId: Int): MutableList<Chat> {
        val chatsCopy = chatsCopy(userId)
        chatsCopy.replaceAll { it.copy(messages = mutableListOf(it.messages.last()))}
        return chatsCopy
    }

    fun getMessagesFromChat(
        chatId: Int, fromMessageId: Int,
        messageCount: Int
    ): List<Message> {
        val result = mutableListOf<Message>()
        usersChats.forEach{ chat ->
            if(chat.id == chatId){
                chat.messages.forEachIndexed { mIndex, message ->
                    when (message.id) {
                        in fromMessageId until fromMessageId + messageCount -> {
                            result += message
                            chat.messages[mIndex] = chat.messages[mIndex].copy(unread = false)}
                        else -> return@forEachIndexed
                    }
                }
            }
        }
        return result
    }

    fun addMessage(
        currentUserId: Int, toId: Int,
        text: String
    ): Boolean{
        val currentChat = (usersChats.find { it.userIds == Pair(currentUserId,toId) }) ?:
            addChat(currentUserId, toId)
        currentChat.messages += Message(
            id = when {
                currentChat.messages.isEmpty() -> 1
                else -> currentChat.messages.size +1
            }, fromId = currentUserId, toId = toId, text = text)
        return true
    }

    fun deleteMessage(chatId: Int, messageId: Int){
        val chat = usersChats.find { it.id == chatId } ?:
        throw ChatDoesNotExistException
        val completed = chat.messages.removeIf { it.id == messageId }
        when{
            completed -> if(chat.messages.isEmpty()) deleteChat(chat.id)
            else ->  throw MessageDoesNotExistException
        }
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

