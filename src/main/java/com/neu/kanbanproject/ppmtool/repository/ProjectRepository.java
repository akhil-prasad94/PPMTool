package com.neu.kanbanproject.ppmtool.repository;

import com.neu.kanbanproject.ppmtool.domain.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {
    @Override
    Iterable<Project> findAll();

    Project findByProjectIdentifier(String projectId);

    @Override
    void delete(Project project);
}
