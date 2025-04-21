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
                direction = "Directions from University of San Carlos - South Campus, J. Alcantara Street, Cebu City, Cebu to University of San Carlos - South Campus, J. Alcantara Street, Cebu City, Cebu",
                routeStops = parseCoordinatesFromFile(context, "coordinates01c")
            ),
            JeepneyRoute(
                code = "01K",
                name = "Urgello - Parkmall",
                direction = "Directions from V Urgello St, Cebu City, Cebu to 91 V. Urgello St, Cebu City, Cebu, Philippines",
                routeStops = parseCoordinatesFromFile(context, "coordinates01k")
            ),
            JeepneyRoute(
                code = "02B",
                name = "South Bus Terminal - Colon",
                direction = "Directions from Cebu Center Medical City, Cebu City, Cebu to 105 Natalio B. Bacalso Ave, Cebu City, 6000 Cebu, Philippines",
                routeStops = parseCoordinatesFromFile(context, "coordinates02b")
            ),
            JeepneyRoute(
                code = "03A",
                name = "Mabolo - Carbon",
                direction = "Directions from 8 F Cabahug Rd, Mandaue City, 6014 Cebu, Philippines to F Cabahug St, Mandaue City, Cebu, Philippines",
                routeStops = parseCoordinatesFromFile(context, "coordinates03a")
            ),
            JeepneyRoute(
                code = "03B",
                name = "Mabolo - Carbon",
                direction = "Directions from Sindulan St, Cebu City, Cebu, Philippines to Virginia, LGF, Kiosk , Metro Colon, Juna Luna Street , 6000 Cebu City , Cebu, Cebu City, Cebu, Philippines",
                routeStops = parseCoordinatesFromFile(context, "coordinates03b1")
            ),
            JeepneyRoute(
                code = "03B",
                name = "Mabolo - Carbon",
                direction = "Directions from Virginia, LGF, Kiosk , Metro Colon, Juna Luna Street , 6000 Cebu City , Cebu, Cebu City, Cebu, Philippines to Sindulan St, Cebu City, Cebu, Philippines",
                routeStops = parseCoordinatesFromFile(context, "coordinates03b2")
            ),
            JeepneyRoute(
                code = "03L",
                name = "Mabolo - Carbon",
                direction = "Directions from P Cabantan, Cebu City, Cebu to 79 M. C. Briones St, Cebu City, Cebu, Philippines",
                routeStops = parseCoordinatesFromFile(context, "coordinates03l1")
            ),
            JeepneyRoute(
                code = "03L",
                name = "Mabolo - Carbon",
                direction = "Directions from Carbon Public Market, Cebu City, Cebu to P Cabantan, Cebu City, Cebu",
                routeStops = parseCoordinatesFromFile(context, "coordinates03l2")
            ),
            JeepneyRoute(
                code = "03Q",
                name = "Ayala - SM",
                direction = "Directions from Ayala Terraces, Biliran Road, Cebu City, Cebu to 114 Juan Luna Ave Ext, Cebu City, Cebu, Philippines",
                routeStops = parseCoordinatesFromFile(context, "coordinates03q")
            ),
            JeepneyRoute(
                code = "04B",
                name = "Lahug - Carbon",
                direction = "Apas to Carbon",
                routeStops = parseCoordinatesFromFile(context, "coordinates04b1")
            ),
            JeepneyRoute(
                code = "04B",
                name = "Lahug - Carbon",
                direction = "Carbon to Apas",
                routeStops = parseCoordinatesFromFile(context, "coordinates04b2")
            ),
            JeepneyRoute(
                code = "04H",
                name = "Pizza Housing - Carbon",
                direction = "Directions from Busay, Cebu City, Cebu to 79 M. C. Briones St, Cebu City, Cebu, Philippines",
                routeStops = parseCoordinatesFromFile(context, "coordinates04h1")
            ),
            JeepneyRoute(
                code = "04H",
                name = "Pizza Housing - Carbon",
                direction = "Directions from Carbon Market, M. C. Briones Street, Cebu City, Cebu to Busay, Cebu City, Cebu",
                routeStops = parseCoordinatesFromFile(context, "coordinates04h2")
            ),
            JeepneyRoute(
                code = "04I",
                name = "Pizza Housing - Carbon",
                direction = "Busay Lahug to Carbon",
                routeStops = parseCoordinatesFromFile(context, "coordinates04i1")
            ),
            JeepneyRoute(
                code = "04I",
                name = "Pizza Housing - Carbon",
                direction = "Carbon to Lahug to Busay",
                routeStops = parseCoordinatesFromFile(context, "coordinates04i2")
            ),
            JeepneyRoute(
                code = "04L",
                name = "Lahug - Ayala",
                direction = "Lahug to SM Cebu",
                routeStops = parseCoordinatesFromFile(context, "coordinates04l1")
            ),
            JeepneyRoute(
                code = "04L",
                name = "Lahug - Ayala",
                direction = "SM Cebu to Lahug",
                routeStops = parseCoordinatesFromFile(context, "coordinates04l2")
            ),
            JeepneyRoute(
                code = "04M",
                name = "Lahug - Ayala",
                direction = "Directions from JY Square Mall, Salinas Drive, Cebu City, Cebu to 24 Archbishop Reyes Ave, Cebu City, Cebu, Philippines",
                routeStops = parseCoordinatesFromFile(context, "coordinates04m1")
            ),
            JeepneyRoute(
                code = "04M",
                name = "Lahug - Ayala",
                direction = "Directions from Tintay Jeepney Terminal, 176 Archbishop Reyes Ave, Cebu City, Cebu, Philippines to JY Square Mall, Salinas Drive, Cebu City, Cebu",
                routeStops = parseCoordinatesFromFile(context, "coordinates04m2")
            ),
            JeepneyRoute(
                code = "06B",
                name = "Guadalupe - Carbon",
                direction = "Directions from Guadalupe Church, Cebu City, Cebu to 409 Kangha Building Osmena Blvd. cor Lapu-lapu St, Lungsod ng Cebu, Lalawigan ng Cebu, Philippines",
                routeStops = parseCoordinatesFromFile(context, "coordinates06b1")
            ),
            JeepneyRoute(
                code = "06B",
                name = "Guadalupe - Carbon",
                direction = "Directions from Metropolitan Cebu Water District, Cebu City, Cebu to Guadalupe Church, Cebu City, Cebu",
                routeStops = parseCoordinatesFromFile(context, "coordinates06b2")
            ),
            JeepneyRoute(
                code = "06C",
                name = "Guadalupe - Carbon",
                direction = "Directions from Guadalupe Church, Cebu City, Cebu to Metropolitan Cebu Water District, Cebu City, Cebu, Philippines",
                routeStops = parseCoordinatesFromFile(context, "coordinates06c1")
            ),
            JeepneyRoute(
                code = "06C",
                name = "Guadalupe - Carbon",
                direction = "Directions from Metropolitan Cebu Water District, Cebu City, Cebu, Philippines to Guadalupe Church, Cebu City, Cebu",
                routeStops = parseCoordinatesFromFile(context, "coordinates06c2")
            ),
            JeepneyRoute(
                code = "06G",
                name = "Guadalupe - Tabo-an",
                direction = "Directions from Guadalupe Church, Cebu City, Cebu to R Padilla St, Cebu City, Cebu, Philippines",
                routeStops = parseCoordinatesFromFile(context, "coordinates06g1")
            ),
            JeepneyRoute(
                code = "06G",
                name = "Guadalupe - Tabo-an",
                direction = "Directions from R Padilla St, Cebu City, Cebu, Philippines to Guadalupe Church, Cebu City, Cebu",
                routeStops = parseCoordinatesFromFile(context, "coordinates06g2")
            ),
            JeepneyRoute(
                code = "06H",
                name = "Guadalupe - SM",
                direction = "Directions from Guadalupe Church, Cebu City, Cebu to SM City Cebu, Juan Luna Avenue Extension, Cebu City, Cebu",
                routeStops = parseCoordinatesFromFile(context, "coordinates06h1")
            ),
            JeepneyRoute(
                code = "06H",
                name = "Guadalupe - SM",
                direction = "Directions from SM City Cebu, Juan Luna Avenue Extension, Cebu City, Cebu to Guadalupe Church, Cebu City, Cebu",
                routeStops = parseCoordinatesFromFile(context, "coordinates06h2")
            ),
            JeepneyRoute(
                code = "07B",
                name = "Banawa - Carbon",
                direction = "Directions from Court of Appeals, Cebu City, Cebu, Philippines to MC Briones, 7WR2+V33, Cebu City, Cebu, Philippines",
                routeStops = parseCoordinatesFromFile(context, "coordinates07b1",)
            ),
            JeepneyRoute(
                code = "07B",
                name = "Banawa - Carbon",
                direction = "Directions from 35 M. C. Briones St, Cebu City, Cebu, Philippines to Court of Appeals, Cebu City, Cebu, Philippines",
                routeStops = parseCoordinatesFromFile(context, "coordinates07b2")
            ),
            JeepneyRoute(
                code = "08G",
                name = "Alumnos - Colon",
                direction = "Directions from SMOKE'N MIX GRILL HOUSE, Cabreros Street, Cebu City, Cebu to Mj Cuenca Ave, Cebu City, Cebu, Philippines",
                routeStops = parseCoordinatesFromFile(context, "coordinates08g1")
            ),
            JeepneyRoute(
                code = "08G",
                name = "Alumnos - Colon",
                direction = "Directions from Mj Cuenca Ave, Cebu City, Cebu, Philippines to SMOKE'N MIX GRILL HOUSE, Cabreros Street, Cebu City, Cebu",
                routeStops = parseCoordinatesFromFile(context, "coordinates08g2")
            ),
            JeepneyRoute(
                code = "09C",
                name = "Basak - ColonG",
                direction = "Directions from Quiot, Cebu City, Cebu, Philippines to 50 Colon St, Cebu City, Cebu, Philippines",
                routeStops = parseCoordinatesFromFile(context, "coordinates09c1")
            ),
            JeepneyRoute(
                code = "09C",
                name = "Basak - ColonG",
                direction = "Directions from 50 Colon St, Cebu City, Cebu, Philippines to Quiot, Cebu City, Cebu, Philippines",
                routeStops = parseCoordinatesFromFile(context, "coordinates09c2")
            ),
            JeepneyRoute(
                code = "09F",
                name = "Basak - Ibabao",
                direction = "Directions from 7VP4+FF2, 92 E Sabellano Street, Cebu City, 6000 Cebu, Philippines to 7WV3+R27, Osmeña Blvd, Cebu City, Cebu, Philippines",
                routeStops = parseCoordinatesFromFile(context, "coordinates09f1")
            ),
            JeepneyRoute(
                code = "09F",
                name = "Basak - Ibabao",
                direction = "Directions from Basilica Minore del Santo Niño de Cebu Pilgrim Center, Osmeña Boulevard, Cebu City, Cebu to 7VP4+FF2, E Sabellano Street, Cebu City, 6000 Cebu, Philippines",
                routeStops = parseCoordinatesFromFile(context, "coordinates09f2")
            ),
            JeepneyRoute(
                code = "09G",
                name = "Basak - Colon",
                direction = "Directions from 130 E Sabellano Street, Cebu City, 6000 Cebu, Philippines to Pres. Osmeña Boulevard, 7WV3+Q3X, Cebu City, Cebu, Philippines",
                routeStops = parseCoordinatesFromFile(context, "coordinates09g1")
            ),
            JeepneyRoute(
                code = "09G",
                name = "Basak - Colon",
                direction = "Directions from City Savings Financial Plaza, P. Burgos St, Cebu City, Cebu, Philippines to 7VR5+2MM, E Sabellano Street, Cebu City, 6000 Cebu, Philippines",
                routeStops = parseCoordinatesFromFile(context, "coordinates09g2")
            ),
            JeepneyRoute(
                code = "10F",
                name = "Bulacao - Colon",
                direction = "Directions from Bulacao, Cebu City, Cebu, Philippines to 16-18 Junquera St, Cebu City, 6000 Cebu, Philippines",
                routeStops = parseCoordinatesFromFile(context, "coordinates10f1")
            ),
            JeepneyRoute(
                code = "10F",
                name = "Bulacao - Colon",
                direction = "Directions from 23 Junquera St, Cebu City, 6000 Cebu, Philippines to Bulacao, Cebu City, Cebu, Philippines",
                routeStops = parseCoordinatesFromFile(context, "coordinates10f2")
            ),
            JeepneyRoute(
                code = "10H",
                name = "Bulacao - SM",
                direction = "Directions from Bulacao, Cebu City, Cebu, Philippines to 8W79+HRX Strand Tower, F. Cabahug St, Cebu City, 6000 Cebu, Philippines",
                routeStops = parseCoordinatesFromFile(context, "coordinates10h2")
            ),
            JeepneyRoute(
                code = "10H",
                name = "Bulacao - SM",
                direction = "Directions from 8W79+HX8, F. Cabahug St, Cebu City, Cebu, Philippines to Bulacao, Cebu City, Cebu, Philippines",
                routeStops = parseCoordinatesFromFile(context, "coordinates10h2")
            ),
            JeepneyRoute(
                code = "10M",
                name = "Bulacaoto - SM",
                direction = "Directions from 7RFX+JQ9, Cebu S Rd, Cebu City, Cebu, Philippines to SM City Cebu Jeepney Bay Terminal, Juan Luna Avenue, Cebu City, 6000 Cebu, Philippines",
                routeStops = parseCoordinatesFromFile(context, "coordinates10m1")
            ),
            JeepneyRoute(
                code = "10M",
                name = "Bulacaoto - SM",
                direction = "Directions from Sm City Cebu, Cebu City, Cebu, Philippines to 155, 6000 Natalio B. Bacalso Ave, Cebu City, Cebu, Philippines",
                routeStops = parseCoordinatesFromFile(context, "coordinates10m2")
            ),
            JeepneyRoute(
                code = "11A",
                name = "Inayawan to Colon",
                direction = "Directions from 66 Colon St, Cebu City, 6000 Cebu, Philippines to Inayawan Church, Cebu City, Cebu",
                routeStops = parseCoordinatesFromFile(context, "coordinates11a1")
            ),
            JeepneyRoute(
                code = "11A",
                name = "Inayawan to Colon",
                direction = "Directions from 66 Colon St, Cebu City, 6000 Cebu, Philippines to Inayawan Church, Cebu City, Cebu",
                routeStops = parseCoordinatesFromFile(context, "coordinates11a2")
            ),
            JeepneyRoute(
                code = "12D",
                name = "Labangon - Cebu",
                direction = "Directions from Punta Princesa, Cebu City, Cebu, Philippines to Humabon, 7WW3+CG6, Cebu City, Cebu, Philippines",
                routeStops = parseCoordinatesFromFile(context, "coordinates12d2")
            ),
            JeepneyRoute(
                code = "12D",
                name = "Labangon - Cebu",
                direction = "Directions from F.Urdaneta St, Cebu City, Cebu, Philippines to Punta Princesa, Cebu City, Cebu, Philippines",
                routeStops = parseCoordinatesFromFile(context, "coordinates12d2")
            ),
            JeepneyRoute(
                code = "12G",
                name = "Labangon - SM",
                direction = "Directions from 8V29+5RR, Francisco Llamas St, Cebu City, 6000 Cebu, Philippines to 6000 F. Cabahug St, Cebu City, Cebu, Philippines",
                routeStops = parseCoordinatesFromFile(context, "coordinates12g1")
            ),
            JeepneyRoute(
                code = "12G",
                name = "Labangon - SM",
                direction = "Directions from SM City Cebu, Juan Luna Avenue Extension, Cebu City, Cebu to 8V29+5RR, Francisco Llamas St, Cebu City, 6000 Cebu, Philippines",
                routeStops = parseCoordinatesFromFile(context, "coordinates12g2")
            ),
            JeepneyRoute(
                code = "12I",
                name = "Labangon - SM",
                direction = "Directions from Punta Princesa, Cebu City, Cebu, Philippines to SM City Cebu, Juan Luna Avenue Extension, Cebu City, Cebu",
                routeStops = parseCoordinatesFromFile(context, "coordinates12i1")
            ),
            JeepneyRoute(
                code = "12I",
                name = "Labangon - SM",
                direction = "Directions from SM City Cebu, Juan Luna Avenue Extension, Cebu City, Cebu to Punta Princesa, Cebu City, Cebu, Philippines",
                routeStops = parseCoordinatesFromFile(context, "coordinates12i2")
            ),
            JeepneyRoute(
                code = "12L",
                name = "Labangon - Ayala",
                direction = "Directions from 7VVC+P2F, Cebu City, Cebu, Philippines to 1 Luzon Ave, Cebu City, 6000 Cebu, Philippines",
                routeStops = parseCoordinatesFromFile(context, "coordinates12l1")
            ),
            JeepneyRoute(
                code = "12L",
                name = "Labangon - Ayala",
                direction = "Directions from Ayala Center Cebu, Archbishop Reyes Avenue, Cebu City, Cebu to 7VVC+P35, Cebu City, Cebu, Philippines",
                routeStops = parseCoordinatesFromFile(context, "coordinates12l2")
            ),
            JeepneyRoute(
                code = "13B",
                name = "Talamban - Carbon",
                direction = "Talamban to Carbon",
                routeStops = parseCoordinatesFromFile(context, "coordinates13b1")
            ),
            JeepneyRoute(
                code = "13B",
                name = "Talamban - Carbon",
                direction = "Carbon to Talamban",
                routeStops = parseCoordinatesFromFile(context, "coordinates13b2")
            ),
            JeepneyRoute(
                code = "13C",
                name = "Talamban - Colon",
                direction = "Directions from Tintay Jeepney Terminal, H Abellana, Cebu City, Cebu to Colonnade Mall, Colon Street, Cebu City, Cebu",
                routeStops = parseCoordinatesFromFile(context, "coordinates13c1")
            ),
            JeepneyRoute(
                code = "13C",
                name = "Talamban - Colon",
                direction = "Directions from Colonnade Mall, Colon Street, Cebu City, Cebu to Tintay Jeepney Terminal, H Abellana, Cebu City, Cebu",
                routeStops = parseCoordinatesFromFile(context, "coordinates13c2")
            ),
            JeepneyRoute(
                code = "13H",
                name = "Pit-os - Mandaue",
                direction = "Directions from Pitos, Cebu City, Cebu, Philippines to New Mandaue City Public Market, Mandaue City, Cebu",
                routeStops = parseCoordinatesFromFile(context, "coordinates13h1")
            ),
            JeepneyRoute(
                code = "13H",
                name = "Pit-os - Mandaue",
                direction = "Directions from New Mandaue City Public Market, Mandaue City, Cebu to Pitos, Cebu City, Cebu, Philippines",
                routeStops = parseCoordinatesFromFile(context, "coordinates13h2")
            ),
            JeepneyRoute(
                code = "14D",
                name = "Ayala- Colon",
                direction = "14D Route Map Ayala Cebu Capitol Colon",
                routeStops = parseCoordinatesFromFile(context, "coordinates14d1")
            ),
            JeepneyRoute(
                code = "14D",
                name = "Ayala- Colon",
                direction = "Colon to Ayala",
                routeStops = parseCoordinatesFromFile(context, "coordinates14d2")
            ),
            JeepneyRoute(
                code = "17B",
                name = "Apas - Carbon",
                direction = "Directions from 11 Wilson St, Apas, Cebu City, Cebu, Philippines to 79 M. C. Briones St, Cebu City, Cebu, Philippines",
                routeStops = parseCoordinatesFromFile(context, "coordinates17b1")
            ),
            JeepneyRoute(
                code = "17B",
                name = "Apas - Carbon",
                direction = "Directions from 35 M. C. Briones St, Cebu City, Cebu, Philippines to 11 Wilson St, Apas, Cebu City, Cebu, Philippines",
                routeStops = parseCoordinatesFromFile(context, "coordinates17b2")
            ),
            JeepneyRoute(
                code = "17C",
                name = "Apas - Carbon",
                direction = "Directions from 11 Wilson St, Apas, Cebu City, Cebu, Philippines to 7VRX+RMQ, Cebu City, Cebu, Philippines",
                routeStops = parseCoordinatesFromFile(context, "coordinates17c1")
            ),
            JeepneyRoute(
                code = "17C",
                name = "Apas - Carbon",
                direction = "Directions from Carbon Public Market, Cebu City, Cebu, Philippines to 11 Wilson St, Apas, Cebu City, Cebu, Philippines",
                routeStops = parseCoordinatesFromFile(context, "coordinates17c2")
            ),
            JeepneyRoute(
                code = "17D",
                name = "Apas - Carbon",
                direction = "Apas Capitol Carbon",
                routeStops = parseCoordinatesFromFile(context, "coordinates17d1")
            ),
            JeepneyRoute(
                code = "17D",
                name = "Apas - Carbon",
                direction = "Directions from 24 F. Gonzales St, Cebu City, Cebu, Philippines to 11 Wilson St, Apas, Cebu City, Cebu, Philippines",
                routeStops = parseCoordinatesFromFile(context, "coordinates17d2")
            ),
            JeepneyRoute(
                code = "20A",
                name = "Ayala - Mandaue",
                direction = "Directions from Ayala PUJ Terminal, Archbishop Reyes Avenue, Cebu City, Cebu to 8WRX+7GX, Mandaue City, Cebu, Philippines",
                routeStops = parseCoordinatesFromFile(context, "coordinates20aeven1")
            ),
            JeepneyRoute(
                code = "20A",
                name = "Ayala - Mandaue",
                direction = "Directions from DFA, Mandaue City, Cebu to Ayala PUJ Terminal, Archbishop Reyes Avenue, Cebu City, Cebu",
                routeStops = parseCoordinatesFromFile(context, "coordinates20aeven2")
            ),
            JeepneyRoute(
                code = "20A",
                name = "Ibabao to Ayala Cebu",
                direction = "Directions from Ayala Public Utility Vehicle Terminal, Archbishop Reyes Avenue, Cebu City, Cebu to 246 R.Colina, Mandaue City, 6014 Cebu, Philippines",
                routeStops = parseCoordinatesFromFile(context, "coordinates20aodd1")
            ),
            JeepneyRoute(
                code = "20A",
                name = "Ibabao to Ayala Cebu",
                direction = "Directions from 8WQW+GC2, S.B.Cabahug, Mandaue City, Cebu, Philippines to Ayala Public Utility Vehicle Terminal, Archbishop Reyes Avenue, Cebu City, Cebu",
                routeStops = parseCoordinatesFromFile(context, "coordinates20aodd2")
            ),
            JeepneyRoute(
                code = "21A",
                name = "Mandaue - Cathedral",
                direction = "Directions from Pacific Mall Mandaue, U.N. Avenue, Mandaue City, Cebu to 7WW3+9C6, Cebu City, Cebu, Philippines",
                routeStops = parseCoordinatesFromFile(context, "coordinates21a1")
            ),
            JeepneyRoute(
                code = "21A",
                name = "Mandaue - Cathedral",
                direction = "Directions from 7WW3+7FJ, F. Urdaneta St, Cebu City, Cebu, Philippines to Pacific Mall Mandaue, U.N. Avenue, Mandaue City, Cebu",
                routeStops = parseCoordinatesFromFile(context, "coordinates21a2")
            ),
            JeepneyRoute(
                code = "22A",
                name = "Mandaue - Cathedral",
                direction = "Directions from 228 C.M.Cabahug, Mandaue City, Cebu, Philippines to F.Urdaneta St, Cebu City, Cebu, Philippines",
                routeStops = parseCoordinatesFromFile(context, "coordinates22a1")
            ),
            JeepneyRoute(
                code = "22A",
                name = "Mandaue - Cathedral",
                direction = "Directions from Cebu Metropolitan Cathedral, Mabini Street, Cebu City, Cebu to Cm Cabahug, Mandaue City, Cebu, Philippines",
                routeStops = parseCoordinatesFromFile(context, "coordinate22a2")
            ),
            JeepneyRoute(
                code = "22D",
                name = "Mandaue - Cathedral",
                direction = "Directions from Cm Cabahug, Mandaue City, Cebu to F.Urdaneta St, Cebu City, Cebu, Philippines",
                routeStops = parseCoordinatesFromFile(context, "coordinates22d1")
            ),
            JeepneyRoute(
                code = "22D",
                name = "Mandaue - Cathedral",
                direction = "Directions from Cebu Metropolitan Cathedral, Mabini Street, Cebu City, Cebu to Cm Cabahug, Mandaue City, Cebu",
                routeStops = parseCoordinatesFromFile(context, "coordinates22d2")
            ),
            JeepneyRoute(
                code = "22I",
                name = "Mandaue - Gaisano Country Mall",
                direction = "Directions from 8WFR+JRR, A. Soriano Ave, Mandaue City, Cebu, Philippines to Gaisano Country Mall, Banilad Road, Apas, Cebu City, Cebu",
                routeStops = parseCoordinatesFromFile(context, "coordinates22i1")
            ),
            JeepneyRoute(
                code = "22I",
                name = "Mandaue - Gaisano Country Mall",
                direction = "Directions from Gaisano Country Mall, Banilad Road, Apas, Cebu City, Cebu to 8WFR+JRR, A. Soriano Ave, Mandaue City, Cebu, Philippines",
                routeStops = parseCoordinatesFromFile(context, "coordinates22i2")
            ),
            JeepneyRoute(
                code = "23",
                name = "Parkmall - Punta Engaño",
                direction = "Directions from AmiSa Private Residences, Punta Engaño Road, Lapu-Lapu City, Cebu to Parkmall, Tipolo, Mandaue City, Cebu",
                routeStops = parseCoordinatesFromFile(context, "coordinates231")
            ),
            JeepneyRoute(
                code = "23",
                name = "Parkmall - Punta Engaño",
                direction = "Directions from 8WGP+36Q, C.D.Seno, Tipolo, Mandaue City, Cebu, Philippines to AmiSa Private Residences, Punta Engaño Road, Lapu-Lapu City, Cebu",
                routeStops = parseCoordinatesFromFile(context, "coordinates232")
            ),
            JeepneyRoute(
                code = "23D",
                name = "Parkmall - Open",
                direction = "Directions from Parkmall, Tipolo, Mandaue City, Cebu to 8W6X+HJ2, Lopez - Jaena St, Lapu-Lapu City, Cebu, Philippines",
                routeStops = parseCoordinatesFromFile(context, "coordinates23d1")
            ),
            JeepneyRoute(
                code = "23D",
                name = "Parkmall - Open",
                direction = "Directions from Lapu-Lapu City PUJ Terminal, M.L. Quezon National Highway, Lapu-Lapu City, Cebu to Parkmall, Tipolo, Mandaue City, Cebu",
                routeStops = parseCoordinatesFromFile(context, "coordinates23d2")
            ),
            JeepneyRoute(
                code = "62B",
                name = "Pit-os - Carbon",
                direction = "Directions from 9WVC+4XP, Cebu City, 6000 Cebu, Philippines to 7VRX+RMQ, Cebu City, Cebu, Philippines",
                routeStops = parseCoordinatesFromFile(context, "coordinates62b1")
            ),
            JeepneyRoute(
                code = "62B",
                name = "Pit-os - Carbon",
                direction = "Directions from Carbon Public Market, Cebu City, Cebu, Philippines to Pit-os, Cebu",
                routeStops = parseCoordinatesFromFile(context, "coordinates62b2")
            ),
            JeepneyRoute(
                code = "MI-01A",
                name = "Punts Engaño - Mactan",
                direction = "Directions from Ompad Street, Lapu-Lapu City, Cebu to Amisa Residences, Lapu-Lapu City, Cebu",
                routeStops = parseCoordinatesFromFile(context, "coordinatesmi01a")
            ),
            JeepneyRoute(
                code = "MI-02B",
                name = "Mandaue - Parkmall",
                direction = "Directions from Imperial Palace Waterpark Resort & Spa, Maribago, Lapu-Lapu City, Cebu to 8WGM+6FX, Park Mall Dr, Tipolo, Mandaue City, Cebu, Philippines",
                routeStops = parseCoordinatesFromFile(context, "coordinatesmi02b1")
            ),
            JeepneyRoute(
                code = "MI-02B",
                name = "Mandaue - Parkmall",
                direction = "Directions from 8WGM+99P, Tipolo, Mandaue City, Cebu, Philippines to Imperial Palace Waterpark Resort & Spa, Maribago, Lapu-Lapu City, Cebu",
                routeStops = parseCoordinatesFromFile(context, "coordinatesmi02b2")
            ),
            JeepneyRoute(
                code = "MI-03A",
                name = "Cordova - Lapu Lapu",
                direction = "Directions from Lapulapu City Public Market, Lapu-Lapu City, Cebu to 7W2X+2R5, Martin Francisco St, Cordova, Cebu, Philippines",
                routeStops = parseCoordinatesFromFile(context, "coordinatesmi03a")
            ),
            JeepneyRoute(
                code = "MI-03B",
                name = "Mactan - Cordova",
                direction = "Directions from Mepz1 2nd Avenue Rd, MEPZ 1, Lapu-Lapu City, Cebu, Philippines to Cordova, Cordova, Cebu, Philippines",
                routeStops = parseCoordinatesFromFile(context, "coordinatesmi03b")
            ),
            JeepneyRoute(
                code = "MI-04A",
                name = "Mactan = Tamiya",
                direction = "Directions from Tamiya Terminal, Mepz 2 Compound, Lapu-Lapu City, Cebu to J Centre Mall, A. S. Fortuna Street, Mandaue City, Cebu",
                routeStops = parseCoordinatesFromFile(context, "coordinatesmi04a")
            ),
            JeepneyRoute(
                code = "MI-04B",
                name = "Mactan - MEPZ2",
                direction = "Directions from 8XJQ+M6M, Lapu-Lapu City, Cebu, Philippines to Tamiya Terminal, Mepz 2 Compound, Lapu-Lapu City, Cebu",
                routeStops = parseCoordinatesFromFile(context, "coordinatesmi04b")
            ),
            JeepneyRoute(
                code = "MI-05A",
                name = "Mactan - Opon",
                direction = "Directions from Mactan Cebu International Airport Terminal, Lapu-Lapu City, 6016 Cebu, Philippines to 655 B.M. Dimataga St, Poblacion, Lapu-Lapu City, 6015 Cebu, Philippines",
                routeStops = parseCoordinatesFromFile(context, "coordinatesmi05a1")
            ),
            JeepneyRoute(
                code = "MI-05A",
                name = "Mactan - Opon",
                direction = "Directions from Opon Plaza, Lapu-Lapu City, Cebu, Philippines to Mactan Cebu International Airport Terminal, Lapu-Lapu City, 6016 Cebu, Philippines",
                routeStops = parseCoordinatesFromFile(context, "coordinatesmi05a2")
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