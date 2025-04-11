package com.example.asatabai.data

import com.google.android.gms.maps.model.LatLng
import java.io.BufferedReader
import java.io.InputStreamReader
import android.content.Context
import android.util.Log

object JeepneyRoutesData {
    var jeepneyRoutes: List<JeepneyRoute> = emptyList()
        private set
    fun initialize(context: Context) {
        jeepneyRoutes = listOf(
            JeepneyRoute(
                code = "01C",
                name = "Private - Colon",
                routeStops = parseCoordinatesFromFile(context, "coordinates01c")
            ),
            JeepneyRoute(
                code = "01K",
                name = "Urgello - Parkmall",
                routeStops = parseCoordinatesFromFile(context, "coordinates01k")
            ),
            JeepneyRoute(
                code = "02B",
                name = "South Bus Terminal - Colon",
                routeStops = parseCoordinatesFromFile(context, "coordinates02b")
            ),
            JeepneyRoute(
                code = "03A",
                name = "Mabolo - Carbon",
                routeStops = parseCoordinatesFromFile(context, "coordinates03a")
            ),
            JeepneyRoute(
                code = "03B",
                name = "Mabolo - Carbon",
                routeStops = parseCoordinatesFromFile(context, "coordinates03b")
            ),
            JeepneyRoute(
                code = "03L",
                name = "Mabolo - Carbon",
                routeStops = parseCoordinatesFromFile(context, "coordinates03l")
            ),
            JeepneyRoute(
                code = "03Q",
                name = "Ayala - SM",
                routeStops = parseCoordinatesFromFile(context, "coordinates03q")
            ),
            JeepneyRoute(
                code = "04B",
                name = "Lahug - Carbon",
                routeStops = parseCoordinatesFromFile(context, "coordinates04b")
            ),
            JeepneyRoute(
                code = "04H",
                name = "Pizza Housing - Carbon",
                routeStops = parseCoordinatesFromFile(context, "coordinates04h")
            ),
            JeepneyRoute(
                code = "04I",
                name = "Pizza Housing - Carbon",
                routeStops = parseCoordinatesFromFile(context, "coordinates04i")
            ),
            JeepneyRoute(
                code = "04L",
                name = "Lahug - Ayala",
                routeStops = parseCoordinatesFromFile(context, "coordinates04l")
            ),
            JeepneyRoute(
                code = "04M",
                name = "Lahug - Ayala",
                routeStops = parseCoordinatesFromFile(context, "coordinates04m")
            ),
            JeepneyRoute(
                code = "06B",
                name = "Guadalupe - Carbon",
                routeStops = parseCoordinatesFromFile(context, "coordinates06b")
            ),
            JeepneyRoute(
                code = "06C",
                name = "Guadalupe - Carbon",
                routeStops = parseCoordinatesFromFile(context, "coordinates06c")
            ),
            JeepneyRoute(
                code = "06G",
                name = "Guadalupe - Tabo-an",
                routeStops = parseCoordinatesFromFile(context, "coordinates06g")
            ),
            JeepneyRoute(
                code = "06H",
                name = "Guadalupe - SM",
                routeStops = parseCoordinatesFromFile(context, "coordinates06h")
            ),
            JeepneyRoute(
                code = "07B",
                name = "Banawa - Carbon",
                routeStops = parseCoordinatesFromFile(context, "coordinates07b")
            ),
            JeepneyRoute(
                code = "08F",
                name = "Alumnos - SM",
                routeStops = parseCoordinatesFromFile(context, "coordinates08f")
            ),
            JeepneyRoute(
                code = "08G",
                name = "Alumnos - Colon",
                routeStops = parseCoordinatesFromFile(context, "coordinates08g")
            ),
            JeepneyRoute(
                code = "09C",
                name = "Basak - ColonG",
                routeStops = parseCoordinatesFromFile(context, "coordinates09c")
            ),
            JeepneyRoute(
                code = "09F",
                name = "Basak - Ibabao",
                routeStops = parseCoordinatesFromFile(context, "coordinates09f")
            ),
            JeepneyRoute(
                code = "09G",
                name = "Basak - Colon",
                routeStops = parseCoordinatesFromFile(context, "coordinates09g")
            ),
            JeepneyRoute(
                code = "10F",
                name = "Bulacao - Colon",
                routeStops = parseCoordinatesFromFile(context, "coordinates10f")
            ),
            JeepneyRoute(
                code = "10G",
                name = "Pardo - Magallanes",
                routeStops = parseCoordinatesFromFile(context, "coordinates10g")
            ),
            JeepneyRoute(
                code = "10H",
                name = "Bulacao - SM",
                routeStops = parseCoordinatesFromFile(context, "coordinates10h")
            ),
            JeepneyRoute(
                code = "10M",
                name = "Bulacaoto - SM",
                routeStops = parseCoordinatesFromFile(context, "coordinates10m")
            ),
            JeepneyRoute(
                code = "11A",
                name = "Inayawan to Colon",
                routeStops = parseCoordinatesFromFile(context, "coordinates11a")
            ),
            JeepneyRoute(
                code = "12D",
                name = "Labangon - Cebu",
                routeStops = parseCoordinatesFromFile(context, "coordinates12d")
            ),
            JeepneyRoute(
                code = "12G",
                name = "Labangon - SM",
                routeStops = parseCoordinatesFromFile(context, "coordinates12g")
            ),
            JeepneyRoute(
                code = "12I",
                name = "Labangon - SM",
                routeStops = parseCoordinatesFromFile(context, "coordinates12i")
            ),
            JeepneyRoute(
                code = "12L",
                name = "Labangon - Ayala",
                routeStops = parseCoordinatesFromFile(context, "coordinates12l")
            ),
            JeepneyRoute(
                code = "13B",
                name = "Talamban - Carbon",
                routeStops = parseCoordinatesFromFile(context, "coordinates13b")
            ),
            JeepneyRoute(
                code = "13C",
                name = "Talamban - Colon",
                routeStops = parseCoordinatesFromFile(context, "coordinates13c")
            ),
            JeepneyRoute(
                code = "13H",
                name = "Pit-os - Mandaue",
                routeStops = parseCoordinatesFromFile(context, "coordinates03h")
            ),
            JeepneyRoute(
                code = "14D",
                name = "Ayala- Colon",
                routeStops = parseCoordinatesFromFile(context, "coordinates14d")
            ),
            JeepneyRoute(
                code = "17B",
                name = "Apas - Carbon",
                routeStops = parseCoordinatesFromFile(context, "coordinates17b")
            ),
            JeepneyRoute(
                code = "17C",
                name = "Apas - Carbon",
                routeStops = parseCoordinatesFromFile(context, "coordinates17c")
            ),
            JeepneyRoute(
                code = "17D",
                name = "Apas - Carbon",
                routeStops = parseCoordinatesFromFile(context, "coordinates17d")
            ),
            JeepneyRoute(
                code = "20A",
                name = "Ayala - Mandaue",
                routeStops = parseCoordinatesFromFile(context, "coordinates20aeven")
            ),
            JeepneyRoute(
                code = "20A",
                name = "Ibabao to Ayala Cebu",
                routeStops = parseCoordinatesFromFile(context, "coordinates20aodd")
            ),
            JeepneyRoute(
                code = "21A",
                name = "Mandaue - Cathedral",
                routeStops = parseCoordinatesFromFile(context, "coordinates21a")
            ),
            JeepneyRoute(
                code = "22A",
                name = "Mandaue - Cathedral",
                routeStops = parseCoordinatesFromFile(context, "coordinates22a")
            ),
            JeepneyRoute(
                code = "22D",
                name = "Mandaue - Cathedral",
                routeStops = parseCoordinatesFromFile(context, "coordinates22d")
            ),
            JeepneyRoute(
                code = "22I",
                name = "Mandaue - Gaisano Country Mall",
                routeStops = parseCoordinatesFromFile(context, "coordinates22i")
            ),
            JeepneyRoute(
                code = "23",
                name = "Parkmall - Punta Engaño",
                routeStops = parseCoordinatesFromFile(context, "coordinates23")
            ),
            JeepneyRoute(
                code = "23D",
                name = "Parkmall - Open",
                routeStops = parseCoordinatesFromFile(context, "coordinates23d")
            ),
            JeepneyRoute(
                code = "62B",
                name = "Pit-os - Carbon",
                routeStops = parseCoordinatesFromFile(context, "coordinates62b")
            ),
            JeepneyRoute(
                code = "MI-01A",
                name = "Punts Engaño - Mactan",
                routeStops = parseCoordinatesFromFile(context, "coordinatesmi01a")
            ),
            JeepneyRoute(
                code = "MI-02B",
                name = "Mandaue - Parkmall",
                routeStops = parseCoordinatesFromFile(context, "coordinatesmi02b")
            ),
            JeepneyRoute(
                code = "MI-03A",
                name = "Cordova - Lapu Lapu",
                routeStops = parseCoordinatesFromFile(context, "coordinatesmi03a")
            ),
            JeepneyRoute(
                code = "MI-03B",
                name = "Mactan - Cordova",
                routeStops = parseCoordinatesFromFile(context, "coordinatesmi03b")
            ),
            JeepneyRoute(
                code = "MI-04A",
                name = "Mactan = Tamiya",
                routeStops = parseCoordinatesFromFile(context, "coordinatesmi04a")
            ),
            JeepneyRoute(
                code = "MI-04B",
                name = "Mactan - MEPZ2",
                routeStops = parseCoordinatesFromFile(context, "coordinatesmi04b")
            ),
            JeepneyRoute(
                code = "MI-05A",
                name = "Mactan - Opon",
                routeStops = parseCoordinatesFromFile(context, "coordinatesmi05a")
            )
        )
    }
    private fun parseCoordinatesFromFile(context: Context, filename: String): List<LatLng> {
        val latLngList = mutableListOf<LatLng>()

        try {
            val inputStream = context.resources.openRawResource(
                context.resources.getIdentifier(filename, "raw", context.packageName)
            )

            BufferedReader(InputStreamReader(inputStream)).use { reader ->
                reader.forEachLine { line ->
                    val trimmed = line.trim()
                    if (trimmed.isEmpty() || trimmed.startsWith("//")) return@forEachLine

                    val parts = trimmed.split(",").map { it.trim() }
                    if (parts.size >= 2) {
                        val lat = parts[1].toDoubleOrNull()
                        val lng = parts[0].toDoubleOrNull()
                        println("$lat, $lng")
                        if (lat != null && lng != null) {
                            latLngList.add(LatLng(lat, lng))
                        } else {
                            Log.e("HELLOOO!!", "No lat or lng")
                        }
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("HELLOOO!!", e.toString())
            // Consider adding fallback coordinates if needed
        }

        return latLngList
    }
}