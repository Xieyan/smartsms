public class ClassConditionalProbability 
{
	private static TrainingDataManager tdm = new TrainingDataManager();
	private static final double M = 0F;
	
	/**
	* ��������������
	* @param x �������ı�����
	* @param c �����ķ���
	* @return ���������µ�����������
	*/
	public static double calculatePxc(String x , int Classification) 
	{
		double ret = 0F;
		double Nxc = tdm.getCountContainKeyOfClassification(x , Classification);
		double Nc = tdm.getTrainingCountOfClassification(Classification);
		ret = (Nxc + 1) / (Nc + M);
		return ret;
	}
	
	public static double getPriorProbability(int i){
		if (i == 0) return tdm.getProbabilyOfC0();
		else return tdm.getPorbabilityOfC1();
	}
}
