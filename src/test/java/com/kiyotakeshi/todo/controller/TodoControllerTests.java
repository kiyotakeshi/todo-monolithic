package com.kiyotakeshi.todo.controller;

import com.google.gson.Gson;
import com.kiyotakeshi.todo.entity.Category;
import com.kiyotakeshi.todo.entity.Progress;
import com.kiyotakeshi.todo.entity.Todo;
import com.kiyotakeshi.todo.service.TodoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
class TodoControllerTests {

    private final String BASE_PATH = "/api/todo/";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private RestDocumentationContextProvider restDocumentationContextProvider;

    // TODO: mock object (@MockBean) に変える
    @Autowired
    private TodoService service;

    /**
     * Todo convert json object
     *
     * @param todo
     * @return
     */
    private String convertJson(Todo todo) {
        var gson = new Gson();
        return gson.toJson(todo);
    }

    private String categoryFieldValue() {
        return Arrays.stream(Category.values()).map(value -> String.format("'%s'", value)).collect(Collectors.joining(", "));
    }

    private String progressFieldValue() {
        return Arrays.stream(Progress.values()).map(value -> String.format("'%s'", value)).collect(Collectors.joining(", "));
    }

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(this.restDocumentationContextProvider).uris().withScheme("http")
                        // .withHost("example.com")
                        .withPort(8081))
                .build();
    }

    @Test
    void shouldReturnTodoList() throws Exception {

        this.mockMvc.perform(get(BASE_PATH) //
                .accept(MediaType.APPLICATION_JSON)) //
                .andDo(print()) //
                .andExpect(status().isOk()) //
                .andDo(document("getTodoList", // target/generated-snippets/getTodoList
                        responseFields(fieldWithPath("[].id").description("unique todo id. auto-generated in database layer"), //
                                (fieldWithPath("[].activityName").description("activity name (not null)")), //
                                (fieldWithPath("[].progress").description("you can set following progress: " + progressFieldValue() + ".\n initial value is " + Progress.Open +  " (not null)")), //
                                (fieldWithPath("[].category").description("you can set following category: " + categoryFieldValue() + " (nullable)")),
                                (fieldWithPath("[].label").description("label (nullable)")).optional() //
                        )));
    }

    @Test
    void shouldReturnTodo() throws Exception {

        this.mockMvc.perform(get(BASE_PATH + "1000") //
                .accept(MediaType.APPLICATION_JSON)) //
                .andDo(print()) //
                .andExpect(status().isOk()) //
                .andExpect(content().json(
                        "{\"id\":1000,\"activityName\":\"go to supermarket\",\"progress\":\"Open\",\"category\":\"Housework\",\"label\":\"my-label\"}"))
                .andDo(document("getTodo", //
                        responseFields(fieldWithPath("id").description("unique todo id. auto-generated in database layer"), //
                                (fieldWithPath("activityName").description("activity name (not null)")), //
                                (fieldWithPath("progress").description("you can set following progress: " + progressFieldValue() + ".\n initial value is " + Progress.Open +  " (not null)")), //
                                (fieldWithPath("category").description("you can set following category: " + categoryFieldValue() + " (nullable)")),
                                (fieldWithPath("label").description("label (nullable)")).optional() //
                        )));
    }

    @Test
    void shouldReturnNotFound() throws Exception {

        this.mockMvc.perform(get(BASE_PATH + "10000") //
                .accept(MediaType.APPLICATION_JSON)) //
                .andDo(print()) //
                .andExpect(status().isNotFound()) //
                .andDo(document("notGetTodo"));
    }

    @Test
    @DirtiesContext
    void shouldCreateTodo() throws Exception {

        var todo = new Todo("test", Progress.Open, null, "test");
        String json = convertJson(todo);

        this.mockMvc.perform(post(BASE_PATH) //
                .contentType(MediaType.APPLICATION_JSON).content(json)).andDo(print()) //
                .andExpect(status().isCreated()) //
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(header().string("Location", BASE_PATH + 1003))
                .andExpect(content().json(
                        "{\"id\":1003,\"activityName\":\"test\",\"progress\":\"Open\",\"category\":null,\"label\":\"test\"}"))
                .andDo(document("postTodo"));
    }

    @Test
    @DisplayName("Todo を作成時のステータスは常に Open であることを確認")
    @DirtiesContext
    void shouldFirstCreatedTodoProgressIsOpen() throws Exception {

        var todo = new Todo("test", Progress.Done, null, "test");
        String json = convertJson(todo);

        this.mockMvc.perform(post(BASE_PATH) //
                .contentType(MediaType.APPLICATION_JSON).content(json)).andDo(print()) //
                .andExpect(status().isCreated()) //
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(header().string("Location", BASE_PATH + 1003))
                .andExpect(content().json(
                        "{\"id\":1003,\"activityName\":\"test\",\"progress\":\"Open\",\"category\":null,\"label\":\"test\"}"))
                .andDo(document("postTodoProgressDone"));
    }

    @Test
    @DirtiesContext
        // after this test is run, spring would automatically reset the date
    void shouldUpdateTodo() throws Exception {

        Todo todo = this.service.findById(1001L);
        todo.setActivityName("update");
        todo.setProgress(Progress.Doing);
        todo.setCategory(Category.Job);
        String json = convertJson(todo);

        this.mockMvc.perform(put(BASE_PATH + "1001") //
                .contentType(MediaType.APPLICATION_JSON).content(json)).andDo(print()).andExpect(status().isOk()) //
                .andExpect(content().json(
                        "{\"id\":1001,\"activityName\":\"update\",\"progress\":\"Doing\",\"category\":\"Job\",\"label\":null}"))
                .andDo(document("putTodo"));
    }

    @Test
    @DirtiesContext
    void shouldDeleteTodo() throws Exception {
        this.mockMvc.perform(delete(BASE_PATH + "1000")) //
                // ref https://developer.mozilla.org/ja/docs/Web/HTTP/Methods/DELETE
                .andExpect(status().isNoContent()) //
                .andDo(document("deleteTodo"));
    }

}