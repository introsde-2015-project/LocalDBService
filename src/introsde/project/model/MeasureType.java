package introsde.project.model;

import introsde.project.dao.LifeCoachDao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlValue;


/**
 * The persistent class for the "MeasureType" database table.
 * 
 */
@Entity
@Cacheable(false)
@Table(name="MeasureType")
@NamedQueries({
	  @NamedQuery(name="MeasureType.findAll", query="SELECT mt FROM MeasureType mt"),
	  @NamedQuery(name="MeasureType.findByName", query="SELECT mt FROM MeasureType mt WHERE mt.measureName = :name")
	})

/**
 * Class for handling MeasureType database table
 * @author Toomas
 *
 */
public class MeasureType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="sqlite_measuretype")
	@TableGenerator(name="sqlite_measuretype", table="sqlite_sequence",
	    pkColumnName="name", valueColumnName="seq",
	    pkColumnValue="MeasureType")
	@Column(name="idMeasureType")
	private int idMeasureType;

	@Column(name="measureName")
	private String measureName;

	// Getters
	@XmlTransient
	public int getIdMeasureType() {
		return this.idMeasureType;
	}
	
	@XmlValue
	public String getMeasureName() {
		return this.measureName;
	}

	// Setters
	public void setIdMeasureType(int idMeasureType) {
		this.idMeasureType = idMeasureType;
	}

	public void setMeasureName(String measureName) {
		this.measureName = measureName;
	}
	
	public static MeasureType getByName(String measureName) {
	    EntityManager em = LifeCoachDao.instance.createEntityManager();
	    MeasureType measureType = em.createNamedQuery("MeasureType.findByName", MeasureType.class)
	      .setParameter("name", measureName)
	      .getSingleResult();
	    LifeCoachDao.instance.closeConnections(em);
	    return measureType;
	  }

	// Database operations
	public static MeasureType getMeasureTypeById(int measureTypeId) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		MeasureType mt = em.find(MeasureType.class, measureTypeId);
		LifeCoachDao.instance.closeConnections(em);
		return mt;
	}
	
	public static List<MeasureType> getAll() {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
	    List<MeasureType> list = em.createNamedQuery("MeasureType.findAll", MeasureType.class).getResultList();
	    LifeCoachDao.instance.closeConnections(em);
	    return list;
	}
	
	public static MeasureType saveMeasureType(MeasureType mt) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(mt);
		tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	    return mt;
	}
	
	public static MeasureType updateMeasureType(MeasureType mt) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		mt=em.merge(mt);
		tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	    return mt;
	}
	
	public static void removeMeasureType(MeasureType mt) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
	    mt=em.merge(mt);
	    em.remove(mt);
	    tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	}
}
