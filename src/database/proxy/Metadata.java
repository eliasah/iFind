package database.proxy;

import java.sql.Date;

/**
 * 
 * @author Abou Haydar Elias - Univ. Paris Denis Diderot
 *
 */
public class Metadata {
	private int id;
	private String 	path;
	private String format;
	private Date datecreation;
	private Date datemodification;
	private long size;
	private String proprio;
	private String groupe;
	private String permissions;	
	
	public Metadata(String path, String format, Date datecreation,
			Date datemodification, long size, String proprio, String groupe,
			String permissions) {
		super();
		this.path = path;
		this.format = format;
		this.datecreation = datecreation;
		this.datemodification = datemodification;
		this.size = size;
		this.proprio = proprio;
		this.groupe = groupe;
		this.permissions = permissions;
	}
	
	
}
