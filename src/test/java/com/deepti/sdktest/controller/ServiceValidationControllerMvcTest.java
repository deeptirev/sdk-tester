package com.deepti.sdktest.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest
public class ServiceValidationControllerMvcTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testServiceSuccess() throws Exception {
        String body = " [ " +
                "{" +
                "\"checkType\": \"DEVICE\"," +
                "\"activityType\": \"SIGNUP\"," +
                "\"checkSessionKey\": \"abc\"," +
                "\"activityData\": [" +
                "{" +
                "\"kvpKey\": \"some.key\"," +
                "\"kvpValue\": \"10\"," +
                "\"kvpType\": \"general.float\"" +
                "}" +
                "]" +
                "}" +
                "]";
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/isgood")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String resultString = result.getResponse().getContentAsString();
        assertNotNull(resultString);
        assertTrue(resultString.contains("puppy"));
        assertTrue(resultString.contains("true"));
    }

    @Test
    public void testServiceBadCheckType() throws Exception {
        String body = " [ " +
                "{" +
                "\"checkType\": \"WRONG_ENUM\"," +
                "\"activityType\": \"SIGNUP\"," +
                "\"checkSessionKey\": \"abc\"," +
                "\"activityData\": [" +
                "{" +
                "\"kvpKey\": \"some.key\"," +
                "\"kvpValue\": \"10\"," +
                "\"kvpType\": \"general.float\"" +
                "}" +
                "]" +
                "}" +
                "]";
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/isgood")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andReturn();

        String resultString = result.getResponse().getContentAsString();
        assertNotNull(resultString);
        assertTrue(resultString.contains("\"code\":0"));
        assertTrue(resultString.contains("checkType not one of"));
    }

    @Test
    public void testServiceBadActivityType() throws Exception {
        String body = " [ " +
                "{" +
                "\"checkType\": \"DEVICE\"," +
                "\"activityType\": \"WRONG_ENUM\"," +
                "\"checkSessionKey\": \"abc\"," +
                "\"activityData\": [" +
                "{" +
                "\"kvpKey\": \"some.key\"," +
                "\"kvpValue\": \"10\"," +
                "\"kvpType\": \"general.float\"" +
                "}" +
                "]" +
                "}" +
                "]";
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/isgood")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andReturn();

        String resultString = result.getResponse().getContentAsString();
        assertNotNull(resultString);
        assertTrue(resultString.contains("\"code\":0"));
        assertTrue(resultString.contains("activityType not one of"));
    }

    @Test
    public void testServiceDupeSessionKey() throws Exception {
        String body = " [ " +
                "{" +
                "\"checkType\": \"DEVICE\"," +
                "\"activityType\": \"SIGNUP\"," +
                "\"checkSessionKey\": \"unique_key\"," +
                "\"activityData\": [" +
                "{" +
                "\"kvpKey\": \"some.key\"," +
                "\"kvpValue\": \"10\"," +
                "\"kvpType\": \"general.float\"" +
                "}" +
                "]" +
                "}" +
                "]";
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/isgood")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        MvcResult result1 = mvc.perform(MockMvcRequestBuilders.post("/isgood")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andReturn();

        String resultString = result1.getResponse().getContentAsString();
        assertNotNull(resultString);
        assertTrue(resultString.contains("\"code\":0"));
        assertTrue(resultString.contains("Duplicate"));
    }

    @Test
    public void testServiceActivityDataDupeKeys() throws Exception {
        String body = " [ " +
                "{" +
                "\"checkType\": \"DEVICE\"," +
                "\"activityType\": \"SIGNUP\"," +
                "\"checkSessionKey\": \"abc\"," +
                "\"activityData\": [" +
                "{" +
                "\"kvpKey\": \"some.dupe.key\"," +
                "\"kvpValue\": \"10\"," +
                "\"kvpType\": \"general.float\"" +
                "}," +
                "{" +
                "\"kvpKey\": \"some.dupe.key\"," +
                "\"kvpValue\": \"10\"," +
                "\"kvpType\": \"general.float\"" +
                "}" +
                "]" +
                "}" +
                "]";
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/isgood")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andReturn();

        String resultString = result.getResponse().getContentAsString();
        assertNotNull(resultString);
        assertTrue(resultString.contains("\"code\":0"));
        assertTrue(resultString.contains("Duplicate keys in the request payload"));
    }

    @Test
    public void testServiceActivityDataIncompatibleTypes() throws Exception {
        String body = " [ " +
                "{" +
                "\"checkType\": \"DEVICE\"," +
                "\"activityType\": \"SIGNUP\"," +
                "\"checkSessionKey\": \"abc\"," +
                "\"activityData\": [" +
                "{" +
                "\"kvpKey\": \"some.key\"," +
                "\"kvpValue\": \"10\"," +
                "\"kvpType\": \"general.bool\"" +
                "}" +
                "]" +
                "}" +
                "]";
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/isgood")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andReturn();

        String resultString = result.getResponse().getContentAsString();
        assertNotNull(resultString);
        assertTrue(resultString.contains("\"code\":0"));
        assertTrue(resultString.contains("kvpValue-kvpType not compatible"));
    }

    @Test
    public void testServiceMalformedBody() throws Exception {
        String body = " [ " +
                "{" +
                "\"checkType\": \"DEVICE\"," +
                "\"activityType\": \"SIGNUP\"," +
                "\"checkSessionKey\": \"abc\"" +
                "\"activityData\": [" +
                "{,," +
                "\"kvpKey\": \"some.key\"," +
                "\"kvpValue\": \"10\"," +
                "\"kvpType\": \"general.bool\"" +
                "}" +
                "]" +
                "}" +
                "]";
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/isgood")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

}