package com.example.demo1222.XmlClasses.StudentXML;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class WeekXML {
    @XmlAttribute
    private int number;

    @XmlElement(name = "grade")
    private List<GradeXML> grades;


}
