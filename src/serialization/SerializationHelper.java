package serialization;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

import model.ConferenceManager;

public class SerializationHelper {
	private static final String DATA_FOLDER = "data";
	private static final String CONFERENCE_MANAGER_FILE = "system.ser";
	private static final String USERS_FILE = "users.ser";
	
	private static final Path CONFERENCE_MANAGER_FILE_SAVE =
			Paths.get(DATA_FOLDER, CONFERENCE_MANAGER_FILE);
	
	private static final Path USERS_FILE_SAVE =
			Paths.get(DATA_FOLDER, USERS_FILE);

	public static ConferenceManager loadConferenceManager() {
		return (ConferenceManager) loadObject(CONFERENCE_MANAGER_FILE_SAVE.toString());
	}

	public static void saveConferenceManager(ConferenceManager cm) {
		saveObject(cm, CONFERENCE_MANAGER_FILE_SAVE.toString());
	}
	
	@SuppressWarnings("unchecked")
	public static Set<String> loadUsers() {
		return (Set<String>) loadObject(USERS_FILE_SAVE.toString());
	}

	public static void saveUsers(Set<String> users) {
		saveObject(users, USERS_FILE_SAVE.toString());
	}
	
	public static Object loadObject(String fileName) {
		Object result;
		
		try {
	        FileInputStream fileIn = new FileInputStream(fileName);
	        ObjectInputStream in = new ObjectInputStream(fileIn);
	        result = in.readObject();
	        in.close();
	        fileIn.close();
	        //System.out.println("Loaded " + fileName);
	    }catch(IOException i) {
	    	System.out.println(i.getMessage());
	    	System.out.println("FAILED to load " + fileName);
	    	return null;
	    }catch(ClassNotFoundException c) {
	    	System.out.println(c.getMessage());
	    	System.out.println("FAILED to load " + fileName + ", class not found");
	    	return null;
	    }
		
		return result;
	}
	
	public static boolean saveObject(Object s, String fileName) {
		boolean result = true;
		
		try {
	         FileOutputStream fileOut =
	         new FileOutputStream(fileName);
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(s);
	         out.close();
	         fileOut.close();
	         
	         //System.out.println("Saved system to " + fileName);
	      } catch(IOException i) {
	    	  System.out.println(i.getMessage());
	    	  System.out.println("FAILED to Save system to " + fileName);
	    	  result = false;
	      }
		
		return result;
	}

	
	
}
