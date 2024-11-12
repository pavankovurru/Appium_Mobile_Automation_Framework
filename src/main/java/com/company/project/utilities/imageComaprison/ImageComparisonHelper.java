package com.company.project.utilities.imageComaprison;

import com.github.romankh3.image.comparison.ImageComparison;
import com.github.romankh3.image.comparison.ImageComparisonUtil;
import com.github.romankh3.image.comparison.exception.ImageNotFoundException;
import com.github.romankh3.image.comparison.model.ImageComparisonResult;
import com.github.romankh3.image.comparison.model.ImageComparisonState;
import com.github.romankh3.image.comparison.model.Rectangle;
import io.appium.java_client.AppiumDriver;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;
import org.testng.SkipException;

public class ImageComparisonHelper {

  private AppiumDriver driver;

  public ImageComparisonHelper(AppiumDriver driver) {
    this.driver = driver;
  }

  public File captureScreenshot(String location, String ScreenShotName) throws Exception {
    File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
    String screenshotPath =
        System.getProperty("user.dir")
            + File.separator
            + location
            + File.separator
            + ScreenShotName
            + "-png";
    File screenshotLocation = new File(screenshotPath);
    FileUtils.copyFile(screenshot, screenshotLocation);
    return screenshotLocation;
  }

  public String getBaselineScreenshot(String location, String ScreenShotName) throws Exception {
    String screenshotPath =
        System.getProperty("user.dir")
            + File.separator
            + location
            + File.separator
            + ScreenShotName
            + "-png";
    try {
      ImageComparisonUtil.readImageFromResources(screenshotPath);
    } catch (ImageNotFoundException e) {
      captureScreenshot(location, ScreenShotName);
      throw new SkipException(
          "Baseline Screenshot Does not exist, A new baseline is created. Please run test again to compare basline and actual screen");
    } catch (Exception e) {
      throw new SkipException("Unknown Exception.");
    }
    return screenshotPath;
  }

  public void compareImagesExcludingAreas(File baselineImage, File actualImage, String resultFilePath, List<Rectangle> excludedAreas) throws IOException {

      // ***** Set Images  *****
    BufferedImage expectedImage =
        ImageComparisonUtil.readImageFromResources(baselineImage.getPath());
    BufferedImage actualScreenshot =
        ImageComparisonUtil.readImageFromResources(actualImage.getPath());
    ImageComparison imageComparison = new ImageComparison(expectedImage, actualScreenshot);

    // ***** Configuration*****
    // Threshold - it's the max distance between non-equal pixels. Default value is 5.
    imageComparison.setThreshold(10);
    // RectangleListWidth - Width of the line that is drawn in the rectangle. Default value is 1.
    imageComparison.setRectangleLineWidth(10);
    // PixelToleranceLevel - Default is 10% Pixel Tolerance Level
    imageComparison.setPixelToleranceLevel(0.15);
    // Excluded areas
    imageComparison.setExcludedAreas(excludedAreas);
    imageComparison.setDrawExcludedRectangles(true);

    // ***** Compare Images, Save Result *****
    ImageComparisonResult result = imageComparison.compareImages();
    File resultImageFile = new File(resultFilePath);
    ImageComparisonUtil.saveImage(resultImageFile, result.getResult());

    // ***** Assertion *****
    if (result.getImageComparisonState() == ImageComparisonState.MATCH) {
      Assert.assertTrue(true, "Images Match 🥳");
    } else {
      Assert.assertTrue(
          false, "Images Don't Match! - Check result saved at " + resultImageFile.getPath());
    }
  }

    public void compareImages(File baselineImage, File actualImage, String resultFilePath, List<Rectangle> excludedAreas) throws IOException {

        // ***** Set Images  *****
        BufferedImage expectedImage =
                ImageComparisonUtil.readImageFromResources(baselineImage.getPath());
        BufferedImage actualScreenshot =
                ImageComparisonUtil.readImageFromResources(actualImage.getPath());
        ImageComparison imageComparison = new ImageComparison(expectedImage, actualScreenshot);

        // ***** Configuration*****
        // Threshold - it's the max distance between non-equal pixels. Default value is 5.
        imageComparison.setThreshold(10);
        // RectangleListWidth - Width of the line that is drawn in the rectangle. Default value is 1.
        imageComparison.setRectangleLineWidth(10);
        // PixelToleranceLevel - Default is 10% Pixel Tolerance Level
        imageComparison.setPixelToleranceLevel(0.15);

        // ***** Compare Images, Save Result *****
        ImageComparisonResult result = imageComparison.compareImages();
        File resultImageFile = new File(resultFilePath);
        ImageComparisonUtil.saveImage(resultImageFile, result.getResult());

        // ***** Assertion *****
        if (result.getImageComparisonState() == ImageComparisonState.MATCH) {
            Assert.assertTrue(true, "Images Match 🥳");
        } else {
            Assert.assertTrue(
                    false, "Images Don't Match! - Check result saved at " + resultImageFile.getPath());
        }
    }
}
