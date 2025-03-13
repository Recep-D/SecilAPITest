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
import org.testng.annotations.*;

import java.io.IOException;
import java.util.Map;

public class GETAPICall extends Hooks {

    @Test
    public void generalApiTest() throws IOException {
        APIResponse apiResponse = requestContext.get("https://jsonplaceholder.typicode.com/users");
        Assert.assertEquals(apiResponse.ok(), true);

        String statusResposeText = apiResponse.statusText();
        System.out.println("statusResponseText = " + statusResposeText);
        Assert.assertEquals(statusResposeText, "OK");

        System.out.println("apiResponse URL = " + apiResponse.url());
        Assert.assertEquals(apiResponse.url(), "https://jsonplaceholder.typicode.com/users");

        System.out.println("*******Headers validation section*****");
        Map<String, String> headersMap = apiResponse.headers();
        headersMap.forEach((k, v) -> System.out.println(k + " : " + v));

        System.out.println("Total response headers = " + headersMap.size());
        Assert.assertNotNull(headersMap);
        Assert.assertEquals(headersMap.get("content-type"), "application/json; charset=utf-8");
        Assert.assertEquals(headersMap.get("x-content-type-options"), "nosniff");

        System.out.println("******* Body() validation section*****");
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonResponse = objectMapper.readTree(apiResponse.body());
        String jsonPrettyResponse = jsonResponse.toPrettyString();
        System.out.println("jsonPrettyResponse = " + jsonPrettyResponse);
        Assert.assertNotNull(jsonPrettyResponse);
        Assert.assertTrue(jsonPrettyResponse.contains("Kurtis Weissnat"));
        Assert.assertTrue(jsonPrettyResponse.contains("Kurt"));
        Assert.assertTrue(jsonPrettyResponse.contains("Weissnat"));
        Assert.assertTrue(jsonPrettyResponse.contains("1"));


    }

    @Test
    public void specificUserApiTest() throws IOException {
        APIResponse apiResponse = requestContext.get("https://jsonplaceholder.typicode.com/users",
                RequestOptions.create()
                        .setQueryParam("zipcode", 58804 - 1099)
                        .setQueryParam("name", "Kurtis Weissnat"));

        Assert.assertEquals(apiResponse.ok(), true);
        Assert.assertEquals(apiResponse.statusText(), "OK");

        String responseText = apiResponse.text();
        System.out.println("responseText = " + responseText);
        Assert.assertNotNull(responseText);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonResponse = objectMapper.readTree(apiResponse.body());
        String jsonPrettyResponse = jsonResponse.toPrettyString();
        System.out.println("jsonPrettyResponse = " + jsonPrettyResponse);
        Assert.assertNotNull(jsonPrettyResponse);


    }

}
