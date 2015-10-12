package utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import solver.ResolutionPolicy;
import solver.Solver;
import solver.constraints.IntConstraintFactory;
import solver.search.strategy.IntStrategyFactory;
import solver.variables.IntVar;
import solver.variables.VariableFactory;
import utils.IntersectionHandler;
import utils.LivraisonHandler;
import modele.Graphe;
import modele.Intersection;
import modele.Livraison;
import modele.Chemin;

public class ChocoSolver {
	/**
	 * Mï¿½ï¿½thode permet de gï¿½ï¿½nï¿½ï¿½rer la matrice des poids entre les points de
	 * livraison pour choco
	 * 
	 * @return Matrice des poids
	 */
	public static int[][] genereMatriceCost(List<Chemin> chemins,
			List<Livraison> ListLivraisons) {
		int nbLivraisons = ListLivraisons.size();
		int[][] ret = new int[nbLivraisons][nbLivraisons];
		int start = 0;
		int target = 0;
		for (int i = 0; i < chemins.size(); i++) {
			start = convertVersIndiceMatrice(chemins.get(i).getStart().getId(),
					ListLivraisons);
			target = convertVersIndiceMatrice(chemins.get(i).getTarget()
					.getId(), ListLivraisons);
			ret[start][target] = (int) chemins.get(i).getDuration();
			ret[target][start] = (int) chemins.get(i).getDuration();
		}
		for (int i = 0; i < ret.length; i++) {
			ret[i][i] = (int) Double.POSITIVE_INFINITY;
		}
		return ret;

	}

	public static int convertVersIndiceMatrice(int idLivraison,
			List<Livraison> ListLivraisons) {
		int indice = 0;
		for (int i = 0; i < ListLivraisons.size(); i++) {
			if (ListLivraisons.get(i).getIdIntersection() == idLivraison) {
				indice = i;
				break;
			}
		}
		return indice;
	}
	
	public static List<Livraison> getListLivraisonOrdonne(IntVar[] xNext,
			List<Livraison> ListLivraisons) {
		List<Livraison> listRet = new ArrayList<Livraison>();
		int indice = 0;
		listRet.add(ListLivraisons.get(indice));
		indice = xNext[indice].getValue();
		while(indice != 0)
		{
			listRet.add(ListLivraisons.get(indice));
			indice = xNext[indice].getValue();
		}
		return listRet;
	}
	
	/**
	 * Mï¿½ï¿½thode permet de gï¿½ï¿½nï¿½ï¿½rer la matrice sucesseur des noeuds
	 * 
	 * @return Matrice des sucesseurs
	 */
	public static int[][] genereMatriceSucc(
			List<List<Livraison>> listLivraisonsPHs,
			List<Livraison> listLivraisons) {
		// boucle sur les plages horaires
		// pour chaque plages horaires, construire le tableau des sucesseur d'un
		// noeud dans les 2 sens
		// pour la plage horaire suivant, construire le tableau des sucesseur
		// d'un noeud dans un seul sens
		int nbLivraisons = listLivraisons.size();
		int[][] matriceRetourne = new int[nbLivraisons][nbLivraisons];
		// successeurs de Depot : premiï¿½ï¿½re plage horaire
		for (int i = 0; i < listLivraisonsPHs.size(); i++) {
			List<Livraison> currentPH = listLivraisonsPHs.get(i);
			boolean next = true;
			int sizeNext = 1;
			List<Livraison> nextPH = null;
			if (i + 1 < listLivraisonsPHs.size())
				nextPH = listLivraisonsPHs.get(i + 1);
			else
				next = false;
			if (next)
				sizeNext = nextPH.size();
			int sizeSucc = currentPH.size() + sizeNext - 1;
			for (int l = 0; l < currentPH.size(); l++) {
				int[] succ = new int[sizeSucc];
				int indiceSucc = 0;
				for (int k = 0; k < currentPH.size(); k++) {
					if (convertVersIndiceMatrice(currentPH.get(k)
							.getIdIntersection(), listLivraisons) != convertVersIndiceMatrice(
							currentPH.get(l).getIdIntersection(),
							listLivraisons))
						succ[indiceSucc++] = convertVersIndiceMatrice(currentPH
								.get(k).getIdIntersection(), listLivraisons);
				}
				if (next) {
					for (int k = 0; k < nextPH.size(); k++) {
						succ[indiceSucc++] = convertVersIndiceMatrice(nextPH
								.get(k).getIdIntersection(), listLivraisons);
					}
				} else
					succ[indiceSucc++] = 0;
				matriceRetourne[convertVersIndiceMatrice(currentPH.get(l)
						.getIdIntersection(), listLivraisons)] = succ;
			}
		}
		return matriceRetourne;
	}

	/**
	 * Mï¿½ï¿½thode pour classer les livraisons selon leurs plages horaires
	 */
	public static List<List<Livraison>> classerListLivraisonsSelonPlageHoraire(
			List<Livraison> ListLivraisons) {
		int nbLivraison = ListLivraisons.size();
		Graphe.getInstance();
		//int nbPlageHoraire = Graphe.getPlageshoraires().size();
		int nbPlageHoraire = Graphe.getPlagesHoraires().size();
		System.out.println(nbPlageHoraire);
		List<List<Livraison>> listLivraisonPHs = new ArrayList<List<Livraison>>();
		List<Livraison> listLivraisonPH = new ArrayList<Livraison>();

		for (int i = 0; i < nbPlageHoraire; i++) {
			for (int j = 0; j < nbLivraison; j++) {
				// Comparer le plage horaire
				System.out.println("Livraison PH indice: "+ListLivraisons.get(j).getPlageHoraire().getId() );
				if (ListLivraisons.get(j).getPlageHoraire().getId() == (i + 1))
					listLivraisonPH.add(ListLivraisons.get(j));
			}
			listLivraisonPHs.add(listLivraisonPH);
			listLivraisonPH = new ArrayList<Livraison>();
		}

		return listLivraisonPHs;
	}

	/**
         * 
         */
	public static int trouverMinCost(List<Chemin> chemins) {
		int min = (int) Double.POSITIVE_INFINITY;
		for (int i = 0; i < chemins.size(); i++) {
			if (chemins.get(i).getDuration() < min)
				min = (int) chemins.get(i).getDuration();
		}
		return min;
	}

	public static int trouverMaxCost(List<Chemin> chemins) {
		int max = 0;
		for (int i = 0; i < chemins.size(); i++) {
			if (chemins.get(i).getDuration() > max)
				max = (int) chemins.get(i).getDuration();
		}
		return max;
	}

	/**
	 * Mï¿½ï¿½thode permet de calculer le plus cours chemin pour une liste des
	 * points de livraisons.
	 * 
	 * @return une liste ordonnï¿½ï¿½ des points de livraisons
	 */
	public static List<Livraison> choco(List<Livraison> ListLivraisons,List<Chemin> chemins) {
		System.out.println("CHOCO");
		System.out.println(ListLivraisons);
		System.out.println(chemins);

		int nbLivraisons = ListLivraisons.size(); // get nbLivraison
		int minCost = trouverMinCost(chemins); // Ajout mï¿½ï¿½thode pour trouver  minCost
		int maxCost = trouverMaxCost(chemins);// Ajout mï¿½ï¿½thode pour trouver	 maxCost
		int bound = maxCost * nbLivraisons; 
		int[][] cost = genereMatriceCost(chemins, ListLivraisons); 															
		List<List<Livraison>> ListLivraisonsPHs = classerListLivraisonsSelonPlageHoraire(ListLivraisons);
		int[][] succ = genereMatriceSucc(ListLivraisonsPHs, ListLivraisons);
																				
		System.out.println("matrice cost");
		for (int i = 0; i < cost.length; i++) {
			for (int j = 0; j < cost[i].length; j++) {
				System.out.print(cost[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println("matrice succ");
		for (int i = 0; i < succ.length; i++) {
			for (int j = 0; j < succ[i].length; j++) {
				System.out.print(succ[i][j] + " ");
			}
			System.out.println();
		}
		// Crï¿½ï¿½ation du solveur
		System.out.println("Choco Solver !");
		Solver solver = new Solver();
		// Dï¿½ï¿½claration des variables
		IntVar[] xNext = xNext = new IntVar[nbLivraisons];
		for (int i = 0; i < nbLivraisons; i++)
			xNext[i] = VariableFactory.enumerated("Next " + i, succ[i], solver);
		IntVar[] xCost = VariableFactory.boundedArray("Cost ", nbLivraisons,
				minCost, maxCost, solver);
		IntVar xTotalCost = VariableFactory.bounded("Total cost ", nbLivraisons
				* minCost, bound - 1, solver);
		// Dï¿½ï¿½claration des contraintes
		for (int i = 0; i < nbLivraisons; i++)
			solver.post(IntConstraintFactory.element(xCost[i], cost[i],
					xNext[i], 0, "none"));
		solver.post(IntConstraintFactory.circuit(xNext, 0));
		solver.post(IntConstraintFactory.sum(xCost, xTotalCost));
		// Rï¿½ï¿½solution
		solver.set(IntStrategyFactory.firstFail_InDomainMin(xNext));
		solver.findOptimalSolution(ResolutionPolicy.MINIMIZE, xTotalCost);
		for (int k = 0; k < xNext.length; k++) {
			System.out.println(xNext[k]);
		}
		//to do
		List<Livraison> listLivraisonOrdonne = getListLivraisonOrdonne(xNext, ListLivraisons);
		System.out.println("List Ordonnee : " + listLivraisonOrdonne);
		return listLivraisonOrdonne;
	}

	
}
