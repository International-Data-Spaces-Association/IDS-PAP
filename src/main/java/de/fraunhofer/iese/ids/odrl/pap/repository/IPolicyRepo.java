package de.fraunhofer.iese.ids.odrl.pap.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.fraunhofer.iese.ids.odrl.pap.entity.IdAndDescription;
import de.fraunhofer.iese.ids.odrl.pap.entity.Policy;


@Repository
public interface IPolicyRepo extends JpaRepository<Policy, Long> {
	//List<IdAndDescription> findAllIDsAndDescriptions();
}

