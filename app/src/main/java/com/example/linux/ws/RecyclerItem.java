package com.example.linux.ws;

public class RecyclerItem {
        private int id;
        private String title;
        private String organiser;
        private String location;
        private String date;
        private String duration;
        private String applied_by;

        public RecyclerItem(int id, String title, String organiser,String location,String date, String duration, String applied_by) {
            this.id = id;
            this.title = title;
            this.organiser = organiser;
            this.location = location;
            this.date = date;
            this.duration = duration;
            this.applied_by=applied_by;
        }

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getOrganiser() {
            return organiser;
        }

        public String getLocation() {
           return location;
        }

        public String getDate() {
            return date;
        }
        public String getDuration() {
            return duration;
        }

        public String getApplied_by() {
            return applied_by;
        }

}
