package ru.netology

 class Chat(
    val id: Int,
    val userIds: Pair<Int, Int>,
    var messages: MutableList<Message> = mutableListOf()
){
     fun copy(id: Int,
              userIds: Pair<Int, Int>,
              messages: MutableList<Message>): Chat{
         return Chat(id = id, userIds = userIds, messages = messages)
     }
     fun containsPair(pair: Pair<Int, Int>): Boolean {
        return when (pair) {
            userIds -> true
            Pair(userIds.second, userIds.first) -> true
            else -> false
        }
     }

     fun containsUserId(id: Int): Boolean{
         return when (id) {
             userIds.first -> true
             userIds.second -> true
             else -> false
         }
     }

     override fun toString(): String {
         return "Chat ID = $id, $messages"
     }
}
