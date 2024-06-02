
public class FamilyMember implements Comparable<FamilyMember> {
	
    private String name;
    private int birthYear;
    private FamilyMember[] children;
    
    public FamilyMember(String name, int birthYear, FamilyMember[] children) {
        this.name = name;
        this.birthYear = birthYear;
        this.children = children;
    }

   
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getBirthYear() {
		return birthYear;
	}


	public void setBirthYear(int birthYear) {
		this.birthYear = birthYear;
	}


	public FamilyMember[] getChildren() {
		return children;
	}


	public void setChildren(FamilyMember[] children) {
		this.children = children;
	}


	@Override
	public int compareTo(FamilyMember o) {
		if(this.birthYear < o.getBirthYear()) {
			return -1;
		}else if(this.birthYear > o.getBirthYear()) {
			return 1;
		}else
			return 0;
	}
	
	
	@Override
	public String toString() {
		return "(" + name + " " + Integer.toString(birthYear) + ")" ;
	}



}
