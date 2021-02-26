package identifiersManagePackage;

public class IdentifiersManager {
	
	private static IdentifiersManager instance;
	

	//to support more input/output files edit accordingly
	public String[][] prepareIdentifiers(String afmInfoFile, String[] fileTags) { 
		
		String[][] fileSpecificIdentifiers = createFileSpecificIdentifiers(afmInfoFile);
		String fileType = fileSpecificIdentifiers[2][0];
		
		String[] fieldsStarters = new String[fileTags.length];
		String[] fieldsEnders = new String[fileTags.length];
		
		for( int i = 0; i < fileTags.length; i++ ) {
			String start = fileTags[i];
			String end = "";
			if(fileType.equals("xml")) {
				start = fileTags[i].replace(" ","");
				end = start;
			}
			
			fieldsStarters[i] = fileSpecificIdentifiers[0][0]+start+fileSpecificIdentifiers[0][1];
			fieldsEnders[i] = fileSpecificIdentifiers[1][0]+end+fileSpecificIdentifiers[1][1];
			
		}
		
		return new String[][]{fieldsStarters,fieldsEnders};
	}

	//to support more input/output files edit accordingly
	private String[][] createFileSpecificIdentifiers(String afmInfoFile) { 
		String[] startIdentifiers = new String[2];
		String[] endIdentifiers = new String[2];
		
		String[] fileType = new String[1];
		
		if (afmInfoFile.endsWith(".txt")){
			startIdentifiers = new String[]{"",": "};
			endIdentifiers = new String[]{"",""};
			fileType[0] = "txt";
		}
		else if(afmInfoFile.endsWith(".xml")) {
			startIdentifiers = new String[]{"<","> "};
			endIdentifiers = new String[]{" </",">"};
			fileType[0] = "xml";
		}
		else {
			System.out.println(afmInfoFile+"'s type not supported yet");
			System.out.println("Exiting now");
			System.exit(0);
		}
		return new String[][]{startIdentifiers,endIdentifiers,fileType};
		
	}
	
	public static IdentifiersManager getInstance() {
		if (instance == null) {
			instance = new IdentifiersManager();
		}
		return instance;
	}

}
