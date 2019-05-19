package ru.javaops.masterjava;

import com.google.common.io.Resources;
import ru.javaops.masterjava.xml.schema.*;
import ru.javaops.masterjava.xml.util.JaxbParser;
import ru.javaops.masterjava.xml.util.Schemas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainXml {
    private static final JaxbParser JAXB_PARSER = new JaxbParser(ObjectFactory.class);

    static {
        JAXB_PARSER.setSchema(Schemas.ofClasspath("payload.xsd"));
    }

    public static void printUsers(String nameProject) throws Exception {
        Payload payload = JAXB_PARSER.unmarshal(
                Resources.getResource("payload.xml").openStream());
        Payload.Projects projects = payload.getProjects();
        List<Project> projectList = projects.getProject();
        Project project = projectList.stream().filter((x) -> nameProject.equals(x.getName())).findFirst().orElse(null);

        List<GroupType> groups = project.getGroup();
        List<User> users = payload.getUsers().getUser();
        List<User> projectUsers = new ArrayList<>();

        for (User x : users) {
            for (Object group : x.getGroups()) {
                if (groups.contains(group)){
                    projectUsers.add(x);
                    break;
                }
            }
        }

        Collections.sort(projectUsers);
        System.out.println();
        for (User user: projectUsers) {
            System.out.println(user.getFullName() + " / " + user.getEmail());
        }
    }
}
