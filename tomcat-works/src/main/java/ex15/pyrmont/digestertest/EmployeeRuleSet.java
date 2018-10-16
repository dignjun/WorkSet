package ex15.pyrmont.digestertest;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.RuleSet;

public class EmployeeRuleSet implements RuleSet {

	public void addRuleInstances(Digester digester) {
		// add rules
		digester.addObjectCreate("employee",
				"ex15.pyrmont.digestertest.Employee");
		digester.addSetProperties("employee");
		digester.addObjectCreate("employee/office",
				"ex15.pyrmont.digestertest.Office");
		digester.addSetProperties("employee/office");
		digester.addSetNext("employee/office", "addOffice");
		digester.addObjectCreate("employee/office/address",
				"ex15.pyrmont.digestertest.Address");
		digester.addSetProperties("employee/office/address");
		digester.addSetNext("employee/office/address", "setAddress");
	}

	public String getNamespaceURI() {
		// TODO Auto-generated method stub
		return null;
	}

}
