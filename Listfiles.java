
import java.io.*;
import java.util.*;
import java.text.*;
import java.io.File;
import java.io.FilenameFilter;
 
public class Listfiles 
{
    public static void main(String[] args) throws IOException
    {
        BufferedReader stdin = new BufferedReader (new InputStreamReader (System.in));
        BufferedReader reader = new BufferedReader(new FileReader("directories.txt"));
        String line = reader.readLine ();
        
        String [] directories = new String [20];
        int counter =0, jsonCounter = 0, totalFileCounter = 0, fileCounter = 0 , leave = 0;
        String output = "prevoty_all.log", output2 = "prevoty_json_all.log";
        
        while (line != null) //Read directories 
        {
            directories [counter] = line;
            counter++;
            line = reader.readLine();
        }
        
        for (int a = 0; a < counter; a++) //For loop prevents any null in directories.txt
        {
        	if (directories [a].equals (""))
        	{
        		directories [a] = directories [0]; 
        	}
        }
        
        
        for (int i = 0; i < counter; i++) { //Opens one directory at a time
                
                File folder = new File(directories[i]); //Reads all the prevoty_json files first and puts into an array        
                FilenameFilter txtFileFilter = new FilenameFilter()
                {    
                        @Override
                        public boolean accept(File dir, String name)
                        {
                                if (name.startsWith("prevoty_json"))
                                {
                                	return true;
                                }
                                else
                                {
                                    return false;
                                }
                        }
                };  
     
                File[] files = folder.listFiles(txtFileFilter); //Puts all the prevoty_json files into an array
                jsonCounter = files.length; //Counts the amount of json files
                
                
                for (int k = 0; k < files.length; k++)   //Gets rid of one file if the ALL file is present
                {
                        if (("prevoty_json_all.log").equalsIgnoreCase((files [k].getName ())))
                        {
                                jsonCounter--;
                        }
                }
  
                txtFileFilter = new FilenameFilter() //Filters for all files with prevoty, will get rid of files that do not start with prevoty
                {    
                        @Override
                        public boolean accept(File dir, String name)
                        {
                                if (name.startsWith("prevoty"))
                                {
                                	return true;
                                }
                                else
                                {
                                    return false;
                                }
                        }
                };  
                
                files = folder.listFiles(txtFileFilter);  //Counts all the files in the directory 
                totalFileCounter  = files.length;
                                    
                for (int a = 0; a < files.length; a++) //Subtract total file count if the ALL files are present
                {
                        if (("prevoty_all.log").equalsIgnoreCase((files [a].getName ())) ||("prevoty_json_all.log").equalsIgnoreCase((files [a].getName ())) )
                        {
                                totalFileCounter--;
                                leave = 1; //Indicates all file is present already, program will not create new one
                        }
                } 
                
                fileCounter = totalFileCounter - jsonCounter;
                 for (int j = (totalFileCounter-jsonCounter); j > 0; j--) //Write to DEBUG ALL
                 {
                         String file2 = directories [i]; 
                         String file1 = "";
         
                         if (leave == 1 || fileCounter == 0) //Wont write anything if the ALL file is present 
                         {
                        	 break;
                         }
                         
                         if (fileCounter == 1)
                         {
                        	 file1 = directories[i]+"prevoty";
                         }
                         else if (fileCounter > 1)
                         {
                        	 file1 = directories[i]+ "prevoty_" + (fileCounter-1);
                        	 fileCounter--;
                         }
           
                         reader = new BufferedReader(new FileReader(file1 + ".log"));
                         line = reader.readLine ();
                     
                         BufferedWriter writer = new BufferedWriter(new FileWriter (file2+output,true));
         
                         while (line != null)
                         {
                                 writer.write(line);
                                 writer.newLine (); 
                                 line = reader.readLine();
                         }
         
                         writer.flush();
                         reader.close();
                         writer.close();
                 }  
                 
                 fileCounter = jsonCounter;
                for (int h = jsonCounter; h > 0; h--) //Write to JSON ALL
                 {
                         String file2 = directories [i]; 
                         String file1 = "";
                         
                         if (jsonCounter == 0 || leave == 1) //Wont write anything if the All file is present or no files
                         {
                        	 break;
                         }
         
                         if (fileCounter == 1)
                         {
                        	 file1 = directories[i]+"prevoty_json";
                         }
                         else if (fileCounter > 1)
                         {
                        	 file1 = directories[i]+ "prevoty_json_" + (fileCounter-1);
                        	 fileCounter--;
                         }
                         
                         reader = new BufferedReader(new FileReader(file1 + ".log"));
                         line = reader.readLine ();
                         
                         BufferedWriter writer = new BufferedWriter(new FileWriter (file2+output2,true));
         
                         while (line != null)
                         {
                                 writer.write(line);
                                 writer.newLine (); 
                                 line = reader.readLine();
                         }
         
                         writer.flush();
                         reader.close();
                         writer.close();
                 } 
                 
                //Variable declarations
                 output = "prevoty_all.log"; 
                 output2 = "prevoty_json_all.log";     
                 totalFileCounter = 0; 
                 jsonCounter = 0;
                 leave = 0;
        }   
    }
}
