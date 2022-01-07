package de.fraunhofer.iese.ids.odrl.pap.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.jsonldjava.utils.JsonUtils;

import de.fraunhofer.iese.ids.odrl.pap.util.OdrlTranslator;
import de.fraunhofer.iese.ids.odrl.pap.util.TransformPolicy;
import de.fraunhofer.iese.ids.odrl.policy.library.model.Action;
import de.fraunhofer.iese.ids.odrl.policy.library.model.OdrlPolicy;
import de.fraunhofer.iese.ids.odrl.policy.library.model.Rule;
import de.fraunhofer.iese.ids.odrl.policy.library.model.enums.ActionType;
import de.fraunhofer.iese.ids.odrl.policy.library.model.enums.RuleType;
import de.fraunhofer.iese.ids.odrl.policy.library.model.tooling.IdsOdrlUtil;

@RestController()
@CrossOrigin
public class IDSPapRestController {
	String baseUid = "https://w3id.org/idsa/autogen/contract/";
	@PostMapping("/policy/ProvideAccess")
	public String accessPolicy(@RequestBody RecievedOdrlPolicy rp) {
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
		return converter.createPolicy(uid);
	}
	
	@PostMapping("/policy/ComplexPolicyForm")
	public String complexPolicy(@RequestBody RecievedOdrlPolicy rp) {
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
		return converter.createPolicy(uid);
	}
	
	@PostMapping("/policy/CountAccessPolicyForm")
	public String countPolicy(@RequestBody RecievedOdrlPolicy rp) {
		JsonIDSConverter converter = new JsonIDSConverter(rp,RuleType.PERMISSION ,ActionType.USE);
		String uid = baseUid + "count-access";
		converter.addCounterCondition();
		converter.addCounterToPostduties();
		return converter.createPolicy(uid);
	}
	
	@PostMapping("/policy/deletePolicyAfterUsage")
	public String deletePolicyAfterUsage(@RequestBody RecievedOdrlPolicy rp) {
		JsonIDSConverter converter = new JsonIDSConverter(rp,RuleType.PERMISSION ,ActionType.USE);
		String uid = baseUid + "delete-after-usage";
		converter.addDeletePolicyAfterUsage();
		return converter.createPolicy(uid);
	}
	
	@PostMapping("/policy/deletePolicyAfterUsagePeriod")
	public String deletePolicyAfterUsagePeriod(@RequestBody RecievedOdrlPolicy rp) {
		JsonIDSConverter converter = new JsonIDSConverter(rp,RuleType.PERMISSION ,ActionType.USE);
		String uid = baseUid + "delete-after-usage";
		converter.addDeletePolicyAfterUsagePeriod();
		return converter.createPolicy(uid);
	}
	
	@PostMapping("/policy/AnonymizeInRestPolicyForm")
	public String anonymizeInRestolicy(@RequestBody RecievedOdrlPolicy rp) {
		JsonIDSConverter converter = new JsonIDSConverter(rp,RuleType.OBLIGATION ,ActionType.ANONYMIZE);
		String uid = baseUid + "anonymize-in-rest";
		return converter.createPolicy(uid);
	}
	
	@PostMapping("/policy/AnonymizeInTransitPolicyForm")
	public String anonymizeTransitPolicy(@RequestBody RecievedOdrlPolicy rp) {
		JsonIDSConverter converter = new JsonIDSConverter(rp,RuleType.PERMISSION ,ActionType.USE);
		String uid = baseUid + "anonymize-in-transit";
		converter.addPreDuties();
		return converter.createPolicy(uid);
	}
	
	@PostMapping("/policy/LogAccessPolicyForm")
	public String logPolicy(@RequestBody RecievedOdrlPolicy rp) {
		JsonIDSConverter converter = new JsonIDSConverter(rp,RuleType.PERMISSION ,ActionType.USE);
		String uid = baseUid + "log-access";
		converter.addPostDuties();
		return converter.createPolicy(uid);
	}
	
	@PostMapping("/policy/InformPolicyForm")
	public String informPolicy(@RequestBody RecievedOdrlPolicy rp) {
		JsonIDSConverter converter = new JsonIDSConverter(rp,RuleType.PERMISSION ,ActionType.USE);
		String uid = baseUid + "notify";
		converter.addPostDuties();
		return converter.createPolicy(uid);
	}
	
	@PostMapping("/policy/DistributePolicyForm")
	public String distributePolicyForm(@RequestBody RecievedOdrlPolicy rp) {
		JsonIDSConverter converter = new JsonIDSConverter(rp,RuleType.PERMISSION ,ActionType.DISTRIBUTE);
		String uid = baseUid + "restrict-access-encoding";
		converter.distributeData();
		return converter.createPolicy(uid);
	}
	
	@PostMapping("/policy/JsonOdrlPolicyMYDATA")
	public String policy(@RequestBody String jsonPolicy) {
		OdrlPolicy odrlPolicy = IdsOdrlUtil.getOdrlPolicy(jsonPolicy);
		boolean tempProviderSide = true;
		return TransformPolicy.createTechnologyDependentPolicy(odrlPolicy, tempProviderSide);
	}
	
	@PostMapping("/policy/InterpretOdrlPolicy")
	public String interpretPolicy(@RequestBody String jsonPolicy) {
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
	
}
