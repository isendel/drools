package org.drools.bpmn2.legacy.beta1.di;

import org.drools.xml.ExtensibleXmlParser;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class GatewayShapeHandler extends ShapeHandler {

    protected String getNodeRefName() {
        return "gatewayRef";
    }

    public Object start(final String uri, final String localName,
            final Attributes attrs, final ExtensibleXmlParser parser)
            throws SAXException {
        NodeInfo nodeInfo = (NodeInfo) super.start(
            uri, localName, attrs, parser);
        int offsetX = (int) ((nodeInfo.getWidth() - 48) / 2);
        nodeInfo.setX(nodeInfo.getX() + offsetX);
        nodeInfo.setWidth(48);
        int offsetY = (int) ((nodeInfo.getHeight() - 48) / 2);
        nodeInfo.setY(nodeInfo.getY() + offsetY);
        nodeInfo.setHeight(48);
        return nodeInfo;
    }
    
}
