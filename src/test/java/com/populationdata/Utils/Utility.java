package com.populationdata.Utils;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class Utility {
    public static Response getPopulationData(int year) {
        return given()
                .queryParams("drilldowns", "Nation")
                .queryParam("measures", "Population")
                .queryParam("year", year)
                .when().get()
                .then().extract().response();
    }

    public static void isInteger(Integer number) {
        try {
            Integer.valueOf(number);
        } catch(Exception e) {
            System.out.println("Not a number");
        }
    }
}
