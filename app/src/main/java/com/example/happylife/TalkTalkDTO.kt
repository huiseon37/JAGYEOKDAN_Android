package com.example.happylife

import java.sql.Timestamp

data class TalkTalkDTO (
    var title : String? = null,
    var tag : String? = null,
    var contents : String? = null,
    var ImageUri : String? = null,
    var uid : String? = null,
    var userID: String? = null,
    var timestamp: Long? = null,
    var favoriteCount : Int = 0,
    var favorites : Map<String, Boolean> = HashMap()) {
    data class Comment(var uid : String? = null,
                       var userID : String? = null,
                       var comment : String? = null,
                       var timestamp: Long? = null)
}