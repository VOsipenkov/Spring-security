package ru.home.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.home.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
