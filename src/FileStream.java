import java.io.*;
import java.util.*;
public class FileStream{
	public static void main (String[] args) throws IOException{
	File filein = new File("smsout.txt");
	File fileout = new File ("TestOut.out");
	Scanner input = new Scanner (filein);	
	Message[] messageSrc = new Message[30000];
	char[] word = new char[5000];
	int [] wordCount = new int[5000];
	int wordNumber = 0;
	int value=0;
	while ( input.hasNext()){
		messageSrc[value] = new Message(input.nextLine());
		messageSrc[value].DivideIntoSentence();
		ArrayList<String> checkWordList = messageSrc[value].getList();
		for (int i=0; i<checkWordList.size() ; i++){
			for (int j =0 ; j< checkWordList.get(i).length() ; j++){
				char ch = checkWordList.get(i).charAt(j);
				boolean alreadyExsit = false; 
				for (int k =0 ; k<wordNumber ; k++){
					if ( word [k] == ch){
						wordCount[k]++;
						alreadyExsit = true;
					}
				}
				if (!alreadyExsit){
					wordNumber++;
					word[wordNumber-1] = ch;
					wordCount[wordNumber -1]++;
				}
			}
		}
		value++;
	}
	quicksort.quickSort(wordCount, 0, wordNumber-1, word);
	//messageSrc[9].DivideIntoSentence();
	//messageSrc[9].printList();
	PrintWriter output = new PrintWriter (fileout);
	for (int i= 0 ; i<wordNumber ; i++){
		output.println(word[i]+ " "+ wordCount[i]);
	}
	//for (int i = value-1 ; i>=0 ;i--){
	//	output.println(message[i]);
	//}
	input.close();
	output.close();
	
	File fileMessageWordNumber = new File("smsWordNum.out");
	PrintWriter outputnum = new PrintWriter (fileMessageWordNumber);
	double totalWords = 0;
	double meanWords = 0;
	double totV = 0;
	double meanV = 0;
	for (int i =0 ; i< Message.count ; i++ ){
		totalWords += messageSrc[i].getWordsNumber();
		totV += messageSrc[i].getVariance();
		outputnum.print(i+": "+messageSrc[i].getWordsNumber());
		outputnum.printf(" %.2f" ,messageSrc[i].getVariance());
		outputnum.println();
		}
	meanWords = totalWords / Message.count;
	meanV = totV / Message.count;
	outputnum.printf("meanWords : %.2f meanVariance : %.2f" ,meanWords ,meanV );
	outputnum.println();
	outputnum.close();
	
	File messageLengthAcount = new File ("smsLengthPersentage.out");
	PrintWriter outputLength = new PrintWriter (messageLengthAcount);
	int[] MessageLength = new int[10];
	for (int i = 0 ; i<Message.count ; i++){
		if (messageSrc[i].smsLength <= 10) MessageLength[0]++;
		if (messageSrc[i].smsLength <= 20 && messageSrc[i].smsLength >10 ) MessageLength[1]++;
		if (messageSrc[i].smsLength <= 30 && messageSrc[i].smsLength >20 ) MessageLength[2]++;
		if (messageSrc[i].smsLength <= 40 && messageSrc[i].smsLength >30 ) MessageLength[3]++;
		if (messageSrc[i].smsLength <= 50 && messageSrc[i].smsLength >40 ) MessageLength[4]++;
		if (messageSrc[i].smsLength <= 60 && messageSrc[i].smsLength >50 ) MessageLength[5]++;
		if (messageSrc[i].smsLength <= 70 && messageSrc[i].smsLength >60 ) MessageLength[6]++;
		if (messageSrc[i].smsLength >70 ) MessageLength[7]++;
	}
	for (int i =0 ; i < 8 ; i++){
		outputLength.print(i*10+"--"+(i+1)*10+" : " );
		float per = (float) ((MessageLength[i]*1.0)/Message.count) ;
		outputLength.printf("%.2f", per*100);
		outputLength.println();
	}
	outputLength.close();
	}
}
