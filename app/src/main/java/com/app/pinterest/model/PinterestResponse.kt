package com.app.pinterest.model

data class PinterestResponse(var id : String , var created_at : String , var width: Int , var height:Int ,
                             var color:String , var likes:Int , var liked_by_user:Boolean , var user:User ,
                             var urls : Urls , var categories : List<Category> , var links : Links__) {
}