package lol.mifan.myblog;

import lol.mifan.myblog.service.EncryptService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
import java.util.Random;

/**
 * Created by 米饭 on 2017-06-29.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class ControllerTests extends BlogServerApplicationTests {

    @Resource
    private EncryptService encryptService;


    private String token;
    private String username = "mifan";
    private int nonce;
    private long curTime;
    private String digest;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        token = super.login("mifan", "mifan");
        nonce = new Random().nextInt();
        curTime =  System.currentTimeMillis();
        digest = encryptService.tokenDigest(token + nonce + curTime);
    }


    @After
    public void clear() throws Exception {
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
    }



    @Test
    public void tag() throws Exception {


        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/tags/2")
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

    @Test
    public void tagList() throws Exception {


        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/tags")
                .param("offset", "0")
                .param("limit", "2")
                .param("query", "b")
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

    @Test
    public void tagArticles() throws Exception {


        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/tags/1/articles")
                .param("offset", "0")
                .param("limit", "100")
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

    @Test
    public void getArticleList() throws Exception {


        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/articles")
                .param("offset", "5")
                .param("limit", "10")
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

    @Test
    public void getArticle() throws Exception {


        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/articles/448")
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

    @Test
    public void getReview() throws Exception {


        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/articles/404/reviews")
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
