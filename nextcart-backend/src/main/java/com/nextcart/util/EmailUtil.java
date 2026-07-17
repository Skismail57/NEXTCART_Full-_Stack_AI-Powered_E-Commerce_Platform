package com.nextcart.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO: Implement actual email sending with JavaMailSender or similar
public class EmailUtil {
    private static final Logger logger = LoggerFactory.getLogger(EmailUtil.class);

    public static void sendOTP(String toEmail, String otp) {
        logger.info("Sending OTP {} to email {}", otp, toEmail);
        // TODO: Implement actual email sending
    }

    public static void sendOrderConfirmation(String toEmail, String orderNumber) {
        logger.info("Sending order confirmation for order {} to email {}", orderNumber, toEmail);
        // TODO: Implement actual email sending
    }
}
