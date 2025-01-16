package org.alicansadeler;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.alicansadeler.constants.UserEndPoints;
import org.alicansadeler.testdata.UserTestData;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Slf4j
public class UserTests {

    private RequestSpecification specification;

    @BeforeClass
    public void setUp() {
        specification = UserEndPoints.userSpec();
    }

    @Test
    public void createUser() {
        log.info("User oluşturma testi başladı.");
        RestAssured.given()
                .spec(specification)
                .body(UserTestData.getUserWithCustomUsername("viewsonic"))
                .when()
                .post(UserEndPoints.CREATE_USER)
                .then()
                .log().ifValidationFails()
                .statusCode(200);
        log.info("User başarıyla oluşturuldu");
    }

    @Test(dependsOnMethods = {"createUser"})
    public void loginUser() {
        log.info("User login testi başlatıldı");

        RestAssured
                .given()
                .spec(specification)
                .queryParam("username", "viewsonic")
                .queryParam("password", "test123")
                .when()
                .get(UserEndPoints.USER_LOGIN)
                .then()
                .log().ifValidationFails()
                .statusCode(200);

        log.info("User login işlemi başarılı");
    }

    @Test(dependsOnMethods = {"loginUser"})
    public void logoutUser() {
        log.info("User logout testi başlatıldı");

        RestAssured
                .given()
                .spec(specification)
                .when()
                .get(UserEndPoints.USER_LOGOUT)
                .then()
                .log().ifValidationFails()
                .statusCode(200);
        log.info("User logout işlemi başarılı");
    }

    @Test(dependsOnMethods = {"createUser"})
    public void getUser() {
        log.info("User bilgilerini alma testi başlatıldı");
        RestAssured
                .given()
                    .spec(specification)
                    .pathParam("username", "viewsonic")
                .when()
                    .get(UserEndPoints.GET_USER)
                .then()
                .log().ifValidationFails()
                .statusCode(200);
        log.info("User bilgileri başarili bir şekilde getirildi");
    }

    @Test(dependsOnMethods = {"createUser", "getUser"})
    public void updateUser() {
        log.info("User bilgilerini güncelleme testi başlatıldı");
        RestAssured
                .given()
                    .spec(specification)
                    .pathParam("username", "viewsonic")
                    .body(UserTestData.getUserWithCustomUsername("viewsonicUpdate"))
                .when()
                    .put(UserEndPoints.UPDATE_USER)
                .then()
                    .log().ifValidationFails()
                    .statusCode(200);
        log.info("User bilgileri başarılı şekilde güncellendi");
    }

    @Test(dependsOnMethods = {"updateUser"})
    public void deleteUser() {
        log.info("User silme testi başlatıldı");
        RestAssured
                .given()
                .spec(specification)
                .pathParam("username", "viewsonicUpdate")
                .when()
                .delete(UserEndPoints.DELETE_USER)
                .then()
                .log().ifValidationFails()
                .statusCode(200);
        log.info("User silme testi başarıyla tamamlandı");
    }


}
