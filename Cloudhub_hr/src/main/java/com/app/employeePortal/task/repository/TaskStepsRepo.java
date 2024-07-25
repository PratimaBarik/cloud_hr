package com.app.employeePortal.task.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.app.employeePortal.task.entity.TaskSteps;

public interface TaskStepsRepo extends JpaRepository<TaskSteps,String> {

	List<TaskSteps> findByTaskId(String taskId);
   
}
