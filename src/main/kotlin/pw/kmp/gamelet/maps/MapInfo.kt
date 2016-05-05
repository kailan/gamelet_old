package pw.kmp.gamelet.maps

data class MapInfo(val name: String, val version: String, val description: String,
                   val authors: List<Contributor>, val contributors: List<Contributor>?) {

    data class Contributor(val name: String, var contribution: String?)

}