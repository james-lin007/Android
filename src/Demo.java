import java.util.ArrayList;
import java.util.*;

class Knapsack implements Comparable {  
      
    public int weight;  
    public int value;  
    public int num;
    public Knapsack(int weight, int value, int num) {  
        this.value = value;  
        this.weight = weight; 
        this.num = num;
    }  
    public String toString() {  
        return "[weight: " + weight + " " + "value: " + value + "]";    
    } 
    public int compareTo(Object o) {  //����
		double vdw1 = (double)this.value/this.weight;
		double vdw2 = (double)((Knapsack)o).value/((Knapsack)o).weight;
		if(vdw1 > vdw2) return -1;
		else if(vdw1 == vdw2) return 0;
		else return  1;
	} 
} 

class KnapsackProblem {  
      
    private Knapsack[] bags;  
    private int totalWeight;  
      
    /** ������������  */  
    private int n;  
    
    /** ǰ n ���������ܳ���Ϊ totalWeight ������ֵ����  */  
    private int[][] bestValues;  
      
    /** ǰ n ���������ܳ���Ϊ totalWeight ������ֵ */  
    private int bestValue;  
      
    /** ǰ n ���������ܳ���Ϊ totalWeight �����Ž����Ʒ��� */  
    private ArrayList<Knapsack> bestSolution;  
      
    public KnapsackProblem(Knapsack[] bags, int totalWeight) {  
        this.bags = bags;  
        this.totalWeight = totalWeight;  
        this.n = bags.length;  
        if (bestValues == null) {  
            bestValues = new int[n+1][totalWeight+1];  
        }  
    }  
      
    /** 
     * ���ǰ n �������������ܳ���Ϊ totalWeight �µı������� 
     *  
     */  
    public void zero_one_knapsack() {  
          
        System.out.println("����������");  
        for(Knapsack b: bags) {  
            System.out.println(b);  
        }  
        System.out.println("�����ܳ���: " + totalWeight);  
          
        // �������ֵ  
        for (int j = 0; j <= totalWeight; j++) {  
            for (int i = 0; i <= n; i++) {  
                if (i == 0 || j == 0) {  
                    bestValues[i][j] = 0;  
                }     
                else   
                {  
                    // ����� i ���������������ܳ��أ������Ž������ǰ i-1 �������У�  
                    // ע�⣺�� i �������� bags[i-1]  
                    if (j < bags[i-1].weight) {  
                        bestValues[i][j] = bestValues[i-1][j];  
                    }     
                    else   
                    {  
                        // ����� i �������������ܳ��أ������Ž�Ҫô�ǰ����� i �����������Ž⣬  
                        // Ҫô�ǲ������� i �����������Ž⣬ ȡ�������ֵ����������˷������۷�  
                        // �� i ������������ iweight �ͼ�ֵ ivalue  
                        int iweight = bags[i-1].weight;  
                        int ivalue = bags[i-1].value;  
                        bestValues[i][j] =   
                            Math.max(bestValues[i-1][j], ivalue + bestValues[i-1][j-iweight]);        
                    }  
                }           
            } 
        }  
          
        // ��ⱳ�����  
        if (bestSolution == null) {  
            bestSolution = new ArrayList<Knapsack>();  
        }  
        int tempWeight = totalWeight;  
        for (int i=n; i >= 1; i--) {  
           if (bestValues[i][tempWeight] > bestValues[i-1][tempWeight]) {  
               bestSolution.add(bags[i-1]);  // bags[i-1] ��ʾ�� i ������  
               tempWeight -= bags[i-1].weight;  
           }  
           if (tempWeight == 0) { break; }  
        }  
        bestValue = bestValues[n][totalWeight];  
    }  
      
    /** 
     * ���ǰ  n �������� �ܳ���Ϊ totalWeight �ı�����������Ž�ֵ 
     * ���������� �����ȵ��� solve ���� 
     *  
     */  
    public int getBestValue() {   
        return bestValue;  
    }  
      
    /** 
     * ���ǰ  n �������� �ܳ���Ϊ totalWeight �ı�����������Ž�ֵ���� 
     * ���������� �����ȵ��� solve ���� 
     *  
     */  
    public int[][] getBestValues() {  
          
        return bestValues;  
    }  
      
    /** 
     * ���ǰ  n �������� �ܳ���Ϊ totalWeight �ı�����������Ž�ֵ���� 
     * ���������� �����ȵ��� solve ���� 
     *  
     */  
    public ArrayList<Knapsack> getBestSolution() {  
        return bestSolution;  
    } 

    public void fractional_knapsack(Knapsack[] commodities, int bag, double[] x)
	{
		Arrays.sort(commodities);
		int n = commodities.length;
		for(int i=0; i<n; ++i)
		{
			if(bag - commodities[i].weight >= 0) 
			{
				bag -= commodities[i].weight;
				x[commodities[i].num] = 1;
			}
			else
			{
				x[commodities[i].num] = (double)bag/commodities[i].weight;
				break;
			}
		}
	}
}  

public class Demo {  
      
    public static void main(String[] args) {  
          
        Knapsack[] bags = new Knapsack[] {  
                new Knapsack(10,20,0), new Knapsack(20,30,1),  
                new Knapsack(30,65,2), new Knapsack(40,40,3),  
                new Knapsack(50,60,4)
        };  
        int totalWeight = 100;  
        KnapsackProblem kp = new KnapsackProblem(bags, totalWeight);  
          
        kp.zero_one_knapsack();  
        System.out.println(" -------- 0-1��������Ľ�: --------- ");  
        System.out.println("����ֵ��" + kp.getBestValue());   
        System.out.println("���Ž⡾ѡȡ�ı�����: ");  
        System.out.println(kp.getBestSolution());
        double[] fra = new double[bags.length];
        kp.fractional_knapsack(bags, totalWeight, fra);
        System.out.println("ѡȡÿ�������ı�����");
        for(int i=0; i<bags.length; ++i)
		{
        	System.out.println("[weight: " + bags[i].weight + " " + "value: " + bags[i].value + "] ����Ϊ��"+fra[i]);
		}
    }  
}   