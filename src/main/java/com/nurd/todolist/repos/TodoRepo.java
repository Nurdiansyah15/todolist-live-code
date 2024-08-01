package com.nurd.todolist.repos;

import com.nurd.todolist.models.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TodoRepo extends JpaRepository<Todo, UUID>, JpaSpecificationExecutor<Todo> {
    @Query(value = "SELECT * FROM todos WHERE user_id = :id", nativeQuery = true)
    Page<Todo> findAllByUser(UUID id, Pageable pageable);
}
