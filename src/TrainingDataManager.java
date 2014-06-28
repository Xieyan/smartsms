import java.io.*;
import java.util.*;
public class TrainingDataManager {
	private File filein ;
	private File filetrain ; 
	private Scanner input ;
	private String[] WishMessage = new String [1300];
	private String[] NormalMessage = new String [30000];
	private int count=0;
	private int Nc = 0;
	private double P0 = 0F; //P0��ʾף������ռ�ܶ��ŵĸ���
	private double P1 = 0F; //P1��ʾ���ö���ռ�ܶ��ŵĸ���
	public TrainingDataManager() {
		filein = new File ("smsout.txt");
		filetrain = new File ("���Ͽ�.txt");
		//���봺��ף���������Ͽ�
		try {
			input = new Scanner (filein);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while ( input.hasNext() ){
			WishMessage[count] = input.nextLine();
			count++;
		}
		input.close();
		//���볣�ö������Ͽ�
		try {
			input = new Scanner (filetrain);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while ( input.hasNext() ){
			NormalMessage[Nc] = input.nextLine();
			Nc++;
		}
		input.close();
		P0 = (double) ((count*1.0) / (count + Nc));
		P1 = (double) (Nc*1.0) / (count + Nc);
	}
	public double getProbabilyOfC0(){
		return P0;
	}
	public double getPorbabilityOfC1(){
		return P1;
	}
	
	//����ѵ�����е�i�����ŵ�����String
	public  String getText(int i) throws FileNotFoundException,IOException 
	{
		return WishMessage[i-1];
	}
	
	public int getTrainingCountOfClassification(int i)
	{
		if (i==0) return count+1;
		else return Nc+1;
	}
	
	public int getCountContainKeyOfClassification(String key ,int Classification) 
	{
		int ret = 0;
		if (Classification == 0){
		for (int i=0 ; i<count ; i++){
			if (WishMessage[i].contains(key)){
				ret++;
			}
		}
		}
		else {
			for (int i=0 ; i<Nc ; i++){
				if (NormalMessage[i].contains(key))
					ret++;
			}
		}
		return ret;
	}
}
