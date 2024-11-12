
---

# TestNG Setup and Usage Guide

## 1. What is TestNG?

**TestNG** is a testing framework inspired by JUnit and NUnit but introduces new functionalities, making it more powerful and easier to use for both unit testing and integration testing. It provides the ability to run tests in parallel, define dependencies, configure different test configurations, and generate detailed reports.

## 2. Installing TestNG in Your Project

### 2.1 Maven Project Setup
1. If you are using Maven, add the TestNG dependency to your `pom.xml`:
```xml
<dependencies>
    <dependency>
        <groupId>org.testng</groupId>
        <artifactId>testng</artifactId>
        <version>7.8.0</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```
2. After adding the dependency, run `mvn install` to download and include TestNG in your project.

### 2.2 Install TestNG in IntelliJ/Eclipse
- In IntelliJ, you can directly use the TestNG plugin that comes built-in.
- In Eclipse, install TestNG from the **Eclipse Marketplace**:
    1. Go to `Help` > `Eclipse Marketplace`.
    2. Search for **TestNG** and click on install.

## 3. Basic TestNG Annotations

TestNG uses annotations to define the structure and behavior of the test methods. Here are the most commonly used annotations:

- **@Test**: Marks a method as a test method.
- **@BeforeMethod**: Runs before each `@Test` method.
- **@AfterMethod**: Runs after each `@Test` method.
- **@BeforeClass**: Runs once before the first test method in the current class is invoked.
- **@AfterClass**: Runs once after all the test methods in the current class are invoked.
- **@BeforeSuite**: Runs before all tests in the suite.
- **@AfterSuite**: Runs after all tests in the suite.

## 4. Writing Your First TestNG Test

```java
import org.testng.annotations.Test;

public class SampleTest {
    @Test
    public void testMethod1() {
        System.out.println("This is Test Method 1");
    }

    @Test
    public void testMethod2() {
        System.out.println("This is Test Method 2");
    }
}
```

### 4.1 Running the Test
- Right-click on the class file in your IDE and choose `Run as TestNG Test` (in IntelliJ) or `Run as TestNG` (in Eclipse).

### 4.2 TestNG XML Configuration (Optional)
You can define a `testng.xml` file to run multiple tests or configure suites.

```xml
<!DOCTYPE suite SYSTEM "https://testng.org/testng-1.0.dtd" >
<suite name="Sample Suite">
    <test name="Sample Test">
        <classes>
            <class name="SampleTest"/>
        </classes>
    </test>
</suite>
```

Run this file by right-clicking and selecting `Run as TestNG Suite`.

## 5. Parameterizing Tests

You can pass parameters to your test methods using the `@Parameters` annotation.

### 5.1 Example of Parameterized Test

```java
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class ParameterizedTest {
    @Test
    @Parameters({"param1", "param2"})
    public void parameterTest(String param1, String param2) {
        System.out.println("Parameter 1: " + param1);
        System.out.println("Parameter 2: " + param2);
    }
}
```

### 5.2 Passing Parameters via testng.xml

```xml
<!DOCTYPE suite SYSTEM "https://testng.org/testng-1.0.dtd" >
<suite name="Parameterized Suite">
    <test name="Parameterized Test">
        <parameter name="param1" value="FirstParam"/>
        <parameter name="param2" value="SecondParam"/>
        <classes>
            <class name="ParameterizedTest"/>
        </classes>
    </test>
</suite>
```

## 6. TestNG Features

### 6.1 Grouping Tests
You can group multiple tests and run specific groups.

```java
import org.testng.annotations.Test;

public class GroupedTest {
    @Test(groups = { "sanity" })
    public void testSanity() {
        System.out.println("Sanity Test");
    }

    @Test(groups = { "regression" })
    public void testRegression() {
        System.out.println("Regression Test");
    }
}
```

### 6.2 Test Dependencies
You can make one test dependent on another using `dependsOnMethods`.

```java
@Test
public class DependentTest {
    @Test
    public void initialTest() {
        System.out.println("Initial Test");
    }

    @Test(dependsOnMethods = { "initialTest" })
    public void dependentTest() {
        System.out.println("Dependent Test");
    }
}
```

### 6.3 Parallel Execution
TestNG supports parallel test execution. In `testng.xml`, you can define how tests should run in parallel.

```xml
<suite name="Parallel Suite" parallel="methods" thread-count="4">
    <test name="TestParallelExecution">
        <classes>
            <class name="SampleTest"/>
        </classes>
    </test>
</suite>
```

## 7. TestNG Assertions

TestNG provides different assertions to validate test conditions. Common ones include:

- `assertEquals(actual, expected)`
- `assertTrue(condition)`
- `assertFalse(condition)`
- `assertNotNull(object)`

### 7.1 Example of Assertions

```java
import org.testng.Assert;
import org.testng.annotations.Test;

public class AssertionTest {
    @Test
    public void assertTest() {
        String expected = "Hello";
        String actual = "Hello";
        Assert.assertEquals(actual, expected, "Strings did not match!");
    }
}
```

## 8. Generating Test Reports

TestNG automatically generates reports in the `test-output` folder after the tests have been executed. To view them:
1. Run the test or suite.
2. Navigate to the `test-output` folder and open `index.html`.

## 9. Additional Resources

- [TestNG Documentation](https://testng.org/doc/index.html)
- [TestNG GitHub](https://github.com/cbeust/testng)

---
