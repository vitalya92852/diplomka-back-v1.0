package com.example.demo1222.XmlClasses.TeacherXML;

import lombok.Data;
import org.hibernate.sql.ast.tree.update.Assignment;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class TeachingAssignmentsXML {
    @XmlElement(name = "assignment")
    private List<AssignmentXML> assignmentXML;
}
