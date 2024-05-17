package com.example.demo1222.XmlClasses.TeacherXML;

import com.example.demo1222.XmlClasses.TeacherXML.TeacherXML;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "teachers")
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class TeachersXML {

    @XmlElement(name = "teacher")
    private List<TeacherXML> teacher;


}