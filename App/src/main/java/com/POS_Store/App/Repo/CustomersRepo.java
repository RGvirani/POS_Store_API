package com.POS_Store.App.Repo;

import com.POS_Store.App.models.CustomersM;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomersRepo extends JpaRepository<CustomersM, Long> {


    List<CustomersM> findByfirstName(String firstname);

    List<CustomersM> findBylastName(String lastname);

    List<CustomersM> findByemail(String email);
}
