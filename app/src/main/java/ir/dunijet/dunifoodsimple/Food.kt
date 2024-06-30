package ir.dunijet.dunifoodsimple

import android.media.Rating
import javax.security.auth.Subject

data class Food (
    val txtSubject: String ,
    val txtPrice : String ,
    val txtDistance : String ,
    val txtCity : String ,
    val urlImage : String ,
    val numOfRating: Int ,
    val rating : Float
)