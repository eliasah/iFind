package parsers;

import java.io.*;

import org.jdom2.Element;
import org.jdom2.filter.Filter;
import org.jdom2.input.SAXBuilder;

import java.util.List;
import java.util.Iterator;

public class JDOM2
{
   static org.jdom2.Document document;
   static Element racine;

   public static void main(String[] args) {
      //On crée une instance de SAXBuilder
      SAXBuilder sxb = new SAXBuilder();
      try {
         //On crée un nouveau document JDOM avec en argument le fichier XML
         //Le parsing est terminé ;)
         document = sxb.build(new File("config/exercice2.xml"));
      }
      catch(Exception e){}

      //On initialise un nouvel élément racine avec l'élément racine du document.
      racine = document.getRootElement();

      //Méthode définie dans la partie 3.2. de cet article
      afficheALL();
   }
   
 //Ajouter cette méthodes à la classe JDOM2
   static void afficheALL()
   {
      //On crée une List contenant tous les noeuds "etudiant" de l'Element racine
      List listEtudiants = racine.getChildren("etudiant");

      //On crée un Iterator sur notre liste
      Iterator i = listEtudiants.iterator();
      while(i.hasNext())
      {
         //On recrée l'Element courant à chaque tour de boucle afin de
         //pouvoir utiliser les méthodes propres aux Element comme :
         //sélectionner un nœud fils, modifier du texte, etc...
         Element courant = (Element)i.next();
         //On affiche le nom de l’élément courant
         System.out.println(courant.getChild("nom").getText());
      }
   }
   
 //Ajouter cette méthode à la classe JDOM2
 //Remplacer la ligne afficheALL(); par afficheFiltre();
 static void afficheFiltre()
 {
    //On crée un nouveau filtre
    Filter filtre = new Filter()
    {
       //On défini les propriétés du filtre à l'aide
       //de la méthode matches
       public boolean matches(Object ob)
       {
          //1 ère vérification : on vérifie que les objets
          //qui seront filtrés sont bien des Elements
          if(!(ob instanceof Element)){return false;}

          //On crée alors un Element sur lequel on va faire les
          //vérifications suivantes.
          Element element = (Element)ob;

          //On crée deux variables qui vont nous permettre de vérifier
          //les conditions de nom et de prenom
          int verifNom = 0;
          int verifPrenom = 0;

          //2 ème vérification: on vérifie que le nom est bien "CynO"
          if(element.getChild("nom").getTextTrim().equals("CynO"))
          {
             verifNom = 1;
          }
          //3 ème vérification: on vérifie que CynO possède un prenom "Laurent"
          //On commence par vérifier que la personne possède un prenom,
          //en effet notre fichier XML possède des étudiants sans prénom !
          Element prenoms = element.getChild("prenoms");
          if(prenoms == null){return false;}

          //On constitue une list avec tous les prenom
          List listprenom = prenoms.getChildren("prenom");

          //On effectue la vérification en parcourant notre liste de prenom
          //(voir: 3.1. Parcourir une arborescence)
          Iterator i = listprenom.iterator();
          while(i.hasNext())
          {
             Element courant = (Element)i.next();
             if(courant.getText().equals("Laurent"))
             {
                verifPrenom = 1;
             }
          }

          //Si nos conditions sont remplies on retourne true, false sinon
          if(verifNom == 1 && verifPrenom == 1)
          {
             return true;
          }
          return false;
       }

	@Override
	public Filter and(Filter arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List filter(List arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object filter(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Filter negate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Filter or(Filter arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Filter refine(Filter arg0) {
		// TODO Auto-generated method stub
		return null;
	}
    };//Fin du filtre

    //getContent va utiliser notre filtre pour créer une liste d'étudiants répondant
    //à nos critères.
    List resultat = racine.getContent(filtre);
    //On affiche enfin l'attribut classe de tous les éléments de notre list
    Iterator i = resultat.iterator();
    while(i.hasNext())
    {
       Element courant = (Element)i.next();
       System.out.println(courant.getAttributeValue("classe"));
    }
 }
}