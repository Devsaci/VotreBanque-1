package org.sid.metier;


import java.util.Date;

import org.sid.dao.CompteRepository;
import org.sid.dao.Operationrepository;

import org.sid.entities.Compte;
import org.sid.entities.CompteCourant;
import org.sid.entities.Operation;
import org.sid.entities.Retrait;
import org.sid.entities.Verssement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;





@Service
/* 
 * @Service Annotation reserver au objet de la couche metier
 * Spring instantie cette classe au demarage
 */  
@Transactional
/* Spring gere les transaction de la couche metrier
 * @Transactional : Toutes les methodes sont transactionnelle
 */
public class BanqueMetierImpl implements IBanqueMetier{
	@Autowired
	private CompteRepository compteRepository;
	@Autowired
	private Operationrepository operationrepository;
	
	
	@Override
	public Compte consulterCompte(String codeCpte) {
		
		Compte cp =compteRepository.findById(codeCpte).orElse(null);
		if (cp == null) {
			throw new RuntimeException("compte introuvable");
		}
		return cp;
	}

	@Override
	public void verser(String codeCpte, double montant) {
		Compte cp=consulterCompte(codeCpte);
		Verssement v  = new Verssement(new Date(), montant, cp);
		operationrepository.save(v);
		cp.setSolde(cp.getSolde() + montant);
		compteRepository.save(cp);
	}

	@Override
	public void retirer(String codeCpte, double montant) {
		Compte cp = consulterCompte(codeCpte);
		double facilitesCaisse = 0;
		if (cp instanceof CompteCourant) {
			facilitesCaisse = ((CompteCourant) cp).getDecouvert();
		}
		if (cp.getSolde() + facilitesCaisse < montant) {
			throw new RuntimeException("Solde inssufisant");
		}
		Retrait r = new Retrait(new Date(), montant, cp);
		operationrepository.save(r);
		cp.setSolde(cp.getSolde() - montant);
		compteRepository.save(cp);
		
	}

	@Override
	public void virement(String codeCpte1, String codeCpte2, double montant) {
		retirer(codeCpte1, montant);
		verser(codeCpte2, montant);			
	}

	@Override
	public Page<Operation> listOperation(String codeCpte, int page, int size) {
		return operationrepository.listOperation(codeCpte, PageRequest.of(page, size));
	}

}
