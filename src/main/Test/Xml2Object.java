import Bean.Jive;
import Bean.Student;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class Xml2Object {

    @Test
    public void locateOpenfire(){
        try {
            String xmlPath = "Student.xml";
            JAXBContext context = JAXBContext.newInstance(xmlPath);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            Student object = (Student) unmarshaller.unmarshal(new File(xmlPath));

            System.out.println(object.getAge());
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
