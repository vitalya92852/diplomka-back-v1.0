package com.example.demo1222.XmlClasses.StudentXML;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class StudentXML {
    @XmlElement(name = "full_name")
    private String fullName;

    @XmlElement
    private String group;

    @XmlElement
    private int course;

    @XmlElement(name = "semester")
    private List<SemesterXML> semesters;
}
