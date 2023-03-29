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

import com.somosf5community.exception.ProfileNotFoundException;
import com.somosf5community.models.Profile;
import com.somosf5community.repositories.ProfileRepository;



@ExtendWith(MockitoExtension.class)
public class ProfileServiceTest {

    @InjectMocks
    ProfileService service;

    @Mock
    ProfileRepository repository;


    private Profile Profile;
    private Profile Profile2;



@BeforeEach
public void init() {
    Profile = new Profile(1L,"Enol","Igareta","Gijon", "Github", "Linkedin",null);
    Profile2 = new Profile(2L,"Diego","Dieguin","Gijon", "Github", "Linkedin",null);
}

    @Test
    public void findById_ShouldReturnProfile_WhenProfileExists() {
        when(repository.findById(1L)).thenReturn(Optional.of(Profile));
        Profile currentProfile = service.findById(1L);

        assertThat(currentProfile.getId()).isEqualTo(1L);
        assertThat(currentProfile.getName()).isEqualTo("Enol");
       }

    @Test
       public void findById_ShouldThrowException_WhenProfileDoesNotExist() {
           when(repository.findById(1L)).thenReturn(Optional.empty());
    
           assertThrows(ProfileNotFoundException.class, () -> service.findById(1L));
       }

    @Test 
       public void findAll_shouldReturnAllProfiles() {
              when(repository.findAll()).thenReturn(List.of(Profile, Profile2));
              List<Profile> Profiles = service.findAll();
     
              assertThat(Profiles).hasSize(2);
              assertThat(Profiles.get(0).getId()).isEqualTo(1L);
              assertThat(Profiles.get(1).getId()).isEqualTo(2L);
              assertThat(Profiles.get(0).getName()).isEqualTo("Enol");
              assertThat(Profiles.get(1).getName()).isEqualTo("Diego");
       }

    @Test
       public void save_shouldSaveProfile() {
           when(repository.save(Profile)).thenReturn(Profile);
           Profile savedProfile = service.save(Profile);
    
           assertThat(savedProfile.getId()).isEqualTo(1L);
           assertThat(savedProfile.getName()).isEqualTo("Enol");
       }

    @Test
        public void deleteById_shouldDeleteProfile() {
              when(repository.findById(1L)).thenReturn(Optional.of(Profile));
              service.deleteById(1L);
        }

    @Test 
        public void deleteById_shouldThrowException_WhenProfileDoesNotExist() {
              when(repository.findById(1L)).thenReturn(Optional.empty());
              assertThrows(ProfileNotFoundException.class, () -> service.deleteById(1L));
        }

    @Test
        public void updateProfile_shouldUpdateProfile() {
                when(repository.findById(1L)).thenReturn(Optional.of(Profile));
                when(repository.save(Profile)).thenReturn(Profile);
                Profile updatedProfile = service.updateProfile(1L, Profile);
        
                assertThat(updatedProfile.getId()).isEqualTo(1L);
                assertThat(updatedProfile.getName()).isEqualTo("Enol");
            }

    @Test       
        public void updateProfile_shouldThrowException_WhenProfileDoesNotExist() {
                when(repository.findById(1L)).thenReturn(Optional.empty());
        
                assertThrows(ProfileNotFoundException.class, () -> service.updateProfile(1L, Profile));
            }

}


