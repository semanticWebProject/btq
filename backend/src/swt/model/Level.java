package swt.model;

public class Level {
	
	int id;
	int offsetMax;
	double fromPageRank;
	double toPageRank;
	
	
	public Level() {
		
	}
	
	public Level(	int id,
					int offsetMax,
					double fromPageRank,
					double toPageRank) {
		this.id=id;
		this.offsetMax=offsetMax;
		this.fromPageRank=fromPageRank;
		this.toPageRank=toPageRank;
	}
	
	
	public int getId() {
		return this.id;
		
	}
	
	public int getOffsetMax() {
		return this.offsetMax;
		
	}
	
	public double getFromPageRank() {
		return this.fromPageRank;
		
	}
	
	public double getToPageRank() {
		return this.toPageRank;		
	}

	
	public void setId(int id) {
		this.id=id;
	}
	
	public void setOffsetMax(int offsetMax) {
		this.offsetMax=offsetMax;
		
	}
	
	public void setFromPageRank(double fromPageRank) {
		this.fromPageRank=fromPageRank;
		
	}
	
	public void setToPageRank(double toPageRank) {
		this.toPageRank=toPageRank;
		
	}

}
