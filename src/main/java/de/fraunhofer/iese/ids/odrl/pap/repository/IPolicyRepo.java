package de.fraunhofer.iese.ids.odrl.pap.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IPolicyRepo extends JpaRepository<Policy, Long> {
    @Override
    List<Policy> findAll();
    
	List<ShortPolicy> findBy();
	
    void deleteById(Long id);
}

