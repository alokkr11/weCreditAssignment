package com.weCredit.weCreditAssignment;

import com.twilio.Twilio;
import com.weCredit.weCreditAssignment.Config.TwilioConfig;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WeCreditAssignmentApplication {

	@Autowired
	private TwilioConfig twilioConfig;

	@PostConstruct
	public void initTwilio(){
		Twilio.init(twilioConfig.getAccountSid(),twilioConfig.getAuthToken());
	}

	public static void main(String[] args) {
		SpringApplication.run(WeCreditAssignmentApplication.class, args);
	}

}
