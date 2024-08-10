
public class generateResultFile
{
    public void generateResultFile(String results[],String resultFileName)
    {
        FileUtil fileResults=new FileUtil(resultFileName);
        fileResults.writeFile();
        for(int counter=0;counter<=results.length-1;counter++)
        {
            fileResults.writeString(results[counter]);
        }//end of for
    }//end of generateResultFile()
}//end of class
