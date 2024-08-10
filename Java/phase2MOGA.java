import java.util.*;
public class phase2MOGA
{
    Dataset trainingDataSet;
    Population sortedPopulationFromP1MOGA;
    Population populationFromP1MOGA;
    int numberOfGenerationofBPMOGA;
    int numberOfGenerationofP2MOGA;
    int sizeOfInitialPopulationOfP2MOGA;
    float minClassRulesProbP2;
    float maxClassRulesProbP2;
    float minCrossProbP2;
    float maxCrossProbP2;
    float minMuProbP2;
    float maxMuProbP2;
    PopulationForP2 populationParetoSelectionForP2;
    /************************************************************************************************
    * Constructor
    /************************************************************************************************/
    public phase2MOGA(int numberOfCallofP2MOGA,Dataset traningDataSet1,Population populationFromP1MOGA1,int numberOfGenerationofBPMOGA1,int numberOfGenerationofP2MOGA1,int sizeOfInitialPopulationOfP2MOGA1,float minRuleProbP21,float maxRuleProbP21,float minCrossProbP21,float maxCrossProbP21,float minMuProbP21,float maxMuProbP21)
    {
        sortedPopulationFromP1MOGA=populationFromP1MOGA1.sortingCSRs();
        /*System.out.println("Sorted Population from P1MOGA at P2MOGA");//for testing
        sortedPopulationFromP1MOGA.showPopulation();
        System.out.println("____________________________");//for testing*/
        int startingGenerationOfP2=numberOfCallofP2MOGA*numberOfGenerationofP2MOGA1;
        trainingDataSet=traningDataSet1;
        populationFromP1MOGA=populationFromP1MOGA1;
        numberOfGenerationofBPMOGA=numberOfGenerationofBPMOGA1;
        //System.out.println("numberOfGenerationofBPMOGA="+numberOfGenerationofBPMOGA);//for testing
        numberOfGenerationofP2MOGA=numberOfGenerationofP2MOGA1;
        //System.out.println("numberOfGenerationofP2MOGA="+numberOfGenerationofP2MOGA);//for testing
        sizeOfInitialPopulationOfP2MOGA=sizeOfInitialPopulationOfP2MOGA1;
        minClassRulesProbP2=minRuleProbP21;
        maxClassRulesProbP2=maxRuleProbP21;
        minCrossProbP2=minCrossProbP21;
        maxCrossProbP2=maxCrossProbP21;
        minMuProbP2=minMuProbP21;
        maxMuProbP2=maxMuProbP21;
        //System.out.println("Within phase2MOGA");//for testing
        //System.out.println("Population by P1MOGA");//for testing
        //populationFromP1MOGA.showPopulation();
        //Population sortedPopulationFromP1MOGA=populationFromP1MOGA.sortingCSRs();
        float classRulesProbP2=calClassRulesProbabilityForP2(startingGenerationOfP2,numberOfGenerationofBPMOGA,minClassRulesProbP2,maxClassRulesProbP2);
        //System.out.println("classRulesProbP2="+classRulesProbP2);//for testing
        //initialPopulationForP2=new PopulationForP2(sortedPopulationFromP1MOGA,sizeOfInitialPopulationOfP2MOGA,classRulesProbP2);
        //initialPopulationForP2.showDnaOfPopulationForP2();//for testing
        for(int generationP2=0;generationP2<=numberOfGenerationofP2MOGA-1;generationP2++)
        {
            System.out.println("generationP2="+generationP2);//for testing
            //generationBPMOGA=numberOfGenerationofP2MOGA*numberOfCallofP2MOGA+generationP2;
            //System.out.println("generationBPMOGA="+generationBPMOGA);//for testing
            PopulationForP2 crossoverPopulationForP2;
            if(generationP2!=0&generationP2%2!=0)
            {
                crossoverPopulationForP2=populationParetoSelectionForP2;
                //System.out.println("Pereto generationP2="+generationP2);//for testing
            }//end of if
            else
            {
                crossoverPopulationForP2=new PopulationForP2(sortedPopulationFromP1MOGA,sizeOfInitialPopulationOfP2MOGA,classRulesProbP2);
                //System.out.println("Initial population generationP2="+generationP2);//for testing
                if(generationP2==0)
                {
                    populationParetoSelectionForP2=crossoverPopulationForP2;
                }//end of if
            }//end of else
            /*System.out.println("Population before Crossover");//for testing
            crossoverPopulationForP2.showPopulationForP2();//for testing
            System.out.println("____________________________");//for testing*/
            float crossoverProbabilityForP2=calCrossoverProbabilityForP2(generationP2,numberOfGenerationofP2MOGA,minCrossProbP2,maxCrossProbP2);
            /*System.out.println("Crossover Probability for P2="+crossoverProbabilityForP2);//for testing
            System.out.println("____________________________");//for testing*/
            PopulationForP2 populationAfterCrossoverForP2=crossoverForP2(crossoverPopulationForP2,crossoverProbabilityForP2);
            /*System.out.println("Population after Crossover");//for testing
            populationAfterCrossoverForP2.showPopulationForP2();//for testing
            System.out.println("____________________________");//for testing*/
            float mutationProbabilityForP2=calMutationProbability(generationP2,numberOfGenerationofP2MOGA,minMuProbP2,maxMuProbP2);
            /*System.out.println("Mutation Probability for P2="+mutationProbabilityForP2);//for testing
            System.out.println("____________________________");//for testing*/
            PopulationForP2 populationAfterMutationForP2=mutationForP2(crossoverPopulationForP2,mutationProbabilityForP2);
            /*System.out.println("Population after Mutation");//for testing
            populationAfterMutationForP2.showPopulationForP2();//for testing
            System.out.println("____________________________");//for testing*/
            PopulationForP2 populationAfterCombinationForP2=combinationForP2(populationParetoSelectionForP2,populationAfterCrossoverForP2,populationAfterMutationForP2);
            /*System.out.println("populationAfterCombinationForP2");//for testing
            populationAfterCombinationForP2.showPopulationForP2();
            System.out.println("________________________________");//for testing*/            
            PopulationForP2 populationAfterElimatingUnnessesaryCSRsForP2=elimatingUnnessesaryCSRs(populationAfterCombinationForP2);
            /*System.out.println("populationAfterElimatingUnnessesaryCSRsForP2");//for testing
            populationAfterElimatingUnnessesaryCSRsForP2.showPopulationForP2();
            System.out.println("________________________________");//for testing*/
            PopulationForP2 populationAfterEliminatingDuplicateForP2=eliminateDuplicateForP2(populationAfterElimatingUnnessesaryCSRsForP2);
            /*System.out.println("populationAfterEliminatingDuplicateForP2");//for testing
            populationAfterEliminatingDuplicateForP2.showPopulationForP2();
            System.out.println("________________________________");//for testing  */
            PopulationForP2 populationAfterFitnessCalculationForP2=fitnessCalculationForP2(populationAfterEliminatingDuplicateForP2);
            /*System.out.println("populationAfterFitnessCalculationForP2");//for testing
            populationAfterFitnessCalculationForP2.showPopulationForP2();
            System.out.println("________________________________");//for testing*/
            //PopulationForP2 populationAfterEliminatingRulesWithZeroCSRsForP2=eliminateRulesWithZeroCSRs(populationAfterFitnessCalculationForP2);
            
            //PopulationForP2 populationAfterElimatingUnnessesaryChromosomeAndAfterEliminatingDuplicateForP2=eliminateDuplicateForP2(populationAfterElimatingUnnessesaryChromosomeForP2);
            /*System.out.println("populationAfterElimatingUnnessesaryChromosomeAndAfterEliminatingDuplicateForP2");//for testing
            populationAfterElimatingUnnessesaryChromosomeAndAfterEliminatingDuplicateForP2.showDnaOfPopulationForP2();
            System.out.println("________________________________");//for testing*/
            populationParetoSelectionForP2=paretoSelectionForP2(populationAfterFitnessCalculationForP2);
            /*System.out.println("populationParetoSelectionForP2");//for testing
            populationParetoSelectionForP2.showPopulationForP2();
            System.out.println("________________________________");//for testing*/
            if(populationParetoSelectionForP2.populationSizeForP2>20)//to limit population size of Phase2 to 100
            {
                PopulationForP2 sortedPopulationForP2=populationParetoSelectionForP2.sortingChromoOfP2();
                PopulationForP2 top20SortedPopulationForP2=returnTop20SortedPopulationForP2(sortedPopulationForP2);
                populationParetoSelectionForP2=top20SortedPopulationForP2;
                /*System.out.println("Top 20 populationParetoSelectionForP2");//for testing
                populationParetoSelectionForP2.showPopulationForP2();
                System.out.println("________________________________");//for testing*/
            }//end of if
            
            /*System.out.println(populationParetoSelectionForP2.populationSizeForP2);//for testing
            System.out.println("________________________________");//for testing*/
            /*System.out.println("InitialPopulationForP2");//for testing
            initialPopulationForP2.showPopulationForP2();
            System.out.println("________________________________");//for testing*/
            //populationParetoSelectionForP2.findMaxTotalConfidenceMaxTotalCoverageMinNORules();
        }//end of for
    }//end of constructor
    /************************************************************************************************
    * Constructor
    /************************************************************************************************/
    public phase2MOGA()
    {

    }//end of constructor
    /******************************************************************************************************
    *method calClassRulesProbabilityForP2(). This method will determine Class Rules Probability for a perticular
    *generation. At biginning Class Rules probability will be lower and at the end it will be higher.
    ******************************************************************************************************/
    public float calClassRulesProbabilityForP2(int generationNumber,int maxNumberOfGeneration,float minClassRulesProbability,float maxClassRulesProbability)
    {
        /*System.out.println("generationNumber"+generationNumber);//for testing
        System.out.println("maxNumberOfGeneration"+maxNumberOfGeneration);//for testing*/
        float classRulesProbability=(float)(maxClassRulesProbability-minClassRulesProbability)*generationNumber/maxNumberOfGeneration+minClassRulesProbability;
        return classRulesProbability;
    }//end of calClassRulesProbabilityForP2()
    /******************************************************************************************************
    *method calCrossoverProbabilityForP2(). This method will determine Crossover Probability for a perticular
    *generation. At biginning crossover probability will be lower and at the end it will be higher.
    ******************************************************************************************************/
    public float calCrossoverProbabilityForP2(int generationOfP2Number,int maxNumberOfGenerationOfP2,float minCrossoverProbability,float maxCrossoverProbability)
    {
        float crossoverProbability=(float)(maxCrossoverProbability-minCrossoverProbability)*generationOfP2Number/(maxNumberOfGenerationOfP2-1)+minCrossoverProbability;
        return crossoverProbability;
    }//end of calCrossoverProbabilityForP2()
    /************************************************************************************************
    * crossoverForP2() method. This method will do single point crossover
    /************************************************************************************************/
    public PopulationForP2 crossoverForP2(PopulationForP2 populationBeforeCrossoverForP2,float crossOverProbabilityForP2)
    {
        ChromosomeForP2 chromoForP2[]=new ChromosomeForP2[populationBeforeCrossoverForP2.populationSizeForP2];
        //System.out.println("Crossover For P2");//for testing
        boolean flag[]=new boolean[populationBeforeCrossoverForP2.populationSizeForP2];
        for(int counter=0;counter<=populationBeforeCrossoverForP2.populationSizeForP2-1;counter++)
        {
            flag[counter]=true;
        }//end of for
        /******************************************************************************************************
        *to calculate no of points where crossover will occur
        ******************************************************************************************************/
        int noOfCrossoverForP2=(int)(populationBeforeCrossoverForP2.populationSizeForP2*crossOverProbabilityForP2);
        //System.out.println("noOfCrossoverForP2="+noOfCrossoverForP2);//for testing
        int counter1 = -1;
        for(int counter=0;counter<=noOfCrossoverForP2-1;counter++)
        {
            /******************************************************************************************************
            *choosing two chromosomes randomly for crossover
            ******************************************************************************************************/
            int randomNo1=(int)(Math.random()*populationBeforeCrossoverForP2.populationSizeForP2);
            int randomNo2=(int)(Math.random()*populationBeforeCrossoverForP2.populationSizeForP2);
            String Dna1=populationBeforeCrossoverForP2.chromoForP2[randomNo1].DnaForP2;
            String Dna2=populationBeforeCrossoverForP2.chromoForP2[randomNo2].DnaForP2;
            while(randomNo1==randomNo2|flag[randomNo1]==false|flag[randomNo2]==false)
            {
                randomNo1=(int)(Math.random()*populationBeforeCrossoverForP2.populationSizeForP2);
                randomNo2=(int)(Math.random()*populationBeforeCrossoverForP2.populationSizeForP2);
                Dna1=populationBeforeCrossoverForP2.chromoForP2[randomNo1].DnaForP2;
                Dna2=populationBeforeCrossoverForP2.chromoForP2[randomNo2].DnaForP2;
            }//end of while
            /******************************************************************************************************
            *making them false to prevent repeated choosing
            ******************************************************************************************************/
            flag[randomNo1]=false;
            flag[randomNo2]=false;
            /******************************************************************************************************
            *to choose one perticular point randomly
            ******************************************************************************************************/
            int randomNo3=(int)(Math.random()*populationBeforeCrossoverForP2.chromoForP2[randomNo1].DnaForP2.length());
            //System.out.println("randomNo3="+randomNo3);//for testing
            /******************************************************************************************************
            *to divide Dna into leftDna and rightDna
            ******************************************************************************************************/
            String leftDna1=populationBeforeCrossoverForP2.chromoForP2[randomNo1].DnaForP2.substring(0,randomNo3);
            String rightDna1=populationBeforeCrossoverForP2.chromoForP2[randomNo1].DnaForP2.substring(randomNo3);
            String leftDna2=populationBeforeCrossoverForP2.chromoForP2[randomNo2].DnaForP2.substring(0,randomNo3);
            String rightDna2=populationBeforeCrossoverForP2.chromoForP2[randomNo2].DnaForP2.substring(randomNo3);
            /******************************************************************************************************
            *crossover
            ******************************************************************************************************/
            Dna1=leftDna1.concat(rightDna2);
            Dna2=leftDna2.concat(rightDna1);
            counter1++;
            chromoForP2[counter1]=new ChromosomeForP2(Dna1,sortedPopulationFromP1MOGA);
            counter1++;
            chromoForP2[counter1]=new ChromosomeForP2(Dna2,sortedPopulationFromP1MOGA);
        }//end of for
        //System.out.println("counter1="+counter1);//for testing
        if(counter1!=-1)
        {
            ChromosomeForP2 chromoForP2_1[]=new ChromosomeForP2[counter1+1];
            for(int counter=0;counter<=counter1;counter++)
            {
                chromoForP2_1[counter]=chromoForP2[counter];
            }//end of for
            PopulationForP2 populationAfterCrossoverForP2=new PopulationForP2(chromoForP2_1,counter1+1);
            return populationAfterCrossoverForP2;
        }//end of if
        else //for no crossover
            return populationBeforeCrossoverForP2;
    }//end of crossoverForP2()
    /******************************************************************************************************
     *method calMutationProbability(). This method will determine Mutation Probability for a perticular
     *generation. At biginning Mutation probability will be higher and at the end it will be lower.
     ******************************************************************************************************/
    public float calMutationProbability(int generationOfP2Number,int maxNumberOfGenerationOfP2,float minMutationProbability,float maxMutationProbability)
    {
        float mutationProbability=(float)0.0;
        mutationProbability=(float)(maxMutationProbability-minMutationProbability)*(maxNumberOfGenerationOfP2-generationOfP2Number-1)/(maxNumberOfGenerationOfP2-1)+minMutationProbability;
        return mutationProbability;
    }//end of getMutationProbability()
    /************************************************************************************************
    * mutationForP2() method. This method will do binary mutation
    /************************************************************************************************/
    public PopulationForP2 mutationForP2(PopulationForP2 populationBeforeMutationForP2,float mutationProbabilityForP2)
    {
        ChromosomeForP2 chromoForP2[]=new ChromosomeForP2[populationBeforeMutationForP2.populationSizeForP2];
        //System.out.println("Mutation For P2");//for testing
        for(int outerCounter=0;outerCounter<=populationBeforeMutationForP2.populationSizeForP2-1;outerCounter++)
        {
            //System.out.println("outerCounter="+outerCounter);//for testing
            String dnaString=populationBeforeMutationForP2.chromoForP2[outerCounter].DnaForP2;
            //System.out.println("dnaString="+dnaString);//for testing
            for(int innerCounter=0;innerCounter<=populationBeforeMutationForP2.chromoForP2[outerCounter].DnaForP2.length()-1;innerCounter++)
            {
                float randomNo=(float)(Math.random());                
                if(randomNo<mutationProbabilityForP2)
                {
                    //System.out.println("Mutation");//for testing
                    //System.out.println("innerCounter="+innerCounter);//for testing
                    if(dnaString.charAt(innerCounter)=='1')
                    {
                        String leftPart=dnaString.substring(0,innerCounter);
                        //System.out.println("leftPart="+leftPart);//for testing
                        String rightPart=dnaString.substring(innerCounter+1,dnaString.length());
                        //System.out.println("rightPart="+rightPart);//for testing
                        dnaString=leftPart.concat("0").concat(rightPart);
                    }//end of if
                    else
                    {
                        String leftPart=dnaString.substring(0,innerCounter);
                        //System.out.println("leftPart="+leftPart);//for testing
                        String rightPart=dnaString.substring(innerCounter+1,dnaString.length());
                        //System.out.println("rightPart="+rightPart);//for testing
                        dnaString=leftPart.concat("1").concat(rightPart);
                    }//end of else
                }//end of if
                //System.out.println("dnaString="+dnaString);//for testing
                //populationBeforeMutationForP2.chromoForP2[outerCounter].DnaForP2=dnaString;
            }//end of for
            chromoForP2[outerCounter]=new ChromosomeForP2(dnaString,sortedPopulationFromP1MOGA);
        }//end of for
        PopulationForP2 populationAfterMutationForP2=new PopulationForP2(chromoForP2,populationBeforeMutationForP2.populationSizeForP2);
        return populationAfterMutationForP2;
    }//end of mutationForP2()
    /************************************************************************************************
    * combinationForP2() method. This method will combine two populations
    /************************************************************************************************/
    public PopulationForP2 combinationForP2(PopulationForP2 earlierGenPopulationForP2,PopulationForP2 presentGenPopulationForP2)
    {
        //System.out.println("Combination for P2 method");
        ChromosomeForP2 chromoForP2[]=new ChromosomeForP2[earlierGenPopulationForP2.populationSizeForP2+presentGenPopulationForP2.populationSizeForP2];
        int counter=0;
        for(int loopCounter=0;loopCounter<=earlierGenPopulationForP2.populationSizeForP2-1;loopCounter++)
        {
            chromoForP2[counter]=new ChromosomeForP2(earlierGenPopulationForP2.chromoForP2[loopCounter].DnaForP2,earlierGenPopulationForP2.chromoForP2[loopCounter].totalConfidence,earlierGenPopulationForP2.chromoForP2[loopCounter].totalCoverage,earlierGenPopulationForP2.chromoForP2[loopCounter].numberOfCSRs,earlierGenPopulationForP2.chromoForP2[loopCounter].sortedPopulationByP1);
            counter++;
        }//end of for
        for(int loopCounter=0;loopCounter<=presentGenPopulationForP2.populationSizeForP2-1;loopCounter++)
        {
            chromoForP2[counter]=new ChromosomeForP2(presentGenPopulationForP2.chromoForP2[loopCounter].DnaForP2,presentGenPopulationForP2.chromoForP2[loopCounter].totalConfidence,presentGenPopulationForP2.chromoForP2[loopCounter].totalCoverage,presentGenPopulationForP2.chromoForP2[loopCounter].numberOfCSRs,presentGenPopulationForP2.chromoForP2[loopCounter].sortedPopulationByP1);
            counter++;
        }//end of for
        PopulationForP2 populationAfterCombinationForP2=new PopulationForP2(chromoForP2,counter);        
        return populationAfterCombinationForP2;
    }//end of combinationForP2()
    /************************************************************************************************
    * combinationForP2() method. This method will combine two populations
    /************************************************************************************************/
    public PopulationForP2 combinationForP2(PopulationForP2 populationParetoSelectionForP2,PopulationForP2 populationAfterCrossoverForP2,PopulationForP2 populationAfterMutationForP2)
    {        
        ChromosomeForP2 chromoForP2[]=new ChromosomeForP2[populationParetoSelectionForP2.populationSizeForP2+populationAfterCrossoverForP2.populationSizeForP2+populationAfterMutationForP2.populationSizeForP2];
        int counter=0;
        for(int loopCounter=0;loopCounter<=populationParetoSelectionForP2.populationSizeForP2-1;loopCounter++)
        {
            chromoForP2[counter]=new ChromosomeForP2(populationParetoSelectionForP2.chromoForP2[loopCounter].DnaForP2,populationParetoSelectionForP2.chromoForP2[loopCounter].totalConfidence,populationParetoSelectionForP2.chromoForP2[loopCounter].totalCoverage,populationParetoSelectionForP2.chromoForP2[loopCounter].numberOfCSRs,populationParetoSelectionForP2.chromoForP2[loopCounter].sortedPopulationByP1);
            counter++;
        }//end of for        
        for(int loopCounter=0;loopCounter<=populationAfterCrossoverForP2.populationSizeForP2-1;loopCounter++)
        {
            chromoForP2[counter]=new ChromosomeForP2(populationAfterCrossoverForP2.chromoForP2[loopCounter].DnaForP2,populationAfterCrossoverForP2.chromoForP2[loopCounter].totalConfidence,populationAfterCrossoverForP2.chromoForP2[loopCounter].totalCoverage,populationAfterCrossoverForP2.chromoForP2[loopCounter].numberOfCSRs,populationAfterCrossoverForP2.chromoForP2[loopCounter].sortedPopulationByP1);
            //System.out.println("loopCounter="+loopCounter);
            //System.out.println("counter="+counter);
            counter++;
        }//end of for
        for(int loopCounter=0;loopCounter<=populationAfterMutationForP2.populationSizeForP2-1;loopCounter++)
        {
            chromoForP2[counter]=new ChromosomeForP2(populationAfterMutationForP2.chromoForP2[loopCounter].DnaForP2,populationAfterMutationForP2.chromoForP2[loopCounter].totalConfidence,populationAfterMutationForP2.chromoForP2[loopCounter].totalCoverage,populationAfterMutationForP2.chromoForP2[loopCounter].numberOfCSRs,populationAfterMutationForP2.chromoForP2[loopCounter].sortedPopulationByP1);
            counter++;
        }//end of for
        PopulationForP2 populationAfterCombinationForP2=new PopulationForP2(chromoForP2,counter);        
        return populationAfterCombinationForP2;
    }//end of combinationForP2()    
    /************************************************************************************************
    * eliminateDuplicateForP2() method. This method will eliminate Duplicate chromosomes
    /************************************************************************************************/
    public PopulationForP2 eliminateDuplicateForP2(PopulationForP2 populationBeforeEliminateDuplicateForP2)
    {
        boolean flag[]=new boolean[populationBeforeEliminateDuplicateForP2.populationSizeForP2];
        for(int loopCounter=0;loopCounter<=populationBeforeEliminateDuplicateForP2.populationSizeForP2-1;loopCounter++)
        {
            flag[loopCounter]=true;
        }//end of for
        for(int outerLoopCounter=0;outerLoopCounter<=populationBeforeEliminateDuplicateForP2.populationSizeForP2-1;outerLoopCounter++)
        {
            String outerChromosome=populationBeforeEliminateDuplicateForP2.chromoForP2[outerLoopCounter].DnaForP2;
            for(int innerLoopCounter=outerLoopCounter+1;innerLoopCounter<=populationBeforeEliminateDuplicateForP2.populationSizeForP2-1;innerLoopCounter++)
            {
                String innerChromosome=populationBeforeEliminateDuplicateForP2.chromoForP2[innerLoopCounter].DnaForP2;
                if(outerChromosome.equals(innerChromosome))
                {
                    flag[innerLoopCounter]=false;                    
                }//end of if
            }//end of innerForLoop
        }//end of outerForLoop
        int counter1=0;
        for(int counter=0;counter<=populationBeforeEliminateDuplicateForP2.populationSizeForP2-1;counter++)
        {            
            if(flag[counter])
                counter1++;
        }//end of for
        ChromosomeForP2 chromoForP2[]=new ChromosomeForP2[counter1];
        counter1=0;
        for(int counter=0;counter<=populationBeforeEliminateDuplicateForP2.populationSizeForP2-1;counter++)
        {
            if(flag[counter])
            {
                chromoForP2[counter1]=new ChromosomeForP2(populationBeforeEliminateDuplicateForP2.chromoForP2[counter].DnaForP2,populationBeforeEliminateDuplicateForP2.chromoForP2[counter].totalConfidence,populationBeforeEliminateDuplicateForP2.chromoForP2[counter].totalCoverage,populationBeforeEliminateDuplicateForP2.chromoForP2[counter].numberOfCSRs,populationBeforeEliminateDuplicateForP2.chromoForP2[counter].sortedPopulationByP1);
                counter1++;
            }//end of if
        }//end of for
        PopulationForP2 populationAfterEliminatingDuplicate=new PopulationForP2(chromoForP2,counter1);
        return populationAfterEliminatingDuplicate;        
    }//end of eliminateDuplicateForP2()    
    /************************************************************************************************
    * fitnessCalculationForP2() method. This method will calculate fitness of chromosomes (Complete rules(CR))
     *Fitness are measured by three objectives of GA. TotalConfidence, TotalCoverage, and
     *number of CSR in CR. We hve to maximise TotalConfidence, TotalCoverage and minimise
     *number of CSR. To convert it into maximisation problem we are considering 1/number of CSRs
    /************************************************************************************************/
    public PopulationForP2 fitnessCalculationForP2(PopulationForP2 populationBeforeFitnessCalculationForP2)
    {
        for(int loopCounter=0;loopCounter<=populationBeforeFitnessCalculationForP2.populationSizeForP2-1;loopCounter++)
        {
            if((populationBeforeFitnessCalculationForP2.chromoForP2[loopCounter].totalConfidence==0.0) && (populationBeforeFitnessCalculationForP2.chromoForP2[loopCounter].totalCoverage==0.0))
            {
                populationBeforeFitnessCalculationForP2.chromoForP2[loopCounter].calculateFitnessForChromosomesForP2(trainingDataSet);
                //System.out.println("Within if");//for testing
            }//end of if
            /*else
            {
                System.out.println("Within else");//for testing
            }//end of else*/
        }//end of for
        return populationBeforeFitnessCalculationForP2;
    }//end of fitnessCalculationForP2()
    /************************************************************************************************
    * elimatingUnnessesaryCSRs() method. This method will eliminate Unnessesary CSRs.
     * CSRs which are not covering any record
    /************************************************************************************************/
    public PopulationForP2 elimatingUnnessesaryCSRs(PopulationForP2 populationBeforeEliminatingUnnessesaryChromosomeForP2)
    {
        for(int loopCounter=0;loopCounter<=populationBeforeEliminatingUnnessesaryChromosomeForP2.populationSizeForP2-1;loopCounter++)
        {
            String dna = populationBeforeEliminatingUnnessesaryChromosomeForP2.chromoForP2[loopCounter].DnaForP2;
            boolean flag[]=new boolean[trainingDataSet.noOfRecords];
            //System.out.println("flag="+flag[0]);//for testing (it is False)
            String modified_dna = "";
            for(int geneNo=0;geneNo<=dna.length()-1;geneNo++)
            {
                if(dna.charAt(geneNo)=='1')
                {                    
                    boolean dnaflag=false;
                    for(int recordNo=0;recordNo<=trainingDataSet.noOfRecords-1;recordNo++)
                    {
                        if(!flag[recordNo])
                        {
                            if(sortedPopulationFromP1MOGA.chromo[geneNo].covered_record_nos.contains(recordNo))
                            {
                                flag[recordNo]=true;
                                dnaflag=true;
                            }//end of if
                        }//end of if
                    }//end of for
                    if(dnaflag)
                       modified_dna=modified_dna.concat("1");
                    else
                       modified_dna=modified_dna.concat("0");
                }//end of if
                else
                {
                    modified_dna=modified_dna.concat("0");
                }//end of else
            }//end of for
            //System.out.println("dna="+dna);//for testing
            //System.out.println("modified_dna="+modified_dna);//for testing
            populationBeforeEliminatingUnnessesaryChromosomeForP2.chromoForP2[loopCounter].DnaForP2=modified_dna;
        }//end of for
        return populationBeforeEliminatingUnnessesaryChromosomeForP2;
    }//end of elimatingUnnessesaryChromosomeForP2()
    
    /************************************************************************************************
    * paretoSelectionForP2
    /************************************************************************************************/
    public PopulationForP2 paretoSelectionForP2(PopulationForP2 populationAfterFitnessCalculationForP2)
    {
        boolean flag[]=new boolean[populationAfterFitnessCalculationForP2.populationSizeForP2];
        for(int loopCounter=0;loopCounter<=populationAfterFitnessCalculationForP2.populationSizeForP2-1;loopCounter++)
        {
            flag[loopCounter]=true;
        }//end of for
        for(int outerLoopCounter=0;outerLoopCounter<=populationAfterFitnessCalculationForP2.populationSizeForP2-1;outerLoopCounter++)
        {
            float outerChromosomeTConfidence=populationAfterFitnessCalculationForP2.chromoForP2[outerLoopCounter].totalConfidence;
            float outerChromosomeTCoverage=populationAfterFitnessCalculationForP2.chromoForP2[outerLoopCounter].totalCoverage;
            float outerChromosomeInverseOfNumberOfCSRs=(float)0.0;
            if(populationAfterFitnessCalculationForP2.chromoForP2[outerLoopCounter].numberOfCSRs!=0)
            {
                outerChromosomeInverseOfNumberOfCSRs=(float)1/populationAfterFitnessCalculationForP2.chromoForP2[outerLoopCounter].numberOfCSRs;
            }//end of if
            else
            {
                outerChromosomeInverseOfNumberOfCSRs=(float)sortedPopulationFromP1MOGA.populationSize+100000;
            }//end of else
            for(int innerLoopCounter=0;innerLoopCounter<=populationAfterFitnessCalculationForP2.populationSizeForP2-1;innerLoopCounter++)
            {
                float innerChromosomeTConfidence=populationAfterFitnessCalculationForP2.chromoForP2[innerLoopCounter].totalConfidence;
                float innerChromosomeTCoverage=populationAfterFitnessCalculationForP2.chromoForP2[innerLoopCounter].totalCoverage;
                float innerChromosomeInverseOfNumberOfCSRs=(float)0.0;
                if(populationAfterFitnessCalculationForP2.chromoForP2[innerLoopCounter].numberOfCSRs!=0)
                {
                    innerChromosomeInverseOfNumberOfCSRs=(float)1/populationAfterFitnessCalculationForP2.chromoForP2[innerLoopCounter].numberOfCSRs;
                }//end of if
                else
                {
                    innerChromosomeInverseOfNumberOfCSRs=(float)sortedPopulationFromP1MOGA.populationSize+100000;
                }//end of else

                if((innerChromosomeTConfidence>outerChromosomeTConfidence&&innerChromosomeTCoverage>outerChromosomeTCoverage&&innerChromosomeInverseOfNumberOfCSRs>outerChromosomeInverseOfNumberOfCSRs)||
               (innerChromosomeTConfidence>outerChromosomeTConfidence&&innerChromosomeTCoverage>outerChromosomeTCoverage&&innerChromosomeInverseOfNumberOfCSRs==outerChromosomeInverseOfNumberOfCSRs)||
               (innerChromosomeTConfidence>outerChromosomeTConfidence&&innerChromosomeTCoverage==outerChromosomeTCoverage&&innerChromosomeInverseOfNumberOfCSRs>outerChromosomeInverseOfNumberOfCSRs)||
               (innerChromosomeTConfidence==outerChromosomeTConfidence&&innerChromosomeTCoverage>outerChromosomeTCoverage&&innerChromosomeInverseOfNumberOfCSRs>outerChromosomeInverseOfNumberOfCSRs)||
               (innerChromosomeTConfidence>outerChromosomeTConfidence&&innerChromosomeTCoverage==outerChromosomeTCoverage&&innerChromosomeInverseOfNumberOfCSRs==outerChromosomeInverseOfNumberOfCSRs)||
               (innerChromosomeTConfidence==outerChromosomeTConfidence&&innerChromosomeTCoverage>outerChromosomeTCoverage&&innerChromosomeInverseOfNumberOfCSRs==outerChromosomeInverseOfNumberOfCSRs)||
               (innerChromosomeTConfidence==outerChromosomeTConfidence&&innerChromosomeTCoverage==outerChromosomeTCoverage&&innerChromosomeInverseOfNumberOfCSRs>outerChromosomeInverseOfNumberOfCSRs))
                {
                    flag[outerLoopCounter]=false;
                    break;
                }//end of if
            }//end of for
        }//end of for
        int noOfParetoChromosome=0;
        for(int counter=0;counter<=populationAfterFitnessCalculationForP2.populationSizeForP2-1;counter++)
        {
            if(flag[counter])
                noOfParetoChromosome++;
        }//end of for
        ChromosomeForP2 chromoForP2[]=new ChromosomeForP2[noOfParetoChromosome];
        int counter1=0;
        for(int counter=0;counter<=populationAfterFitnessCalculationForP2.populationSizeForP2-1;counter++)
        {
            if(flag[counter])
            {
                chromoForP2[counter1]=populationAfterFitnessCalculationForP2.chromoForP2[counter];
                counter1++;
            }//end of if
        }//end of for
        PopulationForP2 populationAfterParetoSelectionForP2=new PopulationForP2(chromoForP2,counter1);
        return populationAfterParetoSelectionForP2;
    }//end of fitnessCalculationForP2()
    /***************************************************************************************************
     * returnTop20SortedPopulationForP2 method will return 20 chromosomesForP2 from sortedPopulationForP2
     *****************************************************************************************************/
    public PopulationForP2 returnTop20SortedPopulationForP2(PopulationForP2 sortedPopulationForP2)
    {
        //System.out.println("returnTop20SortedPopulationForP2 method");//for testing
        ChromosomeForP2 chromoForP2[]=new ChromosomeForP2[20];
        for(int counter=0;counter<=20-1;counter++)
        {
            chromoForP2[counter]=sortedPopulationForP2.chromoForP2[counter];
        }//end of for
        PopulationForP2 top20SortedPopulationForP2=new PopulationForP2(chromoForP2,20);
        return top20SortedPopulationForP2;
    }//end of returnTop20SortedPopulationForP2()
}//end of class
