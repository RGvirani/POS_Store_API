package com.POS_Store.App.Repo;

import com.POS_Store.App.models.EmployeeM;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepo extends JpaRepository<EmployeeM, Long> {

    List<EmployeeM> findByfirstName(String firstname);

    List<EmployeeM> findBylastName(String lastname);

    List<EmployeeM> findByemail(String firstname);

    List<EmployeeM> findByposition(String firstname);

    List<EmployeeM> findBydepartment(String firstname);
}
