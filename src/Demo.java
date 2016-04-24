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
    public int compareTo(Object o) {  //降序
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
      
    /** 给定背包数量  */  
    private int n;  
    
    /** 前 n 个背包，总承重为 totalWeight 的最优值矩阵  */  
    private int[][] bestValues;  
      
    /** 前 n 个背包，总承重为 totalWeight 的最优值 */  
    private int bestValue;  
      
    /** 前 n 个背包，总承重为 totalWeight 的最优解的物品组成 */  
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
     * 求解前 n 个背包、给定总承重为 totalWeight 下的背包问题 
     *  
     */  
    public void zero_one_knapsack() {  
          
        System.out.println("给定背包：");  
        for(Knapsack b: bags) {  
            System.out.println(b);  
        }  
        System.out.println("给定总承重: " + totalWeight);  
          
        // 求解最优值  
        for (int j = 0; j <= totalWeight; j++) {  
            for (int i = 0; i <= n; i++) {  
                if (i == 0 || j == 0) {  
                    bestValues[i][j] = 0;  
                }     
                else   
                {  
                    // 如果第 i 个背包重量大于总承重，则最优解存在于前 i-1 个背包中，  
                    // 注意：第 i 个背包是 bags[i-1]  
                    if (j < bags[i-1].weight) {  
                        bestValues[i][j] = bestValues[i-1][j];  
                    }     
                    else   
                    {  
                        // 如果第 i 个背包不大于总承重，则最优解要么是包含第 i 个背包的最优解，  
                        // 要么是不包含第 i 个背包的最优解， 取两者最大值，这里采用了分类讨论法  
                        // 第 i 个背包的重量 iweight 和价值 ivalue  
                        int iweight = bags[i-1].weight;  
                        int ivalue = bags[i-1].value;  
                        bestValues[i][j] =   
                            Math.max(bestValues[i-1][j], ivalue + bestValues[i-1][j-iweight]);        
                    }  
                }           
            } 
        }  
          
        // 求解背包组成  
        if (bestSolution == null) {  
            bestSolution = new ArrayList<Knapsack>();  
        }  
        int tempWeight = totalWeight;  
        for (int i=n; i >= 1; i--) {  
           if (bestValues[i][tempWeight] > bestValues[i-1][tempWeight]) {  
               bestSolution.add(bags[i-1]);  // bags[i-1] 表示第 i 个背包  
               tempWeight -= bags[i-1].weight;  
           }  
           if (tempWeight == 0) { break; }  
        }  
        bestValue = bestValues[n][totalWeight];  
    }  
      
    /** 
     * 获得前  n 个背包， 总承重为 totalWeight 的背包问题的最优解值 
     * 调用条件： 必须先调用 solve 方法 
     *  
     */  
    public int getBestValue() {   
        return bestValue;  
    }  
      
    /** 
     * 获得前  n 个背包， 总承重为 totalWeight 的背包问题的最优解值矩阵 
     * 调用条件： 必须先调用 solve 方法 
     *  
     */  
    public int[][] getBestValues() {  
          
        return bestValues;  
    }  
      
    /** 
     * 获得前  n 个背包， 总承重为 totalWeight 的背包问题的最优解值矩阵 
     * 调用条件： 必须先调用 solve 方法 
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
        System.out.println(" -------- 0-1背包问题的解: --------- ");  
        System.out.println("最优值：" + kp.getBestValue());   
        System.out.println("最优解【选取的背包】: ");  
        System.out.println(kp.getBestSolution());
        double[] fra = new double[bags.length];
        kp.fractional_knapsack(bags, totalWeight, fra);
        System.out.println("选取每个背包的比例：");
        for(int i=0; i<bags.length; ++i)
		{
        	System.out.println("[weight: " + bags[i].weight + " " + "value: " + bags[i].value + "] 比例为："+fra[i]);
		}
    }  
}   