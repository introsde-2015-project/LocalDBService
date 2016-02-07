package introsde.project.model;

import introsde.project.dao.LifeCoachDao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlValue;


/**
 * The persistent class for the "GoalType" database table.
 * 
 */
@Entity
@Cacheable(false)
@Table(name="GoalType")
@NamedQueries({
	  @NamedQuery(name="GoalType.findAll", query="SELECT gt FROM GoalType gt"),
	  @NamedQuery(name="GoalType.findByName", query="SELECT gt FROM GoalType gt WHERE gt.goalName = :name")
	})

/**
 * Class for handling GoalType database table
 * @author Toomas
 *
 */
public class GoalType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="sqlite_goaltype")
	@TableGenerator(name="sqlite_goaltype", table="sqlite_sequence",
	    pkColumnName="name", valueColumnName="seq",
	    pkColumnValue="GoalType")
	@Column(name="idGoalType")
	private int idGoalType;

	@Column(name="goalName")
	private String goalName;
	
	@Column(name="goalCheck")
	private String goalCheck;
	
	// Join measure to measureType with ManyToOne link
	@ManyToOne
	@JoinColumn(name="idMeasureType",referencedColumnName="idMeasureType")
	private MeasureType measureType;

	// Getters
	@XmlTransient
	public int getIdGoalType() {
		return this.idGoalType;
	}
	
	@XmlValue
	public String getGoalName() {
		return this.goalName;
	}
	
	@XmlTransient
	public String getGoalCheck() {
		return this.goalCheck;
	}
	
	@XmlTransient
	public MeasureType getMeasureType() {
		return measureType;
	}

	// Setters
	public void setIdGoalType(int idGoalType) {
		this.idGoalType = idGoalType;
	}

	public void setGoalName(String goalName) {
		this.goalName = goalName;
	}
	
	public void setGoalCheck(String goalCheck) {
		this.goalCheck = goalCheck;
	}
	
	public void setMeasureType(MeasureType measureType) {
		if (measureType.getIdMeasureType() == 0 ) {
		    measureType = MeasureType.getByName(measureType.getMeasureName());
		    this.measureType = measureType;	
		}
	}
	
	public static GoalType getByName(String goalName) {
	    EntityManager em = LifeCoachDao.instance.createEntityManager();
	    GoalType goalType = em.createNamedQuery("GoalType.findByName", GoalType.class)
	      .setParameter("name", goalName)
	      .getSingleResult();
	    LifeCoachDao.instance.closeConnections(em);
	    return goalType;
	  }

	// Database operations
	public static GoalType getGoalTypeById(int goalTypeId) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		GoalType gt = em.find(GoalType.class, goalTypeId);
		LifeCoachDao.instance.closeConnections(em);
		return gt;
	}
	
	public static List<GoalType> getAll() {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
	    List<GoalType> list = em.createNamedQuery("GoalType.findAll", GoalType.class).getResultList();
	    LifeCoachDao.instance.closeConnections(em);
	    return list;
	}
	
	public static GoalType saveGoalType(GoalType gt) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(gt);
		tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	    return gt;
	}
	
	public static GoalType updateGoalType(GoalType gt) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		gt=em.merge(gt);
		tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	    return gt;
	}
	
	public static void removeGoalType(GoalType gt) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
	    gt=em.merge(gt);
	    em.remove(gt);
	    tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	}
}
