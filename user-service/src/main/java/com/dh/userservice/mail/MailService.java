package com.dh.userservice.mail;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sendgrid.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    private static final Logger logger = LoggerFactory.getLogger(MailService.class);

    @Value("${spring.sendgrid.api-key}")
    private String key;

    private String template = "d-fbf47b7ceb0a4cbd9b74bb6b800a5cb1";

    public String send(String email, String name) throws IOException {
        // the sender email should be the same as we used to Create a Single Sender Verification
        Email from = new Email("damiansformo@gmail.com");
        Email to = new Email(email);
        Mail mail = new Mail();
        // we create an object of our static class feel free to change the class on it's own file
        // I try to keep every think simple
        DynamicTemplatePersonalization personalization = new DynamicTemplatePersonalization();
        personalization.addTo(to);
        mail.setFrom(from);
        mail.setSubject("test email");
        // This is the first_name variable that we created on the template
        personalization.addDynamicTemplateData("name", name);
        mail.addPersonalization(personalization);
        mail.setTemplateId(template);
        // this is the api key
        SendGrid sg = new SendGrid(key);
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            logger.info(response.getBody());
            return response.getBody();
        } catch (IOException ex) {
            throw ex;
        }
    }

    // This class handels the dynamic data for the template
    // Feel free to customise this class our to putted on other file
    private static class DynamicTemplatePersonalization extends Personalization {

        @JsonProperty(value = "dynamic_template_data")
        private Map<String, String> dynamic_template_data;

        @JsonProperty("dynamic_template_data")
        public Map<String, String> getDynamicTemplateData() {
            if (dynamic_template_data == null) {
                return Collections.<String, String>emptyMap();
            }
            return dynamic_template_data;
        }

        public void addDynamicTemplateData(String key, String value) {
            if (dynamic_template_data == null) {
                dynamic_template_data = new HashMap<String, String>();
                dynamic_template_data.put(key, value);
            } else {
                dynamic_template_data.put(key, value);
            }
        }

    }
}