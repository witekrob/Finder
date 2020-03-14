
package com.witek.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.witek.dao.EmployeesDao;
import com.witek.model.Employee;
import com.witek.model.PositionEnum;
import com.witek.repo.EmployeesRepo;

@Service

public class EmployeesService {

	private EmployeesDao employeeDao;
	private EmployeesRepo jobsRepo;
	private List<Employee> lista;

	@Autowired
	public EmployeesService(EmployeesDao employeeDao, EmployeesRepo jobsRepo) {
		this.employeeDao = employeeDao;
		this.jobsRepo = jobsRepo;
	}

	public Employee getOneById(Long id) {
		return employeeDao.getOne(id);
	}

	public List<Employee> getListOfAllEmployees() {
		// lista = jobsRepo.generateEmployeesList();
		// employeeDao.saveAll(lista);
		lista = employeeDao.findAll();

		return lista;
	}

	public void saveNewEmployee(Employee employee) {
		employeeDao.save(employee);
	}

	public void editEmployee(Employee employee) {
		Employee fromDb = employeeDao.findById(employee.getEmployeeId()).get();
		if (employee.getName() == "") {
			employee.setName(fromDb.getName());
		}
		if (employee.getSurname() == "") {
			employee.setSurname(fromDb.getSurname());
		}
		if (employee.getCity() == "") {
			employee.setCity(fromDb.getCity());
		}
		if (employee.getCountry() == "") {
			employee.setCountry(fromDb.getCountry());
		}
		if (employee.getDateOfBirth() == null) {
			employee.setDateOfBirth(fromDb.getDateOfBirth());
		}
		employeeDao.save(employee);

	}

	public void removeEmployeeById(Long id) {
		employeeDao.deleteById(id);
	}
	
	public List<Employee> findEmployeeBySurname(String surname) {
		List<Employee> list = employeeDao.findBySurname(surname);
		return list;
	}

	public List<Employee> findEmployeeByCountry(String country) {
		List<Employee> list = employeeDao.findByCountry(country);
		return list;
	}
	public List<Employee> findEmployeeByCity(String city) {
		List<Employee> list = employeeDao.findByCity(city);
		return list;
	}

	public List<Employee> findEmployeeByEmployer(String employer) {
		List<Employee> list =employeeDao.findByEmployerName(employer);
		return list;
	}

	public List<Employee> findEmployeeByPosition(String position) {
		System.out.println("lookam za : " + position);
		List<Employee> list =employeeDao.findByPosition(position);
		System.out.println(list);
		return list;
	}
}
