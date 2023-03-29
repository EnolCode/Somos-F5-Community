package com.somosf5community.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.somosf5community.services.PostService;
import com.somosf5community.models.Post;


@WebMvcTest(controllers = PostController.class)
@AutoConfigureMockMvc(addFilters = false)
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService service;

    private Post Post;

    @BeforeEach
    void setUp() {
        Post = new Post( 1L, "post1" , null , "description" , null);
    }

    @Test
    void getAllPosts_shouldReturnAllPosts() throws Exception {
        List<Post> Posts = new ArrayList<>();
        Posts.add(Post);

        when(service.findAll()).thenReturn(Posts);
        MockHttpServletResponse response = mockMvc.perform(get("/api/posts")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        assertThat(response.getContentAsString()).contains("post1");
        assertThat(response.getStatus()).isEqualTo(200);
    }

    @Test
    void getPostById_shouldReturnPost() throws Exception {
        when(service.findById(1L)).thenReturn(Post);
        MockHttpServletResponse response = mockMvc.perform(get("/api/posts/1")
                                            .contentType("application/json"))
                                            .andExpect(status().isOk())
                                            .andReturn()
                                            .getResponse();

        assertThat(response.getContentAsString()).contains("post1");
        assertThat(response.getStatus()).isEqualTo(200);
    }

    @Test
    public void testCreatePost() throws Exception {
        when(service.save(any(Post.class))).thenReturn(Post);

        MockHttpServletResponse response = mockMvc.perform(post("/api/posts/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(Post)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse();

        assertThat(response.getContentAsString()).contains("post1");
        assertThat(response.getContentAsString()).contains("description");
        assertThat(response.getStatus()).isEqualTo(201);
    }

    @Test
    public void testUpdatePost() throws Exception {
        Post updatedPost = new Post(1L, "post1" , null , "postUpdate" , null);
        when(service.updatePost(any(Long.class), any(Post.class))).thenReturn(updatedPost);

        MockHttpServletResponse response = mockMvc.perform(put("/api/posts/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updatedPost)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        assertThat(response.getContentAsString()).contains("postUpdate");
        assertThat(response.getContentAsString()).contains("post1");
        assertThat(response.getStatus()).isEqualTo(200);
    }

    @Test
    public void testDeletePost() throws Exception {
        Long PostId = 1L;

        doNothing().when(service).deleteById(PostId);

        MockHttpServletResponse response = mockMvc.perform(delete("/api/posts/" + PostId))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        assertThat(response.getContentAsString()).isEqualTo("Post deleted successfully");
        assertThat(service.findById(1L)).isEqualTo(null);
    }

}
