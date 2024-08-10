
/***************************************************************************************
* authors
* Dipankar Dutta
***************************************************************************************/
import java.util.*;
public class Chromosome 
{
    String Dna;    
    String classLabel;
    int A;
    int C;
    int AUC;
    float confidence;
    //float nagetive_confidence;
    float coverage;
    int noOfValidAttributes;
    Gene gen[];
    Vector<Integer> covered_record_nos = new Vector<Integer>();
    /***************************************************************************************
    * Constructor
    *
    ***************************************************************************************/
    public Chromosome(String DnaValues)
    {
        Dna=DnaValues;
    }//end of method
    /***************************************************************************************
    * Constructor  
    * 
    ***************************************************************************************/
    public Chromosome(String DnaValues,Dataset trainingDataset)
    {
        Dna=DnaValues;
        gen=extractGeneFromDna(trainingDataset);
    }//end of method
    /***************************************************************************************
    * Constructor
    *
    ***************************************************************************************/
    public Chromosome(String DnaValues,String classLabel1,int A1,int C1,int AUC1,float confidence1,float coverage1,int noOfValidAttributes1,Vector<Integer> covered_record_nos1,Dataset trainingDataset)
    {
        Dna=DnaValues;
        classLabel=classLabel1;
        A=A1;
        C=C1;
        AUC=AUC1;
        confidence=confidence1;
        coverage=coverage1;
        noOfValidAttributes=noOfValidAttributes1;
        gen=extractGeneFromDna(trainingDataset);
        covered_record_nos = covered_record_nos1;
    }//end of method
    /***************************************************************************************
    * Constructor
    *
    ***************************************************************************************/
    /*public Chromosome(String DnaValues,String classLabel1,int A1,int C1,int AUC1,float positive_confidence1,float nagetive_confidence1,float coverage1,int noOfValidAttributes1,Dataset trainingDataset)
    {
        Dna=DnaValues;
        classLabel=classLabel1;
        A=A1;
        C=C1;
        AUC=AUC1;
        confidence=positive_confidence1;
        //nagetive_confidence=nagetive_confidence1;
        coverage=coverage1;
        noOfValidAttributes=noOfValidAttributes1;
        gen=extractGeneFromDna(trainingDataset);
    }//end of method*/
    /***************************************************************************************
    * showDnaOfChromosome
    *
    ***************************************************************************************/
    public Gene[] extractGeneFromDna(Dataset trainingDataSet)
    {
        Gene gen1[]=new Gene[trainingDataSet.noOfAttributes-1];
        int startIndex=0;
        int endIndex=0;
        int counter2=0;
        for(int counter1=0;counter1<=Dna.length()-1;counter1++)
        {
            if(Dna.charAt(counter1)=='|')
            {
                endIndex=counter1;                
                gen1[counter2]=new Gene(Dna.substring(startIndex,endIndex),trainingDataSet.attributeInformation[1][counter2],trainingDataSet,counter2);
                //System.out.println("gen1["+counter2+"]="+Dna.substring(startIndex,endIndex));//for testing
                startIndex=endIndex+1;
                counter2++;
            }//end of if
        }//end of for        
        return gen1;
    }//end of method
    /***************************************************************************************
    * showDnaOfChromosome
    *
    ***************************************************************************************/
    public void showDnaOfChromosome()
    {
        System.out.println("Dna="+Dna);        
    }//end of method
    /***************************************************************************************
    * showDnaAndGeneOfChromosome
    *
    ***************************************************************************************/
    public void showDnaAndGeneOfChromosome()
    {
        System.out.println("Dna="+Dna);
        System.out.println("Gene[0]="+gen[0].geneValue);
    }//end of method
    /***************************************************************************************
    * showChromosome
    *
    ***************************************************************************************/
    public void showChromosome()
    {
        System.out.println("Dna="+Dna);
        System.out.println("classLabel="+classLabel);
        System.out.println("A="+A);
        System.out.println("C="+C);
        System.out.println("AUC="+AUC);
        System.out.println("Confidence="+confidence);
        //System.out.println("nagetive_confidence="+nagetive_confidence);
        System.out.println("Coverage="+coverage);
        System.out.println("noOfValidAttributes="+noOfValidAttributes);
        System.out.println("no of covered records="+covered_record_nos);
    }//end of method
}//end of class