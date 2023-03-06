import java.util.*;

public class TSPGeneticAlgorithm {
    private int numCities; // 城市数量
    private int[][] distanceMatrix; // 距离矩阵
    private int populationSize; // 种群大小
    private int maxGenerations; // 最大迭代次数
    private double mutationRate; // 变异率
    private Random random; // 随机数生成器

    public TSPGeneticAlgorithm(int numCities, int[][] distanceMatrix, int populationSize, int maxGenerations, double mutationRate) {
        this.numCities = numCities;
        this.distanceMatrix = distanceMatrix;
        this.populationSize = populationSize;
        this.maxGenerations = maxGenerations;
        this.mutationRate = mutationRate;
        this.random = new Random();
    }

    // 遗传算法求解TSP问题
    public int[] solve() {
        // 初始化种群
        List<int[]> population = initializePopulation();

        // 迭代次数
        int generation = 0;

        // 不断进行迭代，直到达到最大迭代次数
        while (generation < maxGenerations) {
            // 选择和交叉
            population = selectAndCrossover(population);

            // 变异
            mutate(population);

            // 计算适应度
            double[] fitnesses = computeFitnesses(population);

            // 找到最优解
            int bestIndex = findBestIndex(fitnesses);
            int[] bestSolution = population.get(bestIndex);

            // 打印当前最优解
            System.out.println("Generation " + generation + ": Best solution = " + Arrays.toString(bestSolution) + ", Distance = " + computeDistance(bestSolution));

            // 更新迭代次数
            generation++;
        }

        // 返回最优解
        double[] fitnesses = computeFitnesses(population);
        int bestIndex = findBestIndex(fitnesses);
        return population.get(bestIndex);
    }

    private String computeDistance(int[] bestSolution) {
        return null;
    }

    private int findBestIndex(double[] fitnesses) {
        return 0;
    }

    // 初始化种群
    private List<int[]> initializePopulation() {
        List<int[]> population = new ArrayList<>();

        // 对每个个体随机生成一条路径
        for (int i = 0; i < populationSize; i++) {
            int[] path = new int[numCities];
            for (int j = 0; j < numCities; j++) {
                path[j] = j;
            }
            shuffle(path);
            population.add(path);
        }

        return population;
    }

    // 随机打乱一个数组
    private void shuffle(int[] array) {
        for (int i = 0; i < array.length; i++) {
            int j = random.nextInt(array.length);
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }

    // 选择和交叉
    private List<int[]> selectAndCrossover(List<int[]> population) {
        List<int[]> newPopulation = new ArrayList<>();

        // 对种群进行排序，按照适应度从高到低排序
        Collections.sort(population, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                double fitness1 = computeFitness(o1);
                double fitness2 = computeFitness(o2);
                return Double.compare(fitness2, fitness1);
            }
        });    // 选择前50%的个体进行交叉
        int numParents = populationSize / 2;
        for (int i = 0; i < numParents; i++) {
            // 选择两个父亲
            int[] parent1 = selectParent(population);
            int[] parent2 = selectParent(population);

            // 进行交叉
            int[] child = crossover(parent1, parent2);

            // 将新个体加入新种群
            newPopulation.add(child);
        }

        // 将原种群中的前50%个体也加入新种群
        for (int i = 0; i < numParents; i++) {
            newPopulation.add(population.get(i));
        }

        return newPopulation;
    }

    // 选择一个父亲
    private int[] selectParent(List<int[]> population) {
        // 计算每个个体的适应度
        double[] fitnesses = computeFitnesses(population);

        // 根据适应度进行轮盘赌选择
        double totalFitness = 0.0;
        for (double fitness : fitnesses) {
            totalFitness += fitness;
        }

        double r = random.nextDouble() * totalFitness;
        double sum = 0.0;
        for (int i = 0; i < populationSize; i++) {
            sum += fitnesses[i];
            if (sum > r) {
                return population.get(i);
            }
        }

        // 如果无法选择，返回随机一个个体
        return population.get(random.nextInt(populationSize));
    }

    // 交叉两个个体
    private int[] crossover(int[] parent1, int[] parent2) {
        int[] child = new int[numCities];

        // 随机选择交叉点
        int crossoverPoint = random.nextInt(numCities - 1) + 1;

        // 将父亲1的前半部分复制到孩子中
        for (int i = 0; i < crossoverPoint; i++) {
            child[i] = parent1[i];
        }

        // 将父亲2中未出现在孩子中的城市添加到孩子中
        int index = crossoverPoint;
        for (int i = 0; i < numCities; i++) {
            if (!contains(child, parent2[i])) {
                child[index] = parent2[i];
                index++;
            }
        }

        return child;
    }

    // 判断一个数组中是否包含某个元素
    private boolean contains(int[] array, int element) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == element) {
                return true;
            }
        }
        return false;
    }

    // 变异种群
    private void mutate(List<int[]> population) {
        for (int[] individual : population) {
            if (random.nextDouble() < mutationRate) {
                mutateIndividual(individual);
            }
        }
    }

    // 变异个体
    private void mutateIndividual(int[] individual) {
        // 随机选择两个位置进行交换
        int index1 = random.nextInt(numCities);
        int index2 = random.nextInt(numCities);
        int temp = individual[index1];
        individual[index1] = individual[index2];
        individual[index2] = temp;
    }

    // 计算一个个体的适应度
    private double computeFitness(int[] individual) {
        double distance = 0.0;
        double[][] distances = new double[0][];
        for (int i = 0; i < numCities - 1; i++) {
            int city1 = individual[i];
            int city2 = individual[i + 1];
            distance += distances[city1][city2];
        }
        distance += distances[individual[numCities - 1]][individual[0]];
        return 1.0 / distance;
    }

    // 计算种群中所有个体的适应度
    private double[] computeFitnesses(List<int[]> population) {
        double[] fitnesses = new double[populationSize];
        for (int i = 0; i < populationSize; i++) {
            int[] individual = population.get(i);
            fitnesses[i] = computeFitness(individual);
        }
        return fitnesses;
    }

    // 打印一个个体
    private void printIndividual(int[] individual) {
        System.out.print("[" + individual[0]);
        for (int i = 1; i < numCities; i++) {
            System.out.print(", " + individual[i]);
        }
        System.out.println("]");
    }
}


