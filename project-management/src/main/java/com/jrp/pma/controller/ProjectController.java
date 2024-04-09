package com.jrp.pma.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jrp.pma.dao.EmployeeRepository;
import com.jrp.pma.dao.ProjectRepository;
import com.jrp.pma.entities.Employee;
import com.jrp.pma.entities.Project;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/projects")
public class ProjectController {
	
	@Autowired
	ProjectRepository proRepo;
	
	@Autowired
	EmployeeRepository empRepo;
	
	@GetMapping
	public String getProjects(Model model) {
		List<Project> projects = proRepo.findAll();
		model.addAttribute("projects",projects);
		return "projects/list-projects";
	}

	@GetMapping("/new")
	public String displayProjectForm(Model model) {
		Project aProject = new Project();
		model.addAttribute("project", aProject);
		List<Employee> employeeList = empRepo.findAll();
		model.addAttribute("allEmployees", employeeList);
		return "projects/new-project";
	}
	
	@PostMapping("/save")
	public String createProject(Project project,@RequestParam("employees") List<Employee> employeesReq, Model model) {
		 
		System.out.println("Project "+project);
		//handle the saving to the database
		proRepo.save(project);
		
		for(Employee employee : employeesReq) {
			employee.setProject(project);
			empRepo.save(employee);
		}
		// to prevent duplicate submission
		return "redirect:/projects/new";
	}
	
}
