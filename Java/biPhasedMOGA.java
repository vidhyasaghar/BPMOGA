public class biPhasedMOGA
{
    Dataset trainingDataSet;    
    int numberOfGenerationofBPMOGA;
    PopulationForP2 ParetoCRsFromBPMA;
    /************************************************************************************************
    * Constructor
    /************************************************************************************************/
    public biPhasedMOGA(Dataset traningDataSet1,Population initialPopulation1,int numberOfGenerationofBPMOGA1,int numberOfGenerationofP1MOGA1,int numberOfGenerationofP2MOGA1,int sizeOfInitialPopulationOfP2MOGA,float fractionOfTrainingData,float minCrossProbP11,float maxCrossProbP11,float minMuProbP11,float maxMuProbP11,float minRuleProbP21,float maxRuleProbP21,float minCrossProbP21,float maxCrossProbP21,float minMuProbP21,float maxMuProbP21)
    {
        trainingDataSet=traningDataSet1;        
        numberOfGenerationofBPMOGA=numberOfGenerationofBPMOGA1;
        phase2MOGA P2MOGA=new phase2MOGA();
        PopulationForP2 earlierGenPopulationForP2=new PopulationForP2();
        PopulationForP2 afterParetoSelectionPopulationForP2=new PopulationForP2();
        for(int counter=0;counter<=numberOfGenerationofBPMOGA1/numberOfGenerationofP1MOGA1-1;counter++)
        {
            System.out.println("counter="+counter);//for testing
            phase1MOGA P1MOGA=new phase1MOGA(trainingDataSet,initialPopulation1,numberOfGenerationofBPMOGA,numberOfGenerationofP1MOGA1,fractionOfTrainingData,minCrossProbP11,maxCrossProbP11,minMuProbP11,maxMuProbP11);
            /*System.out.println("Population by P1MOGA");//for testing
            P1MOGA.paretoPopulation.showPopulation();
            System.out.println("____________________________");//for testing*/
            P2MOGA=new phase2MOGA(counter,trainingDataSet,P1MOGA.paretoPopulation,numberOfGenerationofBPMOGA,numberOfGenerationofP2MOGA1,sizeOfInitialPopulationOfP2MOGA,minRuleProbP21,maxRuleProbP21,minCrossProbP21,maxCrossProbP21,minMuProbP21,maxMuProbP21);
            PopulationForP2 combinedPopulationForP2=new PopulationForP2();
            Population populationForP1FromP2=new Population();
            
            //System.out.println("earlierGenPopulationForP2="+earlierGenPopulationForP2.populationSizeForP2);//for testing
            combinedPopulationForP2=P2MOGA.combinationForP2(earlierGenPopulationForP2,P2MOGA.populationParetoSelectionForP2);
            //System.out.println("combinedPopulationForP2="+combinedPopulationForP2.populationSizeForP2);//for testing
            /*for(int loopCounter=0;loopCounter<=combinedPopulationForP2.populationSizeForP2-1;loopCounter++)
            {
                combinedPopulationForP2.chromoForP2[loopCounter].showDnaOfChromosomeForP2();
                combinedPopulationForP2.chromoForP2[loopCounter].sortedPopulationByP1.showPopulation();
            }//end of for*/
            /*combinedPopulationForP2.showPopulationForP2();
            System.out.println("______________________________");//for testing*/
            PopulationForP2 afterEliminatingDuplecatePopulationForP2=eliminateDuplicateForP2(combinedPopulationForP2);
            //System.out.println("afterEliminatingDuplecatePopulationForP2="+afterEliminatingDuplecatePopulationForP2.populationSizeForP2);//for testing
            /*for(int loopCounter=0;loopCounter<=afterEliminatingDuplecatePopulationForP2.populationSizeForP2-1;loopCounter++)
            {
                afterEliminatingDuplecatePopulationForP2.chromoForP2[loopCounter].showDnaOfChromosomeForP2();
                afterEliminatingDuplecatePopulationForP2.chromoForP2[loopCounter].sortedPopulationByP1.showPopulation();
            }//end of for*/
            /*afterEliminatingDuplecatePopulationForP2.showPopulationForP2();
            System.out.println("______________________________");//for testing*/            
            afterParetoSelectionPopulationForP2=P2MOGA.paretoSelectionForP2(afterEliminatingDuplecatePopulationForP2);
            if(afterParetoSelectionPopulationForP2.populationSizeForP2>20)
            {
                PopulationForP2 sortedPopulationForP2=afterParetoSelectionPopulationForP2.sortingChromoOfP2();
                PopulationForP2 top20SortedPopulationForP2=P2MOGA.returnTop20SortedPopulationForP2(sortedPopulationForP2);
                afterParetoSelectionPopulationForP2=top20SortedPopulationForP2;
            }//end of if
            //System.out.println("afterParetoSelectionPopulationForP2="+afterParetoSelectionPopulationForP2.populationSizeForP2);//for testing
            //afterParetoSelectionPopulationForP2.showPopulationForP2();
            //afterParetoSelectionPopulationForP2.findMaxTotalConfidenceMaxTotalCoverageMinNORules();
            //System.out.println("______________________________");//for testing
            Population initialPopulationForP1FromP2=generatePopulationForP1(afterParetoSelectionPopulationForP2);
            //System.out.println("initialPopulationForP1FromP2.populationSize="+initialPopulationForP1FromP2.populationSize);//for testing
            populationForP1FromP2=initialPopulationForP1FromP2.paretoSelection();
            /*System.out.println("populationForP1FromP2.populationSize="+populationForP1FromP2.populationSize);//for testing
            populationForP1FromP2.showDnaOfPopulation();//for testing
            System.out.println("______________________________");//for testing*/
            
            earlierGenPopulationForP2=afterParetoSelectionPopulationForP2;
            //System.out.println("earlierGenPopulationForP2="+earlierGenPopulationForP2.populationSizeForP2);//for testing
            //populationForP1FromP2.showDnaOfPopulation();            
            //Population combinedPopulationForP1=P1MOGA.combination(initialPopulation1,populationForP1FromP2);
            //System.out.println("combinedPopulationForP1="+combinedPopulationForP1.populationSize);//for testing
            //Population afterDuplecateElimination=combinedPopulationForP1.eliminateDuplecate();
            //System.out.println("afterDuplecateElimination="+afterDuplecateElimination.populationSize);//for testing
            //Population afterFitnessCalculation=afterDuplecateElimination.fitnessCalculation(trainingDataSet);
            //afterFitnessCalculation.showPopulation();
            //Population afterParetoSelection=afterFitnessCalculation.paretoSelection();
            //System.out.println("afterParetoSelection="+afterParetoSelection.populationSize);//for testing
            initialPopulation1=populationForP1FromP2;
            afterParetoSelectionPopulationForP2.findMaxTotalConfidenceMaxTotalCoverageMinNORules();//for testing
            //initialPopulation1.showPopulation();
            //System.out.println("______________________________");//for testing
        }//end of for
        //afterParetoSelectionPopulationForP2.showPopulationForP2();//for testing
        //System.out.println("______________________________");//for testing
        PopulationForP2 sortedPopulationForP2=afterParetoSelectionPopulationForP2.sortingChromoOfP2();
        //PopulationForP2 top20SortedPopulationForP2=P2MOGA.returnTop20SortedPopulationForP2(sortedPopulationForP2);
        ParetoCRsFromBPMA=sortedPopulationForP2;
        //ParetoCRsFromBPMA.showPopulationForP2();//for testing
    }//end of constructor
    /************************************************************************************************
    * generatePopulationForP1() method. This method will generate population for P1 from P2
    /************************************************************************************************/
    public Population generatePopulationForP1(PopulationForP2 populationFromP2)
    {
        Population populationForP1FromP2=new Population();
        //System.out.println("populationSizeForP2="+populationFromP2.populationSizeForP2);//for testing
        //populationFromP2.showDnaOfPopulationForP2();//for testing
        /*int startIndex=0;
        int endIndex=0;
        int numberOfChromosomesFromP2ForP1=0;*/
        /************************************************************************************************
        * find the range of chromosomes
        /************************************************************************************************/
        int noOfChromo=0;
        for(int outerCounter=0;outerCounter<=populationFromP2.populationSizeForP2-1;outerCounter++)
        {
            for(int innerCounter=0;innerCounter<=populationFromP2.chromoForP2[outerCounter].DnaForP2.length()-1;innerCounter++)
            {
                if(populationFromP2.chromoForP2[outerCounter].DnaForP2.charAt(innerCounter)=='1')
                    noOfChromo++;
            }//end of for
        }//end of for
        //System.out.println("noOfChromo="+noOfChromo);//for testing
        Chromosome chromosome1[]=new Chromosome[noOfChromo];
        Chromosome chromosome2[]=new Chromosome[noOfChromo];
        int chromoCounter=0;
        for(int outerCounter=0;outerCounter<=populationFromP2.populationSizeForP2-1;outerCounter++)
        {
            for(int innerCounter=0;innerCounter<=populationFromP2.chromoForP2[outerCounter].DnaForP2.length()-1;innerCounter++)
            {
                if(populationFromP2.chromoForP2[outerCounter].DnaForP2.charAt(innerCounter)=='1')
                {
                    chromosome1[chromoCounter]=populationFromP2.chromoForP2[outerCounter].sortedPopulationByP1.chromo[innerCounter];
                    chromoCounter++;
                }//end of if
            }//end of for
        }//end of for
        boolean flag[]=new boolean[noOfChromo];
        for(int outerCounter=0;outerCounter<=noOfChromo-1;outerCounter++)
        {
            flag[outerCounter]=true;
        }//end of for
        chromoCounter=0;
        for(int outerCounter=0;outerCounter<=noOfChromo-1;outerCounter++)
        {
            if(flag[outerCounter])
            {
                chromosome2[chromoCounter]=chromosome1[outerCounter];
                chromoCounter++;
                for(int innerCounter=outerCounter+1;innerCounter<=noOfChromo-1;innerCounter++)
                {
                    if(chromosome1[outerCounter].Dna.equals(chromosome1[innerCounter].Dna))
                        flag[innerCounter]=false;
                }//end of for
            }//end of if
        }//end of for
        Chromosome chromosome3[]=new Chromosome[chromoCounter];
        for(int outerCounter=0;outerCounter<=chromoCounter-1;outerCounter++)
        {
            chromosome3[outerCounter]=chromosome2[outerCounter];
        }//end of for
        //System.out.println("chromoCounter="+chromoCounter);//for testing
        populationForP1FromP2=new Population(chromosome3);
        return populationForP1FromP2;
    }//end of generatePopulationForP1()    
    /************************************************************************************************
    * eliminateDuplicateForP2() method. This method will eliminate Duplicate chromosomes from defferent
     * runs of phase-II. Defferent runs of phase-II may generate same chroamosomes DNA but CSRs may
     * be different. So DNA may be same but CR may be different.
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
                    //System.out.println("Chromosomes are same");//for testing
                    for(int innerLoopCounter1=0;innerLoopCounter1<outerChromosome.length()-1;innerLoopCounter1++)
                    {
                        if(outerChromosome.charAt(innerLoopCounter1)=='1')
                        {
                            if(populationBeforeEliminateDuplicateForP2.chromoForP2[outerLoopCounter].sortedPopulationByP1.chromo[innerLoopCounter1].Dna.equals(populationBeforeEliminateDuplicateForP2.chromoForP2[innerLoopCounter].sortedPopulationByP1.chromo[innerLoopCounter1].Dna))
                            {
                                //do nothing
                            }//end of if
                            else
                            {
                                flag[innerLoopCounter]=true;
                                //System.out.println("CSRs are different");//for testing
                                break;
                            }//end of else
                        }//end of if
                    }//end of innerLoopCounter1
                }//end of if
            }//end of innerForLoop
        }//end of outerForLoop
        int counter1=0;
        for(int counter=0;counter<=populationBeforeEliminateDuplicateForP2.populationSizeForP2-1;counter++)
        {
            //System.out.println(flag[counter]);//for testing
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
}///end of class
