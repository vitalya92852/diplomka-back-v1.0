package com.example.demo1222.XmlClasses.StudentXML;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.xml.bind.annotation.*;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class GradeXML {
    @XmlAttribute
    private String subject;
    @XmlValue
    private int grade;



}
