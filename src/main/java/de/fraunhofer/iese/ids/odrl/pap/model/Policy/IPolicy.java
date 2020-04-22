package de.fraunhofer.iese.ids.odrl.pap.model.Policy;

import de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel.Party;
import de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel.PermissionRuleUseAction;
import de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel.PolicyType;

import java.net.URI;
import java.util.List;

public interface IPolicy {
	public IPermissionRuleUseAction getPermissionRuleUseAction();
	public void setPermissionRuleUseAction(IPermissionRuleUseAction permissionRuleUseAction);

	public IProhibitionRuleUseAction getProhibitionRuleUseAction();
	public void setProhibitionRuleUseAction(IProhibitionRuleUseAction prohibitionRuleUseAction);

	public IObligationRuleAnonymizeAction getObligationRuleAnonymizeAction();
	public void setObligationRuleAnonymizeAction(IObligationRuleAnonymizeAction obligationRuleAnonymizeAction);

	public IPermissionRuleReadAction getPermissionRuleReadAction();
	public void setPermissionRuleReadAction(IPermissionRuleReadAction permissionRuleReadAction);

	public IProhibitionRuleReadAction getProhibitionRuleReadAction();
	public void setProhibitionRuleReadAction(IProhibitionRuleReadAction prohibitionRuleReadAction);

	public IObligationRuleLogAction getObligationRuleLogAction();
	public void setObligationRuleLogAction(IObligationRuleLogAction obligationRuleLogAction);

	public IObligationRuleInformAction getObligationRuleInformAction();
	public void setObligationRuleInformAction(IObligationRuleInformAction obligationRuleInformAction);

	public IPermissionRulePrintAction getPermissionRulePrintAction();
	public void setPermissionRulePrintAction(IPermissionRulePrintAction permissionRulePrintAction);

	public IProhibitionRulePrintAction getProhibitionRulePrintAction();
	public void setProhibitionRulePrintAction(IProhibitionRulePrintAction prohibitionRulePrintAction);

	public IObligationRuleDeleteAction getObligationRuleDeleteAction();
	public void setObligationRuleDeleteAction(IObligationRuleDeleteAction obligationRuleDeleteAction);

	public IPermissionRuleDistributeAction getPermissionRuleDistributeAction();
	public void setPermissionRuleDistributeAction(IPermissionRuleDistributeAction permissionRuleDistributeAction);

	public IProhibitionRuleDistributeAction getProhibitionRuleDistributeAction();
	public void setProhibitionRuleDistributeAction(IProhibitionRuleDistributeAction prohibitionRuleDistributeAction);

	public URI getProfile();
	public void setProfile(URI profile);

	public URI getPolicyId();
	public void setPolicyId(URI policyId);

	public PolicyType getType();
	public void setType(PolicyType type);

	public URI getTarget();
	public void setTarget(URI target);

	public Party getConsumer();
	public void setConsumer(Party consumer);
	
	public Party getProvider();
	public void setProvider(Party provider);

	public boolean isProviderSide();
	public void setProviderSide(boolean providerSide);
}
