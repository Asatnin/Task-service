package com.zavalin.task.service;

import com.zavalin.task.model.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface TaskRepository extends CrudRepository<Task, Long> {
    List<Task> findTasksByUsername(String username);
}
