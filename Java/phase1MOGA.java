import java.util.*;
public class phase1MOGA
{
    Dataset trainingDataSet;
    Population initialPopulation;
    int numberOfGenerationofBPMOGA;
    int numberOfGenerationofP1MOGA;
    float fractionOfTrainingData;
    float minCrossProbP1;
    float maxCrossProbP1;
    float minMuProbP1;
    float maxMuProbP1;
    Population paretoPopulation;
    Population buildedPopulation;
    /************************************************************************************************
    * Constructor
    /************************************************************************************************/
    public phase1MOGA(Dataset traningDataSet1,Population initialPopulation1,int numberOfGenerationofBPMOGA1,int numberOfGenerationofP1MOGA1,float fractionOfTrainingData1,float minCrossProbP11,float maxCrossProbP11,float minMuProbP11,float maxMuProbP11)
    {
        //int generationBPMOGA=0;
        trainingDataSet=traningDataSet1;
        initialPopulation=initialPopulation1;
        numberOfGenerationofBPMOGA=numberOfGenerationofBPMOGA1;
        numberOfGenerationofP1MOGA=numberOfGenerationofP1MOGA1;
        fractionOfTrainingData=fractionOfTrainingData1;
        minCrossProbP1=minCrossProbP11;
        maxCrossProbP1=maxCrossProbP11;
        minMuProbP1=minMuProbP11;
        maxMuProbP1=maxMuProbP11;
        paretoPopulation=initialPopulation1;
        for(int generationP1=0;generationP1<=numberOfGenerationofP1MOGA-1;generationP1++)
        {
            //generationBPMOGA++;
            System.out.println("generationP1="+generationP1);//for testing            
            /************************************************************************************************************
            *For every alternative run we are building chromosome from pareto rules of previous generation.
            *If no rule is there then rules are made from records by calling buildInitialPopulation method
            ************************************************************************************************************/
            if(generationP1==0)
            {
                buildedPopulation=buildChromosomeForGA(initialPopulation);
                //System.out.println("For 1st generation="+generationP1);//for testing
            }//end of if
            else if(generationP1%2!=0)
            {
                buildedPopulation=paretoPopulation;
                //System.out.println("For odd generation="+generationP1);//for testing
            }//end of if
            else if(generationP1%2==0&generationP1!=0)
            {
                initialPopulation=new Population(trainingDataSet,fractionOfTrainingData);
                /*System.out.println("Population after initialPopulation creation");//for testing
                initialPopulation.showPopulation();//for testing
                System.out.println("____________________________");//for testing*/
                buildedPopulation=buildChromosomeForGA(initialPopulation);
                /*System.out.println("Population after buildedPopulation creation");//for testing
                buildedPopulation.showPopulation();//for testing
                System.out.println("____________________________");//for testing*/
                //System.out.println("For even generation="+generationP1);//for testing
            }//end of if           
            /*System.out.println("Population before Crossover");//for testing
            buildedPopulation.showPopulation();//for testing
            System.out.println("____________________________");//for testing*/
            float crossoverProbability=calCrossoverProbability(generationP1,numberOfGenerationofP1MOGA,minCrossProbP1,maxCrossProbP1);
            /*System.out.println("Crossover Probability="+crossoverProbability);//for testing
            System.out.println("____________________________");//for testing*/
            Population populationAfterCrossover=crossover(buildedPopulation,crossoverProbability);
            /*System.out.println("Population after Crossover");//for testing
            populationAfterCrossover.showPopulation();//for testing
            System.out.println("____________________________");//for testing
            //Population populationAfterEliminatingInvalidChromosomesAfterCrossover=eliminateInvalidChromosomes(populationAfterCrossover);
            /*System.out.println("Population after Eliminating Invalid Chromosomes After Crossover");//for testing
            populationAfterEliminatingInvalidChromosomesAfterCrossover.showDnaOfPopulation();//for testing
            System.out.println("____________________________");//for testing*/
            float mutationProbability=calMutationProbability(generationP1,numberOfGenerationofP1MOGA,minMuProbP1,maxMuProbP1);
            /*System.out.println("Mutation Probability="+mutationProbability);//for testing
            System.out.println("____________________________");//for testing*/
            Population populationAfterMutation=mutation(buildedPopulation,mutationProbability);
            /*System.out.println("Population after Mutation");//for testing
            populationAfterMutation.showPopulation();//for testing
            System.out.println("____________________________");//for testing
            //Population populationAfterEliminatingInvalidChromosomesAfterMutation=eliminateInvalidChromosomes(populationAfterMutation);
            /*System.out.println("Population after Eliminating Invalid Chromosomes After Mutation");//for testing
            populationAfterEliminatingInvalidChromosomesAfterMutation.showDnaOfPopulation();//for testing
            System.out.println("____________________________");//for testing*/
            /*System.out.println("Previous generation Pareto Population");//for testing
            paretoPopulation.showDnaOfPopulation();//for testing
            System.out.println("____________________________");//for testing*/
            
            Population populationAfterCombination=combination(paretoPopulation,populationAfterCrossover,populationAfterMutation);
            /*System.out.println("Population after Combination");//for testing
            populationAfterCombination.showPopulation();//for testing
            System.out.println("____________________________");//for testing*/

            Population populationAfterEliminateMeaninglessCondition=eliminateMeaninglessCondition(populationAfterCombination);
            /*System.out.println("Population after Eliminating Meaningless Conditions");//for testing
            populationAfterEliminateMeaninglessCondition.showPopulation();//for testing
            System.out.println("____________________________");//for testing*/

            Population populationAfterEliminatingInvalidChromosomes=eliminateInvalidChromosomes(populationAfterEliminateMeaninglessCondition);
            /*System.out.println("Population after Eliminating Invalid Chromosomes After Combination");//for testing
            populationAfterEliminatingInvalidChromosomes.showPopulation();//for testing
            System.out.println("____________________________");//for testing*/

            Population populationAfterEliminatingDuplicate=eliminateDuplicate(populationAfterEliminatingInvalidChromosomes);
            /*System.out.println("Population after Eliminating Duplicate");//for testing
            populationAfterEliminatingDuplicate.showPopulation();//for testing
            System.out.println("____________________________");//for testing*/

            populationAfterEliminatingDuplicate=fitnessCalculation(populationAfterEliminatingDuplicate);
            /*System.out.println("Population after Fitness Calculation");//for testing
            populationAfterEliminatingDuplicate.showPopulation();//for testing
            System.out.println("____________________________");//for testing*/

            paretoPopulation=paretoSelection(populationAfterEliminatingDuplicate);
            //System.out.println("Population size after Pareto Selection="+paretoPopulation.populationSize);//for testing
            if(paretoPopulation.populationSize>1000)//to limit population size of Phase1 to 1000
            {
                Population sortedPopulation=paretoPopulation.sortingCSRs();
                Population top1000SortedPopulation=returnTop1000SortedPopulation(sortedPopulation);
                paretoPopulation=top1000SortedPopulation;
            }//end of if
            /*System.out.println("Population after Pareto Selection");//for testing
            paretoPopulation.showPopulation();//for testing
            System.out.println("____________________________");//for testing*/

            //System.out.println(paretoPopulation.populationSize);//for testing
            //System.out.println("____________________________");//for testing
            //paretoPopulation.findMaxConfidenceAndMaxCoveragePerClass(trainingDataSet);
            //System.out.println("____________________________");//for testing
        }//end of for
    }//end of constructor
    /************************************************************************************************
     * cromosomeForGA() method. This method will construct cromosome from population
    /************************************************************************************************/
    public Population buildChromosomeForGA(Population pop)
    {
         //System.out.println("buildChromosomeForGA method");//for testing
         Chromosome chromo[];
         Population buildPopulation;
         /****************************************************************************************************************
         * To find if all attributes having valid value or not. If contains valid values then flag=true else flag=false
         ******************************************************************************************************************/
         boolean flag=true;
         for(int counter1=0;counter1<=pop.populationSize-1;counter1++)
         {
             int start=0;
             int end=0;
             String value="";
             for(int i=0;i<=pop.chromo[counter1].Dna.length()-1;i++)
             {
                if(pop.chromo[counter1].Dna.charAt(i)=='|')
                {
                    end=i;
                    value=pop.chromo[counter1].Dna.substring(start,end);
                    start=end+1;
                }//end of if
                if(value.equals("#"))
                {
                    flag=false;
                    break;
                }//end of if
             }//end of for
             if(!flag)
                 break;
         }//end of for
         //System.out.println("flag="+flag);//for testing
         /*****************************************************************************************************************
         *  If flag=true then all attribute having valid values. Then any perticular combination of attributes can be chosen
         * for building chromosome for next generation
         *******************************************************************************************************************/
         if(flag)
         {
            chromo=new Chromosome[pop.populationSize];
             /********************************************************************************************************
            * To find no of Attributes
            ********************************************************************************************************/             
             int noOfAttributes=trainingDataSet.noOfAttributes-1;
             /********************************************************************************************************/            
            /********************************************************************************************************
            * Building Chromosomes by randomly choosing attributes. Minimum 1 attribute will be chosen
            ********************************************************************************************************/
            double sumOfInformationGain=0.0;
            for(int attributeNo=0;attributeNo<=noOfAttributes-1;attributeNo++)
            {
                 sumOfInformationGain=sumOfInformationGain+trainingDataSet.informationGain[attributeNo];
            }//end of for
            double featureSelectionProbability[]=new double[noOfAttributes];
            for(int attributeNo=0;attributeNo<=noOfAttributes-1;attributeNo++)
            {
                 featureSelectionProbability[attributeNo]=(double)(trainingDataSet.informationGain[attributeNo]/sumOfInformationGain);
                 //featureSelectionProbability[attributeNo]=(double)(1.0/noOfAttributes);
                 //System.out.println("featureSelectionProbability["+attributeNo+"]="+featureSelectionProbability[attributeNo]);//for testing
            }//end of for            
             //double featureSelectionProbability=1.0/noOfAttributes;
             //System.out.println("featureSelectionProbability="+featureSelectionProbability);//for testing
             for(int counter1=0;counter1<=pop.populationSize-1;counter1++)
             {                
                 boolean flag1=true;
                 do
                 {
                     String DnaChromosome="";
                     for(int attributeNo=0;attributeNo<=noOfAttributes-1;attributeNo++)
                     {
                        if(Math.random()<=featureSelectionProbability[attributeNo])
                        {
                            DnaChromosome=DnaChromosome.concat(pop.chromo[counter1].gen[attributeNo].geneValue).concat("|");
                            flag1=false;
                        }//end of if
                        else
                            DnaChromosome=DnaChromosome.concat("#").concat("|");
                     }//end of for
                     chromo[counter1]=new Chromosome(DnaChromosome,trainingDataSet);
                 }while(flag1);
             }//end of for
             buildPopulation=new Population(chromo);
         }//end of if
          /*****************************************************************************************************************
         *  If flag=false then all attribute don't have valid values. Chromosomes are build from pareto population of
         * previous iteration
         *******************************************************************************************************************/
         else
         {
             buildPopulation=pop;
         }//end of if         
         return buildPopulation;          
    }//end of cromosomeForGA method
    /******************************************************************************************************
     *method calCrossoverProbability(). This method will determine Crossover Probability for a perticular
     *generation. At biginning crossover probability will be lower and at the end it will be higher.
     ******************************************************************************************************/
    public float calCrossoverProbability(int generationOfP1Number,int maxNumberOfGenerationOfP1,float minCrossoverProbability,float maxCrossoverProbability)
    {        
        /*System.out.println("generationOfP1Number"+generationOfP1Number);//for testing
        System.out.println("maxNumberOfGenerationOfP1"+maxNumberOfGenerationOfP1);//for testing*/
        float crossoverProbability=(float)(maxCrossoverProbability-minCrossoverProbability)*generationOfP1Number/(maxNumberOfGenerationOfP1-1)+minCrossoverProbability;
        return crossoverProbability;
    }//end of getCrossoverProbability()
    /******************************************************************************************************
     *method crossover(). This method will do single point chrossover among chromosome
     ******************************************************************************************************/
    public Population crossover(Population popBeforeCrossover,float crossoverProbability)
    {
        //System.out.println("Crossover");//for testing
        //System.out.println("Sizeof population before crossover="+popBeforeCrossover.populationSize);//for testing
        boolean flag[]=new boolean[popBeforeCrossover.populationSize];
        for(int counter=0;counter<=popBeforeCrossover.populationSize-1;counter++)
        {
            flag[counter]=true;
        }//end of for
        /******************************************************************************************************
        *to calculate no of points where crossover will occur
        ******************************************************************************************************/
        int noOfCrossover=(int)(popBeforeCrossover.populationSize*crossoverProbability);
        //System.out.println("noOfCrossover="+noOfCrossover);//for testing
        Chromosome chromo[]=new Chromosome[noOfCrossover*2];
        int counter11 = -1;
        for(int counter=0;counter<=noOfCrossover-1;counter++)
        {
            /******************************************************************************************************
            *choosing two chromosomes randomly for crossover
            ******************************************************************************************************/
            int randomNo1=(int)(Math.random()*popBeforeCrossover.populationSize);
            int randomNo2=(int)(Math.random()*popBeforeCrossover.populationSize);
            //String Dna1=popBeforeCrossover.chromo[randomNo1].Dna;
            //String Dna2=popBeforeCrossover.chromo[randomNo2].Dna;
            while(randomNo1==randomNo2|flag[randomNo1]==false|flag[randomNo2]==false)
            {
                randomNo1=(int)(Math.random()*popBeforeCrossover.populationSize);
                randomNo2=(int)(Math.random()*popBeforeCrossover.populationSize);                
            }//end of while
            String Dna1=popBeforeCrossover.chromo[randomNo1].Dna;
            String Dna2=popBeforeCrossover.chromo[randomNo2].Dna;
            //System.out.println("randomNo1="+randomNo1);//for testing
            //System.out.println("randomNo2="+randomNo2);//for testing
            //System.out.println("Dna1="+Dna1);//for testing
            //System.out.println("Dna2="+Dna2);//for testing
            /******************************************************************************************************
            *making them false to prevent repeated choosing
            ******************************************************************************************************/
            flag[randomNo1]=false;
            flag[randomNo2]=false;
            int noOfPossibleCrossoverPoints=trainingDataSet.noOfAttributes-1;
            //System.out.println("noOfPossibleCrossoverPoints="+noOfPossibleCrossoverPoints);//for testing
            /******************************************************************************************************
            *to choose one perticular point randomly
            ******************************************************************************************************/
            int randomNo3=(int)(Math.random()*noOfPossibleCrossoverPoints);
            //System.out.println("randomNo3="+randomNo3);//for testing
            /******************************************************************************************************
            *to choose a perticular point having '|' for crossover
            ******************************************************************************************************/
            int j=-1;
            int startIndex1=0;
            int endIndex1=0;
            for(int i=0;i<=Dna1.length()-2;i++)
            {
                if(Dna1.charAt(i)=='|')
                {
                    j++;
                    if(j==randomNo3)
                    {
                        endIndex1=i;
                        break;
                    }//end of if
                }//end of if
            }//end of for
            /******************************************************************************************************
            *to divide Dna1 into leftDna1 and rightDna1
            ******************************************************************************************************/
            String leftDna1="";
            String rightDna1="";
            if(startIndex1==0&&endIndex1==0)
            {
                leftDna1=Dna1.substring(startIndex1,endIndex1);
                rightDna1=Dna1.substring(endIndex1);
            }//end of if
            else
            {
                leftDna1=Dna1.substring(startIndex1,endIndex1+1);
                rightDna1=Dna1.substring(endIndex1+1);
            }//end of else
            //System.out.println("leftDna1="+leftDna1);//for testing
            //System.out.println("rightDna1="+rightDna1);//for testing
            /******************************************************************************************************
            *to choose a perticular point having '|' for crossover
            ******************************************************************************************************/
            j=-1;
            int startIndex2=0;
            int endIndex2=0;
            for(int i=0;i<=Dna2.length()-2;i++)
            {
                if(Dna2.charAt(i)=='|')
                {
                    j++;
                    if(j==randomNo3)
                    {
                        endIndex2=i;
                        break;
                    }//end of if
                }//end of if
            }//end of for
            /******************************************************************************************************
            *to divide Dna2 into leftDna2 and rightDna2
            ******************************************************************************************************/
            String leftDna2="";
            String rightDna2="";
            if(startIndex2==0&&endIndex2==0)
            {
                leftDna2=Dna2.substring(startIndex2,endIndex2);
                rightDna2=Dna2.substring(endIndex2);
            }//end of if
            else
            {
                leftDna2=Dna2.substring(startIndex2,endIndex2+1);
                rightDna2=Dna2.substring(endIndex2+1);
            }//end of else
            //System.out.println("leftDna2="+leftDna2);//for testing
            //System.out.println("rightDna2="+rightDna2);//for testing
            /******************************************************************************************************
            *find out type of attribute where crossover will occur
            ******************************************************************************************************/
            int counter8=0;
            for(int i=0;i<=leftDna1.length()-1;i++)
            {
                if(leftDna1.charAt(i)=='|')
                    counter8++;
            }//end of for
            String attributesType=trainingDataSet.attributeInformation[1][counter8];            
            //System.out.println("attributesType="+attributesType);//for testing
            /****************************************************************************************
             * First part of rightDnas will be replaced by a string.
             * So we are finding the position of first '|' in rightDna1 and rightDna2.
             * endIndex3 and endIndex4 will hold that value
             ****************************************************************************************/
            int startIndex3=0;
            int endIndex3=0;
            for(int i=0;i<=rightDna1.length()-1;i++)
            {
                if(rightDna1.charAt(i)=='|')
                {
                    endIndex3=i;
                    break;
                }//end of if
            }//end of for
            int startIndex4=0;
            int endIndex4=0;
            for(int i=0;i<=rightDna2.length()-1;i++)
            {
                if(rightDna2.charAt(i)=='|')
                {
                    endIndex4=i;
                    break;
                }//end of if
            }//end of for
            /****************************************************************************************
             * Constructing first and last PartOfRightDna1
             ****************************************************************************************/
            String firstPartOfRightDna1=rightDna1.substring(startIndex3,endIndex3);
            String lastPartOfRightDna1=rightDna1.substring(endIndex3);            
            //System.out.println("firstPartOfRightDna1="+firstPartOfRightDna1);//for testing
            //System.out.println("lastPartOfRightDna1="+lastPartOfRightDna1);//for testing
            /****************************************************************************************
             * Constructing first and last PartOfRightDna1
             ****************************************************************************************/
            String firstPartOfRightDna2=rightDna2.substring(startIndex4,endIndex4);
            String lastPartOfRightDna2=rightDna2.substring(endIndex4);            
            //System.out.println("firstPartOfRightDna2="+firstPartOfRightDna2);//for testing
            //System.out.println("lastPartOfRightDna2="+lastPartOfRightDna2);//for testing
            /****************************************************************************************
             * If two parts are same (may be "#") or If one part is equal to "#"
             * and other part having valid value then simple crossover will take place
             ****************************************************************************************/
            if(firstPartOfRightDna1.equals(firstPartOfRightDna2)||firstPartOfRightDna1.equals("#")||firstPartOfRightDna2.equals("#"))
            {
                counter11++;
                chromo[counter11]=new Chromosome(leftDna1.concat(rightDna2),trainingDataSet);
                counter11++;
                chromo[counter11]=new Chromosome(leftDna2.concat(rightDna1),trainingDataSet);
                //chromo[randomNo1]=new Chromosome(leftDna1.concat(rightDna2),trainingDataSet);
                //chromo[randomNo2]=new Chromosome(leftDna2.concat(rightDna1),trainingDataSet);
                //System.out.println("chromo[randomNo1]="+chromo[randomNo1].Dna);//for testing
                //System.out.println("chromo[randomNo2]="+chromo[randomNo2].Dna);//for testing
            }//end of if
            /****************************************************************************************
             * If both part having valid values
             ****************************************************************************************/
            else if(attributesType.equals("Integer")||attributesType.equals("Float"))
            {
                int startIndex5=0;
                int endIndex5=0;
                for(int i=0;i<=firstPartOfRightDna1.length()-1;i++)
                {
                    if(firstPartOfRightDna1.charAt(i)==',')
                    {
                        endIndex5=i;
                        break;
                    }//end of if
                }//end of for
                String leftPartOfFirstPartOfRightDna1=firstPartOfRightDna1.substring(startIndex5, endIndex5);
                String rightPartOfFirstPartOfRightDna1=firstPartOfRightDna1.substring(endIndex5+1);
                //System.out.println("leftPartOfFirstPartOfRightDna1="+leftPartOfFirstPartOfRightDna1);//for testing
                //System.out.println("rightPartOfFirstPartOfRightDna1="+rightPartOfFirstPartOfRightDna1);//for testing

                int startIndex6=0;
                int endIndex6=0;
                for(int i=0;i<=firstPartOfRightDna2.length()-1;i++)
                {
                    if(firstPartOfRightDna2.charAt(i)==',')
                    {
                        endIndex6=i;
                        break;
                    }//end of if
                }//end of for
                String leftPartOfFirstPartOfRightDna2=firstPartOfRightDna2.substring(startIndex6,endIndex6);
                String rightPartOfFirstPartOfRightDna2=firstPartOfRightDna2.substring(endIndex6+1);
                //System.out.println("leftPartOfFirstPartOfRightDna2="+leftPartOfFirstPartOfRightDna2);//for testing
                //System.out.println("rightPartOfFirstPartOfRightDna2="+rightPartOfFirstPartOfRightDna2);//for testing
                String substitute1="";
                String substitute2="";
                if(!leftPartOfFirstPartOfRightDna1.equals("#")&!rightPartOfFirstPartOfRightDna2.equals("#"))
                {
                    if(Float.parseFloat(leftPartOfFirstPartOfRightDna1)<=Float.parseFloat(rightPartOfFirstPartOfRightDna2))
                    {
                        substitute1=leftPartOfFirstPartOfRightDna1.concat(",").concat(rightPartOfFirstPartOfRightDna2);
                    }//end of if
                    else
                    {
                        substitute1=rightPartOfFirstPartOfRightDna2.concat(",").concat(leftPartOfFirstPartOfRightDna1);
                    }//end of else
                }//end of if
                else
                {
                    substitute1=leftPartOfFirstPartOfRightDna1.concat(",").concat(rightPartOfFirstPartOfRightDna2);
                }//end of else
                if(!leftPartOfFirstPartOfRightDna2.equals("#")&!rightPartOfFirstPartOfRightDna1.equals("#"))
                {
                    if(Float.parseFloat(leftPartOfFirstPartOfRightDna2)<=Float.parseFloat(rightPartOfFirstPartOfRightDna1))
                    {
                        substitute2=leftPartOfFirstPartOfRightDna2.concat(",").concat(rightPartOfFirstPartOfRightDna1);
                    }//end of if
                    else
                    {
                        substitute2=rightPartOfFirstPartOfRightDna1.concat(",").concat(leftPartOfFirstPartOfRightDna2);
                    }//end of else
                }//end of if
                else
                {
                    substitute2=leftPartOfFirstPartOfRightDna2.concat(",").concat(rightPartOfFirstPartOfRightDna1);
                }//end of else
                //System.out.println("substitute1="+substitute1);//for testing
                //System.out.println("substitute2="+substitute2);//for testing
                //chromo[randomNo1]=new Chromosome(leftDna1.concat(substitute1).concat(lastPartOfRightDna2),trainingDataSet);
                //chromo[randomNo2]=new Chromosome(leftDna2.concat(substitute2).concat(lastPartOfRightDna1),trainingDataSet);
                counter11++;
                chromo[counter11]=new Chromosome(leftDna1.concat(substitute1).concat(lastPartOfRightDna2),trainingDataSet);
                counter11++;
                chromo[counter11]=new Chromosome(leftDna2.concat(substitute2).concat(lastPartOfRightDna1),trainingDataSet);
            }//end of if
            /******************************************************************************************************
             * Simple binary one point crossover
            ******************************************************************************************************/
            else if(attributesType.equals("Binary")||attributesType.equals("String"))
            {                
                //System.out.println("attributesType="+attributesType);//for testing
                int randomNo=(int)(Math.random()*firstPartOfRightDna1.length()-1)+1;
                //System.out.println("randomNo="+randomNo);//for testing
                //System.out.println("firstPartOfRightDna1="+firstPartOfRightDna1);//for testing
                String leftPartOfFirstPartOfRightDna1=firstPartOfRightDna1.substring(0,randomNo);
                //System.out.println("leftPartOfFirstPartOfRightDna1="+leftPartOfFirstPartOfRightDna1);//for testing
                String rightPartOfFirstPartOfRightDna1=firstPartOfRightDna1.substring(randomNo,firstPartOfRightDna1.length());
                //System.out.println("rightPartOfFirstPartOfRightDna1="+rightPartOfFirstPartOfRightDna1);//for testing
                //System.out.println("firstPartOfRightDna2="+firstPartOfRightDna2);//for testing
                String leftPartOfFirstPartOfRightDna2=firstPartOfRightDna2.substring(0,randomNo);
                //System.out.println("leftPartOfFirstPartOfRightDna2="+leftPartOfFirstPartOfRightDna2);//for testing
                String rightPartOfFirstPartOfRightDna2=firstPartOfRightDna2.substring(randomNo,firstPartOfRightDna2.length());
                //System.out.println("rightPartOfFirstPartOfRightDna2="+rightPartOfFirstPartOfRightDna2);//for testing
                String substitute1=leftPartOfFirstPartOfRightDna1.concat(rightPartOfFirstPartOfRightDna2);
                String substitute2=leftPartOfFirstPartOfRightDna2.concat(rightPartOfFirstPartOfRightDna1);
                //System.out.println("substitute1="+substitute1);//for testing
                //System.out.println("substitute2="+substitute2);//for testing
                //chromo[randomNo1]=new Chromosome(leftDna1.concat(substitute1).concat(lastPartOfRightDna2),trainingDataSet);
                //chromo[randomNo2]=new Chromosome(leftDna2.concat(substitute2).concat(lastPartOfRightDna1),trainingDataSet);
                counter11++;
                chromo[counter11]=new Chromosome(leftDna1.concat(substitute1).concat(lastPartOfRightDna2),trainingDataSet);
                counter11++;
                chromo[counter11]=new Chromosome(leftDna2.concat(substitute2).concat(lastPartOfRightDna1),trainingDataSet);
                //System.out.println("chromo[randomNo1]="+chromo[randomNo1].Dna);//for testing
                //System.out.println("chromo[randomNo2]="+chromo[randomNo2].Dna);//for testing
                //System.out.println("_____________________________");//for testing
            }//end of if
            //System.out.println("popBeforeCrossover["+randomNo1+"].Dna="+popBeforeCrossover.chromo[randomNo1].Dna);//for testing
            //System.out.println("popBeforeCrossover["+randomNo2+"].Dna="+popBeforeCrossover.chromo[randomNo2].Dna);//for testing
            //System.out.println("chromo[randomNo1].Dna="+chromo[randomNo1].Dna);//for testing
            //System.out.println("chromo[randomNo2].Dna="+chromo[randomNo2].Dna);//for testing
        }//end of for
        //System.out.println("counter11="+counter11);//for testing
        /****************************************************************************************
         * for copying chromosomes which are not taking part in crossover
         ****************************************************************************************/
        /*for(int counter10=0;counter10<=popBeforeCrossover.populationSize-1;counter10++)
        {
            if(flag[counter10])
                chromo[counter10]=popBeforeCrossover.chromo[counter10];
        }//end of for*/
        Population popAfterCrossover=new Population(chromo);
        //System.out.println("Size of population after crossover="+popAfterCrossover.populationSize);//for testing
        return popAfterCrossover;
    }//end of crossover
    /******************************************************************************************************
     *method eliminateInvalidChromosomes. This method will eliminate Chromosomes with all genes="#"
     ******************************************************************************************************/
    public Population eliminateInvalidChromosomes(Population populationBeforeElimination)
    {
        Population populationAfterElimination;
        boolean flag[]=new boolean[populationBeforeElimination.populationSize];
        for(int counter=0;counter<=populationBeforeElimination.populationSize-1;counter++)
        {
            flag[counter]=true;
        }//end of for
        for(int counter=0;counter<=populationBeforeElimination.populationSize-1;counter++)
        {            
            boolean flag1=true;
            for(int counter1=0;counter1<=trainingDataSet.noOfAttributes-2;counter1++)
            {                
                if(!populationBeforeElimination.chromo[counter].gen[counter1].geneValue.equals("#"))
                {
                    flag1=false;
                    break;
                }//end of if
            }//end of for
            if(flag1)
                flag[counter]=false;//invalid chromosome
        }//end of for
        /*for(int counter=0;counter<=populationBeforeElimination.populationSize-1;counter++)
        {
            System.out.println("flag["+counter+"]="+flag[counter]);//for testing
        }//end of for
        System.out.println("____________________________");//for testing*/
        boolean flag2=true;
        for(int counter=0;counter<=populationBeforeElimination.populationSize-1;counter++)
        {
            if(!flag[counter])
            {
                flag2=false;//invalid population
                break;
            }//end of if
        }//end of for
        int noOfValidChromosome=0;
        if(!flag2)
        {            
            for(int counter=0;counter<=populationBeforeElimination.populationSize-1;counter++)
            {
                if(flag[counter])
                {
                    noOfValidChromosome++;
                }//end of if
            }//end of for
            Chromosome chromo[]=new Chromosome[noOfValidChromosome];
            int counter1=0;
            for(int counter=0;counter<=populationBeforeElimination.populationSize-1;counter++)
            {
                if(flag[counter])
                {
                    chromo[counter1]=populationBeforeElimination.chromo[counter];
                    counter1++;
                }//end of if
            }//end of for
            populationAfterElimination=new Population(chromo);
        }//end of if
        else
        {
            populationAfterElimination=populationBeforeElimination;
        }//end of else
        return populationAfterElimination;
    }//end of eliminateInvalidChromosomes()
    /******************************************************************************************************
     *method calmutationProbability(). This method will determine mutation Probability for a perticular
     *generation. At biginning mutation probability will be higher and at the end it will be lower.
     ******************************************************************************************************/
    public float calMutationProbability(int generationOfP1Number,int maxNumberOfGenerationOfP1,float minMutationProbability,float maxMutationProbability)
    {
        /*System.out.println("generationOfP1Number"+generationOfP1Number);//for testing
        System.out.println("maxNumberOfGenerationOfP1"+maxNumberOfGenerationOfP1);//for testing
        System.out.println("minSubstitutionProbability"+minSubstitutionProbability);//for testing
        System.out.println("maxSubstitutionProbability"+maxSubstitutionProbability);//for testing*/
        float mutationProbability=(float)0.0;
        mutationProbability=(float)(maxMutationProbability-minMutationProbability)*(maxNumberOfGenerationOfP1-1-generationOfP1Number)/(maxNumberOfGenerationOfP1-1)+minMutationProbability;
        return mutationProbability;
    }//end of calMutationProbability()
    /******************************************************************************************************/
    /******************************************************************************************************
     *method mutationProbability(). This method will do mutation of values
     ******************************************************************************************************/
    public Population mutation(Population popBeforeMutation,float mutationProbability)
    {     
        Chromosome chromo[]=new Chromosome[popBeforeMutation.populationSize];
        int counter2 = 0;
        for(int chromosomeNo=0;chromosomeNo<=popBeforeMutation.populationSize-1;chromosomeNo++)
        {
            String dna = "";
            //System.out.println("DNA="+popBeforeMutation.chromo[chromosomeNo].Dna);//for testing
            int attributeNo=(int)(Math.random()*trainingDataSet.noOfAttributes-2);
            //System.out.println("attributeNo="+attributeNo);//for testing
            for(int geneNo=0;geneNo<=trainingDataSet.noOfAttributes-2;geneNo++)
            {
                //gene perticipating in mutation
                if(attributeNo==geneNo)
                {
                    String attributesType=trainingDataSet.attributeInformation[1][geneNo];
                    //System.out.println("geneNo="+geneNo);//for testing
                    //System.out.println("attributesType="+attributesType);//for testing
                    if(attributesType.equals("Integer")||attributesType.equals("Float"))
                    {
                        //System.out.println(popBeforeMutation.chromo[chromosomeNo].gen[geneNo].geneValue);//for testing
                        if(popBeforeMutation.chromo[chromosomeNo].gen[geneNo].geneValue.equals("#")||popBeforeMutation.chromo[chromosomeNo].gen[geneNo].geneValue.equals("#,#"))
                        {
                           dna=dna.concat(popBeforeMutation.chromo[chromosomeNo].gen[geneNo].geneValue).concat("|");
                        }//end of if                       
                        else if(!popBeforeMutation.chromo[chromosomeNo].gen[geneNo].minGeneValue.equals("#")&&popBeforeMutation.chromo[chromosomeNo].gen[geneNo].maxGeneValue.equals("#"))
                        {
                            String minValueofGene=popBeforeMutation.chromo[chromosomeNo].gen[geneNo].minGeneValue;
                            String maxValueofGene=popBeforeMutation.chromo[chromosomeNo].gen[geneNo].maxGeneValue;
                            int positionOfMinValueofGene=-1;
                            for(int loopCounter=0;loopCounter<=trainingDataSet.changePoints[geneNo].length-1;loopCounter++)
                            {
                                if(Float.parseFloat(minValueofGene)==Float.parseFloat(trainingDataSet.changePoints[geneNo][loopCounter]))
                                {
                                    positionOfMinValueofGene=loopCounter;
                                    break;
                                }//end of if
                            }//end of for
                            if(positionOfMinValueofGene==0)
                            {
                                minValueofGene=trainingDataSet.changePoints[geneNo][positionOfMinValueofGene+1];
                            }//end of if
                            else if(positionOfMinValueofGene==trainingDataSet.changePoints[geneNo].length-1)
                            {
                                minValueofGene=trainingDataSet.changePoints[geneNo][positionOfMinValueofGene-1];
                            }//end of if
                            else
                            {
                                float randomNo3=(float)(Math.random());
                                if(randomNo3<0.5)
                                {
                                    minValueofGene=trainingDataSet.changePoints[geneNo][positionOfMinValueofGene-1];
                                }//end of if
                                else
                                {
                                    minValueofGene=trainingDataSet.changePoints[geneNo][positionOfMinValueofGene+1];
                                }//end of else
                            }//end of if
                            dna=dna.concat(minValueofGene).concat(",").concat(maxValueofGene).concat("|");
                        }//end of if
                        else if(popBeforeMutation.chromo[chromosomeNo].gen[geneNo].minGeneValue.equals("#")&&!popBeforeMutation.chromo[chromosomeNo].gen[geneNo].maxGeneValue.equals("#"))
                        {
                            String minValueofGene=popBeforeMutation.chromo[chromosomeNo].gen[geneNo].minGeneValue;
                            String maxValueofGene=popBeforeMutation.chromo[chromosomeNo].gen[geneNo].maxGeneValue;
                            int positionOfMaxValueofGene=-1;
                            for(int loopCounter=0;loopCounter<=trainingDataSet.changePoints[geneNo].length-1;loopCounter++)
                            {
                                if(Float.parseFloat(maxValueofGene)==Float.parseFloat(trainingDataSet.changePoints[geneNo][loopCounter]))
                                {
                                    positionOfMaxValueofGene=loopCounter;
                                    break;
                                }//end of if
                            }//end of for
                            if(positionOfMaxValueofGene==0)
                            {
                                maxValueofGene=trainingDataSet.changePoints[geneNo][positionOfMaxValueofGene+1];
                            }//end of if
                            else if(positionOfMaxValueofGene==trainingDataSet.changePoints[geneNo].length-1)
                            {
                                maxValueofGene=trainingDataSet.changePoints[geneNo][positionOfMaxValueofGene-1];
                            }//end of if
                            else
                            {
                                float randomNo3=(float)(Math.random());
                                if(randomNo3<0.5)
                                {
                                    maxValueofGene=trainingDataSet.changePoints[geneNo][positionOfMaxValueofGene-1];
                                }//end of if
                                else
                                {
                                    maxValueofGene=trainingDataSet.changePoints[geneNo][positionOfMaxValueofGene+1];
                                }//end of else
                            }//end of if
                            dna=dna.concat(minValueofGene).concat(",").concat(maxValueofGene).concat("|");
                        }//end of if
                        else
                        {
                            String minValueofGene=popBeforeMutation.chromo[chromosomeNo].gen[geneNo].minGeneValue;
                            String maxValueofGene=popBeforeMutation.chromo[chromosomeNo].gen[geneNo].maxGeneValue;
                            float randomNo1=(float)(Math.random());
                            //System.out.println("randomNo1="+randomNo1);//for testing
                            if(randomNo1<0.5)
                            {
                                //System.out.println("minValueofGene="+minValueofGene);//for testing
                                int positionOfMinValueofGene=-1;
                                for(int loopCounter=0;loopCounter<=trainingDataSet.changePoints[geneNo].length-1;loopCounter++)
                                {
                                    //System.out.println("Float.parseFloat(minValueofGene)="+Float.parseFloat(minValueofGene));//for testing
                                    //System.out.println("Float.parseFloat(trainingDataSet.attributeValues[geneNo][loopCounter])="+Float.parseFloat(trainingDataSet.attributeValues[geneNo][loopCounter]));//for testing
                                    if(Float.parseFloat(minValueofGene)==Float.parseFloat(trainingDataSet.changePoints[geneNo][loopCounter]))
                                    {
                                        positionOfMinValueofGene=loopCounter;
                                        break;
                                    }//end of if
                                }//end of for
                                //System.out.println("positionOfMinValueofGene="+positionOfMinValueofGene);//for testing
                                if(positionOfMinValueofGene==0)
                                {
                                    minValueofGene=trainingDataSet.changePoints[geneNo][positionOfMinValueofGene+1];
                                }//end of if
                                else if(positionOfMinValueofGene==trainingDataSet.changePoints[geneNo].length-1)
                                {
                                    minValueofGene=trainingDataSet.changePoints[geneNo][positionOfMinValueofGene-1];
                                }//end of if
                                else
                                {
                                    float randomNo3=(float)(Math.random());
                                    if(randomNo3<0.5)
                                    {
                                        minValueofGene=trainingDataSet.changePoints[geneNo][positionOfMinValueofGene-1];
                                    }//end of if
                                    else
                                    {
                                        minValueofGene=trainingDataSet.changePoints[geneNo][positionOfMinValueofGene+1];
                                    }//end of else
                                }//end of if
                                //System.out.println("minValueofGene="+minValueofGene);//for testing
                            }//end of if
                            else
                            {
                                //System.out.println("maxValueofGene="+maxValueofGene);//for testing
                                int positionOfMaxValueofGene=-1;
                                for(int loopCounter=0;loopCounter<=trainingDataSet.changePoints[geneNo].length-1;loopCounter++)
                                {
                                    //System.out.println("Float.parseFloat(maxValueofGene)="+Float.parseFloat(maxValueofGene));//for testing
                                    //System.out.println("Float.parseFloat(trainingDataSet.attributeValues[geneNo][loopCounter])="+Float.parseFloat(trainingDataSet.attributeValues[geneNo][loopCounter]));//for testing
                                    if(Float.parseFloat(maxValueofGene)==Float.parseFloat(trainingDataSet.changePoints[geneNo][loopCounter]))
                                    {
                                        positionOfMaxValueofGene=loopCounter;
                                        break;
                                    }//end of if
                                }//end of for
                                //System.out.println("positionOfMaxValueofGene="+positionOfMaxValueofGene);//for testing
                                if(positionOfMaxValueofGene==0)
                                {
                                    maxValueofGene=trainingDataSet.changePoints[geneNo][positionOfMaxValueofGene+1];
                                }//end of if
                                else if(positionOfMaxValueofGene==trainingDataSet.changePoints[geneNo].length-1)
                                {
                                    maxValueofGene=trainingDataSet.changePoints[geneNo][positionOfMaxValueofGene-1];
                                }//end of if
                                else
                                {
                                    float randomNo3=(float)(Math.random());
                                    if(randomNo3<0.5)
                                    {
                                        maxValueofGene=trainingDataSet.changePoints[geneNo][positionOfMaxValueofGene-1];
                                    }//end of if
                                    else
                                    {
                                        maxValueofGene=trainingDataSet.changePoints[geneNo][positionOfMaxValueofGene-1];
                                    }//end of else
                                }//end of if
                                //System.out.println("maxValueofGene="+maxValueofGene);//for testing
                            }//end of if
                            if(Float.parseFloat(minValueofGene)>Float.parseFloat(maxValueofGene))
                            {
                                String temp=minValueofGene;
                                minValueofGene=maxValueofGene;
                                maxValueofGene=temp;
                            }//end of if
                            dna=dna.concat(minValueofGene).concat(",").concat(maxValueofGene).concat("|");
                        }//end of else
                    }//end of if
                    else if(attributesType.equals("Binary")||attributesType.equals("String"))
                    {
                        if(popBeforeMutation.chromo[chromosomeNo].gen[geneNo].geneValue.equals("#"))
                        {
                            dna=dna.concat(popBeforeMutation.chromo[chromosomeNo].gen[geneNo].geneValue).concat("|");
                        }//end of if
                        else
                        {
                            //System.out.println("gene[geneNo]="+chromo[chromosomeNo].gen[attributeNo].geneValue);//for testing
                            int randomNo3=(int)(Math.random()*popBeforeMutation.chromo[chromosomeNo].gen[geneNo].geneValue.length());
                            //System.out.println("randomNo3="+randomNo3);//for testing
                            //System.out.println("chromo[chromosomeNo].gen[geneNo].geneValue.charAt(randomNo3)="+chromo[chromosomeNo].gen[geneNo].geneValue.charAt(randomNo3));//for testing
                            String leftPartOfGene=popBeforeMutation.chromo[chromosomeNo].gen[geneNo].geneValue.substring(0,randomNo3);
                            //System.out.println("leftPartOfGene="+leftPartOfGene);//for testing
                            String rightPartOfGene=popBeforeMutation.chromo[chromosomeNo].gen[geneNo].geneValue.substring(randomNo3+1,popBeforeMutation.chromo[chromosomeNo].gen[geneNo].geneValue.length());
                            //System.out.println("rightPartOfGene="+rightPartOfGene);//for testing
                            String mutatedGeneValue ="";
                            if(popBeforeMutation.chromo[chromosomeNo].gen[geneNo].geneValue.charAt(randomNo3)=='1')
                            {                                
                                mutatedGeneValue=leftPartOfGene.concat("0").concat(rightPartOfGene);
                            }//end of if
                            else if(popBeforeMutation.chromo[chromosomeNo].gen[geneNo].geneValue.charAt(randomNo3)=='0')
                            {
                                mutatedGeneValue=leftPartOfGene.concat("1").concat(rightPartOfGene);
                            }//end of if
                            dna=dna.concat(mutatedGeneValue).concat("|");
                        }//end of else
                    }//end of else
                }//end of if
                else
                {                     
                    dna=dna.concat(popBeforeMutation.chromo[chromosomeNo].gen[geneNo].geneValue).concat("|");                    
                }//end of else
            }//end of for
            //System.out.println("Modified DNA ="+dna);
            chromo[counter2] = new Chromosome(dna,trainingDataSet);
            counter2++;
        }//end of for        
        Population popAfterMutation=new Population(chromo);
        return popAfterMutation;
    }//end of substitution
    /***************************************************************************************************
     * combination method will combine chromosome before and after applying GA operator
     *****************************************************************************************************/
    public Population combination(Population populationBeforeCrossover,Population populationAfterCrossover,Population populationAfterMutation)
    {
        Chromosome chromo[]=new Chromosome[populationBeforeCrossover.populationSize+populationAfterCrossover.populationSize+populationAfterMutation.populationSize];
        int counter1=0;
        for(int counter=0;counter<=populationBeforeCrossover.populationSize-1;counter++)
        {
            chromo[counter1]=populationBeforeCrossover.chromo[counter];
            //System.out.println("chromosomes[counter]="+chromosomes[counter]);//for testing
            counter1++;
        }//end of for
        for(int counter=0;counter<=populationAfterCrossover.populationSize-1;counter++)
        {
            chromo[counter1]=populationAfterCrossover.chromo[counter];
            //System.out.println("chromosomes[counter]="+chromosomes[counter]);//for testing
            counter1++;
        }//end of for
        for(int counter=0;counter<=populationAfterMutation.populationSize-1;counter++)
        {
            chromo[counter1]=populationAfterMutation.chromo[counter];
            counter1++;
        }//end of for
        Population populationAfterCombination=new Population(chromo);
        return populationAfterCombination;
    }//end of combination    
    /******************************************************************************************************
     * method eliminateMeaninglessCondition(). For categorical attributes if all values of training dataset
     * is present in gene or no values are present then that is equivalent to don't care ("#").
     * In case of continuous feature if min and max value is equal to min and max value of
     * training dataset then that is equivalent to don't care ("#"). Due to crossover min and max value
     * may be equal to "#" and gene value may be "#,#". We have to correct gene value to "#".
     ******************************************************************************************************/
    public Population eliminateMeaninglessCondition(Population populationBeforeElimination)
    {
        Chromosome chromo[]=new Chromosome[populationBeforeElimination.populationSize];
        //String gene[]=new String[trainingDataSet.noOfAttributes-1];
        for(int counter=0;counter<=populationBeforeElimination.populationSize-1;counter++)
        {
            //System.out.println("Chromosome Number="+counter);//for testing
            /****************************************************************************************
            * extracting gene value
            ****************************************************************************************/            
            for(int counter1=0;counter1<=trainingDataSet.noOfAttributes-2;counter1++)
            {
                String attributesType=trainingDataSet.attributeInformation[1][counter1];
                //if(gene[counter1].equals("#"))
                if(populationBeforeElimination.chromo[counter].gen[counter1].geneValue.equals("#"))
                {
                    //do nothing
                }//end of if
                else if(attributesType.equals("Integer")|attributesType.equals("Float"))
                {                   
                    if(populationBeforeElimination.chromo[counter].gen[counter1].minGeneValue.equals(trainingDataSet.minValues[counter1])&&!populationBeforeElimination.chromo[counter].gen[counter1].maxGeneValue.equals(trainingDataSet.maxValues[counter1])&&!populationBeforeElimination.chromo[counter].gen[counter1].maxGeneValue.equals("#"))
                    {
                         populationBeforeElimination.chromo[counter].gen[counter1].geneValue="#".concat(",").concat(populationBeforeElimination.chromo[counter].gen[counter1].maxGeneValue);
                         populationBeforeElimination.chromo[counter].gen[counter1].minGeneValue="#";                         
                         //System.out.println("Attribute Number="+counter1);//for testing
                    }//end of if
                    else if(!populationBeforeElimination.chromo[counter].gen[counter1].minGeneValue.equals(trainingDataSet.minValues[counter1])&&!populationBeforeElimination.chromo[counter].gen[counter1].minGeneValue.equals("#")&&populationBeforeElimination.chromo[counter].gen[counter1].maxGeneValue.equals(trainingDataSet.maxValues[counter1]))
                    {
                         populationBeforeElimination.chromo[counter].gen[counter1].geneValue=populationBeforeElimination.chromo[counter].gen[counter1].minGeneValue.concat(",").concat("#");
                         populationBeforeElimination.chromo[counter].gen[counter1].maxGeneValue="#";
                         //System.out.println("Attribute Number="+counter1);//for testing
                    }//end of if
                    else if(populationBeforeElimination.chromo[counter].gen[counter1].minGeneValue.equals(trainingDataSet.minValues[counter1])&&populationBeforeElimination.chromo[counter].gen[counter1].maxGeneValue.equals(trainingDataSet.maxValues[counter1]))
                    {
                         populationBeforeElimination.chromo[counter].gen[counter1].geneValue=populationBeforeElimination.chromo[counter].gen[counter1].minGeneValue=populationBeforeElimination.chromo[counter].gen[counter1].maxGeneValue="#";
                         //System.out.println("Attribute Number="+counter1);//for testing
                    }//end of if
                    else if(populationBeforeElimination.chromo[counter].gen[counter1].minGeneValue.equals(trainingDataSet.minValues[counter1])&&populationBeforeElimination.chromo[counter].gen[counter1].maxGeneValue.equals("#"))
                    {
                         populationBeforeElimination.chromo[counter].gen[counter1].geneValue=populationBeforeElimination.chromo[counter].gen[counter1].minGeneValue=populationBeforeElimination.chromo[counter].gen[counter1].maxGeneValue="#";
                         //System.out.println("Attribute Number="+counter1);//for testing
                    }//end of if
                    else if(populationBeforeElimination.chromo[counter].gen[counter1].minGeneValue.equals("#")&&populationBeforeElimination.chromo[counter].gen[counter1].maxGeneValue.equals(trainingDataSet.maxValues[counter1]))
                    {
                         populationBeforeElimination.chromo[counter].gen[counter1].geneValue=populationBeforeElimination.chromo[counter].gen[counter1].minGeneValue=populationBeforeElimination.chromo[counter].gen[counter1].maxGeneValue="#";
                         //System.out.println("Attribute Number="+counter1);//for testing
                    }//end of if
                    else if(populationBeforeElimination.chromo[counter].gen[counter1].minGeneValue.equals("#")&&populationBeforeElimination.chromo[counter].gen[counter1].maxGeneValue.equals("#"))
                    {
                         populationBeforeElimination.chromo[counter].gen[counter1].geneValue=populationBeforeElimination.chromo[counter].gen[counter1].minGeneValue=populationBeforeElimination.chromo[counter].gen[counter1].maxGeneValue="#";
                         //System.out.println("Attribute Number="+counter1);//for testing
                    }//end of if
                }//end of if
                else if(attributesType.equals("Binary")|attributesType.equals("String"))
                {                   
                    //System.out.println("Gene Value="+populationBeforeElimination.chromo[counter].gen[counter1].geneValue);//for testing
                    boolean flag0=true;
                    boolean flag1=true;
                    //System.out.println("gene length="+populationBeforeElimination.chromo[counter].gen[counter1].geneValue.length());//for testing
                    for(int counter2=0;counter2<=populationBeforeElimination.chromo[counter].gen[counter1].geneValue.length()-1;counter2++)
                    {
                        //System.out.println("charAt("+counter2+")="+populationBeforeElimination.chromo[counter].gen[counter1].geneValue.charAt(counter2));//for testing
                        if(populationBeforeElimination.chromo[counter].gen[counter1].geneValue.charAt(counter2)=='1')
                        {                            
                            flag0=false;
                            break;
                        }//end of if
                    }//end of for
                    //System.out.println("flag0="+flag0);//for testing
                    for(int counter2=0;counter2<=populationBeforeElimination.chromo[counter].gen[counter1].geneValue.length()-1;counter2++)
                    {
                        //System.out.println("charAt("+counter2+")="+populationBeforeElimination.chromo[counter].gen[counter1].geneValue.charAt(counter2));//for testing
                        if(populationBeforeElimination.chromo[counter].gen[counter1].geneValue.charAt(counter2)=='0')
                        {
                            flag1=false;
                            break;
                        }//end of if
                    }//end of for                    
                    //System.out.println("flag1="+flag1);//for testing
                    if(flag0|flag1)
                    {
                        populationBeforeElimination.chromo[counter].gen[counter1].geneValue="#";
                        //System.out.println("Attribute Number="+counter1);//for testing
                    }//end of if
                }//end of if
            }//end of for
            String modifiedDnaString="";
            for(int counter1=0;counter1<=trainingDataSet.noOfAttributes-2;counter1++)
            {
                //modifiedDnaString=modifiedDnaString.concat(gene[counter1]).concat("|");
                modifiedDnaString=modifiedDnaString.concat(populationBeforeElimination.chromo[counter].gen[counter1].geneValue).concat("|");
            }//end of for
            chromo[counter]=new Chromosome(modifiedDnaString,populationBeforeElimination.chromo[counter].classLabel,populationBeforeElimination.chromo[counter].A,populationBeforeElimination.chromo[counter].C,populationBeforeElimination.chromo[counter].AUC,populationBeforeElimination.chromo[counter].confidence,populationBeforeElimination.chromo[counter].coverage,populationBeforeElimination.chromo[counter].noOfValidAttributes,populationBeforeElimination.chromo[counter].covered_record_nos,trainingDataSet);
        }//end of for
        Population populationAfterEliminateMeaninglessCondition=new Population(chromo);
        return populationAfterEliminateMeaninglessCondition;
    }//end of method    
    /***************************************************************************************************
     * eliminateDuplicate method will eliminate duplicate chromosome
     ***************************************************************************************************/
    public Population eliminateDuplicate(Population populationAfterEliminateMeaninglessCondition)
    {
        boolean flag[]=new boolean[populationAfterEliminateMeaninglessCondition.populationSize];
        for(int counter=0;counter<=populationAfterEliminateMeaninglessCondition.populationSize-1;counter++)
        {
            flag[counter]=true;
        }//end of for
        for(int outerLoopCounter=0;outerLoopCounter<=populationAfterEliminateMeaninglessCondition.populationSize-1;outerLoopCounter++)
        {
            String outerChromosome=populationAfterEliminateMeaninglessCondition.chromo[outerLoopCounter].Dna;
            for(int innerLoopCounter=outerLoopCounter+1;innerLoopCounter<=populationAfterEliminateMeaninglessCondition.populationSize-1;innerLoopCounter++)
            {
                String innerChromosome=populationAfterEliminateMeaninglessCondition.chromo[innerLoopCounter].Dna;
                if(outerChromosome.equals(innerChromosome))
                {
                    flag[innerLoopCounter]=false;
                }//end of if
            }//end of innerForLoop
        }//end of outerForLoop
        int counter1=0;
        for(int counter=0;counter<=populationAfterEliminateMeaninglessCondition.populationSize-1;counter++)
        {
            if(flag[counter])
                counter1++;
        }//end of for
        Chromosome chromo[]=new Chromosome[counter1];
        counter1=0;
        for(int counter=0;counter<=populationAfterEliminateMeaninglessCondition.populationSize-1;counter++)
        {
            if(flag[counter])
            {
                chromo[counter1]=populationAfterEliminateMeaninglessCondition.chromo[counter];
                counter1++;
            }//end of if
        }//end of for
        Population populationAfterEliminatingDuplicate=new Population(chromo);
        return populationAfterEliminatingDuplicate;
    }//end of eliminateDuplicate
    /***************************************************************************************************
     * fitnessCalculation method will calculate Accuracy(Confidence)=AUC/A and Coverage=AUC/C
     *****************************************************************************************************/
     public Population fitnessCalculation(Population populationAfterEliminatingDuplicate)
     {
        /******************************************************************************************************
        *for storing chromosome after Fitness Calculation
        ******************************************************************************************************/
        Chromosome chromo[]=new Chromosome[populationAfterEliminatingDuplicate.populationSize];
        int noOfClass=trainingDataSet.noOfClasses;
        String classLabel[]=trainingDataSet.classLevels;
        int classLabelCounter[]=new int[noOfClass];
        //String gene[]=new String[trainingDataSet.noOfAttributes-1];
        //String minValues[]=new String[trainingDataSet.noOfAttributes-1];
        //String maxValues[]=new String[trainingDataSet.noOfAttributes-1];
        //String categoricalTypeValues[]=new String[trainingDataSet.noOfAttributes-1];
        for(int chromosomeNo=0;chromosomeNo<=populationAfterEliminatingDuplicate.populationSize-1;chromosomeNo++)
        {
            if(populationAfterEliminatingDuplicate.chromo[chromosomeNo].A==0 &&
                    populationAfterEliminatingDuplicate.chromo[chromosomeNo].C==0 &&
                    populationAfterEliminatingDuplicate.chromo[chromosomeNo].AUC==0)
            {
                int A=0;
                int C=0;
                int AUC=0;
                float Confidence=(float)0.0,Coverage=(float)0.0;
                String chosenClassLabel=trainingDataSet.dataSet[0][trainingDataSet.noOfAttributes-1];   //"Not Known";
                int noOfValidAttributes=0;
                Vector<Integer> covered_record_nos = new Vector<Integer>();
                for(int classNo=0;classNo<=noOfClass-1;classNo++)
                {
                    classLabelCounter[classNo]=0;
                }//end of for
                /*******************************************************************************************************
                *Calculating A
                ********************************************************************************************************/
                boolean flag=true;
                for(int recordNo=0;recordNo<=trainingDataSet.noOfRecords-1;recordNo++)
                {
                    /*for(int attributeNo=0;attributeNo<=trainingDataSet.noOfAttributes-1;attributeNo++)
                    {
                        System.out.print(trainingDataSet.dataSet[recordNo][attributeNo]+",");//for testing
                    }//end of if
                    System.out.println();//for testing*/
                    flag=false;
                    for(int attributeNo=0;attributeNo<=trainingDataSet.noOfAttributes-2;attributeNo++)
                    {
                        if(trainingDataSet.attributeInformation[1][attributeNo].equals("Integer")||trainingDataSet.attributeInformation[1][attributeNo].equals("Float"))
                        {
                            if(populationAfterEliminatingDuplicate.chromo[chromosomeNo].gen[attributeNo].geneValue.equals("#")||trainingDataSet.dataSet[recordNo][attributeNo].equals("?"))
                            {
                                //do nothing
                            }//end of if
                            else if(populationAfterEliminatingDuplicate.chromo[chromosomeNo].gen[attributeNo].minGeneValue.equals("#"))
                            {
                                //System.out.println(populationAfterEliminatingDuplicate.chromo[chromosomeNo].gen[attributeNo].geneValue);//for testing
                                if(Float.parseFloat(trainingDataSet.dataSet[recordNo][attributeNo])<=Float.parseFloat(populationAfterEliminatingDuplicate.chromo[chromosomeNo].gen[attributeNo].maxGeneValue))
                                {
                                    flag=true;
                                }//end of if
                                else
                                {
                                    flag=false;
                                    break;
                                }//end of else
                            }//end of if
                            else if(populationAfterEliminatingDuplicate.chromo[chromosomeNo].gen[attributeNo].maxGeneValue.equals("#"))
                            {
                                //System.out.println(populationAfterEliminatingDuplicate.chromo[chromosomeNo].gen[attributeNo].geneValue);//for testing
                                if(Float.parseFloat(trainingDataSet.dataSet[recordNo][attributeNo])>=Float.parseFloat(populationAfterEliminatingDuplicate.chromo[chromosomeNo].gen[attributeNo].minGeneValue))
                                {
                                    flag=true;
                                }//end of if
                                else
                                {
                                    flag=false;
                                    break;
                                }//end of else
                            }//end of else
                            else if((Float.parseFloat(trainingDataSet.dataSet[recordNo][attributeNo])>=Float.parseFloat(populationAfterEliminatingDuplicate.chromo[chromosomeNo].gen[attributeNo].minGeneValue))&&((Float.parseFloat(trainingDataSet.dataSet[recordNo][attributeNo])<=Float.parseFloat(populationAfterEliminatingDuplicate.chromo[chromosomeNo].gen[attributeNo].maxGeneValue))))
                            {
                                flag=true;
                            }//end of if
                            else
                            {
                                flag=false;
                                break;
                            }//end of else
                        }//end of if
                        else if(trainingDataSet.attributeInformation[1][attributeNo].equals("Binary")|trainingDataSet.attributeInformation[1][attributeNo].equals("String"))
                        {
                            if(populationAfterEliminatingDuplicate.chromo[chromosomeNo].gen[attributeNo].geneValue.equals("#")||trainingDataSet.dataSet[recordNo][attributeNo].equals("?"))
                            {
                                //do nothing
                            }//end of if
                            else
                            {
                                boolean flag1=true;
                                for(int i=0;i<=populationAfterEliminatingDuplicate.chromo[chromosomeNo].gen[attributeNo].catGeneValue.length-1;i++)
                                {
                                    if(populationAfterEliminatingDuplicate.chromo[chromosomeNo].gen[attributeNo].catGeneValue[i].equals(trainingDataSet.dataSet[recordNo][attributeNo]))
                                    {
                                        flag1=false;
                                        flag=true;
                                        break;
                                    }//end of if
                                }//end of for
                                if(flag1)
                                {
                                    flag=false;
                                    break;
                                }//end of if
                            }//end of else
                        }//end of if
                    }//end of for
                    //System.out.println("flag="+flag);//for testing
                    if(flag)
                    {
                        A++;
                        covered_record_nos.addElement(recordNo);
                        /*******************************************************************************************************
                        *Deciding Consequence
                        ********************************************************************************************************/
                        String classLabel1=trainingDataSet.dataSet[recordNo][trainingDataSet.noOfAttributes-1];
                        for(int classNo=0;classNo<=noOfClass-1;classNo++)
                        {
                            if(classLabel1.equals(classLabel[classNo]))
                            {
                                classLabelCounter[classNo]++;
                            }//end of if
                        }//end of for
                    }//end of if
                }//end of for
                //System.out.println("A="+A);//for testing
                //System.out.println("Covered Rocords:" +covered_record_nos);
                /*******************************************************************************************************
                *Deciding class Label
                ********************************************************************************************************/
                int maxClassLabelCounter=0;
                for(int classNo=0;classNo<=noOfClass-1;classNo++)
                {
                    if(maxClassLabelCounter<classLabelCounter[classNo])
                    {
                        maxClassLabelCounter=classLabelCounter[classNo];
                        chosenClassLabel=classLabel[classNo];
                    }//end of if
                }//end of for
                //System.out.println("maxClassLabelCounter="+maxClassLabelCounter);//for testing
                //System.out.println("chosenClassLabel="+chosenClassLabel);//for testing
                /*******************************************************************************************************
                *Calculating AUC
                ********************************************************************************************************/
               for(int recordNo=0;recordNo<=trainingDataSet.noOfRecords-1;recordNo++)
                {                   
                    if(covered_record_nos.contains(recordNo))                    
                    {
                        if(chosenClassLabel.equals(trainingDataSet.dataSet[recordNo][trainingDataSet.noOfAttributes-1]))
                        {
                            AUC++;
                        }//end of if
                    }//end of if
                    if(chosenClassLabel.equals(trainingDataSet.dataSet[recordNo][trainingDataSet.noOfAttributes-1]))
                        C++;
                }//end of for
                //System.out.println("C="+C);//for testing
                //System.out.println("AUC="+AUC);//for testing
                if(A!=0&&C!=0)
                {
                    //Confidence=((float)tp/(tp+fn))*(1+(float)tn/(tn+fp));
                    //Confidence=((float)tp/(tp+fp))*(1-(float)tn/(tn+fn));
                    //Coverage=(float)tp/noOfRecordOfChosenClass;
                    //Coverage=((float)(tp+fp)/noOfRecordOfChosenClass)*(1-(float)(tn+fn)/(trainingDataSet.noOfRecords));
                    //Coverage=(float)trainingDataSet.noOfRecords/((trainingDataSet.noOfRecords+fp));
                    Confidence=(float)AUC/A;
                    Coverage=(float)AUC/C;
                    /*System.out.println("A="+A);//for testing
                    System.out.println("C="+C);//for testing
                    System.out.println("AUC="+AUC);//for testing
                    System.out.println("Confidence="+Confidence);//for testing
                    System.out.println("Coverage="+Coverage);//for testing*/
                }//end of if
                noOfValidAttributes=returnNoOfValidAttributes(populationAfterEliminatingDuplicate.chromo[chromosomeNo]);
                //chromo[counter]=new Chromosome(populationAfterEliminatingDuplicate.chromo[counter].Dna,chosenClassLabel,A,C,AUC,Confidence,Coverage,noOfValidAttributes,trainingDataSet);
                chromo[chromosomeNo]=new Chromosome(populationAfterEliminatingDuplicate.chromo[chromosomeNo].Dna,chosenClassLabel,A,C,AUC,Confidence,Coverage,noOfValidAttributes,covered_record_nos,trainingDataSet);
                //chromo[counter]=new Chromosome(populationAfterEliminatingDuplicate.chromo[counter].Dna,chosenClassLabel,A,C,AUC,positive_confidence,negative_confidence,objective6,noOfValidAttributes,trainingDataSet);
                //System.out.println("If part");//for testing
            }//end of if
            else
            {
                chromo[chromosomeNo]=populationAfterEliminatingDuplicate.chromo[chromosomeNo];
                //System.out.println("Else part");//for testing
            }//end of else
        }//end of for
        Population populationAfterFitnessCalculation=new Population(chromo);
        return populationAfterFitnessCalculation;
     }//end of fitnessCalculation
     /***************************************************************************************************
     * returnNoOfValidAttributes method
     *****************************************************************************************************/
    public int returnNoOfValidAttributes(Chromosome chromo)
    {
        int noOfValidAttributes=0;
        //chromo.showDnaOfChromosome();//for testing
        for(int counter=0;counter<=chromo.gen.length-1;counter++)
        {
            if(!chromo.gen[counter].geneValue.equals("#"))
               noOfValidAttributes++;
        }//end of for
        //System.out.println("noOfValidAttributes="+noOfValidAttributes);//for testing
        return noOfValidAttributes;
    }//end of returnNoOfValidAttributes()
     /***************************************************************************************************
     * paretoSelection method will Select rules (chromosomes) lies on the pareto optimal font
     *****************************************************************************************************/
    public Population paretoSelection(Population populationAfterFitnessCalculation)
    {
        boolean flag[]=new boolean[populationAfterFitnessCalculation.populationSize];
        for(int counter=0;counter<=populationAfterFitnessCalculation.populationSize-1;counter++)
        {
            if(populationAfterFitnessCalculation.chromo[counter].classLabel.equals(""))//for rules which are not covering any records
                flag[counter]=false;
            else
                flag[counter]=true;
        }//end of for
        for(int outerLoopCounter=0;outerLoopCounter<=populationAfterFitnessCalculation.populationSize-1;outerLoopCounter++)
        {
            //String outerChromosomeDna=populationAfterFitnessCalculation.chromo[outerLoopCounter].Dna;
            String outerChromosomeClassLabel=populationAfterFitnessCalculation.chromo[outerLoopCounter].classLabel;
            float outerChromosomeConfidence=populationAfterFitnessCalculation.chromo[outerLoopCounter].confidence;
            //float outerChromosomeNagetive_Confidence=populationAfterFitnessCalculation.chromo[outerLoopCounter].nagetive_confidence;
            float outerChromosomeCoverage=populationAfterFitnessCalculation.chromo[outerLoopCounter].coverage;
            int outerChromosomeNoOfValidAttributes=populationAfterFitnessCalculation.chromo[outerLoopCounter].noOfValidAttributes;

            for(int innerLoopCounter=0;innerLoopCounter<=populationAfterFitnessCalculation.populationSize-1;innerLoopCounter++)
            {
                //String innerChromosomeDna=populationAfterFitnessCalculation.chromo[innerLoopCounter].Dna;
                String innerChromosomeClassLabel=populationAfterFitnessCalculation.chromo[innerLoopCounter].classLabel;
                float innerChromosomeConfidence=populationAfterFitnessCalculation.chromo[innerLoopCounter].confidence;
                //float innerChromosomeNagetive_Confidence=populationAfterFitnessCalculation.chromo[innerLoopCounter].nagetive_confidence;
                float innerChromosomeCoverage=populationAfterFitnessCalculation.chromo[innerLoopCounter].coverage;
                int innerChromosomeNoOfValidAttributes=populationAfterFitnessCalculation.chromo[innerLoopCounter].noOfValidAttributes;

                if(outerChromosomeClassLabel.equals(innerChromosomeClassLabel)&&(outerLoopCounter!=innerLoopCounter))//to compare rules of same class
                {
                    
                    /*if((innerChromosomePositive_Confidence>outerChromosomePositive_Confidence&&innerChromosomeNagetive_Confidence>outerChromosomeNagetive_Confidence&&innerChromosomeCoverage>outerChromosomeCoverage&&innerChromosomeNoOfValidAttributes<outerChromosomeNoOfValidAttributes)
                        ||(innerChromosomePositive_Confidence>outerChromosomePositive_Confidence&&innerChromosomeNagetive_Confidence>outerChromosomeNagetive_Confidence&&innerChromosomeCoverage>outerChromosomeCoverage&&innerChromosomeNoOfValidAttributes==outerChromosomeNoOfValidAttributes)
                        ||(innerChromosomePositive_Confidence>outerChromosomePositive_Confidence&&innerChromosomeNagetive_Confidence>outerChromosomeNagetive_Confidence&&innerChromosomeCoverage==outerChromosomeCoverage&&innerChromosomeNoOfValidAttributes<outerChromosomeNoOfValidAttributes)
                        ||(innerChromosomePositive_Confidence==outerChromosomePositive_Confidence&&innerChromosomeNagetive_Confidence>outerChromosomeNagetive_Confidence&&innerChromosomeCoverage>outerChromosomeCoverage&&innerChromosomeNoOfValidAttributes<outerChromosomeNoOfValidAttributes)
                        ||(innerChromosomePositive_Confidence>outerChromosomePositive_Confidence&&innerChromosomeNagetive_Confidence>outerChromosomeNagetive_Confidence&&innerChromosomeCoverage==outerChromosomeCoverage&&innerChromosomeNoOfValidAttributes==outerChromosomeNoOfValidAttributes)
                        ||(innerChromosomePositive_Confidence==outerChromosomePositive_Confidence&&innerChromosomeNagetive_Confidence>outerChromosomeNagetive_Confidence&&innerChromosomeCoverage>outerChromosomeCoverage&&innerChromosomeNoOfValidAttributes==outerChromosomeNoOfValidAttributes)
                        ||(innerChromosomePositive_Confidence==outerChromosomePositive_Confidence&&innerChromosomeNagetive_Confidence>outerChromosomeNagetive_Confidence&&innerChromosomeCoverage==outerChromosomeCoverage&&innerChromosomeNoOfValidAttributes<outerChromosomeNoOfValidAttributes)
                        ||(innerChromosomePositive_Confidence>outerChromosomePositive_Confidence&&innerChromosomeNagetive_Confidence==outerChromosomeNagetive_Confidence&&innerChromosomeCoverage>outerChromosomeCoverage&&innerChromosomeNoOfValidAttributes<outerChromosomeNoOfValidAttributes)
                        ||(innerChromosomePositive_Confidence>outerChromosomePositive_Confidence&&innerChromosomeNagetive_Confidence==outerChromosomeNagetive_Confidence&&innerChromosomeCoverage>outerChromosomeCoverage&&innerChromosomeNoOfValidAttributes==outerChromosomeNoOfValidAttributes)
                        ||(innerChromosomePositive_Confidence>outerChromosomePositive_Confidence&&innerChromosomeNagetive_Confidence==outerChromosomeNagetive_Confidence&&innerChromosomeCoverage==outerChromosomeCoverage&&innerChromosomeNoOfValidAttributes<outerChromosomeNoOfValidAttributes)
                        ||(innerChromosomePositive_Confidence==outerChromosomePositive_Confidence&&innerChromosomeNagetive_Confidence==outerChromosomeNagetive_Confidence&&innerChromosomeCoverage>outerChromosomeCoverage&&innerChromosomeNoOfValidAttributes<outerChromosomeNoOfValidAttributes)
                        ||(innerChromosomePositive_Confidence>outerChromosomePositive_Confidence&&innerChromosomeNagetive_Confidence==outerChromosomeNagetive_Confidence&&innerChromosomeCoverage==outerChromosomeCoverage&&innerChromosomeNoOfValidAttributes==outerChromosomeNoOfValidAttributes)
                        ||(innerChromosomePositive_Confidence==outerChromosomePositive_Confidence&&innerChromosomeNagetive_Confidence==outerChromosomeNagetive_Confidence&&innerChromosomeCoverage>outerChromosomeCoverage&&innerChromosomeNoOfValidAttributes==outerChromosomeNoOfValidAttributes)
                        ||(innerChromosomePositive_Confidence==outerChromosomePositive_Confidence&&innerChromosomeNagetive_Confidence==outerChromosomeNagetive_Confidence&&innerChromosomeCoverage==outerChromosomeCoverage&&innerChromosomeNoOfValidAttributes<outerChromosomeNoOfValidAttributes)
                        )*/
                      if((innerChromosomeConfidence>outerChromosomeConfidence&&innerChromosomeCoverage>outerChromosomeCoverage&&innerChromosomeNoOfValidAttributes<outerChromosomeNoOfValidAttributes)
                        ||(innerChromosomeConfidence>outerChromosomeConfidence&&innerChromosomeCoverage>outerChromosomeCoverage&&innerChromosomeNoOfValidAttributes==outerChromosomeNoOfValidAttributes)
                        ||(innerChromosomeConfidence>outerChromosomeConfidence&&innerChromosomeCoverage==outerChromosomeCoverage&&innerChromosomeNoOfValidAttributes<outerChromosomeNoOfValidAttributes)
                        ||(innerChromosomeConfidence==outerChromosomeConfidence&&innerChromosomeCoverage>outerChromosomeCoverage&&innerChromosomeNoOfValidAttributes<outerChromosomeNoOfValidAttributes)
                        ||(innerChromosomeConfidence>outerChromosomeConfidence&&innerChromosomeCoverage==outerChromosomeCoverage&&innerChromosomeNoOfValidAttributes==outerChromosomeNoOfValidAttributes)
                        ||(innerChromosomeConfidence==outerChromosomeConfidence&&innerChromosomeCoverage>outerChromosomeCoverage&&innerChromosomeNoOfValidAttributes==outerChromosomeNoOfValidAttributes)
                        ||(innerChromosomeConfidence==outerChromosomeConfidence&&innerChromosomeCoverage==outerChromosomeCoverage&&innerChromosomeNoOfValidAttributes<outerChromosomeNoOfValidAttributes)
                        )

                        /*if((innerChromosomePositive_Confidence>outerChromosomePositive_Confidence&&innerChromosomeCoverage>outerChromosomeCoverage&&innerChromosomeNagetive_Confidence>outerChromosomeNagetive_Confidence)
                        ||(innerChromosomePositive_Confidence>outerChromosomePositive_Confidence&&innerChromosomeCoverage>outerChromosomeCoverage&&innerChromosomeNagetive_Confidence==outerChromosomeNagetive_Confidence)
                        ||(innerChromosomePositive_Confidence>outerChromosomePositive_Confidence&&innerChromosomeCoverage==outerChromosomeCoverage&&innerChromosomeNagetive_Confidence>outerChromosomeNagetive_Confidence)
                        ||(innerChromosomePositive_Confidence==outerChromosomePositive_Confidence&&innerChromosomeCoverage>outerChromosomeCoverage&&innerChromosomeNagetive_Confidence>outerChromosomeNagetive_Confidence)
                        ||(innerChromosomePositive_Confidence>outerChromosomePositive_Confidence&&innerChromosomeCoverage==outerChromosomeCoverage&&innerChromosomeNagetive_Confidence==outerChromosomeNagetive_Confidence)
                        ||(innerChromosomePositive_Confidence==outerChromosomePositive_Confidence&&innerChromosomeCoverage>outerChromosomeCoverage&&innerChromosomeNagetive_Confidence==outerChromosomeNagetive_Confidence)
                        ||(innerChromosomePositive_Confidence==outerChromosomePositive_Confidence&&innerChromosomeCoverage==outerChromosomeCoverage&&innerChromosomeNagetive_Confidence>outerChromosomeNagetive_Confidence)
                        )*/

                    //if((innerChromosomeConfidence>outerChromosomeConfidence&&innerChromosomeCoverage>outerChromosomeCoverage)||(innerChromosomeConfidence==outerChromosomeConfidence&&innerChromosomeCoverage>outerChromosomeCoverage)||(innerChromosomeConfidence>outerChromosomeConfidence&&innerChromosomeCoverage==outerChromosomeCoverage))

                    {
                        flag[outerLoopCounter]=false;
                        break;
                    }//end of if
                }//end of if
            }//end of innerForLoop
        }//end of outerForLoop
        int noOfParetoChromosome=0;
        for(int counter=0;counter<=populationAfterFitnessCalculation.populationSize-1;counter++)
        {
            if(flag[counter])
                noOfParetoChromosome++;
        }//end of for
        Chromosome chromo[]=new Chromosome[noOfParetoChromosome];
        int counter1=0;
        for(int counter=0;counter<=populationAfterFitnessCalculation.populationSize-1;counter++)
        {
            if(flag[counter])
            {
                chromo[counter1]=populationAfterFitnessCalculation.chromo[counter];
                counter1++;
            }//end of if
        }//end of for
        paretoPopulation=new Population(chromo);
        return paretoPopulation;
    }//end of paretoSelection
    /***************************************************************************************************
     * returnTop1000SortedPopulation method will return 1000 chromosomes from sortedPopulation
     *****************************************************************************************************/
    public Population returnTop1000SortedPopulation(Population sortedPopulation)
    {
        //System.out.println("returnTop1000SortedPopulation method");//for testing
        Chromosome chromo[]=new Chromosome[1000];
        for(int counter=0;counter<=1000-1;counter++)
        {
            chromo[counter]=sortedPopulation.chromo[counter];
        }//end of for
        Population top1000SortedPopulation=new Population(chromo);
        return top1000SortedPopulation;
    }//end of returnTop1000SortedPopulation()
}//end of class
