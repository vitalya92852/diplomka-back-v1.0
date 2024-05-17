package com.example.demo1222.XmlClasses.TeacherXML;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class TeacherXML {
    @XmlElement(name = "full_name")
    private String fullname;

    @XmlElement(name = "teaching_assignments")
    private TeachingAssignmentsXML teachingAssignmentsXML;
}
