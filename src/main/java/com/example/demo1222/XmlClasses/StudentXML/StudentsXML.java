package com.example.demo1222.XmlClasses.StudentXML;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

@Data
@XmlRootElement(name = "students")
@XmlAccessorType(XmlAccessType.FIELD)
public class StudentsXML {
    @XmlElement(name = "student")
    private List<StudentXML> students;

}
