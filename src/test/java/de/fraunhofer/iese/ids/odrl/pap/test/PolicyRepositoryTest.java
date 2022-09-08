package de.fraunhofer.iese.ids.odrl.pap.test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.util.Assert;

import de.fraunhofer.iese.ids.odrl.pap.repository.IPolicyRepo;
import de.fraunhofer.iese.ids.odrl.pap.repository.Policy;
import de.fraunhofer.iese.ids.odrl.policy.library.model.interfaces.IPolicy;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
@DataJpaTest
public class PolicyRepositoryTest {
    @Autowired
	private IPolicyRepo policyRepo;

    @BeforeEach
    void initUseCase() {
        List<Policy> customers = Arrays.asList(
                new Policy("Example Policy", "Agreement", "/policy/ComplexPolicyForm", "Example Comment", ""));
                policyRepo.saveAll(customers);
    }

    @AfterEach
    public void destroyAll(){
    	policyRepo.deleteAll();
    }

    @Test
    void saveAllSuccess() {
        List<Policy> customers = Arrays.asList(
                new Policy("Example Policy", "Agreement", "/policy/ComplexPolicyForm", "Example Comment", ""),
                new Policy("Example Policy", "Agreement", "/policy/ComplexPolicyForm", "Example Comment", ""),
                new Policy("Example Policy", "Agreement", "/policy/ComplexPolicyForm", "Example Comment", "")
        );
        Iterable<Policy> allCustomer = policyRepo.saveAll(customers);

        AtomicInteger validIdFound = new AtomicInteger();
        allCustomer.forEach(customer -> {
            if(customer.getId()>0){
                validIdFound.getAndIncrement();
            }
        });
        Assert.isTrue(validIdFound.intValue() == 3, "");
        //assertThat(validIdFound.intValue()).isEqualTo(3);
    }

    @Test
    void findAllSuccess() {
        List<Policy> allCustomer = policyRepo.findAll();
        Assert.isTrue(allCustomer.size() == 1, "");
        //assertThat(allCustomer.size()).isGreaterThanOrEqualTo(1);
    }
}

