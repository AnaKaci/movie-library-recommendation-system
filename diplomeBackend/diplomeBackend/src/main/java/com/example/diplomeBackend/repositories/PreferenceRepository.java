package com.example.diplomeBackend.repositories;
import com.example.diplomeBackend.models.Preference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreferenceRepository extends JpaRepository<Preference, Long> {

    @Query("SELECT p FROM Preference p WHERE p.user.userId = :userId AND p.preferenceType = :preferenceTypes")
    List<Preference> findByUserIdAndPreferenceTypes(Long userId, String preferenceTypes);
}