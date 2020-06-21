package com.neu.kanbanproject.ppmtool.services;

import com.neu.kanbanproject.ppmtool.domain.Backlog;
import com.neu.kanbanproject.ppmtool.domain.User;
import com.neu.kanbanproject.ppmtool.exceptions.ProjectIdException;
import com.neu.kanbanproject.ppmtool.exceptions.ProjectNotFoundException;
import com.neu.kanbanproject.ppmtool.repository.BacklogRepository;
import com.neu.kanbanproject.ppmtool.repository.ProjectRepository;
import com.neu.kanbanproject.ppmtool.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.neu.kanbanproject.ppmtool.domain.Project;
import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BacklogRepository backlogRepository;

    public Project saveOrUpdateProject(Project project, String username){
        try{

            User user = userRepository.findByUsername(username);
            project.setUser(user);
            project.setProjectLeader(user.getUsername());
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());

            if(project.getId()==null){
                Backlog backlog = new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            }

            if(project.getId()!=null){
                project.setBacklog(backlogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
            }

            return projectRepository.save(project);

        }catch (Exception e){
            throw new ProjectIdException("Project ID '"+project.getProjectIdentifier().toUpperCase()+"' already exists");
        }

    }


    public Project findProjectByIdentifier(String projectId, String  username){

        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if(project == null) {
            throw new ProjectIdException("Project ID '" + projectId + "' does not exist");
        }
        if(!project.getProjectLeader().equals(username)) {
            throw new ProjectNotFoundException("Project not found in your account!");
        }

        return project;
    }

    public Iterable<Project> findAllProjects(String username){
        return projectRepository.findAllByProjectLeader(username);
    }


    public void deleteProjectByIdentifier(String projectid, String username){


        projectRepository.delete(findProjectByIdentifier(projectid, username));
    }
}