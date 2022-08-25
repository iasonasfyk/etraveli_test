package pages;

import com.persado.oss.quality.stevia.selenium.core.WebComponent;
import org.springframework.stereotype.Component;

@Component
public class ResultsPageObject extends WebComponent {

    public enum ResultsPageLocators {

        FILTERS_BUTTON("css=div.controls svg"),
        NONSTOP_FLIGHT("css=label[for='MAX_STOPS_direct']"),
        CLEAR_ALL_AIRLINES("css=div[data-testid='resultPage-AIRLINESFilter-content'] span:contains('Clear all')"),
        AEGEAN("css=div[data-testid='resultPage-AIRLINESFilter-content'] li input[value='A3']"),
        DONE_BUTTON("css=button[data-testid='filtersForm-applyFilters-button']"),
        RESULTS("css=header[data-testid='resultPage-filters-header'] > div"),
        LOADER("css=div.progressSpinner__image"),

        ONE_RESULT("css=div.resultPage-flightResults-0"),

        SECOND_RESULT("css=div.resultPage-flightResults-1"),

        PLACES_OF_FLIGHTS_TITLE("css=div [data-testid='tripDetails-title-TitleText']"),

        FLIGHT_DATES_TITLE("css=div [data-testid='tripDetails-title-date-desktop']"),

        AEGEAN_IMAGE("css=section.tripDetailsBound div img[alt='Aegean Airlines']");


        private String myLocator;

        ResultsPageLocators(String locator) {
            myLocator = locator;
        }

        public String get(){
            return myLocator;
        }
    }

    /**
     * enum with options for the filter headers
     */
    public enum FilterHeaders {
        AIRLINES("AIRLINES"),
        NUMBER_OF_STOPS("MAX_STOPS"),
        DEPARTURE("departureArrival0"),
        RETURN("departureArrival1");

        private String myHeader;

        FilterHeaders(String locator) {
            myHeader = locator;
        }

        public String get(){
            return myHeader;
        }
    }


    /**
     * get the header needed for filterLocatorByHeader method
     * @param: header from FilterHeaders enum
     * @return: headerName for locator in filterLocatorByHeader
     */
    public String getHeaderName(String filterHeader){
        String headerName = FilterHeaders.AIRLINES.toString();
        if (filterHeader.equals(FilterHeaders.AIRLINES.toString()))
            headerName =  FilterHeaders.AIRLINES.get();
        else if (filterHeader.equals(FilterHeaders.NUMBER_OF_STOPS.toString())) {
            headerName =  FilterHeaders.NUMBER_OF_STOPS.get();
        }
        else if (filterHeader.equals(FilterHeaders.DEPARTURE.toString())) {
            headerName =  FilterHeaders.DEPARTURE.get();
        }
        else if (filterHeader.equals(FilterHeaders.RETURN.toString())) {
            headerName =  FilterHeaders.RETURN.get();
        }
        return headerName;
    }

    /**
     * All filter headers have the same locator with 1 difference (headerName)
     * This method gets the locator needed, given one of the option in the enum above
     * @param headerName
     * @return locator
     */
    public String filterLocatorByHeader(String headerName) {
        return "header[data-testid='resultPage-" + headerName + "-header']";
    }

    /**
     * Wait for page to load after a flight search in the main page
     */
    public void waitForFiltersPageToLoad() {
        controller().waitForElement(ResultsPageLocators.LOADER.get());
        controller().waitForElementInvisibility(ResultsPageLocators.LOADER.get());
        controller().waitForElement(ResultsPageLocators.FILTERS_BUTTON.get());
    }

    /**
     * press filters button
     */
    public void pressFiltersButton() {
        controller().press(ResultsPageLocators.FILTERS_BUTTON.get());
        controller().waitForElement(ResultsPageLocators.DONE_BUTTON.get());
    }

    /**
     * press done button (after applying filters)
     */
    public void pressDoneButton() {
        controller().press(ResultsPageLocators.DONE_BUTTON.get());
        controller().waitForElementInvisibility(ResultsPageLocators.DONE_BUTTON.get());
        controller().waitForElement(ResultsPageLocators.RESULTS.get()); //wait for results to load
    }

    /**
     * get number of flights without filters applied
     */
    public String getTotalResultsWithoutFilters() {
        return controller().getText(ResultsPageLocators.RESULTS.get())
                .replace("Filter by: ", "").replace(" Flights", "");
    }

    /**
     * get number of flights after filters applied
     */
    public String getResultsWithFilters() {
        return controller().getText(ResultsPageLocators.RESULTS.get())
                .replace("Filter by: ", "").replace(" flightsReset filter", "");
    }

    /**
     * filter by number of stops -> non-stop flight
     */
    public void filterByCustomStops() {
        controller().press(filterLocatorByHeader(getHeaderName(FilterHeaders.NUMBER_OF_STOPS.toString()))); //open stops filter
        controller().press(ResultsPageLocators.NONSTOP_FLIGHT.get()); //select non-stop flight
        controller().press(filterLocatorByHeader(getHeaderName(FilterHeaders.NUMBER_OF_STOPS.toString()))); //close stops filter
    }

    /**
     * filter by custom airline (Aegean)
     */
    public void filterByCustomAirline() {
        controller().press(filterLocatorByHeader(getHeaderName(FilterHeaders.AIRLINES.toString()))); //open airlines filter
        controller().waitForElement(ResultsPageLocators.CLEAR_ALL_AIRLINES.get());
        controller().press(ResultsPageLocators.CLEAR_ALL_AIRLINES.get()); //clear all airlines
        controller().press(ResultsPageLocators.AEGEAN.get()); // select aegean airline
        controller().press(filterLocatorByHeader(getHeaderName(FilterHeaders.AIRLINES.toString()))); //close airlines filter
    }

    public String getPlaceOfFlight(){
        return controller().getText(ResultsPageLocators.PLACES_OF_FLIGHTS_TITLE.get());
    }

    public String getDatesOfFlight(){
        return controller().getText(ResultsPageLocators.FLIGHT_DATES_TITLE.get());
    }


}
