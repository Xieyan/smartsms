/**
 * ͣ�ôʴ�����
 * 
 * @author phinecos
 * 
 */
public class StopWordsHandler {
	private static String stopWordsList[] = { "��", "����", "Ҫ", "�Լ�", "֮", "��",
			"��", "��", "��", "��", "��", "��", "Ӧ", "��", "ĳ", "��", "��", "��", "λ",
			"�Ǻ�", "����", "��", "��", "��", "��", "��", "��", "һ", "��", "��", "��", "��","Ŷ",
			"��","ô","" };// ����ͣ�ô�

	public static boolean IsStopWord(String word) {
		for (int i = 0; i < stopWordsList.length; ++i) {
			if (word.equalsIgnoreCase(stopWordsList[i]))
				return true;
		}
		return false;
	}
}
