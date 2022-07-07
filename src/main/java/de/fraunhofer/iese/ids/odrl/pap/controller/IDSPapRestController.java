package de.fraunhofer.iese.ids.odrl.pap.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.github.jsonldjava.utils.JsonUtils;

import de.fraunhofer.iese.ids.odrl.pap.model.JsonOdrlPolicy;
import de.fraunhofer.iese.ids.odrl.pap.model.ShortPolicy;
import de.fraunhofer.iese.ids.odrl.pap.util.OdrlTranslator;
import de.fraunhofer.iese.ids.odrl.pap.util.TransformPolicy;
import de.fraunhofer.iese.ids.odrl.pap.util.UcAppService;
import de.fraunhofer.iese.ids.odrl.policy.library.model.OdrlPolicy;
import de.fraunhofer.iese.ids.odrl.policy.library.model.enums.ActionType;
import de.fraunhofer.iese.ids.odrl.policy.library.model.enums.RuleType;
import de.fraunhofer.iese.ids.odrl.policy.library.model.tooling.IdsOdrlUtil;
import de.fraunhofer.iese.ids.odrl.pap.repository.IPolicyRepo;
import de.fraunhofer.iese.ids.odrl.pap.entity.Policy;

@RestController()
@CrossOrigin
public class IDSPapRestController {
	@Autowired
	IPolicyRepo policyRepo;
	
	String baseUid = "https://w3id.org/idsa/autogen/contract/";
	
	private void checkIfPolicyIsEditedAndDelete(RecievedOdrlPolicy rp) {
		try {
			Optional<Policy> policy = policyRepo.findById((long) rp.getId());
			if (policy.isPresent()) {
				policyRepo.delete(policy.get());
			}
		} catch (Exception e) {
		}
	}
	
	@DeleteMapping("/api/policies/{id}")
	public ResponseEntity<HttpStatus> deletePolicy(@PathVariable Long id) {
		try {
			Optional<Policy> policy = policyRepo.findById(id);
			if (policy.isPresent()) {
				policyRepo.delete(policy.get());
			}
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/api/policies/export_{id}")
	public ResponseEntity<String> exportPolicy(@PathVariable Long id) {
		try {
			Optional<Policy> policy = policyRepo.findById(id);
			if (policy.isPresent()) {
				Policy realPolicy = policy.get();
				return new ResponseEntity<>(realPolicy.getIDSPolicy(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/api/policies/edit_{id}")
	public ResponseEntity<Policy> editPolicy(@PathVariable Long id) {
		try {
			Optional<Policy> policy = policyRepo.findById(id);
			if (policy.isPresent()) {
				Policy realPolicy = policy.get();
				return new ResponseEntity<>(realPolicy, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/api/policies")
	public ResponseEntity<List<ShortPolicy>> getAllPolicies() {
		try {
			List<ShortPolicy> list = policyRepo.findBy();
			return new ResponseEntity<>(list, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/policy/ProvideAccessPolicyForm")
	public String accessPolicy(@RequestBody RecievedOdrlPolicy rp) {
		checkIfPolicyIsEditedAndDelete(rp);
		JsonIDSConverter converter = new JsonIDSConverter(rp,RuleType.PERMISSION ,ActionType.USE);
		String uid = baseUid + "restrict-access";
		if (converter.addLocationCondition()) {
			uid = baseUid + "restrict-access-location";
		}
		if (converter.addApplicationCondition()) {
			uid = baseUid + "restrict-access-application";
		}
		if (converter.addUserRoleCondition()) {
			uid = baseUid + "restrict-access-user-role";
		}
		if (converter.addSecurityLevelCondition()) {
			uid = baseUid + "restrict-access-user-security-level";
		}
		if (converter.addStateCondition()) {
			uid = baseUid + "restrict-access-state";
		}
		if (converter.addConnectorCondition()) {
			uid = baseUid + "restrict-access-connector";
		}
		if (converter.addPurposeCondition()) {
			uid = baseUid + "restrict-access-purpose";
		}
		if (converter.addEventCondition()) {
			uid = baseUid + "restrict-access-event";
		}
		if (converter.addRestrictTimeIntervalCondition()) {
			uid = baseUid + "restrict-access-interval";
		}
		if (converter.addRestrictEndTimeCondition()) {
			uid = baseUid + "restrict-access-end-time";
		}
		if (converter.addPaymentCondition()) {
			uid = baseUid + "provide-access-after-payment";
		}
		if (converter.addElapsedTimeRightOperand()) {
			uid = baseUid + "restrict-access-interval";
		}
		return converter.createPolicy(uid, policyRepo, "/policy/ProvideAccessPolicyForm");
	}
	
	@PostMapping("/policy/ComplexPolicyForm")
	public String complexPolicy(@RequestBody RecievedOdrlPolicy rp) {
		checkIfPolicyIsEditedAndDelete(rp);
		JsonIDSConverter converter = new JsonIDSConverter(rp,RuleType.PERMISSION ,ActionType.USE);
		String uid = baseUid + "complex-policy-access";
		converter.addLocationCondition();
		converter.addApplicationCondition();
		converter.addUserRoleCondition();
		converter.addConnectorCondition();
		converter.addSecurityLevelCondition();
		converter.addStateCondition();
		converter.addPurposeCondition();
		converter.addEventCondition();
		converter.addCounterCondition();
		converter.addRestrictTimeIntervalCondition();
		converter.addRestrictEndTimeCondition();
		converter.addPaymentCondition();
		converter.addElapsedTimeRightOperand();
		converter.addRuleDistributeData();			
		converter.addPostDuties();
		converter.addPreDuties();
		return converter.createPolicy(uid, policyRepo, "/policy/ComplexPolicyForm");
	}
	
	@PostMapping("/policy/CountAccessPolicyForm")
	public String countPolicy(@RequestBody RecievedOdrlPolicy rp) {
		checkIfPolicyIsEditedAndDelete(rp);
		JsonIDSConverter converter = new JsonIDSConverter(rp,RuleType.PERMISSION ,ActionType.USE);
		String uid = baseUid + "count-access";
		converter.addCounterCondition();
		converter.addCounterToPostduties();
		return converter.createPolicy(uid, policyRepo, "/policy/CountAccessPolicyForm");
	}
	
	@PostMapping("/policy/deletePolicyAfterUsage")
	public String deletePolicyAfterUsage(@RequestBody RecievedOdrlPolicy rp) {
		checkIfPolicyIsEditedAndDelete(rp);
		JsonIDSConverter converter = new JsonIDSConverter(rp,RuleType.PERMISSION ,ActionType.USE);
		String uid = baseUid + "delete-after-usage";
		converter.addDeletePolicyAfterUsage();
		return converter.createPolicy(uid, policyRepo, "/policy/DeleteData");
	}
	
	@PostMapping("/policy/deletePolicyAfterUsagePeriod")
	public String deletePolicyAfterUsagePeriod(@RequestBody RecievedOdrlPolicy rp) {
		checkIfPolicyIsEditedAndDelete(rp);
		JsonIDSConverter converter = new JsonIDSConverter(rp,RuleType.PERMISSION ,ActionType.USE);
		String uid = baseUid + "delete-after-usage";
		converter.addDeletePolicyAfterUsagePeriod();
		return converter.createPolicy(uid, policyRepo, "/policy/DeleteData");
	}
	
	@PostMapping("/policy/AnonymizeInRestPolicyForm")
	public String anonymizeInRestolicy(@RequestBody RecievedOdrlPolicy rp) {
		checkIfPolicyIsEditedAndDelete(rp);
		JsonIDSConverter converter = new JsonIDSConverter(rp,RuleType.OBLIGATION ,ActionType.ANONYMIZE);
		String uid = baseUid + "anonymize-in-rest";
		return converter.createPolicy(uid, policyRepo, "/policy/AnonymizeInRestPolicyForm");
	}
	
	@PostMapping("/policy/AnonymizeInTransitPolicyForm")
	public String anonymizeTransitPolicy(@RequestBody RecievedOdrlPolicy rp) {
		checkIfPolicyIsEditedAndDelete(rp);
		JsonIDSConverter converter = new JsonIDSConverter(rp,RuleType.PERMISSION ,ActionType.USE);
		String uid = baseUid + "anonymize-in-transit";
		converter.addPreDuties();
		return converter.createPolicy(uid, policyRepo, "/policy/AnonymizeInTransitPolicyForm");
	}
	
	@PostMapping("/policy/LogAccessPolicyForm")
	public String logPolicy(@RequestBody RecievedOdrlPolicy rp) {
		checkIfPolicyIsEditedAndDelete(rp);
		JsonIDSConverter converter = new JsonIDSConverter(rp,RuleType.PERMISSION ,ActionType.USE);
		String uid = baseUid + "log-access";
		converter.addPostDuties();
		return converter.createPolicy(uid, policyRepo, "/policy/LogAccessPolicyForm");
	}
	
	@PostMapping("/policy/InformPolicyForm")
	public String informPolicy(@RequestBody RecievedOdrlPolicy rp) {
		checkIfPolicyIsEditedAndDelete(rp);
		JsonIDSConverter converter = new JsonIDSConverter(rp,RuleType.PERMISSION ,ActionType.USE);
		String uid = baseUid + "notify";
		converter.addPostDuties();
		return converter.createPolicy(uid, policyRepo, "/policy/InformPolicyForm");
	}
	
	@PostMapping("/policy/DistributePolicyForm")
	public String distributePolicyForm(@RequestBody RecievedOdrlPolicy rp) {
		checkIfPolicyIsEditedAndDelete(rp);
		JsonIDSConverter converter = new JsonIDSConverter(rp,RuleType.PERMISSION ,ActionType.DISTRIBUTE);
		String uid = baseUid + "restrict-access-encoding";
		converter.distributeData();
		return converter.createPolicy(uid, policyRepo, "/policy/DistributeData");
	}
	
	@PostMapping("/policy/JsonOdrlPolicyMYDATA")
	public String policy(@RequestBody String jsonPolicy)  {
		OdrlPolicy odrlPolicy = IdsOdrlUtil.getOdrlPolicy(jsonPolicy);
		boolean tempProviderSide = true;
		return TransformPolicy.createTechnologyDependentPolicy(odrlPolicy, tempProviderSide);
	}
	
	@SuppressWarnings("rawtypes")
	@PostMapping("/policy/InterpretOdrlPolicy")
	public String interpretPolicy(@RequestBody String jsonPolicy)  {
		OdrlPolicy odrlPolicy = IdsOdrlUtil.getOdrlPolicy(jsonPolicy);
		Map map = null;
		try {
			if (null != jsonPolicy) {
				Object o = JsonUtils.fromString(jsonPolicy);
				if (o instanceof Map) {
					map = (Map) o;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		boolean tempProviderSide = true;
		String dtPolicy = OdrlTranslator.translate(map, tempProviderSide, odrlPolicy);
		return dtPolicy;
	}
	
	@PostMapping("/policy/initialTechnologyDependentPolicy")
	public String initialTechnologyDependentPolicy(@RequestBody String odrl)  {
		OdrlPolicy odrlPolicy = IdsOdrlUtil.getOdrlPolicy(odrl);
		boolean tempProviderSide = true;
		String dtPolicy = OdrlTranslator.translate(null, tempProviderSide, odrlPolicy);
		return dtPolicy;
	}
	
	@PostMapping("/policy/sendPolicy")
	public String sendPolicy(@RequestBody JsonOdrlPolicy jsonOdrlPolicy) {
		String returnedPolicy = UcAppService.sendPolicy(jsonOdrlPolicy);
		return returnedPolicy;
	}
	
}
