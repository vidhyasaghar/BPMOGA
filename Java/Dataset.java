/***************************************************************************************
* Author
* Dipankar Dutta
* This class will read data from file and construct Dataset Object for further processing
***************************************************************************************/
public class Dataset
{
    int noOfRecords;
    int noOfAttributes;
    String[][] dataSet;
    String attributeInformation[][];
    String[] minValues;
    String[] maxValues;
    String[][] attributeValues;
    int noOfClasses;
    String[] classLevels;
    int noOfDifferentAttributeValues[];
    String[][] normalizedDataSet;
    String[][] normalizedAttributeValues;
    String valuesOfAttributes[];
    String changePoints[][];
    double informationGain[];
    String dominentClassLabel;
    /***************************************************************************************
    * Constructor
    * It will initialize instance variables of this class
    ***************************************************************************************/
    Dataset(String dataFileName,String dataFileDlimiter,String attributeFileName,String attributeFileDlimiter,String type)
    {
        /***************************************************************************************
        * To find number of records in Dataset
        ***************************************************************************************/
        findNoOfRecords(dataFileName);
        /***************************************************************************************
        * To show number of records in Dataset
        ***************************************************************************************/
        //showNoOfRecords();//for testing
        //System.out.println("_________________________________________________________________");//for testing
        /***************************************************************************************
        * To find number of attributes in Dataset
        ***************************************************************************************/
        findNoOfAttributes(dataFileName,dataFileDlimiter);
        /***************************************************************************************
        * To show number of Attributes in Dataset
        ***************************************************************************************/
        //showNoOfAttributes();//for testing
        //System.out.println("_________________________________________________________________");//for testing
         /***************************************************************************************
        * To insert records in two dimentional array
        ***************************************************************************************/
        insertRecordsInTwoDimentionalArray(dataFileName,dataFileDlimiter);
        if(type.equals("training"))
        {
            /***************************************************************************************
            * to show dataset
            ***************************************************************************************/
            //showDataset();//for testing
            //System.out.println("_________________________________________________________________");//for testing
            /***************************************************************************************
            * To insert attribute informations in two dimentional array
            ***************************************************************************************/
            insertAttributeInformationsInTwoDimentionalArray(attributeFileName,attributeFileDlimiter);
            /***************************************************************************************
            * to show attribute informations
            ***************************************************************************************/
            //showAttributeInformation();//for testing
            //System.out.println("_________________________________________________________________");//for testing
            /***************************************************************************************
            * to find mimimum values of continuous attributes
            ***************************************************************************************/
            findMinValues();
            //showMinValues();//for testing
            //System.out.println("_________________________________________________________________");//for testing
            /***************************************************************************************
            * to find maximum values of continuous attributes
            ***************************************************************************************/
            findMaxValues();
            //showMaxValues();//for testing
            //System.out.println("_________________________________________________________________");//for testing
             /***************************************************************************************
            * to store sorted unique attribute values
            * in attributeValues
            ***************************************************************************************/
            storeSortedUniqueAttributeValues();
            //showSorted();//for testing
            //System.out.println("_________________________________________________________________");//for testing
            /***************************************************************************************
            * to store number of unique attribute values
            ***************************************************************************************/
            findNoOfDifferentAttributeValues();
            //showNoOfDifferentAttributeValues();//for testing
            //System.out.println("_________________________________________________________________");//for testing
            /***************************************************************************************
            * to find number of classes
            ***************************************************************************************/
            getNumberOfClasses();
            //showNoOfClasses();//for testing
            //System.out.println("_________________________________________________________________");//for testing
            /***************************************************************************************
            * to find Class Levels
            ***************************************************************************************/
            findClassLevels();//for testing
            //showClassLevels();//for testing
            //System.out.println("_________________________________________________________________");//for testing
            /***************************************************************************************
            * to find Class Levels
            ***************************************************************************************/
            findDominantClassLevel();//for testing
            //showDominantClassLevel();//for testing
            //System.out.println("_________________________________________________________________");//for testing
            /***************************************************************************************
            * to covert attribute values in the range 0 to 1
            ***************************************************************************************/
            //normalize();
            //showNormalized();//for testing
            //System.out.println("_________________________________________________________________");//for testing
            /***************************************************************************************
            * to store sorted unique normalized attribute values
            * in normalizedAttributeValues
            ***************************************************************************************/
            //storeNormalizedAttributeValues();
            /***************************************************************************************
            * to insert all normalized values of each attributes in a string, separated by ,
            ***************************************************************************************/
            //findValuesOfAttributes();
            //showValuesOfAttributes();//for testing
            //System.out.println("_________________________________________________________________");//for testing
            /***************************************************************************************
            * find change points
            ***************************************************************************************/
            changePoints=findChangePoints();
            //showChangePoints();//for testing
            //System.out.println("_________________________________________________________________");//for testing
            /***************************************************************************************
            * calculate entropy
            ***************************************************************************************/
            informationGain=calculateInformationGain();
            //showInformationGain();
            //System.out.println("_________________________________________________________________");//for testing
        }//end of if
    }//end of method
    /***************************************************************************************
    * findNoOfRecords method
    * To find No Of Records in dataset
    ***************************************************************************************/
    public void findNoOfRecords(String dataFileName)
    {
        FileUtil dataFileOriginal=new FileUtil(dataFileName);
        dataFileOriginal.readFile();
        noOfRecords=dataFileOriginal.noOfLines()-1;
    }//end of method
    /***************************************************************************************
    * showNoOfRecords method
    * To show No Of Records in dataset
    ***************************************************************************************/
    public void showNoOfRecords()
    {
        System.out.println("noOfRecords="+noOfRecords);//for testing
    }//end of method
    /***************************************************************************************
    * findNoOfAttributes method
    * To find No Of atributes in dataset
    ***************************************************************************************/
    public void findNoOfAttributes(String dataFileName,String dataFileDlimiter)
    {
        FileUtil dataFileOriginal=new FileUtil(dataFileName);
        dataFileOriginal.readFile();
        dataFileOriginal.readFile();
        String firstLine=dataFileOriginal.readLine(1,noOfRecords);
        IOUtil FL=new IOUtil(firstLine);
        FL.init_delim(dataFileDlimiter);
        noOfAttributes=0;
        while(FL.getNext()!=null)
        {
            noOfAttributes++;
        }//end of while
    }//end of method
     /***************************************************************************************
    * showNoOfAttributes method
    * To show No Of Attributes in dataset
    ***************************************************************************************/
    public void showNoOfAttributes()
    {
        System.out.println("noOfAttributes="+noOfAttributes);//for testing
    }//end of method
    /***************************************************************************************
    * insertRecordsInTwoDimentionalArray method
    * To insert records in two dimentional array
    ***************************************************************************************/
    public void insertRecordsInTwoDimentionalArray(String dataFileName,String dataFileDlimiter)
    {
        FileUtil dataFileOriginal=new FileUtil(dataFileName);
        dataSet=new String[noOfRecords][noOfAttributes];
        for(int outerLoopCounter=0;outerLoopCounter<=noOfRecords-1;outerLoopCounter++)
        {
            String record=dataFileOriginal.readLine(outerLoopCounter+1,noOfRecords);
            IOUtil IO=new IOUtil(record);
            IO.init_delim(dataFileDlimiter);
            for(int innerLoopCounter=0;innerLoopCounter<=noOfAttributes-1;innerLoopCounter++)
            {
                dataSet[outerLoopCounter][innerLoopCounter]=IO.getNext();
            }//end of innerloop for
        }//end of outerloop for
    }//end of method
    /***************************************************************************************
    * showDataset method
    * To show dataset after reading it from file
    ***************************************************************************************/
    public void showDataset()
    {
        for(int outerLoopCounter=0;outerLoopCounter<=noOfRecords-1;outerLoopCounter++)
        {
            for(int innerLoopCounter=0;innerLoopCounter<=noOfAttributes-1;innerLoopCounter++)
            {
                System.out.print(dataSet[outerLoopCounter][innerLoopCounter]+" ");
            }//end of for
            System.out.println("");
        }//end of for
    }//end of method
    /***************************************************************************************
    * insertAttributeInformationsInTwoDimentionalArray method
    * To insert attribute informations in two dimentional array
    ***************************************************************************************/
    public void insertAttributeInformationsInTwoDimentionalArray(String attributeFileName,String attributeFileDlimiter)
    {
        FileUtil attributeFileOriginal=new FileUtil(attributeFileName);
        attributeFileOriginal.readFile();
        int noOfLines=attributeFileOriginal.noOfLines()-1;
        attributeFileOriginal.readFile();
        String attributeFirstLine=attributeFileOriginal.readLine(1,noOfLines);
        IOUtil FL2=new IOUtil(attributeFirstLine);
        FL2.init_delim(attributeFileDlimiter);
        /***************************************************************************************
        * To insert attribute informations in two dimentional array
        ***************************************************************************************/
        attributeInformation=new String[2][noOfAttributes];
        for(int outerLoopCounter=0;outerLoopCounter<=1;outerLoopCounter++)
        {
            String record=attributeFileOriginal.readLine(outerLoopCounter+1,noOfLines);
            IOUtil IO=new IOUtil(record);
            IO.init_delim(attributeFileDlimiter);
            for(int innerLoopCounter=0;innerLoopCounter<=noOfAttributes-1;innerLoopCounter++)
            {
                attributeInformation[outerLoopCounter][innerLoopCounter]=IO.getNext();
            }//end of innerloop for
        }//end of outerloop for
    }//end of method
     /***************************************************************************************
    * showAttributeInformation method
    * To show Attribute Information after reading it from file
    ***************************************************************************************/
    public void showAttributeInformation()
    {
        for(int outerLoopCounter=0;outerLoopCounter<=2-1;outerLoopCounter++)
        {
            for(int innerLoopCounter=0;innerLoopCounter<=noOfAttributes-1;innerLoopCounter++)
            {
                System.out.print(attributeInformation[outerLoopCounter][innerLoopCounter]+" ");
            }//end of for
            System.out.println("");
        }//end of for
    }//end of method
    /***************************************************************************************
    * findMinValues method
    *
    ***************************************************************************************/
    public void findMinValues()
    {
        minValues=new String[noOfAttributes];
        for(int loopCounter=0;loopCounter<=noOfAttributes-1;loopCounter++)
        {            
            if(attributeInformation[1][loopCounter].equals("Float")||attributeInformation[1][loopCounter].equals("Integer"))
            {
                int counter=-1;
                do
                {
                    counter++;                    
                    if(!dataSet[counter][loopCounter].equals("?"))
                        break;
                }while(counter<(noOfRecords-1));
                minValues[loopCounter]=dataSet[counter][loopCounter];
                for(int loopCounter1=0;loopCounter1<=noOfRecords-1;loopCounter1++)
                {      
                    if(!dataSet[loopCounter1][loopCounter].equals("?"))
                    {
                        if(Double.parseDouble(minValues[loopCounter])>Double.parseDouble(dataSet[loopCounter1][loopCounter]))
                        {
                            minValues[loopCounter]=dataSet[loopCounter1][loopCounter];
                        }//end of if
                    }//end of if
                }//end of for
            }//end of if
        }//end of for
    }//end of method
   /***************************************************************************************
    * showMinValues method
    * To show min values of attributes
    ***************************************************************************************/
    public void showMinValues()
    {
        for(int loopCounter=0;loopCounter<=noOfAttributes-1;loopCounter++)
        {
            if(attributeInformation[1][loopCounter].equals("Float")||attributeInformation[1][loopCounter].equals("Integer"))
            {
                System.out.println("minValues["+loopCounter+"]="+minValues[loopCounter]);
            }//end of if
        }//end of for
    }//end of method
    /***************************************************************************************
    * findMaxValues method
    *
    ***************************************************************************************/
    public void findMaxValues()
    {
        maxValues=new String[noOfAttributes];
        for(int loopCounter=0;loopCounter<=noOfAttributes-1;loopCounter++)
        {
            if(attributeInformation[1][loopCounter].equals("Float")||attributeInformation[1][loopCounter].equals("Integer"))
            {
                int counter=-1;
                do
                {
                    counter++;
                    if(!dataSet[counter][loopCounter].equals("?"))
                    break;
                }while(counter<(noOfRecords-1));
                maxValues[loopCounter]=dataSet[counter][loopCounter];
                for(int loopCounter1=0;loopCounter1<=noOfRecords-1;loopCounter1++)
                {
                    if(!dataSet[loopCounter1][loopCounter].equals("?"))
                    {
                        if(Double.parseDouble(maxValues[loopCounter])<Double.parseDouble(dataSet[loopCounter1][loopCounter]))
                        {
                            maxValues[loopCounter]=dataSet[loopCounter1][loopCounter];
                        }//end of if
                    }//end of if
                }//end of for
            }//end of if
        }//end of for
    }//end of method
     /***************************************************************************************
    * showMaxValues method
    * To show max values of attributes
    ***************************************************************************************/
    public void showMaxValues()
    {
        for(int loopCounter=0;loopCounter<=noOfAttributes-1;loopCounter++)
        {
            if(attributeInformation[1][loopCounter].equals("Float")||attributeInformation[1][loopCounter].equals("Integer"))
            {
                System.out.println("maxValues["+loopCounter+"]="+maxValues[loopCounter]);
            }//end of if
        }//end of for
    }//end of method
    /***************************************************************************************
    * storeSortedUniqueAttributeValues method
    * To store sorted unique attribute values
    ***************************************************************************************/
    public void storeSortedUniqueAttributeValues()
    {
        attributeValues=new String[noOfAttributes][noOfRecords+1];
        String Temp[]=new String[noOfRecords];
        boolean flag[]=new boolean[noOfRecords];
        for(int outerLoopCounter=0;outerLoopCounter<=noOfAttributes-1;outerLoopCounter++)
        {
            if(attributeInformation[1][outerLoopCounter].equals("Float")||attributeInformation[1][outerLoopCounter].equals("Integer"))
            {
                //System.out.println("attributeInformation[1]["+outerLoopCounter+"]="+attributeInformation[1][outerLoopCounter]);//for testing
                String Temp2[]=new String[noOfRecords+1];
                for(int innerLoopCounter=0;innerLoopCounter<=noOfRecords-1;innerLoopCounter++)
                {
                    Temp[innerLoopCounter]=dataSet[innerLoopCounter][outerLoopCounter];//to store attributes values
                    if(!Temp[innerLoopCounter].equals("?"))
                        flag[innerLoopCounter]=true;
                    else
                        flag[innerLoopCounter]=false;
                }//end of for
                boolean flag1=false;
                int counter=0;
                do
                {
                    flag1=false;
                    String lowestAvaliableValue=maxValues[outerLoopCounter];
                    for(int innerLoopCounter=0;innerLoopCounter<=noOfRecords-1;innerLoopCounter++)
                    {
                         if(flag[innerLoopCounter])
                         {                             
                             if(Double.parseDouble(lowestAvaliableValue)>Double.parseDouble(Temp[innerLoopCounter]))
                             {
                                 lowestAvaliableValue=Temp[innerLoopCounter];
                                 flag1=true;
                             }//end of if                             
                         }//end of if
                    }//end of for
                    Temp2[counter]=lowestAvaliableValue;
                    //System.out.println("Temp2["+counter+"]="+Temp2[counter]);//for testing
                    counter++;
                    for(int innerLoopCounter=0;innerLoopCounter<=noOfRecords-1;innerLoopCounter++)
                    {
                        if(lowestAvaliableValue.equals(Temp[innerLoopCounter]))
                        {
                            flag[innerLoopCounter]=false;
                            //System.out.println("Temp["+innerLoopCounter+"]="+Temp[innerLoopCounter]);//for testing
                        }//end of if
                    }//end of for
                }while(flag1);
                attributeValues[outerLoopCounter]=Temp2;
            }//end of if
            if(attributeInformation[1][outerLoopCounter].equals("String")||attributeInformation[1][outerLoopCounter].equals("Binary"))
            {                
                String Temp2[]=new String[noOfRecords+1];
                for(int innerLoopCounter=0;innerLoopCounter<=noOfRecords-1;innerLoopCounter++)
                {
                    Temp[innerLoopCounter]=dataSet[innerLoopCounter][outerLoopCounter];//to store attributes values
                    if(!Temp[innerLoopCounter].equals("?"))
                        flag[innerLoopCounter]=true;
                    else
                        flag[innerLoopCounter]=false;
                }//end of for
                boolean flag1=false;
                int counter=0;
                do
                {
                    flag1=false;
                    int innerLoopCounter1=0;
                    String firstAvaliableValue="";
                    do
                    {
                        firstAvaliableValue=Temp[innerLoopCounter1];
                        innerLoopCounter1++;
                        if(!firstAvaliableValue.equals("?"))
                            break;
                    }while(innerLoopCounter1<=(noOfRecords-1));                    
                    for(int innerLoopCounter=0;innerLoopCounter<=noOfRecords-1;innerLoopCounter++)
                    {
                        if(flag[innerLoopCounter])
                        {
                            firstAvaliableValue=Temp[innerLoopCounter];
                            flag1=true;
                            break;
                        }//end of if
                    }//end of for
                    if(flag1)
                    {
                        Temp2[counter]=firstAvaliableValue;
                        counter++;
                        for(int innerLoopCounter=0;innerLoopCounter<=noOfRecords-1;innerLoopCounter++)
                        {
                            if(firstAvaliableValue.equals(Temp[innerLoopCounter]))
                            {
                                flag[innerLoopCounter]=false;
                            }//end of if
                        }//end of for
                    }//end of if
                }while(flag1);
                attributeValues[outerLoopCounter]=Temp2;                
            }//end of if            
        }//end of for
    }//end of method
    /***************************************************************************************
    * findNoOfDifferentAttributeValues method
    * To find no Of Different Attribute Values
    ***************************************************************************************/
    public void findNoOfDifferentAttributeValues()
    {
        noOfDifferentAttributeValues=new int[noOfAttributes];
        for(int outerLoopCounter=0;outerLoopCounter<=noOfAttributes-1;outerLoopCounter++)
        {
            int counter=0;
            for(int innerLoopCounter=0;innerLoopCounter<=noOfRecords;innerLoopCounter++)
            {
                //System.out.println("attributeValues["+outerLoopCounter+"]["+innerLoopCounter+"]="+attributeValues[outerLoopCounter][innerLoopCounter]);
                if(attributeValues[outerLoopCounter][innerLoopCounter]==null)
                {
                    counter=innerLoopCounter;
                    break;
                }//end of if
            }//end of for
            noOfDifferentAttributeValues[outerLoopCounter]=counter;
            //System.out.println("noOfDifferentAttributeValues["+outerLoopCounter+"]="+noOfDifferentAttributeValues[outerLoopCounter]);
        }//end of for
    }//end of method
    /***************************************************************************************
    * showNoOfDifferentAttributeValues method
    * To show No Of Different Attribute Values
    ***************************************************************************************/
    public void showNoOfDifferentAttributeValues()
    {
        for(int counter1=0;counter1<=noOfAttributes-1;counter1++)
        {
            System.out.println("Number of different values for "+counter1+"th Attribute="+noOfDifferentAttributeValues[counter1]);
        }//end of for
    }//end of method
    /***************************************************************************************
    * showSorted method
    * To Show Sorted attributes values of Dataset
    ***************************************************************************************/
    public void showSorted()
    {
        System.out.println("Sorted Attributes Values");
        for(int counter1=0;counter1<=noOfAttributes-1;counter1++)
        {
            System.out.println("Attribute Number="+counter1);
            for(int counter2=0;counter2<=noOfRecords-1;counter2++)
            {
                 if(attributeValues[counter1][counter2]==null)
                    break;
                 else
                    System.out.print(attributeValues[counter1][counter2]);
                 System.out.print(",");
            }//end of for
            System.out.println("");
        }//end of for
    }//end of method
    /***************************************************************************************
    * getNumberOfClasses method
    *
    ***************************************************************************************/
    public void getNumberOfClasses()
    {
        noOfClasses=0;
        for(int counter=0;counter<=noOfRecords-1;counter++)
        {
            //System.out.println("attributeValues[noOfAttributes-1][counter]"+attributeValues[noOfAttributes-1][counter]);
            if(attributeValues[noOfAttributes-1][counter]!=null)
                noOfClasses++;
            else
                break;
        }//end of for
    }//end of method
    /***************************************************************************************
    * showNoOfClasses method
    *
    ***************************************************************************************/
    public void showNoOfClasses()
    {
        System.out.println("No of Classes="+noOfClasses);
    }//end of method
    /***************************************************************************************
    * findClassLevels method
    *
    ***************************************************************************************/
    public void findClassLevels()
    {
        classLevels=new String[noOfClasses];
        for(int counter=0;counter<=noOfClasses-1;counter++)
        {
            classLevels[counter]=attributeValues[noOfAttributes-1][counter];
        }//end of for
    }//end of method
    /***************************************************************************************
    * showClassLevels method
    *
    ***************************************************************************************/
    public void showClassLevels()
    {
        System.out.println("Class Levels");
        for(int i=0;i<noOfClasses;i++)
            System.out.println(classLevels[i]);
    }//end of method
    /***************************************************************************************
    * findDominantClassLevel method
    *
    ***************************************************************************************/
    public void findDominantClassLevel()
    {
        int classCounter[]=new int[noOfClasses];
        for(int recordNo=0;recordNo<=noOfRecords-1;recordNo++)
        {
            for(int classNo=0;classNo<=noOfClasses-1;classNo++)
            {
                if(dataSet[recordNo][noOfAttributes-1].equals(classLevels[classNo]))
                {
                    classCounter[classNo]++;
                    break;
                }//end of if
            }//end of for
        }//end of for
        int maxClassCounter=0;
        for(int classNo=0;classNo<=noOfClasses-1;classNo++)
        {
            if(classCounter[classNo]>maxClassCounter)
            {
                maxClassCounter=classCounter[classNo];
                dominentClassLabel=classLevels[classNo];
            }//end of if
        }//end of for
    }//end of method
    /***************************************************************************************
    * showDominantClassLevel method
    *
    ***************************************************************************************/
    public void showDominantClassLevel()
    {
        System.out.println("Dominant Class Level="+dominentClassLabel);
    }//end of method
    /***************************************************************************************
    * normalize method
    *
    ***************************************************************************************/
    public void normalize()
    {
        normalizedAttributeValues=new String[noOfAttributes-1][noOfRecords];
        normalizedDataSet=new String[noOfRecords][noOfAttributes];
        for(int outerLoopCounter=0;outerLoopCounter<=noOfAttributes-1;outerLoopCounter++)
        {
            for(int innerLoopCounter=0;innerLoopCounter<=noOfRecords-1;innerLoopCounter++)
            {
                if(attributeInformation[1][outerLoopCounter].equals("Float")||attributeInformation[1][outerLoopCounter].equals("Integer"))
                {
                    normalizedDataSet[innerLoopCounter][outerLoopCounter]=String.valueOf((Double.valueOf(Double.valueOf(dataSet[innerLoopCounter][outerLoopCounter]))-Double.valueOf(minValues[outerLoopCounter]))/(Double.valueOf(maxValues[outerLoopCounter])-Double.valueOf(minValues[outerLoopCounter])));
                }//end of if
                if(attributeInformation[1][outerLoopCounter].equals("String")||attributeInformation[1][outerLoopCounter].equals("Binary"))
                {
                    normalizedDataSet[innerLoopCounter][outerLoopCounter]=dataSet[innerLoopCounter][outerLoopCounter];
                }//end of if
            }//end of for
        }//end of for
     }//end of method
    /***************************************************************************************
    * showNormalized method
    * This method is for showing normalized dataSet and normalized attribute values
    ***************************************************************************************/
    public void showNormalized()
    {
        for(int outerLoopCounter=0;outerLoopCounter<=noOfRecords-1;outerLoopCounter++)
        {
            for(int innerLoopCounter=0;innerLoopCounter<=noOfAttributes-1;innerLoopCounter++)
            {
                 System.out.print(normalizedDataSet[outerLoopCounter][innerLoopCounter]);
                 System.out.print("\t");
            }//end of for
            System.out.println("");
        }//end of for
    }//end of method
    /***************************************************************************************
    * storeNormalizedAttributeValues method
    *
    ***************************************************************************************/
    public void storeNormalizedAttributeValues()
    {
        normalizedAttributeValues=new String[noOfAttributes][noOfRecords];
        for(int outerLoopCounter=0;outerLoopCounter<=noOfAttributes-1;outerLoopCounter++)
        {
            if(attributeInformation[1][outerLoopCounter].equals("Float")||attributeInformation[1][outerLoopCounter].equals("Integer"))
            {
                for(int innerLoopCounter=0;innerLoopCounter<=noOfRecords-1;innerLoopCounter++)
                {
                    if(attributeValues[outerLoopCounter][innerLoopCounter]==null)
                        break;
                    else
                        normalizedAttributeValues[outerLoopCounter][innerLoopCounter]=String.valueOf((Double.valueOf(attributeValues[outerLoopCounter][innerLoopCounter])-Double.valueOf(minValues[outerLoopCounter]))/(Double.valueOf(maxValues[outerLoopCounter])-Double.valueOf(minValues[outerLoopCounter])));
                }//end of for
            }//end of if
            if(attributeInformation[1][outerLoopCounter].equals("String")||attributeInformation[1][outerLoopCounter].equals("Binary"))
            {
                for(int innerLoopCounter=0;innerLoopCounter<=noOfRecords-1;innerLoopCounter++)
                {
                    if(attributeValues[outerLoopCounter][innerLoopCounter]==null)
                        break;
                    else
                        normalizedAttributeValues[outerLoopCounter][innerLoopCounter]=attributeValues[outerLoopCounter][innerLoopCounter];
                }//end of for
            }//end of if
        }//end of for
    }//end of method
    /***************************************************************************************
    * findValuesOfAttributes method
    *
    ***************************************************************************************/
    public void findValuesOfAttributes()
    {
        valuesOfAttributes=new String[noOfAttributes];
        for(int counter1=0;counter1<=noOfAttributes-1;counter1++)
        {
            valuesOfAttributes[counter1]="";
            for(int counter2=0;counter2<=noOfRecords-1;counter2++)
            {
                 if(attributeValues[counter1][counter2]==null)
                    break;
                 else
                 {
                    valuesOfAttributes[counter1]=valuesOfAttributes[counter1].concat(attributeValues[counter1][counter2]);
                    valuesOfAttributes[counter1]=valuesOfAttributes[counter1].concat(",");
                 }//end of else
            }//end of for
        }//end of for
    }//end of method
    /***************************************************************************************
    * showValuesOfAttributes method
    *
    ***************************************************************************************/
    public void showValuesOfAttributes()
    {
        for(int loopCounter=0;loopCounter<=noOfAttributes-1;loopCounter++)
        {
            System.out.println("Attribute["+loopCounter+"]="+valuesOfAttributes[loopCounter]);//for testing
        }//end of for
    }//end of method
     /***************************************************************************************
    * findChangePoints method
    *
    ***************************************************************************************/
    public String[][] findChangePoints()
    {
        String changePoints[][]=new String[noOfAttributes][];
        for(int outerloopCounter=0;outerloopCounter<=noOfAttributes-2;outerloopCounter++)
        {
            if(attributeInformation[1][outerloopCounter].equals("Float")||attributeInformation[1][outerloopCounter].equals("Integer"))
            {
                String sortedAttributeAndClass[][]=sortRecordsAsPerAttributeValue(outerloopCounter,noOfAttributes,dataSet,attributeValues[outerloopCounter]);
                int noOfChangePoints=findNoOfChangePoints(sortedAttributeAndClass);
                //System.out.println("noOfChangePoints"+noOfChangePoints);//for testing
                changePoints[outerloopCounter]=findArrayOfChangePoints(sortedAttributeAndClass,noOfChangePoints);                
            }//end of if
        }//end of for
        return changePoints;
    }//end of findChangePoints()
    /***************************************************************************************
    * showChangePoints method
    *
    ***************************************************************************************/
    public void showChangePoints()
    {
        System.out.println("Change Points");//for testing
        for(int outerloopCounter=0;outerloopCounter<=noOfAttributes-2;outerloopCounter++)
        {
            if(attributeInformation[1][outerloopCounter].equals("Float")||attributeInformation[1][outerloopCounter].equals("Integer"))
            {
                for(int innerloopCounter=0;innerloopCounter<=changePoints[outerloopCounter].length-1;innerloopCounter++)
                {
                    System.out.print(changePoints[outerloopCounter][innerloopCounter]);//for testing
                    System.out.print(",");//for testing
                }//end of for
                System.out.println();//for testing
            }//end of if
        }//end of for
    }//end of showChangePoints()
    /***************************************************************************************
    * sortRecordsAsPerAttributeValue method
     ***************************************************************************************/
    String [][] sortRecordsAsPerAttributeValue(int attributeNo,int numberOfAttribute,String dataSet[][],String attributeValues[])
    {
        String sortedAttributeAndClass[][]=new String[2][dataSet.length];
        boolean flag[]=new boolean[dataSet.length];
        int counter=0;
        for(int outerloopCounter=0;outerloopCounter<=attributeValues.length-1;outerloopCounter++)
        {
            for(int innerloopCounter=0;innerloopCounter<=dataSet.length-1;innerloopCounter++)
            {
                if(dataSet[innerloopCounter][attributeNo].equals(attributeValues[outerloopCounter])&&!flag[innerloopCounter])
                {
                    sortedAttributeAndClass[0][counter]=dataSet[innerloopCounter][attributeNo];
                    sortedAttributeAndClass[1][counter]=dataSet[innerloopCounter][numberOfAttribute-1];
                    flag[innerloopCounter]=true;
                    counter++;
                }//end of if
            }//end of for
        }//end of for
        //to deal with missing attribute value
        counter=0;
        boolean flag2=false;
        for(int loopCounter=0;loopCounter<=dataSet.length-1;loopCounter++)
        {
            //System.out.println(sortedAttributeAndClass[0][loopCounter]+","+sortedAttributeAndClass[1][loopCounter]);//for testing
            counter++;
            if(sortedAttributeAndClass[0][loopCounter]==null)
            {
                flag2=true;
                break;
            }//end of if
        }//end of for
        String sortedAttributeAndClass2[][];
        if(flag2)
        {
            //if last one having null
            sortedAttributeAndClass2=new String[2][counter-1];
        }//end of for
        else
        {
            //if all having values
            sortedAttributeAndClass2=new String[2][counter];
        }//end of for
        for(int loopCounter=0;loopCounter<=sortedAttributeAndClass2[0].length-1;loopCounter++)
        {
            sortedAttributeAndClass2[0][loopCounter]=sortedAttributeAndClass[0][loopCounter];
            sortedAttributeAndClass2[1][loopCounter]=sortedAttributeAndClass[1][loopCounter];
        }//end of for
        //System.out.println("sortedAttributeAndClass2[0].length="+sortedAttributeAndClass2[0].length);
        return sortedAttributeAndClass2;
    }//end of sortRecordsAsPerAttributeValue
    /***************************************************************************************
    * findNoOfChangePoints method
     ***************************************************************************************/
    int findNoOfChangePoints(String sortedAttributeAndClass[][])
    {        
        int noOfChangePoints=0;
        String classLabel_1=sortedAttributeAndClass[1][0];
        String attributevalue_1=sortedAttributeAndClass[0][0];
        for(int loopCounter=1;loopCounter<=sortedAttributeAndClass[0].length-1;loopCounter++)
        {
            //System.out.println(sortedAttributeAndClass[0][loopCounter]+","+sortedAttributeAndClass[1][loopCounter]);//for testing
            String classLabel_2=sortedAttributeAndClass[1][loopCounter];
            String attributevalue_2=sortedAttributeAndClass[0][loopCounter];
            if(!classLabel_1.equals(classLabel_2))
            {
                if(!attributevalue_1.equals(attributevalue_2))
                {
                    noOfChangePoints=noOfChangePoints+1;
                    //System.out.println("1-"+attributevalue_1+","+classLabel_1);//for testing
                    //System.out.println("2-"+attributevalue_2+","+classLabel_2);//for testing
                    //System.out.println("**"+attributevalue_1+","+classLabel_1);//for testing
                }//end of if                
            }//end of if
            attributevalue_1=sortedAttributeAndClass[0][loopCounter];
            classLabel_1=sortedAttributeAndClass[1][loopCounter];
            //System.out.println("*"+attributevalue_1+","+classLabel_1);//for testing
        }//end of for
        /*if(attributevalue_1.equals(sortedAttributeAndClass[0][sortedAttributeAndClass[0].length-1]))
        {
            noOfChangePoints=noOfChangePoints+1;//to include last and fast points
        }//end of if
        else
        {*/
            noOfChangePoints=noOfChangePoints+2;//to include last and fast points
        //}//end of else
        //System.out.println("noOfChangePoints="+noOfChangePoints);//for testing
        return noOfChangePoints;
    }//end of findNoOfChangePoints
    /***************************************************************************************
    * findArrayOfChangePoints method
     ***************************************************************************************/
    String[] findArrayOfChangePoints(String sortedAttributeAndClass[][],int noOfChangePoints)
    {
        String arrayOfChangePointsWithDuplicateValues[]=new String[noOfChangePoints];
        arrayOfChangePointsWithDuplicateValues[0]=sortedAttributeAndClass[0][0];
        int counter=1;
        String attributevalue_1=sortedAttributeAndClass[0][0];
        String classLabel_1=sortedAttributeAndClass[1][0];
        for(int loopCounter=1;loopCounter<=sortedAttributeAndClass[0].length-1;loopCounter++)
        {
            String classLabel_2=sortedAttributeAndClass[1][loopCounter];
            String attributevalue_2=sortedAttributeAndClass[0][loopCounter];
            if(!classLabel_1.equals(classLabel_2))
            {
                if(!attributevalue_1.equals(attributevalue_2))
                {  
                    double a=Double.valueOf(attributevalue_1);
                    double b=Double.valueOf(attributevalue_2);
                    double c=((a+b)/2.0);
                    arrayOfChangePointsWithDuplicateValues[counter]=Double.toString(c);
                    //arrayOfChangePointsWithDuplicateValues[counter]=Double.toString(Double.valueOf(attributevalue_1)+Double.valueOf(attributevalue_2)/2.0);
                    //System.out.println(arrayOfChangePointsWithDuplicateValues[counter]);//for testing
                    counter++;
                }//end of if
            }//end of if
            attributevalue_1=sortedAttributeAndClass[0][loopCounter];
            classLabel_1=sortedAttributeAndClass[1][loopCounter];
        }//end of for
        arrayOfChangePointsWithDuplicateValues[counter]=sortedAttributeAndClass[0][sortedAttributeAndClass[0].length-1];
        /*if(!attributevalue.equals(sortedAttributeAndClass[0][sortedAttributeAndClass[0].length-1]))
        {
            arrayOfChangePoints[counter]=sortedAttributeAndClass[0][sortedAttributeAndClass[0].length-1];
        }//end of if*/
        /*for(int loopCounter=0;loopCounter<=arrayOfChangePointsWithDuplicateValues.length-1;loopCounter++)
        {
            System.out.println(arrayOfChangePointsWithDuplicateValues[loopCounter]);//for testing
        }//end of for*/
        //to eliminate duplicate values
        noOfChangePoints=1;
        String point=arrayOfChangePointsWithDuplicateValues[0];
        for(int loopCounter=1;loopCounter<=arrayOfChangePointsWithDuplicateValues.length-1;loopCounter++)
        {
            if(!point.equals(arrayOfChangePointsWithDuplicateValues[loopCounter]))
            {
                noOfChangePoints++;
                point=arrayOfChangePointsWithDuplicateValues[loopCounter];
            }//end of if
        }//end of for        
        //System.out.println("noOfChangePoints="+noOfChangePoints);//for testing
        String arrayOfChangePoints[]=new String[noOfChangePoints];
        point=arrayOfChangePointsWithDuplicateValues[0];
        arrayOfChangePoints[0]=point;
        counter=1;
        for(int loopCounter=1;loopCounter<=arrayOfChangePointsWithDuplicateValues.length-1;loopCounter++)
        {
            if(!point.equals(arrayOfChangePointsWithDuplicateValues[loopCounter]))
            {
                arrayOfChangePoints[counter]=arrayOfChangePointsWithDuplicateValues[loopCounter];
                point=arrayOfChangePointsWithDuplicateValues[loopCounter];
                counter++;
            }//end of if
        }//end of for
        return arrayOfChangePoints;
    }//end of findArrayOfChangePoints
    /***************************************************************************************
    * calculateInformationGain method
     ***************************************************************************************/
    public double[] calculateInformationGain()
    {
        double entropy[]=calculateEntropy();
        double informationGain[]=new double[noOfAttributes-1];
        for(int attributeNo=0;attributeNo<=noOfAttributes-2;attributeNo++)
        {
            informationGain[attributeNo]=entropy[noOfAttributes-1]-entropy[attributeNo];
            //System.out.println("informationGain["+attributeNo+"]="+informationGain[attributeNo]);//for testing
        }//end of for
        return informationGain;
    }//end of calculateInformationGain
    /***************************************************************************************
    * calculateEntropy method
     ***************************************************************************************/
    public double[] calculateEntropy()
    {
        //System.out.println("calculateEntropy method");//for testing
        double classEntropy=calculateEntropyOfClass();
        //System.out.println("classEntropy="+classEntropy);//for testing
        double entropy[]=new double[noOfAttributes];
        entropy[noOfAttributes-1]=classEntropy;
        for(int attributeNo=0;attributeNo<=noOfAttributes-2;attributeNo++)
        {
            String attributesType=attributeInformation[1][attributeNo];
            if(attributesType.equals("Integer")||attributesType.equals("Float"))
            {
                entropy[attributeNo]=calculateEntropyOfContinuousAttribute(attributeNo);
                //System.out.println("entropy["+attributeNo+"]="+entropy[attributeNo]);//for testing
            }//end of if
            else if(attributesType.equals("Binary")||attributesType.equals("String"))
            {
                entropy[attributeNo]=calculateEntropyOfCategoricalAttribute(attributeNo);
                //System.out.println("entropy["+attributeNo+"]="+entropy[attributeNo]);//for testing
            }//end of if
        }//end of for
        return entropy;
    }//end of calculateEntropy()
    /***************************************************************************************
    * calculateEntropyOfClass method. Classentropy=-sum p*log2p
     ***************************************************************************************/
    public double calculateEntropyOfClass()
    {
        double probability_of_class[]=new double[noOfClasses];
        double classEntropy=0.0;
        for(int loopCounter=0;loopCounter<=noOfClasses-1;loopCounter++)
        {
            probability_of_class[loopCounter]=calculateProbabilityOfClass(classLevels[loopCounter]);
            classEntropy=classEntropy-probability_of_class[loopCounter]*Math.log(probability_of_class[loopCounter])/Math.log(2);
        }//end of for
        //System.out.println("classEntropy="+classEntropy);//for testing
        return classEntropy;
    }//end of calculateEntropyOfClass()
    /***************************************************************************************
    * calculateProbabilityOfClass method
     ***************************************************************************************/
    public double calculateProbabilityOfClass(String classLabel)
    {
        double classProbability=0.0;
        int classCount=0;
        for(int loopCounter=0;loopCounter<=noOfRecords-1;loopCounter++)
        {
            if(classLabel.equals(dataSet[loopCounter][noOfAttributes-1]))
                classCount++;
        }//end of for
        //System.out.println("classCount="+classCount);//for testing
        classProbability=(double)classCount/noOfRecords;
        //System.out.println("classProbability="+classProbability);//for testing
        return classProbability;
    }//end of calculateProbabilityOfClass()
    /***************************************************************************************
    * calculateEntropyOfContinuousAttribute method
     ***************************************************************************************/
    public double calculateEntropyOfContinuousAttribute(int attributeNo)
    {
        //System.out.println("attributeNo="+attributeNo);//for testing
        //System.out.println("noOfClasses="+noOfClasses);//for testing
        //System.out.println("noOfDifferentAttributeValues[attributeNo]="+noOfDifferentAttributeValues[attributeNo]);//for testing
        //System.out.println("No of Change Points="+changePoints[attributeNo].length);//for testing
        double average_interval_and_class_Entropy=0.0;
        int noOfIntervals=changePoints[attributeNo].length-1;
        for(int intervalNo=0;intervalNo<=noOfIntervals-1;intervalNo++)
        {            
            double minValue=Double.valueOf(changePoints[attributeNo][intervalNo]);            
            double maxValue=Double.valueOf(changePoints[attributeNo][intervalNo+1]);
            //System.out.println("minValue="+minValue);//for testing
            //System.out.println("maxValue="+maxValue);//for testing
            double interval_and_class_Entropy=0.0;
            /*if(intervalNo==0)
            {
                for(int classNo=0;classNo<=noOfClasses-1;classNo++)
                {
                    //System.out.println("Class="+classLevels[classNo]);//for testing
                    double probability_of_interval_and_class=calculateProbabilityOfFirstIntervalAndClass(classLevels[classNo],minValue,maxValue,attributeNo);
                    //System.out.println("probability_of_interval_and_class="+probability_of_interval_and_class);//for testing
                    if(probability_of_interval_and_class>0.0)
                        interval_and_class_Entropy=interval_and_class_Entropy-probability_of_interval_and_class*Math.log(probability_of_interval_and_class)/Math.log(2);
                }//end of for
                int interval_value_count=calculateFirstIntervalValueCount(minValue,maxValue,attributeNo);
                average_interval_and_class_Entropy=average_interval_and_class_Entropy+(double)interval_value_count/noOfRecords*interval_and_class_Entropy;
                //System.out.println("average_interval_and_class_Entropy="+average_interval_and_class_Entropy);//for testing
            }//end of if
            else
            {*/
                for(int classNo=0;classNo<=noOfClasses-1;classNo++)
                {
                    //System.out.println("Class="+classLevels[classNo]);//for testing
                    double probability_of_interval_and_class=calculateProbabilityOfIntervalAndClass(classLevels[classNo],minValue,maxValue,attributeNo);
                    //System.out.println("probability_of_interval_and_class="+probability_of_interval_and_class);//for testing
                    if(probability_of_interval_and_class>0.0)
                        interval_and_class_Entropy=interval_and_class_Entropy-probability_of_interval_and_class*Math.log(probability_of_interval_and_class)/Math.log(2);
                }//end of for
                int interval_value_count=calculateIntervalValueCount(minValue,maxValue,attributeNo);
                //System.out.println("interval_value_count="+interval_value_count);//for testing
                average_interval_and_class_Entropy=average_interval_and_class_Entropy+(double)interval_value_count/noOfRecords*interval_and_class_Entropy;
                //System.out.println("average_interval_and_class_Entropy="+average_interval_and_class_Entropy);//for testing
            //}//end of else
        }//end of for
        //System.out.println("average_interval_and_class_Entropy="+average_interval_and_class_Entropy);//for testing
        return average_interval_and_class_Entropy;
    }//end of calculateEntropyOfContinuousAttribute()
    /***************************************************************************************
    * calculateEntropyOfCategoricalAttribute method
     ***************************************************************************************/
    public double calculateEntropyOfCategoricalAttribute(int attributeNo)
    {
        //System.out.println("attributeNo="+attributeNo);//for testing
        //System.out.println("noOfClasses="+noOfClasses);//for testing
        //System.out.println("noOfDifferentAttributeValues[attributeNo]="+noOfDifferentAttributeValues[attributeNo]);//for testing
        double average_attribute_and_class_Entropy=0.0;
        for(int attributeValueNo=0;attributeValueNo<=noOfDifferentAttributeValues[attributeNo]-1;attributeValueNo++)
        {
            double attribute_and_class_Entropy=0.0;
            for(int classNo=0;classNo<=noOfClasses-1;classNo++)
            {
                double probability_of_attribute_and_class=calculateProbabilityOfAttributeAndClass(classLevels[classNo],attributeValues[attributeNo][attributeValueNo],attributeNo);
                //System.out.println("Class="+classLevels[classNo]+":+attributeValues="+attributeValues[attributeNo][attributeValueNo]);
                //System.out.println("probability_of_attribute_and_class="+probability_of_attribute_and_class);//for testing
                if(probability_of_attribute_and_class>0.0)
                    attribute_and_class_Entropy=attribute_and_class_Entropy-probability_of_attribute_and_class*Math.log(probability_of_attribute_and_class)/Math.log(2);                
            }//end of for
            //System.out.println("attribute_and_class_Entropy="+attribute_and_class_Entropy);
            int attribute_value_count=calculateAtttributeValueCount(attributeValues[attributeNo][attributeValueNo],attributeNo);
            //System.out.println("attribute_value_count="+attribute_value_count);
            average_attribute_and_class_Entropy=average_attribute_and_class_Entropy+(double)attribute_value_count/noOfRecords*attribute_and_class_Entropy;
        }//end of for
        //System.out.println("average_attribute_and_class_Entropy="+average_attribute_and_class_Entropy);
        return average_attribute_and_class_Entropy;
    }//end of calculateEntropyOfCategoricalAttribute()
    /***************************************************************************************
    * calculateProbabilityOfFirstIntervalAndClass method
     ***************************************************************************************/
    public double calculateProbabilityOfFirstIntervalAndClass(String classLabel,double min,double max,int attributeNo)
    {
        double intervalAndClassProbability=0.0;
        int intervalAndClassCount=0;
        for(int loopCounter=0;loopCounter<=noOfRecords-1;loopCounter++)
        {
            if(!dataSet[loopCounter][attributeNo].equals("?"))
            {
                if(classLabel.equals(dataSet[loopCounter][noOfAttributes-1])&&min<=Double.valueOf(dataSet[loopCounter][attributeNo])&&max>=Double.valueOf(dataSet[loopCounter][attributeNo]))
                    intervalAndClassCount++;
            }//end of if
        }//end of for
        //System.out.println("intervalAndClassCount="+intervalAndClassCount);//for testing
        intervalAndClassProbability=(double)intervalAndClassCount/noOfRecords;
        //System.out.println("classProbability="+intervalAndClassProbability);//for testing
        return intervalAndClassProbability;
    }//end of calculateProbabilityOfFirstIntervalAndClass()
    /***************************************************************************************
    * calculateProbabilityOfIntervalAndClass method
     ***************************************************************************************/
    public double calculateProbabilityOfIntervalAndClass(String classLabel,double min,double max,int attributeNo)
    {
        double intervalAndClassProbability=0.0;
        int intervalAndClassCount=0;
        for(int loopCounter=0;loopCounter<=noOfRecords-1;loopCounter++)
        {
            if(!dataSet[loopCounter][attributeNo].equals("?"))
            {
                if(classLabel.equals(dataSet[loopCounter][noOfAttributes-1])&&min<Double.valueOf(dataSet[loopCounter][attributeNo])&&max>=Double.valueOf(dataSet[loopCounter][attributeNo]))
                    intervalAndClassCount++;
            }//end of if
        }//end of for
        //System.out.println("intervalAndClassCount="+intervalAndClassCount);//for testing
        intervalAndClassProbability=(double)intervalAndClassCount/noOfRecords;
        //System.out.println("classProbability="+intervalAndClassProbability);//for testing
        return intervalAndClassProbability;
    }//end of calculateProbabilityOfIntervalAndClass()
    /***************************************************************************************
    * calculateProbabilityOfClass method
     ***************************************************************************************/
    public double calculateProbabilityOfAttributeAndClass(String classLabel,String attributeValue,int attributeNo)
    {
        double attributeAndClassProbability=0.0;
        int attributeAndClassCount=0;
        for(int loopCounter=0;loopCounter<=noOfRecords-1;loopCounter++)
        {
            if(classLabel.equals(dataSet[loopCounter][noOfAttributes-1])&&attributeValue.equals(dataSet[loopCounter][attributeNo]))
                attributeAndClassCount++;
        }//end of for
        //System.out.println("classCount="+classCount);//for testing
        attributeAndClassProbability=(double)attributeAndClassCount/noOfRecords;
        //System.out.println("classProbability="+classProbability);//for testing
        return attributeAndClassProbability;
    }//end of calculateProbabilityOfClass()
    /***************************************************************************************
    * calculateAtttributeValueCount method
     ***************************************************************************************/
    public int calculateAtttributeValueCount(String attributeValue,int attributeNo)
    {
        int attribute_value_count=0;
        for(int loopCounter=0;loopCounter<=noOfRecords-1;loopCounter++)
        {
            if(attributeValue.equals(dataSet[loopCounter][attributeNo]))
                attribute_value_count++;
        }//end of for
        //System.out.println("classCount="+classCount);//for testing
        //System.out.println("classProbability="+classProbability);//for testing
        return attribute_value_count;
    }//end of calculateAtttributeValueCount()
    /***************************************************************************************
    * calculateFirstIntervalValueCount method
     ***************************************************************************************/
    public int calculateFirstIntervalValueCount(double min,double max,int attributeNo)
    {
        int interval_value_count=0;
        for(int loopCounter=0;loopCounter<=noOfRecords-1;loopCounter++)
        {
            if(!dataSet[loopCounter][attributeNo].equals("?"))
            {
                if(min<=Double.valueOf(dataSet[loopCounter][attributeNo])&&max>=Double.valueOf(dataSet[loopCounter][attributeNo]))
                    interval_value_count++;
            }//end of if
        }//end of for
        //System.out.println("interval_value_count="+interval_value_count);//for testing
        return interval_value_count;
    }//end of calculateFirstIntervalValueCount()
    /***************************************************************************************
    * calculateIntervalValueCount method
     ***************************************************************************************/
    public int calculateIntervalValueCount(double min,double max,int attributeNo)
    {
        int interval_value_count=0;
        for(int loopCounter=0;loopCounter<=noOfRecords-1;loopCounter++)
        {
            if(!dataSet[loopCounter][attributeNo].equals("?"))
            {
                if(min<Double.valueOf(dataSet[loopCounter][attributeNo])&&max>=Double.valueOf(dataSet[loopCounter][attributeNo]))
                    interval_value_count++;
            }//end of if
        }//end of for
        //System.out.println("interval_value_count="+interval_value_count);//for testing
        return interval_value_count;
    }//end of calculateIntervalValueCount()
    /***************************************************************************************
    * showInformationGain method
    *
    ***************************************************************************************/
    public void showInformationGain()
    {
        for(int attributeNo=0;attributeNo<=noOfAttributes-2;attributeNo++)
        {
            System.out.println("Information gain of Attribute No"+attributeNo+"="+informationGain[attributeNo]);
        }//end of for
    }//end of showInformationGain()
}//end of class
