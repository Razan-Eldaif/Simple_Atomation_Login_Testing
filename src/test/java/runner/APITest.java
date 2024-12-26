package runner;

import org.testng.Assert;
import org.testng.annotations.Test;
import steps.PasswordResetExecution;

public class APITest {

    @Test
    public void testGetOtpFromTheApi() {
        PasswordResetExecution passwordResetExecution = new PasswordResetExecution();
        String userId = "163165"; // User ID
        passwordResetExecution.getOtpFromTheApi(userId);

        // Verify that OTP is not null
        String otp = passwordResetExecution.getOtp();
        Assert.assertNotNull(otp, "OTP should not be null");
        Assert.assertFalse(otp.isEmpty(), "OTP should not be empty");

    }
}
