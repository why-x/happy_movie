package com.why.happy_movie.bean;

public class TimeCnimea {
        /**
         * address : 北京市崇文区崇文门外大街18号国瑞城首层、地下二层
         * followCinema : false
         * id : 9
         * name : 北京百老汇影城国瑞购物中心店
         */

        private String address;
        private boolean followCinema;
        private int id;
        private String name;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public boolean isFollowCinema() {
            return followCinema;
        }

        public void setFollowCinema(boolean followCinema) {
            this.followCinema = followCinema;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
}
