package com.witek.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.witek.model.Employee;
import com.witek.model.Employer;
import com.witek.model.PositionEnum;
import com.witek.service.EmployeesService;
import com.witek.service.EmployerService;

@Controller
public class EmployeesController {
	private EmployeesService employeesService;
	private List<Employee> employeesList;
	private EmployerService employersService;
	@Autowired
	public EmployeesController(EmployeesService employeesService, EmployerService employersService) {
		this.employeesService = employeesService;
		this.employersService=employersService;
	}

	@GetMapping("/employeesManager")
	public String goToEmployeessManager(Model model) {
		employeesList = employeesService.getListOfAllEmployees();
		PositionEnum [] positions = PositionEnum.values();
		model.addAttribute("positions", positions);
		model.addAttribute("employeesList", employeesList);
		return "employeesManager";
	}

	@PostMapping("/createNewEmployeeForm")
	public String createNewEmployeeForm(@ModelAttribute Employee employee,Model model) {
		employeesService.saveNewEmployee(employee);
		model.addAttribute("employee", employee);
		return "employeePage";
		}

	@GetMapping("/addNewEmployee")
	public String getNewEmployeeForm(Model model) {
		List<Employer> employersList = employersService.getListOfAllEmployers();
		model.addAttribute("employersList", employersList);
		return "createNewEmployeeForm";
	}
	@GetMapping("/showInfoAboutEmployee")
	public String showOneEmployees(Long employeeId, Model model) {
		Employee employee = employeesService.getOneById(employeeId);
		List<Employer> employersList = employersService.getListOfAllEmployers();
		model.addAttribute("employee", employee);
		model.addAttribute("employersList", employersList);
		PositionEnum [] positions = PositionEnum.values();
		model.addAttribute("positions", positions);
		return "employeePage";
	}
	
	@PostMapping("/showInfoAboutEmployee")
	public String editEmployeesForm(@ModelAttribute Employee employee, Long employerId) {
		System.out.println("EMPLOYEE : "+ employee);
		Employer selectedEmployer = employersService.getOneById(employerId);
		System.out.println("SELECTED EMPLOYER : " + selectedEmployer);
		employee.setEmployer(selectedEmployer);
		selectedEmployer.getListOfEmployees().add(employee);
		employersService.editEmployer(selectedEmployer);
		employeesService.editEmployee(employee);
		return "employeePage";
	}

	@PostMapping("/removeEmployee/{id}")
	public String removeEmployees(@PathVariable Long id, Model model) {
		System.out.println(id);
		employeesService.removeEmployeeById(id);
		employeesList = employeesService.getListOfAllEmployees();

		model.addAttribute("employeesList", employeesList);
		return "employeesManager";
	}
	
	@PostMapping("/findEmployee")
	public String findEmployee(String searchBy, String searchKey, Model model) {
	System.out.println("Searching : "+ searchBy + " " + searchKey);	
	List <Employee> list = new ArrayList<Employee> ();
	switch(searchBy) {
	case "surname":
	list = employeesService.findEmployeeBySurname(searchKey);	
	break;
	case "country":
	list=	employeesService.findEmployeeByCountry(searchKey);
	break;
	case "city":
	list = employeesService.findEmployeeByCity(searchKey);
	break;
	case "employer":
	list = employeesService.findEmployeeByEmployer(searchKey);
	break;
	case "position":
	list = employeesService.findEmployeeByPosition(searchKey);
	break;
		}
	model.addAttribute("employeesList", list);
	
	return "employeesManager";
	}
}