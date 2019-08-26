package com.github.terentich.rssgenerator.web;

import com.github.terentich.rssgenerator.service.RssGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(RssController.class)
public class RssControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RssGenerator rssGenerator;

    private static final String INPUT_URL = "/mosfm.com/programs/7";

    @Test
    public void findAll_ShouldAddTodoEntriesToModelAndRenderTodoListView() throws Exception {
        String content = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<feed xmlns=\"http://www.w3.org/2005/Atom\" xmlns:dc=\"http://purl.org/dc/elements/1.1/\">\n" +
                "  <title>Москва FM. Управление Делами</title>\n" +
                "  <link rel=\"alternate\" href=\"http://mosfm.com/programs/7\" />\n" +
                "  <entry>\n" +
                "    <title>\"Управление делами\": Андриеш Гандрабур</title>\n" +
                "    <author>\n" +
                "      <name />\n" +
                "    </author>\n" +
                "    <id>/audios/137896?type=programs</id>\n" +
                "    <updated>2019-08-19T13:24:00Z</updated>\n" +
                "    <published>2019-08-19T13:24:00Z</published>\n" +
                "    <content>https://b1.m24.ru/c/1254616.190x138.jpg</content>\n" +
                "    <dc:date>2019-08-19T13:24:00Z</dc:date>\n" +
                "  </entry>\n";

        given(this.rssGenerator.createRss("http:/" + INPUT_URL))
                .willReturn(content);

        mockMvc.perform(get("/rss" + INPUT_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_ATOM_XML))
                .andExpect(content().encoding(StandardCharsets.UTF_8.toString()))
                .andExpect(content().string(content));
    }
}
