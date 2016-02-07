package introsde.project.model;

import introsde.project.dao.LifeCoachDao;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlTransient;

/**
 * The persistent class for the Timeline database table.
 * 
 */
@Entity
@Cacheable(false)
@Table(name = "Timeline")
@NamedQueries({
	@NamedQuery(name = "Timeline.findAll", query = "SELECT tl FROM Timeline tl ORDER BY tl.timelineId DESC"),
	@NamedQuery(name = "Timeline.findAllByPersonId", query = "SELECT tl FROM Timeline tl WHERE tl.person.idPerson = :idPerson ORDER BY tl.timelineId DESC")
})

/**
 * Class for handling Timeline database table
 * @author Toomas
 *
 */
public class Timeline implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="sqlite_timeline")
	@TableGenerator(name="sqlite_timeline", table="sqlite_sequence",
	    pkColumnName="name", valueColumnName="seq",
	    pkColumnValue="Timeline")
	
	@Column(name = "idTimeline")
	private int timelineId;
	
	@Column(name = "JSONString")
	private String JSONString;
	
	@Column(name = "date")
	private String date;
	
	// Join timeline to person with ManyToOne link
	@ManyToOne
	@JoinColumn(name="idPerson",referencedColumnName="idPerson")
	private Person person;

	// Getters
	public int getTimelineId() {
		return timelineId;
	}
	
	public String getJSONString() {
		return JSONString;
	}
	
	public String getDate() {
		return date;
	}
	
	@XmlTransient
	public Person getPerson() {
		return person;
	}
	
	// Setters
	
	public void setTimelineId(int id) {
		this.timelineId = id;
	}
	
	public void setJSONString(String JSONString) {
		this.JSONString = JSONString;
	}
	
	public void setDate(String date) {
		this.date = date;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	
	// Database operations
	public static Timeline getTimelineById(int timelineId) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		Timeline tl = em.find(Timeline.class, timelineId);
		LifeCoachDao.instance.closeConnections(em);
		return tl;
	}
	
	public static List<Timeline> getAll() {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
	    List<Timeline> list = em.createNamedQuery("Timeline.findAll", Timeline.class).getResultList();
	    LifeCoachDao.instance.closeConnections(em);
	    return list;
	}
	
	public static List<Timeline> getAllByPersonId(int id) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		@SuppressWarnings("unchecked")
		List<Timeline> timelineList = em.createNamedQuery("Timeline.findAllByPersonId")
    	        .setParameter("idPerson", id)
    	        .getResultList();
	    LifeCoachDao.instance.closeConnections(em);
	    return timelineList;
	}
	
	public static Timeline saveTimeline(Timeline tl) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(tl);
		tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	    return tl;
	}
	
	public static Timeline updateTimeline(Timeline tl) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		tl=em.merge(tl);
		tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	    return tl;
	}
	
	public static void removeTimeline(Timeline tl) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
	    tl=em.merge(tl);
	    em.remove(tl);
	    tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	}
}
