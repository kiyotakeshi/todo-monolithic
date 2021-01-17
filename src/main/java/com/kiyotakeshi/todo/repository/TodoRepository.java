package com.kiyotakeshi.todo.repository;

import com.kiyotakeshi.todo.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

	List<Todo> findByCategory(String category);

}
