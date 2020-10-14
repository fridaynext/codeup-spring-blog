package com.codeup.blog;

import com.codeup.blog.models.Ad;
import com.codeup.blog.models.User;
import com.codeup.blog.repositories.AdRepository;
import com.codeup.blog.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.HttpSession;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BlogApplication.class)
@AutoConfigureMockMvc
public class AdsIntegrationTests {

    private User testUser;
    private HttpSession httpSession;

    @Autowired
    private MockMvc mvc;

    @Autowired
    UserRepository userDao;

    @Autowired
    AdRepository adsDao;

    // @Autowired
    // private PasswordEncoder passwordEncoder;

    @Before
    public void setup() throws Exception {

        // Just like in Junit TDD, we can prep some items for testing by using the @Before annotation on a setup() method

        try {
            testUser = userDao.findByUsername("testUser");
        } catch (Exception e) {
            // probably didn't find a user, so let's create one
            User newUser = new User();
            newUser.setUsername("testUser");
            // newUser.setPassword(passwordEncoder.encode("pass"));
            newUser.setPassword("codeup2020"); //TODO: Change this once we get through Spring Security
            newUser.setEmail("testUser@codeup.com");
            testUser = userDao.save(newUser);
        }

        // TODO: Need a login page to do this test - which we'll set up in Spring Security module
        // Throws a Post request to /login and expect a redirection to the Ads index page after being logged in
        // httpSession = this.mvc.perform(post("/login").with(csrf())
        //         .param("username", "testUser")
        //         .param("password", "codeup2020"))
        //         .andExpect(status().is(HttpStatus.FOUND.value()))
        //         .andExpect(redirectedUrl("/ads"))
        //         .andReturn()
        //         .getRequest()
        //         .getSession();
    }

    // ********************* Sanity Tests ********************** //
    @Test
    public void contextLoads() {
        // Sanity Test, just to make sure the MVC bean is working
        assertNotNull(mvc);
    }

    @Test
    public void testIfUserSessionIsActive() throws Exception {
        // It makes sure the returned session is not null
        assertNotNull(httpSession);
    }

    // ******************** CRUD Testing *********************** //
    // We now need to check to make sure that posting ads/blog posts works, as well as editing/deleting/etc
    @Test
    public void testCreateAd() throws Exception {
        // Makes a Post request to /ads/create and expect a redirection to the Ad
        this.mvc.perform(
                post("/ads/create").with(csrf())
                        .session((MockHttpSession) httpSession)
                        // Add all the required parameters to your request like this
                        .param("title", "test")
                        .param("description", "for sale"))
                .andExpect(status().is3xxRedirection());
    }

    // ******************* Test displaying an Ad, and showing all ads *********** //
    @Test
    public void testShowAd() throws Exception {

        Ad existingAd = adsDao.findAll().get(0);

        // Makes a Get request to /ads/{id} and expect a redirection to the Ad show page
        this.mvc.perform(get("/ads/" + existingAd.getId()))
                .andExpect(status().isOk())
                // Test the dynamic content of the page
                .andExpect(content().string(containsString(existingAd.getDescription())));
    }

    @Test
    public void testAdsIndex() throws Exception {
        Ad existingAd = adsDao.findAll().get(0);

        // Makes a Get request to /ads and verifies that we get some of the static text of the ads/index.html template and at least the title from the first Ad is present in the template.
        this.mvc.perform(get("/ads"))
                .andExpect(status().isOk())
                // Test the static content of the page
                .andExpect(content().string(containsString("Latest ads")))
                // Test the dynamic content of the page
                .andExpect(content().string(containsString(existingAd.getTitle())));
    }

    // *************************** Test Editing an Ad **************************** //
    @Test
    public void testEditAd() throws Exception {
        // Gets the first Ad for tests purposes
        Ad existingAd = adsDao.findAll().get(0);

        // Makes a Post request to /ads/{id}/edit and expect a redirection to the Ad show page
        this.mvc.perform(
                post("/ads/" + existingAd.getId() + "/edit").with(csrf())
                        .session((MockHttpSession) httpSession)
                        .param("title", "edited title")
                        .param("description", "edited description"))
                .andExpect(status().is3xxRedirection());

        // Makes a GET request to /ads/{id} and expect a redirection to the Ad show page
        this.mvc.perform(get("/ads/" + existingAd.getId()))
                .andExpect(status().isOk())
                // Test the dynamic content of the page
                .andExpect(content().string(containsString("edited title")))
                .andExpect(content().string(containsString("edited description")));
    }

    // *********************************** TEST DELETING AN AD ************************** //
    @Test
    public void testDeleteAd() throws Exception {
        // Creates a test Ad to be deleted
        this.mvc.perform(
                post("/ads/create").with(csrf())
                        .session((MockHttpSession) httpSession)
                        .param("title", "ad to be deleted")
                        .param("description", "won't last long"))
                .andExpect(status().is3xxRedirection());

        // Get the recent Ad that matches the title
        Ad existingAd = adsDao.findByTitle("ad to be deleted");

        // Makes a Post request to /ads/{id}/delete and expect a redirection to the Ads index
        this.mvc.perform(
                post("/ads/" + existingAd.getId() + "/delete").with(csrf())
                        .session((MockHttpSession) httpSession)
                        .param("id", String.valueOf(existingAd.getId())))
                .andExpect(status().is3xxRedirection());
    }
}
