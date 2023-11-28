package ru.netology.selenide;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.conditions.Text;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class AppCardDeliveryTaskTwoTest {

    private String generateDate(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    public void shouldBeSuccessfullyCompleted() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("См");
        $$(".menu-item__control").findBy(text("Смоленск")).click();
        String planingDate = generateDate(7, "dd.MM.yyyy");
        $("button.icon-button").click();
        if (!generateDate(3, "MM").equals(generateDate(7, "MM"))) {
            $$(".calendar__arrow.calendar__arrow_direction_right").last().click();
        }
        String planingDay = generateDate(7, "d");
        $$(".calendar__day").findBy(text(planingDay)).click();
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        //$("[data-test-id='date'] input").setValue(planingDate);
        $("[data-test-id='name'] input").setValue("Петров-Иван Сидорович");
        $("[data-test-id='phone'] input").setValue("+79876543210");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(".notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(14))
                .shouldHave(Condition.exactText("Встреча успешно забронирована на " + planingDate));
    }
}