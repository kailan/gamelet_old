package pw.kmp.gamelet.util

import com.google.common.base.Preconditions
import org.bukkit.ChatColor
import org.bukkit.util.Vector

fun String.toBoolean() : Boolean {
    return when (this) {
        "true", "yes", "on" -> true
        "false", "no", "off" -> false
        else -> throw IllegalArgumentException("Invalid boolean: " + this)
    }
}

fun String.toColor() = ChatColor.valueOf(this.toUpperCase().replace(" ", "_"))

fun String.toVector2D(): Vector {
    val vec = this.replace(" ", "").split(",")
    Preconditions.checkArgument(vec.size == 2, "Invalid 2D vector")
    return Vector(vec[0].toDouble(), 0.0, vec[1].toDouble())
}

fun String.toVector3D(): Vector {
    val vec = this.replace(" ", "").split(",")
    Preconditions.checkArgument(vec.size == 3, "Invalid 3D vector")
    return Vector(vec[0].toDouble(), 0.0, vec[1].toDouble())
}
