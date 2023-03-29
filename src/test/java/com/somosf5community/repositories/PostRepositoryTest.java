package com.somosf5community.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.somosf5community.models.Post;

import jakarta.persistence.EntityManager;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PostRepositoryTest {


    @Autowired
    PostRepository repository;

    @Autowired
    EntityManager entityManager;

    @Test
    public void findById(){
        Post post =  repository.findById(1L).orElseThrow();
       assertThat(post.getId()).isEqualTo(1L);
       assertThat(post.getTitle()).isEqualTo("Titulo de prueba");
    }

     
    @Test
    public void savepost(){
        Post post = new Post();
        post.setTitle("post3");
        repository.save(post);
        assertThat(post.getId()).isNotNull();
    }

    @Test
    public void deletepost(){
        Post post = repository.findById(1L).orElseThrow();
        repository.delete(post);
        assertThat(repository.findById(1L)).isEmpty();
    }

    @Test
    public void updatepost(){
        Post post = repository.findById(1L).orElseThrow();
        post.setTitle("post1Updated");
        repository.save(post);
        assertThat(post.getTitle()).isEqualTo("post1Updated");
    }

}

