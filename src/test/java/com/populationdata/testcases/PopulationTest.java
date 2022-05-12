package com.populationdata.testcases;

import com.populationdata.Utils.Utility;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class PopulationTest {

    private static Logger logger = LoggerFactory.getLogger(PopulationTest.class);

    @Test(dataProvider = "getData")
    public void validatePopulationData(int frmYear, int toYear) {
        logger.info("---------Population data -----------");
        for (int i = frmYear; i < toYear; i++) {
            JsonPath js = new JsonPath(Utility.getPopulationData(i).asString());

            logger.info("Start validations for the year "+i);
            //Year and ID Year should be the same as the year in the query
            Assert.assertEquals(Integer.valueOf(js.get("data[0].Year")), i);
            Assert.assertEquals((Integer) js.get("data[0]['ID Year']"), i);

            //Population should be a number
            Utility.isInteger(js.get("data[0].Population"));

            //All the other fields ID Nation, Nation, Slug Nation should return the value shown above
            Assert.assertEquals(js.get("data[0]['ID Nation']"), "01000US");
            Assert.assertEquals(js.getString("data[0].Nation"), "United States");
            Assert.assertEquals(js.get("data[0]['Slug Nation']"), "united-states");
            logger.info("Validations complete for the year "+i);

        }

    }

    @DataProvider
    public Object[][] getData() {
        //return new Object[][] {{"Faded Short Sleeve T Shirt", 1, "M", "Blue"}};
        return new Object[][] {{2013, 2019}};
    }

    @BeforeTest
    public void setup() {
        RestAssured.baseURI="https://datausa.io/api/data";

    }
}
