package com.neu.kanbanproject.ppmtool.services;


import com.neu.kanbanproject.ppmtool.domain.Backlog;
import com.neu.kanbanproject.ppmtool.domain.Project;
import com.neu.kanbanproject.ppmtool.domain.ProjectTask;
import com.neu.kanbanproject.ppmtool.exceptions.ProjectNotFoundException;
import com.neu.kanbanproject.ppmtool.repository.BacklogRepository;
import com.neu.kanbanproject.ppmtool.repository.ProjectTaskRepository;
import com.neu.kanbanproject.ppmtool.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService {


    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    @Autowired
    private ProjectRepository projectRepository;


    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask){

        try {

            Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);

            projectTask.setBacklog(backlog);

            Integer BacklogSequence = backlog.getPTSequence();

            BacklogSequence++;

            backlog.setPTSequence(BacklogSequence);


            projectTask.setProjectSequence(backlog.getProjectIdentifier()+"-"+BacklogSequence);
            projectTask.setProjectIdentifier(projectIdentifier);

            if(projectTask.getStatus()==""|| projectTask.getStatus()==null){
                projectTask.setStatus("TO_DO");
            }

            if(projectTask.getPriority()==null){
                projectTask.setPriority(3);
            }

            return projectTaskRepository.save(projectTask);
        }catch (Exception e){
            throw new ProjectNotFoundException("Project not Found");
        }

    }

    public Iterable<ProjectTask> findBacklogById(String id){

        Project project = projectRepository.findByProjectIdentifier(id);

        if(project==null){
            throw new ProjectNotFoundException("Project with ID: '"+id+"' does not exist");
        }

        return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
    }


    public ProjectTask findPTByProjectSequence(String backlog_id, String pt_id) {

        Backlog backlog = backlogRepository.findByProjectIdentifier(backlog_id);
        if(backlog == null) {
            throw new ProjectNotFoundException("Project with backlog ID:" + backlog_id  +"doesnt exist");
        }
            ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);

        if(projectTask == null)
        {
            throw new ProjectNotFoundException("Project task" +pt_id+"not found!");
        }
        if(!projectTask.getProjectIdentifier().equals(backlog_id)) {
            throw new ProjectNotFoundException("Project task" + pt_id + "does not exist in project!" + backlog_id);
        }
        return projectTask;
    }


    public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String backlog_id, String pt_id)
    {
     ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id);
     projectTask = updatedTask;
     return  projectTaskRepository.save(projectTask);

    }

    public void deletePTByProjectSequence(String backlog_id, String pt_id)
    {
        ProjectTask projectTask =  findPTByProjectSequence(backlog_id, pt_id);

        projectTaskRepository.delete(projectTask);

    }




}