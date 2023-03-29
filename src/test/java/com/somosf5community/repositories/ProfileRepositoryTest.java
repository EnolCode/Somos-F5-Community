
package com.somosf5community.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.somosf5community.models.Profile;

import jakarta.persistence.EntityManager;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProfileRepositoryTest {

    @Autowired
    ProfileRepository repository;

    @Autowired
    EntityManager entityManager;

    @Test
    public void findById() {
        Profile profile = repository.findById(1L).orElseThrow();
        assertThat(profile.getId()).isEqualTo(1L);
        assertThat(profile.getName()).isEqualTo("Saul");
    }

    @Test
    public void saveprofile() {
        Profile profile = new Profile();
        profile.setName("profile3");
        repository.save(profile);
        assertThat(profile.getId()).isNotNull();
    }

    @Test
    public void deleteprofile() {
        Profile profile = repository.findById(1L).orElseThrow();
        repository.delete(profile);
        assertThat(repository.findById(1L)).isEmpty();
    }

    @Test
    public void updateprofile() {
        Profile profile = repository.findById(1L).orElseThrow();
        profile.setName("profile1Updated");
        repository.save(profile);
        assertThat(profile.getName()).isEqualTo("profile1Updated");
    }

}
