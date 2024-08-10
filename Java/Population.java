public class Population 
{
    int populationSize;
    Chromosome chromo[];
    /***************************************************************************************
    * constructor method
    * Creates a new instance of Population
    ***************************************************************************************/
    public Population()
    {

    }//end of method
    /***************************************************************************************
    * constructor method
    * Creates a new instance of Population
    ***************************************************************************************/
    public Population(Chromosome chromo1[])
    {
        chromo=chromo1;
        populationSize=chromo1.length;
    }//end of method
    /******************************************************************************************************
     * constructor method
     * Creates a new instance of Population from training DataSet
     ******************************************************************************************************/
    public Population(Dataset trainingDataSet,float fractionOfTrainingData)
    {
        populationSize=(int)(trainingDataSet.noOfRecords*fractionOfTrainingData);
        //System.out.println(populationSize);//for testing
        if(populationSize<20)
        {
            populationSize=20;//for small datasets
        }//end of if
        chromo=new Chromosome[populationSize];
        int serialNumberOfChromosome=0;
        do
        {
            /******************************************************************************************************
            * to generate a random number between 0 and maximum number of records-1 to select a record randomly
            ******************************************************************************************************/
            int randomNumber1=(int)(trainingDataSet.noOfRecords*Math.random());
            String classLabel1=trainingDataSet.dataSet[randomNumber1][trainingDataSet.noOfAttributes-1];
            /******************************************************************************************************
            * to generate a random number between 0 and maximum number of records-1 to select a record randomly
            ******************************************************************************************************/
            int randomNumber2=(int)(trainingDataSet.noOfRecords*Math.random());
            String classLabel2=trainingDataSet.dataSet[randomNumber2][trainingDataSet.noOfAttributes-1];
            if(classLabel1.equals(classLabel2))
            {                
                String Dna="";
                for(int attributeNo=0;attributeNo<=trainingDataSet.noOfAttributes-2;attributeNo++)
                {
                    String typeOfAttribute=trainingDataSet.attributeInformation[1][attributeNo];                    
                    if(typeOfAttribute.equals("Integer")||typeOfAttribute.equals("Float"))
                    {
                        String attributeValue1;
                        String attributeValue2;
                        if(trainingDataSet.dataSet[randomNumber1][attributeNo].equals("?")&!trainingDataSet.dataSet[randomNumber2][attributeNo].equals("?"))
                        {
                            attributeValue1="#";
                            attributeValue2=trainingDataSet.dataSet[randomNumber2][attributeNo];
                        }//end of if
                        else if(trainingDataSet.dataSet[randomNumber2][attributeNo].equals("?")&!trainingDataSet.dataSet[randomNumber1][attributeNo].equals("?"))
                        {
                            attributeValue1=trainingDataSet.dataSet[randomNumber1][attributeNo];
                            attributeValue2="#";
                        }//end of if
                        else if(trainingDataSet.dataSet[randomNumber1][attributeNo].equals("?")&trainingDataSet.dataSet[randomNumber2][attributeNo].equals("?"))
                        {                            
                            attributeValue1="#";
                            attributeValue2="#";
                        }//end of if
                        else
                        {
                            float randomNumber3=(float)(Math.random());
                            if(randomNumber3<0.5)
                            {
                                attributeValue1=trainingDataSet.dataSet[randomNumber1][attributeNo];
                                attributeValue2=trainingDataSet.dataSet[randomNumber2][attributeNo];
                            }//end of if
                            else
                            {
                                float randomNumber4=(float)(Math.random());
                                if(randomNumber4<0.5)
                                {
                                    attributeValue1="#";
                                    attributeValue2=trainingDataSet.dataSet[randomNumber2][attributeNo];
                                }//end of if
                                else
                                {
                                    attributeValue1=trainingDataSet.dataSet[randomNumber1][attributeNo];
                                    attributeValue2="#";
                                }//end of if
                            }//end of else
                        }//end of else
                        //System.out.println("attributeValue1="+attributeValue1);//for testing
                        //System.out.println("attributeValue2="+attributeValue2);//for testing
                        if(!attributeValue1.equals("#")&!attributeValue2.equals("#"))
                        {
                            if(Float.parseFloat(attributeValue2)<Float.parseFloat(attributeValue1))
                            {
                                float exChange=Float.parseFloat(attributeValue1);
                                attributeValue1=attributeValue2;
                                attributeValue2=String.valueOf(exChange);
                            }//end of if
                        }//end of if
                        if(!attributeValue1.equals("#"))
                        {
                            int counter=0;
                            //System.out.println("Number of change points="+trainingDataSet.changePoints[attributeNo].length);//for testing
                            for(int loopCounter=trainingDataSet.changePoints[attributeNo].length-1;loopCounter>=0;loopCounter--)
                            {
                                //System.out.println("loopCounter="+loopCounter);//for testing
                                //System.out.println("Value="+trainingDataSet.changePoints[attributeNo][loopCounter]);//for testing
                                if(Float.parseFloat(trainingDataSet.changePoints[attributeNo][loopCounter])<Float.parseFloat(attributeValue1))
                                {
                                    counter=loopCounter;
                                    break;
                                }//end of if
                            }//end of for
                            /*System.out.println("attributeNo="+attributeNo);//for testing
                            System.out.println("counter="+counter);//for testing
                            System.out.println("attributeValue1="+attributeValue1);//for testing*/
                            attributeValue1=trainingDataSet.changePoints[attributeNo][counter];
                            //System.out.println("attributeValue1="+attributeValue1);//for testing
                        }//end of if
                        if(!attributeValue2.equals("#"))
                        {
                            int counter=0;
                            for(int loopCounter=0;loopCounter<=trainingDataSet.changePoints[attributeNo].length-1;loopCounter++)
                            {
                                if(Float.parseFloat(trainingDataSet.changePoints[attributeNo][loopCounter])>Float.parseFloat(attributeValue2))
                                {
                                    counter=loopCounter;
                                    break;
                                }//end of if
                            }//end of for
                            /*System.out.println("attributeNo="+attributeNo);//for testing
                            System.out.println("counter="+counter);//for testing
                            System.out.println("attributeValue2="+attributeValue2);//for testing*/
                            attributeValue2=trainingDataSet.changePoints[attributeNo][counter];
                            //System.out.println("attributeValue2="+attributeValue2);//for testing
                        }//end of if                        
                        Dna=Dna.concat(String.valueOf(attributeValue1)).concat(",").concat(String.valueOf(attributeValue2)).concat("|");                        
                    }//end of if
                    /******************************************************************************************************
                    * For "Binary" and "String" type of attributes we will take two values seperated by * if two records contains
                     * different values, format value1*value2. If two records contains same value, we will take that value
                    ******************************************************************************************************/
                    else if(typeOfAttribute.equals("Binary")|typeOfAttribute.equals("String"))
                    {
                        String attributeValue1;
                        String attributeValue2;
                        if(trainingDataSet.dataSet[randomNumber1][attributeNo].equals("?")&!trainingDataSet.dataSet[randomNumber2][attributeNo].equals("?"))
                        {
                            attributeValue1=trainingDataSet.dataSet[randomNumber2][attributeNo];
                            attributeValue2=trainingDataSet.dataSet[randomNumber2][attributeNo];
                        }//end of if
                        else if(trainingDataSet.dataSet[randomNumber2][attributeNo].equals("?")&!trainingDataSet.dataSet[randomNumber1][attributeNo].equals("?"))
                        {
                            attributeValue1=trainingDataSet.dataSet[randomNumber1][attributeNo];
                            attributeValue2=trainingDataSet.dataSet[randomNumber1][attributeNo];
                        }//end of if
                        else if(trainingDataSet.dataSet[randomNumber1][attributeNo].equals("?")&trainingDataSet.dataSet[randomNumber2][attributeNo].equals("?"))
                        {                           
                            attributeValue1="#";
                            attributeValue2="#";
                        }//end of if
                        else
                        {
                            attributeValue1=trainingDataSet.dataSet[randomNumber1][attributeNo];
                            attributeValue2=trainingDataSet.dataSet[randomNumber2][attributeNo];
                        }//end of else
                        String binaryString="";
                        if(attributeValue1.equals("#")&attributeValue2.equals("#"))
                            binaryString=binaryString.concat("#");
                        else
                        {
                            for(int valueCounter=0;valueCounter<=trainingDataSet.noOfDifferentAttributeValues[attributeNo]-1;valueCounter++)
                            {
                                if(attributeValue1.equals(trainingDataSet.attributeValues[attributeNo][valueCounter])||attributeValue2.equals(trainingDataSet.attributeValues[attributeNo][valueCounter]))
                                {
                                    binaryString=binaryString.concat("1");
                                }//end of if
                                else
                                {
                                    float randomNo=(float)Math.random();
                                    if(randomNo<0.5)
                                        binaryString=binaryString.concat("1");
                                    else
                                        binaryString=binaryString.concat("0");
                                }//end of else
                            }//end of for
                        }//end of if
                        Dna=Dna.concat(binaryString).concat("|");
                    }//end of if
                }//end of for
                //System.out.println("Dna="+Dna);//for testing
                chromo[serialNumberOfChromosome]=new Chromosome(Dna,trainingDataSet);
                serialNumberOfChromosome++;
            }//end of if
        }while(serialNumberOfChromosome<=populationSize-1);        
    }//end of buildInitialPopulation    
    /***************************************************************************************
    * showDnaOfPopulation
    * 
    ***************************************************************************************/
    public void showDnaOfPopulation()
    {
        System.out.println("Population Size:"+populationSize);//for testing
        for(int chromosomeNo=0;chromosomeNo<=this.populationSize-1;chromosomeNo++)
        {
            this.chromo[chromosomeNo].showDnaOfChromosome();
        }//end of for       
    }//end of method
    /***************************************************************************************
    * showDnaAndGeneOfPopulation
    *
    ***************************************************************************************/
    public void showDnaAndGeneOfPopulation()
    {
        System.out.println("Population Size:"+populationSize);//for testing
        for(int chromosomeNo=0;chromosomeNo<=this.populationSize-1;chromosomeNo++)
        {
            this.chromo[chromosomeNo].showDnaAndGeneOfChromosome();
        }//end of for
    }//end of method
    /***************************************************************************************
    * showPopulation
    *
    ***************************************************************************************/
    public void showPopulation()
    {
        System.out.println("Population Size:"+populationSize);//for testing
        for(int chromosomeNo=0;chromosomeNo<=this.populationSize-1;chromosomeNo++)
        {
            this.chromo[chromosomeNo].showChromosome();
        }//end of for
    }//end of method
    /***************************************************************************************
    * sortingCSRs. This method sort CSRs in desending order of confidence. If Confidence is
    * same then it is as per decending order of coverage   *
    ***************************************************************************************/
    /*public Population sortingCSRs()
    {
        boolean flag[]=new boolean[this.populationSize];
        for(int chromosomeNo=0;chromosomeNo<=this.populationSize-1;chromosomeNo++)
        {
            flag[chromosomeNo]=true;
        }//end of for
        Chromosome chromo1[]=new Chromosome[this.populationSize];
        int counter=0;
        for(int loopCounter=0;loopCounter<=this.populationSize-1;loopCounter++)
        {
            float con=(float)0.0;
            float cov=(float)0.0;
            int chromosomeNumber=-1;
            for(int chromosomeNo=0;chromosomeNo<=this.populationSize-1;chromosomeNo++)
            {
                if(flag[chromosomeNo])
                {
                    if(con<this.chromo[chromosomeNo].confidence)
                    {
                        chromosomeNumber=chromosomeNo;
                        con=this.chromo[chromosomeNo].confidence;
                        cov=this.chromo[chromosomeNo].coverage;
                    }//end of if
                    else if(con==this.chromo[chromosomeNo].confidence)
                    {
                        if(cov<this.chromo[chromosomeNo].coverage)
                        {
                            chromosomeNumber=chromosomeNo;
                            cov=this.chromo[chromosomeNo].coverage;
                        }//end of if
                        else if(cov==this.chromo[chromosomeNo].coverage)
                        {
                            chromosomeNumber=chromosomeNo;
                        }//end of if
                    }//end of if
                }//end of if
            }//end of for
            flag[chromosomeNumber]=false;
            chromo1[counter]=this.chromo[chromosomeNumber];
            counter++;
            /*System.out.println("chromosomeNumber:"+chromosomeNumber);//for testing
            System.out.println("Confidence:"+this.chromo[chromosomeNumber].confidence);//for testing
            System.out.println("Coverage:"+this.chromo[chromosomeNumber].coverage);//for testing*/
        /*}//end of for
        Population sortedPopulation=new Population(chromo1);
        return sortedPopulation;
    }//end of method*/



    /***************************************************************************************
    * sortingCSRs. This method sort CSRs in desending order of confidence. If Confidence is
    * same then it is as per decending order of coverage   *
    ***************************************************************************************/
    public Population sortingCSRs()
    {
        boolean flag[]=new boolean[this.populationSize];
        for(int chromosomeNo=0;chromosomeNo<=this.populationSize-1;chromosomeNo++)
        {
            flag[chromosomeNo]=true;
        }//end of for
        Chromosome chromo1[]=new Chromosome[this.populationSize];
        int counter=0;
        for(int loopCounter=0;loopCounter<=this.populationSize-1;loopCounter++)
        {
            float con=(float)0.0;
            float cov=(float)0.0;
            float no_of_attributes=(float)1000.0;

            int chromosomeNumber=-1;
            for(int chromosomeNo=0;chromosomeNo<=this.populationSize-1;chromosomeNo++)
            {
                if(flag[chromosomeNo])
                {
                    if(con<this.chromo[chromosomeNo].confidence)
                    {
                        chromosomeNumber=chromosomeNo;
                        con=this.chromo[chromosomeNo].confidence;
                        cov=this.chromo[chromosomeNo].coverage;
                        no_of_attributes=this.chromo[chromosomeNo].noOfValidAttributes;
                    }//end of if
                    else if(con==this.chromo[chromosomeNo].confidence)
                    {
                        if(cov<this.chromo[chromosomeNo].coverage)
                        {
                            chromosomeNumber=chromosomeNo;
                            cov=this.chromo[chromosomeNo].coverage;
                            no_of_attributes=this.chromo[chromosomeNo].noOfValidAttributes;
                        }//end of if
                        else if(cov==this.chromo[chromosomeNo].coverage)
                        {
                            if(no_of_attributes>this.chromo[chromosomeNo].noOfValidAttributes)
                            {
                                chromosomeNumber=chromosomeNo;
                                no_of_attributes=this.chromo[chromosomeNo].noOfValidAttributes;
                            }//end of if
                            else if(no_of_attributes==this.chromo[chromosomeNo].noOfValidAttributes)
                            {
                                chromosomeNumber=chromosomeNo;
                            }//end of if
                        }//end of if
                    }//end of if
                }//end of if
            }//end of for
            //System.out.println("counter:"+counter);//for testing
            flag[chromosomeNumber]=false;
            chromo1[counter]=this.chromo[chromosomeNumber];
            counter++;
            /*System.out.println("chromosomeNumber:"+chromosomeNumber);//for testing
            System.out.println("positive_Confidence:"+this.chromo[chromosomeNumber].positive_confidence);//for testing
            System.out.println("nagetive_confidence:"+this.chromo[chromosomeNumber].nagetive_confidence);//for testing
            System.out.println("Coverage:"+this.chromo[chromosomeNumber].coverage);//for testing
            System.out.println("No of Valid Attributes:"+this.chromo[chromosomeNumber].noOfValidAttributes);//for testing*/
        }//end of for
        Population sortedPopulation=new Population(chromo1);
        return sortedPopulation;
    }//end of method  

    /***************************************************************************************
    * findMaxConfidenceAndMaxCoveragePerClass. This method will find Max Confidence
    * and coverage Per Class
    ***************************************************************************************/
    public void findMaxConfidenceAndMaxCoveragePerClass(Dataset trainingDataSet)
    {
        for(int loopCounter=0;loopCounter<=trainingDataSet.noOfClasses-1;loopCounter++)
        {
            System.out.println("Classes:"+trainingDataSet.classLevels[loopCounter]);//for testing
            float maxCon=(float)0.0;
            float maxCov=(float)0.0;
            for(int innerLoopCounter=0;innerLoopCounter<=this.populationSize-1;innerLoopCounter++)
            {
                if(this.chromo[innerLoopCounter].classLabel.equals(trainingDataSet.classLevels[loopCounter]))
                {
                    if(maxCon<this.chromo[innerLoopCounter].confidence)
                        maxCon=this.chromo[innerLoopCounter].confidence;
                    if(maxCov<this.chromo[innerLoopCounter].coverage)
                        maxCov=this.chromo[innerLoopCounter].coverage;
                }//end of if
            }//end of for
            System.out.println("Max confidence:"+maxCon);//for testing
            System.out.println("Max coverage:"+maxCov);//for testing
        }//end of for
    }//end of method    
     /***************************************************************************************
    * eliminateDuplecate()
    ***************************************************************************************/
    public Population eliminateDuplecate()
    {
        Population populationAfterEliminationgDuplicate;
        boolean flag[]=new boolean[this.chromo.length];
        for(int loopCounter=0;loopCounter<=this.chromo.length-1;loopCounter++)
        {
            flag[loopCounter]=true;
        }//end of for
        for(int outerCounter=0;outerCounter<=this.chromo.length-1;outerCounter++)
        {
            if(flag[outerCounter])
            {
                for(int innerCounter=outerCounter+1;innerCounter<=this.chromo.length-1;innerCounter++)
                {
                    if(flag[innerCounter])
                    {
                        if(this.chromo[outerCounter].Dna.equals(this.chromo[innerCounter].Dna))
                            flag[innerCounter]=true;
                    }//end of if
                }//end of for
            }//end of if
        }//end of for
        int counter=0;
        for(int loopCounter=0;loopCounter<=this.chromo.length-1;loopCounter++)
        {
            if(flag[loopCounter])
            {
                counter++;
            }//end of if
        }//end of for
        Chromosome chromo1[]=new Chromosome[counter];
        counter=0;
        for(int loopCounter=0;loopCounter<=this.chromo.length-1;loopCounter++)
        {
            if(flag[loopCounter])
            {
                chromo1[counter]=this.chromo[loopCounter];
                counter++;
            }//end of if
        }//end of for
        populationAfterEliminationgDuplicate=new Population(chromo1);
        return populationAfterEliminationgDuplicate;
    }//end of method
     /***************************************************************************************
    * paretoSelection()
    ***************************************************************************************/
    public Population paretoSelection()
    {
        Population populationAfterParetoSelection;
        boolean flag[]=new boolean[this.populationSize];
        for(int counter=0;counter<=this.populationSize-1;counter++)
        {
            //System.out.println(this.chromo[counter].classLabel);//for testing
            if(this.chromo[counter].classLabel.equals(""))//for rules which are not covering any records
                flag[counter]=false;
            else
                flag[counter]=true;
        }//end of for
        for(int outerLoopCounter=0;outerLoopCounter<=this.populationSize-1;outerLoopCounter++)
        {
            //String outerChromosomeDna=populationAfterFitnessCalculation.chromo[outerLoopCounter].Dna;
            String outerChromosomeClassLabel=this.chromo[outerLoopCounter].classLabel;
            float outerChromosomeConfidence=this.chromo[outerLoopCounter].confidence;
            //float outerChromosomeNagetive_Confidence=this.chromo[outerLoopCounter].nagetive_confidence;
            float outerChromosomeCoverage=this.chromo[outerLoopCounter].coverage;
            int outerChromosomeNoOfValidAttributes=this.chromo[outerLoopCounter].noOfValidAttributes;
            for(int innerLoopCounter=0;innerLoopCounter<=this.populationSize-1;innerLoopCounter++)
            {
                //String innerChromosomeDna=populationAfterFitnessCalculation.chromo[innerLoopCounter].Dna;
                String innerChromosomeClassLabel=this.chromo[innerLoopCounter].classLabel;
                float innerChromosomeConfidence=this.chromo[innerLoopCounter].confidence;
                //float innerChromosomeNagetive_Confidence=this.chromo[innerLoopCounter].nagetive_confidence;
                float innerChromosomeCoverage=this.chromo[innerLoopCounter].coverage;
                int innerChromosomeNoOfValidAttributes=this.chromo[innerLoopCounter].noOfValidAttributes;
                if(outerChromosomeClassLabel.equals(innerChromosomeClassLabel)&&(outerLoopCounter!=innerLoopCounter))//to compare rules of same class
                {
                    //if((innerChromosomeConfidence>outerChromosomeConfidence&&innerChromosomeCoverage>outerChromosomeCoverage)||(innerChromosomeConfidence==outerChromosomeConfidence&&innerChromosomeCoverage>outerChromosomeCoverage)||(innerChromosomeConfidence>outerChromosomeConfidence&&innerChromosomeCoverage==outerChromosomeCoverage))

                    /*if((innerChromosomePositive_Confidence>outerChromosomePositive_Confidence&&innerChromosomeCoverage>outerChromosomeCoverage&&innerChromosomeNagetive_Confidence>outerChromosomeNagetive_Confidence)
                        ||(innerChromosomePositive_Confidence>outerChromosomePositive_Confidence&&innerChromosomeCoverage>outerChromosomeCoverage&&innerChromosomeNagetive_Confidence==outerChromosomeNagetive_Confidence)
                        ||(innerChromosomePositive_Confidence>outerChromosomePositive_Confidence&&innerChromosomeCoverage==outerChromosomeCoverage&&innerChromosomeNagetive_Confidence>outerChromosomeNagetive_Confidence)
                        ||(innerChromosomePositive_Confidence==outerChromosomePositive_Confidence&&innerChromosomeCoverage>outerChromosomeCoverage&&innerChromosomeNagetive_Confidence>outerChromosomeNagetive_Confidence)
                        ||(innerChromosomePositive_Confidence>outerChromosomePositive_Confidence&&innerChromosomeCoverage==outerChromosomeCoverage&&innerChromosomeNagetive_Confidence==outerChromosomeNagetive_Confidence)
                        ||(innerChromosomePositive_Confidence==outerChromosomePositive_Confidence&&innerChromosomeCoverage>outerChromosomeCoverage&&innerChromosomeNagetive_Confidence==outerChromosomeNagetive_Confidence)
                        ||(innerChromosomePositive_Confidence==outerChromosomePositive_Confidence&&innerChromosomeCoverage==outerChromosomeCoverage&&innerChromosomeNagetive_Confidence>outerChromosomeNagetive_Confidence)
                        )*/
                    if((innerChromosomeConfidence>outerChromosomeConfidence&&innerChromosomeCoverage>outerChromosomeCoverage&&innerChromosomeNoOfValidAttributes<outerChromosomeNoOfValidAttributes)
                        ||(innerChromosomeConfidence>outerChromosomeConfidence&&innerChromosomeCoverage>outerChromosomeCoverage&&innerChromosomeNoOfValidAttributes==outerChromosomeNoOfValidAttributes)
                        ||(innerChromosomeConfidence>outerChromosomeConfidence&&innerChromosomeCoverage==outerChromosomeCoverage&&innerChromosomeNoOfValidAttributes<outerChromosomeNoOfValidAttributes)
                        ||(innerChromosomeConfidence==outerChromosomeConfidence&&innerChromosomeCoverage>outerChromosomeCoverage&&innerChromosomeNoOfValidAttributes<outerChromosomeNoOfValidAttributes)
                        ||(innerChromosomeConfidence>outerChromosomeConfidence&&innerChromosomeCoverage==outerChromosomeCoverage&&innerChromosomeNoOfValidAttributes==outerChromosomeNoOfValidAttributes)
                        ||(innerChromosomeConfidence==outerChromosomeConfidence&&innerChromosomeCoverage>outerChromosomeCoverage&&innerChromosomeNoOfValidAttributes==outerChromosomeNoOfValidAttributes)
                        ||(innerChromosomeConfidence==outerChromosomeConfidence&&innerChromosomeCoverage==outerChromosomeCoverage&&innerChromosomeNoOfValidAttributes<outerChromosomeNoOfValidAttributes)
                        )
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
                    {
                        flag[outerLoopCounter]=false;
                        break;
                    }//end of if
                }//end of if
            }//end of innerForLoop
        }//end of outerForLoop
        int noOfParetoChromosome=0;
        for(int counter=0;counter<=this.populationSize-1;counter++)
        {
            if(flag[counter])
                noOfParetoChromosome++;
        }//end of for
        Chromosome chromo1[]=new Chromosome[noOfParetoChromosome];
        int counter1=0;
        for(int counter=0;counter<=this.populationSize-1;counter++)
        {
            if(flag[counter])
            {
                chromo1[counter1]=this.chromo[counter];
                counter1++;
            }//end of if
        }//end of for
        populationAfterParetoSelection=new Population(chromo1);
        return populationAfterParetoSelection;
    }//end of method
    /***************************************************************************************************
     * fitnessCalculation method will calculate Accuracy(Confidence)=AUC/A and Coverage=AUC/C
     *****************************************************************************************************/
     /*public Population fitnessCalculation(Dataset trainingDataSet1)
     {
        /******************************************************************************************************
        *for storing chromosome after Fitness Calculation
        ******************************************************************************************************/
        /*Chromosome chromo[]=new Chromosome[this.populationSize];
        int noOfClass=trainingDataSet1.noOfClasses;
        String classLabel[]=trainingDataSet1.classLevels;
        int classLabelCounter[]=new int[noOfClass];
        //String gene[]=new String[trainingDataSet1.noOfAttributes-1];
        //String minValues[]=new String[trainingDataSet1.noOfAttributes-1];
        //String maxValues[]=new String[trainingDataSet1.noOfAttributes-1];
        //String categoricalTypeValues[]=new String[trainingDataSet1.noOfAttributes-1];
        for(int counter=0;counter<=this.populationSize-1;counter++)
        {
            int A=0;
            int C=0;
            int AUC=0;            
            float Confidence=(float)0.0,Coverage=(float)0.0;
            for(int counter1=0;counter1<=noOfClass-1;counter1++)
            {
                classLabelCounter[counter1]=0;
            }//end of for            
            /*******************************************************************************************************
            *Calculating A
            ********************************************************************************************************/
            /*boolean flag=true;
            for(int counter6=0;counter6<=trainingDataSet1.noOfRecords-1;counter6++)
            {
                flag=true;
                for(int counter7=0;counter7<=trainingDataSet1.noOfAttributes-2;counter7++)
                {
                    //System.out.println("minValues[counter7]="+minValues[counter7]);//for testing
                    if(trainingDataSet1.attributeInformation[1][counter7].equals("Integer")|trainingDataSet1.attributeInformation[1][counter7].equals("Float"))
                    {
                        //if(gene[counter7].equals("#")|trainingDataSet.dataSet[counter6][counter7].equals("?"))
                        if(this.chromo[counter].gen[counter7].geneValue.equals("#")|trainingDataSet1.dataSet[counter6][counter7].equals("?"))
                        {
                            //do nothing
                        }//end of if
                        //else if((Float.parseFloat(trainingDataSet.dataSet[counter6][counter7])>=Float.parseFloat(minValues[counter7]))&&((Float.parseFloat(trainingDataSet.dataSet[counter6][counter7])<=Float.parseFloat(maxValues[counter7]))))
                        else if((Float.parseFloat(trainingDataSet1.dataSet[counter6][counter7])>=Float.parseFloat(this.chromo[counter].gen[counter7].minGeneValue))&&((Float.parseFloat(trainingDataSet1.dataSet[counter6][counter7])<=Float.parseFloat(this.chromo[counter].gen[counter7].maxGeneValue))))
                        {
                            //do nothing
                        }//end of if
                        else
                        {
                            flag=false;
                            break;
                        }//end of else
                    }//end of if
                    else if(trainingDataSet1.attributeInformation[1][counter7].equals("Binary")|trainingDataSet1.attributeInformation[1][counter7].equals("String"))
                    {
                        //if(gene[counter7].equals("#")|trainingDataSet.dataSet[counter6][counter7].equals("?"))
                        if(this.chromo[counter].gen[counter7].geneValue.equals("#")|trainingDataSet1.dataSet[counter6][counter7].equals("?"))
                        {
                            //do nothing
                        }//end of if
                        else
                        {                            
                            boolean flag1=true;
                            //for(int i=0;i<=counter3;i++)
                            for(int i=0;i<=this.chromo[counter].gen[counter7].catGeneValue.length-1;i++)
                            {
                                //if(catValues[i].equals(trainingDataSet.dataSet[counter6][counter7]))
                                if(this.chromo[counter].gen[counter7].catGeneValue[i].equals(trainingDataSet1.dataSet[counter6][counter7]))
                                {
                                    flag1=false;
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
                if(flag)
                {
                    A++;
                    /*******************************************************************************************************
                    *Deciding Consequence
                    ********************************************************************************************************/
                    /*String classLabel1=trainingDataSet1.dataSet[counter6][trainingDataSet1.noOfAttributes-1];
                    for(int counter9=0;counter9<=noOfClass-1;counter9++)
                    {
                        if(classLabel1.equals(classLabel[counter9]))
                        {
                            classLabelCounter[counter9]++;
                        }//end of if
                    }//end of for
                }//end of if
            }//end of for
            //System.out.println("A="+A);//for testing
            /*******************************************************************************************************
            *Deciding class Label
            ********************************************************************************************************/
            /*String chosenClassLabel="";
            //int maxClassLabelCounter=0;
            int maxClassLabelCounter=0;
            //System.out.println("___________________________");
            for(int counter9=0;counter9<=noOfClass-1;counter9++)
            {
                if(maxClassLabelCounter<classLabelCounter[counter9])
                {
                    maxClassLabelCounter=classLabelCounter[counter9];
                    //maxClassLabelCounter=(float)(classLabelCounter[counter9]/classLabelsCounterForRecords[counter9]);
                    chosenClassLabel=classLabel[counter9];
                    //System.out.println("chosenClassLabel="+chosenClassLabel);
                }//end of if
            }//end of for
            //System.out.println("maxClassLabelCounter="+maxClassLabelCounter);//for testing
            //System.out.println("chosenClassLabel="+chosenClassLabel);//for testing
            /*******************************************************************************************************
            *Calculating AUC
            ********************************************************************************************************/
            /*for(int counter6=0;counter6<=trainingDataSet1.noOfRecords-1;counter6++)
            {
                flag=true;
                for(int counter7=0;counter7<=trainingDataSet1.noOfAttributes-2;counter7++)
                {
                    //System.out.println("minValues[counter7]="+minValues[counter7]);//for testing
                    if(trainingDataSet1.attributeInformation[1][counter7].equals("Integer")|trainingDataSet1.attributeInformation[1][counter7].equals("Float"))
                    {
                        //if(gene[counter7].equals("#")|trainingDataSet.dataSet[counter6][counter7].equals("?"))
                        if(this.chromo[counter].gen[counter7].geneValue.equals("#")|trainingDataSet1.dataSet[counter6][counter7].equals("?"))
                        {
                            //do nothing
                        }//end of if
                        //else if((Float.parseFloat(trainingDataSet.dataSet[counter6][counter7])>=Float.parseFloat(minValues[counter7]))&&((Float.parseFloat(trainingDataSet.dataSet[counter6][counter7])<=Float.parseFloat(maxValues[counter7]))))
                        else if((Float.parseFloat(trainingDataSet1.dataSet[counter6][counter7])>=Float.parseFloat(this.chromo[counter].gen[counter7].minGeneValue))&&((Float.parseFloat(trainingDataSet1.dataSet[counter6][counter7])<=Float.parseFloat(this.chromo[counter].gen[counter7].maxGeneValue))))
                        {
                            //do nothing
                        }//end of if
                        else
                        {
                            flag=false;
                            break;
                        }//end of else
                    }//end of if
                    else if(trainingDataSet1.attributeInformation[1][counter7].equals("Binary")|trainingDataSet1.attributeInformation[1][counter7].equals("String"))
                    {
                        //if(gene[counter7].equals("#")|trainingDataSet.dataSet[counter6][counter7].equals("?"))
                        if(this.chromo[counter].gen[counter7].geneValue.equals("#")|trainingDataSet1.dataSet[counter6][counter7].equals("?"))
                        {
                            //do nothing
                        }//end of if
                        else
                        {                            
                            boolean flag1=true;
                            //for(int i=0;i<=counter3;i++)
                            for(int i=0;i<=this.chromo[counter].gen[counter7].catGeneValue.length-1;i++)
                            {
                                //if(catValues[i].equals(trainingDataSet.dataSet[counter6][counter7]))
                                if(this.chromo[counter].gen[counter7].catGeneValue[i].equals(trainingDataSet1.dataSet[counter6][counter7]))
                                {
                                    flag1=false;
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
                if(flag)
                {
                    if(chosenClassLabel.equals(trainingDataSet1.dataSet[counter6][trainingDataSet1.noOfAttributes-1]))
                    {
                        AUC++;                        
                    }//end of if                    
                }//end of if                
                if(chosenClassLabel.equals(trainingDataSet1.dataSet[counter6][trainingDataSet1.noOfAttributes-1]))
                    C++;
            }//end of for
            if(A!=0&&C!=0)
            {
                Confidence=(float)AUC/A;
                Coverage=(float)AUC/C;
            }//end of if           
            int noOfValidAttributes=returnNoOfValidAttributes(this.chromo[counter]);
            //chromo[counter]=new Chromosome(this.chromo[counter].Dna,chosenClassLabel,A,C,AUC,Confidence,Coverage,noOfValidAttributes,trainingDataSet1);
            //chromo[counter]=new Chromosome(this.chromo[counter].Dna,chosenClassLabel,A,C,AUC,objective1,Coverage,objective2,noOfValidAttributes,trainingDataSet1);
            chromo[counter]=new Chromosome(this.chromo[counter].Dna,chosenClassLabel,A,C,AUC,Confidence,Coverage,noOfValidAttributes,trainingDataSet1);
            //chromo[counter]=new Chromosome(this.chromo[counter].Dna,chosenClassLabel,A,C,AUC,positive_confidence,negative_confidence,objective6,noOfValidAttributes,trainingDataSet1);
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
        for(int counter=0;counter<=chromo.gen.length-1;counter++)
        {
            if(!chromo.gen[counter].geneValue.equals("#"))
               noOfValidAttributes++;
        }//end of for
        return noOfValidAttributes;
    }//end of returnNoOfValidAttributes()
}//end of class