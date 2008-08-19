//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-520 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2008.08.19 at 11:36:25 PM BST 
//


package org.drools.task;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.jvnet.jaxb2_commons.lang.Equals;
import org.jvnet.jaxb2_commons.lang.HashCode;
import org.jvnet.jaxb2_commons.lang.builder.JAXBEqualsBuilder;
import org.jvnet.jaxb2_commons.lang.builder.JAXBHashCodeBuilder;


/**
 * <p>Java class for tGenericHumanRole complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tGenericHumanRole">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.example.org/WS-HT}tExtensibleElements">
 *       &lt;sequence>
 *         &lt;element name="from" type="{http://www.example.org/WS-HT}tFrom"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tGenericHumanRole", propOrder = {
    "from"
})
@Entity(name = "org.example.ws_ht.TGenericHumanRole")
@Table(name = "TGENERICHUMANROLE")
public class TGenericHumanRole
    extends TExtensibleElements
    implements Equals, HashCode
{

    @XmlElement(required = true)
    protected org.drools.task.TFrom from;

    /**
     * Gets the value of the from property.
     * 
     * @return
     *     possible object is
     *     {@link org.drools.task.TFrom }
     *     
     */
    @ManyToOne(targetEntity = org.drools.task.TFrom.class, cascade = {
        CascadeType.ALL
    })
    @JoinColumn(name = "FROM__TGENERICHUMANROLE_ID")
    public org.drools.task.TFrom getFrom() {
        return from;
    }

    /**
     * Sets the value of the from property.
     * 
     * @param value
     *     allowed object is
     *     {@link org.drools.task.TFrom }
     *     
     */
    public void setFrom(org.drools.task.TFrom value) {
        this.from = value;
    }

    public void equals(Object object, EqualsBuilder equalsBuilder) {
        if (!(object instanceof TGenericHumanRole)) {
            equalsBuilder.appendSuper(false);
            return ;
        }
        if (this == object) {
            return ;
        }
        super.equals(object, equalsBuilder);
        final TGenericHumanRole that = ((TGenericHumanRole) object);
        equalsBuilder.append(this.getFrom(), that.getFrom());
    }

    public boolean equals(Object object) {
        if (!(object instanceof TGenericHumanRole)) {
            return false;
        }
        if (this == object) {
            return true;
        }
        final EqualsBuilder equalsBuilder = new JAXBEqualsBuilder();
        equals(object, equalsBuilder);
        return equalsBuilder.isEquals();
    }

    public void hashCode(HashCodeBuilder hashCodeBuilder) {
        super.hashCode(hashCodeBuilder);
        hashCodeBuilder.append(this.getFrom());
    }

    public int hashCode() {
        final HashCodeBuilder hashCodeBuilder = new JAXBHashCodeBuilder();
        hashCode(hashCodeBuilder);
        return hashCodeBuilder.toHashCode();
    }

}
