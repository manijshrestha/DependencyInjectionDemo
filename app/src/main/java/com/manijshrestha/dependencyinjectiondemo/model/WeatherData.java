package com.manijshrestha.dependencyinjectiondemo.model;

public class WeatherData {

    private MainData main;

    public MainData getMain() {
        return main;
    }

    public void setMain(MainData main) {
        this.main = main;
    }

    public static class MainData {
        private String temp;

        public String getTemp() {
            return temp;
        }

        public void setTemp(String temp) {
            this.temp = temp;
        }
    }
}
