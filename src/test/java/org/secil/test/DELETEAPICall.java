package org.secil.test;

import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;
import org.secil.utils.Hooks;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DELETEAPICall extends Hooks {

    @Test
    public void deleteUserTest() {
        APIResponse deleteApiResponse= requestContext.delete("https://jsonplaceholder.typicode.com/users/1",
                RequestOptions.create()
                        .setHeader("Content-Type", "application/json; charset=utf-8")
        );

        System.out.println("deleteApiResponse = " + deleteApiResponse.status());
        System.out.println("deleteApiResponse.statusText() = " + deleteApiResponse.statusText());
        System.out.println("deleteApiResponse.text() = " + deleteApiResponse.text());
        Assert.assertEquals(deleteApiResponse.status(),200);
        Assert.assertEquals(deleteApiResponse.statusText(),"OK");
        Assert.assertTrue(deleteApiResponse.text().contains("{}"));






    }
}
