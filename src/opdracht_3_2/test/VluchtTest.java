package opdracht_3_2.test;

import opdracht_3_2.main.domeinLaag.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class VluchtTest {

	static LuchtvaartMaatschappij lvm ;
	static Fabrikant f1; 
	static VliegtuigType vtt1; 
	static Vliegtuig vt1;
	static Luchthaven lh1, lh2;
	static Vlucht vl1, vl2; 

	@BeforeEach
	public void initialize() {
		try {
			lvm = new LuchtvaartMaatschappij("NLM");
			f1 = new Fabrikant("Airbus","G. Dejenelle");
			vtt1 = f1.creeervliegtuigtype("A-200", 140);
			Calendar datum = Calendar.getInstance();
			datum.set(2000, 01, 01);
			vt1 = new Vliegtuig(lvm, vtt1, "Luchtbus 100", datum);
			Land l1 = new Land("Nederland", 31);
			Land l2 = new Land("België", 32);
			lh1 = new Luchthaven("Schiphol", "ASD", true, l1);
			lh2 = new Luchthaven("Tegel", "TEG", true, l2);
			Calendar vertr = Calendar.getInstance();
			vertr.set(2020, 03, 30, 14, 15, 0);
			Calendar aank = Calendar.getInstance();
			aank.set(2020, 03, 30, 15, 15, 0);
			vl1 = new Vlucht(vt1, lh1, lh2, vertr, aank );
			vertr.set(2020, 4, 1, 8, 15, 0);
			aank.set(2020, 4, 1, 9, 15, 0);
			vl2 = new Vlucht(vt1, lh1, lh2, vertr, aank );
		} catch (Exception e){
			String errorMessage =  "Exception: " + e.getMessage();
			System.out.println(errorMessage); 
		}
	}

	/**
	 * Business rule:
	 * De bestemming moet verschillen van het vertrekpunt van de vlucht.
	 */

	@Test
	public void testVliegtuigNotNull() {
		Vlucht vlucht = new Vlucht();
		Calendar a1 = Calendar.getInstance();
		a1.add(Calendar.DATE, 1); // 1 Dag later
		try {
			//vlucht.zetVliegtuig(vt1);
			vlucht.zetVertrekpunt(lh1);
			vlucht.zetBestemming(lh2);
			vlucht.zetVertrekTijd(Calendar.getInstance());
			vlucht.zetAankomstTijd(a1);
			vlucht.bewaar();
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			assertTrue(e.getMessage().contains("Geen geldige vliegtuig"));
		}
	}

	@Test
	public void testBestemmingNotNull() {
		Vlucht vlucht = new Vlucht();
		Calendar a1 = Calendar.getInstance();
		a1.add(Calendar.DATE, 1); // 1 Dag later
		try {
			vlucht.zetVliegtuig(vt1);
			vlucht.zetVertrekpunt(lh1);
			//vlucht.zetBestemming(lh2);
			vlucht.zetVertrekTijd(Calendar.getInstance());
			vlucht.zetAankomstTijd(a1);
			vlucht.bewaar();
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			assertTrue(e.getMessage().contains("Geen geldige bestemming"));
		}
	}

	@Test
	public void testAankomsttijdNotNull() {
		Vlucht vlucht = new Vlucht();
		Calendar a1 = Calendar.getInstance();
		a1.add(Calendar.DATE, 1); // 1 Dag later
		try {
			vlucht.zetVliegtuig(vt1);
			vlucht.zetVertrekpunt(lh1);
			vlucht.zetBestemming(lh2);
			vlucht.zetVertrekTijd(Calendar.getInstance());
			//vlucht.zetAankomstTijd(a1);
			vlucht.bewaar();
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			assertTrue(e.getMessage().contains("Geen geldige aankomsttijd"));
		}
	}

	@Test
	public void testVertrektijdNotNull() {
		Vlucht vlucht = new Vlucht();
		Calendar a1 = Calendar.getInstance();
		a1.add(Calendar.DATE, 1); // 1 Dag later
		try {
			vlucht.zetVliegtuig(vt1);
			vlucht.zetVertrekpunt(lh1);
			vlucht.zetBestemming(lh2);
			//vlucht.zetVertrekTijd(Calendar.getInstance());
			vlucht.zetAankomstTijd(a1);
			vlucht.bewaar();
		}
		catch(Exception e) {
			/*Dit is grappig want dit betekent dat deze exception in bewaar nooit wordt
			* gethrowd, omdat het onmogelijk geen lege aankomsttijd te hebben, maar wel een
			* lege vertrektijd, want je kan geen aankomsttijd setten zonder vertrektijd*/
			System.out.println(e.getMessage());
			assertTrue(e.getMessage().contains("Geen geldige vertrektijd"));
		}
	}
	
	@Test
	public void testBestemmingMagNietGelijkZijnAanVertrek_False() {
		Vlucht vlucht = new Vlucht();
		try {
			vlucht.zetVliegtuig(vt1);
			vlucht.zetVertrekpunt(lh1);
			vlucht.zetBestemming(lh1);

			Luchthaven bestemming = vlucht.getBestemming();
			assertTrue(bestemming == null);
		}
		catch(IllegalArgumentException e) {
			Luchthaven bestemming = vlucht.getBestemming();
			assertNotEquals(bestemming, lh1);
		}
	}


	@Test
	public void testVluchtVliegtuigBezet() {
		Vlucht vlucht = new Vlucht();
		Vlucht vlucht2 = new Vlucht();

		Calendar v1 = Calendar.getInstance();
		Calendar a1 = Calendar.getInstance();
		a1.add(Calendar.DATE, 1); // 1 Dag later

		Calendar v2 = Calendar.getInstance();
		v2.add(Calendar.HOUR, 1); // 1 Uur later

		try {

			//1
			vlucht.zetVliegtuig(vt1);
			vlucht.zetVertrekpunt(lh1);
			vlucht.zetBestemming(lh2);
			vlucht.zetVertrekTijd(v1);
			vlucht.zetAankomstTijd(a1);
			vlucht.bewaar();

			//2
			vlucht2.zetVliegtuig(vt1);
			vlucht2.zetVertrekpunt(lh1);
			vlucht2.zetBestemming(lh2);
			vlucht2.zetVertrekTijd(v2);

			fail( "Geen Bezet Exception" );

		} catch (Exception e){
			String errorMessage =  "Exception: " + e.getMessage();
			System.out.println(errorMessage);
		}
	}

	@Test
	public void testVluchtVliegtuigNietBezet() {
		Vlucht vlucht = new Vlucht();
		Vlucht vlucht2 = new Vlucht();

		try {

			//1
			vlucht.zetVliegtuig(vt1);
			vlucht.zetVertrekpunt(lh1);
			vlucht.zetBestemming(lh2);
			vlucht.zetVertrekTijd(Calendar.getInstance());
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, 1); // 1 Dag later
			vlucht.zetAankomstTijd(calendar);
			vlucht.bewaar();

			//2
			vlucht2.zetVliegtuig(vt1);
			vlucht2.zetVertrekpunt(lh1);
			vlucht2.zetBestemming(lh2);
			vlucht2.zetVertrekTijd(calendar); //Calendar 1 dag later

		} catch (Exception e){
			fail( "Bezet Exception" );
			e.printStackTrace();
		}
	}

	@Test
	public void testAankomstTijdVoorVertrekTijd() {
		Vlucht vlucht2 = new Vlucht();

		try {
			vlucht2.zetVliegtuig(vt1);
			vlucht2.zetVertrekpunt(lh1);
			vlucht2.zetBestemming(lh2);

			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, 1); // 1 Dag later

			vlucht2.zetAankomstTijd(Calendar.getInstance());
			vlucht2.zetVertrekTijd(calendar);

			fail( "Geen Vlucht Exception" );

		} catch (Exception e){
			String errorMessage =  "Exception: " + e.getMessage();
			System.out.println(errorMessage);

		}
	}

	@Test
	public void testBestemmingMagNietGelijkZijnAanVertrek_True() {
		Vlucht vlucht = new Vlucht();
		Luchthaven bestemming;
		try {
			vlucht.zetVliegtuig(vt1);
			vlucht.zetVertrekpunt(lh2);
			bestemming = vlucht.getBestemming();
			assertTrue(bestemming == null);
			vlucht.zetBestemming(lh1);
			bestemming = vlucht.getBestemming();
			assertTrue(bestemming.equals(lh1));
		}
		catch(IllegalArgumentException e) {
			bestemming = vlucht.getBestemming();
			assertTrue(bestemming.equals(lh1));
		}
	}

	/**
	 * Business rule:
	 * xxx
	 */
	
		
}
