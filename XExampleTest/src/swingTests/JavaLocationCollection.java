package swingTests;
public class JavaLocationCollection {
	  private static JavaLocation[] defaultLocations =
	    { new JavaLocation("Belgium",
	                       "near Liege",
	                       "/Users/heatherciechowski/CSC531/XExampleTest/src/swingTests/flags/Belgium.gif"),
	      new JavaLocation("Brazil",
	                       "near Salvador",
	                       "flags/Brazil.gif"),
	      new JavaLocation("Colombia",
	                       "near Bogota",
	                       "flags/Colombia.gif"),
	      new JavaLocation("Indonesia",
	                       "main island",
	                       "flags/Indonesia.gif"),
	      new JavaLocation("Jamaica",
	                       "near Spanish Town",
	                       "flags/Jamaica.gif"),
	      new JavaLocation("Mozambique",
	                       "near Sofala",
	                       "flags/Mozambique.gif"),
	      new JavaLocation("Philippines",
	                       "near Quezon City",
	                       "flags/Philippines.gif"),
	      new JavaLocation("Sao Tome",
	                       "near Santa Cruz",
	                       "flags/Saotome.gif"),
	      new JavaLocation("Spain",
	                       "near Viana de Bolo",
	                       "flags/Spain.gif"),
	      new JavaLocation("Suriname",
	                       "near Paramibo",
	                       "flags/Suriname.gif"),
	      new JavaLocation("United States",
	                       "near Montgomery, Alabama",
	                       "flags/USA.gif"),
	      new JavaLocation("United States",
	                       "near Needles, California",
	                       "flags/USA.gif"),
	      new JavaLocation("United States",
	                       "near Dallas, Texas",
	                       "flags/USA.gif")
	    };

	  private JavaLocation[] locations;
	  private int numCountries;

	  public JavaLocationCollection(JavaLocation[] locations) {
	    this.locations = locations;
	    this.numCountries = countCountries(locations);
	  }
	  
	  public JavaLocationCollection() {
	    this(defaultLocations);
	  }

	  public JavaLocation[] getLocations() {
	    return(locations);
	  }

	  public int getNumCountries() {
	    return(numCountries);
	  }

	  // Assumes the list is sorted by country name
	  
	  private int countCountries(JavaLocation[] locations) {
	    int n = 0;
	    String currentCountry, previousCountry = "None";
	    for(int i=0;i<locations.length;i++) {
	      currentCountry = locations[i].getCountry();
	      if(!previousCountry.equals(currentCountry))
	        n = n + 1;
	      currentCountry = previousCountry;
	    }
	    return(n);
	  }
	}
