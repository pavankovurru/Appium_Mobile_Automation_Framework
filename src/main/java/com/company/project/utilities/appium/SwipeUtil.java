package com.company.project.utilities.appium;

import io.appium.java_client.AppiumDriver;
import java.time.Duration;
import java.util.Arrays;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

public class SwipeUtil {

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    public static void swipeUntilElementIsVisible(AppiumDriver driver, By elementLocator, Direction direction, int maxSwipes) {
        int swipeCount = 0;
        while (swipeCount < maxSwipes) {
            try {
                // If the element is already visible, no need to swipe
                if (driver.findElement(elementLocator).isDisplayed()) {
                    break;
                }
            } catch (Exception e) {
                // Element not found, proceed with swipe
            }

            Dimension size = driver.manage().window().getSize();
            int startX = size.width / 2;
            int startY = size.height / 2;

            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence swipe = new Sequence(finger, 1)
                    .addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY))
                    .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                    .addAction(finger.createPointerMove(Duration.ofMillis(500), PointerInput.Origin.viewport(),
                            calculateEndX(startX, size.width, direction),
                            calculateEndY(startY, size.height, direction)))
                    .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

            driver.perform(Arrays.asList(swipe));

            swipeCount++;
        }

        if (swipeCount == maxSwipes) {
            throw new RuntimeException("Element not found after " + maxSwipes + " swipes.");
        }
    }

    private static int calculateEndX(int startX, int screenWidth, Direction direction) {
        switch (direction) {
            case LEFT:
                return startX - (int) (screenWidth * 0.4);
            case RIGHT:
                return startX + (int) (screenWidth * 0.4);
            default:
                return startX;
        }
    }

    private static int calculateEndY(int startY, int screenHeight, Direction direction) {
        switch (direction) {
            case UP:
                return startY - (int) (screenHeight * 0.4);
            case DOWN:
                return startY + (int) (screenHeight * 0.4);
            default:
                return startY;
        }
    }
}