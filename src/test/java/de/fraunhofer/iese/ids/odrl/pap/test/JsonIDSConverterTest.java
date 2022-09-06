package de.fraunhofer.iese.ids.odrl.pap.test;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;

import de.fraunhofer.iese.ids.odrl.pap.controller.JsonIDSConverter;
import de.fraunhofer.iese.ids.odrl.pap.repository.Policy;
import de.fraunhofer.iese.ids.odrl.policy.library.model.enums.ActionType;
import de.fraunhofer.iese.ids.odrl.policy.library.model.enums.RuleType;

public class JsonIDSConverterTest {

	JsonIDSConverter converter;
	
    @BeforeEach
    void initUseCase() {
		converter = new JsonIDSConverter(null ,RuleType.PERMISSION ,ActionType.USE);

    }
}
