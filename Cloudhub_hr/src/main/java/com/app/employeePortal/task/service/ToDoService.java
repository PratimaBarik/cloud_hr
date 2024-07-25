package com.app.employeePortal.task.service;

import java.util.List;
import java.util.Map;

import com.app.employeePortal.employee.mapper.NotesMapper;
import com.app.employeePortal.task.mapper.ToDoDetailsMapper;
import com.app.employeePortal.task.mapper.ToDoMapper;
import com.app.employeePortal.task.mapper.UpcomingEventMapper;
import com.app.employeePortal.task.mapper.UserPlannerMapper;

public interface ToDoService {

	List<ToDoMapper> getToDoByUserId(String userId, String startDate,String endDate);

	ToDoMapper updateToDoByCallId(String eventCallTaskId, ToDoDetailsMapper toDoDetailsMapper);

	ToDoMapper updateToDoByTaskId(String eventCallTaskId, ToDoDetailsMapper toDoDetailsMapper);

	ToDoMapper updateToDoByEventId(String eventCallTaskId, ToDoDetailsMapper toDoDetailsMapper);

	List<UpcomingEventMapper> upcomingBirthdayAndAniversary();

	Map<String,Long> getToDoCountByUserId(String userId, String startDate, String endDate);

	ToDoMapper getLastActivityByUserId(String id);

	List<NotesMapper> getActivityNotesList(String type, String id);

	String saveActivityNotes(NotesMapper notesMapper, String type, String id);

	String deleteActivity(String activityType, String activityId, String creatorType, String creatorId);

	List<UserPlannerMapper> getPlannerDataByUserId(String userId, String startDate, String endDate);
}
