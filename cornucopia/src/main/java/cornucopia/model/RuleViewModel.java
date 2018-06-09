package cornucopia.model;

import cornucopia.entity.RuleConditionEntity;
import cornucopia.entity.RuleEntity;

public class RuleViewModel {
	private RuleEntity rule;
	private RuleConditionEntity[] ruleConditions;

	public RuleEntity getRule() {
		return rule;
	}

	public void setRule(RuleEntity rule) {
		this.rule = rule;
	}

	public RuleConditionEntity[] getRuleConditions() {
		return ruleConditions;
	}

	public void setRuleConditions(RuleConditionEntity[] ruleConditions) {
		this.ruleConditions = ruleConditions;
	}
}
