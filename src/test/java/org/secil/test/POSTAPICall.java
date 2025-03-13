package org.secil.test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import org.secil.utils.Hooks;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class POSTAPICall extends Hooks {

    static String emailId;

    public static String getRandomEmail() {
        emailId = "recep" + System.currentTimeMillis() + "@gmail.com";
        return emailId;
    }

    @Test
    public void createUserTest() throws IOException {

        Map<String, Object> data = new HashMap<>();
        data.put("name", "recep laz");
        data.put("username", "lazer");
        data.put("email", getRandomEmail());

        APIResponse apiPOSTResponse = requestContext.post("https://jsonplaceholder.typicode.com/users",
                RequestOptions.create()
                        .setHeader("Content-Type", "application/json; charset=utf-8")
                        .setData(data));

        System.out.println("apiPOSTResponse = " + apiPOSTResponse.status());
        Assert.assertEquals(apiPOSTResponse.status(), 201);
        Assert.assertEquals(apiPOSTResponse.statusText(), "Created");

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode postJsonNode = objectMapper.readTree(apiPOSTResponse.body());
        String postJsonPrettyResponse = postJsonNode.toPrettyString();
        System.out.println("postJsonPrettyResponse = " + postJsonPrettyResponse);
        Assert.assertNotNull(postJsonPrettyResponse);
        Assert.assertTrue(postJsonPrettyResponse.contains("recep laz"));
        Assert.assertTrue(postJsonPrettyResponse.contains("lazer"));
        Assert.assertTrue(postJsonPrettyResponse.contains(emailId));

        String userId = postJsonNode.get("id").asText();
        System.out.println("userId = " + userId);
        Assert.assertNotNull(userId);

        /**
         // Normally, for Double check we can use "GET" request: for this demo,404 not found error because of missing Authentication
         APIResponse apiGETResponse=requestContext.get("https://jsonplaceholder.typicode.com/users/"+userId);
         System.out.println("apiGETResponse Text = " + apiGETResponse.text()); // Empty
         System.out.println("apiGETResponse = " + apiGETResponse.status());    //404 error message
         Assert.assertEquals(apiGETResponse.status(), 200);     // Actual result:404
         Assert.assertEquals(apiGETResponse.statusText(), "OK");
         Assert.assertTrue(apiGETResponse.text().contains(emailId));
         */


    }
}
