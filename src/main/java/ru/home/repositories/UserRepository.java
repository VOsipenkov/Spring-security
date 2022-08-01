package ru.home.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.home.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findFirstByUsername(String username);
}
