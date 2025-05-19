package com.joseluis.hurtado;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class Demo_Home_Challenge {
    private static final String testURL = "https://www.mercadolibre.com";
    private static final By countryMx = new By.ByCssSelector("a#MX");
    private static WebDriver testDriver;

    private WebDriver InitializeWebDriver() {
        System.setProperty("webdriver.chrome.driver", "../../../WebDriver/chromedriver-win64/chromedriver.exe");
        WebDriver tmpDriver = new ChromeDriver();
        tmpDriver.manage().window().maximize();
        return tmpDriver;
    }

    private void makeProductSearch(String productToSearch, WebDriver currentDriver) {
        final By navSearchBy = new By.ByCssSelector("form[class='nav-search'] input");
        final By sumbmitButtonBy = new By.ByCssSelector("form[class='nav-search'] button");

        WebElement navSearch = currentDriver.findElement(navSearchBy);
        navSearch.sendKeys(productToSearch);
        WebElement searchButton = currentDriver.findElement(sumbmitButtonBy);
        searchButton.click();
        testDriver = currentDriver;
    }

    private void closeIntroduceCodigoPostal(WebDriver currentDriver) {
        final By recuadroPreguntaIngresaCodigoPostal = new By.ByCssSelector("div[class='onboarding-cp'] div");
        final By butonMasTardeIngresarBy = new By.ByCssSelector("button[data-js='onboarding-cp-close'] span");

        WebDriverWait waitingForFloatingWindows = new WebDriverWait(currentDriver, Duration.ofSeconds(5));
        waitingForFloatingWindows.until(ExpectedConditions.presenceOfElementLocated(recuadroPreguntaIngresaCodigoPostal));

        currentDriver.findElement(butonMasTardeIngresarBy).click();
        testDriver = currentDriver;
    }

    private void makeAnyFilter(WebDriver currentDriver, String[] filterSetup) {
        final By moduleFiltersBy = new By.ByCssSelector(filterSetup[0]);
        final By filtroCondicionBy = new By.ByCssSelector(filterSetup[1]);
        final By nuevoLinkNameBy = new By.ByCssSelector(filterSetup[2]);

        WebDriverWait waitingForCondicionFiltro = new WebDriverWait(currentDriver, Duration.ofSeconds(5));
        waitingForCondicionFiltro.until(ExpectedConditions.presenceOfElementLocated(moduleFiltersBy));
        waitingForCondicionFiltro.until(ExpectedConditions.presenceOfElementLocated(filtroCondicionBy));

        WebElement moduleFilters = currentDriver.findElement(moduleFiltersBy);
        WebElement filtroCondicion = moduleFilters.findElement(filtroCondicionBy);
        new Actions(currentDriver).scrollToElement(filtroCondicion).perform();
        WebElement applyNuevoAsFilter = filtroCondicion.findElement(nuevoLinkNameBy);
        new Actions(currentDriver).scrollToElement(applyNuevoAsFilter).perform();
        applyNuevoAsFilter.click();
        testDriver = currentDriver;
    }

    private void waitElementAndAfterClick(WebDriver currentDriver, By elementToCoordinate) {
        WebDriverWait waitingForElement = new WebDriverWait(currentDriver, Duration.ofSeconds(12));
        waitingForElement.until(ExpectedConditions.presenceOfElementLocated(elementToCoordinate));
        waitingForElement.until(ExpectedConditions.elementToBeClickable(elementToCoordinate));
        currentDriver.findElement(elementToCoordinate).click();
        testDriver = currentDriver;
    }

    private String[] GotProductNameList(WebDriver currentDriver) throws InterruptedException {
        final By productItemContainer = new By.ByCssSelector("ol[class='ui-search-layout ui-search-layout--stack'] li");
        final By productItemName = new By.ByCssSelector("h3");
        String[] productTitleList;
        int counter = 0;

        Thread.sleep(35);
        WebDriverWait waitingForItemsList = new WebDriverWait(currentDriver, Duration.ofSeconds(15));
        waitingForItemsList.until(ExpectedConditions.numberOfElementsToBeMoreThan(productItemContainer, 15));
        waitingForItemsList.until(ExpectedConditions.numberOfElementsToBeMoreThan(new By.ByCssSelector("ol[class='ui-search-layout ui-search-layout--stack'] li h3"), 15));

        List<WebElement> itemsCardSpaceList = currentDriver.findElements(productItemContainer);
        productTitleList = new String[itemsCardSpaceList.size()];
        for (WebElement currentCardSpace : itemsCardSpaceList) {
            WebElement tmpCurrentProductTitle = currentCardSpace.findElement(productItemName);
            productTitleList[counter] = tmpCurrentProductTitle.getText();
            counter++;
        }
        testDriver = currentDriver;
        return productTitleList;
    }

    public static void main(String[] args) throws InterruptedException {
        String[] filtersLevelToNuevo = {"section[class='ui-search-filter-groups']", "div[class='ui-search-filter-dl']:nth-child(5)", "ul li:first-child a"};
        String[] filtersLevelToLocation = {"section[class='ui-search-filter-groups']", "div[class='ui-search-filter-dl']:nth-child(10)", "ul li:first-child a span[class*='name']"};
        Demo_Home_Challenge tmpDemoResources = new Demo_Home_Challenge();
        testDriver = tmpDemoResources.InitializeWebDriver();
        testDriver.get(testURL);
        WebElement countrySelector = testDriver.findElement(countryMx);
        countrySelector.click();
        tmpDemoResources.closeIntroduceCodigoPostal(testDriver);
        tmpDemoResources.makeProductSearch("playstation 5", testDriver);
        tmpDemoResources.makeAnyFilter(testDriver, filtersLevelToNuevo);
        tmpDemoResources.makeAnyFilter(testDriver, filtersLevelToLocation);
        testDriver.navigate().refresh();
        tmpDemoResources.waitElementAndAfterClick(testDriver, new By.ByCssSelector("div[class='ui-search-sort-filter'] button div svg"));
        tmpDemoResources.waitElementAndAfterClick(testDriver, new By.ByCssSelector("div[data-testid='popper'] ul li:nth-child(3) span"));
        testDriver.navigate().refresh();
        tmpDemoResources.GotProductNameList(testDriver);
        testDriver.quit();
    }
}
