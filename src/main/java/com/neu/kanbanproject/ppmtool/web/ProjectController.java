package com.neu.kanbanproject.ppmtool.web;


import com.neu.kanbanproject.ppmtool.domain.Project;
import com.neu.kanbanproject.ppmtool.services.MapValidationErrorService;
import com.neu.kanbanproject.ppmtool.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/project")
@CrossOrigin
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping("")
    public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult result) {

        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidation(result);
        if(errorMap != null)
            return errorMap;
        Project p1 = projectService.saveOrUpdateProject(project);
        return new ResponseEntity<Project>(p1, HttpStatus.CREATED);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<?> findProjectById(@PathVariable String projectId) {

//        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidation(result);
//        if(errorMap != null)
//            return errorMap;

        Project p2 = projectService.findProjectById(projectId);
        return new ResponseEntity<Project>(p2, HttpStatus.OK);
    }
    @GetMapping("projects/all")
    public Iterable<Project> findAllProjects() {
        return  projectService.findAllProjects();

    }

    @DeleteMapping ("/{projectid}")
    public ResponseEntity<?> deleteProject(@PathVariable String projectid) {
        projectService.deleteprojectByIdentifier(projectid.toUpperCase());
    return new ResponseEntity<String>("project with ID "+projectid.toUpperCase()+" has been deleted",HttpStatus.OK);
    }
}
