package com.fengdu.mytask.service;

import com.fengdu.mytask.domain.Person;
import com.fengdu.mytask.repository.PersonRepository;
import org.flowable.task.api.Task;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class MyService {

    private RuntimeService runtimeService;
    private TaskService taskService;

    private PersonRepository personRepository;

    public MyService(RuntimeService runtimeService, TaskService taskService, PersonRepository personRepository) {
        this.runtimeService = runtimeService;
        this.taskService = taskService;
        this.personRepository = personRepository;
    }

    public void startProcess(String assignee) {
        Person person = personRepository.findByUsername(assignee);

        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("person", person);
        runtimeService.startProcessInstanceByKey("oneTaskProcess", variables);
    }

    public List<Task> getTasks(String assignee) {
        return taskService.createTaskQuery().taskAssignee(assignee).list();
    }

    public void createDemoUsers() {
        if (personRepository.findAll().size() == 0) {
            personRepository.save(new Person("jbarrez", "Joram", "Barrez", new Date()));
            personRepository.save(new Person("trademakers", "Tijs", "Rademakers", new Date()));
        }
    }

}
