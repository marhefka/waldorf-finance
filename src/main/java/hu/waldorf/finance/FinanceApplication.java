package hu.waldorf.finance;

import hu.waldorf.finance.import_.Befizetes;
import hu.waldorf.finance.import_.BefizetesRepository;
import hu.waldorf.finance.import_.FeldolgozasStatusza;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class FinanceApplication implements CommandLineRunner {
	@Autowired
	private BefizetesRepository befizetesRepository;

	public static void main(String[] args) {
		SpringApplication.run(FinanceApplication.class, args);
	}

	@Override
	public void run(String... args) {
		feldolgozo();
	}

	public void feldolgozo() {
		while (true) {
			List<Befizetes> befizetesek = befizetesRepository.findNemFeldolgozottak();
			System.out.println("Feldolgozandó befizetések: " + befizetesek.size());
			System.out.println();

			if (befizetesek.isEmpty()) {
				System.out.println("KÉSZ!");
				return;
			}

			Befizetes befizetes = befizetesek.get(0);
			print(befizetes);

			System.out.println("> ");

			Scanner scanner = new Scanner(System.in);
			String utasitas = scanner.nextLine();

			switch (utasitas.toLowerCase()) {
				case "f":
					ignoreBefizetes(befizetes);
					break;
				case "?":
					printHelp();
					break;
				default:
					System.out.println("Ismeretlen parancs. Használd a ?-t az utasítások megtekintéséhez.");
			}
		}
	}

	private void ignoreBefizetes(Befizetes befizetes) {
		befizetes.setStatusz(FeldolgozasStatusza.FIGYELMEN_KIVUL_HAGYVA);
		befizetesRepository.save(befizetes);
	}

	private void printHelp() {
		System.out.println("Segitseg");
		System.out.println("?: segitseg megjelenitese");
	}

	private void print(Befizetes befizetes) {
		System.out.println("Forrás: " + befizetes.getImportForras());
		System.out.println("Beeérkezes időpontja: " + befizetes.getKonyvelesiNap());
		System.out.println("Befizető: " + befizetes.getBefizetoNev() + " (" + befizetes.getBefizetoSzamlaszam() + ")");
		System.out.println("Összeg: " + befizetes.getOsszeg());
		System.out.println("Közlemény: " + befizetes.getKozlemeny());
		System.out.println();
	}
}
