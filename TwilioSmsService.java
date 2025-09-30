package com.example.demo.services;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TwilioSmsService {

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    // Use ONE of these: prefer Messaging Service SID
    @Value("${twilio.messaging.service.sid:}") // e.g., MGXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    private String messagingServiceSid;

    @Value("${twilio.phone.number:}") // Twilio-number in E.164, e.g., +14155550123
    private String fromNumber;

    @PostConstruct
    void init() {
        if (isBlank(accountSid) || isBlank(authToken)) {
            throw new IllegalStateException("Twilio credentials missing: twilio.account.sid / twilio.auth.token");
        }
        Twilio.init(accountSid, authToken);

        if (isBlank(messagingServiceSid) && isBlank(fromNumber)) {
            throw new IllegalStateException(
                    "Configure either twilio.messaging.service.sid (recommended) OR twilio.phone.number");
        }
        if (!isBlank(fromNumber) && !fromNumber.startsWith("+")) {
            throw new IllegalStateException("twilio.phone.number must be E.164 (start with + and country code)");
        }
    }

    /**
     * Send an SMS.
     * @param toPhoneNumber E.164 number (e.g., +9477xxxxxxx). Normalize before calling.
     * @param body message text
     * @return true if Twilio accepted the message; false if validation or API error occurred.
     */
    public boolean sendSms(String toPhoneNumber, String body) {
        try {
            if (isBlank(toPhoneNumber) || isBlank(body)) {
                System.err.println("Failed to send SMS: missing 'to' or 'body'");
                return false;
            }

            // Prevent self-send when using a fixed From number
            if (!isBlank(fromNumber) && fromNumber.equals(toPhoneNumber)) {
                System.err.println("Skipping SMS: 'To' and 'From' are identical (" + toPhoneNumber + ")");
                return false;
            }

            if (!isBlank(messagingServiceSid)) {
                Message.creator(new PhoneNumber(toPhoneNumber), messagingServiceSid, body).create();
            } else {
                Message.creator(new PhoneNumber(toPhoneNumber), new PhoneNumber(fromNumber), body).create();
            }
            return true;

        } catch (Exception e) {
            System.err.println("Failed to send SMS: " + e.getMessage());
            return false;
        }
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
