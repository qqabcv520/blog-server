package lol.mifan.myblog;

import com.fasterxml.jackson.databind.ObjectMapper;
import lol.mifan.myblog.service.EncryptService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockFilterConfig;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class BlogServerApplicationTests {

    @Resource
    private EncryptService encryptService;

    @Autowired
    private WebApplicationContext wac;

    protected MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {


        DelegatingFilterProxy delegatingFilterProxy = new DelegatingFilterProxy("shiroFilter", wac);
        MockFilterConfig mockFilterConfig = new MockFilterConfig();
        mockFilterConfig.addInitParameter("targetFilterLifecycle", "true");
        delegatingFilterProxy.init(mockFilterConfig);
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .addFilter(delegatingFilterProxy, "/*")
                .build();
    }


    @After
    public void clear() throws Exception {

    }


    String login(String username, String password) throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/token")
                .content("{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}")
                .contentType(MediaType.APPLICATION_JSON_UTF8);

        MvcResult result = mockMvc.perform(requestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        String tokenJson = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(tokenJson).findValue("token").asText();
    }






    @Test
    public void notLoggedIn() throws Exception {

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/tags/1");

        MvcResult result = mockMvc.perform(requestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        Assert.assertEquals(result.getResponse().getStatus(), 403);
    }


	@Test
	public void logedIn() throws Exception {

        String token = login("mifan", "mifan");

        String username = "mifan";
        int nonce = new Random().nextInt();
        long curTime =  System.currentTimeMillis();
        String digest = encryptService.tokenDigest(token + nonce + curTime);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/tags/1")
                .header("Username", username)
                .header("Nonce", nonce)
                .header("CurTime", curTime)
                .header("Digest", digest)
                ;

        MvcResult result2 = mockMvc.perform(requestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        Assert.assertEquals(result2.getResponse().getStatus(), 200);
	}



    @Test
    public void duplicateLogin() throws Exception {

        String token = login("mifan", "mifan");

        String username = "mifan";

        int nonce = new Random().nextInt();
        long curTime =  System.currentTimeMillis();
        String digest = encryptService.tokenDigest(token + nonce + curTime);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/tags/1")
                .header("Username", username)
                .header("Nonce", nonce)
                .header("CurTime", curTime)
                .header("Digest", digest)
                ;

        MvcResult result = mockMvc.perform(requestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        Assert.assertEquals(result.getResponse().getStatus(), 200);


        Thread.sleep(1000);


        String token2 = login("mifan", "mifan");

        int nonce2 = new Random().nextInt();
        long curTime2 =  System.currentTimeMillis();
        String digest2 = encryptService.tokenDigest(token2 + nonce2 + curTime2);

        MockHttpServletRequestBuilder requestBuilder2 = MockMvcRequestBuilders
                .get("/tags/1")
                .header("Username", username)
                .header("Nonce", nonce2)
                .header("CurTime", curTime2)
                .header("Digest", digest2)
                ;

        MvcResult result2 = mockMvc.perform(requestBuilder2)
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        Assert.assertEquals(result2.getResponse().getStatus(), 200);


        Assert.assertNotEquals(token, token2);
    }


    @Test
    public void logout() throws Exception {
        //登录
        String token = login("mifan", "mifan");


        //访问需要权限资源
        String username = "mifan";
        int nonce = new Random().nextInt();
        long curTime =  System.currentTimeMillis();
        String digest = encryptService.tokenDigest(token + nonce + curTime);

        //登出
        MockHttpServletRequestBuilder requestBuilder1 = MockMvcRequestBuilders
                .delete("/users/token")
                .header("Username", username)
                .header("Nonce", nonce)
                .header("CurTime", curTime)
                .header("Digest", digest)
                ;

        MvcResult result = mockMvc.perform(requestBuilder1)
                .andDo(MockMvcResultHandlers.print())
                .andReturn();




        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/tags/1")
                .header("Username", username)
                .header("Nonce", nonce)
                .header("CurTime", curTime)
                .header("Digest", digest)
                ;

        MvcResult result2 = mockMvc.perform(requestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andReturn();


        Assert.assertEquals(result2.getResponse().getStatus(), 403);
    }


    @Test
    public void authorizationCache() throws Exception {
        //登录
        String token = login("mifan", "mifan");


        String username = "mifan";


        for (int i = 0; i < 10; i++) {
            int nonce = new Random().nextInt();
            long curTime =  System.currentTimeMillis();
            String digest = encryptService.tokenDigest(token + nonce + curTime);

            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .get("/tags/1")
                    .header("Username", username)
                    .header("Nonce", nonce)
                    .header("CurTime", curTime)
                    .header("Digest", digest)
                    ;

            MvcResult result = mockMvc.perform(requestBuilder)
//                .andDo(MockMvcResultHandlers.print())
                    .andReturn();

            Assert.assertEquals(result.getResponse().getStatus(), 200);
            Thread.sleep(2000);
        }
    }


    @Test
    public void userInfo() throws Exception {
        //登录
        String token = login("mifan", "mifan");

        String username = "mifan";
        int nonce = new Random().nextInt();
        long curTime =  System.currentTimeMillis();
        String digest = encryptService.tokenDigest(token + nonce + curTime);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/users/info")
                .header("Username", username)
                .header("Nonce", nonce)
                .header("CurTime", curTime)
                .header("Digest", digest)
                ;

        MvcResult result = mockMvc.perform(requestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        Assert.assertEquals(result.getResponse().getStatus(), 200);

    }


}
