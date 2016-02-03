package introsde.project.model;

import introsde.project.dao.LifeCoachDao;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * The persistent class for the "Measure" database table.
 * 
 */
@XmlType(name = "Goal", propOrder = {
	    "gid",
	    "measureType",
	    "goalType",
	    "value",
	    "created",
	    "end"
	})
@Entity
@Cacheable(false)
@Table(name = "Goal")
@NamedQueries({
	@NamedQuery(name = "Goal.findAll", query = "SELECT g FROM Goal g"),
	@NamedQuery(name = "Goal.findAllByPersonId", query = "SELECT g FROM Goal g WHERE g.person.idPerson = :idPerson"),
	@NamedQuery(name="Goal.findAllByIdAndMeasureType", query="SELECT g FROM Goal g, MeasureType mt WHERE g.person.idPerson = :idPerson AND g.measureType.idMeasureType = mt.idMeasureType AND mt.measureName = :measureType")
})

/**
 * Class for handling Goal database table
 * @author Toomas
 *
 */
public class Goal implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="sqlite_goal")
	@TableGenerator(name="sqlite_goal", table="sqlite_sequence",
	    pkColumnName="name", valueColumnName="seq",
	    pkColumnValue="Goal")
	
	@Column(name = "idGoal")
	private int gid;
	
	@Column(name = "value")
	private double value;
	
	@Column(name = "created")
	private String created;
	
	@Column(name = "end")
	private String end;
	
	// Join goal to person with ManyToOne link
	@ManyToOne
	@JoinColumn(name="idPerson",referencedColumnName="idPerson")
	private Person person;
	
	// Join goal to measureType with ManyToOne link
	@ManyToOne
	@JoinColumn(name="idMeasureType",referencedColumnName="idMeasureType")
	private MeasureType measureType;
	
	// Join goal to goalType with ManyToOne link
	@ManyToOne
	@JoinColumn(name="idGoalType",referencedColumnName="idGoalType")
	private GoalType goalType;

	// Getters
	public int getGid() {
		return gid;
	}
	
	@XmlElement(name="measureName")
	public MeasureType getMeasureType() {
		return measureType;
	}
	
	public double getValue() {
		return value;
	}
	
	public String getCreated() {
		return created;
	}
	
	public String getEnd() {
		return end;
	}
	
	@XmlTransient
	public Person getPerson() {
		return person;
	}
	
	@XmlElement(name="goalName")
	public GoalType getGoalType() {
		return goalType;
	}
	
	// Setters
	
	public void setGid(int gid) {
		this.gid = gid;
	}
	
	public void setMeasureType(MeasureType measureType) {
		if (measureType.getIdMeasureType() == 0 ) {
		    measureType = MeasureType.getByName(measureType.getMeasureName());
		    this.measureType = measureType;	
		}
	}
	
	public void setGoalType(GoalType goalType) {
		if (goalType.getIdGoalType() == 0 ) {
		    goalType = GoalType.getByName(goalType.getGoalName());
		    this.goalType = goalType;	
		}
	}
	
	public void setValue(double value) {
		this.value = value;
	}
	
	public void setCreated(String created) {
		this.created = created;
	}
	
	public void setEnd(String end) {
		this.end = end;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	
	// Database operations
	public static Goal getGoalById(int goalId) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		Goal g = em.find(Goal.class, goalId);
		LifeCoachDao.instance.closeConnections(em);
		return g;
	}
	
	public static List<Goal> getAll() {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
	    List<Goal> list = em.createNamedQuery("Goal.findAll", Goal.class).getResultList();
	    LifeCoachDao.instance.closeConnections(em);
	    return list;
	}
	
	public static List<Goal> getAllByPersonId(int id) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		@SuppressWarnings("unchecked")
		List<Goal> goalList = em.createNamedQuery("Goal.findAllByPersonId")
    	        .setParameter("idPerson", id)
    	        .getResultList();
	    LifeCoachDao.instance.closeConnections(em);
	    return goalList;
	}
	
	public static List<Goal> getAllByIdAndType(int id, String measureType) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
	    @SuppressWarnings("unchecked")
		List<Goal> goalList = em.createNamedQuery("Goal.findAllByIdAndMeasureType")
    	        .setParameter("idPerson", id)
    	        .setParameter("measureType", measureType)
    	        .getResultList();
	    LifeCoachDao.instance.closeConnections(em);
	    return goalList;
	}
	
	public static Goal saveGoal(Goal g) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(g);
		tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	    return g;
	}
	
	public static Goal updateGoal(Goal g) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		g=em.merge(g);
		tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	    return g;
	}
	
	public static void removeGoal(Goal g) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
	    g=em.merge(g);
	    em.remove(g);
	    tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	}
}
