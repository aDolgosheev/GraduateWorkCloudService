package ru.dolgosheev.cloudservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.dolgosheev.cloudservice.entities.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, String> {
}