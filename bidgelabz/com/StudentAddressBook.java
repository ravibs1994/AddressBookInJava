package bidgelabz.com;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class StudentAddressBook {
	Scanner sc = new Scanner(System.in);
	public void insert() {
		String studentFirstName = null, studentLastName = null, zipcode = null, mobNo = null;
		String firstName = "^[A-Z]{1}[a-z]{2,}$";
		String lastName = "^[A-Z]{1}[a-z]{2,}$";
		String city = "";
		String state = "";
		String zip = "^[1-9]{1}[0-9]{2}[ ]{0,1}[0-9]{3}$";
		String mob = "^(0/91)?[7-9][0-9]{9}$";
		Student s1 = new Student();
		Address address = new Address();
		System.out.println("Enter StudentFirst name ");
		studentFirstName = sc.next();
		Pattern pat1 = Pattern.compile(firstName);
		Matcher match1 = pat1.matcher(studentFirstName);
		if (match1.matches()) {
			System.out.println("Success");
			s1.setStudentName(studentFirstName);
		} else
			System.out.printf("\nMinimum 3 Chracter,atleast 1 Uppercase\n" + firstName);
		System.out.println("Enter StudentLast name ");
		studentLastName = sc.next();
		Pattern pat4 = Pattern.compile(lastName);
		Matcher match4 = pat4.matcher(studentLastName);
		if (match4.matches()) {
			System.out.println("Success");
			s1.setStudentLastName(studentLastName);
		} else
			System.out.printf("\nMinimum 3 Chracter,atleast 1 Uppercase\n" + lastName);
		System.out.println("Enter Student City ");
		city = sc.next();
		System.out.println("Enter Student state ");
		state = sc.next();

		System.out.println("Enter Student zip ");
		zipcode = sc.next();
		Pattern pat2 = Pattern.compile(zip);
		Matcher match2 = pat2.matcher(zipcode);
		if (match2.matches()) {
			System.out.println("Success");
			address.setZip(zipcode);
		} else
			System.out.printf("\nMinimum 6 digit number \n" + zip);
		address.setCity(city);
		address.setState(state);
		s1.setAddress(address);
		System.out.println("Enter Student Mob Number ");
		mobNo = sc.next();
		Pattern pat3 = Pattern.compile(mob);
		Matcher match3 = pat3.matcher(mobNo);
		if (match3.matches()) {
			System.out.println("Success");
			s1.setMob(mobNo);
		} else
			System.out.printf("\nMinimum 10 digit Number \n" + mob);
		BufferedWriter bufferedWriter = null;
		try {

			// String strContent = " ";
			File myFile = new File("D:\\Bridgelabs\\Week4Java\\AddressBookInFile\\AddressBook.csv");
			// check if file exist, otherwise create the file before writing
			if (myFile.exists()) {
				System.out.println("File Exist");
			} else {
				myFile.createNewFile();
			}
			String loc = myFile.getCanonicalPath() + File.separator;
			FileWriter fstream = new FileWriter(loc, true);
			bufferedWriter = new BufferedWriter(fstream);
			bufferedWriter.write(s1.getStudentName());
			bufferedWriter.write(",");
			bufferedWriter.write(s1.getStudentLastName());
			bufferedWriter.write(",");
			bufferedWriter.write(s1.address.getCity());
			bufferedWriter.write(",");
			bufferedWriter.write(s1.address.getState());
			bufferedWriter.write(",");
			bufferedWriter.write(s1.address.getZip());
			bufferedWriter.write(",");
			bufferedWriter.write(s1.getMob());
			bufferedWriter.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferedWriter != null)
					bufferedWriter.close();
			} catch (Exception ex) {
			}
		}
		System.out.println("Content Added");

	}

	public void delete() throws IOException {
		File inputFile = new File("D:\\Bridgelabs\\Week4Java\\AddressBookInFile\\AddressBook.csv");
		File tempFile = new File("D:\\Bridgelabs\\Week4Java\\AddressBookInFile\\myTempFile.csv");

		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

		String lineToRemove = "Jean,author,Java";
		String currentLine;

		while ((currentLine = reader.readLine()) != null) {
			// trim newline when comparing with lineToRemove
			String trimmedLine = currentLine.trim();
			System.out.println(trimmedLine);
			if (trimmedLine.equals(lineToRemove))
				continue;
			writer.write(currentLine + System.getProperty("line.separator"));
			System.out.println(currentLine + System.getProperty("line.separator"));
		}
		writer.close();
		reader.close();
		boolean successful = tempFile.renameTo((inputFile));
		System.out.println(successful);

	}

	public void search() throws IOException {
		String resultRow = null;
		String search = "";
		int columIndex = 0;
		BufferedReader br1 = new BufferedReader(
				new FileReader("D:\\Bridgelabs\\Week4Java\\AddressBookInFile/AddressBook.csv"));
		String line;
		System.out.println("Enter Which Content You want to Search");
		search = sc.next();
		System.out.println("Enter Column Index");
		columIndex = sc.nextInt();
		while ((line = br1.readLine()) != null) {
			String[] values = line.split(",");
			if (values[columIndex].equals(search)) {
				resultRow = line;
				System.out.println("1");
				break;
			}
		}
		br1.close();
		if (resultRow == null) {
			System.out.println("Search Content Not Found ");
		} else {
			System.out.println(resultRow);
		}
	}

	public void View() {
		BufferedReader br = null;
		String strLine = "";
		try {
			br = new BufferedReader(new FileReader("D:\\Bridgelabs\\Week4Java\\AddressBookInFile/AddressBook.csv"));
			while ((strLine = br.readLine()) != null) {
				System.out.println(strLine);
			}
		} catch (FileNotFoundException e) {
			System.err.println("Unable to find the file: fileName");
		} catch (IOException e) {
			System.err.println("Unable to read the file: fileName");
		}
	}

	public static void main(String[] args) throws IOException {
		Scanner sc1 = new Scanner(System.in);
		StudentAddressBook book = new StudentAddressBook();
		int choice = 0;
		System.out.println("Choice ");
		System.out.println("1:Add ,2:Search, 3:Delete,4:view All");
		System.out.println("Enter Your Choice ");
		choice = sc1.nextInt();
		switch (choice) {
		// Add record to the file
		case 1:
			book.insert();
			break;
		// Search The Record From File
		case 2:
			book.search();
			break;
		// Delete The Record From File
		case 3:
			book.delete();

			break;
		// View Records from File
		case 4:
			book.View();

			break;
		default:
			System.out.println("Plz Enter Valid Choise");
			break;
		}

	}
}