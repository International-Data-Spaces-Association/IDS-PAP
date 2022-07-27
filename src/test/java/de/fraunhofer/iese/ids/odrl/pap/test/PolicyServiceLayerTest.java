package de.fraunhofer.iese.ids.odrl.pap.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.Assert;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import de.fraunhofer.iese.ids.odrl.pap.repository.IPolicyRepo;
import de.fraunhofer.iese.ids.odrl.pap.repository.Policy;
import de.fraunhofer.iese.ids.odrl.pap.repository.ShortPolicy;
import de.fraunhofer.iese.ids.odrl.pap.services.PolicyService;
import de.fraunhofer.iese.ids.odrl.pap.services.PolicyServiceImpl;

@ExtendWith(MockitoExtension.class)
public class PolicyServiceLayerTest {
	
	class ShortPolicyImpl implements ShortPolicy {
		 Long id;
		 String name;
		 String policyType;
		 String comment;
		 
		 ShortPolicyImpl(Long id, String name, String policyType, String comment ){
			this.id = id;
			this.name = name;
			this.policyType = policyType;
			this.comment = comment;
		}
		 
		@Override
		public Long getId() {
			return this.id;
		}

		@Override
		public String getName() {
			return this.name;
		}

		@Override
		public String getPolicyType() {
			return this.policyType;
		}

		@Override
		public String getComment() {
			return this.comment;
		}
		
	}
    @Mock
	private IPolicyRepo policyRepo;

    PolicyService policyService;

    @BeforeEach
    void initUseCase() {
    	policyService = new PolicyServiceImpl(policyRepo);
    }

    @Test
    public void policyFetchAllPolicies() {
    	Policy policy1 = new Policy("Example Policy", "agreement", "/policy/ComplexPolicyForm", "Example Comment", "");
    	Policy policy2 = new Policy("Example Policy", "offer", "/policy/ComplexPolicyForm", "Example Comment", "");
        List<Policy> policyList = new ArrayList<>();
        policyList.add(policy1);
        policyList.add(policy2);

        // providing knowledge
        when(policyRepo.findAll()).thenReturn(policyList);

        List<Policy> fetchedPolicies = policyService.fetchAllPolicies();
        Assert.assertTrue(fetchedPolicies.size() == 2);
    }
    
    @Test
    public void policyShortPolicy() {
    	ShortPolicy policy1 = new ShortPolicyImpl(0L, "Example Policy", "agreement", "Example Comment");
    	ShortPolicy policy2 = new ShortPolicyImpl(1L, "Example Policy", "offer", "Example Comment");
    	
        List<ShortPolicy> policyList = new ArrayList<>();
        policyList.add(policy1);
        policyList.add(policy2);

        // providing knowledge
        when(policyRepo.findBy()).thenReturn(policyList);

        List<ShortPolicy> fetchedPolicies = policyService.shortPolicyFindById();
        Assert.assertTrue(fetchedPolicies.size() == 2);
    }
    
    
    @Test
    public void policySaveSuccess() {
        when(policyRepo.save(any(Policy.class))).thenReturn(new Policy("Example Policy", "Agreement", "/policy/ComplexPolicyForm", "Example Comment", ""));
        Policy savedPolicy = policyService.savePolicy("Example Policy", "Agreement", "/policy/ComplexPolicyForm", "Example Comment", "");
        assertNotNull(savedPolicy.getName(), "Policy Repository is not available!");
    }
    
    @Test
    public void policySaveSuccessWithPolicy() {
    	Policy policy = new Policy("Example Policy", "agreement", "/policy/ComplexPolicyForm", "Example Comment", "");

        // providing knowledge
        when(policyRepo.save(any(Policy.class))).thenReturn(policy);

        Policy savedPolicy = policyService.savePolicy(policy);
        assertNotNull(savedPolicy.getName());
    }
    
}