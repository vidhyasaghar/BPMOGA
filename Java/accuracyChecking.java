/****************************************************************************************************
 *Class accuracyChecking
 *This class will check accuracy of the rules generated 
 *****************************************************************************************************/ 
public class accuracyChecking
{
    float coverageOfTestDataSet=(float)0.0;
    float confidenceOfTestDataSet=(float)0.0;
    int numberOfCSRs=0;
    /***************************************************************************************
    * Constructor
    *
    ***************************************************************************************/
    accuracyChecking(Dataset testDataSet,ChromosomeForP2 chromoForP2,Dataset trainingDataSet)
    {        
        /*chromoForP2.showDnaOfChromosomeForP2();//for testing
        chromoForP2.showTotalConfidenceChromosomeForP2();
        chromoForP2.showTotalCoverageChromosomeForP2();
        System.out.println("____________________________");//for testing*/
        chromoForP2=replaceMinAndMaxValuesOfRules(chromoForP2,trainingDataSet);
        //ParetoCRsFromBPMA.chromoForP2[slNumberOfChosenRule].sortedPopulationByP1.showPopulation();
        //System.out.println("____________________________");//for testing*/
        //chromoForP2.sortedPopulationByP1.chromo[0].showChromosome();//for testing
        String predictedClassLabels[]=new String[testDataSet.noOfRecords];
        //testDataSet.showDataset();//for testing
        for(int counter1=0;counter1<=testDataSet.noOfRecords-1;counter1++)
        {            
            predictedClassLabels[counter1]=predictClassLabel(testDataSet.dataSet[counter1],chromoForP2,trainingDataSet);
        }//end of for
        /*for(int counter1=0;counter1<=testDataSet.noOfRecords-1;counter1++)
        {
             System.out.println("Actual Class Lavel:"+testDataSet.dataSet[counter1][testDataSet.noOfAttributes-1]+" "+"Predicted Class Lavel:"+predictedClassLabels[counter1]);//for testing
        }//end of for
        System.out.println("____________________________");//for testing*/
        coverageOfTestDataSet=calculateCoverage(predictedClassLabels);
        System.out.println("coverageOfTestDataSet="+coverageOfTestDataSet);//for testing
        if(coverageOfTestDataSet<1)
        {
            //String dominentClassLabel=findClassLabelOfDominentClass(trainingDataSet);//where dominent class label is the majority class in the training dataset
            String dominentClassLabel=findClassLabelByUnmatchedTraningData(chromoForP2,trainingDataSet);//where dominent class label is the majority class in the training dataset which are not covered by rule
            //System.out.println("dominentClassLabel="+dominentClassLabel);//for testing
            for(int counter1=0;counter1<=predictedClassLabels.length-1;counter1++)
            {
                if(predictedClassLabels[counter1].equals("Not covered"))                
                    predictedClassLabels[counter1]=dominentClassLabel;               
            }//end of for
        }//end of if
        confidenceOfTestDataSet=calculateConfidence(predictedClassLabels,testDataSet);
        for(int counter1=0;counter1<=testDataSet.noOfRecords-1;counter1++)
        {
             System.out.println("Actual Class Lavel:"+testDataSet.dataSet[counter1][testDataSet.noOfAttributes-1]+" "+"Predicted Class Lavel:"+predictedClassLabels[counter1]);//for testing
        }//end of for
        System.out.println("____________________________");//for testing
        System.out.println("confidenceOfTestDataSet="+confidenceOfTestDataSet);//for testing
        numberOfCSRs=chromoForP2.numberOfCSRs;
        System.out.println("numberOfCSRs="+numberOfCSRs);//for testing
        System.out.println("____________________________");//for testing
        int confutionMatrix[][]=new int[trainingDataSet.noOfClasses][trainingDataSet.noOfClasses];
        for(int testRecordNo=0;testRecordNo<=testDataSet.noOfRecords-1;testRecordNo++)
        {
            for(int actualClassCounter=0;actualClassCounter<=trainingDataSet.noOfClasses-1;actualClassCounter++)
            {
                for(int predictedClassCounter=0;predictedClassCounter<=trainingDataSet.noOfClasses-1;predictedClassCounter++)
                {
                    if(testDataSet.dataSet[testRecordNo][testDataSet.noOfAttributes-1].equals(trainingDataSet.classLevels[actualClassCounter])&&predictedClassLabels[testRecordNo].equals(trainingDataSet.classLevels[predictedClassCounter]))
                    confutionMatrix[actualClassCounter][predictedClassCounter]++;
                }//end of for
            }//end of for
        }//end of for
        System.out.println("Confusion Matrix");//for testing
        System.out.println("Actual Class Labels                             Predicted Class Labels");//for testing
        for(int predictedClassCounter=0;predictedClassCounter<=trainingDataSet.noOfClasses-1;predictedClassCounter++)
        {
            System.out.print("       "+trainingDataSet.classLevels[predictedClassCounter]);//for testing
        }//end of for
        System.out.println();
        for(int actualClassCounter=0;actualClassCounter<=trainingDataSet.noOfClasses-1;actualClassCounter++)
        {
            System.out.print(trainingDataSet.classLevels[actualClassCounter]+"      ");//for testing
            for(int predictedClassCounter=0;predictedClassCounter<=trainingDataSet.noOfClasses-1;predictedClassCounter++)
            {
                System.out.print(confutionMatrix[actualClassCounter][predictedClassCounter]+"       ");//for testing
            }//end of for
            System.out.println();
        }//end of for
    }//end of constructor
    /************************************************************************************************
    * selectCR
    /************************************************************************************************/
    /*public int selectCR(PopulationForP2 ParetoCRsFromBPMA)
    {
        float maxTotalConf=ParetoCRsFromBPMA.chromoForP2[0].totalConfidence;
        float maxTotalCov=ParetoCRsFromBPMA.chromoForP2[0].totalCoverage;
        int minCSRs=ParetoCRsFromBPMA.chromoForP2[0].numberOfCSRs;
        int slNumberOfChosenRule=0;
        for(int counter2=1;counter2<=ParetoCRsFromBPMA.populationSizeForP2-1;counter2++)
        {
            if(maxTotalConf<ParetoCRsFromBPMA.chromoForP2[counter2].totalConfidence)
            {
                maxTotalConf=ParetoCRsFromBPMA.chromoForP2[counter2].totalConfidence;
                maxTotalCov=ParetoCRsFromBPMA.chromoForP2[counter2].totalCoverage;
                minCSRs=ParetoCRsFromBPMA.chromoForP2[counter2].numberOfCSRs;
                slNumberOfChosenRule=counter2;
            }//end of if
            else if(maxTotalConf==ParetoCRsFromBPMA.chromoForP2[counter2].totalConfidence)
            {
                if(maxTotalCov<ParetoCRsFromBPMA.chromoForP2[counter2].totalCoverage)
                {
                    maxTotalCov=ParetoCRsFromBPMA.chromoForP2[counter2].totalCoverage;
                    minCSRs=ParetoCRsFromBPMA.chromoForP2[counter2].numberOfCSRs;
                    slNumberOfChosenRule=counter2;
                }//end of if
                else if(maxTotalCov==ParetoCRsFromBPMA.chromoForP2[counter2].totalCoverage)
                {
                    if(minCSRs>ParetoCRsFromBPMA.chromoForP2[counter2].numberOfCSRs)
                    {
                        minCSRs=ParetoCRsFromBPMA.chromoForP2[counter2].numberOfCSRs;
                        slNumberOfChosenRule=counter2;
                    }//end of if
                }//end of else
            }//end of else
        }//end of for        
        return slNumberOfChosenRule;
    }//end of selectCR()*/
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
                //completeRule.sortedPopulationByP1.chromo[chromosomeNo].showChromosome();//for testing
                boolean flag=false;
                for(int attributeNo=0;attributeNo<=trainingDataSet.noOfAttributes-2;attributeNo++)
                {
                    if(trainingDataSet.attributeInformation[1][attributeNo].equals("Integer")|trainingDataSet.attributeInformation[1][attributeNo].equals("Float"))
                    {
                        if(completeRule.sortedPopulationByP1.chromo[chromosomeNo].gen[attributeNo].geneValue.equals("#")|record[attributeNo].equals("?"))
                        {
                            //do nothing
                        }//end of if
                        else if(completeRule.sortedPopulationByP1.chromo[chromosomeNo].gen[attributeNo].minGeneValue.equals("#"))
                        {
                            //System.out.println(populationAfterEliminatingDuplicate.chromo[chromosomeNo].gen[attributeNo].geneValue);//for testing
                            if(Float.parseFloat(record[attributeNo])<=Float.parseFloat(completeRule.sortedPopulationByP1.chromo[chromosomeNo].gen[attributeNo].maxGeneValue))
                            {
                                flag=true;
                            }//end of if
                            else
                            {
                                flag=false;
                                break;
                            }//end of else
                        }//end of if
                        else if(completeRule.sortedPopulationByP1.chromo[chromosomeNo].gen[attributeNo].maxGeneValue.equals("#"))
                        {
                            //System.out.println(populationAfterEliminatingDuplicate.chromo[chromosomeNo].gen[attributeNo].geneValue);//for testing
                            if(Float.parseFloat(record[attributeNo])>=Float.parseFloat(completeRule.sortedPopulationByP1.chromo[chromosomeNo].gen[attributeNo].minGeneValue))
                            {
                                flag=true;
                            }//end of if
                            else
                            {
                                flag=false;
                                break;
                            }//end of else
                        }//end of else
                        else if((Float.parseFloat(record[attributeNo])>=Float.parseFloat(completeRule.sortedPopulationByP1.chromo[chromosomeNo].gen[attributeNo].minGeneValue))&&((Float.parseFloat(record[attributeNo])<=Float.parseFloat(completeRule.sortedPopulationByP1.chromo[chromosomeNo].gen[attributeNo].maxGeneValue))))
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
                        if(completeRule.sortedPopulationByP1.chromo[chromosomeNo].gen[attributeNo].geneValue.equals("#")|record[attributeNo].equals("?"))
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
                if(flag)
                {
                    predictedClassLabel=completeRule.sortedPopulationByP1.chromo[chromosomeNo].classLabel;
                    //System.out.println(chromosomeNo);//for testing
                    break;
                }//end of if
            }//end of if
        }//end of for
        return predictedClassLabel;
    }//end of predictClassLabel()    
    /************************************************************************************************
    * calculateCoverage
    /************************************************************************************************/
    public float calculateCoverage(String decidedClassLabel[])
    {
        int noOfRecordsNotCovered=0;
        float coverageOfDataSet=(float)0.0;
        for(int counter1=0;counter1<=decidedClassLabel.length-1;counter1++)
        {            
            if(decidedClassLabel[counter1].equals("Not covered"))
                noOfRecordsNotCovered++;
        }//end of for        
        coverageOfDataSet=(float)(((float)decidedClassLabel.length-(float)noOfRecordsNotCovered)/(float)decidedClassLabel.length);
        return coverageOfDataSet;
    }//end of calculateCoverage()    
    /************************************************************************************************
    * findClassLabelByUnmatchedTraningData
    /************************************************************************************************/
    public String findClassLabelByUnmatchedTraningData(ChromosomeForP2 completeRule,Dataset trainingDataSet)
    {
        String dominentClassLabel="";
        String predictedClassLabels1[]=new String[trainingDataSet.noOfRecords];
        /************************************************************************************************
        * find predicted ClassLabels of training dataset
        /************************************************************************************************/
        for(int counter1=0;counter1<=trainingDataSet.noOfRecords-1;counter1++)
        {
            predictedClassLabels1[counter1]=predictClassLabel(trainingDataSet.dataSet[counter1],completeRule,trainingDataSet);
            //System.out.println("predictedClassLabels1[counter1]="+predictedClassLabels1[counter1]+"Actual Class Label="+trainingDataSet.dataSet[counter1][trainingDataSet.noOfAttributes-1]);//for testing
        }//end of for
        /************************************************************************************************
        * wheather all records of training dataset is covered or not
        /************************************************************************************************/
        boolean flag=true;
        for(int counter1=0;counter1<=trainingDataSet.noOfRecords-1;counter1++)
        {
            if(predictedClassLabels1[counter1].equals("Not covered"))
            {
                flag=false;
                break;
            }//end of if
        }//end of for
        /************************************************************************************************
        * if all records of training dataset is covered then dominent ClassLabel is the majority class label of training dataset
        /************************************************************************************************/
        if(flag)
        {
            dominentClassLabel=findClassLabelOfDominentClass(trainingDataSet);
        }//end of if
        /************************************************************************************************
        * if all records of training dataset is not covered then dominent ClassLabel will be the class
         * label with the largest number of unmatched training data
        /************************************************************************************************/
        else
        {
            int classCounter[]=new int[trainingDataSet.noOfClasses];
            for(int counter1=0;counter1<=trainingDataSet.noOfRecords-1;counter1++)
            {
                if(predictedClassLabels1[counter1].equals("Not covered"))
                {
                    String classLabel=trainingDataSet.dataSet[counter1][trainingDataSet.noOfAttributes-1];
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
    * findClassLabelOfDominentClass
    /************************************************************************************************/
    public String findClassLabelOfDominentClass(Dataset trainingDataSet)
    {
        String dominentClassLabel="";
        int classCounter[]=new int[trainingDataSet.noOfClasses];
        for(int counter1=0;counter1<=trainingDataSet.noOfRecords-1;counter1++)
        {
            for(int counter2=0;counter2<=trainingDataSet.noOfClasses-1;counter2++)
            {
                if(trainingDataSet.dataSet[counter1][trainingDataSet.noOfAttributes-1].equals(trainingDataSet.classLevels[counter2]))
                {
                    classCounter[counter2]++;
                    break;
                }//end of if
            }//end of for
        }//end of for
        int maxClassCounter=0;
        for(int counter1=0;counter1<=trainingDataSet.noOfClasses-1;counter1++)
        {
            if(classCounter[counter1]>maxClassCounter)
            {
                maxClassCounter=classCounter[counter1];
                dominentClassLabel=trainingDataSet.classLevels[counter1];
            }//end of if
        }//end of for
        return dominentClassLabel;
    }//end of findClassLabelOfDominentClass()
    /************************************************************************************************
    * calculateConfidence
    /************************************************************************************************/
    public float calculateConfidence(String decidedClassLabels[],Dataset testDataSet)
    {
        float confidenceOfDataSet=(float)0.0;
        int noOfMatch=0;
        for(int counter1=0;counter1<=decidedClassLabels.length-1;counter1++)
        {
            if(decidedClassLabels[counter1].equals(testDataSet.dataSet[counter1][testDataSet.noOfAttributes-1]))
                noOfMatch++;
        }//end of for
        confidenceOfDataSet=(float)noOfMatch/decidedClassLabels.length;
        return confidenceOfDataSet;
    }//end of findClassLabelOfDominentClass()
    /************************************************************************************************
    * findingDefaultClassLabel
    /************************************************************************************************/
    public String findingDefaultClassLabel(String trainingRecords[][],String valuesOfAttributes[])
    {
        String defaultClassLabel="";
        /*******************************************************************************************************
        * Finding different class labels
        ********************************************************************************************************/            
        int noOfClass=0;        
        for(int i=0;i<=valuesOfAttributes[valuesOfAttributes.length-1].length()-1;i++)
        {
            if(valuesOfAttributes[valuesOfAttributes.length-1].charAt(i)==',')
            {
                noOfClass++;                    
            }//end of if
        }//end of for        
        String classLabels[]=new String[noOfClass];       
        int classLabelsCounterForRecords[]=new int[noOfClass];  
        int start=1;
        int end=0;
        int counter8=0;        
        //System.out.println("valuesOfAttributes[valuesOfAttributes.length-1]="+valuesOfAttributes[valuesOfAttributes.length-1]);//for testing
        for(int i=1;i<=valuesOfAttributes[valuesOfAttributes.length-1].length()-1;i++)
        {
            if(valuesOfAttributes[valuesOfAttributes.length-1].charAt(i)==',')
            {
                end=i;
                classLabels[counter8]=valuesOfAttributes[valuesOfAttributes.length-1].substring(start,end);   
                //System.out.println("classLabels[counter8]="+classLabels[counter8]);//for testing
                counter8++;
                start=end+1;
            }//end of if
            if(i==valuesOfAttributes[valuesOfAttributes.length-1].length()-1)
            {
                end=i;
                classLabels[counter8]=valuesOfAttributes[valuesOfAttributes.length-1].substring(start,end+1);  
                //System.out.println("classLabels[counter8]="+classLabels[counter8]);//for testing
            }//end of if
        }//end of for 
        /*for(int counter12=0;counter12<=noOfClass-1;counter12++)
        {
            System.out.println("classLabels[counter12]="+classLabels[counter12]);//for testing
        }//end of for */
        int noOfAttributesofRecords=trainingRecords[0].length;
        //System.out.println("noOfAttributesofRecords="+noOfAttributesofRecords);//for testing
        for(int counter11=0;counter11<=trainingRecords.length-1;counter11++)
        {
            String classLabelForRecord=trainingRecords[counter11][noOfAttributesofRecords-1];
            //System.out.println(classLabelForRecord);
            for(int counter12=0;counter12<=noOfClass-1;counter12++)
            {
                if(classLabelForRecord.equals(classLabels[counter12]))
                {
                    classLabelsCounterForRecords[counter12]++;
                    break;
                }//end of if
            }//end of for 
        }//end of for 
        /*for(int counter12=0;counter12<=noOfClass-1;counter12++)
        {
            System.out.println("classLabelsCounterForRecords[counter12]="+classLabelsCounterForRecords[counter12]);//for testing
        }//end of for*/
        int maxClassLebelCounter=0;
        for(int counter12=0;counter12<=noOfClass-1;counter12++)
        {
            if(maxClassLebelCounter<classLabelsCounterForRecords[counter12])
            {
                maxClassLebelCounter=classLabelsCounterForRecords[counter12];
                defaultClassLabel=classLabels[counter12];
            }//end of if
        }//end of for
        //System.out.println("defaultClassLabel="+defaultClassLabel);//for testing
        return defaultClassLabel;        
    }//end of findingDefaultClassLabel()
    /************************************************************************************************
    * replaceMinAndMaxValuesOfRules
    /************************************************************************************************/
    public ChromosomeForP2 replaceMinAndMaxValuesOfRules(ChromosomeForP2 completeRule,Dataset trainingDataSet)
    {
        //System.out.println("Within replaceMinAndMaxValuesOfRules");//for testing
        //completeRule.showDnaOfChromosomeForP2();//for testing
        for(int dnaNumber=0;dnaNumber<=completeRule.DnaForP2.length()-1;dnaNumber++)
        {
            if(completeRule.DnaForP2.charAt(dnaNumber)=='1')
            {
                Chromosome chromosomeOfRule=completeRule.sortedPopulationByP1.chromo[dnaNumber];
                //completeRule.sortedPopulationByP1.chromo[dnaNumber].showDnaOfChromosome();//for testing
                boolean flag1=false;
                for(int attributeNumber=0;attributeNumber<=trainingDataSet.noOfAttributes-2;attributeNumber++)
                {
                    String attributesType=trainingDataSet.attributeInformation[1][attributeNumber];
                    if(attributesType.equals("Integer")||attributesType.equals("Float"))
                    {
                        boolean flag=false;
                        if(chromosomeOfRule.gen[attributeNumber].minGeneValue.equals(trainingDataSet.minValues[attributeNumber]))
                        {
                            chromosomeOfRule.gen[attributeNumber].minGeneValue="#";
                            flag=true;                            
                        }//end of if
                        if(chromosomeOfRule.gen[attributeNumber].maxGeneValue.equals(trainingDataSet.maxValues[attributeNumber]))
                        {
                            chromosomeOfRule.gen[attributeNumber].maxGeneValue="#";
                            flag=true;                            
                        }//end of if
                        if(flag)
                        {
                            if(chromosomeOfRule.gen[attributeNumber].minGeneValue.equals("#")&&chromosomeOfRule.gen[attributeNumber].maxGeneValue.equals("#"))
                            {
                                chromosomeOfRule.gen[attributeNumber].geneValue="#";
                            }//end of if
                            else
                            {
                                chromosomeOfRule.gen[attributeNumber].geneValue=chromosomeOfRule.gen[attributeNumber].minGeneValue.concat(",").concat(chromosomeOfRule.gen[attributeNumber].maxGeneValue);
                            }//end of else
                            flag1=true;
                        }//end of if
                    }//end of if
                }//end of for
                if(flag1)
                {
                    String Dna="";
                    for(int attributeNumber=0;attributeNumber<=trainingDataSet.noOfAttributes-2;attributeNumber++)
                    {
                        Dna=Dna.concat(chromosomeOfRule.gen[attributeNumber].geneValue).concat("|");
                    }//end of for
                    completeRule.sortedPopulationByP1.chromo[dnaNumber].Dna=Dna;
                }//end of if
                //completeRule.sortedPopulationByP1.chromo[dnaNumber].showDnaOfChromosome();//for testing
            }//end of if
        }//end of for
        //completeRule.showDnaOfChromosomeForP2();//for testing
        ChromosomeForP2 modifiedCompleteRule=new ChromosomeForP2(completeRule.DnaForP2,completeRule.totalConfidence,completeRule.totalCoverage,completeRule.numberOfCSRs,completeRule.sortedPopulationByP1);
        return modifiedCompleteRule;
    }//end of method
}//end of accuracyChecking class 
