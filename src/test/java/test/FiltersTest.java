package test;

import com.persado.oss.quality.stevia.spring.SteviaTestBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

/**
 * Purpose of this test is to test some results filters:
 * - make a custom flight search (Athens - Berlin, 26 Aug - 28 Aug)
 * <p>
 * Step1: verify results title (places and dates)
 * Step2: - keep number of results (flights) in a value -> X
 * - apply some filters (airline, non-stop flight)
 * - verify results: "1 (checked in UI before) of X"
 * Step3: Verify only one flight result
 * Step4: Verify airline in results is the one chosen (Aegean)
 */

public class FiltersTest extends SteviaTestBase {

    String numberOfFlightsWithFilters = "1";

    String placeOfDeparture = "Athens", destination = "Berlin";

    String dateOfDeparture = "Fri 26 Aug", dateOfReturn = "Sun 28 Aug";


    @Autowired
    protected BaseObject baseObject;

    @Autowired
    protected ResultsPageObject resultsPageObject;

    @BeforeSuite
    public void preconditions() {
        baseObject.inputCustomFlightConfig(placeOfDeparture, destination);
    }


    @Test
    public void step1_verifyFlightDetailsTitle() {
        baseObject.verifyFlightPlacesTitle(placeOfDeparture, destination);
        baseObject.verifyFlightDates(dateOfDeparture, dateOfReturn);
    }

    @Test
    public void step2_applyAndVerifyFilters() {
        String resultsWithoutFilters = resultsPageObject.getTotalResultsWithoutFilters();
        resultsPageObject.pressFiltersButton();
        resultsPageObject.filterByCustomStops();
        resultsPageObject.filterByCustomAirline();
        resultsPageObject.pressDoneButton();
        baseObject.verifyFlightResults(resultsPageObject.getResultsWithFilters(), numberOfFlightsWithFilters, resultsWithoutFilters);
    }

    @Test
    public void step3_verifyOnlyOneResultInPage() {
        baseObject.verifyOnlyOneResult();
    }

    @Test
    public void step4_verifyAirline() {
        baseObject.verifyFlightAirline();
    }

}
