package org.drools.xml.processes;

import org.drools.workflow.core.Node;
import org.drools.workflow.core.node.RuleSetNode;
import org.drools.xml.Configuration;
import org.drools.xml.ExtensibleXmlParser;
import org.xml.sax.SAXException;

public class RuleSetNodeHandler extends AbstractNodeHandler {

    protected Node createNode() {
        return new RuleSetNode();
    }

    public void handleNode(final Node node, final Configuration config, final String uri,
            final String localName, final ExtensibleXmlParser parser)
            throws SAXException {
        super.handleNode(node, config, uri, localName, parser);
        RuleSetNode ruleSetNode = (RuleSetNode) node;
        String ruleFlowGroup = config.getAttribute("ruleFlowGroup");
        ruleSetNode.setRuleFlowGroup(ruleFlowGroup);
    }

    public Class generateNodeFor() {
        return RuleSetNode.class;
    }

	public void writeNode(Node node, StringBuffer xmlDump, boolean includeMeta) {
		RuleSetNode ruleSetNode = (RuleSetNode) node;
		writeNode("ruleSet", ruleSetNode, xmlDump, includeMeta);
        String ruleFlowGroup = ruleSetNode.getRuleFlowGroup();
        if (ruleFlowGroup != null) {
            xmlDump.append("ruleFlowGroup=\"" + ruleFlowGroup + "\" ");
        }
        endNode(xmlDump);
	}

}
