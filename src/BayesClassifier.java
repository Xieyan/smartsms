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
* 朴素贝叶斯分类器
*/
public class BayesClassifier 
{
	private TrainingDataManager tdm;//训练集管理器
	private static double zoomFactor = 10.0f;
	private static double[][] Plength = {
		{ 0.0016 , 0.0016 , 0.0288 , 0.0599 , 0.1869 , 0.1757 , 0.3435 , 0.2061},
		{ 0.4934 , 0.3031 , 0.1143 , 0.0443 , 0.0208 , 0.0099 , 0.0075 , 0.0067}
	};
	/**
	* 默认的构造器，初始化训练集
	*/
	public BayesClassifier() 
	{
		tdm =new TrainingDataManager();
	}

	/**
	* 计算给定的文本属性向量X在给定的分类Cj中的类条件概率
	* <code>ClassConditionalProbability</code>连乘值
	* @param X 给定的文本属性向量
	* @param Cj 给定的类别
	* @return 分类条件概率连乘值，即<br>
	*/
	double calcProd(String[] X , int Classification , int k) 
	{
		double ret = 1.0F;
		// 类条件概率连乘
		for (int i = 0; i <X.length; i++)
		{
			String Xi = X[i];
			//因为结果过小，因此在连乘之前放大10倍，这对最终结果并无影响，因为我们只是比较概率大小而已
			ret *=ClassConditionalProbability.calculatePxc(Xi , Classification)*zoomFactor;
		}
		// 再乘以先验概率
		ret = ret*ClassConditionalProbability.getPriorProbability(Classification);
		ret = (double) (ret * Plength[Classification][k]);
		return ret;
	}
	/**
	* 去掉停用词
	* @param text 给定的文本
	* @return 去停用词后结果
	*/
	public String[] DropStopWords(String[] oldWords)
	{
		Vector<String> v1 = new Vector<String>();
		for(int i=0;i<oldWords.length;++i)
		{
			if(StopWordsHandler.IsStopWord(oldWords[i])==false)
			{//不是停用词
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
	* 对给定的文本进行分类
	* @param text 给定的文本
	* @return 分类结果
	*/
	@SuppressWarnings("unchecked")
	public double classify(String text , int Classification) 
	{
		String[] terms = null;
		int k = PlengthType(text);
		terms= ChineseSpliter.split(text, " ").split(" ");//中文分词处理(分词后结果可能还包含有停用词）
		terms = DropStopWords(terms);//去掉停用词，以免影响分类
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
		BayesClassifier classifier = new BayesClassifier();//构造Bayes分类器
		int CheckCount = 0;
		for (int i=0 ; i<value ; i++){
		SpecialClassify s=new SpecialClassify(text[i]);
		if (s.CheckSpecialWordsMethod()){
		double result0 = classifier.classify(text[i] , 0);//进行分类
		double result1 = classifier.classify(text[i] , 1);
		//System.out.print(i+" ：P0["+result0+"]"+" P1["+result1+"]");
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