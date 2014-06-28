import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.SpringLayout;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.RestoreAction;

/**
* ���ر�Ҷ˹������
*/
public class BayesClassifier 
{
	private TrainingDataManager tdm;//ѵ����������
	private static double zoomFactor = 10.0f;
	private static double[][] Plength = {
		{ 0.0016 , 0.0016 , 0.0288 , 0.0599 , 0.1869 , 0.1757 , 0.3435 , 0.2061},
		{ 0.4934 , 0.3031 , 0.1143 , 0.0443 , 0.0208 , 0.0099 , 0.0075 , 0.0067}
	};
	/**
	* Ĭ�ϵĹ���������ʼ��ѵ����
	*/
	public BayesClassifier() 
	{
		tdm =new TrainingDataManager();
	}

	/**
	* ����������ı���������X�ڸ����ķ���Cj�е�����������
	* <code>ClassConditionalProbability</code>����ֵ
	* @param X �������ı���������
	* @param Cj ���������
	* @return ����������������ֵ����<br>
	*/
	double calcProd(String[] X , int Classification , int k) 
	{
		double ret = 1.0F;
		// ��������������
		for (int i = 0; i <X.length; i++)
		{
			String Xi = X[i];
			//��Ϊ�����С�����������֮ǰ�Ŵ�10����������ս������Ӱ�죬��Ϊ����ֻ�ǱȽϸ��ʴ�С����
			ret *=ClassConditionalProbability.calculatePxc(Xi , Classification)*zoomFactor;
		}
		// �ٳ����������
		ret = ret*ClassConditionalProbability.getPriorProbability(Classification);
		ret = (double) (ret * Plength[Classification][k]);
		return ret;
	}
	/**
	* ȥ��ͣ�ô�
	* @param text �������ı�
	* @return ȥͣ�ôʺ���
	*/
	public String[] DropStopWords(String[] oldWords)
	{
		Vector<String> v1 = new Vector<String>();
		for(int i=0;i<oldWords.length;++i)
		{
			if(StopWordsHandler.IsStopWord(oldWords[i])==false)
			{//����ͣ�ô�
				v1.add(oldWords[i]);
			}
		}
		String[] newWords = new String[v1.size()];
		v1.toArray(newWords);
		return newWords;
	}
	
	public int PlengthType(String str){
		int k = 0;
		k = str.length() / 10 ; 
		if (k>7) return 7;
		else return k;
	}
	/**
	* �Ը������ı����з���
	* @param text �������ı�
	* @return ������
	*/
	@SuppressWarnings("unchecked")
	public double classify(String text , int Classification) 
	{
		String[] terms = null;
		int k = PlengthType(text);
		terms= ChineseSpliter.split(text, " ").split(" ");//���ķִʴ���(�ִʺ������ܻ�������ͣ�ôʣ�
		terms = DropStopWords(terms);//ȥ��ͣ�ôʣ�����Ӱ�����
		return (calcProd(terms , Classification , k));		
	}
	
	public static void main (String[] args) throws IOException
	{
		File filetest = new File ("zfdxtest.txt");
		Scanner input = new Scanner(filetest);
		String text[] = new String [30000];
		int value = 0;
		while (input.hasNext()){
			text[value] = input.nextLine();
			value++;
		}
		input.close();
		BayesClassifier classifier = new BayesClassifier();//����Bayes������
		int CheckCount = 0;
		for (int i=0 ; i<value ; i++){
		SpecialClassify s=new SpecialClassify(text[i]);
		if (s.CheckSpecialWordsMethod()){
		double result0 = classifier.classify(text[i] , 0);//���з���
		double result1 = classifier.classify(text[i] , 1);
		//System.out.print(i+" ��P0["+result0+"]"+" P1["+result1+"]");
		double K;
		//if (text[i].length()<15) K = (text[i].length()/7) * (text[i].length()/7);
		K=10000;
		if ((result0/result1)<K){
		//System.out.print(text[i]+" ");
		//System.out.printf("p0:%.6f p1:%.6f" , ClassConditionalProbability.calculatePxc(text[i], 0),ClassConditionalProbability.calculatePxc(text[i], 1));
		//System.out.println();
			//System.out.println(result0);
			//System.out.println(result1);
			System.out.println(result0/result1);
		CheckCount++;
		}
		}
		//else System.out.println();
		}
		System.out.println(value);
		System.out.printf("%.4f", (double)(CheckCount*1.0/(value)));
		System.out.println();
	}
}