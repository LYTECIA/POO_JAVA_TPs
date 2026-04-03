package tp05.model;

public class EnergyStock implements IEnergyStock {
	//ATTRIBUTS
	private int crtLevel ;
	
	//REQUETES
	@Override
	public int crtLevel() {
		return crtLevel;
	}
	
	@Override
	public String describe() {
		return "crtLevel : " +  this.crtLevel ;
	}
	
	@Override
	public boolean isEmpty() {
		return crtLevel == 0 ;
	}
	
	//CONSTRUCTEUR
	
	public EnergyStock (int nrj) {
		if(nrj<0) {
			throw new AssertionError("l'energie doit etre positif") ;
		}
		crtLevel = nrj;
	}
	
	//COMMANDES 
	@Override
    public void consume(int qty) {
	if(!(0<=qty && qty <=crtLevel)) {
		throw new AssertionError("l'energie doit etre positif et inferieure à la valeur actuelle");
	}
	crtLevel -= qty;
    }
	@Override  
    public void raiseLevel(int qty) {
    	if(qty<0) {
			throw new AssertionError("l'energie doit etre positif") ;
		}
    	crtLevel += qty;
    }

}
	
