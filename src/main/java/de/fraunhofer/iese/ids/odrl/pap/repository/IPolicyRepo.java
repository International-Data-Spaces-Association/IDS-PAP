package de.fraunhofer.iese.ids.odrl.pap.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import de.fraunhofer.iese.ids.odrl.pap.entity.ShortPolicy;
import de.fraunhofer.iese.ids.odrl.pap.entity.Policy;


@Repository
public interface IPolicyRepo extends JpaRepository<Policy, Long> {
	// TODO:  
	//@Query(value = "SELECT ID, POLICYID, DESCRIPTION, POLICY_TYPE  FROM TBL_POLICY", nativeQuery = true)
	//List<Object[]> findAllShort();
}

