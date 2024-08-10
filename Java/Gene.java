
public class Gene
{
    String geneValue;
    String minGeneValue;
    String maxGeneValue;
    String catGeneValue[];
    /***************************************************************************************
    * Constructor
    *
    ***************************************************************************************/
    public Gene(String geneValue1,String typeOfAttribute,Dataset trainingDataSet,int attributeNo)
    {
        geneValue=geneValue1;        
        if(typeOfAttribute.equals("Integer")|typeOfAttribute.equals("Float"))
        {
            if(geneValue.equals("#"))
            {
                minGeneValue="#";
                maxGeneValue="#";
            }//end of if
            else
            {
                int startIndex=0;
                int endIndex=0;
                for(int counter3=0;counter3<=geneValue.length()-1;counter3++)
                {
                    if(geneValue.charAt(counter3)==',')
                    {
                        endIndex=counter3;
                        minGeneValue=geneValue.substring(startIndex,endIndex);
                        maxGeneValue=geneValue.substring(endIndex+1);
                        //System.out.println("minGeneValue="+minGeneValue);//for testing
                        //System.out.println("maxGeneValue="+maxGeneValue);//for testing
                    }//end of if
                }//end of for
            }//end of else
        }//end of if
        else if(typeOfAttribute.equals("Binary")|typeOfAttribute.equals("String"))
        {
            if(geneValue.equals("#"))
            {
                geneValue="#";
            }//end of if
            else
            {
                int noOfValues=0;
                for(int i=0;i<=geneValue.length()-1;i++)
                {
                    if(geneValue.charAt(i)=='1')
                    {
                        noOfValues++;
                    }//end of if
                }//end of for
                catGeneValue=new String[noOfValues];
                int counter1=0;
                for(int i=0;i<=geneValue.length()-1;i++)
                {
                    if(geneValue.charAt(i)=='1')
                    {                        
                        catGeneValue[counter1]=trainingDataSet.attributeValues[attributeNo][i];
                        //System.out.println("catValues[counter1]="+catGeneValue[counter1]);//for testing
                        counter1++;
                    }//end of if
                }//end of for                
            }//end of else
        }//end of if
    }//end of method
}//end of class