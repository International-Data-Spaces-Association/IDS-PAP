package de.fraunhofer.iese.ids.odrl.pap.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.fraunhofer.iese.ids.odrl.pap.repository.IPolicyRepo;
import de.fraunhofer.iese.ids.odrl.pap.repository.Policy;
import de.fraunhofer.iese.ids.odrl.pap.repository.ShortPolicy;

@Service
public class PolicyServiceImpl implements PolicyService {
	@Autowired
	private IPolicyRepo policyRepo;

    public PolicyServiceImpl(IPolicyRepo policyRepo) {
        this.policyRepo = policyRepo;
    }
    
	@Override
	public List<Policy> fetchAllPolicies() {
		return policyRepo.findAll();
	}

	@Override
	public List<ShortPolicy> shortPolicyFindById() {
		return policyRepo.findBy();
	}
	
	
	@Override
	public void deletePolicyById(Long id) {
		policyRepo.deleteById(id);
	}
	
	@Override
	public void deletePolicy(Policy p) {
		policyRepo.delete(p);
	}

	@Override
	public Policy savePolicy(String name, String policyType, String queryOrigin, String comment, String fieldValues) {
		return policyRepo.save(new Policy(name, policyType, queryOrigin, comment, fieldValues));
	}
	
	@Override
	public Policy savePolicy(Policy policy) {
		return policyRepo.save(policy);
	}

	@Override
	public Optional<Policy> findPolicyById(long id) {
		return policyRepo.findById(id);
	}

}
