package introsde.project.soap;
import introsde.project.model.Goal;
import introsde.project.model.GoalType;
import introsde.project.model.Measure;
import introsde.project.model.MeasureType;
import introsde.project.model.Person;
import introsde.project.model.Timeline;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.jws.WebService;

/**
 * Service implementation
 * @author Toomas
 *
 */
@WebService(endpointInterface = "introsde.project.soap.People",
    serviceName="PeopleService")
public class PeopleImpl implements People {


    @Override
    public List<Person> readPersonList() {
        return Person.getAll();
    }

    @Override
    public Person readPerson(int id) {
        return Person.getPersonById(id);
    }

    @Override
    public Person createPerson(Person person) {
        return Person.savePerson(person);
    }

    @Override
    public Person updatePerson(Person person) {
    	// Updating only person information
    	Person existing = Person.getPersonById(person.getIdPerson());
        if (person.getFirstname() == null) {
        	person.setFirstname(existing.getFirstname());
        }
        if (person.getLastname() == null) {
        	person.setLastname(existing.getLastname());
        }
        if (person.getBirthdate() == null) {
        	person.setBirthdate(existing.getBirthdate());
        }
        Person updPerson = Person.updatePerson(person);
        return updPerson;
    }

    @Override
    public boolean deletePerson(int id) {
        Person p = Person.getPersonById(id);
        if (p!=null) {
            Person.removePerson(p);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<Measure> readPersonMeasureHistory(int id, String measureType) {
        List<Measure> measures = Measure.getMeasureHistory(id, measureType, null, null);
        return measures;
    }

    @Override
    public List<MeasureType> readMeasureTypes() {
        return MeasureType.getAll();
    }

    @Override
    public Measure readPersonMeasure(int id, String measureType, int mid) {
        Measure m = Measure.getMeasureById(mid);
        // Check that measure equals the given measure type and person id
        if (m.getMeasureType().getMeasureName().equals(measureType) && m.getPerson().getIdPerson() == id) {
            return m;
        } else {
            System.out.println("Could not find measure " + measureType + " with id " + mid + " from person " + id);
            return null;
        }
    }

    @Override
    public Measure createPersonMeasure(int id, Measure measure) {
        Person person = Person.getPersonById(id);
        // Set person and healthProfile for measure
        measure.setPerson(person);
        measure.setHealthProfile(person.getHealthProfile());
        // If no created date, set the current date as measure created date
        if (measure.getDate() == null) {
            measure.setDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        }
        return Measure.saveMeasure(measure);
    }

    @Override
    public Measure updatePersonMeasure(int personId, Measure measure) {
        Person person = Person.getPersonById(personId);
        // Set person and healthProfile for measure
        measure.setPerson(person);
        measure.setHealthProfile(person.getHealthProfile());
        Measure updMeasure = Measure.updateMeasure(measure);
        return updMeasure;
    }

	@Override
	public List<Goal> readPersonGoals(int id) {
		return Goal.getAllByPersonId(id);
	}

	@Override
	public Goal createPersonGoal( Goal goal, int personId) {
		Person person = Person.getPersonById(personId);
		GoalType gt = GoalType.getByName(goal.getGoalType().getGoalName());
		MeasureType mt = gt.getMeasureType();
        // Set person and healthProfile for measure
        goal.setPerson(person);
        // If no created date, set the current date as measure created date
        goal.setDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        goal.setMeasureType(mt);
        return Goal.saveGoal(goal);
	}

	@Override
	public Goal updatePersonGoal(int personId, Goal goal) {
		Person person = Person.getPersonById(personId);
		Goal existing = Goal.getGoalById(goal.getGid());
        // Set person and healthProfile for measure
        goal.setPerson(person);
        goal.setDate(existing.getDate());
        
        if (goal.getValue() == 0) {
        	goal.setValue(existing.getValue());
        }
        if (goal.getMeasureType() == null) {
        	goal.setMeasureType(existing.getMeasureType());
        }
        if (goal.getGoalType() == null) {
        	goal.setGoalType(existing.getGoalType());
        }
        Goal updatedGoal = Goal.updateGoal(goal);
        return updatedGoal;
	}

	@Override
	public List<GoalType> readGoalTypes() {
		return GoalType.getAll();
	}

	@Override
	public List<Goal> readPersonGoalsByMeasure(int id, String measureType) {
		return Goal.getAllByIdAndType(id, measureType);
	}

	@Override
	public List<Timeline> readPersonTimelines(int id) {
		return Timeline.getAllByPersonId(id);
	}

	@Override
	public Timeline createPersonTimeline(int id, Timeline tl) {
		Person person = Person.getPersonById(id);
        // Set person and healthProfile for measure
        tl.setPerson(person);
        // If no created date, set the current date as measure created date
        if (tl.getDate() == null) {
            tl.setDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        }
        return Timeline.saveTimeline(tl);
	}

	@Override
	public boolean removeTimeline(int id) {
        Timeline tl = Timeline.getTimelineById(id);
        if (tl!=null) {
            Timeline.removeTimeline(tl);
            return true;
        } else {
            return false;
        }
	}

	@Override
	public boolean removeMeasure(int id) {
        Measure m = Measure.getMeasureById(id);
        if (m!=null) {
            Measure.removeMeasure(m);
            return true;
        } else {
            return false;
        }
	}

	@Override
	public boolean removeGoal(int personId, int gid) {
        Goal g = Goal.getGoalById(gid);
        if (g!=null) {
        	if (g.getPerson().getIdPerson() == personId) {
        		Goal.removeGoal(g);
                return true;
            }
            return false;
        } else {
            return false;
        }
	}

	@Override
	public Goal readSingleGoal(int personId, int gid) {
        Goal g = Goal.getGoalById(gid);
        // Check that measure equals the given measure type and person id
        if (g.getPerson().getIdPerson() == personId) {
            return g;
        } else {
            System.out.println("Could not find goal with id " + gid + " from person " + personId);
            return null;
        }
	}

	@Override
	public Timeline readSingleTimeline(int id, int timelineId) {
        Timeline tl = Timeline.getTimelineById(timelineId);
        // Check that measure equals the given measure type and person id
        if (tl.getPerson().getIdPerson() == id) {
            return tl;
        } else {
            System.out.println("Could not find timeline with id" + timelineId + " from person " + id);
            return null;
        }
	}




}