package com.somosf5community.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.somosf5community.exception.PostNotFoundException;
import com.somosf5community.models.Post;
import com.somosf5community.repositories.PostRepository;



@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @InjectMocks
    PostService service;

    @Mock
    PostRepository repository;


    private Post post;
    private Post post2;



@BeforeEach
public void init() {
    post = new Post();
    post.setId(1L);
    post.setTitle("post1");
    post2 = new Post();
    post2.setId(2L);
    post2.setTitle("post2");
}

    @Test
    public void findById_ShouldReturnPost_WhenPostExists() {
        when(repository.findById(1L)).thenReturn(Optional.of(post));
        Post currentPost = service.findById(1L);

        assertThat(currentPost.getId()).isEqualTo(1L);
        assertThat(currentPost.getTitle()).isEqualTo("post1");
       }

    @Test
       public void findById_ShouldThrowException_WhenpostDoesNotExist() {
           when(repository.findById(1L)).thenReturn(Optional.empty());
    
           assertThrows(PostNotFoundException.class, () -> service.findById(1L));
       }

    @Test 
       public void findAll_shouldReturnAllPosts() {
              when(repository.findAll()).thenReturn(List.of(post, post2));
              List<Post> posts = service.findAll();
     
              assertThat(posts).hasSize(2);
              assertThat(posts.get(0).getId()).isEqualTo(1L);
              assertThat(posts.get(1).getId()).isEqualTo(2L);
              assertThat(posts.get(0).getTitle()).isEqualTo("post1");
              assertThat(posts.get(1).getTitle()).isEqualTo("post2");
       }

    @Test
       public void save_shouldSavePost() {
           when(repository.save(post)).thenReturn(post);
           Post savedPost = service.save(post);
    
           assertThat(savedPost.getId()).isEqualTo(1L);
           assertThat(savedPost.getTitle()).isEqualTo("post1");
       }

    @Test
        public void deleteById_shouldDeletePost() {
              when(repository.findById(1L)).thenReturn(Optional.of(post));
              service.deleteById(1L);
        }

    @Test 
        public void deleteById_shouldThrowException_WhenPostDoesNotExist() {
              when(repository.findById(1L)).thenReturn(Optional.empty());
              assertThrows(PostNotFoundException.class, () -> service.deleteById(1L));
        }

    @Test
        public void updatePost_shouldUpdatePost() {
                when(repository.findById(1L)).thenReturn(Optional.of(post));
                when(repository.save(post)).thenReturn(post);
                Post updatedPost = service.updatePost(1L, post);
        
                assertThat(updatedPost.getId()).isEqualTo(1L);
                assertThat(updatedPost.getTitle()).isEqualTo("post1");
            }

    @Test       
        public void updatePost_shouldThrowException_WhenPostDoesNotExist() {
                when(repository.findById(1L)).thenReturn(Optional.empty());
        
                assertThrows(PostNotFoundException.class, () -> service.updatePost(1L, post));
            }

}
