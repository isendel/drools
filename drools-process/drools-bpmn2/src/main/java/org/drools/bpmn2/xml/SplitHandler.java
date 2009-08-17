package org.drools.bpmn2.xml;

import org.drools.workflow.core.Node;
import org.drools.workflow.core.node.Split;
import org.xml.sax.Attributes;

public class SplitHandler extends AbstractNodeHandler {
    
    protected Node createNode(Attributes attrs) {
    	throw new IllegalArgumentException("Reading in should be handled by gateway handler");
    }
    
    @SuppressWarnings("unchecked")
	public Class generateNodeFor() {
        return Split.class;
    }

	public void writeNode(Node node, StringBuilder xmlDump, boolean includeMeta) {
		Split split = (Split) node;
		switch (split.getType()) {
			case Split.TYPE_AND:
				writeNode("parallelGateway", node, xmlDump, includeMeta);
				break;
			case Split.TYPE_XOR:
				writeNode("exclusiveGateway", node, xmlDump, includeMeta);
				break;
			default:
				throw new IllegalArgumentException("Unsupported split type: " + split.getType());
		}
		xmlDump.append("gatewayDirection=\"diverging\" ");
		endNode(xmlDump);
	}

}