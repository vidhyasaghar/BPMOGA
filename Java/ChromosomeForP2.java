
public class ChromosomeForP2
{
    String DnaForP2;
    float totalConfidence=0;
    float totalCoverage=0;
    int numberOfCSRs=0;
    Population sortedPopulationByP1;
    /***************************************************************************************
    * Constructor
    *
    ***************************************************************************************/
    public ChromosomeForP2(String DnaForP21,Population sortedPopulationByP11)
    {
        DnaForP2=DnaForP21;
        sortedPopulationByP1=sortedPopulationByP11;
    }//end of Constructor
    /***************************************************************************************
    * Constructor
    *
    ***************************************************************************************/
    public ChromosomeForP2(String DnaForP21,float totalConfidence1,float totalCoverage1,int numberOfCSRs1,Population sortedPopulationByP11)
    {
        DnaForP2=DnaForP21;
        totalConfidence=totalConfidence1;
        totalCoverage=totalCoverage1;
        numberOfCSRs=numberOfCSRs1;
        sortedPopulationByP1=sortedPopulationByP11;
    }//end of Constructor
    /***************************************************************************************
    * showDnaOfChromosome
    *
    ***************************************************************************************/
    public void showDnaOfChromosomeForP2()
    {
        System.out.println("DnaForP2="+DnaForP2);
    }//end of method
    /***************************************************************************************
    * showTotalConfidenceOfChromosome
    *
    ***************************************************************************************/
    public void showTotalConfidenceChromosomeForP2()
    {
        System.out.println("totalConfidence="+totalConfidence);
    }//end of method
    /***************************************************************************************
    * showTotalCoverageOfChromosome
    *
    ***************************************************************************************/
    public void showTotalCoverageChromosomeForP2()
    {
        System.out.println("totalCoverage="+totalCoverage);
    }//end of method
    /***************************************************************************************
    * showNumberOfCSRsOfChromosome
    *
    ***************************************************************************************/
    public void showNumberOfCSRsChromosomeForP2()
    {
        System.out.println("numberOfCSRs="+numberOfCSRs);
    }//end of method
    /***************************************************************************************
    *calculateFitnessForChromosomesForP2()
    *
    ***************************************************************************************/
    public void calculateFitnessForChromosomesForP2(Dataset trainingDataSet)
    {
        //this.showDnaOfChromosomeForP2();//for testing
        //this.sortedPopulationByP1.showPopulation();//for testing
        //System.out.println("_________________________");//for testing
        String tConfidenceAndtCoverage=findTotalConfidenceAndCoverage(trainingDataSet);
        totalConfidence=Float.parseFloat(tConfidenceAndtCoverage.substring(0, tConfidenceAndtCoverage.indexOf(',')));
        totalCoverage=Float.parseFloat(tConfidenceAndtCoverage.substring(tConfidenceAndtCoverage.indexOf(',')+1));
        numberOfCSRs=findNumberOfCSRsInCR();
        /*System.out.println("totalConfidence="+totalConfidence);//for testing
        System.out.println("totalCoverage="+totalCoverage);//for testing
        System.out.println("numberOfCSRs="+numberOfCSRs);//for testing*/
    }//end of method
    /***************************************************************************************
    * findTotalConfidenceAndCoverage
    *
    ***************************************************************************************/
    public String findTotalConfidenceAndCoverage(Dataset trainingDataSet)
    {
        float tConfidence=0;
        float tCoverage=0;
        int noOfMatch=0;
        int noOfCovered=0;
        String defaultClassLabel=findClassLabelByUnmatchedTrainingData(this,trainingDataSet);
        for(int recordNo=0;recordNo<=trainingDataSet.noOfRecords-1;recordNo++)
        {            
            boolean coveredFlag=false;
            for(int chromosomeNo=0;chromosomeNo<=this.sortedPopulationByP1.populationSize-1;chromosomeNo++)
            {
                if(this.DnaForP2.charAt(chromosomeNo)=='1')
                {
                    if(this.sortedPopulationByP1.chromo[chromosomeNo].covered_record_nos.contains(recordNo))
                    {
                        coveredFlag=true;
                        noOfCovered++;
                        //System.out.println("Record="+recordNo+"     "+"Rule"+chromosomeNo);//for testing
                        if(this.sortedPopulationByP1.chromo[chromosomeNo].classLabel.equals(trainingDataSet.dataSet[recordNo][trainingDataSet.noOfAttributes-1]))
                        {
                            noOfMatch++;
                        }//end of if
                        break;
                    }//end of if
                }//end of if
            }//end of for
            if(!coveredFlag)
            {
                if(defaultClassLabel.equals(trainingDataSet.dataSet[recordNo][trainingDataSet.noOfAttributes-1]))
                {
                    noOfMatch++;
                }//end of if
            }//end of if
        }//end of for
        tConfidence=(float)noOfMatch/trainingDataSet.noOfRecords;
        tCoverage=(float)noOfCovered/trainingDataSet.noOfRecords;
        String tConfidenceAndtCoverage=Float.toString(tConfidence).concat(",").concat(Float.toString(tCoverage));
        return tConfidenceAndtCoverage;
    }//end of method
    /************************************************************************************************
    * findClassLabelByUnmatchedTrainingData
    /************************************************************************************************/
    public String findClassLabelByUnmatchedTrainingData(ChromosomeForP2 completeRule,Dataset trainingDataSet)
    {
        String dominentClassLabel="";
        boolean flag[] = new boolean[trainingDataSet.noOfRecords];//default value is false
        String predictedClassLabels1[]=new String[trainingDataSet.noOfRecords];
        /************************************************************************************************
        * find coverage of records in training dataset
        /************************************************************************************************/
        for(int recordNo=0;recordNo<=trainingDataSet.noOfRecords-1;recordNo++)
        {
            for(int chromosomeNo=0;chromosomeNo<=this.sortedPopulationByP1.populationSize-1;chromosomeNo++)
            {
                if(this.DnaForP2.charAt(chromosomeNo)=='1')
                {
                    if(this.sortedPopulationByP1.chromo[chromosomeNo].covered_record_nos.contains(recordNo))
                    {
                        flag[recordNo]=true;
                        break;
                    }//end of if
                }//end of if
            }//end of for
        }//end of for
        /************************************************************************************************
        * wheather all records of training dataset is covered or not
        /************************************************************************************************/
        boolean flag1=true;
        for(int recordNo=0;recordNo<=trainingDataSet.noOfRecords-1;recordNo++)
        {
            if(!flag[recordNo])
            {
                flag1=false;
                break;
            }//end of if
        }//end of for
        /************************************************************************************************
        * if all records of training dataset is covered then dominent ClassLabel is the majority class label of training dataset
        /************************************************************************************************/
        if(flag1)
        {
            dominentClassLabel=trainingDataSet.dominentClassLabel;
        }//end of if
        /************************************************************************************************
        * if all records of training dataset is not covered then dominent ClassLabel will be the class
        * label with the largest number of unmatched training data
        /************************************************************************************************/
        else
        {
            int classCounter[]=new int[trainingDataSet.noOfClasses];
            for(int recordNo=0;recordNo<=trainingDataSet.noOfRecords-1;recordNo++)
            {
                if(!flag[recordNo])
                {
                    String classLabel=trainingDataSet.dataSet[recordNo][trainingDataSet.noOfAttributes-1];
                    for(int counter2=0;counter2<=trainingDataSet.noOfClasses-1;counter2++)
                    {
                        if(classLabel.equals(trainingDataSet.classLevels[counter2]))
                        {
                            classCounter[counter2]++;
                            break;
                        }//end of if
                    }//end of for
                }//end of if
            }//end of for
            int maxClassCounter=classCounter[0];
            dominentClassLabel=trainingDataSet.classLevels[0];
            for(int counter2=1;counter2<=trainingDataSet.noOfClasses-1;counter2++)
            {
                if(maxClassCounter<classCounter[counter2])
                {
                    maxClassCounter=classCounter[counter2];
                    dominentClassLabel=trainingDataSet.classLevels[counter2];
                }//end of if
            }//end of for
        }//end of else
        //System.out.println("dominentClassLabel="+dominentClassLabel);//for testing
        return dominentClassLabel;
    }//end of findClassLabelByUnmatchedTraningData()
    /************************************************************************************************
    * predictClassLabel
    /************************************************************************************************/
    public String predictClassLabel(String record[],ChromosomeForP2 completeRule,Dataset trainingDataSet)
    {
        String predictedClassLabel="Not covered";
        for(int chromosomeNo=0;chromosomeNo<=completeRule.sortedPopulationByP1.populationSize-1;chromosomeNo++)
        {
            if(completeRule.DnaForP2.charAt(chromosomeNo)=='1')
            {
                boolean flag=true;
                for(int attributeNo=0;attributeNo<=trainingDataSet.noOfAttributes-2;attributeNo++)
                {
                    if(trainingDataSet.attributeInformation[1][attributeNo].equals("Integer")||trainingDataSet.attributeInformation[1][attributeNo].equals("Float"))
                    {
                        if(completeRule.sortedPopulationByP1.chromo[chromosomeNo].gen[attributeNo].geneValue.equals("#")||record[attributeNo].equals("?"))
                        {
                            //do nothing
                        }//end of if                        
                        else if(completeRule.sortedPopulationByP1.chromo[chromosomeNo].gen[attributeNo].minGeneValue.equals("#"))
                        {
                            if(Float.parseFloat(record[attributeNo])<=Float.parseFloat(completeRule.sortedPopulationByP1.chromo[chromosomeNo].gen[attributeNo].maxGeneValue))
                            {
                                //do nothing
                            }//end of if
                            else
                            {
                                flag=false;
                                break;//perticular rule is not covering the record
                            }//end of else
                        }//end of if
                        else if(completeRule.sortedPopulationByP1.chromo[chromosomeNo].gen[attributeNo].maxGeneValue.equals("#"))
                        {
                            if(Float.parseFloat(record[attributeNo])>=Float.parseFloat(completeRule.sortedPopulationByP1.chromo[chromosomeNo].gen[attributeNo].minGeneValue))
                            {
                                //do nothing
                            }//end of if
                            else
                            {
                                flag=false;
                                break;//perticular rule is not covering the record
                            }//end of else
                        }//end of if
                        else if((Float.parseFloat(record[attributeNo])>=Float.parseFloat(completeRule.sortedPopulationByP1.chromo[chromosomeNo].gen[attributeNo].minGeneValue))&&((Float.parseFloat(record[attributeNo])<=Float.parseFloat(completeRule.sortedPopulationByP1.chromo[chromosomeNo].gen[attributeNo].maxGeneValue))))
                        {
                            //do nothing
                        }//end of if
                        else
                        {
                            flag=false;
                            break;//perticular rule is not covering the record
                        }//end of else
                    }//end of if
                    else if(trainingDataSet.attributeInformation[1][attributeNo].equals("Binary")||trainingDataSet.attributeInformation[1][attributeNo].equals("String"))
                    {
                        if(completeRule.sortedPopulationByP1.chromo[chromosomeNo].gen[attributeNo].geneValue.equals("#")||record[attributeNo].equals("?"))
                        {
                            //do nothing
                        }//end of if
                        else
                        {
                            boolean flag1=true;
                            for(int i=0;i<=completeRule.sortedPopulationByP1.chromo[chromosomeNo].gen[attributeNo].catGeneValue.length-1;i++)
                            {
                                if(completeRule.sortedPopulationByP1.chromo[chromosomeNo].gen[attributeNo].catGeneValue[i].equals(record[attributeNo]))
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
                    predictedClassLabel=completeRule.sortedPopulationByP1.chromo[chromosomeNo].classLabel;
                    //System.out.println(counter2);//for testing
                    break;
                }//end of if
            }//end of if
        }//end of for
        return predictedClassLabel;
    }//end of predictClassLabel()   
     /***************************************************************************************
    * findNumberOfCSRsInCR
    *
    ***************************************************************************************/
    public int findNumberOfCSRsInCR()
    {
        int noOfCSRs=0;
        //System.out.println("findNumberOfCSRsInCR="+DnaForP2);//for testing
        for(int counter=0;counter<=DnaForP2.length()-1;counter++)
        {
            if(DnaForP2.charAt(counter)=='1')
            {
                noOfCSRs++;
            }//end of if
        }//end of for
        //System.out.println("noOfCSRs="+noOfCSRs);//for testing
        return noOfCSRs;
    }//end of method
}//end of class
