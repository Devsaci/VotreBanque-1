package org.sid;

import java.util.Date;

import org.sid.dao.ClientRepository;
import org.sid.dao.CompteRepository;
import org.sid.dao.Operationrepository;

import org.sid.entities.Client;
import org.sid.entities.Compte;
import org.sid.entities.CompteCourant;
import org.sid.entities.CompteEpargne;
import org.sid.entities.Retrait;
import org.sid.entities.Verssement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class VotreBanque1Application implements CommandLineRunner{

	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private CompteRepository compteRepository;
	@Autowired
	private Operationrepository operationRepository;
	
	
	public static void main(String[] args) {
		SpringApplication.run(VotreBanque1Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Client c1=clientRepository.save(new Client("client1","client1@gmail.com"));
		Client c2=clientRepository.save(new Client("client2","client2@gmail.com"));
		Client c3=clientRepository.save(new Client("client3","client3@gmail.com"));
		Client c4=clientRepository.save(new Client("client4","client4@gmail.com"));
		
		Compte cp1 = compteRepository.save(new CompteCourant("c1", new Date(), 9000, c1,6000));
		Compte cp2 = compteRepository.save(new CompteEpargne("c2", new Date(), 6000, c2,5.5));
		
		
		operationRepository.save(new Verssement(new Date(),9000,cp1));
		operationRepository.save(new Verssement(new Date(),6000,cp1));
		operationRepository.save(new Verssement(new Date(),2300,cp1));
		operationRepository.save(new Retrait(new Date(),9000,cp1));
		
		operationRepository.save(new Verssement(new Date(),2300,cp2));
		operationRepository.save(new Verssement(new Date(),400,cp2));
		operationRepository.save(new Verssement(new Date(),2300,cp2));
		operationRepository.save(new Retrait(new Date(),3000,cp2));
		
		
		
		
		
	}

}
