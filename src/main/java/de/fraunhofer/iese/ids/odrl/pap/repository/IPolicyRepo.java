package de.fraunhofer.iese.ids.odrl.pap.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import de.fraunhofer.iese.ids.odrl.pap.entity.Policy;
import de.fraunhofer.iese.ids.odrl.pap.model.ShortPolicy;

@Repository
public interface IPolicyRepo extends JpaRepository<Policy, Long> {
	List<ShortPolicy> findBy();
}

