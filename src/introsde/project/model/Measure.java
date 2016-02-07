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
@XmlType(name = "Measure", propOrder = {
	    "mid",
	    "date",
	    "measureType",
	    "value"
	})
@Entity
@Cacheable(false)
@Table(name = "Measure")
@NamedQueries({
	@NamedQuery(name = "Measure.findAll", query = "SELECT m FROM Measure m"),
	@NamedQuery(name="Measure.findAllByIdAndMeasureType", query="SELECT m FROM Measure m, MeasureType mt WHERE m.person.idPerson = :idPerson AND m.measureType.idMeasureType = mt.idMeasureType AND mt.measureName = :measureType")
})

/**
 * Class for handling Measure database table
 * @author Toomas
 *
 */
public class Measure implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="sqlite_measure")
	@TableGenerator(name="sqlite_measure", table="sqlite_sequence",
	    pkColumnName="name", valueColumnName="seq",
	    pkColumnValue="Measure")
	
	@Column(name = "idMeasure")
	private int mid;

	@Column(name = "value")
	private double value;
	
	@Column(name = "date")
	private String date;
	
	// Join measure to person with ManyToOne link
	@ManyToOne
	@JoinColumn(name="idPerson",referencedColumnName="idPerson")
	private Person person;
	
	// Join measure to healthProfile with ManyToOne link
	@ManyToOne
	@JoinColumn(name="idHealthProfile",referencedColumnName="idHealthProfile")
	private HealthProfile healthProfile;
	
	// Join measure to measureType with ManyToOne link
	@ManyToOne
	@JoinColumn(name="idMeasureType",referencedColumnName="idMeasureType")
	private MeasureType measureType;

	// Getters
	public int getMid() {
		return mid;
	}
	
	@XmlElement(name="measureName")
	public MeasureType getMeasureType() {
		return measureType;
	}
	
	public double getValue() {
		return value;
	}
	
	public String getDate() {
		return date;
	}
	
	@XmlTransient
	public Person getPerson() {
		return person;
	}
	
	@XmlTransient
	public HealthProfile getHealthProfile() {
		return healthProfile;
	}
	
	// Setters
	
	public void setMid(int mid) {
		this.mid = mid;
	}
	
	public void setMeasureType(MeasureType measureType) {
		if (measureType.getIdMeasureType() == 0 ) {
		    measureType = MeasureType.getByName(measureType.getMeasureName());
		}
		this.measureType = measureType;	
	}

	public void setValue(double value) {
		this.value = value;
	}
	
	public void setDate(String date) {
		this.date = date;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public void setHealthProfile(HealthProfile healthProfile) {
		this.healthProfile = healthProfile;
	}
	
	// Database operations
	public static Measure getMeasureById(int measureId) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		Measure m = em.find(Measure.class, measureId);
		LifeCoachDao.instance.closeConnections(em);
		return m;
	}
	
	public static List<Measure> getAll() {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
	    List<Measure> list = em.createNamedQuery("Measure.findAll", Measure.class).getResultList();
	    LifeCoachDao.instance.closeConnections(em);
	    return list;
	}
	
	public static List<Measure> getMeasureHistory(int id, String measureType, String beforeDate, String afterDate) {
        
		EntityManager em = LifeCoachDao.instance.createEntityManager();
	    @SuppressWarnings("unchecked")
		List<Measure> measureList = em.createNamedQuery("Measure.findAllByIdAndMeasureType")
    	        .setParameter("idPerson", id)
    	        .setParameter("measureType", measureType)
    	        .getResultList();
	    List<Measure> measureHistory = new ArrayList<Measure>();
	    LifeCoachDao.instance.closeConnections(em);
	    
	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
	    
	    for (Measure measure: measureList) {
			// If queryParams presented, check that measure date fits the query params
			try {
				if (beforeDate == null || (beforeDate != null && df.parse(measure.getDate()).before(df.parse(beforeDate)))) {
					if (afterDate == null || (afterDate != null && df.parse(measure.getDate()).after(df.parse(afterDate)))) {
						// If measure passes checks, add to list
						measureHistory.add(measure);
					}
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
	    }
	    return measureHistory;
	}
	
	public static Measure saveMeasure(Measure m) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(m);
		tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	    return m;
	}
	
	public static Measure updateMeasure(Measure m) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		m=em.merge(m);
		tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	    return m;
	}
	
	public static void removeMeasure(Measure m) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
	    m=em.merge(m);
	    em.remove(m);
	    tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	}
}
