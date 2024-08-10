
public class PopulationForP2
{
    int populationSizeForP2;
    ChromosomeForP2 chromoForP2[];
    /******************************************************************************************************
     * constructor method
     * Creates a new instance of PopulationForP2
     ******************************************************************************************************/
    public PopulationForP2()
    {

    }//end of PopulationForP2()
    /******************************************************************************************************
     * constructor method
     * Creates a new instance of PopulationForP2 from population by P1MOGA
     * ******************************************************************************************************/
    public PopulationForP2(Population sortedPopulationFromP1MOGA,int sizeOfInitialPopulationOfP2MOGA,float classRulesProbP2)
    {
        populationSizeForP2=sizeOfInitialPopulationOfP2MOGA;
        chromoForP2=new ChromosomeForP2[populationSizeForP2];
        int serialNumberOfChromosomeForP2=0;
        do
        {
            String DnaStringForP2="";
            int noOfValidCSRs=0;
            for(int loopCounter=0;loopCounter<=sortedPopulationFromP1MOGA.populationSize-1;loopCounter++)
            {
                float randomNo=(float)Math.random();
                if(randomNo<classRulesProbP2)
                {
                    DnaStringForP2=DnaStringForP2.concat("1");
                    noOfValidCSRs++;
                }//end of if
                else
                    DnaStringForP2=DnaStringForP2.concat("0");
            }//end of for
            chromoForP2[serialNumberOfChromosomeForP2]=new ChromosomeForP2(DnaStringForP2,sortedPopulationFromP1MOGA);
                serialNumberOfChromosomeForP2++;
        }while(serialNumberOfChromosomeForP2<=sizeOfInitialPopulationOfP2MOGA-1);
    }//end of PopulationForP2()
    /******************************************************************************************************
     * constructor method
     * Creates a new instance of PopulationForP2 from population by P1MOGA
     * In every CR there will be at least one CSR from each class label
     * ******************************************************************************************************/
    /*public PopulationForP2(Population sortedPopulationFromP1MOGA,int sizeOfInitialPopulationOfP2MOGA,float classRulesProbP2)
    {
        populationSizeForP2=sizeOfInitialPopulationOfP2MOGA;
        //System.out.println("populationSizeForP2:"+populationSizeForP2);//for testing
        chromoForP2=new ChromosomeForP2[populationSizeForP2];
        int noOfClassesInCSRs=0;
        boolean flag[]=new boolean[sortedPopulationFromP1MOGA.populationSize];
        /******************************************************************************************************
        * To find out the number of classes in CSRs
        ******************************************************************************************************/
        /*for(int counter=0;counter<=sortedPopulationFromP1MOGA.populationSize-1;counter++)
        {
            flag[counter]=true;
        }//end of for
        for(int outerLoopCounter=0;outerLoopCounter<=sortedPopulationFromP1MOGA.populationSize-1;outerLoopCounter++)
        {
            if(flag[outerLoopCounter])
            {
                noOfClassesInCSRs++;
                for(int innerLoopCounter=0;innerLoopCounter<=sortedPopulationFromP1MOGA.populationSize-1;innerLoopCounter++)
                {
                    if(sortedPopulationFromP1MOGA.chromo[outerLoopCounter].classLabel.equals(sortedPopulationFromP1MOGA.chromo[innerLoopCounter].classLabel))
                        flag[innerLoopCounter]=false;
                }//end of for
            }//end of if
        }//end of for
        //System.out.println("noOfClassesInCSRs:"+noOfClassesInCSRs);//for testing
        /******************************************************************************************************
        * To store class labels of CSRs
        ******************************************************************************************************/
        /*String classLabels[]=new String[noOfClassesInCSRs];
        for(int counter=0;counter<=sortedPopulationFromP1MOGA.populationSize-1;counter++)
        {
            flag[counter]=true;
        }//end of for
        int classCounter=0;
        for(int outerLoopCounter=0;outerLoopCounter<=sortedPopulationFromP1MOGA.populationSize-1;outerLoopCounter++)
        {
            if(flag[outerLoopCounter])
            {
                classLabels[classCounter]=sortedPopulationFromP1MOGA.chromo[outerLoopCounter].classLabel;
                classCounter++;
                for(int innerLoopCounter=0;innerLoopCounter<=sortedPopulationFromP1MOGA.populationSize-1;innerLoopCounter++)
                {
                    if(sortedPopulationFromP1MOGA.chromo[outerLoopCounter].classLabel.equals(sortedPopulationFromP1MOGA.chromo[innerLoopCounter].classLabel))
                        flag[innerLoopCounter]=false;
                }//end of for
            }//end of if
        }//end of for
        /*for(int counter=0;counter<=noOfClassesInCSRs-1;counter++)
        {
            System.out.println(classLabels[counter]);//for testing
        }//end of for*/        
        /******************************************************************************************************
        * To create population for p2
        ******************************************************************************************************/
        /*int serialNumberOfChromosomeForP2=0;
        do
        {
            String DnaStringForP2="";
            int noOfValidCSRs=0;
            for(int loopCounter=0;loopCounter<=sortedPopulationFromP1MOGA.populationSize-1;loopCounter++)
            {
                float randomNo=(float)Math.random();
                if(randomNo<classRulesProbP2)
                {
                    DnaStringForP2=DnaStringForP2.concat("1");
                    noOfValidCSRs++;
                }//end of if
                else
                    DnaStringForP2=DnaStringForP2.concat("0");
            }//end of for
            //System.out.println("DnaStringForP2="+DnaStringForP2);//for testing
            String classLabelsofValidCSRs[]=new String[noOfValidCSRs];
            int counter=0;
            for(int loopCounter=0;loopCounter<=sortedPopulationFromP1MOGA.populationSize-1;loopCounter++)
            {
                if(DnaStringForP2.charAt(loopCounter)=='1')
                {
                    classLabelsofValidCSRs[counter]=sortedPopulationFromP1MOGA.chromo[loopCounter].classLabel;
                    counter++;
                }//end of if
            }//end of for
            /*for(int loopCounter=0;loopCounter<=noOfValidCSRs-1;loopCounter++)
            {
                System.out.println("classLabelsofValidCSRs[counter]="+classLabelsofValidCSRs[loopCounter]);//for testing
            }//end of for*/
            /*for(int loopCounter=0;loopCounter<=sortedPopulationFromP1MOGA.populationSize-1;loopCounter++)
            {
                flag[loopCounter]=true;
            }//end of for
            int noOfClassesInValidCSRs=0;
            for(int outerLoopCounter=0;outerLoopCounter<=sortedPopulationFromP1MOGA.populationSize-1;outerLoopCounter++)
            {
                if(flag[outerLoopCounter]&DnaStringForP2.charAt(outerLoopCounter)=='1')
                {
                    noOfClassesInValidCSRs++;
                    for(int innerLoopCounter=0;innerLoopCounter<=sortedPopulationFromP1MOGA.populationSize-1;innerLoopCounter++)
                    {
                        if(DnaStringForP2.charAt(innerLoopCounter)=='1')
                        {
                            if(sortedPopulationFromP1MOGA.chromo[outerLoopCounter].classLabel.equals(sortedPopulationFromP1MOGA.chromo[innerLoopCounter].classLabel))
                                flag[innerLoopCounter]=false;
                        }//end of if
                    }//end of for
                }//end of if
            }//end of for
            //System.out.println("noOfClassesInValidCSRs="+noOfClassesInValidCSRs);//for testing
            if(noOfClassesInValidCSRs==noOfClassesInCSRs)
            {
                chromoForP2[serialNumberOfChromosomeForP2]=new ChromosomeForP2(DnaStringForP2,sortedPopulationFromP1MOGA);
                serialNumberOfChromosomeForP2++;
            }//end of if
        }while(serialNumberOfChromosomeForP2<=sizeOfInitialPopulationOfP2MOGA-1);           
    }//end of PopulationForP2()
    
    /******************************************************************************************************
     * constructor method
     * Creates a new instance of PopulationForP2 from population by P1MOGA
     ******************************************************************************************************/
    public PopulationForP2(ChromosomeForP2 chromoForP21[],int sizeOfPopulationOfP2MOGA1)
    {
        populationSizeForP2=sizeOfPopulationOfP2MOGA1;
        chromoForP2=chromoForP21;
    }//end of PopulationForP2()
    /***************************************************************************************
    * showDnaOfPopulationForP2
    *
    ***************************************************************************************/
    public void showDnaOfPopulationForP2()
    {
        System.out.println("Population Size For P2:"+populationSizeForP2);//for testing
        for(int chromosomeNoForP2=0;chromosomeNoForP2<=this.populationSizeForP2-1;chromosomeNoForP2++)
        {
            this.chromoForP2[chromosomeNoForP2].showDnaOfChromosomeForP2();
        }//end of for
    }//end of method
    /***************************************************************************************
    * showPopulationForP2
    *
    ***************************************************************************************/
    public void showPopulationForP2()
    {
        System.out.println("Population Size For P2:"+populationSizeForP2);//for testing
        for(int chromosomeNoForP2=0;chromosomeNoForP2<=this.populationSizeForP2-1;chromosomeNoForP2++)
        {
            this.chromoForP2[chromosomeNoForP2].showDnaOfChromosomeForP2();
            this.chromoForP2[chromosomeNoForP2].showTotalConfidenceChromosomeForP2();
            this.chromoForP2[chromosomeNoForP2].showTotalCoverageChromosomeForP2();
            this.chromoForP2[chromosomeNoForP2].showNumberOfCSRsChromosomeForP2();
            //this.chromoForP2[chromosomeNoForP2].sortedPopulationByP1.showPopulation();
        }//end of for
    }//end of method
    /***************************************************************************************
    * findMaxTotalConfidenceMaxTotalCoverageMinNORules
    *
    ***************************************************************************************/
    public void findMaxTotalConfidenceMaxTotalCoverageMinNORules()
    {
        float totalMaxCon=(float)0.0;
        float totalMaxCov=(float)0.0;
        int nCSR=1000;
        for(int innerLoopCounter=0;innerLoopCounter<=this.populationSizeForP2-1;innerLoopCounter++)
        {
            if(totalMaxCon<this.chromoForP2[innerLoopCounter].totalConfidence)
                        totalMaxCon=this.chromoForP2[innerLoopCounter].totalConfidence;
            if(totalMaxCov<this.chromoForP2[innerLoopCounter].totalCoverage)
                        totalMaxCov=this.chromoForP2[innerLoopCounter].totalCoverage;
            if(nCSR>this.chromoForP2[innerLoopCounter].numberOfCSRs)
                        nCSR=this.chromoForP2[innerLoopCounter].numberOfCSRs;
        }//end of for
        System.out.println("Total Max confidence:"+totalMaxCon);//for testing
        System.out.println("Total Max coverage:"+totalMaxCov);//for testing
        System.out.println("Total Min CSR:"+nCSR);//for testing
        System.out.println("_______________________");//for testing
    }//end of method
    /***************************************************************************************
    * sortingChromoOfP2. This method sort Total Rules in desending order of confidence.
     * If Confidence is same then it is as per decending order of coverage.
     * If Confidence and coverage are same then it is as per asending order of CSR*
    ***************************************************************************************/
    public PopulationForP2 sortingChromoOfP2()
    {
        boolean flag[]=new boolean[this.populationSizeForP2];
        ChromosomeForP2 sortedChromoForP2[]=new ChromosomeForP2[this.populationSizeForP2];
        for(int chromosomeNo=0;chromosomeNo<=this.populationSizeForP2-1;chromosomeNo++)
        {
            flag[chromosomeNo]=true;
        }//end of for
        int counter=0;
        for(int loopCounter=0;loopCounter<=this.populationSizeForP2-1;loopCounter++)
        {
            float tCon=(float)0.0;
            float tCov=(float)0.0;
            int nCSR=1000;
            int chromosomeNumber=-1;
            for(int chromosomeNo=0;chromosomeNo<=this.populationSizeForP2-1;chromosomeNo++)
            {
                if(flag[chromosomeNo])
                {
                    if(tCon<this.chromoForP2[chromosomeNo].totalConfidence)
                    {
                        chromosomeNumber=chromosomeNo;
                        tCon=this.chromoForP2[chromosomeNo].totalConfidence;
                        tCov=this.chromoForP2[chromosomeNo].totalCoverage;
                        nCSR=this.chromoForP2[chromosomeNo].numberOfCSRs;
                    }//end of if
                    else if(tCon==this.chromoForP2[chromosomeNo].totalConfidence)
                    {
                        if(tCov<chromoForP2[chromosomeNo].totalCoverage)
                        {
                            chromosomeNumber=chromosomeNo;
                            tCov=this.chromoForP2[chromosomeNo].totalCoverage;
                            nCSR=this.chromoForP2[chromosomeNo].numberOfCSRs;
                        }//end of if
                        else if(tCov==this.chromoForP2[chromosomeNo].totalCoverage)
                        {
                            if(nCSR>chromoForP2[chromosomeNo].numberOfCSRs)
                            {
                                chromosomeNumber=chromosomeNo;
                                nCSR=this.chromoForP2[chromosomeNo].numberOfCSRs;
                            }//end of if
                            else if(nCSR==this.chromoForP2[chromosomeNo].numberOfCSRs)
                            {
                                chromosomeNumber=chromosomeNo;
                            }//end of if
                        }//end of if
                    }//end of if
                }//end of if
            }//end of for
            flag[chromosomeNumber]=false;
            sortedChromoForP2[counter]=this.chromoForP2[chromosomeNumber];
            counter++;
            /*System.out.println("chromosomeNumber:"+chromosomeNumber);//for testing
            System.out.println("Total Confidence:"+this.chromoForP2[chromosomeNumber].totalConfidence);//for testing
            System.out.println("Total Coverage:"+this.chromoForP2[chromosomeNumber].totalCoverage);//for testing
            System.out.println("Number of CSRs:"+this.chromoForP2[chromosomeNumber].numberOfCSRs);//for testing
            for(int chromosomeNo=0;chromosomeNo<=this.populationSizeForP2-1;chromosomeNo++)
            {
                System.out.println("flag["+chromosomeNo+"]"+flag[chromosomeNo]);//for testing
            }//end of for*/
        }//end of for
        PopulationForP2 sortedPopulation=new PopulationForP2(sortedChromoForP2,chromoForP2.length);
        return sortedPopulation;
    }//end of sortingChromoOfP2    
}//end of class
