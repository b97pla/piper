//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.04.19 at 11:26:29 AM CEST 
//


package molmed.xml.illuminareport;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{illuminareport.xml.molmed}LaneMetrics" maxOccurs="unbounded"/>
 *         &lt;element ref="{illuminareport.xml.molmed}SampleMetrics" maxOccurs="unbounded"/>
 *         &lt;element ref="{illuminareport.xml.molmed}MetaData"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "laneMetrics",
    "sampleMetrics",
    "metaData"
})
@XmlRootElement(name = "SequencingReport")
public class SequencingReport {

    @XmlElement(name = "LaneMetrics", required = true)
    protected List<LaneMetrics> laneMetrics;
    @XmlElement(name = "SampleMetrics", required = true)
    protected List<SampleMetrics> sampleMetrics;
    @XmlElement(name = "MetaData", required = true)
    protected MetaData metaData;

    /**
     * Gets the value of the laneMetrics property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the laneMetrics property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLaneMetrics().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LaneMetrics }
     * 
     * 
     */
    public List<LaneMetrics> getLaneMetrics() {
        if (laneMetrics == null) {
            laneMetrics = new ArrayList<LaneMetrics>();
        }
        return this.laneMetrics;
    }

    /**
     * Gets the value of the sampleMetrics property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sampleMetrics property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSampleMetrics().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SampleMetrics }
     * 
     * 
     */
    public List<SampleMetrics> getSampleMetrics() {
        if (sampleMetrics == null) {
            sampleMetrics = new ArrayList<SampleMetrics>();
        }
        return this.sampleMetrics;
    }

    /**
     * Gets the value of the metaData property.
     * 
     * @return
     *     possible object is
     *     {@link MetaData }
     *     
     */
    public MetaData getMetaData() {
        return metaData;
    }

    /**
     * Sets the value of the metaData property.
     * 
     * @param value
     *     allowed object is
     *     {@link MetaData }
     *     
     */
    public void setMetaData(MetaData value) {
        this.metaData = value;
    }

}
