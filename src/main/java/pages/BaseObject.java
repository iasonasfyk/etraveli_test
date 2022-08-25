package pages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.testng.Assert;


public class BaseObject {

    @Autowired
    protected MainPageObject mainPageObject;

    @Autowired
    protected ResultsPageObject resultsPageObject;

    /**
     * input a custom flight configuration in main page
     * @param placeOfDeparture
     * @param destination
     */
    public void inputCustomFlightConfig(String placeOfDeparture, String destination){
        mainPageObject.inputDeparturePlace(placeOfDeparture);
        mainPageObject.inputDestination(destination);
        mainPageObject.inputCustomDateOfDeparture();
        mainPageObject.inputCustomDateOfReturn();
        mainPageObject.pressSearch();
        resultsPageObject.waitForFiltersPageToLoad();
    }

    /**
     * Verify flight results after applying custom filters
     * @param resultsWithFilters
     * @param numberOfFlightsWithFilters
     * @param resultsWithoutFilters
     */
    public void verifyFlightResults(String resultsWithFilters, String numberOfFlightsWithFilters, String resultsWithoutFilters){
        Assert.assertEquals(resultsWithFilters, numberOfFlightsWithFilters + " of " + resultsWithoutFilters);
    }

    /**
     * verify there is only one result after filters applied
     */
    public void verifyOnlyOneResult(){
        resultsPageObject.controller().isComponentVisible(ResultsPageObject.ResultsPageLocators.ONE_RESULT.get());
        resultsPageObject.controller().isComponentNotVisible(ResultsPageObject.ResultsPageLocators.SECOND_RESULT.get());
    }

    public void verifyFlightPlacesTitle(String place1, String place2){
        Assert.assertEquals(resultsPageObject.getDatesOfFlight(), place1 + "-" + place2);
    }

    public void verifyFlightDates(String dateOfDeparture, String dateOfReturn){
        Assert.assertEquals(resultsPageObject.getPlaceOfFlight(), dateOfDeparture + "-" + dateOfReturn);
    }

    public void verifyFlightAirline(){
        resultsPageObject.controller().isComponentVisible(ResultsPageObject.ResultsPageLocators.AEGEAN_IMAGE.get());
    }

}
