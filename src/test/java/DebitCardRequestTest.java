import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.pseudo;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class DebitCardRequestTest {

    SelenideElement form = $(".form");


    @Test
    void shouldTest() {
        open("http://localhost:9999/");
        form.$("[data-test-id=name] input").setValue("Иванов Иван");
        form.$("[data-test-id=phone] input").setValue("+71234567899");
        form.$("[data-test-id=agreement]").click();
        form.$("button").click();
        $("[data-test-id=order-success]").shouldHave(exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));

    }

    @Test
    void shouldWarningIfInvalidName() {
        open("http://localhost:9999/");
        form.$("[data-test-id=name] input").setValue("Ivanov Ivan");
        form.$("[data-test-id=phone] input").setValue("+71234567899");
        form.$("[data-test-id=agreement]").click();
        form.$("button").click();
        $("[data-test-id=name] .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldWarningIfInvalidPhone() {
        open("http://localhost:9999/");
        form.$("[data-test-id=name] input").setValue("Иванов Иван");
        form.$("[data-test-id=phone] input").setValue("+7123456789");
        form.$("[data-test-id=agreement]").click();
        form.$("button").click();
        $("[data-test-id=phone] .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldWarningIfEmptyCheckbox() {
        open("http://localhost:9999/");
        SelenideElement form = $(".form");
        form.$("[data-test-id='name'] input").setValue("Иванов-Петров Иван");
        form.$("[data-test-id='phone'] input").setValue("+79099876545");
        form.$(".button").click();
        $("[data-test-id=agreement].input_invalid").shouldHave(pseudo(":checkbox__text", "color", "rgb(255, 92, 92)"));
    }

}
