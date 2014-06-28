
public class quicksort {
	public static void quickSort(int a[], int start, int end , char c[])
	{        int i,j;
	         i = start;
	         j = end;
	         if((a==null)||(a.length==0))
	             return;
	         while(i<j){
	             while(i<j&&a[i]>=a[j]){     //������start�±������Ϊkey���Ҳ�ɨ��
	                 j--;
	             }
	             if(i<j){                   //�Ҳ�ɨ�裬�ҳ���һ����keyС�ģ�����λ��
	                 int temp = a[i];
	                 a[i] = a[j];
	                 a[j] = temp;
	                 char ctemp = c[i];
	                 c[i] = c[j];
	                 c[j] = ctemp;
	             }
	              while(i<j&&a[i]>a[j]){    //���ɨ�裨��ʱa[j]�д洢��keyֵ��
	                 i++;
	               }
	             if(i<j){                 //�ҳ���һ����key��ģ�����λ��
	                 int temp = a[i];
	                 a[i] = a[j];
	                 a[j] = temp;
	                 char ctemp = c[i];
	                 c[i] = c[j];
	                 c[j] = ctemp;
	             }
	        }
	        if(i-start>1){
	             //�ݹ���ã���keyǰ����������
	            quickSort(a,start,i-1 ,c);
	        }
	        if(end-i>1){
	            quickSort(a,i+1,end ,c);    //�ݹ���ã���key������������
	        }
	}
}
