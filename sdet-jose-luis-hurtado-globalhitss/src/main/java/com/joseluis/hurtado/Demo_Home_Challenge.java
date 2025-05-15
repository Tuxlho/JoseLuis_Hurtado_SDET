package com.joseluis.hurtado;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

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

    private void makeProductSearch(String productToSearch, WebDriver currentDriver)
    {
        final By navSearchBy = new By.ByCssSelector("form[class='nav-search'] input");
        final By sumbmitButtonBy = new By.ByCssSelector("form[class='nav-search'] button");

        WebElement navSearch = currentDriver.findElement(navSearchBy);
        navSearch.sendKeys(productToSearch);
        WebElement searchButton = currentDriver.findElement(sumbmitButtonBy);
        searchButton.click();
        testDriver = currentDriver;
    }

    private void closeIntroduceCodigoPostal(WebDriver currentDriver)
    {
        final By recuadroPreguntaIngresaCodigoPostal = new By.ByCssSelector("div[class='onboarding-cp'] div");
        final By butonMasTardeIngresarBy = new By.ByCssSelector("button[data-js='onboarding-cp-close'] span");

        WebDriverWait waitingForFloatingWindows = new WebDriverWait(currentDriver, Duration.ofSeconds(5));
        waitingForFloatingWindows.until(ExpectedConditions.presenceOfElementLocated(recuadroPreguntaIngresaCodigoPostal));

        currentDriver.findElement(butonMasTardeIngresarBy).click();
        testDriver = currentDriver;
    }

    private void makeFilterNuevos(WebDriver currentDriver)
    {
        final By moduleFiltersBy = new By.ByCssSelector("section[class='ui-search-filter-groups']");
        final By filtroCondicionBy = new By.ByCssSelector("div[class='ui-search-filter-dl']:nth-child(5)");
        final By nuevoLinkNameBy = new By.ByCssSelector("ul li:first-child a");

        WebDriverWait waitingForCondicionFiltro = new WebDriverWait(currentDriver, Duration.ofSeconds(5));
        waitingForCondicionFiltro.until(ExpectedConditions.presenceOfElementLocated(moduleFiltersBy));
        waitingForCondicionFiltro.until(ExpectedConditions.presenceOfElementLocated(filtroCondicionBy));

        WebElement moduleFilters = currentDriver.findElement(moduleFiltersBy);
        WebElement filtroCondicion = moduleFilters.findElement(filtroCondicionBy);
        WebElement applyNuevoAsFilter = filtroCondicion.findElement(nuevoLinkNameBy);
        applyNuevoAsFilter.click();
    }

    public static void main(String[] args)
    {
        Demo_Home_Challenge tmpDemoResources = new Demo_Home_Challenge();
        testDriver = tmpDemoResources.InitializeWebDriver();
        testDriver.get(testURL);
        WebElement countrySelector = testDriver.findElement(countryMx);
        countrySelector.click();
        tmpDemoResources.closeIntroduceCodigoPostal(testDriver);
        tmpDemoResources.makeProductSearch("playstation 5", testDriver);
        tmpDemoResources.makeFilterNuevos(testDriver);
        testDriver.quit();
    }
}
