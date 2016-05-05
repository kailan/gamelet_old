package pw.kmp.gamelet.i18n

import org.bukkit.ChatColor
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import pw.kmp.gamelet.players.Playerlet
import java.util.*

object Translation {

    lateinit var defaultLang: Locale
    lateinit var strings: YamlConfiguration

    fun initialize(file: YamlConfiguration, defaultLang: Locale) {
        this.defaultLang = defaultLang
        this.strings = file
    }

    fun getString(id: String, locale: Locale): String {
        val string = strings.getString(locale.language + "." + id) ?: return id
        return formatString(string)
    }

    fun formatString(text: String): String {
        return text.replace("&", ChatColor.COLOR_CHAR.toString())
    }

    infix fun of(id: String): TranslatedMessage = TranslatedMessage(id)

    class TranslatedMessage(val id: String) {

        var locale = defaultLang
        var args = HashMap<String, String>()

        infix fun to(locale: Locale): TranslatedMessage {
            this.locale = locale
            return this
        }

        infix fun to(player: Player): TranslatedMessage {
            // removed until fixed
            // return to(player.currentLocale)
            return to(defaultLang)
        }

        infix fun to(player: Playerlet): TranslatedMessage {
            return to(player.bukkit)
        }

        infix fun and(arg: String) = Argument(this, arg)
        infix fun with(arg: String) = Argument(this, arg)

        fun get(): String {
            var string = getString(id, locale)
            args.entries.forEach { string = string.replace("{" + it.key + "}", it.value) }
            return string
        }

        class Argument(val parent: TranslatedMessage, val id: String) {

            infix fun being(value: String): TranslatedMessage {
                parent.args.put(id, value)
                return parent
            }

        }

    }

}