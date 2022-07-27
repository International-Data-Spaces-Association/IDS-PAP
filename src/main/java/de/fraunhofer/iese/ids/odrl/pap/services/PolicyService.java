package de.fraunhofer.iese.ids.odrl.pap.services;

import java.util.List;
import java.util.Optional;

import de.fraunhofer.iese.ids.odrl.pap.repository.Policy;
import de.fraunhofer.iese.ids.odrl.pap.repository.ShortPolicy;

public interface  PolicyService {

    List<Policy> fetchAllPolicies();
    
	List<ShortPolicy> shortPolicyFindById();
	
    void deletePolicyById(Long id);
        
    Optional<Policy> findPolicyById(long id);

	Policy savePolicy(String name, String policyType, String queryOrigin, String comment, String fieldValues);

	Policy savePolicy(Policy policy);

	void deletePolicy(Policy p);
}
