package com.weCredit.weCreditAssignment.Config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class TwilioConfig {
    @Value("${twilio.account_sid}")
    private String accountSid;
    @Value("${twilio.auth_token}")
    private String authToken;
    @Value("${twilio.trial_number}")
    private String trialNumber;

}
