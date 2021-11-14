package Text_Analyzer;

	
	import java.io.*;
	import java.util.Scanner;
	import java.util.ArrayList;
	import java.util.List;
	import java.net.URL;
	import org.jsoup.Jsoup;
	import org.jsoup.nodes.Document;
	import java.util.Collections;


	/**
	 * Hello Dr. Macon, I have created a simple constructor in my Text Analyzer in order
	 * to demonstrate the JavaDoc. It does nothing yet except to receive a URL as a string, and 
	 * an int for the max number of words to be displayed..
	 * 
	 * This is a program that will download all of the HTML code from the web-site
	 * https://www.gutenberg.org/files/1065/1065-h/1065-h.htm
	 * The web-site contains the text from "The Raven" by Edgar Allen Poe.
	 * The program first writes the HTML to a local text file and then parses out
	 * all of the tags and leaves the relevant text from the literary work. Next the
	 * program counts each occurrence of every word and displays the results.
	 * 
	 * @author Dennis Heeke (CEN 3024)
	 * @version 1.0
	 *@param The only parameters are the URL of the web-site and the word count desired.
	 *@return The only return value for now comes from a couple of lines of code
	 *that test that the reader has reached the end of the HTML code and another that outputs
	 *the first 3 words of the text file to ensure it has been written successfully.
	 *
	 */
	public class Word_Count
	{
		private final String website;
		private final int maxWords;

		/**
		 * This is the constructor for the Word_Count class. 
		 * URL and maximum words to be displayed are passed here.
		 * @param website
		 * @param maxWords
		 */
		public Word_Count(String website, int maxWords) {this.website = website; this.maxWords = maxWords;}
		
		public static String WriteFile() //This method opens the URL and writes the text from The Raven to a file
		{
			System.out.println("Fetching Text From Source https://www.gutenberg.org/files/1065/1065-h/1065-h.htm");
		  	URL url;
		 	try
		 	{
		 		url = new URL("https://www.gutenberg.org/files/1065/1065-h/1065-h.htm"); //Create object for URL.
				BufferedReader readr = new BufferedReader(new InputStreamReader(url.openStream())); //Open a reader to stream the file
				BufferedWriter writer = new BufferedWriter(new FileWriter("Test_Download.txt")); //Create file to store the downloaded info.
				String line;
	            while ((line = readr.readLine()) != null) //Read the html source code from the website.
	             {
	                writer.write(line); //Write the html code to a local file.
				 }
				readr.close();
	            writer.close();
	            return line;
		 	}
		 	catch
		 	(IOException  e)
		 	{
	        System.out.println("Error: " + e.getMessage());
	        e.printStackTrace();
	        return "error";
		 	}
		}
		
		public static String ParseFile()
		{
			 System.out.println("Parsing text of your downloaded file...");
			  try
			  {
				  File input = new File("Test_Download.txt"); //Open the newly written file containing the HTML from The Raven.
				  Document doc = Jsoup.parse(input, "UTF-8"); //Parse the file to eliminate the tags
				  String body = doc.body().text(); //Save the parsed words to a string.
				  BufferedWriter writer = new BufferedWriter(new FileWriter("Test_Download.txt"));//rewrite the file without the tags.
				  writer.write(body);
				  writer.close(); 
				  return body.substring(0, 21);
			  }
			  catch(IOException e)
			  {
			        System.out.println("Error: " + e.getMessage());
			        e.printStackTrace();
			        return "error";
			  }
		}
		
		public static void DisplayResults()
		{
			 try
			  {
				  File file = new File("Test_Download.txt"); //Open the file we have written for a word count analysis.
			        Scanner sc = new Scanner(file); //Create a scanner to read the rewritten text file.
			        int i = 0, indexOfWord = 0, count = 0;
			        List<String> words = new ArrayList<String>(); //Create two arrays to store the words and word count.
			        List<Integer> wordCount = new ArrayList<Integer>();
			        
			        while (sc.hasNext()) //Starting here we read each individual word of the file.
			        {
			         String word = sc.next();
			         word = word.replaceAll("\\p{Punct}", " "); //This removes the punctuation and any non letters.;
			           if(words.contains(word))//Here if the word already exist we just increment a counter for that word.
			           {
			            indexOfWord = words.indexOf(word);
			            count = wordCount.get(indexOfWord);
			            count = count+1;
			            wordCount.add(indexOfWord, count);        
			           }
			           else
			           {
			            words.add(i,word);
			            wordCount.add(i,1);
			            i++;
			           }
			          }sc.close();
			         

			         
			         
			         Integer max = Collections.max(wordCount); //At this point we go through the array and sort it by max occurrences of words
			         System.out.println("max word occurence is:" + max);// and then display them in descending order.
			         System.out.println("|Count|Word");
			   	  	 System.out.println("|-----|-----------------|");
			   	  	 
			   	  int no_of_elements = words.size();
			      
			      while(max != 0)
			      {
			       for(int j = 0; j < no_of_elements; j++)
			       {       	       	
			      	 String word = words.get(j);
			      	 count = wordCount.get(j);
			      	 if(count == max)
			      	 {
			      	 //if(word.isEmpty()){continue;}// I put this in because the program was displaying random blanks for some reason.
			      	 System.out.printf("|%-4d", count);
			      	 System.out.printf(" |" + word + "\n");
			      	 }
					
			       }
			       max--;
			      }
			  }
			  catch(IOException e)
			  {
			        System.out.println("Error: " + e.getMessage());
			        e.printStackTrace();
			  }
		  
		  }
		
		
	  public static void main(String[] args) throws Exception
	  {
	 	String output1 = WriteFile();
	 	System.out.println(output1);
	 	String output2 = ParseFile();
	 	System.out.println(output2);
	 	DisplayResults();
	  }
	 }
