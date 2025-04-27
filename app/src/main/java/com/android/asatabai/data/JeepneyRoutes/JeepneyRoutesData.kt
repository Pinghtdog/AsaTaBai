package com.android.asatabai.data.JeepneyRoutes

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
                routeStops = parseCoordinatesFromFile(context, "coordinates01c"),
                locationStops = parseCoordinatesFromFile(context, "locationpoints01c"),
                locationStopsDescription = listOf("University of San Carlos - South Campus, J. Alcantara Street, Cebu City, Cebu","Pier3, Cebu City, Cebu, Philippines","50 Colon St, Cebu City, Cebu, Philippines","University of San Carlos - South Campus, J. Alcantara Street, Cebu City, Cebu")
            ),
            JeepneyRoute(
                code = "01K",
                name = "Urgello - Parkmall",
                routeStops = parseCoordinatesFromFile(context, "coordinates01k"),
                locationStops = parseCoordinatesFromFile(context, "locationpoints01k"),
                locationStopsDescription = listOf("V Urgello St, Cebu City, Cebu","8WCG+6JC, Mandaue City, Cebu, Philippines","8WGJ+4XJ, Mantawe Ave, Tipolo, Mandaue City, Cebu, Philippines","Legaspi St, Cebu City, Cebu, Philippines","8V3V+94G, Cebu City, Cebu, Philippines","91 V. Urgello St, Cebu City, Cebu, Philippines")
            ),
            JeepneyRoute(
                code = "02B",
                name = "South Bus Terminal - Colon",
                routeStops = parseCoordinatesFromFile(context, "coordinates02b"),
                locationStops = parseCoordinatesFromFile(context, "locationpoints02b"),
                locationStopsDescription = listOf("Cebu Center Medical City, Cebu City, Cebu","South Bus Terminal - CSBT, Natalio B. Bacalso Avenue, Cebu City, Cebu","Pier 3, Cebu City, Cebu","105 Natalio B. Bacalso Ave, Cebu City, 6000 Cebu, Philippines")
            ),
            JeepneyRoute(
                code = "03A",
                name = "Mabolo - Carbon",
                routeStops = parseCoordinatesFromFile(context, "coordinates03a"),
                locationStops = parseCoordinatesFromFile(context, "locationpoints03a"),
                locationStopsDescription = listOf("8 F Cabahug Rd, Mandaue City, 6014 Cebu, Philippines","7VRX+VPC, Progreso St, Cebu City, Cebu, Philippines","M. C. Briones Street corner Plaridel Street (near Prime Asia Pawnshop), Carbon, Cebu City, 6000, Cebu, Cebu City, Cebu, Philippines","Cebu Technological University, Cebu City, Cebu, Philippines","8W77+PVH, M. J. Cuenco Ave, Cebu City, Cebu, Philippines","F Cabahug St, Mandaue City, Cebu, Philippines")
            ),
            JeepneyRoute(
                code = "03B",
                name = "Mabolo - Carbon",
                routeStops = parseCoordinatesFromFile(context, "coordinates03b"),
                locationStops = parseCoordinatesFromFile(context, "locationpoints03b"),
                locationStopsDescription = listOf("Sindulan St, Cebu City, Cebu, Philippines","Virginia, LGF, Kiosk , Metro Colon, Juna Luna Street , 6000 Cebu City , Cebu, Cebu City, Cebu, Philippines","Virginia, LGF, Kiosk , Metro Colon, Juna Luna Street , 6000 Cebu City , Cebu, Cebu City, Cebu, Philippines","Sindulan St, Cebu City, Cebu, Philippines")
            ),
            JeepneyRoute(
                code = "03L",
                name = "Mabolo - Carbon",
                routeStops = parseCoordinatesFromFile(context, "coordinates03l"),
                locationStops = parseCoordinatesFromFile(context, "locationpoints03l"),
                locationStopsDescription = listOf("P Cabantan, Cebu City, Cebu","79 M. C. Briones St, Cebu City, Cebu, Philippines","Carbon Public Market, Cebu City, Cebu","P Cabantan, Cebu City, Cebu")
            ),
            JeepneyRoute(
                code = "03Q",
                name = "Ayala - SM",
                routeStops = parseCoordinatesFromFile(context, "coordinates03q"),
                locationStops = parseCoordinatesFromFile(context, "locationpoints03q"),
                locationStopsDescription = listOf("Ayala Terraces, Biliran Road, Cebu City, Cebu","114 Juan Luna Ave Ext, Cebu City, Cebu, Philippines")
            ),
            JeepneyRoute(
                code = "04B",
                name = "Lahug - Carbon",
                routeStops = parseCoordinatesFromFile(context, "coordinates04b"),
                locationStops = parseCoordinatesFromFile(context, "locationpoints04b"),
                locationStopsDescription = listOf("Stephenson Street, Apas, Cebu City, Cebu","Carbon Public Market, Cebu City, Cebu, Philippines","Carbon Public Market, Cebu City, Cebu, Philippines","Stephenson Street, Apas, Cebu City, Cebu")
            ),
            JeepneyRoute(
                code = "04H",
                name = "Pizza Housing - Carbon",
                routeStops = parseCoordinatesFromFile(context, "coordinates04h"),
                locationStops = parseCoordinatesFromFile(context, "locationpoints04h"),
                locationStopsDescription = listOf("Busay, Cebu City, Cebu","79 M. C. Briones St, Cebu City, Cebu, Philippines","Carbon Market, M. C. Briones Street, Cebu City, Cebu","Busay, Cebu City, Cebu")
            ),
            JeepneyRoute(
                code = "04I",
                name = "Pizza Housing - Carbon",
                routeStops = parseCoordinatesFromFile(context, "coordinates04i"),
                locationStops = parseCoordinatesFromFile(context, "locationpoints04i"),
                locationStopsDescription = listOf("Busay, Cebu City, Cebu","7VRX+RVX, M. C. Briones St, Cebu City, Cebu, Philippines","35 M. C. Briones St, Cebu City, Cebu, Philippines","Busay, Cebu City, Cebu")
            ),
            JeepneyRoute(
                code = "04L",
                name = "Lahug - Ayala",
                routeStops = parseCoordinatesFromFile(context, "coordinates04l"),
                locationStops = parseCoordinatesFromFile(context, "locationpoints04l"),
                locationStopsDescription = listOf("504 Gorordo Ave, Cebu City, 6000 Cebu, Philippines","114 Juan Luna Ave Ext, Cebu City, Cebu, Philippines","114 Juan Luna Ave Ext, Cebu City, Cebu, Philippines","Lahug, Cebu City, Cebu, Philippines")
            ),
            JeepneyRoute(
                code = "04M",
                name = "Lahug - Ayala",
                routeStops = parseCoordinatesFromFile(context, "coordinates04m"),
                locationStops = parseCoordinatesFromFile(context, "locationpoints04m"),
                locationStopsDescription = listOf("JY Square Mall, Salinas Drive, Cebu City, Cebu","24 Archbishop Reyes Ave, Cebu City, Cebu, Philippines","Tintay Jeepney Terminal, 176 Archbishop Reyes Ave, Cebu City, Cebu, Philippines","JY Square Mall, Salinas Drive, Cebu City, Cebu")
            ),
            JeepneyRoute(
                code = "06B",
                name = "Guadalupe - Carbon",
                routeStops = parseCoordinatesFromFile(context, "coordinates06b"),
                locationStops = parseCoordinatesFromFile(context, "locationpoints06b"),
                locationStopsDescription = listOf("Guadalupe Church, Cebu City, Cebu","409 Kangha Building Osmena Blvd. cor Lapu-lapu St, Lungsod ng Cebu, Lalawigan ng Cebu, Philippines","Metropolitan Cebu Water District, Cebu City, Cebu","Guadalupe Church, Cebu City, Cebu")
            ),
            JeepneyRoute(
                code = "06C",
                name = "Guadalupe - Carbon",
                routeStops = parseCoordinatesFromFile(context, "coordinates06c"),
                locationStops = parseCoordinatesFromFile(context, "locationpoints06c"),
                locationStopsDescription = listOf("Guadalupe Church, Cebu City, Cebu","Metropolitan Cebu Water District, Cebu City, Cebu, Philippines","Metropolitan Cebu Water District, Cebu City, Cebu, Philippines","Guadalupe Church, Cebu City, Cebu")
            ),
            JeepneyRoute(
                code = "06G",
                name = "Guadalupe - Tabo-an",
                routeStops = parseCoordinatesFromFile(context, "coordinates06g"),
                locationStops = parseCoordinatesFromFile(context, "locationpoints06g"),
                locationStopsDescription = listOf("Guadalupe Church, Cebu City, Cebu","R Padilla St, Cebu City, Cebu, Philippines","R Padilla St, Cebu City, Cebu, Philippines","Guadalupe Church, Cebu City, Cebu")
            ),
            JeepneyRoute(
                code = "06H",
                name = "Guadalupe - SM",
                routeStops = parseCoordinatesFromFile(context, "coordinates06h"),
                locationStops = parseCoordinatesFromFile(context, "locationpoints06h"),
                locationStopsDescription = listOf("Guadalupe Church, Cebu City, Cebu","SM City Cebu, Juan Luna Avenue Extension, Cebu City, Cebu","SM City Cebu, Juan Luna Avenue Extension, Cebu City, Cebu","Guadalupe Church, Cebu City, Cebu")
            ),
            JeepneyRoute(
                code = "07B",
                name = "Banawa - Carbon",
                routeStops = parseCoordinatesFromFile(context, "coordinates07b"),
                locationStops = parseCoordinatesFromFile(context, "locationpoints07b"),
                locationStopsDescription = listOf("Court of Appeals, Cebu City, Cebu, Philippines","MC Briones, 7WR2+V33, Cebu City, Cebu, Philippines","35 M. C. Briones St, Cebu City, Cebu, Philippines","Court of Appeals, Cebu City, Cebu, Philippines")
            ),
            JeepneyRoute(
                code = "08G",
                name = "Alumnos - Colon",
                routeStops = parseCoordinatesFromFile(context, "coordinates08g"),
                locationStops = parseCoordinatesFromFile(context, "locationpoints08g"),
                locationStopsDescription = listOf("SMOKE'N MIX GRILL HOUSE, Cabreros Street, Cebu City, Cebu","Colon Street, Cebu City, Cebu","Colon Obelisk, Colon Street, Cebu City, Cebu","Mj Cuenca Ave, Cebu City, Cebu, Philippines", "Mj Cuenca Ave, Cebu City, Cebu, Philippines", "SMOKE'N MIX GRILL HOUSE, Cabreros Street, Cebu City, Cebu")
            ),
            JeepneyRoute(
                code = "09C",
                name = "Basak - ColonG",
                routeStops = parseCoordinatesFromFile(context, "coordinates09c"),
                locationStops = parseCoordinatesFromFile(context, "locationpoints09c"),
                locationStopsDescription = listOf("Quiot, Cebu City, Cebu, Philippines","50 Colon St, Cebu City, Cebu, Philippines","50 Colon St, Cebu City, Cebu, Philippines","Quiot, Cebu City, Cebu, Philippines")
            ),
            JeepneyRoute(
                code = "09F",
                name = "Basak - Ibabao",
                routeStops = parseCoordinatesFromFile(context, "coordinates09f"),
                locationStops = parseCoordinatesFromFile(context, "locationpoints09f"),
                locationStopsDescription = listOf("7VP4+FF2, 92 E Sabellano Street, Cebu City, 6000 Cebu, Philippines","7WV3+R27, Osmeña Blvd, Cebu City, Cebu, Philippines","Basilica Minore del Santo Niño de Cebu Pilgrim Center, Osmeña Boulevard, Cebu City, Cebu","7VP4+FF2, E Sabellano Street, Cebu City, 6000 Cebu, Philippines")
            ),
            JeepneyRoute(
                code = "09G",
                name = "Basak - Colon",
                routeStops = parseCoordinatesFromFile(context, "coordinates09g"),
                locationStops = parseCoordinatesFromFile(context, "locationpoints09g"),
                locationStopsDescription = listOf("130 E Sabellano Street, Cebu City, 6000 Cebu, Philippines","Pres. Osmeña Boulevard, 7WV3+Q3X, Cebu City, Cebu, Philippines","City Savings Financial Plaza, P. Burgos St, Cebu City, Cebu, Philippines","7VR5+2MM, E Sabellano Street, Cebu City, 6000 Cebu, Philippines")
            ),
            JeepneyRoute(
                code = "10F",
                name = "Bulacao - Colon",
                routeStops = parseCoordinatesFromFile(context, "coordinates10f"),
                locationStops = parseCoordinatesFromFile(context, "locationpoints10f"),
                locationStopsDescription = listOf("Bulacao, Cebu City, Cebu, Philippines","16-18 Junquera St, Cebu City, 6000 Cebu, Philippines","23 Junquera St, Cebu City, 6000 Cebu, Philippines","Bulacao, Cebu City, Cebu, Philippines")
            ),
            JeepneyRoute(
                code = "10G",
                name = "Pardo - Magallanes",
                routeStops = parseCoordinatesFromFile(context, "coordinates10g"),
                locationStops = parseCoordinatesFromFile(context, "locationpoints10g"),
                locationStopsDescription = listOf("Bulacao, Cebu City, Cebu, Philippines","16-18 Junquera St, Cebu City, 6000 Cebu, Philippines","23 Junquera St, Cebu City, 6000 Cebu, Philippines","Bulacao, Cebu City, Cebu, Philippines")
            ),
            JeepneyRoute(
                code = "10H",
                name = "Bulacao - SM",
                routeStops = parseCoordinatesFromFile(context, "coordinates10h"),
                locationStops = parseCoordinatesFromFile(context, "locationpoints10h"),
                locationStopsDescription = listOf("Bulacao, Cebu City, Cebu, Philippines","8W79+HRX Strand Tower, F. Cabahug St, Cebu City, 6000 Cebu, Philippines","8W79+HX8, F. Cabahug St, Cebu City, Cebu, Philippines","Bulacao, Cebu City, Cebu, Philippines")
            ),
            JeepneyRoute(
                code = "10M",
                name = "Bulacaoto - SM",
                routeStops = parseCoordinatesFromFile(context, "coordinates10m"),
                locationStops = parseCoordinatesFromFile(context, "locationpoints10m"),
                locationStopsDescription = listOf("7RFX+JQ9, Cebu S Rd, Cebu City, Cebu, Philippines","SM City Cebu Jeepney Bay Terminal, Juan Luna Avenue, Cebu City, 6000 Cebu, Philippines","Sm City Cebu, Cebu City, Cebu, Philippines","155, 6000 Natalio B. Bacalso Ave, Cebu City, Cebu, Philippines")
            ),
            JeepneyRoute(
                code = "11A",
                name = "Inayawan to Colon",
                routeStops = parseCoordinatesFromFile(context, "coordinates11a"),
                locationStops = parseCoordinatesFromFile(context, "locationpoints11a"),
                locationStopsDescription = listOf("Inayawan Church, Cebu City, Cebu","622 Colon St, Cebu City, 6000 Cebu, Philippines","66 Colon St, Cebu City, 6000 Cebu, Philippines","Inayawan Church, Cebu City, Cebu")
            ),
            JeepneyRoute(
                code = "12D",
                name = "Labangon - Cebu",
                routeStops = parseCoordinatesFromFile(context, "coordinates12d"),
                locationStops = parseCoordinatesFromFile(context, "locationpoints12d"),
                locationStopsDescription = listOf("Punta Princesa, Cebu City, Cebu, Philippines","Humabon, 7WW3+CG6, Cebu City, Cebu, Philippines","F.Urdaneta St, Cebu City, Cebu, Philippines","Punta Princesa, Cebu City, Cebu, Philippines")
            ),
            JeepneyRoute(
                code = "12G",
                name = "Labangon - SM",
                routeStops = parseCoordinatesFromFile(context, "coordinates12g"),
                locationStops = parseCoordinatesFromFile(context, "locationpoints12g"),
                locationStopsDescription = listOf("8V29+5RR, Francisco Llamas St, Cebu City, 6000 Cebu, Philippines","6000 F. Cabahug St, Cebu City, Cebu, Philippines","SM City Cebu, Juan Luna Avenue Extension, Cebu City, Cebu","8V29+5RR, Francisco Llamas St, Cebu City, 6000 Cebu, Philippines")
            ),
            JeepneyRoute(
                code = "12I",
                name = "Labangon - SM",
                routeStops = parseCoordinatesFromFile(context, "coordinates12i"),
                locationStops = parseCoordinatesFromFile(context, "locationpoints12i"),
                locationStopsDescription = listOf("Punta Princesa, Cebu City, Cebu, Philippines","SM City Cebu, Juan Luna Avenue Extension, Cebu City, Cebu","SM City Cebu, Juan Luna Avenue Extension, Cebu City, Cebu","Punta Princesa, Cebu City, Cebu, Philippines")
            ),
            JeepneyRoute(
                code = "12L",
                name = "Labangon - Ayala",
                routeStops = parseCoordinatesFromFile(context, "coordinates12l"),
                locationStops = parseCoordinatesFromFile(context, "locationpoints12l"),
                locationStopsDescription = listOf("7VVC+P2F, Cebu City, Cebu, Philippines","Francisco Llamas Street, Cebu City, Cebu","1 Luzon Ave, Cebu City, 6000 Cebu, Philippines","Ayala Center Cebu, Archbishop Reyes Avenue, Cebu City, Cebu","7VVC+P35, Cebu City, Cebu, Philippines")
            ),
            JeepneyRoute(
                code = "13B",
                name = "Talamban - Carbon",
                routeStops = parseCoordinatesFromFile(context, "coordinates13b"),
                locationStops = parseCoordinatesFromFile(context, "locationpoints13b"),
                locationStopsDescription = listOf("9WCF+8XC, H Abellana, Cebu City, 6000 Cebu, Philippines","79 M. C. Briones St, Cebu City, Cebu, Philippines","Carbon Market, M. C. Briones Street, Cebu City, Cebu","Tintay Jeepney Terminal, H Abellana, Cebu City, Cebu")
            ),
            JeepneyRoute(
                code = "13C",
                name = "Talamban - Colon",
                routeStops = parseCoordinatesFromFile(context, "coordinates13c"),
                locationStops = parseCoordinatesFromFile(context, "locationpoints13c"),
                locationStopsDescription = listOf("Tintay Jeepney Terminal, H Abellana, Cebu City, Cebu","Colonnade Mall, Colon Street, Cebu City, Cebu","Colonnade Mall, Colon Street, Cebu City, Cebu","Tintay Jeepney Terminal, H Abellana, Cebu City, Cebu")
            ),
            JeepneyRoute(
                code = "13H",
                name = "Pit-os - Mandaue",
                routeStops = parseCoordinatesFromFile(context, "coordinates03h"),
                locationStops = parseCoordinatesFromFile(context, "locationpoints13h"),
                locationStopsDescription = listOf("Pitos, Cebu City, Cebu, Philippines","New Mandaue City Public Market, Mandaue City, Cebu","New Mandaue City Public Market, Mandaue City, Cebu","Pitos, Cebu City, Cebu, Philippines")
            ),
            JeepneyRoute(
                code = "14D",
                name = "Ayala- Colon",
                routeStops = parseCoordinatesFromFile(context, "coordinates14d"),
                locationStops = parseCoordinatesFromFile(context, "locationpoints14d"),
                locationStopsDescription = listOf("Ayala Public Utility Vehicle Terminal, Archbishop Reyes Avenue, Cebu City, Cebu","179 Vicente Gullas St, Cebu City, Cebu, Philippines","7WW2+X77, Colon St, Cebu City, Cebu, Philippines","Legaspi St, Cebu City, Cebu, Philippines","Ayala Public Utility Vehicle Terminal, Archbishop Reyes Avenue, Cebu City, Cebu")
            ),
            JeepneyRoute(
                code = "17B",
                name = "Apas - Carbon",
                routeStops = parseCoordinatesFromFile(context, "coordinates17b"),
                locationStops = parseCoordinatesFromFile(context, "locationpoints17b"),
                locationStopsDescription = listOf("11 Wilson St, Apas, Cebu City, Cebu, Philippines","79 M. C. Briones St, Cebu City, Cebu, Philippines","35 M. C. Briones St, Cebu City, Cebu, Philippines","11 Wilson St, Apas, Cebu City, Cebu, Philippines")
            ),
            JeepneyRoute(
                code = "17C",
                name = "Apas - Carbon",
                routeStops = parseCoordinatesFromFile(context, "coordinates17c"),
                locationStops = parseCoordinatesFromFile(context, "locationpoints17c"),
                locationStopsDescription = listOf("11 Wilson St, Apas, Cebu City, Cebu, Philippines","7VRX+RMQ, Cebu City, Cebu, Philippines","Carbon Public Market, Cebu City, Cebu, Philippines","11 Wilson St, Apas, Cebu City, Cebu, Philippines")
            ),
            JeepneyRoute(
                code = "17D",
                name = "Apas - Carbon",
                routeStops = parseCoordinatesFromFile(context, "coordinates17d"),
                locationStops = parseCoordinatesFromFile(context, "locationpoints17d"),
                locationStopsDescription = listOf("11 Wilson St, Apas, Cebu City, Cebu, Philippines","35 M. C. Briones St, Cebu City, Cebu, Philippines","24 F. Gonzales St, Cebu City, Cebu, Philippines","11 Wilson St, Apas, Cebu City, Cebu, Philippines")
            ),
            JeepneyRoute(
                code = "20A",
                name = "Ayala - Mandaue",
                routeStops = parseCoordinatesFromFile(context, "coordinates20aeven"),
                locationStops = parseCoordinatesFromFile(context, "locationpoints20aeven"),
                locationStopsDescription = listOf("Ayala PUJ Terminal, Archbishop Reyes Avenue, Cebu City, Cebu","8WGQ+55W, Ouano Ave, Mandaue City, 6014 Cebu, Philippines","8WRX+7GX, Mandaue City, Cebu, Philippines","DFA, Mandaue City, Cebu","Ayala PUJ Terminal, Archbishop Reyes Avenue, Cebu City, Cebu")
            ),
            JeepneyRoute(
                code = "20A",
                name = "Ibabao to Ayala Cebu",
                routeStops = parseCoordinatesFromFile(context, "coordinates20aodd"),
                locationStops = parseCoordinatesFromFile(context, "locationpoints20aodd"),
                locationStopsDescription = listOf("Ayala Public Utility Vehicle Terminal, Archbishop Reyes Avenue, Cebu City, Cebu","246 R.Colina, Mandaue City, 6014 Cebu, Philippines","8WQW+GC2, S.B.Cabahug, Mandaue City, Cebu, Philippines","Ayala Public Utility Vehicle Terminal, Archbishop Reyes Avenue, Cebu City, Cebu")
            ),
            JeepneyRoute(
                code = "21A",
                name = "Mandaue - Cathedral",
                routeStops = parseCoordinatesFromFile(context, "coordinates21a"),
                locationStops = parseCoordinatesFromFile(context, "locationpoints21a"),
                locationStopsDescription = listOf("Pacific Mall Mandaue, U.N. Avenue, Mandaue City, Cebu","7WW3+9C6, Cebu City, Cebu, Philippines","7WW3+7FJ, F. Urdaneta St, Cebu City, Cebu, Philippines","Pacific Mall Mandaue, U.N. Avenue, Mandaue City, Cebu")
            ),
            JeepneyRoute(
                code = "22A",
                name = "Mandaue - Cathedral",
                routeStops = parseCoordinatesFromFile(context, "coordinates22a"),
                locationStops = parseCoordinatesFromFile(context, "locationpoints22a"),
                locationStopsDescription = listOf("228 C.M.Cabahug, Mandaue City, Cebu, Philippines","F.Urdaneta St, Cebu City, Cebu, Philippines","Cebu Metropolitan Cathedral, Mabini Street, Cebu City, Cebu","Cm Cabahug, Mandaue City, Cebu, Philippines")
            ),
            JeepneyRoute(
                code = "22D",
                name = "Mandaue - Cathedral",
                routeStops = parseCoordinatesFromFile(context, "coordinates22d"),
                locationStops = parseCoordinatesFromFile(context, "locationpoints22d"),
                locationStopsDescription = listOf("Cm Cabahug, Mandaue City, Cebu","F.Urdaneta St, Cebu City, Cebu, Philippines","Cebu Metropolitan Cathedral, Mabini Street, Cebu City, Cebu","Cm Cabahug, Mandaue City, Cebu")
            ),
            JeepneyRoute(
                code = "22I",
                name = "Mandaue - Gaisano Country Mall",
                routeStops = parseCoordinatesFromFile(context, "coordinates22i"),
                locationStops = parseCoordinatesFromFile(context, "locationpoints22i"),
                locationStopsDescription = listOf("8WFR+JRR, A. Soriano Ave, Mandaue City, Cebu, Philippines","Gaisano Country Mall, Banilad Road, Apas, Cebu City, Cebu","Gaisano Country Mall, Banilad Road, Apas, Cebu City, Cebu","8WFR+JRR, A. Soriano Ave, Mandaue City, Cebu, Philippines")
            ),
            JeepneyRoute(
                code = "23",
                name = "Parkmall - Punta Engaño",
                routeStops = parseCoordinatesFromFile(context, "coordinates23"),
                locationStops = parseCoordinatesFromFile(context, "locationpoints23"),
                locationStopsDescription = listOf("AmiSa Private Residences, Punta Engaño Road, Lapu-Lapu City, Cebu","Parkmall, Tipolo, Mandaue City, Cebu","8WGP+36Q, C.D.Seno, Tipolo, Mandaue City, Cebu, Philippines","AmiSa Private Residences, Punta Engaño Road, Lapu-Lapu City, Cebu")
            ),
            JeepneyRoute(
                code = "23D",
                name = "Parkmall - Open",
                routeStops = parseCoordinatesFromFile(context, "coordinates23d"),
                locationStops = parseCoordinatesFromFile(context, "locationpoints23d"),
                locationStopsDescription = listOf("Parkmall, Tipolo, Mandaue City, Cebu","8W6X+HJ2, Lopez - Jaena St, Lapu-Lapu City, Cebu, Philippines","Lapu-Lapu City PUJ Terminal, M.L. Quezon National Highway, Lapu-Lapu City, Cebu","Parkmall, Tipolo, Mandaue City, Cebu")
            ),
            JeepneyRoute(
                code = "62B",
                name = "Pit-os - Carbon",
                routeStops = parseCoordinatesFromFile(context, "coordinates62b"),
                locationStops = parseCoordinatesFromFile(context, "locationpoints62b"),
                locationStopsDescription = listOf("9WVC+4XP, Cebu City, 6000 Cebu, Philippines","7VRX+RMQ, Cebu City, Cebu, Philippines","Carbon Public Market, Cebu City, Cebu, Philippines","Pit-os, Cebu")
            ),
            JeepneyRoute(
                code = "MI-01A",
                name = "Punts Engaño - Mactan",
                routeStops = parseCoordinatesFromFile(context, "coordinatesmi01a"),
                locationStops = parseCoordinatesFromFile(context, "locationpointsmi01a"),
                locationStopsDescription = listOf("Ompad Street, Lapu-Lapu City, Cebu","Amisa Residences, Lapu-Lapu City, Cebu")
            ),
            JeepneyRoute(
                code = "MI-02B",
                name = "Mandaue - Parkmall",
                routeStops = parseCoordinatesFromFile(context, "coordinatesmi02b"),
                locationStops = parseCoordinatesFromFile(context, "locationpointsmi02b"),
                locationStopsDescription = listOf("Imperial Palace Waterpark Resort & Spa, Maribago, Lapu-Lapu City, Cebu","8WGM+6FX, Park Mall Dr, Tipolo, Mandaue City, Cebu, Philippines","8WGM+99P, Tipolo, Mandaue City, Cebu, Philippines","Imperial Palace Waterpark Resort & Spa, Maribago, Lapu-Lapu City, Cebu")
            ),
            JeepneyRoute(
                code = "MI-03A",
                name = "Cordova - Lapu Lapu",
                routeStops = parseCoordinatesFromFile(context, "coordinatesmi03a"),
                locationStops = parseCoordinatesFromFile(context, "locationpointsmi03a"),
                locationStopsDescription = listOf("Lapulapu City Public Market, Lapu-Lapu City, Cebu","7W2X+2R5, Martin Francisco St, Cordova, Cebu, Philippines")
            ),
            JeepneyRoute(
                code = "MI-03B",
                name = "Mactan - Cordova",
                routeStops = parseCoordinatesFromFile(context, "coordinatesmi03b"),
                locationStops = parseCoordinatesFromFile(context, "locationpointsmi03b"),
                locationStopsDescription = listOf("Mepz1 2nd Avenue Rd, MEPZ 1, Lapu-Lapu City, Cebu, Philippines","Cordova, Cordova, Cebu, Philippines")
            ),
            JeepneyRoute(
                code = "MI-04A",
                name = "Mactan = Tamiya",
                routeStops = parseCoordinatesFromFile(context, "coordinatesmi04a"),
                locationStops = parseCoordinatesFromFile(context, "locationpointsmi04a"),
                locationStopsDescription = listOf("Tamiya Terminal, Mepz 2 Compound, Lapu-Lapu City, Cebu","J Centre Mall, A. S. Fortuna Street, Mandaue City, Cebu")
            ),
            JeepneyRoute(
                code = "MI-04B",
                name = "Mactan - MEPZ2",
                routeStops = parseCoordinatesFromFile(context, "coordinatesmi04b"),
                locationStops = parseCoordinatesFromFile(context, "locationpointsmi04b"),
                locationStopsDescription = listOf("8XJQ+M6M, Lapu-Lapu City, Cebu, Philippines","Tamiya Terminal, Mepz 2 Compound, Lapu-Lapu City, Cebu")
            ),
            JeepneyRoute(
                code = "MI-05A",
                name = "Mactan - Opon",
                routeStops = parseCoordinatesFromFile(context, "coordinatesmi05a"),
                locationStops = parseCoordinatesFromFile(context, "locationpointsmi05a"),
                locationStopsDescription = listOf("Mactan Cebu International Airport Terminal, Lapu-Lapu City, 6016 Cebu, Philippines","655 B.M. Dimataga St, Poblacion, Lapu-Lapu City, 6015 Cebu, Philippines","Opon Plaza, Lapu-Lapu City, Cebu, Philippines","Mactan Cebu International Airport Terminal, Lapu-Lapu City, 6016 Cebu, Philippines")
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
                        //println("$lat, $lng")
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