package introsde.project.soap;
import introsde.project.model.Goal;
import introsde.project.model.GoalType;
import introsde.project.model.Measure;
import introsde.project.model.MeasureType;
import introsde.project.model.Person;
import introsde.project.model.Timeline;

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
    @WebResult(name="updatedPerson") 
    public Person updatePerson(@WebParam(name="person") Person person);

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
    @WebResult(name="updatedMeasure") 
    public Measure updatePersonMeasure(@WebParam(name="personId") int id, @WebParam(name="measure") Measure measure);
    
    @WebMethod(operationName="removeMeasure")
    @WebResult(name="deletedMeasure") 
    public boolean removeMeasure(@WebParam(name="measureId") int id);
    
    @WebMethod(operationName="readPersonGoals")
    @WebResult(name="personGoals") 
    public List<Goal> readPersonGoals(@WebParam(name="personId") int id);
    
    @WebMethod(operationName="readPersonGoalsByMeasure")
    @WebResult(name="personMeasureGoals") 
    public List<Goal> readPersonGoalsByMeasure(@WebParam(name="personId") int id, @WebParam(name="measureType") String measureType);
    
    @WebMethod(operationName="readSingleGoal")
    @WebResult(name="singleGoal") 
    public Goal readSingleGoal(@WebParam(name="personId") int id, @WebParam(name="goalId") int gid);
    
    @WebMethod(operationName="createPersonGoal")
    @WebResult(name="newGoal") 
    public Goal createPersonGoal(@WebParam(name="goal") Goal goal, @WebParam(name="personId") int id);
    
    @WebMethod(operationName="updatePersonGoal")
    @WebResult(name="updatedGoal") 
    public Goal updatePersonGoal(@WebParam(name="personId") int id, @WebParam(name="goal") Goal goal);
    
    @WebMethod(operationName="removeGoal")
    @WebResult(name="deletedGoal") 
    public boolean removeGoal(@WebParam(name="personId") int id, @WebParam(name="goalId") int gid);
    
    @WebMethod(operationName="readPersonTimelines")
    @WebResult(name="personTimelines") 
    public List<Timeline> readPersonTimelines(@WebParam(name="personId") int id);
    
    @WebMethod(operationName="readSingleTimeline")
    @WebResult(name="singleTimeline") 
    public Timeline readSingleTimeline(@WebParam(name="personId") int id, @WebParam(name="timelineId") int timelineId);
    
    @WebMethod(operationName="createPersonTimeline")
    @WebResult(name="newTimeline") 
    public Timeline createPersonTimeline(@WebParam(name="personId") int id, @WebParam(name="timeline") Timeline tl);
    
    @WebMethod(operationName="removeTimeline")
    @WebResult(name="deletedTimeline") 
    public boolean removeTimeline(@WebParam(name="timelineId") int id);
    
    @WebMethod(operationName="readGoalTypes")
    @WebResult(name="goaltypes") 
    public List<GoalType> readGoalTypes();
    
}
