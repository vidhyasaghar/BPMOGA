
public class main
{
    public static void main(String args[])
    {
        /*******************************************************************************************************
        *all dataset
        **********************************************************************************************************/
        //String dataset="AUTO";String filename="automobile1";
        //String dataset="CREDIT";String filename="credit10";
        //String dataset="DERMATOLOGY";String filename="dermatology9";
        //String dataset="DIADETES";String filename="pima10";
        //String dataset="ECOLI";String filename="ecoli10";
        //String dataset="FLARE";String filename="flare10";
        //String dataset="GLASS";String filename="glass10";
        //String dataset="HEART_C";String filename="heart_c10";
        //String dataset="HEBERMAN";String filename="haberman10";
        //String dataset="IRIS";String filename="iris1";
        //String dataset="LABOR";String filename="labor10";
        //String dataset="LED";String filename="led7digit10";
        //String dataset="MONK";String filename="monk10";
        //String dataset="NEWTHYROID";String filename="newthyroid10";
        //String dataset="SONAR";String filename="sonar10";
        String dataset="VEHICLE";String filename="vehicle10";
        //String dataset="VOWEL";String filename="vowel10";
        //String dataset="WINE";String filename="wine10";
        //String dataset="WISCONSIN";String filename="wisconsin10";
        //String dataset="YEAST";String filename="yeast10";
        //String dataset="ZOO";String filename="zoo10";

        
        //String dataset="ANNEAL";
        //String dataset="TEST";
        System.out.println(dataset);//for testing
        /********************************************************************************************************/
        /*********************************************^**********************************************************
        *calling buildTwoDimentionalArrayOfData method to build two dimentional array of records from Original.data file
        *******************************************************************************************************/
        String resultForTrainingData[]=new String[36];
        float confidenceForTrainingData[]=new float[10];
        float coverageForTrainingData[]=new float[10];
        int noOfCSRsForTrainingData[]=new int[10];

        String resultForTestData[]=new String[36];
        float confidenceForTestData[]=new float[10];
        float coverageForTestData[]=new float[10];
        int noOfCSRsForTestData[]=new int[10];

        String rules[]=new String[10];
        for(int number=1;number<=10;number++)//for ten fold CV
        {           
            System.out.println("Fold:"+number);//for testing
            String path_Training_DataSet="C:/Users/UTI/My Documents/NetBeansProjects/BPMOGA_Classification/src/"+dataset+"/"+filename+"/INPUT_FILES/"+filename+"-10-"+number+"tra.dat";//for ten fold CV
            //String path_Training_DataSet="C:/Users/UTI/My Documents/NetBeansProjects/BPMOGA_Classification/src/"+dataset+"/INPUT_FILES/Training"+number+".data";//for ten fold CV
            //String path_Training_DataSet="C:/Users/UTI/My Documents/NetBeansProjects/BPMOGA_Classification/src/"+dataset+"/INPUT_FILES/Original.data";//for 100% training and testing
            String path_Attribute_Information="C:/Users/UTI/My Documents/NetBeansProjects/BPMOGA_Classification/src/"+dataset+"/"+filename+"/INPUT_FILES/Attribute_information.data";
            //String path_Attribute_Information="C:/Users/UTI/Documents/NetBeansProjects/BPMOGA_Classification/src/"+dataset+"/INPUT_FILES/Attribute_information.data";//for test dataset only
            String dlimiter=",";
            Dataset training_DataSet= new Dataset(path_Training_DataSet,dlimiter,path_Attribute_Information,dlimiter,"training");
            String path_Test_DataSet="C:/Users/UTI/My Documents/NetBeansProjects/BPMOGA_Classification/src/"+dataset+"/"+filename+"/INPUT_FILES/"+filename+"-10-"+number+"tst.dat";//for ten fold CV
            //String path_Test_DataSet="C:/Users/UTI/My Documents/NetBeansProjects/BPMOGA_Classification/src/"+dataset+"/INPUT_FILES/Test"+number+".data";//for ten fold CV
            //String path_Test_DataSet="C:/Users/UTI/My Documents/NetBeansProjects/BPMOGA_Classification/src/"+dataset+"/INPUT_FILES/Original.data";//for 100% training and testing
            //String path_Test_DataSet="C:/Users/UTI/My Documents/NetBeansProjects/BPMOGA_Classification/src/"+dataset+"/INPUT_FILES/Training"+number+".data";//for test dataset only
            Dataset test_DataSet= new Dataset(path_Test_DataSet,dlimiter,path_Attribute_Information,dlimiter,"test");
            /*******************************************************************************************************
            *calling buildInitialPopulation method
            ********************************************************************************************************/
            float fractionOfTrainingData=(float)0.1;//fraction of training data used for building initial population
            Population initialPopulation=new Population(training_DataSet,fractionOfTrainingData);
            /*initialPopulation.showDnaOfPopulation();//for testing
            System.out.println("____________________________");//for testing*/
            /*******************************************************************************************************
            *calling cromosomeForGA method
            ********************************************************************************************************/
            int numberOfGenerationofBPMOGA=1000;
            int numberOfGenerationofP1MOGA=50;
            int numberOfGenerationofP2MOGA=50;
            int sizeOfInitialPopulationOfP2MOGA=20;
            float minCrossProbP1=(float)0.5;
            float maxCrossProbP1=(float)0.5;
            float minMuProbP1=(float)1.0;
            float maxMuProbP1=(float)1.0;
            float minRuleProbP2=(float)0.5;
            float maxRuleProbP2=(float)0.5;
            float minCrossProbP2=(float)0.5;
            float maxCrossProbP2=(float)0.5;
            float minMuProbP2=(float)0.1;
            float maxMuProbP2=(float)0.1;
            biPhasedMOGA BPMOGA=new biPhasedMOGA(training_DataSet,initialPopulation,numberOfGenerationofBPMOGA,numberOfGenerationofP1MOGA,numberOfGenerationofP2MOGA,sizeOfInitialPopulationOfP2MOGA,fractionOfTrainingData,minCrossProbP1,maxCrossProbP1,minMuProbP1,maxMuProbP1,minRuleProbP2,maxRuleProbP2,minCrossProbP2,maxCrossProbP2,minMuProbP2,maxMuProbP2);
            //BPMOGA.ParetoCRsFromBPMA.showPopulationForP2();//for testing
            //accuracyChecking ACForTrainingData=new  accuracyChecking(training_DataSet,BPMOGA.ParetoCRsFromBPMA,training_DataSet);//constructor called
            //BPMOGA.ParetoCRsFromBPMA.showPopulationForP2();//for testing
            confidenceForTrainingData[number-1]=BPMOGA.ParetoCRsFromBPMA.chromoForP2[0].totalConfidence;
            coverageForTrainingData[number-1]=BPMOGA.ParetoCRsFromBPMA.chromoForP2[0].totalCoverage;
            noOfCSRsForTrainingData[number-1]=BPMOGA.ParetoCRsFromBPMA.chromoForP2[0].numberOfCSRs;
            //PopulationForP2 sortedPopulationFromBPMOGA=BPMOGA.ParetoCRsFromBPMA.sortingChromoOfP2();
            //sortedPopulationFromBPMOGA.showPopulationForP2();//for testing
            /*for(int loopCounter=0;loopCounter<=BPMOGA.ParetoCRsFromBPMA.populationSizeForP2-1;loopCounter++)
            {
                accuracyChecking ACForTestData=new accuracyChecking(test_DataSet,BPMOGA.ParetoCRsFromBPMA.chromoForP2[loopCounter],training_DataSet);//constructor called
                System.out.println("Rule Number="+loopCounter);//for testing
                System.out.println("Confidence for training data="+BPMOGA.ParetoCRsFromBPMA.chromoForP2[loopCounter].totalConfidence);//for testing
                System.out.println("Coverage for training data="+BPMOGA.ParetoCRsFromBPMA.chromoForP2[loopCounter].totalCoverage);//for testing                

                System.out.println("Confidence for test data="+ACForTestData.confidenceOfTestDataSet);//for testing
                System.out.println("Coverage for test data="+ACForTestData.coverageOfTestDataSet);//for testing
                System.out.println("No of CSRs="+ACForTestData.numberOfCSRs);//for testing
                System.out.println("____________________________________");//for testing
            }//end of for*/
            accuracyChecking ACForTestData=new accuracyChecking(test_DataSet,BPMOGA.ParetoCRsFromBPMA.chromoForP2[0],training_DataSet);//constructor called
            confidenceForTestData[number-1]=ACForTestData.confidenceOfTestDataSet;
            coverageForTestData[number-1]=ACForTestData.coverageOfTestDataSet;
            noOfCSRsForTestData[number-1]=ACForTestData.numberOfCSRs;
            rules[number-1]="";
            int ruleCounter=1;
            for(int loopCounter=0;loopCounter<=BPMOGA.ParetoCRsFromBPMA.chromoForP2[0].sortedPopulationByP1.populationSize-1;loopCounter++)
            {
                if(BPMOGA.ParetoCRsFromBPMA.chromoForP2[0].DnaForP2.charAt(loopCounter)=='1')
                {
                    String rule="IF ".concat(BPMOGA.ParetoCRsFromBPMA.chromoForP2[0].sortedPopulationByP1.chromo[loopCounter].Dna)
                            .concat(" THEN ").concat(BPMOGA.ParetoCRsFromBPMA.chromoForP2[0].sortedPopulationByP1.chromo[loopCounter].classLabel)
                            .concat(" With Confidence=").concat(String.valueOf(BPMOGA.ParetoCRsFromBPMA.chromoForP2[0].sortedPopulationByP1.chromo[loopCounter].confidence))
                            .concat(" and Coverage=").concat(String.valueOf(BPMOGA.ParetoCRsFromBPMA.chromoForP2[0].sortedPopulationByP1.chromo[loopCounter].coverage));
                    //System.out.println(rule);//for testing
                    //System.out.println("____________________________");//for testing
                    rules[number-1]=rules[number-1].concat(String.valueOf(ruleCounter)).concat(":").concat(rule).concat("\n");
                    ruleCounter++;
                }//end of if                
            }//end of for
            //System.out.println(rules[number]);//for testing
            //System.out.println("____________________________");//for testing
        }//end of for
        float averageConfidenceFor10FoldForTrainingData=(float)0.0;
        float averageCoverageFor10FoldForTrainingData=(float)0.0;
        float averageCSRsFor10FoldForTrainingData=(float)0.0;
        float averageConfidenceFor10FoldForTestData=(float)0.0;
        float averageCoverageFor10FoldForTestData=(float)0.0;
        float averageCSRsFor10FoldForTestData=(float)0.0;
        int counter=0;
        resultForTrainingData[counter]="Confidence";
        resultForTrainingData[counter+12]="Coverage";
        resultForTrainingData[counter+24]="No of CSRs";
        resultForTestData[counter]="Confidence";
        resultForTestData[counter+12]="Coverage";
        resultForTestData[counter+24]="No of CSRs";
        counter++;
        for(int number=0;number<=9;number++)
        {
            averageConfidenceFor10FoldForTrainingData=averageConfidenceFor10FoldForTrainingData+confidenceForTrainingData[number];
            resultForTrainingData[counter]=/*"Fold  "+number+":  ".concat(*/String.valueOf(confidenceForTrainingData[number]*100)/*)*/;
            //System.out.println("resultForTrainingData["+counter+"]="+resultForTrainingData[counter]);//for testing
            averageCoverageFor10FoldForTrainingData=averageCoverageFor10FoldForTrainingData+coverageForTrainingData[number];
            resultForTrainingData[counter+12]=/*"Fold  "+number+":  ".concat(*/String.valueOf(coverageForTrainingData[number]*100)/*)*/;
            //System.out.println(result[counter+number+10]);//for testing
            averageCSRsFor10FoldForTrainingData=averageCSRsFor10FoldForTrainingData+noOfCSRsForTrainingData[number];
            resultForTrainingData[counter+24]=/*"Fold  "+number+":  ".concat(*/String.valueOf(noOfCSRsForTrainingData[number])/*)*/;
            //System.out.println(result[counter+number+20]);//for testing

            averageConfidenceFor10FoldForTestData=averageConfidenceFor10FoldForTestData+confidenceForTestData[number];
            resultForTestData[counter]=/*"Fold  "+number+":  ".concat(*/String.valueOf(confidenceForTestData[number]*100)/*)*/;
            //System.out.println(result[counter+number]);//for testing
            averageCoverageFor10FoldForTestData=averageCoverageFor10FoldForTestData+coverageForTestData[number];
            resultForTestData[counter+12]=/*"Fold  "+number+":  ".concat(*/String.valueOf(coverageForTestData[number]*100)/*)*/;
            //System.out.println(result[counter+number+10]);//for testing
            averageCSRsFor10FoldForTestData=averageCSRsFor10FoldForTestData+noOfCSRsForTestData[number];
            resultForTestData[counter+24]=/*"Fold  "+number+":  ".concat(*/String.valueOf(noOfCSRsForTestData[number])/*)*/;
            //System.out.println(result[counter+number+20]);//for testing
            counter++;
        }//end of for
        /*for(int number=0;number<=32;number++)
        {
            System.out.println(number+"      "+result[number]);//for testing
        }//end of for*/
        averageConfidenceFor10FoldForTrainingData=(float)averageConfidenceFor10FoldForTrainingData/10;
        averageCoverageFor10FoldForTrainingData=(float)averageCoverageFor10FoldForTrainingData/10;
        averageCSRsFor10FoldForTrainingData=(float)averageCSRsFor10FoldForTrainingData/10;
        resultForTrainingData[11]="Average Confidence:  ".concat(String.valueOf(averageConfidenceFor10FoldForTrainingData));
        resultForTrainingData[23]="Average Coverage:  ".concat(String.valueOf(averageCoverageFor10FoldForTrainingData));
        resultForTrainingData[35]="Average No of CSRs:  ".concat(String.valueOf(averageCSRsFor10FoldForTrainingData));
        averageConfidenceFor10FoldForTestData=(float)averageConfidenceFor10FoldForTestData/10;
        averageCoverageFor10FoldForTestData=(float)averageCoverageFor10FoldForTestData/10;
        averageCSRsFor10FoldForTestData=(float)averageCSRsFor10FoldForTestData/10;
        resultForTestData[11]="Average Confidence:  ".concat(String.valueOf(averageConfidenceFor10FoldForTestData));
        resultForTestData[23]="Average Coverage:  ".concat(String.valueOf(averageCoverageFor10FoldForTestData));
        resultForTestData[35]="Average No of CSRs:  ".concat(String.valueOf(averageCSRsFor10FoldForTestData));
        System.out.println("averageConfidenceFor10FoldForTrainingData="+averageConfidenceFor10FoldForTrainingData*100);//for testing
        System.out.println("averageCoverageFor10FoldForTrainingData="+averageCoverageFor10FoldForTrainingData*100);//for testing
        System.out.println("averageCSRsFor10FoldForTrainingData="+averageCSRsFor10FoldForTrainingData);//for testing
        System.out.println("averageConfidenceFor10FoldForTestData="+averageConfidenceFor10FoldForTestData*100);//for testing
        System.out.println("averageCoverageFor10FoldForTestData="+averageCoverageFor10FoldForTestData*100);//for testing
        System.out.println("averageCSRsFor10FoldForTestData="+averageCSRsFor10FoldForTestData);//for testing
        /*String pathResultForTrainingData="C:/Users/UTI/My Documents/NetBeansProjects/BPMOGA_Classification/src/"+dataset+"/OUTPUT_FILES/ResultForTrainingData.data";
        String pathResultForTestData="C:/Users/UTI/My Documents/NetBeansProjects/BPMOGA_Classification/src/"+dataset+"/OUTPUT_FILES/ResultForTestData.data";
        String pathRules="C:/Users/UTI/My Documents/NetBeansProjects/BPMOGA_Classification/src/"+dataset+"/OUTPUT_FILES/Rules.data";*/
        String pathResultForTrainingData="C:/Users/UTI/My Documents/NetBeansProjects/BPMOGA_Classification/src/"+dataset+"/"+filename+"/OUTPUT_FILES/ResultForTrainingData.data";
        String pathResultForTestData="C:/Users/UTI/My Documents/NetBeansProjects/BPMOGA_Classification/src/"+dataset+"/"+filename+"/OUTPUT_FILES/ResultForTestData.data";
        String pathRules="C:/Users/UTI/My Documents/NetBeansProjects/BPMOGA_Classification/src/"+dataset+"/"+filename+"/OUTPUT_FILES/Rules.data";
        generateResultFile GRFForTrainingData=new generateResultFile();
        generateResultFile GRFForTestData=new generateResultFile();
        generateResultFile GRFForRules=new generateResultFile();
        //result[0]=String.valueOf(averageConfidenceFor10Fold*100).concat("    ").concat(String.valueOf(averageCoverageFor10Fold*100)).concat("    ").concat(String.valueOf(averageCSRsFor10Fold));
        //result[1]=String.valueOf(averageCoverageFor10Fold*100);
        //result[2]=String.valueOf(averageCSRsFor10Fold);
        GRFForTrainingData.generateResultFile(resultForTrainingData, pathResultForTrainingData);
        GRFForTestData.generateResultFile(resultForTestData, pathResultForTestData);
        GRFForRules.generateResultFile(rules, pathRules);
    }//end of main()
}//end of Repeat