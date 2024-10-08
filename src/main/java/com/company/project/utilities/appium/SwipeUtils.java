package com.company.project.utilities.appium;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;

public class SwipeUtils {

    private AppiumDriver driver;
    private final int MAX_SWIPE_ATTEMPTS = 5; // Limit swipes to avoid infinite loop

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    public SwipeUtils(AppiumDriver driver) {
        this.driver = driver;
    }

    public void swipeUntilElementVisible(By locator, Direction direction) {
        int swipeAttempts = 0;
        while (swipeAttempts < MAX_SWIPE_ATTEMPTS) {
            try {
                // Try to find the element, if found, exit loop
                WebElement element = new WebDriverWait(driver, Duration.ofSeconds(1))
                        .until(ExpectedConditions.visibilityOfElementLocated(locator));
                if (element.isDisplayed()) {
                    return;
                }
            } catch (Exception e) {
                // Element not found, so swipe
                swipe(direction);
                swipeAttempts++;
            }
        }
        throw new RuntimeException("Element not found after swiping " + MAX_SWIPE_ATTEMPTS + " times.");
    }

    private void swipe(Direction direction) {
        int height = driver.manage().window().getSize().height;
        int width = driver.manage().window().getSize().width;

        int startX, startY, endX, endY;

        // Determine the start and end coordinates for each direction
        switch (direction) {
            case UP:
                startX = width / 2;
                startY = (int) (height * 0.8);
                endX = width / 2;
                endY = (int) (height * 0.2);
                break;

            case DOWN:
                startX = width / 2;
                startY = (int) (height * 0.2);
                endX = width / 2;
                endY = (int) (height * 0.8);
                break;

            case LEFT:
                startX = (int) (width * 0.8);
                startY = height / 2;
                endX = (int) (width * 0.2);
                endY = height / 2;
                break;

            case RIGHT:
                startX = (int) (width * 0.2);
                startY = height / 2;
                endX = (int) (width * 0.8);
                endY = height / 2;
                break;

            default:
                throw new IllegalArgumentException("Invalid swipe direction");
        }

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);

        // Add actions to perform the swipe
        swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(600), PointerInput.Origin.viewport(), endX, endY));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Arrays.asList(swipe));
    }
}
