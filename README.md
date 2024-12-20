# Simple_Automation_Login_Testing

## Description
This project automates the testing of Login and Reset password functionalities. The testing framework uses **Cucumber** for writing behavior-driven development (BDD) scenarios, and **Selenium** for executing these tests in the Chrome browser. Currently, the project includes 14 test cases.

## Features
- Automated testing for Login UI using **Selenium**.
- Automated testing for OTP API using Rest Assured.
- Cucumber for defining test scenarios in an easy-to-read format.



## Requirements
- Java 23 or higher
- Maven
- Google Chrome browser
- ChromeDriver ( Download ChromeDriver and ensure it's compatible with your Chrome version)


## Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/Razan-Eldaif/Simple_Automation_Login_Testing.git
   ```
2. Navigate into the project directory:
   ```bash
   cd Simple_Automation_Login_Testing
   ```
3. Build the project using Maven:
   ```bash
   mvn clean install
   ```

## Usage
1. Open the terminal and navigate to the project root directory.
2. Open The "TestRunner" Class to identify the feature file you want to run. (By default it's LoginUI.feature)
    ```bash
           features = "src/test/resources/features/LoginUI.feature", // Path to the file you want to run

   ```
4. Run the tests using Maven:
   ```bash
   mvn test
   ```

## Test Results
After running your test, open the "target" file, you will find an html file called "Cucumber-report", Open it with your browser to see the test result.


## Project Structure
 ```bash
Simple_Automation_Login_Testing/  
├── src/  
│   ├── main/                      
│   └── test/  
│       ├── java/  
│       │   └── steps/           
│       │       ├── LoginUiExecution.java  // Step definitions for Login functionality tests  
│       │       └── PasswordResetExecution.java // Step definitions for Password Reset functionality tests  
│       │       └── TestRunner.java          // Test runner class for TestNG  
│       └── resources/  
│           └── features/  
│               ├── Login.feature         // Cucumber feature file for login tests  
│               ├── PasswordReset.feature  // Cucumber feature file for password reset tests  
├── testng.xml                      
├── pom.xml                          // Maven configuration file  
└── README.md                        
 ```

## Test Cases For Login
- Verify the Presence of the Login Form
- Verify Login with Incorrect Password
- Verify Login with Unregistered Phone
- Verify Login with Incorrect Phone
- Verify Login with Blank Password and Phone
- Verify Login with Valid Phone and Blank Password
- Verify Login with Blank Phone and valid Password
- Verify Phone Number Field Validation
- Verify Password Hidden
- User Enters an Unregistered Phone Number
  
## Test Cases For Reset Password
- User Enters an Unregistered Phone Number
- User Enters a Registered Phone Number (This include API invoke using userID to get the OTP).
- User Enters Incorrect OTP and a Short Password
- User Enters an Invalid Password
- User Successfully Resets the Password
  
## Considerations
1. If you got any problem, please ensure you logged out before starting the test.
2. If you got problem while running login with correct credentials, change the password to "Pass@new456" because during my test it maybe change.
3. For now just run the LoginUI file, because during the testing of Reset Password page I have been blocked from the Server. So I coudlnt run all the Reset Password test cases.

## Challenges
The proccess of Finding a particular element in the page took some time.

## Contact
For any questions feel free to reach out:
- Email: razaneldaif@gmail.com
