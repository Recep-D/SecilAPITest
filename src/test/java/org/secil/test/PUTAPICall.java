package org.secil.test;

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

public class PUTAPICall extends Hooks {

    static String emailId;

    public static String getRandomEmail() {
        emailId = "recep" + System.currentTimeMillis() + "@gmail.com";
        return emailId;
    }

    @Test
    public void putUserTest() {
        APIResponse putApiResponse = requestContext.put("https://jsonplaceholder.typicode.com/users/1",
                RequestOptions.create()
                        .setHeader("Content-Type", "application/json; charset=utf-8")
                        .setQueryParam("name", "new recep")
                        .setQueryParam("email", emailId)
        );
        System.out.println("putApiResponse = " + putApiResponse.status());
        System.out.println("putApiResponse.statusText() = " + putApiResponse.statusText());
        System.out.println("putApiResponse.headers() = " + putApiResponse.headers());
        Assert.assertEquals(putApiResponse.status(), 200);
        Assert.assertEquals(putApiResponse.statusText(), "OK");
        Assert.assertTrue(putApiResponse.headers().get("content-type").contains("application/json"));
        Assert.assertTrue(putApiResponse.headers().get("x-content-type-options").contains("nosniff"));


    }
}
