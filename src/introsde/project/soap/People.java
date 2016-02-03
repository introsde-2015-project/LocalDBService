package introsde.project.soap;
import introsde.project.model.Goal;
import introsde.project.model.GoalType;
import introsde.project.model.Measure;
import introsde.project.model.MeasureType;
import introsde.project.model.Person;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.WebResult;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

/**
 * People web service
 * @author Toomas
 *
 */
@WebService
@SOAPBinding(style = Style.DOCUMENT, use=Use.LITERAL)
public interface People {

    @WebMethod(operationName="readPersonList")
    @WebResult(name="people") 
    public List<Person> readPersonList();

    @WebMethod(operationName="readPerson")
    @WebResult(name="person") 
    public Person readPerson(@WebParam(name="personId") int id);

    @WebMethod(operationName="updatePerson")
    @WebResult(name="personId") 
    public int updatePerson(@WebParam(name="person") Person person);

    @WebMethod(operationName="createPerson")
    @WebResult(name="newPerson") 
    public Person createPerson(@WebParam(name="person") Person person);

    @WebMethod(operationName="deletePerson")
    @WebResult(name="deletedValue") 
    public boolean deletePerson(@WebParam(name="personId") int id);
    
    @WebMethod(operationName="readPersonMeasureHistory")
    @WebResult(name="measurehistory") 
    public List<Measure> readPersonMeasureHistory(@WebParam(name="personId") int id, @WebParam(name="measureType") String measureType);
    
    @WebMethod(operationName="readMeasureTypes")
    @WebResult(name="measuretypes") 
    public List<MeasureType> readMeasureTypes();
    
    @WebMethod(operationName="readPersonMeasure")
    @WebResult(name="measure") 
    public Measure readPersonMeasure(@WebParam(name="personId") int id, @WebParam(name="measureType") String measureType, @WebParam(name="measureId") int mid);
    
    @WebMethod(operationName="createPersonMeasure")
    @WebResult(name="newMeasure") 
    public Measure createPersonMeasure(@WebParam(name="personId") int id, @WebParam(name="measure") Measure measure);
    
    @WebMethod(operationName="updatePersonMeasure")
    @WebResult(name="measureId") 
    public int updatePersonMeasure(@WebParam(name="personId") int id, @WebParam(name="measure") Measure measure);
    
    @WebMethod(operationName="readPersonGoals")
    @WebResult(name="personGoals") 
    public List<Goal> readPersonGoals(@WebParam(name="personId") int id);
    
    @WebMethod(operationName="readPersonGoalsByMeasure")
    @WebResult(name="personMeasureGoals") 
    public List<Goal> readPersonGoalsByMeasure(@WebParam(name="personId") int id, @WebParam(name="measureType") String measureType);
    
    @WebMethod(operationName="createPersonGoal")
    @WebResult(name="newGoal") 
    public Goal createPersonGoal(@WebParam(name="personId") int id, @WebParam(name="goal") Goal goal);
    
    @WebMethod(operationName="updatePersonGoal")
    @WebResult(name="goalId") 
    public int updatePersonGoal(@WebParam(name="personId") int id, @WebParam(name="goal") Goal goal);
    
    @WebMethod(operationName="readGoalTypes")
    @WebResult(name="goaltypes") 
    public List<GoalType> readGoalTypes();
    
}
