import java.util.ArrayList;
import java.util.Scanner;

public class RoundOne {

	private static ArrayList<Person> people;
	private static ArrayList<Integer> solutions; 

	public RoundOne() {
		people = new ArrayList<Person>();
		solutions = new ArrayList<Integer>();
	}

	public static ArrayList<Person> setUpPeople() {
		people = new ArrayList<Person>();
		
		Person placeHolder = new Person(null, null, 0, null, false, null, null, null);

		ArrayList<Restriction> r = new ArrayList();
		r.add(Restriction.Want_Head);
		Person a = new Person("Abba", "Levi", 30, Gender.MALE, true, r, null, placeHolder);

		
		r.clear();
		Person b = new Person("Ima", "Levi", 29, Gender.FEMALE, true, null, null, a);
		a.setSpouse(b);

		r.clear();
		r.add(Restriction.Not_Window);
		Person c = new Person("Mrs.", "Kar", 29, Gender.FEMALE, true, r, null, placeHolder);

		r.clear();
		r.add(Restriction.See_Abba);
		Person d = new Person("Mr.", "Kar", 32, Gender.MALE, true, r, null, c);
		d.setSpouse(c);

		r.clear();
		Person e = new Person("Mrs.", "Stein", 35, Gender.FEMALE, true, null, null, placeHolder);

		r.clear();
		Person f = new Person("Dr.", "Stein", 35, Gender.MALE, true, null, null, e);
		f.setSpouse(e);

		r.clear();
		Person g = new Person("Shloimy", "Stein", 4, Gender.MALE, false, null, null, null);

		r.clear();
		r.add(Restriction.Bro_Fight);
		Person h = new Person("Yitzy", "Stein", 8, Gender.MALE, false, r, null, null);

		people.add(a);
		people.add(b);
		people.add(c);
		people.add(d);
		people.add(e);
		people.add(f);
		people.add(g);
		people.add(h);

		return people;
	}

	public String displayPeople() {
		StringBuffer s = new StringBuffer();
		for (Person p : people) {
			s.append(p.toString());
			s.append("\n");
		}
		return s.toString();
	}

	public static ArrayList<Integer> solutions(){
		solutions = new ArrayList<Integer>();
		solutions.add(12756834);
		solutions.add(12584376);
		solutions.add(16752834);
		solutions.add(12856734);
		solutions.add(12574386);
		solutions.add(16852734);
		
		return solutions;
	}
	
	public static String displayTable(){
		StringBuilder s = new StringBuilder();
		s.append("The table looks like this:");
		s.append("\n	Windows");
		s.append("\n	__ __ __");
		s.append("\n      __	__");
		s.append("\n	__ __ __");
		
		s.append("\nEnter the person # (Ex: Abba is 1) for each seat starting on the left (the head) and move clockwise. Do not put a space between numbers");
		
		return s.toString();
	}
	public static boolean checkSolution() {
		Scanner i = new Scanner(System.in);
		Integer answer = i.nextInt(); //autoboxing
		for(Integer num : solutions){
			if(answer.equals(num)){
				return true;
			}
		}
		return false;
			}
}
