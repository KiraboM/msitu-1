package com.rtnmsitu.utils;
/*
 *  This file is part of Msitu.
 *  <https://github.com/kitandara/kibira>
 *
 *  Copyright (C) 2022 Digital Solutions
 *
 *  Msitu is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Msitu is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Msitu. If not, see <http://www.gnu.org/licenses/>
 */

import android.location.Location;
import android.location.LocationManager;
import com.google.android.gms.maps.model.LatLng;
import com.rtnmsitu.geometry.LongLat;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import dilivia.s2.S2LatLng;
import dilivia.s2.index.point.S2PointIndex;
import gov.nasa.worldwind.geom.LatLon;
import gov.nasa.worldwind.geom.coords.UTMCoord;
import kotlin.Pair;

public class GeneralHelper {

    public static S2PointIndex<S2LatLng> convertLineToS2(Collection<LatLng> list){
        S2PointIndex<S2LatLng> querySet = new S2PointIndex<S2LatLng>();
       for (LatLng x : list){
           S2LatLng s2LatLng = S2LatLng.fromDegrees(x.latitude, x.longitude);
           querySet.add(s2LatLng.toPoint(),s2LatLng );

       }
       return querySet;
    }

    public static float findDistanceBtnTwoPoints(LatLng pt1, LatLng pt2){

        Location firstPoint = new Location(LocationManager.GPS_PROVIDER);
        Location secondPoint = new Location(LocationManager.GPS_PROVIDER);
        // set latLong for first point
        firstPoint.setLatitude(pt1.latitude);
        firstPoint.setLongitude(pt1.longitude);
        // set latLong for second point
        secondPoint.setLatitude(pt2.latitude);
        secondPoint.setLongitude(pt2.longitude);

            return firstPoint.distanceTo(secondPoint); // in metres

    }

    public static Pair<List<Double>, List<Double>> coordsToEastingsNorthings(List<LatLng> coordinates){
        List<Double> eastings = new ArrayList<>();
        List<Double> northings = new ArrayList<>();

        for (LatLng coord: coordinates) {
            LatLon ll = LatLon.fromDegrees(coord.latitude, coord.longitude);
            UTMCoord utm =  UTMCoord.fromLatLon(ll.latitude, ll.longitude);
            eastings.add(utm.getEasting());
            northings.add(utm.getNorthing());
        }
        return new Pair<>(eastings, northings);
    }

    public static double convert(double area, String units) {
        // 1 for acres and 2 for hectares
        double finalArea = 0.0;

        DecimalFormat df = new DecimalFormat("#.####");
        df.setRoundingMode(RoundingMode.DOWN);

        // finalArea = area / 10000;
        finalArea = switch (units) {
            case "1.0" -> Double.parseDouble(df.format(area / 4047));
            // finalArea = area / 4047;
            case "2.0" -> Double.parseDouble(df.format(area / 10000));
            default -> finalArea;
        };
        return finalArea;
    }

}
