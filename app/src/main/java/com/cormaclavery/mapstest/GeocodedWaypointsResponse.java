package com.cormaclavery.mapstest;

public final class GeocodedWaypointsResponse {
    public final Geocoded_waypoint geocoded_waypoints[];
    public final Route routes[];
    public final String status;

    public GeocodedWaypointsResponse(Geocoded_waypoint[] geocoded_waypoints, Route[] routes, String status){
        this.geocoded_waypoints = geocoded_waypoints;
        this.routes = routes;
        this.status = status;
    }

    public static final class Geocoded_waypoint {
        public final String geocoder_status;
        public final String place_id;
        public final String[] types;

        public Geocoded_waypoint(String geocoder_status, String place_id, String[] types){
            this.geocoder_status = geocoder_status;
            this.place_id = place_id;
            this.types = types;
        }
    }

    public static final class Route {
        public final Bounds bounds;
        public final String copyrights;
        public final Leg legs[];
        public final Overview_polyline overview_polyline;
        public final String summary;
        public final String[] warnings;
        public final Waypoint_order waypoint_order[];

        public Route(Bounds bounds, String copyrights, Leg[] legs, Overview_polyline overview_polyline, String summary, String[] warnings, Waypoint_order[] waypoint_order){
            this.bounds = bounds;
            this.copyrights = copyrights;
            this.legs = legs;
            this.overview_polyline = overview_polyline;
            this.summary = summary;
            this.warnings = warnings;
            this.waypoint_order = waypoint_order;
        }

        public static final class Bounds {
            public final Northeast northeast;
            public final Southwest southwest;

            public Bounds(Northeast northeast, Southwest southwest){
                this.northeast = northeast;
                this.southwest = southwest;
            }

            public static final class Northeast {
                public final double lat;
                public final double lng;

                public Northeast(double lat, double lng){
                    this.lat = lat;
                    this.lng = lng;
                }
            }

            public static final class Southwest {
                public final double lat;
                public final double lng;

                public Southwest(double lat, double lng){
                    this.lat = lat;
                    this.lng = lng;
                }
            }
        }

        public static final class Leg {
            public final Distance distance;
            public final Duration duration;
            public final String end_address;
            public final End_location end_location;
            public final String start_address;
            public final Start_location start_location;
            public final Step steps[];
            public final Traffic_speed_entry traffic_speed_entry[];
            public final Via_waypoint via_waypoint[];

            public Leg(Distance distance, Duration duration, String end_address, End_location end_location, String start_address, Start_location start_location, Step[] steps, Traffic_speed_entry[] traffic_speed_entry, Via_waypoint[] via_waypoint){
                this.distance = distance;
                this.duration = duration;
                this.end_address = end_address;
                this.end_location = end_location;
                this.start_address = start_address;
                this.start_location = start_location;
                this.steps = steps;
                this.traffic_speed_entry = traffic_speed_entry;
                this.via_waypoint = via_waypoint;
            }

            public static final class Distance {
                public final String text;
                public final long value;

                public Distance(String text, long value){
                    this.text = text;
                    this.value = value;
                }
            }

            public static final class Duration {
                public final String text;
                public final long value;

                public Duration(String text, long value){
                    this.text = text;
                    this.value = value;
                }
            }

            public static final class End_location {
                public final double lat;
                public final double lng;

                public End_location(double lat, double lng){
                    this.lat = lat;
                    this.lng = lng;
                }
            }

            public static final class Start_location {
                public final double lat;
                public final double lng;

                public Start_location(double lat, double lng){
                    this.lat = lat;
                    this.lng = lng;
                }
            }

            public static final class Step {
                public final Distance distance;
                public final Duration duration;
                public final End_location end_location;
                public final String html_instructions;
                public final Polyline polyline;
                public final Start_location start_location;
                public final String travel_mode;
                public final String maneuver;

                public Step(Distance distance, Duration duration, End_location end_location, String html_instructions, Polyline polyline, Start_location start_location, String travel_mode, String maneuver){
                    this.distance = distance;
                    this.duration = duration;
                    this.end_location = end_location;
                    this.html_instructions = html_instructions;
                    this.polyline = polyline;
                    this.start_location = start_location;
                    this.travel_mode = travel_mode;
                    this.maneuver = maneuver;
                }

                public static final class Distance {
                    public final String text;
                    public final long value;

                    public Distance(String text, long value){
                        this.text = text;
                        this.value = value;
                    }
                }

                public static final class Duration {
                    public final String text;
                    public final long value;

                    public Duration(String text, long value){
                        this.text = text;
                        this.value = value;
                    }
                }

                public static final class End_location {
                    public final double lat;
                    public final double lng;

                    public End_location(double lat, double lng){
                        this.lat = lat;
                        this.lng = lng;
                    }
                }

                public static final class Polyline {
                    public final String points;

                    public Polyline(String points){
                        this.points = points;
                    }
                }

                public static final class Start_location {
                    public final double lat;
                    public final double lng;

                    public Start_location(double lat, double lng){
                        this.lat = lat;
                        this.lng = lng;
                    }
                }
            }

            public static final class Traffic_speed_entry {

                public Traffic_speed_entry(){
                }
            }

            public static final class Via_waypoint {

                public Via_waypoint(){
                }
            }
        }

        public static final class Overview_polyline {
            public final String points;

            public Overview_polyline(String points){
                this.points = points;
            }
        }

        public static final class Waypoint_order {

            public Waypoint_order(){
            }
        }
    }
}