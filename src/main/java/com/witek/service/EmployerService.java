package com.witek.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.witek.dao.EmployerDao;
import com.witek.model.Employer;
import com.witek.model.Employee;
import com.witek.repo.EmployerRepo;

@Service
public class EmployerService {

	private EmployerDao employerDao;
	private List<Employer> lista;
	private EmployerRepo empRepo;

	@Autowired
	public EmployerService(EmployerDao employerDao, EmployerRepo empRepo) {
		this.employerDao = employerDao;
		this.empRepo = empRepo;
	}

	public Employer getOneById(Long id) {
		return employerDao.getOne(id);
	}

	public List<Employer> getListOfAllEmployers() {
		// lista = empRepo.generateEmployersList();
		// employerDao.saveAll(lista);
		lista = employerDao.findAll();
		return lista;
	}

	public void createNewEmployer(Employer employer) {
		employerDao.save(employer);

	}

	public void removeEmployerById(Long id) {
		employerDao.deleteById(id);
	}
	public void editEmployer(Employer employer) {

		Employer fromDb = employerDao.findById(employer.getEmployerId()).get();
		if (employer.getName() == "") {
			employer.setName(fromDb.getName());
		}
		if (employer.getCity() == "") {
			employer.setCity(fromDb.getCity());
		}
		List<Employee> listOfEmployees = fromDb.getListOfEmployees();
		employer.setListOfEmployees(listOfEmployees);
		employerDao.save(employer);
	}

	public void saveNewEmployer(Employer employer) {
		employerDao.save(employer);
	}
}
