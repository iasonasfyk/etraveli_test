package pages;

import com.persado.oss.quality.stevia.selenium.core.WebComponent;
import com.persado.oss.quality.stevia.selenium.core.controllers.commonapi.KeyInfo;
import org.springframework.stereotype.Component;

@Component
public class MainPageObject extends WebComponent {
    public enum MainPageLocators {

       DEPARTURE_PLACE("css=input[aria-label='From']"),
       DESTINATION_PLACE("css=input[aria-label='To']"),
        SEARCH_BUTTON("css=button[type='submit']"),
        DEPARTURE_DATE("css=input[placeholder=Departure]"),
        FR26AUGUST("css=div[aria-label='Fri Aug 26 2022']"),
        SUN28AUGUST("css=div[aria-label='Sun Aug 28 2022']"),
        RETURN_DATE("css=input[placeholder=Return]"),
        PASSENGERS("css=div.etiTravelerDescription"),
        CLASS("css=div.etiListItem__header");

       private String myLocator;

        MainPageLocators(String locator) {
            myLocator = locator;
        }

        public String get(){
            return myLocator;
        }
    }

    /**
     * press search button
     */
    public void pressSearch() {
        controller().press(MainPageLocators.SEARCH_BUTTON.get());
    }

    /**
     * input place of departure
     * @param: String place of departure
     */
    public void inputDeparturePlace(String departurePlace) {
        controller().input(MainPageLocators.DEPARTURE_PLACE.get(), departurePlace);
        controller().keyPress(KeyInfo.ENTER);
    }

    /**
     * input destination
     * @param: String destination
     */
    public void inputDestination(String destination) {
        controller().input(MainPageLocators.DESTINATION_PLACE.get(), destination);
        controller().keyPress(KeyInfo.ENTER);
    }

    /**
     * input custom date of departure (26 Aug)
     */
    public void inputCustomDateOfDeparture() {
        controller().press(MainPageLocators.DEPARTURE_DATE.get());
        controller().waitForElement(MainPageLocators.FR26AUGUST.get());
        controller().press(MainPageLocators.FR26AUGUST.get());
        controller().waitForElementInvisibility(MainPageLocators.FR26AUGUST.get());
    }

    /**
     * input custom date of return (28 Aug)
     */
    public void inputCustomDateOfReturn() {
        controller().press(MainPageLocators.RETURN_DATE.get());
        controller().waitForElement(MainPageLocators.SUN28AUGUST.get());
        controller().press(MainPageLocators.SUN28AUGUST.get());
        controller().waitForElementInvisibility(MainPageLocators.SUN28AUGUST.get());
    }

}
