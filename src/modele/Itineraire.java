package modele;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Itineraire {
	private int id;
	private List<Chemin> chemins;
	private List<List<Livraison>> livraisons;
	private List<HashMap<Integer, Date>> listHeureDepart=new ArrayList<HashMap<Integer, Date>>() ;
	private List<HashMap<Integer, Date>> listHeureArrive=new ArrayList<HashMap<Integer, Date>>();
	private List<Livraison> listInfaisable=new ArrayList<Livraison>();
	private String instruction;
	
	
	public Itineraire(int id, HashMap<Integer,HashMap> mapChemins,List< List<Livraison>> livraisons) {
		super();
		this.id = id;
		this.livraisons = livraisons;
		this.chemins=new ArrayList<Chemin>();
		String intro="Géneration d'instructions: " +"\n";
		String body=" ";
		
		Date tmiefinal=livraisons.get(0).get(0).getPlageHoraire().getDebut();
		
		for(int j=0;j<livraisons.size();j++)
		{
			//List des livraisons au PlageHoraire ID j
			List<Livraison> listlivraison=livraisons.get(j);
			String para=" ";
			
			HashMap<Integer, Date>maptimeDepart=new HashMap<Integer, Date>();
			
			Date heureDepart;
			Date heureFin=listlivraison.get(0).getPlageHoraire().getFin();
			//Verifie on arrive au premier point avant ou apres L'heure
			if(tmiefinal.after(listlivraison.get(0).getPlageHoraire().getDebut()))
			{
				if(j==0)
				{
					//Enregistrer l'heurre depart du ce point
					heureDepart=listlivraison.get(0).getPlageHoraire().getDebut();
					maptimeDepart.put(listlivraison.get(0).getIdIntersection(), heureDepart);
					para+="Vous partez de l'entrepot à "+heureDepart+" . "+'\n';
				}else
				{
				heureDepart=addition(tmiefinal,10);
				maptimeDepart.put(listlivraison.get(0).getIdIntersection(), heureDepart);
				para+="Vous partez du Point du livraison "+listlivraison.get(0).getIdIntersection()+" à"+heureDepart+". "+'\n';
								
				}
					
				
			}else{
				//Enregistrer l'heurre depart du ce point
				heureDepart=addition(listlivraison.get(0).getPlageHoraire().getDebut(),10);
				maptimeDepart.put(listlivraison.get(0).getIdIntersection(), heureDepart);
				para+="Vous partez du Point du livraison "+listlivraison.get(0).getIdIntersection()+" à "+heureDepart+". "+'\n';
			
				
			}
			
				
				HashMap<Integer, Date>maptimeArrive=new HashMap<Integer, Date>();
						
					for(int i=0;i<listlivraison.size()-1;i++)
					{
						Livraison actuel=listlivraison.get(i);
						System.out.println(actuel.getIdIntersection());
						Livraison prochiane=listlivraison.get(i+1);	
						System.out.println("actuel"+actuel.getIdIntersection());
						System.out.println("prochiane"+prochiane.getIdIntersection());
						Chemin chemin=(Chemin) mapChemins.get(actuel.getIdIntersection()).get(prochiane.getIdIntersection());
						//Chemin lie le point actuel et le prochain
						double duration =chemin.getDuration();
						
						Date arrive=addition(maptimeDepart.get(actuel.getIdIntersection()),(int) duration);
						
							if(heureFin.after(arrive)&&heureFin.after(tmiefinal))
							{	
								tmiefinal=arrive;
								maptimeArrive.put(listlivraison.get(i+1).getIdIntersection(), arrive);
								for(Troncon t : chemin.getTroncons())
								{
									para+="Vous prenez la rue "+t.getNom()+" jusqu'à l'adresse "+t.getIdNoeudDestination()+" à la vitesse suggerée "+t.getVitesse()+'\n';
									
								}
																	
								para+="Vous arrivez à l'adresse "+prochiane.getIdIntersection()+" à l'heure "+arrive+'\n';
								Date repaertir=addition(arrive,(int) 10);
								maptimeDepart.put(listlivraison.get(i+1).getIdIntersection(), repaertir);
								para+="Vous restez à l'adresse "+prochiane.getIdIntersection()+" pendant 10 minutes en attendant le client. "+'\n';
								
							}
							else
							{	
								tmiefinal=arrive;
								maptimeArrive.put(listlivraison.get(i+1).getIdIntersection(), arrive);
								para+="Ratez! Les livraison suivantes seront livrées en retard."+'\n';
								para+="On néglige et continue les livraison suivantes."+'\n';
								listInfaisable.add(prochiane);				
								maptimeDepart.put(listlivraison.get(i+1).getIdIntersection(), tmiefinal);
								
							}
							 
					}//Fin
					
					//On gere la dernier livraison dans chanque plage Horaire
					Livraison actuel;
					Livraison prochaine;
					if(listlivraison.size()>1)
					{
						actuel=listlivraison.get(listlivraison.size()-1);
						
						if(actuel.getPlageHoraire().getId()!=Graphe.getInstance().getPlagesHoraires().size())
						{
							List<Livraison> nextlistlivraison=livraisons.get(j+1);
							prochaine=nextlistlivraison.get(0);
							Chemin lastchemin=(Chemin) mapChemins.get(actuel.getIdIntersection()).get(prochaine.getIdIntersection());
							System.out.println("lastchemin");
							System.out.println(lastchemin);
							System.out.println(listlivraison.size()-2);
							tmiefinal=addition(maptimeDepart.get(actuel.getIdIntersection()),(int) lastchemin.getDuration());
							
							maptimeArrive.put(prochaine.getIdIntersection(), tmiefinal);
							for(Troncon t : lastchemin.getTroncons())
							{
								para+="Vous prenez la rue "+t.getNom()+" jusqu'à l'adresse "+t.getIdNoeudDestination()+" à la vitesse suggerée "+t.getVitesse()+'\n';
								
							}
								
							para+="On arrive a la première livraison "+prochaine.getIdIntersection()+" dans la plage horaire suivante au "+tmiefinal+'\n';
							para+="Bravo! Vous venez de finir une plage de Livraison !"+'\n';
						}else{
							Chemin lastchemin=(Chemin) mapChemins.get(actuel.getIdIntersection()).get(Graphe.getInstance().getAdresseEntrepot());
							System.out.println("lastlastchemin");
							System.out.println(lastchemin);
							tmiefinal=addition(maptimeDepart.get(actuel.getIdIntersection()),(int) lastchemin.getDuration());
							maptimeArrive.put(-1, tmiefinal);
							for(Troncon t : lastchemin.getTroncons())
							{
								para+="Vous prenez la rue "+t.getNom()+" jusqu'à l'adresse "+t.getIdNoeudDestination()+" à la vitesse suggerée "+t.getVitesse()+'\n';
								
							}
							para+="On rentre à l'entrepot à "+tmiefinal+'\n';							
						}
					}else{
							actuel=listlivraison.get(0);
							if(actuel.getPlageHoraire().getId()!=Graphe.getInstance().getPlagesHoraires().size())
							{
								List<Livraison> nextlistlivraison=livraisons.get(j+1);
								prochaine=nextlistlivraison.get(0);
								Chemin lastchemin=(Chemin) mapChemins.get(actuel.getIdIntersection()).get(prochaine.getIdIntersection());
								System.out.println("lastchemin");
								System.out.println(lastchemin);
								tmiefinal=addition(heureDepart,(int) lastchemin.getDuration());
								maptimeArrive.put(prochaine.getIdIntersection(), tmiefinal);
								for(Troncon t : lastchemin.getTroncons())
								{
									para+="Vous prenez la rue "+t.getNom()+" jusqu'à l'adresse "+t.getIdNoeudDestination()+" à la vitesse suggerée "+t.getVitesse()+'\n';
									
								}
								para+="On arrive à la premiere livraison "+prochaine.getIdIntersection()+" dans la plage horaire suivante au tmiefinal "+tmiefinal+'\n';
								para+="Bravo! Vous venez de finir une plage de Livraison !"+'\n';
							}else
							{		
								Chemin lastchemin=(Chemin) mapChemins.get(actuel.getIdIntersection()).get(Graphe.getInstance().getAdresseEntrepot());
								System.out.println("lastlastchemin");
								System.out.println(lastchemin);
								tmiefinal=addition(heureDepart,(int) lastchemin.getDuration());
								maptimeArrive.put(-1, tmiefinal);
								for(Troncon t : lastchemin.getTroncons())
								{
									para+="Vous prenez la rue "+t.getNom()+" jusqu'à l'adresse "+t.getIdNoeudDestination()+" à la vitesse suggerée"+t.getVitesse()+'\n';
									
								}
								para+="On rentre à l'entrepot à "+tmiefinal+'\n';
							}
						
					}
					
						

				System.out.println(para);
				listHeureDepart.add(maptimeDepart);
				listHeureArrive.add(maptimeArrive);	
				body+=para;
				body+='\n';
			}
		instruction=intro+body+"FIN";
		System.out.println("List retard");
		System.out.println(listInfaisable);
		
	}
	public static Date addition(Date Heure, int duration){
		Date resultat=(Date) Heure.clone();
		int actuel_min =Heure.getMinutes();
//		System.out.println(actuel_min);
		int actuel_heure =Heure.getHours();
//		System.out.println(actuel_heure);
		int next_mins=(duration+actuel_min)%60;
//		System.out.println("next_mins "+(duration+actuel_min)%60);
		int add_hours= (duration+actuel_min-next_mins)/60;
//		System.out.println(add_hours);
		resultat.setHours(actuel_heure+add_hours);
		resultat.setMinutes(next_mins);
		return resultat;
	}
	
	

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<Chemin> getChemins() {
		return chemins;
	}
	public void setChemins(List<Chemin> chemins) {
		this.chemins = chemins;
	}
	public List<HashMap<Integer, Date>> getListHeureDepart() {
		return listHeureDepart;
	}
	public void setListHeureDepart(List<HashMap<Integer, Date>> listHeureDepart) {
		this.listHeureDepart = listHeureDepart;
	}
	public List<HashMap<Integer, Date>> getListHeureArrive() {
		return listHeureArrive;
	}
	public void setListHeureArrive(List<HashMap<Integer, Date>> listHeureArrive) {
		this.listHeureArrive = listHeureArrive;
	}
	public List<Livraison> getListInfaisable() {
		return listInfaisable;
	}
	public void setListInfaisable(List<Livraison> listInfaisable) {
		this.listInfaisable = listInfaisable;
	}
	public String getInstruction() {
		return instruction;
	}
	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}
	
//	public static void main(String[] args){
//		Date test=new Date(0, 0, 0, 5, 12, 0);
//
//		System.out.println(test);
//		System.out.println(addition(test,30)); 
//		
//	}
	
	
	
	
}
