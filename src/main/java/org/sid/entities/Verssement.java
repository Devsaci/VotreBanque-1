package org.sid.entities;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
@Entity
@DiscriminatorValue("V")
public class Verssement extends Operation{

	public Verssement() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Verssement(long numero, Date dateOperation, double montant, Compte compte) {
		super(numero, dateOperation, montant, compte);
		// TODO Auto-generated constructor stub
	}

	
	
	
}
